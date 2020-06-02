package com.coungard.client.util;

import com.coungard.client.Settings;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public static void createDirectories() {
        try {
            Path smtp = Paths.get(Settings.SMTP_PATH);
            Path attachments = Paths.get(Settings.ATTACHMENTS_PATH);
            if (!Files.exists(smtp)) Files.createDirectory(smtp);
            if (!Files.exists(attachments)) Files.createDirectory(attachments);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
