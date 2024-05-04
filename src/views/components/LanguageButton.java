package src.views.components;

import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;
import src.views.utils.LangUtils;

public class LanguageButton extends JButton {

  private int lang;

  private static final int LANG_EN = 0;
  private static final int LANG_FR = 1;

  public LanguageButton(ActionListener actionListener) {
    lang = 0;

    // Load icons
    ImageIcon frImg = ImageUtils.loadImage("fr.png", 30, 30);
    ImageIcon enImg = ImageUtils.loadImage("en.png", 30, 30);

    // Add style
    setIcon(enImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    // Add action
    setActionCommand("Language");
    addActionListener(e -> {
      // Change icon
      if (lang == LANG_EN) {
        setIcon(frImg);
        LangUtils.setLang(LANG_EN);
      } else if (lang == LANG_FR) {
        setIcon(enImg);
        LangUtils.setLang(LANG_FR);
      }
      lang = (lang + 1) % 2;
      // Call the main action listener
      actionListener.actionPerformed(e);
    });
  }
}
