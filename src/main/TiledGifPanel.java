package main;

import javax.swing.*;
import java.awt.*;

public class TiledGifPanel extends JPanel {
    private final Image gifFrame;

    public TiledGifPanel(String gifPath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(gifPath));
        this.gifFrame = icon.getImage();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imgW = gifFrame.getWidth(this);
        int imgH = gifFrame.getHeight(this);

        if (imgW <= 0 || imgH <= 0) return;

        int scaledW = imgW * 2;
        int scaledH = imgH * 2;

        int offsetX = -((getWidth() % scaledW) / 2);
        int offsetY = -((getHeight() % scaledH) / 2);

        for (int x = offsetX; x < getWidth(); x += scaledW) {
            for (int y = offsetY; y < getHeight(); y += scaledH) {
                g.drawImage(gifFrame, x, y, scaledW, scaledH, this);
            }
        }
    }
}