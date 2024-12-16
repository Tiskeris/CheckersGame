package Game;

class Board {
    private static final int BOARD_SIZE = 8;
    private final GamePiece[][] board;

    public Board() {
        this.board = new GamePiece[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        PieceFactory factory = new CheckersPieceFactory();


        for (int y = 0; y < 3; y++) {
            for (int x = (y + 1) % 2; x < BOARD_SIZE; x += 2) {
                board[y][x] = factory.createPiece(PieceColor.RED);
            }
        }
        //board[2][3] = factory.createPiece(Game.PieceColor.RED);
        //board[4][5] = factory.createPiece(Game.PieceColor.BLACK);
        for (int y = 5; y < BOARD_SIZE; y++) {
            for (int x = (y + 1) % 2; x < BOARD_SIZE; x += 2) {
                board[y][x] = factory.createPiece(PieceColor.BLACK);
            }
        }
    }

    public GamePiece getPiece(int x, int y) {
        if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
            return board[y][x];
        }
        return null;
    }

    public void movePiece(int fromX, int fromY, int toX, int toY) {
        board[toY][toX] = board[fromY][fromX];
        board[fromY][fromX] = null;

        if ((toY == 0 && board[toY][toX].getColor() == PieceColor.BLACK) ||
                (toY == BOARD_SIZE - 1 && board[toY][toX].getColor() == PieceColor.RED)) {
            board[toY][toX].promote();
        }
    }

    public void removePiece(int x, int y) {
        if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
            board[y][x] = null;
        }
    }

    public int getSize() {
        return BOARD_SIZE;
    }


}