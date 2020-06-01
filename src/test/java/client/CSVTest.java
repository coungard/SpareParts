package client;

import com.coungard.client.util.MyCSVReader;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class CSVTest {

    @Test
    public void testRows() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("csv/employee.csv").getFile());
        FileReader fileReader = new FileReader(file);

        MyCSVReader csvReader = new MyCSVReader(fileReader, ';');
        Map<Integer, String[]> rows = csvReader.getRows();

        assert rows != null;
        assert rows.size() == 4;
    }
}
