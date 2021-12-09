package com.example.hotel.validator;

import java.util.regex.Pattern;

public class LoginValidator implements Validator {

    private static final Pattern LOGIN_REGEX_PATTERN = Pattern.compile("^[(\\w)-]{4,20}");

    @Override
    public boolean isValidated(String login) {
        return login != null && !login.isEmpty() &&
                LOGIN_REGEX_PATTERN.matcher(login).matches();
    }
}
