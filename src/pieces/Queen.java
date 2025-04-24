package pieces;

import main.Board;
import main.PieceSpriteManager;

public class Queen extends Piece{
    public Queen(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Queen";

        updateSprite();

        //Image of piece
        this.sprite = PieceSpriteManager.getPieceSprite("Queen", isWhite, board.tileSize);
    }

    //Special move of Queen (Vertically, horizontally or diagonally (combined Rook and Bishop))
    public boolean isValidMovement(int col, int row){
        return this.col == col || this.row == row || Math.abs(col - this.col) == Math.abs(row - this.row);
    }

    //Is piece on way?
    public boolean moveCollidesWithPiece(int col, int row){
        if (Math.abs(col - this.col) == Math.abs(row - this.row)) {
            int colDirection = (col > this.col) ? 1 : -1; //1 = right, -1 = left
            int rowDirection = (row > this.row) ? 1 : -1; //1 = down, -1 = up
            for (int i = 1; i < Math.abs(col - this.col); i++) {
                int checkCol = this.col + i * colDirection;
                int checkRow = this.row + i * rowDirection;
                if (board.getPiece(checkCol, checkRow) != null) return true;
            }
        } else if (col == this.col) {
            //Vertical
            int start = Math.min(this.row, row) + 1;
            int end = Math.max(this.row, row);
            for (int r = start; r < end; r++) {
                if (board.getPiece(this.col, r) != null) return true;
            }
        } else {
            //Horizontal
            int start = Math.min(this.col, col) + 1;
            int end = Math.max(this.col, col);
            for (int c = start; c < end; c++) {
                if (board.getPiece(c, this.row) != null) return true;
            }
        }
        return false;
    }
}
