package com.coungard.client.desktop;

import com.coungard.client.models.ColorRenderer;
import com.coungard.client.util.MyCSVReader;
import com.coungard.client.util.Utils;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PriceListTab extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(PriceListTab.class.getName());
    private static final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private JScrollPane scrollPane;
    private JTable table = new JTable();

    private String[] headers = new String[]{"Бренд", "Каталожный номер", "Описание", "Цена", "Наличие"};

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
        scrollPane.setBounds(0, 0, 800, 450);
        scrollPane.setOpaque(false);
        scrollPane.setFont(font);

        DefaultTableModel model = new DefaultTableModel(new String[0][0], headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);

        JButton loadData = Utils.createButton("Загрузить данные", 220, 40, font);
        loadData.setLocation(10, 470);
        add(loadData);
        loadData.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTable();
            }
        });
    }

    private void loadTable() {
        // todo.. fill from csv file
    }
}
