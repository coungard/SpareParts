package com.coungard.client.desktop;

import com.coungard.client.DBManager;
import com.coungard.client.Settings;
import com.coungard.client.entity.AutoPart;
import com.coungard.client.util.MyCSVReader;
import com.coungard.client.util.Utils;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PriceListTab extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(PriceListTab.class.getName());
    private static final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private JScrollPane scrollPane;
    private JTable table = new JTable();

    private String[] headers = new String[]{"№", "Бренд", "Каталожный номер", "Описание", "Цена", "Наличие"};
    private TableColumnModel colModel;

    public PriceListTab() {
        setLayout(null);
        setOpaque(false);
        setBounds(0, 0, 1000, 480);

        scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        add(scrollPane);

        content();
    }

    private void content() {
        scrollPane.setBounds(10, 0, 970, 450);
        scrollPane.setOpaque(false);
        scrollPane.setFont(font);

        DefaultTableModel model = new DefaultTableModel(new String[0][0], headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);
        colModel = table.getColumnModel();

        JButton loadData = Utils.createButton("Загрузить данные", 220, 40, font);
        loadData.setLocation(10, 470);
        add(loadData);
        loadData.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // todo... DBManager.deleteDB();
                loadTable();
            }
        });
    }

    private void loadTable() {
        Map<Integer, String[]> csvData;
        try {
            FileReader fileReader = new FileReader("smtp/attachments/10953.csv");
            MyCSVReader csvReader = new MyCSVReader(fileReader, ';');
            csvData = csvReader.getRows();
            if (csvData == null) {
                LOGGER.error("Can not load csv data!");
                return;
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return;
        }

        List<String[]> rowList = getStructure(csvData);
        addPriceItemsToDB(rowList);

        String[][] data = new String[rowList.size()][];
        int count = 0;
        for (String[] r : rowList) data[count++] = r;
        DefaultTableModel model = new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(model);
        colModel.getColumn(0).setMinWidth(70);
        colModel.getColumn(1).setMinWidth(180);
        colModel.getColumn(2).setMinWidth(180);
        colModel.getColumn(3).setMinWidth(300);
        colModel.getColumn(4).setMinWidth(130);
        colModel.getColumn(5).setMinWidth(160);

        // для каждой ячейки добавляем всплывающую подсказку с ее содержимым
        for (int j = 0; j < colModel.getColumnCount(); j++) {
            colModel.getColumn(j).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    String tip = (String) table.getModel().getValueAt(row, column);
                    setToolTipText("<html><h3>" + tip);
                    return this;
                }
            });
        }
        repaint();
    }

    private void addPriceItemsToDB(List<String[]> rowList) {
        LOGGER.info("Loading PriceItems to DataBase...");
        long started = System.currentTimeMillis();
        for (int i = 0; i < rowList.size(); i++) {
            AutoPart autoPart = fetchAutoPart(rowList.get(i), i);
            DBManager.saveAutoPart(autoPart);
        }
        LOGGER.info("Price list loaded");
        String elapsed = Utils.calcElapsedTime(started);
        LOGGER.info("Elapsed time: " + elapsed);
    }

    private AutoPart fetchAutoPart(String[] part, int order) {
        long id = Long.parseLong(part[0]);
        if (part[1].length() > 64) {
            part[1] = part[1].substring(0, 64);
        }
        if (part[2].length() > 64) {
            part[2] = part[2].substring(0, 64);
        }
        if (part[3].length() > 512) {
            part[3] = part[3].substring(0, 64);
        }
        String vendor = part[1];
        String number = part[2];
        String vendorSearch = vendor.toUpperCase();
        String numberSearch = number.toUpperCase();
        String description = part[3];
        BigDecimal price;
        try {
            price = new BigDecimal(part[4]);
        } catch (NumberFormatException ex) {
            LOGGER.debug("Price cell on " + order + " line is broken! Fill empty.");
            price = BigDecimal.ZERO;
        }
        int count;
        try {
            part[5] = part[5].replaceAll("<", "");
            part[5] = part[5].replaceAll(">", "");
            String[] both;
            if ((both = part[5].split("-")).length > 1) {
                count = Math.max(Integer.parseInt(both[0]), Integer.parseInt(both[1]));
            } else {
                count = Integer.parseInt(part[5]);
            }
        } catch (NumberFormatException ex) {
            LOGGER.debug("Count cell on " + order + " line is broken! Put zero.");
            count = 0;
        }
        return new AutoPart(id, vendor, number, vendorSearch,
                numberSearch, description, price, count);
    }

    private List<String[]> getStructure(Map<Integer, String[]> csvData) {
        List<String[]> rowList = new ArrayList<>();

        int brandColumn = 0;
        int numberColumn = 0;
        int descriptionColumn = 0;
        int priceColumn = 0;
        int countColumn = 0;

        String[] head = csvData.get(0);
        for (int i = 0; i < head.length; i++) {
            String column = head[i];
            if (column.contains(Settings.VENDOR_COLUMN_NAME)) brandColumn = i;
            if (column.contains(Settings.NUMBER_COLUMN_NAME)) numberColumn = i;
            if (column.contains(Settings.DESCRIPTION_COLUMN_NAME)) descriptionColumn = i;
            if (column.contains(Settings.PRICE_COLUMN_NAME)) priceColumn = i;
            if (column.contains(Settings.COUNT_COLUMN_NAME)) countColumn = i;
        }
        LOGGER.debug("Columns order: brand[" + brandColumn + "], number[" + numberColumn + "], " +
                "description[" + descriptionColumn + "], price[" + priceColumn + "], count[" + countColumn + "]");

        for (Map.Entry<Integer, String[]> entry : csvData.entrySet()) {
            if (entry.getKey() == 0) // headers
                continue;
            String[] row = new String[headers.length];
            String[] parts = entry.getValue();

            row[0] = String.valueOf(entry.getKey());
            row[1] = parts.length > brandColumn ? parts[brandColumn] : "";
            row[2] = parts.length > numberColumn ? parts[numberColumn] : "";
            row[3] = parts.length > descriptionColumn ? parts[descriptionColumn] : "";
            row[4] = parts.length > priceColumn ? parts[priceColumn] : "";
            row[5] = parts.length > countColumn ? parts[countColumn] : "";
            rowList.add(row);
        }
        return rowList;
    }
}
