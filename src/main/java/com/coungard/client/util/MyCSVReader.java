package com.coungard.client.util;

import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class MyCSVReader extends CSVReader {
    private static final Logger LOGGER = Logger.getLogger(MyCSVReader.class.getName());
    private static final char QUOTE = '"';
    private static int lineNumber;
    private String badSequence = "\"\"\"\"";

    public MyCSVReader(Reader reader, char separator) {
        super(reader, separator);
    }

    public Map<Integer, String[]> getRows() {
        lineNumber = 0;
        Map<Integer, String[]> rows = new LinkedHashMap<>();
        try {
            String[] line;
            while ((line = readNext()) != null) {
                String[] withDelimeter = splitWithDelimeter(line, ",");
                rows.put(lineNumber, withDelimeter);
                lineNumber++;
            }
            LOGGER.debug("Total rows = " + lineNumber);
            return rows;
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;
    }

    private String[] splitWithDelimeter(String[] line, String delimeter) {
        List<String> list = new ArrayList<>();
        for (String s : line) {
            String[] parts = s.split(delimeter);
            list.addAll(Arrays.asList(parts));
        }
        String[] result = new String[list.size()];
        return list.toArray(result);
    }

    /**
     * Переопределенный метод класса com.opencsv.CSVReader, возвращающий строку из таблицы csv. В данном случае
     * проверяется четность ковычек в рамках одной ячейки для валидной загрузки данных.
     *
     * @return строка из csv файла
     */
    @Override
    protected String getNextLine() throws IOException {
        String line = super.getNextLine();
        if (line != null && line.contains(badSequence)) {
            debugLine(line);
        }
        return line;
    }

    private void debugLine(String line) {
        String[] parts = line.split(String.valueOf(this.getParser().getSeparator()));
        for (String s : parts) {
            int quotesCount = 0;
            if (s.contains(badSequence)) {
                char[] array = s.toCharArray();
                for (int i = 0; i < s.length(); i++) {
                    if (array[i] == QUOTE) quotesCount++;
                }
                if (quotesCount % 2 != 0) {
                    LOGGER.warn("Quotes on line " + lineNumber + " is broken! Deleting 1 quote!");
                    line = line.replaceAll(badSequence, "\"\"\"");
                }
            }
        }
    }
}
