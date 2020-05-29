package com.coungard.client.email;

import org.junit.Test;

import javax.mail.Message;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EmailTest {

    @Test
    public void testConnection() {
        ReadEmail email = new ReadEmail();
        Message message = email.getMessage();

        assert (message != null);
    }

    @Test
    public void testDownloadFile() {
        ReadEmail email = new ReadEmail();
        String path = email.saveAttachment(email.getMessage());

        assert (path != null);
        assert (Files.exists(Paths.get(path)));
    }
}
