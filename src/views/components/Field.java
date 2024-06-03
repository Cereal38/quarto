package src.views.components;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.listeners.ThemeListener;
import src.views.utils.ThemeUtils;

/**
 * A customizable field component.
 * <p>
 * This component provides a customizable field that can display text messages.
 * It supports changing the appearance based on the current theme and toggling
 * the visibility of the text message.
 */

public class Field extends JPanel implements ThemeListener {

  private JLabel textLbl;
  private ImageThemed bgImageOn = new ImageThemed("field-on.png");
  private ImageThemed bgImageOff = new ImageThemed("field-off.png");
  private boolean isOn;

  /**
   * Constructs a new Field with the specified message and initial visibility state.
   *
   * @param message the message to display in the field
   * @param isOn    a boolean indicating whether the field is initially visible
   */
  public Field(String message, boolean isOn) {
    ThemeUtils.addThemeListener(this);

    this.isOn = isOn;

    setLayout(new BorderLayout());

    // Add text only if the field is on
    textLbl = new JLabel(message, JLabel.CENTER);
    textLbl.setFont(new Font("Arial", Font.BOLD, 16));
    add(textLbl, BorderLayout.CENTER);
    textLbl.setVisible(isOn);
  }

  /**
   * Sets the visibility state of the field.
   *
   * @param isOn a boolean indicating whether the field should be visible
   */
  public void setOn(boolean isOn) {
    this.isOn = isOn;

    // Show or hide text
    textLbl.setVisible(isOn);

    repaint();
  }

  /**
   * Sets the text message to display in the field.
   *
   * @param text the text message to display
   */
  public void setText(String text) {
    textLbl.setText(text);
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    if (isOn) {
      g.drawImage(bgImageOn.getImage(), 0, 0, getWidth(), getHeight(), this);
    } else {
      g.drawImage(bgImageOff.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
  }

  @Override
  public void updatedTheme() {
    repaint();
  }
}
