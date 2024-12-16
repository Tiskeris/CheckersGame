package Game;

class CheckersGame {
    private final Board board;
    private final MoveStrategy moveStrategy;
    private PieceColor currentPlayer;
    private boolean gameOver;
    private PieceColor winner;
    private boolean mustContinueCapturing;
    private int lastCapturedX = -1;
    private int lastCapturedY = -1;

    public CheckersGame() {
        this.board = new Board();
        this.moveStrategy = new StandardCheckersMoveStrategy();
        this.currentPlayer = PieceColor.RED;
        this.gameOver = false;
        this.mustContinueCapturing = false;
    }

    public boolean makeMove(int fromX, int fromY, int toX, int toY) {
        GamePiece piece = board.getPiece(fromX, fromY);

        if (piece == null || piece.getColor() != currentPlayer) {
            return false;
        }

        //System.out.println(mustContinueCapturing);
        if (mustContinueCapturing && (fromX != lastCapturedX || fromY != lastCapturedY)) {
            return false;
        }

        if (!moveStrategy.isValidMove(board, piece, fromX, fromY, toX, toY)) {
            return false;
        }

        boolean isCapture = Math.abs(fromX - toX) == 2;
        if (isCapture) {
            int midX = (fromX + toX) / 2;
            int midY = (fromY + toY) / 2;
            board.removePiece(midX, midY);
        }

        board.movePiece(fromX, fromY, toX, toY);

        if (isCapture && canContinueCapturing(toX, toY)) {
            mustContinueCapturing = true;
            lastCapturedX = toX;
            lastCapturedY = toY;
            return true;
        }

        mustContinueCapturing = false;
        lastCapturedX = -1;
        lastCapturedY = -1;
        if (currentPlayer == PieceColor.RED) {
            currentPlayer = PieceColor.BLACK;
        } else {
            currentPlayer = PieceColor.RED;
        }

        checkGameStatus();

        return true;
    }

    private boolean canContinueCapturing(int x, int y) {
        GamePiece piece = board.getPiece(x, y);
        if (piece == null) return false;

        int[] dx = {-2, 2, -2, 2};
        int[] dy = {-2, -2, 2, 2};

        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (newX >= 0 && newX < board.getSize() &&
                    newY >= 0 && newY < board.getSize()) {
                if (moveStrategy.isValidMove(board, piece, x, y, newX, newY)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void checkGameStatus() {
        int redPieces = countPieces(PieceColor.RED);
        int blackPieces = countPieces(PieceColor.BLACK);

        if (redPieces == 0) {
            gameOver = true;
            winner = PieceColor.BLACK;
        } else if (blackPieces == 0) {
            gameOver = true;
            winner = PieceColor.RED;
        }
    }

    private int countPieces(PieceColor color) {
        int count = 0;
        for (int y = 0; y < board.getSize(); y++) {
            for (int x = 0; x < board.getSize(); x++) {
                GamePiece piece = board.getPiece(x, y);
                if (piece != null && piece.getColor() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    public Board getBoard() {
        return board;
    }

    public PieceColor getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public PieceColor getWinner() {
        return winner;
    }
}