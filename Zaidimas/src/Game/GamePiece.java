package Game;

abstract class GamePiece {
    private final PieceColor color;
    private boolean isKing;

    public GamePiece(PieceColor color) {
        this.color = color;
        this.isKing = false;
    }

    public PieceColor getColor() {
        return color;
    }

    public boolean isKing() {
        return isKing;
    }

    public void promote() {
        this.isKing = true;
    }

    public abstract boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY);
}