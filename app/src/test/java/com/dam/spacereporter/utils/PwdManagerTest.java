package com.dam.spacereporter.utils;

import org.junit.Assert;
import org.junit.Test;

public class PwdManagerTest {

    @Test
    public void validatePassword_NullPointerFirstArg() {
        Assert.assertThrows(NullPointerException.class, () -> PwdManager.validatePassword(null, ""));
    }

    @Test
    public void validatePassword_NullPointerSecondArg() {
        Assert.assertThrows(NullPointerException.class, () -> PwdManager.validatePassword("Potato22", null));
    }

    @Test
    public void validatePassword_oneWrong() {
        Assert.assertFalse(PwdManager.validatePassword("Potato22", "null"));
    }

    @Test
    public void validatePassword_isCorrect() {
        Assert.assertTrue(PwdManager.validatePassword("Potato22", "Potato22"));
    }


}
