package src.views.load.save;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.CustomizedTextField;
import src.views.components.ImageThemed;
import src.views.components.TranslatedButton;
import src.views.components.TranslatedLabel;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

public class SavePage extends JPanel implements ThemeListener {

  private CustomizedTextField nameField;
  private TranslatedButton saveButton;
  private JLabel messageLabel;
  private LoadPage loadSavePage;
  private ImageThemed bgImage = new ImageThemed("squared-background.png");
  private ImageThemed topbarImage = new ImageThemed("flat.png");

  public SavePage(LoadPage l) {
    ThemeUtils.addThemeListener(this);

    loadSavePage = l;
    // Set up the layout
    setLayout(new BorderLayout());

    // Create a panel for the input area
    JPanel inputPanel = new JPanel(new GridBagLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background gradient
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
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
    saveButton = new TranslatedButton("save");
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
    inputPanel.add(new TranslatedLabel("enter-name"), gbc);

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
    g.drawImage(topbarImage.getImage(), 0, 0, getWidth(), getHeight(), this);
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

  @Override
  public void updatedTheme() {
    repaint();
  }
}
