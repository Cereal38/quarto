package src.views.components;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A panel that centers its content in a 3x3 grid layout.
 * <p>
 * This panel arranges its content in a 3x3 grid layout, with the specified content panel placed at the center.
 */
public class GridCenterPanel extends JPanel {

  /**
   * Constructs a new GridCenterPanel with the specified content panel.
   *
   * @param content the panel to be placed at the center of the grid
   */
  public GridCenterPanel(JPanel content) {
    setLayout(new GridLayout(3, 3));

    add(new JLabel(""));
    add(new JLabel(""));
    add(new JLabel(""));
    add(new JLabel(""));
    add(content);
    add(new JLabel(""));
    add(new JLabel(""));
    add(new JLabel(""));
    add(new JLabel(""));
  }

}
