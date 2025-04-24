package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PieceSpriteManager {
    private static BufferedImage sheet;
    //Size of Piece in png file
    private static final int PIECE_WIDTH = 64;
    private static final int PIECE_HEIGHT = 64;

    public enum Style {
        DOGS, PIXEL;
    }

    private static Style currentStyle = Style.DOGS; //Default style is dogs

    public static void setStyle(Style style) {
        currentStyle = style;
    }

    public static Style getStyle() {
        return currentStyle;
    }

    //Source file
    public static void loadSheet() {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("Chess_Pieces.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getPieceSprite(String pieceName, boolean isWhite, int tileSize) {
        if (sheet == null) loadSheet();

        //Dogs (rows 1 & 2) or pixel style (rows 3 & 4)
        int row = currentStyle == Style.DOGS ? (isWhite ? 0 : 1) : (isWhite ? 2 : 3);

        int col = switch (pieceName) {
            case "King" -> 0;
            case "Queen" -> 1;
            case "Bishop" -> 2;
            case "Knight" -> 3;
            case "Rook" -> 4;
            case "Pawn" -> 5;
            default -> throw new IllegalArgumentException("Unknown piece: " + pieceName);
        };

        BufferedImage sub = sheet.getSubimage(col * PIECE_WIDTH,row * PIECE_HEIGHT, PIECE_WIDTH, PIECE_HEIGHT);

        //piece size = 90% of tile size
        return sub.getScaledInstance((int) (tileSize * 0.9), (int) (tileSize * 0.9), Image.SCALE_SMOOTH);
    }
}
