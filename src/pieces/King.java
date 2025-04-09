package pieces;

import main.Board;
import main.Move;

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
    //Special move of King (One square in any direction)
    public boolean isValidMovement(int col, int row) {
        return Math.abs((col - this.col) * (row - this.row)) == 1 || Math.abs(col - this.col) + Math.abs(row - this.row) == 1 || canCastle(col, row);
    }

    public boolean canCastle(int col, int row) {
        if (this.row == row) {
            if (col == 6) {
                Piece rook = board.getPiece(7, row);
                if (rook != null && rook.isFirstMove && isFirstMove) {
                    return board.getPiece(5, row) == null && board.getPiece(6, row) == null && !board.checkScanner.isKingChecked(new Move(board, this, 5, row));

                }
            }
            else if (col == 2) {
                Piece rook = board.getPiece(0, row);
                if (rook != null && rook.isFirstMove && isFirstMove) {
                    return board.getPiece(3, row) == null &&
                            board.getPiece(2, row) == null &&
                            board.getPiece(1, row) == null &&
                            !board.checkScanner.isKingChecked(new Move(board, this, 3, row));
                }
            }
        }
        return false;
    }
}
