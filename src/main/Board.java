package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Board extends JPanel {
    private JLabel turnLabel;
    //Size of tile
    public int tileSize = 85;
    //Board 8x8
    final int cols = 8;
    final int rows = 8;

    ArrayList<Piece> pieceList = new ArrayList<>();

    public Piece selectedPiece;

    Input input = new Input(this);

    public CheckScanner checkScanner = new CheckScanner(this);

    public int enPassantTile = -1;
    public String enPassantTarget = "-";

    private boolean isWhiteToMove = true;
    private boolean isGameOver = false;

    public boolean isCheck = false;

    private int fiftyMoveCounter = 0;

    public int halfmoveClock = 0;
    public int fullmoveNumber = 1;

    private Clock clock = new Clock();

    public Board(JLabel turnLabel){
        clock.startGame();
        this.turnLabel = turnLabel;
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        addPieces();
    }

    public void updateAllSprites() {
        for (Piece piece : pieceList) {
            piece.updateSprite();
        }
        repaint();
    }

    public Clock getClock() {
        return clock;
    }

    public Piece getPiece(int col, int row){
        for (Piece piece : pieceList){
            if (piece.col == col && piece.row == row){
                return piece;
            }
        }
        return null;
    }

    public void makeMove(Move move){
        if (isGameOver) {
            return;
        }
        if (move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else if (move.piece.name.equals("King")) {
            moveKing(move);
        }
        capture(move.capture);

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newCol * tileSize;
        move.piece.yPos = move.newRow * tileSize;

        move.piece.isFirstMove = false;

        isWhiteToMove = !isWhiteToMove; //Changing color to move
        clock.switchTurn();
        this.turnLabel.setText("Current player: " + (isWhiteToMove ? "White" : "Black"));
        updateGameState();

        //50 Moves
        if (move.capture != null || move.piece.name.equals("Pawn")) {
            fiftyMoveCounter = 0;
        } else {
            fiftyMoveCounter += 1;
        }

        //enPassant
        if (move.piece.name.equals("Pawn") && Math.abs(move.newRow - move.piece.row) == 2) {
            int enPassantRow = (move.piece.row + move.newRow) / 2;
            char colChar = (char) ('a' + move.newCol);
            enPassantTarget = "" + colChar + (8 - enPassantRow);
        } else {
            enPassantTarget = "-";
        }

        //Halfmove
        if (move.piece.name.equals("Pawn") || move.capture != null) {
            halfmoveClock = 0; //Reset if captured or pawn
        } else {
            halfmoveClock += 1;
        }
        if(!isWhiteToMove) {
            fullmoveNumber += 1;
        }
    }

    private boolean wCanCastleKing = true;
    private boolean wCanCastleQueen = true;
    private boolean bCanCastleKing = true;
    private boolean bCanCastleQueen = true;

    private void moveKing(Move move) {
        //If kings move 2 squares (Castling)
        if (Math.abs(move.piece.col - move.newCol) == 2) {
            //Short castling (0-0)
            if (move.newCol == 6) {
                Piece rook = getPiece(7, move.piece.row);
                rook.col = 5;
                rook.xPos = 5 * tileSize;
                rook.isFirstMove = false;
                //Update castling status
                if (move.piece.isWhite) {
                    wCanCastleKing = false;
                } else {
                    bCanCastleKing = false;
                }
            } else if (move.newCol == 2) {
                //Long castling (0-0-0)
                Piece rook = getPiece(0, move.piece.row);
                rook.col = 3;
                rook.xPos = 3 * tileSize;
                rook.isFirstMove = false;
                //Update castling status
                if (move.piece.isWhite) {
                    wCanCastleQueen = false;
                } else {
                    bCanCastleQueen = false;
                }
            }
        }
        move.piece.isFirstMove = false;
        //Update castling status for both sides when king moves
        if (move.piece.isWhite) {
            wCanCastleKing = false;
            wCanCastleQueen = false;
        } else {
            bCanCastleKing = false;
            bCanCastleQueen = false;
        }
    }

    private void movePawn(Move move) {
        //En passant
        int colorIndex = move.piece.isWhite ? 1 : -1;

        if (getTileNum(move.newCol, move.newRow) == enPassantTile) {
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        }
        if (Math.abs(move.piece.row - move.newRow) == 2) {
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        } else {
            enPassantTile = -1;
        }

        //Promotions
        colorIndex = move.piece.isWhite ? 0 : 7;
        if (move.newRow == colorIndex) {
            promotePawn(move);
        }
    }

    private void promotePawn(Move move) {
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece){
        pieceList.remove(piece);
    }

    public boolean isValidMove(Move move){
        if (isGameOver) return false;
        if (move.piece.isWhite != isWhiteToMove) return false;
        if (sameTeam(move.piece, move.capture)) return false;
        if (!move.piece.isValidMovement(move.newCol, move.newRow)) return false;
        if (move.piece.moveCollidesWithPiece(move.newCol, move.newRow)) return false;

        //Move simulaton
        Piece originalCapture = move.capture;
        int oldCol = move.piece.col;
        int oldRow = move.piece.row;

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        pieceList.remove(originalCapture);

        boolean kingInCheck = checkScanner.isKingInCheck(move.piece.isWhite);

        //Undo move
        move.piece.col = oldCol;
        move.piece.row = oldRow;
        if (originalCapture != null) {
            pieceList.add(originalCapture);
        }

        if (kingInCheck) return false;

        //Castling
        if (move.piece.name.equals("King") && Math.abs(move.piece.col - move.newCol) == 2) {
            return isValidCastling(move);
        }
        return true;
    }

    public boolean isValidCastling(Move move) {
        //Only king during 1st move
        if (!move.piece.name.equals("King") || !move.piece.isFirstMove) {
            return false;
        }
        //Direction of castling
        boolean isShortCastle = (move.newCol == 6);
        int rookCol = isShortCastle ? 7 : 0;
        Piece rook = getPiece(rookCol, move.piece.row);

        //Does rook still on board and don't move already?
        if (rook == null || !rook.name.equals("Rook") || !rook.isFirstMove) {
            return false;
        }

        //Are squares empty between king and rook?
        int start = Math.min(move.piece.col, rookCol) + 1;
        int end = Math.max(move.piece.col, rookCol);
        for (int col = start; col < end; col++) {
            if (getPiece(col, move.piece.row) != null) {
                return false;
            }
        }
        //Is king checked and not moving thru attack
        int step = (move.newCol > move.piece.col) ? 1 : -1;
        for (int col = move.piece.col; col != move.newCol + step; col += step) {
            Move testMove = new Move(this, move.piece, col, move.piece.row);
            if (checkScanner.isKingChecked(testMove)) {
                this.turnLabel.setText(isWhiteToMove ? "Black wins!" : "White wins!");
                return false;
            }
        }
        return true;
    }

    public boolean sameTeam(Piece p1, Piece p2){
        if (p1 == null || p2 == null){
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    public int getTileNum(int col, int row) {
        return row * rows + col;
    }

    Piece findKing(boolean isWhite) {
        for (Piece piece : pieceList) {
            if (isWhite == piece.isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }

    public void addPieces(){
        //Black pieces
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new Queen(this, 3, 0, false));
        for(int i = 0; i < cols; i++){
            pieceList.add(new Pawn(this, i, 1, false));
        }
        //White pieces
        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));
        for(int i = 0; i < cols; i++){
            pieceList.add(new Pawn(this, i, 6, true));
        }
    }

    private void updateGameState() {
        Piece king = findKing(isWhiteToMove);
        if (king == null) {
            this.turnLabel.setText(isWhiteToMove ? "Black wins!" : "White wins!");
            isGameOver = true;
            return;
        }

        //Has king legal moves?
        boolean hasLegalMoves = false;
        for (Piece piece : pieceList) {
            if (piece.isWhite == isWhiteToMove) {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        Move move = new Move(this, piece, col, row);
                        if (isValidMove(move)) {
                            hasLegalMoves = true;
                            break;
                        }
                    }
                    if (hasLegalMoves) break;
                }
                if (hasLegalMoves) break;
            }
        }

        if (!hasLegalMoves) {
            //Move kingPosition = new Move(this, king, king.col, king.row);
            isCheck = checkScanner.isKingInCheck(isWhiteToMove);
            if (isCheck) {
                this.turnLabel.setText(isWhiteToMove ? "Black wins!" : "White wins!"); // Mat
            } else {
                this.turnLabel.setText("Stalemate"); // Pat
            }
            isGameOver = true;
        } else if (insufficientMaterial(true) && insufficientMaterial(false)) {
            this.turnLabel.setText("Insufficient Material");
            isGameOver = true;
        } else if (fiftyMoveCounter >= 100) {
            this.turnLabel.setText("Draw by 50-move rule");
            isGameOver = true;
        }

        if (isGameOver) {
            clock.switchTurn();
            repaint();
            Main.hasStartedGame = false;
        }
    }

    private boolean insufficientMaterial(boolean isWhite) {
        ArrayList<String> names = pieceList.stream()
                .filter(p -> p.isWhite == isWhite)
                .map(p -> p.name)
                .collect(Collectors.toCollection(ArrayList::new));
        if (names.contains("Queen") || names.contains("Pawn") || names.contains("Rook")) {
            return false;
        }
        return names.size() < 3;
    }

    public void resetGame() {
        pieceList.clear();
        addPieces();
        isWhiteToMove = true;
        isGameOver = false;
        selectedPiece = null;
        turnLabel.setText("Current player: White");
        clock.resetClock();
        repaint();
        Main.hasStartedGame = true; //Unlock resume button
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        //Paint squares
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                //Colors of squares
                g2d.setColor((c + r) % 2 == 0 ? new Color(248, 247, 249) : new Color(146, 220, 229));
                //Draw squares
                g2d.fillRect(c*tileSize, r*tileSize, tileSize, tileSize);
                //Legend on board
                if (r == 7) { // Dolna krawędź (A-H)
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, 10));
                    g2d.drawString(Character.toString((char)('A' + c)),
                            c * tileSize + tileSize / 2,
                            r * tileSize + tileSize - 5);
                }
                if (c == 0) { // Lewa krawędź (1-8)
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, 10));
                    g2d.drawString(Integer.toString(8 - r),
                            5,
                            r * tileSize + tileSize / 2);
                }
            }
        }

        //Highlight possible moves
        if (selectedPiece != null) {
            //Standard moves
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (isValidMove(new Move(this, selectedPiece, c, r))) {
                        g2d.setColor(new Color(0, 230, 65, 150));
                        g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                    }
                }
            }
            //Castling for King
            if (selectedPiece.name.equals("King") && selectedPiece.isFirstMove) {
                //Short (O-O)
                if (isValidMove(new Move(this, selectedPiece, 6, selectedPiece.row))) {
                    g2d.setColor(new Color(245, 255, 100, 190));
                    g2d.fillRect(6 * tileSize, selectedPiece.row * tileSize, tileSize, tileSize);
                }
                //Long (O-O-O)
                if (isValidMove(new Move(this, selectedPiece, 2, selectedPiece.row))) {
                    g2d.setColor(new Color(245, 255, 100, 190));
                    g2d.fillRect(2 * tileSize, selectedPiece.row * tileSize, tileSize, tileSize);
                }
            }
        }
        //Paint Pieces
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }
}