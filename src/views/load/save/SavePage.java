package src.views.load.save;

import src.views.components.CustomizedTextField;
import src.views.components.TranslatedLabel;
import src.views.load.save.LoadPage;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

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
    private Image backGroundImage;

    public SavePage(LoadPage l) {
        loadSavePage = l;
        backGroundImage = new ImageIcon(getClass().getResource("/assets/images/bg-board.png")).getImage();
        // Set up the layout
        setLayout(new BorderLayout());

        // Create a panel for the input area
        JPanel inputPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw background gradient
                Graphics2D g2d = (Graphics2D) g.create();
                GradientPaint gradient = new GradientPaint(0, 0, new Color(230, 255, 230), 0, getHeight(), new Color(200, 255, 200));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                // Draw background image with transparency
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f)); // Adjust transparency as needed
                g2d.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        };
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title label
        TranslatedLabel titleLabel = new TranslatedLabel("save-page");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24)); // Modern font
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.BLACK); // Dodger Blue color
        titleLabel.setOpaque(false); // Make the label transparent
        add(titleLabel);



        nameField = new CustomizedTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Modern font
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Modern font
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(30, 144, 255)); // Dodger Blue color
        saveButton.setFocusPainted(false); // Remove button focus border

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Modern font
        messageLabel.setForeground(Color.RED);

        // Add action listener to the save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if (!name.isEmpty()) {
                    try {
                        EventsHandler.getController().saveGame(name);
                        loadSavePage.refreshPage();
                        EventsHandler.hideDialog();
                        EventsHandler.navigate("MainMenu");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    messageLabel.setText("Name cannot be empty.");
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
        messagePanel.setBackground(new Color(230, 255, 230)); // Light green background
        messagePanel.add(messageLabel);
        add(messagePanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loadSavePage.getWoodTexture(), 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Save Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        // Create and add the SavePage panel
        SavePage savePage = new SavePage(new LoadPage());
        frame.add(savePage);

        // Make the frame visible
        frame.setVisible(true);
    }
}
