package Game;

class CheckersPiece extends GamePiece {
    public CheckersPiece(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY) {
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);

        if (toX < 0 || toX >= board.getSize() || toY < 0 || toY >= board.getSize()) {
            return false;
        }

        if (isKing() && dx == 1 && dy == 1 && board.getPiece(toX, toY) == null) {
            return true;
        }

        if (dx == 1 && dy == 1 && board.getPiece(toX, toY) == null) {
            if (getColor() == PieceColor.RED && toY < fromY) return false;
            if (getColor() == PieceColor.BLACK && toY > fromY) return false;
            return true;
        }

        if (dx == 2 && dy == 2 && board.getPiece(toX, toY) == null) {
            int midX = (fromX + toX) / 2;
            int midY = (fromY + toY) / 2;
            GamePiece middlePiece = board.getPiece(midX, midY);
            return middlePiece != null && middlePiece.getColor() != getColor();
        }

        return false;
    }
}