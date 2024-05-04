package src.views.components;

import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class MusicButton extends JButton {

  public MusicButton(ActionListener actionListener) {

    // Load icons
    ImageIcon musicOnImg = ImageUtils.loadImage("music-on.png", 30, 30);
    ImageIcon musicOffImg = ImageUtils.loadImage("music-off.png", 30, 30);

    // Add style
    setIcon(musicOnImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    // Add action
    setActionCommand("Music");
    addActionListener(actionListener);

  }

}
