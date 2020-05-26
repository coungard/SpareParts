package com.coungard.client.email;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class ReadEmail {
    String IMAP_Server = "imap.mail.ru";
    String IMAP_Port = "993";
    String IMAP_AUTH_EMAIL = "sfetodagi@mail.ru";
    String IMAP_AUTH_PWD = "art112358";

    public ReadEmail() {
        Properties properties = new Properties();
        properties.put("mail.debug", "false");
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", IMAP_Port);

        Authenticator auth = new EmailAuthenticator(IMAP_AUTH_EMAIL,
                IMAP_AUTH_PWD);
        Session session = Session.getDefaultInstance(properties, auth);
        session.setDebug(false);
        try {
            Store store = session.getStore();
            // Подключение к почтовому серверу
            store.connect(IMAP_Server, IMAP_AUTH_EMAIL, IMAP_AUTH_PWD);
            // Папка входящих сообщений
            Folder inbox = store.getFolder("INBOX");
            // Открываем папку в режиме только для чтения
            inbox.open(Folder.READ_ONLY);

            System.out.println("Количество сообщений : " + inbox.getMessageCount());
            if (inbox.getMessageCount() == 0)
                return;
            // Последнее сообщение; первое сообщение под номером 1
            Message message = inbox.getMessage(inbox.getMessageCount());
            Multipart mp = (Multipart) message.getContent();
            // Вывод содержимого в консоль
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bp = mp.getBodyPart(i);
                if (bp.getFileName() == null)
                    System.out.println("    " + i + ". сообщение : '" + bp.getContent() + "'");
                else
                    System.out.println("    " + i + ". файл : '" + bp.getFileName() + "'");
            }
        } catch (MessagingException | IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ReadEmail();
        System.exit(0);
    }
}