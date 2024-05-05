package src.views.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageUtils {
    public static ImageIcon loadImage(String nom, int width, int height) {
        try {
            // print ls
            String path = "assets/images/" + nom;
            ImageIcon icon = new ImageIcon(ImageIO.read(new File(path)));
            Image newIcon = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon = new ImageIcon(newIcon);
            return icon;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e);
            return null;
        }
    }

    public static JButton createButtonFromImage(ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);

        return button;
    }
}
