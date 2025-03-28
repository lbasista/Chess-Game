package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class King extends Piece{
    public King(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "King";

        //Scale of piece
        this.sprite = sheet.getSubimage(0, isWhite ? 0: sheetScale, sheetScale, sheetScale)
                .getScaledInstance(
                        (int) (board.tileSize * 0.9),
                        (int) (board.tileSize * 0.9),
                        BufferedImage.SCALE_SMOOTH
                );
    }
}
