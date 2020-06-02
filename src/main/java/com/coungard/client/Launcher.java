package com.coungard.client;

import com.coungard.client.desktop.Manager;
import com.coungard.client.util.Utils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Launcher extends JTabbedPane {
    private static final Logger LOGGER = Logger.getLogger(Launcher.class.getName());

    public static void main(String[] args) {
        try {
            DOMConfigurator.configure("log4j.xml");
            LOGGER.info("Start application");

            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            Utils.createDirectories();
            Utils.loadProp(Settings.EMAIL_PROPERTIES_PATH);
            Map<String, String> config = Settings.getEmailConfig();
            LOGGER.info("Email configuration: " + config.entrySet()
                    .stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining("\n")));
            DBManager.createDB();
            SwingUtilities.invokeAndWait(Manager::new);
        } catch (UnsupportedLookAndFeelException | InterruptedException | InvocationTargetException | IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
