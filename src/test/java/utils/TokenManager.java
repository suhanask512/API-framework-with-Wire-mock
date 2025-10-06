package utils;

/**
 * TokenManager:
 * Stores token in memory during test execution.
 */
public class TokenManager {
    private static String token;

    public static void setToken(String t) {
        token = t;
    }

    public static String getToken() {
        return token;
    }
}
