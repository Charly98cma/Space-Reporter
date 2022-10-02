package com.dam.spacereporter.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class PwdManager {

    private static final String hash = "SHA-256";

    // Password requirements
    private static Pattern uppercase = Pattern.compile("[A-Z]");
    private static Pattern lowercase = Pattern.compile("[a-z]");
    private static Pattern digit = Pattern.compile("[0-9]");

    public static String getSHA(String input) throws NoSuchAlgorithmException {

        // Static instance of the hashing algorithm
        MessageDigest md = MessageDigest.getInstance(hash);

        StringBuilder hexString = new StringBuilder(new BigInteger(
                1, md.digest(input.getBytes(StandardCharsets.UTF_8))
        ).toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
            hexString.insert(0, '0');

        return hexString.toString();
    }

    public static boolean validatePassword(String pwd) {
       return uppercase.matcher(pwd).find() && lowercase.matcher(pwd).find() &&
               digit.matcher(pwd).find() && pwd.length() >= 8;
    }
}
