package pieces;

import main.Board;
import main.PieceSpriteManager;

public class Rook extends Piece{
    public Rook(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Rook";

        updateSprite();

        //Image of piece
        this.sprite = PieceSpriteManager.getPieceSprite("Rook", isWhite, board.tileSize);
    }

    //Special move of Rook (only vertically or horizontally)
    public boolean isValidMovement(int col, int row){
        return this.col == col || this.row == row;
    }

    //Is piece on way?
    public boolean moveCollidesWithPiece(int col, int row){
        if (col == this.col) {
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
