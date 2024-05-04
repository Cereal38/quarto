package src.views.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogPanel extends JPanel {

  public DialogPanel(JFrame main) {

    // Setup the background component (main component)
    setBackground(new Color(0, 0, 0, 0.5f));
    setBounds(0, 0, main.getWidth(), main.getHeight());

    setLayout(new GridLayout(3, 3));

    // Setup the dialog
    JPanel dialog = new JPanel();
    dialog.setLayout(new BorderLayout());
    dialog.setBounds(100, 100, 400, 200);
    dialog.setVisible(true);

    JButton abandanButton = new JButton("Abandon");
    JButton restartButton = new JButton("Restart");
    JButton saveButton = new JButton("Save");
    JButton rulesButton = new JButton("Rules");
    JButton mainMenuButton = new JButton("Main Menu");

    dialog.add(mainMenuButton, BorderLayout.SOUTH);
    dialog.add(abandanButton, BorderLayout.LINE_START);
    dialog.add(restartButton, BorderLayout.LINE_END);
    dialog.add(saveButton, BorderLayout.NORTH);
    dialog.add(rulesButton, BorderLayout.CENTER);

    add(new JLabel(""));
    add(new JLabel(""));
    add(new JLabel(""));
    add(new JLabel(""));
    add(dialog);
    add(new JLabel(""));
    add(new JLabel(""));
    add(new JLabel(""));
    add(new JLabel(""));

    // Allow to resize the menu when the main frame is resized
    main.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        setBounds(0, 0, main.getWidth(), main.getHeight());
      }
    });

  }
}