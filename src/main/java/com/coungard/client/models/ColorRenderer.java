package com.coungard.client.models;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColorRenderer extends JLabel implements TableCellRenderer {
    private final boolean isBordered;

    public ColorRenderer(boolean isBordered) {
        this.isBordered = isBordered;
        setOpaque(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object color,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Color newColor = (Color) color;
        setBackground(newColor);
        if (isBordered) {
            if (isSelected) {
                setBorder(BorderFactory.createTitledBorder("selected"));
            } else {
                setBorder(BorderFactory.createTitledBorder("unselected"));
            }
        }

        return this;
    }
}