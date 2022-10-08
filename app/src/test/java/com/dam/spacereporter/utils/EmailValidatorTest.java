package com.dam.spacereporter.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailValidatorTest {

    @Test
    public void isValidEmail_NullPointer() {
        assertThrows(NullPointerException.class, () -> EmailValidator.isValidEmail(null));
    }

    @Test
    public void isValidEmail_isCorrect() {
        assertTrue(EmailValidator.isValidEmail("ejemplo@macejemplo.com"));
    }

    @Test
    public void isValidEmail_isWrong() {
        assertFalse(EmailValidator.isValidEmail("Potato"));
    }
}
