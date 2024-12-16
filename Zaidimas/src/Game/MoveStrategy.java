package Game;

interface MoveStrategy {
    boolean isValidMove(Board board, GamePiece piece, int fromX, int fromY, int toX, int toY);
}