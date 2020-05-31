package com.coungard.client.util;

import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyCSVReader extends CSVReader {
    private static final Logger LOGGER = Logger.getLogger(MyCSVReader.class.getName());
    private static final char QUOTE = '"';
    private static int lineNumber;

    public MyCSVReader(Reader reader) {
        super(reader);
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
        String sequence = "\"\"\"\"";
        if (line != null && line.contains(sequence)) {
            String[] parts = line.split(";");
            for (String s : parts) {
                int quotesCount = 0;
                if (s.contains(sequence)) {
                    char[] array = s.toCharArray();
                    for (int i = 0; i < s.length(); i++) {
                        if (array[i] == QUOTE) quotesCount++;
                    }
                    if (quotesCount % 2 != 0) {
                        LOGGER.warn("Quotes on line " + lineNumber + " is broken! Deleting 1 quote!");
                        line = line.replaceAll(sequence, "\"\"\"");
                    }
                }
            }
        }
        return line;
    }

    public static Map<Integer, String[]> getRows() {
        String csvFile = "attachments/10953.csv";
        lineNumber = 0;

        MyCSVReader reader;
        Map<Integer, String[]> rows = new LinkedHashMap<>();
        try {
            reader = new MyCSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                lineNumber++;
                rows.put(lineNumber, line);
            }
            LOGGER.debug("Строк всего = " + lineNumber);
            return rows;
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;
    }
}

/**
 * Нужные колонки из файла:
 * - "Бренд" загрузить в Vendor
 * - "Каталожный номер" загрузить в Number
 * - "Описание" загрузить в Description
 * - "Цена" в Price
 * - "Наличие" в Count
 */
