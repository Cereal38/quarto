package src.views.players.names;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.CustomizedButton;
import src.views.components.CustomizedTextField;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;

/**
 * Component containing the fields to setup a player.
 */
public class PlayerFields extends JPanel {

  private CustomizedButton btnSwitchPlayer1 = new CustomizedButton("switch-to-ai");

  private CustomizedButton btnSwitchPlayer2 = new CustomizedButton("switch-to-ai");
  private CustomizedTextField namePlayer1 = new CustomizedTextField();
  private CustomizedTextField namePlayer2 = new CustomizedTextField();
  private CustomizedButton btnStartGame = new CustomizedButton("start");
  private boolean player1IsAI = false;
  private boolean player2IsAI = false;
  private JComboBox<String> aiLevelPlayer1 = new JComboBox<>();
  private JComboBox<String> aiLevelPlayer2 = new JComboBox<>();

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

    // Setup buttons
    btnSwitchPlayer1.addActionListener(e -> {
      // Toggle the flag
      // Switch components
      // Change text
      player1IsAI = !player1IsAI;
      if (player1IsAI) {
        btnSwitchPlayer1.setKey("switch-to-player"); // Change the text
        player1Panel.remove(namePlayer1);
        player1Panel.add(aiLevelPlayer1, 0);
      } else {
        btnSwitchPlayer1.setKey("switch-to-ai"); // Change the text
        player1Panel.remove(aiLevelPlayer1);
        player1Panel.add(namePlayer1, 0);
      }
      revalidate();
      repaint();
    });

    btnSwitchPlayer2.addActionListener(e -> {
      // Toggle the flag
      // Switch components
      // Change text
      player2IsAI = !player2IsAI;
      if (player2IsAI) {
        btnSwitchPlayer2.setKey("switch-to-player"); // Change the text
        player2Panel.remove(namePlayer2);
        player2Panel.add(aiLevelPlayer2, 0);
      } else {
        btnSwitchPlayer2.setKey("switch-to-ai"); // Change the text
        player2Panel.remove(aiLevelPlayer2);
        player2Panel.add(namePlayer2, 0);
      }
      revalidate();
      repaint();
    });

    // Create Start button (initially hidden)
    btnStartGame.addActionListener(e -> {

      EventsHandler.getController().createModel(player1IsAI ? 1 : 0, player2IsAI ? 1 : 0, namePlayer1.getText(),
          namePlayer2.getText());

      // Start the game
      GameStatusHandler.startGame();

      EventsHandler.navigate("GameBoard");
    });

    // Setup boxes
    // First player
    aiLevelPlayer1.setModel(new DefaultComboBoxModel<>(new String[] { "random", "easy", "medium", "hard" }));
    aiLevelPlayer1.setPreferredSize(new Dimension(componentWidth, componentHeight));

    // Second player
    aiLevelPlayer2.setModel(new DefaultComboBoxModel<>(new String[] { "random", "easy", "medium", "hard" }));
    aiLevelPlayer2.setPreferredSize(new Dimension(componentWidth, componentHeight));

  }
}