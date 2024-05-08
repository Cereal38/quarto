package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;
import src.views.utils.LangUtils;

public class LanguageButton extends JButton {

    private static final int LANG_EN = 0;
    private static final int LANG_FR = 1;
    private int lang = LANG_EN;
    private TranslatedString tooltip;

    public LanguageButton() {

        // Load icons
        ImageIcon frImg = ImageUtils.loadImage("fr.png", 40, 30);
        ImageIcon enImg = ImageUtils.loadImage("en.png", 35, 30);

        // Add style
        setIcon(frImg);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add action
        setActionCommand("Language");
        addActionListener(e -> {
            // Change icon
            if (lang == LANG_EN) {
                setIcon(enImg);
                LangUtils.setLang(LANG_FR);
            } else if (lang == LANG_FR) {
                setIcon(frImg);
                LangUtils.setLang(LANG_EN);
            }
            lang = (lang + 1) % 2;
        });

        // Add tooltip
        tooltip = new TranslatedString("languageButtonTooltip", this, true);
    }
}
