package src.views.players.names;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import src.views.components.CustomTextField;
import src.views.components.CustomizedButton;

/**
 * Component containing the fields to setup a player.
 */
public class PlayerFields extends JPanel {

  private CustomTextField playerNameField;
  private JComboBox<String> difficultyComboBox;
  private CustomizedButton btnChangePlayerType = new CustomizedButton("switch-to-ai");

  public PlayerFields() {
    setLayout(new GridLayout(2, 1));

    setPreferredSize(new Dimension(400, 250));

    // Add the text field for the player's name
    add(btnChangePlayerType);

  }

}
