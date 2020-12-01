package com.coungard.client;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    public static final String EMAIL_DIRECTORY_PATH = "email/";
    public static final String EMAIL_PROPERTIES_PATH = "email/email.properties";
    public static final String EMAIL_ATTACHMENTS_PATH = "email/attachments/";

    public static final String dbDir = "./db";

    public static final String VENDOR_COLUMN_NAME = "Бренд";
    public static final String NUMBER_COLUMN_NAME = "Каталожный номер";
    public static final String DESCRIPTION_COLUMN_NAME = "Описание";
    public static final String PRICE_COLUMN_NAME = "Цена";
    public static final String COUNT_COLUMN_NAME = "Наличие";

    // Дефолтные настройки почты
    public static String IMAP_Server = "imap.mail.ru";
    public static String IMAP_Port = "993";
    public static String IMAP_AUTH_EMAIL = "example@mail.ru";
    public static String IMAP_AUTH_PASSWORD = "qwerty";

    public static Map<String, String> getEmailConfig() {
        Map<String, String> map = new HashMap<>();
        map.put("server", IMAP_Server);
        map.put("port", IMAP_Port);
        map.put("email", IMAP_AUTH_EMAIL);
        map.put("pass", IMAP_AUTH_PASSWORD);

        return map;
    }
}
