package edu.softserve.zoo.service.security;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Secure password generator based on {@link SecureRandom}.
 *
 * @author Ilya Doroshenko
 */
public class PasswordGenerator {

    private static final int RADIX = 32;

    /**
     * Use to generate passwords of given length.
     *
     * @param length of the password in bits
     * @return generated password
     */
    public static String generate(int length) {
        return new BigInteger(length, new SecureRandom()).toString(RADIX);
    }
}
