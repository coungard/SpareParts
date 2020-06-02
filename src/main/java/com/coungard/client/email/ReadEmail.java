package com.coungard.client.email;

import com.coungard.client.Settings;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ReadEmail {
    private static final Logger LOGGER = Logger.getLogger(ReadEmail.class.getName());
    private final Properties properties;

    private Message message;
    private Store store;

    public ReadEmail() {
        properties = new Properties();
        properties.put("mail.debug", "false");
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", Settings.IMAP_Port);
        properties.put("mail.imaps.partialfetch", "false");
    }

    public boolean connection() {
        Authenticator auth = new EmailAuthenticator(Settings.IMAP_AUTH_EMAIL, Settings.IMAP_AUTH_PASSWORD);
        Session session = Session.getDefaultInstance(properties, auth);
        session.setDebug(false);
        try {
            // Подключение к почтовому серверу
            store = session.getStore();
            store.connect(Settings.IMAP_Server, Settings.IMAP_AUTH_EMAIL, Settings.IMAP_AUTH_PASSWORD);
        } catch (MessagingException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    public void checkMessage() {
        // Папка входящих сообщений
        Folder inbox;
        try {
            inbox = store.getFolder("INBOX");
            // Открываем папку в режиме только для чтения
            inbox.open(Folder.READ_ONLY);

            System.out.println("Количество сообщений : " + inbox.getMessageCount());
            if (inbox.getMessageCount() == 0)
                return;
            // Последнее сообщение; первое сообщение под номером 1
            message = inbox.getMessage(inbox.getMessageCount());
        } catch (MessagingException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public String saveAttachment(Message message) {
        try {
            if (message.getContent() instanceof Multipart) {
                Multipart multipart = (Multipart) message.getContent();

                for (int i = 0; i < multipart.getCount(); i++) {
                    Part part = multipart.getBodyPart(i);
                    String disposition = part.getDisposition();

                    if ((disposition != null) && ((disposition.equalsIgnoreCase(Part.ATTACHMENT) ||
                            (disposition.equalsIgnoreCase(Part.INLINE))))) {
                        MimeBodyPart mimeBodyPart = (MimeBodyPart) part;
                        String fileName = mimeBodyPart.getFileName();

                        File fileToSave = new File(Settings.EMAIL_ATTACHMENTS_PATH + fileName);
                        mimeBodyPart.saveFile(fileToSave);
                        LOGGER.info("Saved file : " + fileToSave.getPath());
                        return fileToSave.getPath();
                    }
                }
            }
        } catch (IOException | MessagingException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;
    }

    public Message getMessage() {
        return message;
    }
}