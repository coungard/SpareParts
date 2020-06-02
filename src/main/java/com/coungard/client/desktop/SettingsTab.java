package com.coungard.client.desktop;

import com.coungard.client.Settings;
import com.coungard.client.util.Utils;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class SettingsTab extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(SettingsTab.class.getName());
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);

    EmailPanel emailPanel;

    public SettingsTab() {
        setLayout(null);
        setOpaque(false);

        emailPanel = new EmailPanel();
        add(emailPanel);

        setBounds(0, 0, 1000, 480);
    }

    public class EmailPanel extends JPanel {
        private JTextField serverField;
        private JTextField portField;
        private JTextField emailField;
        private JTextField passField;

        EmailPanel() {
            setLayout(null);
            setOpaque(false);
            setBounds(20, 20, 500, 300);
            TitledBorder border = BorderFactory.createTitledBorder("Конфигурация почты");
            border.setTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            setBorder(border);

            add(createLabel("IMAP Сервер:", 15, 40));
            add(createLabel("IMAP Порт:", 15, 80));
            add(createLabel("Почта:", 15, 120));
            add(createLabel("Пароль:", 15, 160));

            serverField = createField(180, 40);
            add(serverField);
            portField = createField(180, 80);
            add(portField);
            emailField = createField(180, 120);
            add(emailField);
            passField = createField(180, 160);
            add(passField);

            addSaveButton();
            loadEmailProperties();
        }

        private JTextField createField(int x, int y) {
            JTextField field = new JTextField();
            field.setLocation(x, y);
            field.setSize(300, 35);
            field.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
            field.setHorizontalAlignment(SwingConstants.CENTER);
            return field;
        }

        private JLabel createLabel(String name, int x, int y) {
            JLabel label = new JLabel(name);
            label.setSize(150, 35);
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            label.setLocation(x, y);
            return label;
        }

        private void loadEmailProperties() {
            serverField.setText(Settings.IMAP_Server);
            portField.setText(Settings.IMAP_Server);
            emailField.setText(Settings.IMAP_AUTH_EMAIL);
            passField.setText(Settings.IMAP_AUTH_PASSWORD);
        }

        private void addSaveButton() {
            JButton but = Utils.createButton("Сохранить", 180, 40, font);
            but.setLocation(300, 255);
            but.setForeground(Color.WHITE);
            but.setBackground(new Color(22, 13, 56));
            but.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LOGGER.info("save parameters button");
                    saveEmailProperties();
                    JOptionPane.showMessageDialog(null, "Конфигурация почты сохранена",
                            null, JOptionPane.INFORMATION_MESSAGE);
                }
            });
            add(but);
        }

        /**
         * Сохраняет настройки из полей в Settings и в проперти файл
         */
        private void saveEmailProperties() {
            Settings.IMAP_Server = serverField.getText();
            Settings.IMAP_Port = portField.getText();
            Settings.IMAP_AUTH_EMAIL = emailField.getText();
            Settings.IMAP_AUTH_PASSWORD = passField.getText();

            Map<String, String> map = new LinkedHashMap<>();
            map.put("server", serverField.getText());
            map.put("port", portField.getText());
            map.put("email", emailField.getText());
            map.put("pass", passField.getText());

            Utils.saveProp(map, Settings.EMAIL_PROPERTIES_PATH);
        }
    }
}
