package com.restaurant.backend.constants;

import com.restaurant.backend.domain.Manager;

public final class PasswordUserServiceTestConstants {
    public static final String USER_EMAIL = "petar.petrovic@petrovic.domain.com";
    public static final String USER_FIRST_NAME = "Petar";
    public static final String USER_LAST_NAME = "Petrovic";
    public static final Long USER_ID = 1L;
    public static final String USED_USERNAME = "Petar1";
    public static final String USERNAME = "Petar2";
    public static final String OTHER_USERNAME = "Petar3";
    public static final String PASSWORD = "Password123";
    public static final String USER_PHONE_NUMBER = "555-333";

    public static Manager generateManager() {
        Manager manager = new Manager();
        manager.setFirstName(USER_FIRST_NAME);
        manager.setLastName(USER_LAST_NAME);
        manager.setUsername(USERNAME);
        manager.setPassword(PASSWORD);
        manager.setPhoneNumber(USER_PHONE_NUMBER);
        manager.setEmail(USER_EMAIL);
        return manager;
    }
}
