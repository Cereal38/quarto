package src.views.load.save;

import src.views.components.CustomizedTextField;
import src.views.utils.EventsHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SavePage extends JPanel {

    private CustomizedTextField nameField;
    private JButton saveButton;
    private JLabel messageLabel;
    private LoadPage loadSavePage;

    public SavePage(LoadPage l) {
        this.loadSavePage = l;
        // Set up the layout
        setLayout(new BorderLayout());

        // Create a panel for the input area
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title label
        JLabel titleLabel = new JLabel("Save Your Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create UI components
        nameField = new CustomizedTextField(20);
        saveButton = new JButton("Save");
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setVerticalAlignment(JLabel.CENTER);

        // Add action listener to the save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if (!name.isEmpty()) {
                    try {
                        EventsHandler.getController().saveGame(name);
                        loadSavePage.refreshPage();
                        EventsHandler.navigate("MainMenu");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    messageLabel.setText("Name cannot be empty.");
                    messageLabel.setForeground(Color.RED);
                }
            }
        });

        // Add components to the input panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Enter Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(saveButton, gbc);

        // Add components to the main panel
        add(titleLabel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new GridBagLayout());
        messagePanel.add(messageLabel);
        add(messagePanel, BorderLayout.SOUTH);
    }

//    public static void main(String[] args) {
//        // Create the frame
//        JFrame frame = new JFrame("Save Page");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 200);
//        frame.setLocationRelativeTo(null);
//
//        // Create and add the SavePage panel
//        SavePage savePage = new SavePage();
//        frame.add(savePage);
//
//        // Make the frame visible
//        frame.setVisible(true);
//    }
}
