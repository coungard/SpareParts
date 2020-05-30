package com.coungard.client.desktop;

import com.coungard.client.util.Utils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class Manager extends JTabbedPane {
    private static final Logger LOGGER = Logger.getLogger(Manager.class.getName());
    private final JFrame frame;
    private int width = 1000;
    private int height = 700;
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);

    public Manager() {
        LOGGER.info("Spare Parts desktop starting...");
        setLayout(null);
        setOpaque(false);

        frame = new JFrame();
        frame.setTitle("Дешевые Запчасти");
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        MainPanel mainPanel = new MainPanel();
        mainPanel.setBounds(0, 0, frame.getSize().width, frame.getSize().height - 85);
        frame.getContentPane().add(mainPanel);

        addExitButton();
        setTheme();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void setTheme() {
        JLabel themeLabel = new JLabel();
        themeLabel.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("theme.jpg"))));
        themeLabel.setBounds(0, 0, width, height);
        frame.getContentPane().add(themeLabel, JLayeredPane.POPUP_LAYER);
    }

    private void addExitButton() {
        JButton but = Utils.createButton("Выход", 200, 50, font);
        but.setLocation(frame.getSize().width - 215, frame.getSize().height - 85);
        but.setForeground(Color.WHITE);
        but.setBackground(Color.BLACK);
        but.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOGGER.info("exit");
                System.exit(0);
            }
        });

        frame.getContentPane().add(but);
    }
}
