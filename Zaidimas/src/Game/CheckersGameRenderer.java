package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CheckersGameRenderer extends JPanel {
    private static final int TILE_SIZE = 64;
    private final CheckersGame game;
    private int selectedX = -1;
    private int selectedY = -1;

    private BufferedImage whiteTile, blackTile;
    private BufferedImage redPiece, blackPiece;
    private BufferedImage redKing, blackKing;

    public CheckersGameRenderer(CheckersGame game) {
        this.game = game;
        loadImages();
        setupMouseListener();
    }

    private void loadImages() {
        try {
            whiteTile = ImageIO.read(new File("whitertile.png"));
            blackTile = ImageIO.read(new File("brown.png"));
            redPiece = ImageIO.read(new File("redpiece.png"));
            blackPiece = ImageIO.read(new File("blackpiece.png"));
            redKing = ImageIO.read(new File("redking.png"));
            blackKing = ImageIO.read(new File("blackking.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / TILE_SIZE;
                int y = e.getY() / TILE_SIZE;
                System.out.println(x + " " + y);
                handleMouseClick(x, y);
            }
        });
    }

    private void handleMouseClick(int x, int y) {
        if (game.isGameOver()) return;

        if (selectedX == -1) {
            GamePiece piece = game.getBoard().getPiece(x, y);
            if (piece != null && piece.getColor() == game.getCurrentPlayer()) {
                selectedX = x;
                selectedY = y;
            }
        } else {
            game.makeMove(selectedX, selectedY, x, y);

            selectedX = -1;
            selectedY = -1;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderBoard(g);
    }

    private void renderBoard(Graphics g) {
        Board board = game.getBoard();

        for (int y = 0; y < board.getSize(); y++) {
            for (int x = 0; x < board.getSize(); x++) {
                BufferedImage tileImage = ((x + y) % 2 == 0) ? whiteTile : blackTile;
                g.drawImage(tileImage, x * TILE_SIZE, y * TILE_SIZE, null);

                GamePiece piece = board.getPiece(x, y);
                if (piece != null) {
                    BufferedImage pieceImage = getPieceImage(piece);
                    g.drawImage(pieceImage, x * TILE_SIZE, y * TILE_SIZE, null);
                }
            }
        }

        if (selectedX != -1 && selectedY != -1) {
            g.setColor(Color.YELLOW);
            g.drawRect(selectedX * TILE_SIZE, selectedY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        if (game.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String winMessage = game.getWinner() + " WINS!";
            g.drawString(winMessage, getWidth() / 2 - 100, getHeight() / 2);
        }
    }

    private BufferedImage getPieceImage(GamePiece piece) {
        if (piece.getColor() == PieceColor.RED) {
            return piece.isKing() ? redKing : redPiece;
        } else {
            return piece.isKing() ? blackKing : blackPiece;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Checkers Game");
            CheckersGame game = new CheckersGame();
            CheckersGameRenderer renderer = new CheckersGameRenderer(game);

            frame.add(renderer);
            frame.setSize(game.getBoard().getSize() * TILE_SIZE + 15,
                    game.getBoard().getSize() * TILE_SIZE + 39);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}