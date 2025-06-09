package org.example.GUI.Views;

import javax.swing.*;
import java.awt.*;

public class AlphaPanel extends JPanel {
    public AlphaPanel(Color bgColor) {
        setOpaque(false); // Hacer que permita transparencia
        setBackground(bgColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getBackground() != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }
}