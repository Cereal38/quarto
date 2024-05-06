package src.views;

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
    private int playerTextFieldCount = 0;

    public ChoosePlayers() {
        // Set BorderLayout for the ChoosePlayers panel
        setLayout(new BorderLayout());

        // Create a title label
        //JLabel titleLabel = new JLabel("Enter Players' Names");
        TranslatedLabel titleLabel = new TranslatedLabel("enter-players-names");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Create text fields for player names
        player1TextField = new CustomTextField(new TranslatedString("player1"), "Enter Player 1's name");
        player2TextField = new CustomTextField(new TranslatedString("player2"), "Enter Player 2's name");

        // Create buttons for adding players or AI
        TranslatedButton addPlayerButton = new TranslatedButton("add-player");
        TranslatedButton addAIButton = new TranslatedButton("add-ai");

        // Style the buttons
        addPlayerButton.setBackground(Color.GREEN);
        addAIButton.setBackground(Color.CYAN);

        // Add action listeners to the buttons
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerTextFieldCount == 0) {
                    // Show text field for player 1 name
                    player1TextField.setVisible(true);
                    createToggleButton(player1TextField, false, 1, difficultyComboBox1); // Create "Make AI" button for player 1
                    playerTextFieldCount = 1;
                } else if (playerTextFieldCount == 1) {
                    // Show text field for player 2 name
                    player2TextField.setVisible(true);
                    createToggleButton(player2TextField, false, 2, difficultyComboBox2); // Create "Make AI" button for player 2
                    playerTextFieldCount = 2;
                }
            }
        });

        addAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerTextFieldCount == 0) {
                    player1TextField.setText(new TranslatedString("ai-1"));
                    player1TextField.setEditable(false);
                    player1TextField.setVisible(true);
                    createToggleButton(player1TextField, true, 1, difficultyComboBox1); // Create "Make AI" button for player 1
                    player1TextField.add(difficultyComboBox1); // Add difficulty combo box
                    difficultyComboBox1.setVisible(true);
                    playerTextFieldCount = 1; // Now one player is added
                } else if (playerTextFieldCount == 1) {
                    player2TextField.setText(new TranslatedString("ai-2"));
                    player2TextField.setEditable(false);
                    player2TextField.setVisible(true);
                    createToggleButton(player2TextField, true, 2, difficultyComboBox2); // Create "Make AI" button for player 2
                    player2TextField.add(difficultyComboBox2); // Add difficulty combo box
                    difficultyComboBox2.setVisible(true);
                    playerTextFieldCount = 2; // Now both players are added
                }
            }
        });

        // Create difficulty combo boxes for each player
        difficultyComboBox1 = createDifficultyComboBox();
        difficultyComboBox2 = createDifficultyComboBox();

        // Create a panel to hold the title label and buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(addPlayerButton);
        buttonPanel.add(addAIButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create a panel to hold the text fields and AI labels
        JPanel inputPanel = new JPanel(new GridLayout(2, 4));

        // Add components to the inputPanel
        inputPanel.add(player1TextField);
        inputPanel.add(player2TextField);


        GridCenterPanel center = new GridCenterPanel(inputPanel);


        // Initially hide text fields
        player1TextField.setVisible(false);
        player2TextField.setVisible(false);

        // Add panels to the ChoosePlayers panel
        add(topPanel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        // Create Start button (initially hidden)
        startButton = new TranslatedButton("start");
        startButton.addActionListener(e -> {
            EventsHandler.navigate("GameBoard");
        });
        add(startButton, BorderLayout.PAGE_END);
        startButton.setVisible(false);

        player1TextField.getDocument().addDocumentListener(new TextFieldChangeListener(this));
        player2TextField.getDocument().addDocumentListener(new TextFieldChangeListener(this));
    }

    private void createToggleButton(CustomTextField textField, boolean isAIInitially, int playerNumber, JComboBox<String> difficultyComboBox) {
        JButton toggleButton = new JButton(isAIInitially ? "Make Player" : "Make AI");

        toggleButton.addActionListener(new ActionListener() {
            private boolean isAI = isAIInitially; // Track current state (AI or Player)

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAI) {
                    // Convert the associated text field to AI
                    textField.setText(new TranslatedString("ai-" + playerNumber));
                    textField.setEditable(false); // Disable editing
                    toggleButton.setText("Make Player"); // Change button text
                    textField.add(difficultyComboBox); // Add difficulty combo box
                    difficultyComboBox.setVisible(true); // Show difficulty combo box
                    isAI = true; // Update state to AI
                } else {
                    // Convert the associated text field back to Player
                    textField.setText(new TranslatedString("")); // Clear AI designation
                    textField.setEditable(true); // Enable editing
                    toggleButton.setText("Make AI"); // Change button text
                    textField.remove(difficultyComboBox); // Remove difficulty combo box
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Choose Players");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ChoosePlayers choosePlayersPanel = new ChoosePlayers();
            frame.getContentPane().add(choosePlayersPanel);

            frame.pack();
            frame.setVisible(true);
        });
    }
}