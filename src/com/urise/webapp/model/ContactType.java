package com.urise.webapp.model;

public enum ContactType {
    PHONENUMBER("Номер телефона"),
    SKYPENICKNAME("Никнейм скайпа"),
    EMAIL("Адрес электронной почты");
    private String title;

    public String getTitle() {
        return title;
    }

    ContactType(String title) {
        this.title = title;
    }
}
