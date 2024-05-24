package src.views.players.names;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
  private CustomizedButton btnStartGame = new CustomizedButton("start");

  public PlayerFields() {

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    int componentHeight = 45;
    int componentWidth = 200;
    int spacing = 10;
    int vsLabelWidth = 100;
    int startButtonWidth = 2 * componentWidth + vsLabelWidth;

    JPanel playerFieldsWrapper = new JPanel();
    playerFieldsWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // First player
    JPanel player1Panel = new JPanel();
    player1Panel.setLayout(new GridLayout(2, 1, 0, spacing));
    player1Panel.setPreferredSize(new Dimension(componentWidth, componentHeight * 2 + spacing));
    player1Panel.add(namePlayer1);
    player1Panel.add(btnSwitchPlayer1);

    // VS
    JPanel vsPanel = new JPanel();
    vsPanel.setPreferredSize(new Dimension(vsLabelWidth, componentHeight));
    vsPanel.add(new JLabel("VS"));

    // Second player
    JPanel player2Panel = new JPanel();
    player2Panel.setLayout(new GridLayout(2, 1, 0, spacing));
    player2Panel.setPreferredSize(new Dimension(componentWidth, componentHeight * 2 + spacing));
    player2Panel.add(namePlayer2);
    player2Panel.add(btnSwitchPlayer2);

    playerFieldsWrapper.add(player1Panel);
    playerFieldsWrapper.add(vsPanel);
    playerFieldsWrapper.add(player2Panel);

    // Start button
    JPanel startButtonWrapper = new JPanel();
    startButtonWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    JPanel startButtonPanel = new JPanel();
    startButtonPanel.setLayout(new GridLayout(1, 1));
    startButtonPanel.setPreferredSize(new Dimension(startButtonWidth, componentHeight));
    startButtonWrapper.add(startButtonPanel);
    startButtonPanel.add(btnStartGame);

    add(playerFieldsWrapper);
    add(Box.createRigidArea(new Dimension(0, spacing))); // Divider
    add(startButtonWrapper);
  }
}