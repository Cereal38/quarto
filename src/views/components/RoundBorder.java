package src.views.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

/**
 * A custom border with rounded corners.
 */
public class RoundBorder extends AbstractBorder {
    private Color color;
    private int thickness;
    private int radius;

    /**
     * Constructs a new RoundBorder with the specified color, thickness, and radius.
     *
     * @param color     the color of the border
     * @param thickness the thickness of the border
     * @param radius    the radius of the rounded corners
     */
    public RoundBorder(Color color, int thickness, int radius) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
    }

    /**
     * Paints the border with rounded corners.
     *
     * @param c      the component to paint on
     * @param g      the graphics context
     * @param x      the x-coordinate of the top-left corner
     * @param y      the y-coordinate of the top-left corner
     * @param width  the width of the border
     * @param height the height of the border
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        for (int i = 0; i < thickness; i++) {
            g.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, radius, radius);
        }
    }

    /**
     * Retrieves the insets of the border.
     *
     * @param c the component for which the border insets are calculated
     * @return the insets of the border
     */
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    /**
     * Retrieves the insets of the border.
     *
     * @param c      the component for which the border insets are calculated
     * @param insets the insets object to be modified
     * @return the insets of the border
     */
    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.bottom = insets.top = thickness;
        return insets;
    }
}
