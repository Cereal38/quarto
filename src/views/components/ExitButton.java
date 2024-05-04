package src.views.components;

import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class ExitButton extends JButton {

  public ExitButton(ActionListener actionListener) {
    // Load
    ImageIcon exitImg = ImageUtils.loadImage("exit.png", 30, 30);

    // Add style
    setIcon(exitImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    // Add action
    setActionCommand("Quit");

    addActionListener(actionListener);
  }
}
