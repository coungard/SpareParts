package client;

import com.coungard.client.DBManager;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DBManagerTest {
    String dbPath = "./db";

    @Test
    public void connection() {
        DBManager.createDB();
        boolean created = Files.exists(Paths.get(dbPath));

        assert created;
    }

    @Test
    public void prms() {
        DBManager.updatePrm("my name", null);
        assertNull(DBManager.getPrm(null));
        DBManager.updatePrm("my name", "Artur");
        assertEquals("Artur", DBManager.getPrm("my name"));
    }
}
