package com.coungard.client.util;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Utils {

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
}
