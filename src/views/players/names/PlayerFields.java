package src.views.players.names;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import src.views.components.CustomizedButton;

/**
 * Component containing the fields to setup a player.
 */
public class PlayerFields extends JPanel {

  private JTextField namePlayer1 = new JTextField();
  private CustomizedButton btnSwitchPlayer1 = new CustomizedButton("switch-to-ai");
  private JTextField namePlayer2 = new JTextField();
  private CustomizedButton btnSwitchPlayer2 = new CustomizedButton("switch-to-ai");

  public PlayerFields() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));

    // First player
    JPanel player1Panel = new JPanel();
    player1Panel.setLayout(new GridLayout(2, 1, 0, 10));
    player1Panel.setPreferredSize(new Dimension(200, 100));
    player1Panel.add(namePlayer1);
    player1Panel.add(btnSwitchPlayer1);

    // VS
    JPanel vsPanel = new JPanel();
    vsPanel.add(new JLabel("VS"));

    // Second player
    JPanel player2Panel = new JPanel();
    player2Panel.setLayout(new GridLayout(2, 1, 0, 10));
    player2Panel.setPreferredSize(new Dimension(200, 100));
    player2Panel.add(namePlayer2);
    player2Panel.add(btnSwitchPlayer2);

    add(player1Panel);
    add(vsPanel);
    add(player2Panel);

  }

}
