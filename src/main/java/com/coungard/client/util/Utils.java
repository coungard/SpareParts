package com.coungard.client.util;

import com.coungard.client.Settings;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Utils {
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    public static JButton createButton(String name, int x, int y, Font font) {
        JButton button = new JButton(name);
        button.setSize(x, y);
        button.setFont(font);

        return button;
    }

    public static String calcElapsedTime(long started) {
        long millis = System.currentTimeMillis() - started;
        return String.format("%d min, %d sec, %d ms",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
                TimeUnit.MILLISECONDS.toMillis(millis) -
                        TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis))
        );
    }

    public static void createDirectories() {
        try {
            Path smtp = Paths.get(Settings.EMAIL_DIRECTORY_PATH);
            Path attachments = Paths.get(Settings.EMAIL_ATTACHMENTS_PATH);
            if (!Files.exists(smtp)) Files.createDirectory(smtp);
            if (!Files.exists(attachments)) Files.createDirectory(attachments);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    /**
     * Принимает на вход текст и удаляет из него все нецифернобуквенные символы<br>
     *
     * <b>Notice</b><br>Ascii numbers from 0 to 9 : 48 - 57
     * <br>Ascii words UPPERCASE from A to Z : 65 - 90
     *
     * @param text текст с любыми символами
     * @return только циферкно-буквенный текст
     */
    public static String getClearedAsciiString(String text) {
        String result = text.toUpperCase();
        char[] symbols = result.toCharArray();

        for (int i = 0; i < symbols.length; i++) {
            int c = symbols[i];
            if ((c < 48 || c > 57) && (c < 65 || c > 90)) {
                symbols[i] = '!';
            }
        }
        String temp = String.valueOf(symbols);
        result = temp.replaceAll("!", "");
        return result;
    }

    /**
     * Сохранить настройки формата ключ-значение в файл как property
     */
    public static void saveProp(Map<String, String> prms, String file) {
        try {
            Properties prop = new Properties();
            for (Map.Entry<String, String> e : prms.entrySet()) {
                prop.setProperty(e.getKey(), e.getValue());
            }
            OutputStream os = new FileOutputStream(file);
            prop.store(os, null);
            os.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void loadProp(String file) throws IOException {
        if (!Files.exists(Paths.get(Settings.EMAIL_PROPERTIES_PATH))) {
            LOGGER.info("Email properties doesn't exist. Create default property file");
            Files.createFile(Paths.get(Settings.EMAIL_PROPERTIES_PATH));

            Map<String, String> values = new LinkedHashMap<>();
            values.put("server", Settings.IMAP_Server);
            values.put("port", Settings.IMAP_Port);
            values.put("email", Settings.IMAP_AUTH_EMAIL);
            values.put("pass", Settings.IMAP_AUTH_PASSWORD);

            Utils.saveProp(values, Settings.EMAIL_PROPERTIES_PATH);
        } else {
            InputStream is = new FileInputStream(file);
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            Properties properties = new Properties();
            properties.load(reader);

            Settings.IMAP_Server = properties.getProperty("server");
            Settings.IMAP_Port = properties.getProperty("port");
            Settings.IMAP_AUTH_EMAIL = properties.getProperty("email");
            Settings.IMAP_AUTH_PASSWORD = properties.getProperty("pass");

            is.close();
        }
    }
}
