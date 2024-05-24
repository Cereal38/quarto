package src.views.components;

import java.awt.Graphics;
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
  }

  @Override
  protected void paintComponent(Graphics g) {
    // Draw the background image
    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

    // Draw the text
    super.paintComponent(g);
  }

}
