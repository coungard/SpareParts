package com.coungard.client.util;

import javax.swing.*;
import java.awt.*;

public class Utils {

    public static JButton createButton(String name, int x, int y, Font font) {
        JButton button = new JButton(name);
        button.setSize(x, y);
        button.setFont(font);

        return button;
    }
}
