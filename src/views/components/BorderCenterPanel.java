package src.views.components;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class allow to center the content of a panel in a border layout.
 */
public class BorderCenterPanel extends JPanel {

    int top;
    int left;
    int bottom;
    int right;
    private java.awt.Image backGroundImage;

    public BorderCenterPanel(JPanel content, int marginTop, int marginLeft, int marginBottom, int marginRight) {
        this.top = marginTop;
        this.left = marginLeft;
        this.bottom = marginBottom;
        this.right = marginRight;
        backGroundImage = new ImageIcon(getClass().getResource("/assets/images/bg-board.png")).getImage();

        setLayout(new BorderLayout());

        // South
        JPanel southPanel = new JPanel();
        JLabel southMargin = new JLabel("");
        southMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, bottom, 0));
        southPanel.add(southMargin);
        add(southPanel, BorderLayout.SOUTH);
        southPanel.setOpaque(false);

        // North
        JPanel northPanel = new JPanel();
        JLabel northMargin = new JLabel("");
        northMargin.setBorder(BorderFactory.createEmptyBorder(top, 0, 0, 0));
        northPanel.add(northMargin);
        add(northPanel, BorderLayout.NORTH);
        northPanel.setOpaque(false);

        // West
        JPanel westPanel = new JPanel();
        JLabel westMargin = new JLabel("");
        westMargin.setBorder(BorderFactory.createEmptyBorder(0, left, 0, 0));
        westPanel.add(westMargin);
        add(westPanel, BorderLayout.WEST);
        westPanel.setOpaque(false);
        // East
        JPanel eastPanel = new JPanel();
        JLabel eastMargin = new JLabel("");
        eastMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, right));
        eastPanel.add(eastMargin);
        add(eastPanel, BorderLayout.EAST);
        eastPanel.setOpaque(false);
        // Content
        add(content, BorderLayout.CENTER);

        // Resize listener
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                northMargin.setBorder(BorderFactory.createEmptyBorder(top, 0, 0, 0));
                westMargin.setBorder(BorderFactory.createEmptyBorder(0, left, 0, 0));
                southMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, bottom, 0));
                eastMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, right));
            }
        });
    }

    public BorderCenterPanel(JScrollPane content, int marginTop, int marginLeft, int marginBottom, int marginRight) {
        this.top = marginTop;
        this.left = marginLeft;
        this.bottom = marginBottom;
        this.right = marginRight;
        backGroundImage = new ImageIcon(getClass().getResource("/assets/images/bg-board.png")).getImage();

        setLayout(new BorderLayout());

        // South
        JPanel southPanel = new JPanel();
        JLabel southMargin = new JLabel("");
        southMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, bottom, 0));
        southPanel.add(southMargin);
        add(southPanel, BorderLayout.SOUTH);
        southPanel.setOpaque(false);
        // North
        JPanel northPanel = new JPanel();
        JLabel northMargin = new JLabel("");
        northMargin.setBorder(BorderFactory.createEmptyBorder(top, 0, 0, 0));
        northPanel.add(northMargin);
        add(northPanel, BorderLayout.NORTH);
        northPanel.setOpaque(false);
        // West
        JPanel westPanel = new JPanel();
        JLabel westMargin = new JLabel("");
        westMargin.setBorder(BorderFactory.createEmptyBorder(0, left, 0, 0));
        westPanel.add(westMargin);
        add(westPanel, BorderLayout.WEST);
        westPanel.setOpaque(false);
        // East
        JPanel eastPanel = new JPanel();
        JLabel eastMargin = new JLabel("");
        eastMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, right));
        eastPanel.add(eastMargin);
        add(eastPanel, BorderLayout.EAST);
        eastPanel.setOpaque(false);
        // Content
        add(content, BorderLayout.CENTER);

        // Resize listener
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                northMargin.setBorder(BorderFactory.createEmptyBorder(top, 0, 0, 0));
                westMargin.setBorder(BorderFactory.createEmptyBorder(0, left, 0, 0));
                southMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, bottom, 0));
                eastMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, right));
            }
        });
    }

    public void setMargins(int marginTop, int marginLeft, int marginBottom, int marginRight) {
        this.top = marginTop;
        this.left = marginLeft;
        this.bottom = marginBottom;
        this.right = marginRight;
    }
    @Override
    protected void paintComponent(java.awt.Graphics g) {
      super.paintComponent(g);
      // Draw background image
      g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
