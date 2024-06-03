package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;
import src.views.utils.LangUtils;

/**
 * A button to switch between languages.
 */
public class LanguageButton extends JButton {
  private static final int LANG_EN = 0;
  private static final int LANG_FR = 1;
  private int lang = LANG_EN;
  private boolean isLightTheme = true;
  private TranslatedString tooltip;

  // Load icons for light and dark themes
  private ImageIcon frImg = ImageUtils.loadImage("fr.png", 40, 30);
  private ImageIcon enImg = ImageUtils.loadImage("en.png", 35, 30);
  private ImageIcon frWhiteImg = ImageUtils.loadImage("fr-white.png", 40, 30);
  private ImageIcon enWhiteImg = ImageUtils.loadImage("en-white.png", 35, 30);

  /**
   * Constructs a new LanguageButton.
   */
  public LanguageButton() {
    // Set the default icon based on the current theme
    updateIcon(isLightTheme);

    // Add style
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);
    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Add action to switch languages
    addActionListener(e -> switchLanguage());

    // Add tooltip
    tooltip = new TranslatedString("languageButtonTooltip", this, true);
  }

  private void switchLanguage() {
    LangUtils.setLang(lang == LANG_EN ? LANG_FR : LANG_EN);
    lang = (lang + 1) % 2;
    updateIcon(isLightTheme);
  }

  /**
   * Updates the icon of the button based on the theme.
   *
   * @param isLightTheme true if the theme is light, false otherwise
   */
  public void updateIcon(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;

    if (lang == LANG_EN) {
      setIcon(isLightTheme ? frImg : frWhiteImg);
    } else {
      setIcon(isLightTheme ? enImg : enWhiteImg);
    }
  }

  /**
   * Changes the theme of the button.
   *
   * @param isLightTheme true if the theme is light, false otherwise
   */
  public void changeTheme(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;
    updateIcon(isLightTheme);
  }
}
