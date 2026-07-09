# Exercise 2 — Inventory Management with Service Discovery + Centralized Config

Full stack: **Config Server → Eureka → Product + Inventory microservices**

## Services

| Service           | Port | Role                                    |
| ----------------- | ---- | --------------------------------------- |
| config-server     | 8888 | Serves shared config from `config-repo/`|
| eureka-server     | 8761 | Service discovery registry              |
| product-service   | 8181 | Manages products & stock (config from server) |
| inventory-service | 8182 | Tracks inventory, calls product via Eureka + Feign |

## Startup order (critical!)

```
1. config-server       → wait until it's up on :8888
2. eureka-server       → wait until it's up on :8761
3. product-service     → registers with eureka, pulls config from :8888
4. inventory-service   → registers with eureka, pulls config, calls product-service
```

Each service just runs `mvn spring-boot:run` from its own folder.

## Verify centralized config works

```bash
# Config server directly serves product-service.properties
curl http://localhost:8888/product-service/default

# Product endpoint returns the message that lives in config-repo (not local)
curl http://localhost:8181/products/welcome
# → "Products served from centralized config"

curl http://localhost:8182/inventory/welcome
# → "Inventory served from centralized config"
```

## Verify Eureka discovery works

- Open http://localhost:8761 → both `product-service` and `inventory-service` should show under "Instances currently registered with Eureka".
- Test the cross-service call (inventory-service calls product-service by **service name**, not URL):

```bash
curl http://localhost:8182/inventory/product/1
```

Returns inventory record + product details fetched via Feign+Eureka.

## Product endpoints

```bash
curl http://localhost:8181/products
curl http://localhost:8181/products/1
curl -X POST http://localhost:8181/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Monitor","category":"Electronics","price":15000,"stock":5}'
curl -X PUT "http://localhost:8181/products/1/stock?stock=25"
```

## Inventory endpoints

```bash
curl http://localhost:8182/inventory
curl http://localhost:8182/inventory/product/1
curl -X POST http://localhost:8182/inventory \
  -H "Content-Type: application/json" \
  -d '{"productId":5,"stockLevel":20,"warehouseLocation":"DEL-WH3"}'
```

## What proves this actually works

1. If you kill config-server and restart product-service, it fails to load `product.welcome-message` → proves centralized config is being pulled.
2. If you rename product-service in its `application.properties`, Feign call from inventory-service breaks → proves it's using service discovery, not URLs.
