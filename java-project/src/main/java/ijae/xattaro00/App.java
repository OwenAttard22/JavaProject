package ijae.xattaro00;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class App {
    public static void run() {
        SwingUtilities.invokeLater(App::showLevelSelectionMenu);
    }

    // Show a menu to select a level to play or create a new one
    private static void showLevelSelectionMenu() {
        JFrame menuFrame = new JFrame("Main Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLayout(new GridLayout(0, 1));
        menuFrame.setSize(400, 300);

        JLabel title = new JLabel("Main Menu");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        menuFrame.add(title);

        // Button to select and play a level
        JButton playButton = new JButton("Play Level");
        playButton.addActionListener(e -> {
            menuFrame.dispose(); // Close the main menu window
            showLevelList(menuFrame); // Show level selection menu
        });
        menuFrame.add(playButton);

        // Button to create a new level
        JButton createLevelButton = new JButton("Create Level");
        createLevelButton.addActionListener(e -> {
            menuFrame.dispose(); // Close the main menu
            runLevelCreatorCLI(); // Launch CLI for LevelCreator
        });
        menuFrame.add(createLevelButton);

        menuFrame.setVisible(true);
    }

    // Show the level selection menu
    private static void showLevelList(JFrame menuFrame) {
        JFrame levelFrame = new JFrame("Level Selection");
        levelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        levelFrame.setLayout(new GridLayout(0, 1));
        levelFrame.setSize(400, 300);

        JLabel title = new JLabel("Select a Level to Play");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        levelFrame.add(title);

        File saveDirectory = new File("./");
        File[] savedFiles = saveDirectory.listFiles((dir, name) -> name.endsWith(".dat")); // Get all files ending in .dat

        if (savedFiles == null || savedFiles.length == 0) {
            JOptionPane.showMessageDialog(levelFrame, "No saved levels found.", "Error", JOptionPane.ERROR_MESSAGE);
            levelFrame.dispose();
            showLevelSelectionMenu(); // Return to the main menu if no levels are found
            return;
        }

        for (File file : savedFiles) {
            JButton levelButton = new JButton(file.getName());
            levelButton.addActionListener(e -> {
                Level level = Level.loadLevelFromFile(file.getName());
                if (level != null) {
                    levelFrame.dispose();
                    Game game = new Game(level);
                    SwingUtilities.invokeLater(() -> new GameGUI(game));
                } else {
                    JOptionPane.showMessageDialog(levelFrame, "Failed to load level: " + file.getName(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            levelFrame.add(levelButton);
        }

        levelFrame.setVisible(true);
    }

    // Run the LevelCreator CLI and return to the main menu
    private static void runLevelCreatorCLI() {
        try {
            // Run the LevelCreator in the CLI
            LevelCreator.main(new String[] {});

            // After completion, return to the main menu
            SwingUtilities.invokeLater(App::run);
        } catch (Exception e) {
            System.err.println("Error running LevelCreator: " + e.getMessage());
        }
    }
}
