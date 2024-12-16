package Game;

class StandardCheckersMoveStrategy implements MoveStrategy {
    @Override
    public boolean isValidMove(Board board, GamePiece piece, int fromX, int fromY, int toX, int toY) {
        return piece.isValidMove(board, fromX, fromY, toX, toY);
    }
}