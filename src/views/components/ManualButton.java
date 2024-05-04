package src.views.components;

import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class ManualButton extends JButton {

  public ManualButton(ActionListener actionListener) {

    // Load icon
    ImageIcon bookImg = ImageUtils.loadImage("book.png", 30, 30);

    // Add style
    setIcon(bookImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    // Add action
    setActionCommand("Manual");

    addActionListener(actionListener);
  }
}
