package Game;

import static org.junit.Assert.*;
import org.junit.Test;

public class Tests {

    @Test
    public void testBoardCreation() {
        Board board = new Board();
        int redPieces = 0;
        int blackPieces = 0;

        for (int y = 0; y < board.getSize(); y++) {
            for (int x = 0; x < board.getSize(); x++) {
                GamePiece piece = board.getPiece(x, y);
                if (piece != null) {
                    if (piece.getColor() == PieceColor.RED) {
                        redPieces++;
                    } else if (piece.getColor() == PieceColor.BLACK) {
                        blackPieces++;
                    }
                }
            }
        }

        assertEquals("Number of red checkers should be 12", 12, redPieces);
        assertEquals("Number of black checkers should be 12", 12, blackPieces);
    }

    @Test
    public void testPieceMovement() {
        CheckersGame game = new CheckersGame();
        Board board = game.getBoard();

        boolean moveSuccessful = game.makeMove(1, 2, 2, 3);
        assertTrue("Move should be successful", moveSuccessful);

        GamePiece piece = board.getPiece(2, 3);
        assertNotNull("Piece should be at the new position", piece);
        assertNull("Original position should be empty", board.getPiece(1, 2));
    }

    @Test
    public void testGameOver() {
        CheckersGame game = new CheckersGame();

        Board board = game.getBoard();
        for (int y = 5; y < board.getSize(); y++) {
            for (int x = (y + 1) % 2; x < board.getSize(); x += 2) {
                board.removePiece(x, y);
            }
        }

        game.makeMove(1, 2, 2, 3);

        assertTrue("Game should be over", game.isGameOver());
        assertEquals("Winner should be RED", PieceColor.RED, game.getWinner());
    }
}
