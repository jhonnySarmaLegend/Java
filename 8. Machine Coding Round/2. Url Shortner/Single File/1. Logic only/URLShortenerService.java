import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class URLShortenerService {
    private static final String BASE_URL = "https://short.ly/";
    private static final int CACHE_CAPACITY = 5; // LRU Cache capacity
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static long counter = 1; // Auto-increment counter for generating short URLs

    // Permanent storage for URL mappings
    private static final Map<String, String> urlMap = new HashMap<>();
    private static final Map<String, String> reverseMap = new HashMap<>();

    // LRU Cache (shortKey → longUrl)
    private static final Map<String, String> cache =
        new LinkedHashMap<String, String>(CACHE_CAPACITY, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > CACHE_CAPACITY;
            }
        };

    // Method to shorten a long URL
    public static String shortenUrl(String longUrl) {
        // Check if the URL already exists in reverseMap
        if (reverseMap.containsKey(longUrl)) {
            String existingShortKey = reverseMap.get(longUrl);
            cache.put(existingShortKey, longUrl); // Add to LRU cache
            return BASE_URL + existingShortKey;
        }

        // Create a new short key
        String shortKey = encodeBase62(counter++);
        urlMap.put(shortKey, longUrl);    // Add mapping to permanent storage
        reverseMap.put(longUrl, shortKey); // Add reverse mapping
        cache.put(shortKey, longUrl);    // Add to LRU cache

        return BASE_URL + shortKey;
    }

    // Method to retrieve long URL from short URL
    public static String getLongUrl(String shortUrl) {
        String shortKey = shortUrl.replace(BASE_URL, ""); // Extract short key from input

        // Check in the cache first
        if (cache.containsKey(shortKey)) {
            return cache.get(shortKey); // Return from cache
        }

        // Fallback to permanent storage
        String longUrl = urlMap.get(shortKey);
        if (longUrl != null) {
            cache.put(shortKey, longUrl); // Add to cache if found
            return longUrl;
        }

        return "URL not found"; // Handle the case where URL does not exist
    }

    // Base62 encoding method
    private static String encodeBase62(long number) {
        StringBuilder sb = new StringBuilder();

        while (number > 0) {
            sb.append(ALPHABET.charAt((int) (number % ALPHABET.length())));
            number /= ALPHABET.length();
        }

        return sb.reverse().toString(); // Get Base62 encoded string
    }

    // Main method to demonstrate functionality
    public static void main(String[] args) {
        // Test URLs
        String[] urls = {
            "https://www.example.com/some/long/url1",
            "https://www.example.com/some/long/url2",
            "https://openai.com/research/url3",
            "https://github.com/openai/url4",
            "https://stackoverflow.com/questions/url5",
            "https://linkedin.com/in/user/url6",
            "https://twitter.com/usecase/url7",
            "https://facebook.com/profile/url8"
        };

        // Shorten multiple URLs
        System.out.println("=== Shortening URLs ===");
        for (String longUrl : urls) {
            String shortUrl = shortenUrl(longUrl);
            System.out.printf("  %s -> %s%n", longUrl, shortUrl);
        }

        // Show cache content after shortening
        System.out.println("\n=== Cache Contents after Shortening (Most Recent Last) ===");
        cache.keySet().forEach(key -> System.out.println("Short Key: " + key));

        // Retrieve some URLs to demonstrate cache updates
        System.out.println("\n=== Retrieving URLs to Test LRU Cache ===");
        System.out.println("  Retrieving URL for https://short.ly/1 -> " + getLongUrl("https://short.ly/1")); // Cache hit
        System.out.println("  Retrieving URL for https://short.ly/3 -> " + getLongUrl("https://short.ly/3")); // Cache hit
        System.out.println("  Retrieving URL for https://short.ly/5 -> " + getLongUrl("https://short.ly/5")); // Cache hit
        System.out.println("  Retrieving URL for https://short.ly/7 -> " + getLongUrl("https://short.ly/7")); // Cache miss

        // Show cache content again after retrievals
        System.out.println("\n=== Cache Contents after Retrievals (Most Recent Last) ===");
        cache.keySet().forEach(key -> System.out.println("Short Key: " + key));

        // Handle invalid short URLs
        System.out.println("\n=== Testing Invalid URL ===");
        System.out.println("  InvalidKey -> " + getLongUrl("https://short.ly/invalid"));
    }
}
