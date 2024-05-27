package src.views.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class CustomizedTextField extends JTextField {

  private ImageIcon backgroundImage;

  public CustomizedTextField() {
    super();
    init();
  }

  public CustomizedTextField(int columns) {
    super(columns);
    init();
  }

  public CustomizedTextField(String text) {
    super(text);
    init();
  }

  public CustomizedTextField(String text, int columns) {
    super(text, columns);
    init();
  }

  private void init() {
    // Load the background image
    backgroundImage = new ImageIcon("assets/images/text-field.png");

    // Set the text field to be opaque
    setOpaque(false);

    // Set the text field's border to be empty
    setBorder(BorderFactory.createEmptyBorder());

    // Set the text color to white
    setForeground(Color.WHITE);

    // Set the font and font size
    Font font = new Font("Arial", Font.PLAIN, 16); // Change "Arial" to the desired font and 16 to the desired font size
    setFont(font);
  }

  @Override
  protected void paintComponent(Graphics g) {
    // Draw the background image
    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

    // Draw the text
    super.paintComponent(g);
  }

  @Override
  public Insets getInsets() {
    // Add some padding to the text field
    return new Insets(0, 10, 0, 0);
  }

}