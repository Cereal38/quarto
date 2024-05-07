package src.views.players.names;

import src.views.components.*;
import src.views.listeners.TextFieldChangeListener;
import src.views.utils.EventsHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
TODO :
    1. MAKE IT RESPONSIVE
    2. TRANSLATION
 */

public class ChoosePlayers extends JPanel {

    private CustomTextField player1TextField;
    private CustomTextField player2TextField;
    private JComboBox<String> difficultyComboBox1;
    private JComboBox<String> difficultyComboBox2;
    private TranslatedButton startButton;
    private boolean player1IsAI = false;
    private boolean player2IsAI = false;

    public ChoosePlayers() {
        // Set BorderLayout for the ChoosePlayers panel
        setLayout(new BorderLayout());

        // Create a title label
        TranslatedLabel titleLabel = new TranslatedLabel("enter-players-names");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Create text fields for player names
        player1TextField = new CustomTextField(new TranslatedString("player1"), "Enter Player 1's name");
        player2TextField = new CustomTextField(new TranslatedString("player2"), "Enter Player 2's name");

        // Create difficulty combo boxes for each player
        difficultyComboBox1 = createDifficultyComboBox();
        difficultyComboBox2 = createDifficultyComboBox();

        // Initially hide combo boxes
        difficultyComboBox1.setVisible(false);
        difficultyComboBox2.setVisible(false);

        // Create toggle buttons for AI/Player
        createToggleButton(player1TextField, difficultyComboBox1, 1);
        createToggleButton(player2TextField, difficultyComboBox2, 2);

        // Create a panel to hold the title label and text fields
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Create a panel to hold the text fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 4));
        inputPanel.add(player1TextField);
        inputPanel.add(player2TextField);

        GridCenterPanel center = new GridCenterPanel(inputPanel);
        // Create Start button (initially hidden)
        startButton = new TranslatedButton("start");
        startButton.addActionListener(e -> {
            EventsHandler.navigate("GameBoard");
        });
        startButton.setVisible(false);

        // Add panels to the ChoosePlayers panel
        add(topPanel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);

        // Add document listeners to update Start button visibility
        player1TextField.getDocument().addDocumentListener(new TextFieldChangeListener(this));
        player2TextField.getDocument().addDocumentListener(new TextFieldChangeListener(this));
    }

    private void createToggleButton(CustomTextField textField, JComboBox<String> difficultyComboBox, int numPlayer) {
        JButton toggleButton = new JButton("Make AI");

        toggleButton.addActionListener(new ActionListener() {
            private boolean isAI = false; // Track current state (AI or Player)

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAI) {
                    // Convert the associated text field to AI
                    textField.setText(new TranslatedString("ai"+numPlayer));
                    textField.setEditable(false); // Disable editing
                    toggleButton.setText("Make Player"); // Change button text
                    textField.add(difficultyComboBox);
                    difficultyComboBox.setVisible(true); // Show difficulty combo box
                    isAI = true; // Update state to AI
                } else {
                    // Convert the associated text field back to Player
                    textField.setText(new TranslatedString("")); // Clear AI designation
                    textField.setEditable(true); // Enable editing
                    toggleButton.setText("Make AI"); // Change button text
                    textField.remove(difficultyComboBox);
                    difficultyComboBox.setVisible(false); // Hide difficulty combo box

                    isAI = false; // Update state to Player
                }
                Container parent = textField.getParent();
                if (parent != null) {
                    parent.revalidate(); // Revalidate layout
                    parent.repaint(); // Repaint to reflect changes
                }
            }
        });

        // Add the toggle button to the panel alongside the text field
        textField.add(toggleButton);
    }

    private JComboBox<String> createDifficultyComboBox() {
        String[] difficultyLevels = {"Easy", "Medium", "Hard"}; // Define difficulty options
        JComboBox<String> comboBox = new JComboBox<>(difficultyLevels); // Create combo box
        comboBox.setSelectedIndex(1); // Set default selection to "Medium" (index 1)
        return comboBox;
    }

    public void updateStartButtonVisibility() {
        boolean bothPlayersFilled = !player1TextField.getInputText().isEmpty() && !player2TextField.getInputText().isEmpty();
        startButton.setVisible(bothPlayersFilled);
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         JFrame frame = new JFrame("Choose Players");
    //         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //         ChoosePlayers choosePlayersPanel = new ChoosePlayers();
    //         frame.getContentPane().add(choosePlayersPanel);

    //         frame.pack();
    //         frame.setVisible(true);
    //     });
    // }
}