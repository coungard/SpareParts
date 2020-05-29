package com.coungard.client.desktop;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JTabbedPane {

    public MainPanel() {
        EmailTab emailTab = new EmailTab();
        PriceListTab priceListTab = new PriceListTab();
        SettingsTab settingsTab = new SettingsTab();
        addTab("Почта", emailTab);
        addTab("Прайс лист", priceListTab);
        addTab("Настройки", settingsTab);

        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 23));
        setOpaque(false);
    }
}
