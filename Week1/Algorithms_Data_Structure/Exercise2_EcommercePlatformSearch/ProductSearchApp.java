import java.util.Arrays;
import java.util.Comparator;

// Exercise 2: E-commerce Platform Search - linear and binary search in one file.

class Product {
    int productId;
    String productName;
    String category;

    Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }
}

public class ProductSearchApp {

    // Linear search: check each product. O(n).
    static Product linearSearch(Product[] products, int targetId) {
        for (Product p : products) {
            if (p.productId == targetId) return p;
        }
        return null;
    }

    // Binary search: needs a sorted array. O(log n).
    static Product binarySearch(Product[] sorted, int targetId) {
        int low = 0, high = sorted.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (sorted[mid].productId == targetId) return sorted[mid];
            else if (sorted[mid].productId < targetId) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    public static void main(String[] args) {
        Product[] products = {
            new Product(105, "Keyboard", "Electronics"),
            new Product(101, "Shoes", "Fashion"),
            new Product(103, "Coffee Mug", "Home"),
            new Product(102, "Novel", "Books"),
            new Product(104, "Backpack", "Travel")
        };

        Product found = linearSearch(products, 103);
        System.out.println("Linear search found: " + (found != null ? found.productName : "not found"));

        Product[] sorted = products.clone();
        Arrays.sort(sorted, Comparator.comparingInt(p -> p.productId));
        Product found2 = binarySearch(sorted, 103);
        System.out.println("Binary search found: " + (found2 != null ? found2.productName : "not found"));
    }
}

/*
Linear search: O(n). Binary search: O(log n) but needs sorting first.
For a large, frequently-searched catalog, binary search or a hash index is far better.
*/
