package org.aleksa.security.util;

public class HeaderUtil {
    public static boolean isValid(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        }
        else return header.split(" ").length == 2;
    }

    public static String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // "Bearer " is 7 characters
        }
        return null;
    }
}
