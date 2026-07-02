import java.util.Arrays;
import java.util.Comparator;

// Exercise 2: E-commerce Platform Search Function
// Shows linear search on an unsorted array and binary search on a sorted one.
public class SearchDemo {

    // Linear search: check every product one by one. O(n).
    static Product linearSearch(Product[] products, int targetId) {
        for (Product p : products) {
            if (p.productId == targetId) {
                return p;
            }
        }
        return null;
    }

    // Binary search: needs a sorted array. Keeps halving the range. O(log n).
    static Product binarySearch(Product[] sorted, int targetId) {
        int low = 0;
        int high = sorted.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (sorted[mid].productId == targetId) {
                return sorted[mid];
            } else if (sorted[mid].productId < targetId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
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

        // Linear search works on the array as-is
        Product found = linearSearch(products, 103);
        System.out.println("Linear search found: " + (found != null ? found.productName : "not found"));

        // Binary search needs the array sorted by the key we search on
        Product[] sorted = products.clone();
        Arrays.sort(sorted, Comparator.comparingInt(p -> p.productId));
        Product found2 = binarySearch(sorted, 103);
        System.out.println("Binary search found: " + (found2 != null ? found2.productName : "not found"));
    }
}

/*
Complexity notes:
- Linear search: O(n). Best case O(1) if the item is first; worst case O(n) if last/absent.
- Binary search: O(log n), but the array must be sorted first.
- For a large product catalog that's searched often, binary search (or a hash-based
  index) is far better. Linear search is only fine for small or unsorted data.
*/
