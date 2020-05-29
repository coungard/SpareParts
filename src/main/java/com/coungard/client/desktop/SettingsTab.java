package com.coungard.client.desktop;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class SettingsTab extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(SettingsTab.class.getName());

    public SettingsTab() {
        setLayout(null);
        setOpaque(false);

        setBounds(0, 0, 1000, 480);
        setBackground(Color.BLUE);
    }
}
