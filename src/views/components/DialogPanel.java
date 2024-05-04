package src.views.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A custom JPanel class that represents a dialog panel. This panel is used to
 * display a dialog with buttons and options.
 */
public class DialogPanel extends JPanel {

  public DialogPanel(JFrame main) {

    // Setup the background component (main component)
    setLayout(new GridBagLayout());
    setBackground(new Color(0, 0, 0, 0.5f));
    setBounds(0, 0, main.getWidth(), main.getHeight());
    setVisible(true); // TODO: Make it visible only when needed

    // Setup the dialog
    JPanel dialog = new JPanel();
    dialog.setLayout(new BorderLayout());
    dialog.setPreferredSize(new Dimension(main.getWidth() / 2, main.getHeight() / 2));
    dialog.setBackground(Color.RED);

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

  }
}