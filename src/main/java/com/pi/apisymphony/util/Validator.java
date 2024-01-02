package com.pi.apisymphony.util;

import java.util.regex.Pattern;
public class Validator {
    private static final String EMAIL_VERIFICATION_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_VALIDATION_REGEX = "^1-\\d{3}-\\d{3}-\\d{4}$";
    private static boolean patternMatches(String text, String pattern){
        return Pattern.compile(pattern)
                .matcher(text)
                .matches();
    }
    public static boolean validateEmail(String email){
        return patternMatches(email, EMAIL_VERIFICATION_REGEX);
    }
    public static boolean validatePhone(String phone){
        return patternMatches(phone,PHONE_VALIDATION_REGEX);
    }
}
