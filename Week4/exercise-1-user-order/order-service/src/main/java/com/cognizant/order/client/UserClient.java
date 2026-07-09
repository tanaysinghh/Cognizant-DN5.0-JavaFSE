package com.cognizant.order.client;

import com.cognizant.order.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// URL-based Feign client (no Eureka needed for exercise 1).
// If Eureka were used: @FeignClient(name = "user-service") and drop the url.
@FeignClient(name = "user-service", url = "${user-service.url:http://localhost:8081}")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable("id") Long id);
}
