package org.example.startupprjoect.Util;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator {

    public static boolean isPasswordStrong(String password) {
        return password != null && password.matches(
                "^(?=.*[0-9])" +
                        "(?=.*[a-z])" +
                        "(?=.*[A-Z])" +
                        "(?=.*[@#$%^&+=])" +
                        "(?=\\S+$)" +
                        ".{8,}$"
        );
    }


    public static List<String> getPasswordStrengthErrors(String password) {
        List<String> errors = new ArrayList<>();

        if (password == null || password.length() < 8) {
            errors.add("Password must be at least 8 characters long");
        }

        if (!password.matches(".*[0-9].*")) {
            errors.add("Password must contain at least one digit");
        }

        if (!password.matches(".*[a-z].*")) {
            errors.add("Password must contain at least one lowercase letter");
        }

        if (!password.matches(".*[A-Z].*")) {
            errors.add("Password must contain at least one uppercase letter");
        }

        if (!password.matches(".*[@#$%^&+=].*")) {
            errors.add("Password must contain at least one special character");
        }

        return errors;
    }
}