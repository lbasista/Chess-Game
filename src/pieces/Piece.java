package pieces;

import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {
    public int col, row;
    public int xPos, yPos;

    public boolean isWhite;
    public String name;
    public int value;

    BufferedImage sheet;
    {
        try{
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("Chess_Pieces.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    protected int sheetScale = sheet.getWidth()/6;
    Image sprite;

    Board board;

    public Piece(Board board){
        this.board = board;
    }

    public boolean isValidMovement(int col, int row) {return true;}
    public boolean moveCollidesWithPiece(int col, int row) {return false;}

    public  void paint(Graphics2D g2d){
        //Center figure on square
        int drawX = xPos + (board.tileSize - sprite.getWidth(null)) / 2;
        int drawY = yPos + (board.tileSize - sprite.getHeight(null)) / 2;
        g2d.drawImage(sprite, drawX, drawY, null);
    }
}
