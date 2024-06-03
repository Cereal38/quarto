package src.views.main.menu;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import src.views.utils.ImageUtils;

public class LeftPanel extends JPanel {
    private Image backgroundImage;

    public LeftPanel() {
        backgroundImage = ImageUtils.loadImage("woodSlots.png", 50, 50).getImage();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}