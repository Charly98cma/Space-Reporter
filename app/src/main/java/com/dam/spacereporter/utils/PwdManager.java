package com.dam.spacereporter.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PwdManager {

    private static final String hash = "SHA-256";

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
}
