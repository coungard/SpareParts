package com.coungard.client.desktop;

import com.coungard.client.email.ReadEmail;
import com.coungard.client.util.Utils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EmailTab extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(EmailTab.class.getName());
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);

    public EmailTab() {
        setLayout(null);
        setOpaque(false);
        setBounds(0, 0, 800, 480);
        content();
    }

    private void content() {
        JButton saveCSV = Utils.createButton("Сохранить CSV файл", 240, 50, font);
        saveCSV.setLocation(10, 20);
        add(saveCSV);
        saveCSV.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOGGER.info("save CSV button pressed");
                downloadFile();
            }
        });

        JButton loadCSV = Utils.createButton("Загрузить CSV файл", 240, 50, font);
        loadCSV.setLocation(10, 100);
        add(loadCSV);
        loadCSV.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOGGER.info("load CSV button pressed");
                loadCSV();
            }
        });
    }

    private void loadCSV() {
        // todo...
    }

    private void downloadFile() {
        long started = System.currentTimeMillis();
        ReadEmail email = new ReadEmail();
        if (!email.connection()) {
            JOptionPane.showMessageDialog(null, "Connection error!", "", JOptionPane.ERROR_MESSAGE);
            return;
        };
        email.checkMessage();
        if (email.getMessage() == null) {
            JOptionPane.showMessageDialog(null, "Error trying to find message!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        email.saveAttachment(email.getMessage());
        String elapsed = Utils.calcElapsedTime(started);
        LOGGER.debug("Elapsed time: " + elapsed);
    }
}
