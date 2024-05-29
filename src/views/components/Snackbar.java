package src.views.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import src.views.utils.EventsHandler;

public class Snackbar {
  private TranslatedLabel translatedLabel;
  private final JFrame snackbarFrame;
  private final int margin = 20;

  public Snackbar() {
    snackbarFrame = new JFrame();
    snackbarFrame.setUndecorated(true);
    snackbarFrame.setBackground(new Color(0, 0, 0, 0));
    snackbarFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowOpened(WindowEvent e) {
        Timer timer = new Timer(1500, (ActionEvent e1) -> snackbarFrame.dispose());
        timer.setRepeats(false);
        timer.start();
      }
    });

    translatedLabel = new TranslatedLabel("");
    translatedLabel.setForeground(Color.BLACK);
    translatedLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
    translatedLabel.setBackground(Color.WHITE);
    translatedLabel.setOpaque(true);
    translatedLabel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));

    snackbarFrame.getContentPane().setLayout(new BorderLayout());
    snackbarFrame.add(translatedLabel, BorderLayout.CENTER);
    snackbarFrame.pack();
  }

  public void show(String key) {
    translatedLabel.setKey(key);
    snackbarFrame.setSize(snackbarFrame.getPreferredSize());

    // Set the Snackbar location using main panel reference
    JPanel mainPanel = EventsHandler.getMainPanel();

    // Set the Snackbar location to the right bottom corner with a 20 pixels space
    Point parentLocation = mainPanel.getLocationOnScreen();
    Dimension parentSize = mainPanel.getSize();
    Dimension snackbarSize = snackbarFrame.getSize();
    snackbarFrame.setLocation(parentLocation.x + margin,
        parentLocation.y + parentSize.height - snackbarSize.height - margin);

    snackbarFrame.setVisible(true);
  }

}
