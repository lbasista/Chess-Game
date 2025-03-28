package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Bishop extends Piece{
    public Bishop(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Bishop";

        //Scale of piece
        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0: sheetScale, sheetScale, sheetScale)
                .getScaledInstance(
                        (int) (board.tileSize * 0.9),
                        (int) (board.tileSize * 0.9),
                        BufferedImage.SCALE_SMOOTH
                );
    }
}
