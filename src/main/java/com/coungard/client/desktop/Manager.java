package com.coungard.client.desktop;

import com.coungard.client.email.ReadEmail;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

public class Manager extends JTabbedPane {
    private static final Logger LOGGER = Logger.getLogger(Manager.class.getName());
    private final JFrame frame;
    private final JPanel mailPanel;

    public Manager() {
        LOGGER.info("Spare Parts desktop starting...");
        frame = new JFrame();
        frame.setTitle("Spare Parts");
        frame.setSize(1000, 680);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        mailPanel = new JPanel();
        mailPanel.setLayout(null);
        mailPanel.setBounds(0, 0, frame.getSize().width, frame.getSize().height - 85);
        frame.getContentPane().add(mailPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        content();
    }

    private void content() {
        JButton saveCSV = new JButton("Сохранить CSV файл");
        saveCSV.setBounds(10, 20, 240, 50);
        mailPanel.add(saveCSV);
        saveCSV.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOGGER.info("save CSV button pressed");
                long starter = System.currentTimeMillis();
                new ReadEmail();
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
