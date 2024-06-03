/**
 * A JPanel subclass that allows centering the content within a border layout with customizable margins.
 */
package src.views.components;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
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

  /**
   * Constructs a BorderCenterPanel with the specified content panel and margins.
   *
   * @param content      the content panel to be centered
   * @param marginTop    the top margin
   * @param marginLeft   the left margin
   * @param marginBottom the bottom margin
   * @param marginRight  the right margin
   */
  public BorderCenterPanel(JPanel content, int marginTop, int marginLeft, int marginBottom, int marginRight) {

    this.top = marginTop;
    this.left = marginLeft;
    this.bottom = marginBottom;
    this.right = marginRight;

    setLayout(new BorderLayout());
    setOpaque(false);

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

  /**
   * Constructs a BorderCenterPanel with the specified content scroll pane and margins.
   *
   * @param content      the content scroll pane to be centered
   * @param marginTop    the top margin
   * @param marginLeft   the left margin
   * @param marginBottom the bottom margin
   * @param marginRight  the right margin
   */
  public BorderCenterPanel(JScrollPane content, int marginTop, int marginLeft, int marginBottom, int marginRight) {

    this.top = marginTop;
    this.left = marginLeft;
    this.bottom = marginBottom;
    this.right = marginRight;

    setLayout(new BorderLayout());
    setOpaque(false);

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

  /**
   * Sets the margins for this BorderCenterPanel.
   *
   * @param marginTop    the top margin
   * @param marginLeft   the left margin
   * @param marginBottom the bottom margin
   * @param marginRight  the right margin
   */
  public void setMargins(int marginTop, int marginLeft, int marginBottom, int marginRight) {
    this.top = marginTop;
    this.left = marginLeft;
    this.bottom = marginBottom;
    this.right = marginRight;
  }

}
