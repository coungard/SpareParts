package com.coungard.client.desktop;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JTabbedPane {

    public MainPanel() {
        setOpaque(false);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 23));

        EmailTab emailTab = new EmailTab();
        PriceListTab priceListTab = new PriceListTab();
        SettingsTab settingsTab = new SettingsTab();
        addTab("Почтовый сервис", emailTab);
        addTab("Прайс лист", priceListTab);
        addTab("Настройки", settingsTab);
    }
}
