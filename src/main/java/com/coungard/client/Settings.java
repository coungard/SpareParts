package com.coungard.client;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    public static final String IMAP_Server = "imap.mail.ru";
    public static final String IMAP_Port = "993";
    public static final String IMAP_AUTH_EMAIL = "sfetodagi@mail.ru";
    public static final String IMAP_AUTH_PWD = "art112358";

    public static final String dbDir = "./db";

    public static final String VENDOR_COLUMN_NAME = "Бренд";
    public static final String NUMBER_COLUMN_NAME = "Каталожный номер";
    public static final String DESCRIPTION_COLUMN_NAME = "Описание";
    public static final String PRICE_COLUMN_NAME = "Цена";
    public static final String COUNT_COLUMN_NAME = "Наличие";

    public static Map<String, String> getEmailConfig() {
        Map<String, String> map = new HashMap<>();
        map.put("server", IMAP_Server);
        map.put("port", IMAP_Port);
        map.put("email", IMAP_AUTH_EMAIL);
        map.put("pass", IMAP_AUTH_PWD);

        return map;
    }
}
