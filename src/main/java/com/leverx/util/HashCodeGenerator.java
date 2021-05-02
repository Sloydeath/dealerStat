package com.leverx.util;

import java.util.Random;

/**
 * This is a util class that create random code for email activation and reset password.
 * Code is added into redis.
 *
 * @author Andrew Panas
 */

public class HashCodeGenerator {
    private static final String CHARACTERS = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOASDFGHJKLZXCVBNM";
    private static final int CODE_LENGTH = 8;

    public static String generateHashCode() {
        Random rnd = new Random();
        char[] text = new char[CODE_LENGTH];
        for (int i = 0; i < CODE_LENGTH; i++)
        {
            text[i] = CHARACTERS.charAt(rnd.nextInt(CHARACTERS.length()));
        }
        return new String(text);
    }
}
