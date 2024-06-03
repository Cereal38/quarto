/**
 * A text field with a customized appearance.
 */
package src.views.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import src.views.listeners.ThemeListener;
import src.views.utils.ThemeUtils;

public class CustomizedTextField extends JTextField implements ThemeListener {

  private ImageThemed bgImage = new ImageThemed("text-field.png");

  /**
   * Constructs a CustomizedTextField with no initial text and columns.
   */
  public CustomizedTextField() {
    super();
    init();
  }

  /**
   * Constructs a CustomizedTextField with the specified number of columns.
   *
   * @param columns the number of columns to use to calculate the preferred width; if columns is set to zero, the text field's preferred width will be whatever naturally results from the text it contains
   */
  public CustomizedTextField(int columns) {
    super(columns);
    init();
  }

  /**
   * Constructs a CustomizedTextField initialized with the specified text.
   *
   * @param text the text to be displayed, or null
   */
  public CustomizedTextField(String text) {
    super(text);
    init();
  }

  /**
   * Constructs a CustomizedTextField with the specified text and number of columns.
   *
   * @param text the text to be displayed, or null
   * @param columns the number of columns to use to calculate the preferred width; if columns is set to zero, the text field's preferred width will be whatever naturally results from the text it contains
   */
  public CustomizedTextField(String text, int columns) {
    super(text, columns);
    init();
  }

  /**
   * Initializes the CustomizedTextField with custom settings.
   * <p>
   * This method sets the text field to be opaque, removes any border, sets the text color to white,
   * sets a specific font and font size, and adds a key listener to prevent the user from typing spaces.
   */
  private void init() {
    ThemeUtils.addThemeListener(this);

    // Set the text field to be opaque
    setOpaque(false);

    // Set the text field's border to be empty
    setBorder(BorderFactory.createEmptyBorder());

    // Set the text color to white
    setForeground(Color.WHITE);

    // Set the font and font size
    Font font = new Font("Arial", Font.PLAIN, 16); // Change "Arial" to the desired font and 16 to the desired font size
    setFont(font);

    // Add a key listener to prevent the user from typing spaces
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == ' ') {
          e.consume();
        }
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    // Draw the background image
    g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);

    // Draw the text
    super.paintComponent(g);
  }

  @Override
  public Insets getInsets() {
    // Add some padding to the text field
    return new Insets(0, 10, 0, 0);
  }

  @Override
  public void updatedTheme() {
    repaint();
  }

}