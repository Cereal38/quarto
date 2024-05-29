package src.views.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Snackbar {
  private final JFrame snackbarFrame;
  private final JLabel snackbarLabel;

  public Snackbar() {
    snackbarFrame = new JFrame();
    snackbarFrame.setUndecorated(true);
    snackbarFrame.setBackground(new Color(0, 0, 0, 150));
    snackbarFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowOpened(WindowEvent e) {
        Timer timer = new Timer(3000, (ActionEvent e1) -> snackbarFrame.dispose());
        timer.setRepeats(false);
        timer.start();
      }
    });

    snackbarLabel = new JLabel();
    snackbarLabel.setForeground(Color.WHITE);
    snackbarLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

    snackbarFrame.getContentPane().setLayout(new BorderLayout());
    snackbarFrame.add(snackbarLabel, BorderLayout.CENTER);
    snackbarFrame.pack();
  }

  public void show(String message, Component parentComponent) {
    snackbarLabel.setText(message);
    snackbarFrame.setSize(snackbarFrame.getPreferredSize());

    // Set the Snackbar location to the right bottom corner
    Point parentLocation = parentComponent.getLocationOnScreen();
    Dimension parentSize = parentComponent.getSize();
    Dimension snackbarSize = snackbarFrame.getSize();
    snackbarFrame.setLocation(parentLocation.x + parentSize.width - snackbarSize.width,
        parentLocation.y + parentSize.height - snackbarSize.height);

    snackbarFrame.setVisible(true);
  }
}
