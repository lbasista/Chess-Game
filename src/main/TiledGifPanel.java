package main;

import javax.swing.*;
import java.awt.*;

public class TiledGifPanel extends JPanel {
    private final Image gifFrame;
    private final int tileCount;
    private final boolean horizontal;

    public TiledGifPanel(String gifPath, int tileCount, boolean horizontal) {
        this.tileCount = tileCount;
        this.horizontal = horizontal;
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

        int tileW, tileH, countX, countY;
        int offsetX = 0, offsetY = 0;

        if (horizontal) {
            tileW = getWidth() / tileCount;
            tileH = (int) ((double) tileW * imgH / imgW);
            countX = tileCount;
            countY = getHeight() / tileH + 2;
            offsetX = (getWidth() - (tileW * tileCount)) / 2;
            offsetY = (getHeight() - (countY * tileH)) / 2;
        } else {
            tileH = getHeight() / tileCount;
            tileW = (int) ((double) tileH * imgW / imgH);
            countY = tileCount;
            countX = getWidth() / tileW + 2;
            offsetX = (getWidth() - (countX * tileW)) / 2;
            offsetY = 0;
        }

        int fullHeight = tileH * tileCount;
        for (int x = 0; x < getWidth(); x += tileW) {
            for (int y = 0; y < fullHeight; y += tileH) {
                g.drawImage(gifFrame, x, y, tileW, tileH, this);
            }
            g.drawImage(gifFrame, x, fullHeight, tileW, tileH, this);
        }
    }
}