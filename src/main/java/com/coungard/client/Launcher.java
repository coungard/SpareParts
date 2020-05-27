package com.coungard.client;

import com.coungard.client.desktop.Manager;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.lang.reflect.InvocationTargetException;

public class Launcher extends JTabbedPane {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            SwingUtilities.invokeAndWait(Manager::new);
        } catch (UnsupportedLookAndFeelException | InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
