package Game;

class CheckersPieceFactory implements PieceFactory {
    @Override
    public GamePiece createPiece(PieceColor color) {
        return new CheckersPiece(color);
    }
}