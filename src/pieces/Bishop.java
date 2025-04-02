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

    //Special move of Bishop (diagonally)
    public boolean isValidMovement(int col, int row){
        return Math.abs(col - this.col) == Math.abs(row - this.row);
    }

    //Is piece on way?
    public boolean moveCollidesWithPiece(int col, int row){
        int colDirection = (col > this.col) ? 1 : -1; //1 = right, -1 = left
        int rowDirection = (row > this.row) ? 1 : -1; //1 = down, -1 = up
        for (int i = 1; i < Math.abs(col - this.col); i++) {
            int checkCol = this.col + i * colDirection;
            int checkRow = this.row + i * rowDirection;
            if (board.getPiece(checkCol, checkRow) != null) return true;
        }
        return false;
    }
}