package src.views.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A panel to display snackbar messages at the bottom of a frame.
 */
public class SnackbarPanel extends JPanel {

  private JPanel snackbar;
  private TranslatedLabel messageLabel;
  private Timer timer;

  /**
   * Constructs a new SnackbarPanel with the specified main frame.
   *
   * @param main the main JFrame to which the snackbar is attached
   */
  public SnackbarPanel(JFrame main) {

    // Setup the background component (main component)
    setLayout(new BorderLayout());
    setOpaque(false);
    setBounds(0, 0, main.getWidth(), main.getHeight());

    // Setup the snackbar
    snackbar = new JPanel();
    snackbar.setLayout(new BorderLayout());
    snackbar.setBackground(Color.WHITE);

    messageLabel = new TranslatedLabel("");
    messageLabel.setForeground(Color.BLACK);
    snackbar.add(messageLabel, BorderLayout.CENTER);

    // Setup the wrapper
    JPanel wrapper = new JPanel();
    wrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
    wrapper.setOpaque(false);

    // Add margin to the snackbar
    setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 40));
    messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Add the snackbar to the container
    wrapper.add(snackbar);
    add(wrapper, BorderLayout.SOUTH);

    // Close the snackbar when clicking on it
    snackbar.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        setVisible(false);
      }
    });

    // Setup the timer
    timer = new Timer(1500, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
      }
    });
    timer.setRepeats(false);

    // Update snackbar position when main frame is resized
    main.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        setBounds(0, 0, main.getWidth(), main.getHeight());
      }
    });
  }

  /**
   * Sets the message of the snackbar panel and starts the timer.
   *
   * @param key the key representing the message of the snackbar
   */
  public void setMessage(String key) {
    messageLabel.setKey(key);
    snackbar.revalidate();
    snackbar.repaint();
    setVisible(true);
    timer.start();
  }

  /**
   * Sets the visibility of the snackbar panel.
   *
   * @param visible the visibility of the snackbar panel
   */
  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    snackbar.setVisible(visible);
  }

}
