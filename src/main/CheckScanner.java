package main;

import pieces.Piece;

public class CheckScanner {
    Board board;

    public CheckScanner(Board board) {
        this.board = board;
    }

    public boolean isKingChecked(Move move) {
        Piece king = board.findKing(move.piece.isWhite);
        if (king == null) return false;

        int kingCol = king.col;
        int kingRow = king.row;

        // Check rook and queen moves
        return checkStraightLines(king, kingCol, kingRow) ||
                checkDiagonals(king, kingCol, kingRow) ||
                checkKnightAttacks(king, kingCol, kingRow) ||
                checkPawnAttacks(king, kingCol, kingRow) ||
                checkKingAttacks(king, kingCol, kingRow);
    }

    private boolean checkStraightLines(Piece king, int kingCol, int kingRow) {
        // Left, right, up, down
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] direction : directions) {
            for (int i = 1; i < 8; i++) {
                int col = kingCol + direction[0] * i;
                int row = kingRow + direction[1] * i;

                if (col < 0 || col > 7 || row < 0 || row > 7) break;

                Piece piece = board.getPiece(col, row);
                if (piece != null) {
                    if (!board.sameTeam(piece, king) &&
                            (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonals(Piece king, int kingCol, int kingRow) {
        // All 4 diagonals
        int[][] directions = {{-1, -1}, {1, -1}, {1, 1}, {-1, 1}};

        for (int[] direction : directions) {
            for (int i = 1; i < 8; i++) {
                int col = kingCol + direction[0] * i;
                int row = kingRow + direction[1] * i;

                if (col < 0 || col > 7 || row < 0 || row > 7) break;

                Piece piece = board.getPiece(col, row);
                if (piece != null) {
                    if (!board.sameTeam(piece, king) &&
                            (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    private boolean checkKnightAttacks(Piece king, int kingCol, int kingRow) {
        int[][] moves = {{-2,-1},{-1,-2},{1,-2},{2,-1},{2,1},{1,2},{-1,2},{-2,1}};

        for (int[] move : moves) {
            int col = kingCol + move[0];
            int row = kingRow + move[1];

            if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                Piece piece = board.getPiece(col, row);
                if (piece != null && !board.sameTeam(piece, king) && piece.name.equals("Knight")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkPawnAttacks(Piece king, int kingCol, int kingRow) {
        int direction = king.isWhite ? -1 : 1;
        int[][] attacks = {{-1, direction}, {1, direction}};

        for (int[] attack : attacks) {
            int col = kingCol + attack[0];
            int row = kingRow + attack[1];

            if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                Piece piece = board.getPiece(col, row);
                if (piece != null && !board.sameTeam(piece, king) && piece.name.equals("Pawn")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkKingAttacks(Piece king, int kingCol, int kingRow) {
        int[][] moves = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

        for (int[] move : moves) {
            int col = kingCol + move[0];
            int row = kingRow + move[1];

            if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                Piece piece = board.getPiece(col, row);
                if (piece != null && !board.sameTeam(piece, king) && piece.name.equals("King")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGameOver(Piece king) {
        for (Piece piece : board.pieceList) {
            if (board.sameTeam(piece, king)) {
                board.selectedPiece = piece == king ? king : null;
                for (int row = 0; row < board.rows; row++) {
                    for (int col = 0; col < board.cols; col++) {
                        Move move = new Move(board, piece, col, row);
                        if (board.isValidMove(move)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isKingInCheck(boolean isWhite) {
        Piece king = board.findKing(isWhite);
        if (king == null) return false;

        int kingCol = king.col;
        int kingRow = king.row;

        return checkStraightLines(king, kingCol, kingRow) ||
                checkDiagonals(king, kingCol, kingRow) ||
                checkKnightAttacks(king, kingCol, kingRow) ||
                checkPawnAttacks(king, kingCol, kingRow) ||
                checkKingAttacks(king, kingCol, kingRow);
    }
}