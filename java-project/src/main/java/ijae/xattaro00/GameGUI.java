package ijae.xattaro00;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class GameGUI {
    private JFrame frame; // Main game window
    private JPanel boardPanel; // Panel for the game board
    private JTextArea logTextArea; // Area to display logs
    private JLabel coinCounterLabel; // Label to show coins collected
    private Game game; // Game logic and state
    private Map<Class<? extends BoardObject>, ImageIcon> iconMap; // Icons for game objects

    public GameGUI(Game game) {
        if (game == null || game.level == null) {
            throw new IllegalArgumentException("Game or Game Level cannot be null");
        }
        this.game = game;
        initializeIcons(); // Load icons
        initialize(); // Set up the GUI
    }

    private void initializeIcons() {
        iconMap = new HashMap<>();
        iconMap.put(Player.class, new ImageIcon("Images/blueshirt.png"));
        iconMap.put(Enemy.class, new ImageIcon("Images/greenshirt.png"));
        iconMap.put(Coin.class, new ImageIcon("Images/coin.png"));
        iconMap.put(Key.class, new ImageIcon("Images/key.png"));
        iconMap.put(Wall.class, new ImageIcon("Images/wall.png"));
        iconMap.put(Gate.class, new ImageIcon("Images/closedgate.png"));
    }

    private void initialize() {
        frame = new JFrame("Swing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 700);

        coinCounterLabel = new JLabel("Coins: 0", SwingConstants.CENTER);
        coinCounterLabel.setFont(coinCounterLabel.getFont().deriveFont(16.0f));
        frame.add(coinCounterLabel, BorderLayout.NORTH);

        logTextArea = new JTextArea(10, 50);
        logTextArea.setEditable(false);
        frame.add(new JScrollPane(logTextArea), BorderLayout.SOUTH);

        redirectSystemOut(); // Log console output to the text area

        boardPanel = new JPanel(new GridLayout(10, 10)); // Create the game board
        frame.add(boardPanel, BorderLayout.CENTER);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        performStep(Move.UP);
                        break;
                    case KeyEvent.VK_A:
                        performStep(Move.LEFT);
                        break;
                    case KeyEvent.VK_S:
                        performStep(Move.DOWN);
                        break;
                    case KeyEvent.VK_D:
                        performStep(Move.RIGHT);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        frame.setFocusable(true);
        frame.setVisible(true);

        updateBoard(); // Render the board
        updateCoinCounter(); // Update coin counter
    }

    private void performStep(Move move) {
        if (game.isGameOver) return;

        game.moveCharacter(game.level.player, move);

        if (game.checkWin()) {
            logTextArea.append("You Win!\n");
            SwingUtilities.invokeLater(() -> showEndScreen(true));
            return;
        }

        if (game.checkLoss()) {
            logTextArea.append("Game Over!\n");
            SwingUtilities.invokeLater(() -> showEndScreen(false));
            return;
        }

        game.level.getEnemies().forEach(enemy -> {
            Move randomMove = Move.values()[game.random.nextInt(Move.values().length)];
            game.moveCharacter(enemy, randomMove);
        });

        updateBoard();
        updateCoinCounter();
    }

    private void updateBoard() {
        boardPanel.removeAll();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                BoardObject obj = game.level.getBoard().getObjectAt(i, j);
                JLabel cellLabel = new JLabel();
                cellLabel.setHorizontalAlignment(SwingConstants.CENTER);

                if (obj != null) {
                    cellLabel.setIcon(iconMap.get(obj.getClass()));
                }

                boardPanel.add(cellLabel);
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void updateCoinCounter() {
        coinCounterLabel.setText("Coins: " + game.coins); // Update the coin count display
    }

    private void redirectSystemOut() {
        PrintStream out = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                logTextArea.append(String.valueOf((char) b)); // Redirect console output to the log area
            }
        });
        System.setOut(out);
        System.setErr(out);
    }

    protected void showEndScreen(boolean playerWon) {
        frame.dispose(); // Close the main game window

        JFrame endFrame = new JFrame("Game Over");
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setLayout(new BorderLayout());
        endFrame.setSize(400, 200);

        JLabel messageLabel = new JLabel(
            "<html>" + (playerWon ? "Congratulations! You Won!" : "Game Over! You Lost.") +
            "<br/>Coins Collected: " + game.coins + "</html>", SwingConstants.CENTER
        );
        messageLabel.setFont(messageLabel.getFont().deriveFont(18.0f));
        endFrame.add(messageLabel, BorderLayout.CENTER);

        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.addActionListener(e -> {
            endFrame.dispose();
            SwingUtilities.invokeLater(App::run); // Reopen the main menu
        });
        endFrame.add(mainMenuButton, BorderLayout.SOUTH);

        endFrame.setVisible(true);
    }
}
