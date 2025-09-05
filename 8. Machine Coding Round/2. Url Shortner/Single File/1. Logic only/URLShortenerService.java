import java.util.HashMap;
import java.util.Map;

public class URLShortenerService {
    private static final String BASE_URL = "https://short.ly/";
    private static long counter = 1; // Auto-incremental ID
    private static Map<String, String> urlMap = new HashMap<>();
    private static Map<String, String> reverseMap = new HashMap<>();
    
    // Method to shorten a long URL
    public static String shortenUrl(String longUrl) {
        if (reverseMap.containsKey(longUrl)) {
            return reverseMap.get(longUrl); // Return existing short URL if exists
        }
        String shortKey = encodeBase62(counter); // Generate a short key
        counter++; // Increment counter for the next URL
        urlMap.put(shortKey, longUrl); // Store the mapping
        reverseMap.put(longUrl, shortKey); // Store the reverse mapping
        return BASE_URL + shortKey; // Return the complete short URL
    }

    // Method to retrieve the original long URL from a short URL
    public static String getLongUrl(String shortUrl) {
        String shortKey = shortUrl.replace(BASE_URL, ""); // Extract the short key
        return urlMap.getOrDefault(shortKey, "URL not found"); // Return long URL or error message
    }

    // Method for Base62 encoding
    private static String encodeBase62(long number) {
        final String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        
        while (number > 0) {
            sb.append(characters.charAt((int) (number % characters.length())));
            number /= characters.length();
        }
        return sb.reverse().toString(); // Get the Base62 encoded string
    }

    // Main method for testing the service
    public static void main(String[] args) {
        String longUrl = "https://www.example.com/some/long/url";
        String shortUrl = shortenUrl(longUrl);
        System.out.println("Shortened URL: " + shortUrl);
        
        String retrievedLongUrl = getLongUrl(shortUrl);
        System.out.println("Retrieved Long URL: " + retrievedLongUrl);
        
        // Example of retrieving an URL not found
        String notFoundUrl = getLongUrl("https://short.ly/invalidKey");
        System.out.println("Not Found URL: " + notFoundUrl);
    }
}
