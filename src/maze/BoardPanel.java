package maze;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private final int CELL_SIZE = 10;
    private int rows;
    private int columns;
    private int boardWidth;
    private int boardHeight;
    private int x;
    private int y;
    private Board board;
    private boolean win;

    BoardPanel(int rows, int columns, Board board) {
        newGame(rows, columns, board);
    }

    void newGame(int rows, int columns, Board board) {
        this.rows = rows;
        this.columns = columns;
        boardWidth = columns * CELL_SIZE;
        boardHeight = rows * CELL_SIZE;
        this.board = board;
        x = 0;
        y = 0;
        win = false;
        initBoardGui();
    }

    private void initBoardGui() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
    }

    boolean handleMove(Direction direction) {
        switch (direction) {
            case left:
                moveLeft();
                break;
            case right:
                moveRight();
                break;
            case up:
                moveUp();
                break;
            case down:
                moveDown();
                break;
        }
        if (checkForWin()) {
            handleWin();
            return true;
        }
        return false;
    }

    private void handleWin() {
        win = true;
    }

    private boolean checkForWin() {
        return x == columns - 1 && y == rows - 1;
    }

    private void moveLeft() {
        if (!win) {
            if (x != 0) {
                if (board.getBoardState()[y][x - 1] != 1) {
                    x--;
                }
            }
        }
    }

    private void moveRight() {
        if (!win) {
            if (x != columns - 1) {
                if (board.getBoardState()[y][x + 1] != 1) {
                    x++;
                }
            }
        }
    }

    private void moveUp() {
        if (!win) {
            if (y != 0) {
                if (board.getBoardState()[y - 1][x] != 1) {
                    y--;
                }
            }
        }
    }

    private void moveDown() {
        if (!win) {
            if (y != rows - 1) {
                if (board.getBoardState()[y + 1][x] != 1) {
                    y++;
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board.getBoardState()[i][j] == 1) {
                    g.fillRect((j * CELL_SIZE), (i * CELL_SIZE), CELL_SIZE, CELL_SIZE);
                }
            }
            drawFinish(g);
        }
        drawCurrentPosition(g);
    }

    private void drawFinish(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(((columns - 1) * CELL_SIZE), ((rows - 1) * CELL_SIZE), CELL_SIZE, CELL_SIZE);
        g.setColor(Color.BLACK);
    }

    private void drawCurrentPosition(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE - 1, CELL_SIZE - 1);
        g.setColor(Color.BLACK);
    }
}
