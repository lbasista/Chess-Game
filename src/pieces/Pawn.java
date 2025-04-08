package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Pawn";

        //Scale of piece
        this.sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale)
                .getScaledInstance(
                        (int) (board.tileSize * 0.9),
                        (int) (board.tileSize * 0.9),
                        BufferedImage.SCALE_SMOOTH
                );
    }

    //Special move of Pawn (One square forward (2 in 1st move), captures diagonally)
    public boolean isValidMovement(int col, int row) {
        int moveDir = isWhite ? -1 : 1; //Black: 1 (going down), White: -1 (going up)

        if (this.col == col) { //Move forward
            if (row == this.row + moveDir && board.getPiece(col, row) == null)
                return true;
            if (isFirstMove && row == this.row + 2 * moveDir && board.getPiece(col, row) == null && board.getPiece(col, this.row + moveDir) == null)
                return true;
        } else if (Math.abs(col - this.col) == 1 && row == this.row + moveDir && board.getPiece(col, row) != null) { //Capture
            return true;
        }
        return false;
    }
}