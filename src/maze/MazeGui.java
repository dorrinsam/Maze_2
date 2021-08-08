package maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MazeGui extends JFrame {
    private final static int ROWS = 25;
    private final static int COLUMNS = 100;
    private Board board;
    private Timer timer;
    private BoardPanel boardPanel;
    private boolean winPrinted;

    private MazeGui() {
        MazeGenerator generator = new AtLeast85Percent(ROWS, COLUMNS);
        this.board = new Board(generator);
        initUi();
    }

    private void initUi() {
        JMenuBar menuBar = new JMenuBar();
        JMenu newMenu = new JMenu("New");
        JMenuItem classicGame = new JMenuItem("Classic Game");
        JMenuItem denseGame = new JMenuItem("85% Game");
        newMenu.add(classicGame);
        newMenu.add(denseGame);
        menuBar.add(newMenu);
        setJMenuBar(menuBar);

        timer = new Timer();
        add(timer, BorderLayout.SOUTH);

        boardPanel = new BoardPanel(ROWS, COLUMNS, board);
        add(boardPanel);

        setResizable(false);
        pack();

        setTitle("Maze");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                handleKeyPressed(keyCode);
            }

        });

        classicGame.addActionListener(e -> newGame(new DFS(ROWS, COLUMNS)));
        denseGame.addActionListener(e -> newGame(new AtLeast85Percent(ROWS, COLUMNS)));
    }

    private void newGame(MazeGenerator generator) {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure?");
        if (result == JOptionPane.YES_OPTION) {
            winPrinted = false;
            timer.reset();
            this.board = new Board(generator);
            boardPanel.newGame(ROWS, COLUMNS, board);
            repaint();
        }
    }

    private void handleKeyPressed(int keyCode) {
        Direction direction = null;
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                direction = Direction.left;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                direction = Direction.right;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                direction = Direction.up;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                direction = Direction.down;
                break;
        }
        if (direction != null) {
            boolean win = boardPanel.handleMove(direction);
            if (!timer.isStarted() && !win) {
                timer.start(this::repaint);
            }
            repaint();
            if (win && !winPrinted) {
                timer.end();
                JOptionPane.showMessageDialog(this, "You win in " + timer.getDisplayTime() + "!", "", JOptionPane.INFORMATION_MESSAGE);
                winPrinted = true;
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MazeGui mazeGui = new MazeGui();
            mazeGui.setVisible(true);
        });
    }
}
