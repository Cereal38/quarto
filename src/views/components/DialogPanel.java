package src.views.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A custom JPanel class that represents a dialog panel. This panel is used to
 * display a custom dialog on top of the main frame.
 */

public class DialogPanel extends JPanel {

  private JPanel dialog;
  private boolean closeable = true;

  public DialogPanel(JFrame main) {

    // Setup the background component (main component)
    setLayout(new GridBagLayout());
    setBackground(new Color(0, 0, 0, 0.5f));
    setBounds(0, 0, main.getWidth(), main.getHeight());

    // Setup the dialog
    dialog = new JPanel();
    dialog.setLayout(new BorderLayout());
    dialog.setPreferredSize(new Dimension(main.getWidth() / 2, main.getHeight() / 2));
    dialog.setBackground(Color.RED);

    // Add the dialog to the container
    add(dialog);

    // Allow to resize the menu when the main frame is resized
    main.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        setBounds(0, 0, main.getWidth(), main.getHeight());
        dialog.setPreferredSize(new Dimension(main.getWidth() / 2, main.getHeight() / 2));
      }
    });

    // Close the dialog when clicking outside of it if it is closeable
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (!dialog.getBounds().contains(e.getPoint()) && closeable) {
          setVisible(false);
        }
      }
    });
  }

  /**
   * Sets the content of the dialog panel.
   * 
   * @param content the panel representing the content of the dialog
   */
  public void setContent(JPanel content) {
    dialog.removeAll();
    dialog.add(content, BorderLayout.CENTER);
    dialog.revalidate();
    dialog.repaint();
  }

  /**
   * Sets the visibility of the dialog panel.
   * 
   * @param visible the visibility of the dialog panel
   */
  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    dialog.setVisible(visible);
  }

  /**
   * Set if the dialog panel is closeable by clicking outside of it.
   * 
   * @param closeable the closeable property of the dialog panel
   */
  public void setCloseable(boolean closeable) {
    this.closeable = closeable;
  }

}