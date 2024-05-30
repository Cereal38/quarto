package src.views.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class SnackbarPanel extends JPanel {

  private JPanel snackbar;
  private TranslatedLabel messageLabel;
  private Timer timer;

  public SnackbarPanel(JFrame main) {

    // Setup the background component (main component)
    setLayout(new BorderLayout());
    setOpaque(false);
    setBounds(0, 0, main.getWidth(), main.getHeight());

    // Setup the snackbar
    snackbar = new JPanel();
    snackbar.setLayout(new BorderLayout());
    snackbar.setPreferredSize(new Dimension(main.getWidth() / 4, main.getHeight() / 10));
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
    // snackbar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
        setBounds(0, main.getHeight() - 100, main.getWidth() / 4, 100);
        snackbar.setPreferredSize(new Dimension(main.getWidth() / 4, 100));
      }
    });
  }

  /**
   * Sets the message of the snackbar panel and starts the timer.
   *
   * @param message the string representing the message of the snackbar
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