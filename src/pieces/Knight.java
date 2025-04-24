package pieces;

import main.Board;
import main.PieceSpriteManager;

public class Knight extends Piece{
    public Knight(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Knight";

        updateSprite();

        //Image of piece
        this.sprite = PieceSpriteManager.getPieceSprite("Knight", isWhite, board.tileSize);
    }

    //Special move of Knight (L-shape)
    public boolean isValidMovement(int col, int row){
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2;
    }
}
