package Utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {
    @Test
    public void validateTime() throws Exception {
        assertTrue(Validator.validateTime("12:34"));
        assertTrue(Validator.validateTime("00:00"));
        assertFalse(Validator.validateTime(""));
        assertFalse(Validator.validateTime("asdczxczxc"));
        assertFalse(Validator.validateTime("aa:34"));
        assertFalse(Validator.validateTime("43:34"));
        assertFalse(Validator.validateTime("24:60"));
        assertFalse(Validator.validateTime("12:74"));
    }

    @Test
    public void validateString() throws Exception {
        assertFalse(Validator.validateString(""));
        assertTrue(Validator.validateString("asdczxczxc"));
        assertTrue(Validator.validateString("AbrakadabraA"));

        assertFalse(Validator.validateString("asdasd123asdas"));
        assertFalse(Validator.validateString("asdasdsdas111"));
        assertFalse(Validator.validateString("asdasd!@#asdas"));
        assertFalse(Validator.validateString("123asdasd123asdas123"));
        assertFalse(Validator.validateString("1111asdasdsdas"));
    }

    @Test
    public void validateNumber() {
        assertTrue(Validator.validateNumber("3.12"));
        assertTrue(Validator.validateNumber("33.12"));

        assertFalse(Validator.validateNumber("asdasd123asdas"));
        assertFalse(Validator.validateNumber("asdasdsdas111"));
        assertFalse(Validator.validateNumber("3.12jhgjh"));
        assertFalse(Validator.validateNumber(""));

    }

}