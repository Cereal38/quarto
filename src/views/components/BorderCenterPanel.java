package src.views.components;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class allow to center the content of a panel in a border layout.
 */
public class BorderCenterPanel extends JPanel {

  int top;
  int left;
  int bottom;
  int right;

  public BorderCenterPanel(JPanel content, int marginTop, int marginLeft, int marginBottom, int marginRight) {
    this.top = marginTop;
    this.left = marginLeft;
    this.bottom = marginBottom;
    this.right = marginRight;

    setLayout(new BorderLayout());

    // South
    JPanel southPanel = new JPanel();
    JLabel southMargin = new JLabel("");
    southMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, bottom, 0));
    southPanel.add(southMargin);
    add(southPanel, BorderLayout.SOUTH);

    // North
    JPanel northPanel = new JPanel();
    JLabel northMargin = new JLabel("");
    northMargin.setBorder(BorderFactory.createEmptyBorder(top, 0, 0, 0));
    northPanel.add(northMargin);
    add(northPanel, BorderLayout.NORTH);

    // West
    JPanel westPanel = new JPanel();
    JLabel westMargin = new JLabel("");
    westMargin.setBorder(BorderFactory.createEmptyBorder(0, left, 0, 0));
    westPanel.add(westMargin);
    add(westPanel, BorderLayout.WEST);

    // East
    JPanel eastPanel = new JPanel();
    JLabel eastMargin = new JLabel("");
    eastMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, right));
    eastPanel.add(eastMargin);
    add(eastPanel, BorderLayout.EAST);

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

  public void setMargins(int top, int left, int bottom, int right) {
    this.top = top;
    this.left = left;
    this.bottom = bottom;
    this.right = right;
  }
}
