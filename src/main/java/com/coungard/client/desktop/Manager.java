package com.coungard.client.desktop;

import com.coungard.client.email.ReadEmail;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Manager extends JTabbedPane {
    private final JFrame frame;
    private final JPanel mailPanel;

    public Manager() {
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
                System.out.println("save CSV pressed");
                new ReadEmail();
            }
        });
    }
}
