package client;

import com.coungard.client.email.ReadEmail;
import org.junit.Test;

import javax.mail.Message;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EmailTest {

    @Test
    public void testConnection() {
        ReadEmail email = new ReadEmail();
        boolean connection = email.connection();

        assert connection;
    }

    @Test
    public void testMessage() {
        ReadEmail email = new ReadEmail();
        email.connection();
        email.checkMessage();
        Message message = email.getMessage();

        assert message != null;
    }

    @Test
    public void testDownloadFile() {
        ReadEmail email = new ReadEmail();
        email.connection();
        email.checkMessage();
        String path = email.saveAttachment(email.getMessage());

        assert path != null;
        assert Files.exists(Paths.get(path));
    }
}
