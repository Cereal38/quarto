package src.views.players.names;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.TranslatedLabel;

public class ChoosePlayers extends JPanel {

  public ChoosePlayers() {

    setLayout(new GridLayout(3, 1));

    // Setup title bar
    JPanel titleWrapper = new JPanel();
    titleWrapper.setLayout(new BorderLayout());
    TranslatedLabel titleLabel = new TranslatedLabel("enter-players-names");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(JLabel.CENTER); // Center the label horizontally
    titleWrapper.add(titleLabel, BorderLayout.SOUTH);

    // Setup fields
    JPanel fieldsWrapper = new JPanel();
    fieldsWrapper.setLayout(new GridBagLayout());
    fieldsWrapper.add(new PlayerFields());

    // Add components
    add(titleWrapper);
    add(fieldsWrapper);

  }
}
