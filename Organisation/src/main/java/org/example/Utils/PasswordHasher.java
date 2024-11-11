package org.example.Utils;

public class PasswordHasher {

    public static String hashPassword(String plainPassword) {
        int hashed = plainPassword.hashCode();
        return Integer.toString(hashed);  // Store as a string
    }
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return Integer.toString(plainPassword.hashCode()).equals(hashedPassword);
    }
}
