package com.coungard.client.desktop;

import com.coungard.client.email.ReadEmail;
import com.coungard.client.util.Utils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

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
                long starter = System.currentTimeMillis();
                ReadEmail readEmail = new ReadEmail();
                readEmail.saveAttachment(readEmail.getMessage());
                long millis = System.currentTimeMillis() - starter;
                String elapsed = String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millis),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                );
                LOGGER.debug("Elapsed time: " + elapsed);
            }
        });
    }
}
