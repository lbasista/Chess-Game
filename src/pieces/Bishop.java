package pieces;

import main.Board;
import main.PieceSpriteManager;

public class Bishop extends Piece{
    public Bishop(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Bishop";

        updateSprite();

        //Image of piece
        this.sprite = PieceSpriteManager.getPieceSprite("Bishop", isWhite, board.tileSize);
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