package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class UtilsService {
    public static String getFullName(String surname, String firstName, String patronymic) {
        surname = surname == null ? "-" : surname;
        firstName = firstName == null ? "-" : firstName;
        patronymic = patronymic == null ? "-" : patronymic;

        return String.format("%s %s %s", surname, firstName, patronymic);
    }
}
