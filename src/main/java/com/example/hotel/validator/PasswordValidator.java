package com.example.hotel.validator;

import java.util.regex.Pattern;

public class PasswordValidator implements Validator {

    private static final Pattern PASSWORD_REGEX_PATTERN =
            Pattern.compile("^(?=.*\\d)(?=.*\\p{Lower})(?=.*\\p{Upper})(?=.*\\p{Punct})(?=\\S+$).{8,20}$");

    @Override
    public boolean isValidated(String password) {
        return password != null && !password.isEmpty() &&
                PASSWORD_REGEX_PATTERN.matcher(password).matches();
    }
}
