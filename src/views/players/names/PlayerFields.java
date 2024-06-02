package src.views.players.names;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import src.views.components.CustomizedButton;
import src.views.components.CustomizedTextField;
import src.views.components.TranslatedLabel;
import src.views.components.TranslatedString;
import src.views.listeners.LanguageChangeListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.LangUtils;

/**
 * Component containing the fields to setup a player.
 */
public class PlayerFields extends JPanel implements LanguageChangeListener {

  private CustomizedButton btnSwitchPlayer1 = new CustomizedButton("switch-to-ai");
  private CustomizedButton btnSwitchPlayer2 = new CustomizedButton("switch-to-ai");
  private CustomizedButton btnStartGame = new CustomizedButton("start");
  private boolean player1IsAI = false;
  private boolean player2IsAI = false;
  private TranslatedString easyStr = new TranslatedString("easy");
  private TranslatedString mediumStr = new TranslatedString("medium");
  private TranslatedString hardStr = new TranslatedString("hard");
  private TranslatedString aiStr = new TranslatedString("ai");
  private TranslatedString playerStr = new TranslatedString("player");
  private TranslatedLabel startingPlayer = new TranslatedLabel("starting-player");
  private TranslatedLabel startingPlayer2 = new TranslatedLabel("starting-player");
  private CustomizedTextField namePlayer1 = new CustomizedTextField(playerStr + "1");
  private CustomizedTextField namePlayer2 = new CustomizedTextField(playerStr + "2");
  private JComboBox<TranslatedString> aiLevelPlayer1 = new JComboBox<>(
      new TranslatedString[] { easyStr, mediumStr, hardStr });
  private JComboBox<TranslatedString> aiLevelPlayer2 = new JComboBox<>(
      new TranslatedString[] { easyStr, mediumStr, hardStr });

  public PlayerFields() {

    LangUtils.addLanguageChangeListener(this);

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    setOpaque(false);

    int componentHeight = 45;
    int componentWidth = 200;
    int spacing = 10;
    int vsLabelWidth = 100;
    int startButtonWidth = 2 * componentWidth + vsLabelWidth;

    JPanel playerFieldsWrapper = new JPanel();
    playerFieldsWrapper.setOpaque(false);
    playerFieldsWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // Starting player
    JPanel startingPlayerPanel = new JPanel();
    startingPlayerPanel.setOpaque(false);
    startingPlayerPanel.setLayout(new GridLayout(1, 3));
    startingPlayerPanel.setPreferredSize(new Dimension(componentWidth, componentHeight));
    startingPlayerPanel.add(startingPlayer);
    startingPlayerPanel.add(startingPlayer2);

    // First player
    JPanel player1Panel = new JPanel();
    player1Panel.setOpaque(false);
    player1Panel.setLayout(new GridLayout(2, 1, 0, spacing));
    player1Panel.setPreferredSize(new Dimension(componentWidth, componentHeight * 2 + spacing));
    player1Panel.add(namePlayer1);
    player1Panel.add(btnSwitchPlayer1);

    // VS
    JPanel vsPanel = new JPanel();
    vsPanel.setOpaque(false);
    vsPanel.setPreferredSize(new Dimension(vsLabelWidth, componentHeight));
    vsPanel.add(new JLabel("VS"));

    // Second player
    JPanel player2Panel = new JPanel();
    player2Panel.setOpaque(false);
    player2Panel.setLayout(new GridLayout(2, 1, 0, spacing));
    player2Panel.setPreferredSize(new Dimension(componentWidth, componentHeight * 2 + spacing));
    player2Panel.add(namePlayer2);
    player2Panel.add(btnSwitchPlayer2);

    playerFieldsWrapper.add(player1Panel);
    playerFieldsWrapper.add(vsPanel);
    playerFieldsWrapper.add(player2Panel);

    // Start button
    JPanel startButtonWrapper = new JPanel();
    startButtonWrapper.setOpaque(false);
    startButtonWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    JPanel startButtonPanel = new JPanel();
    startButtonPanel.setOpaque(false);
    startButtonPanel.setLayout(new GridLayout(1, 1));
    startButtonPanel.setPreferredSize(new Dimension(startButtonWidth, componentHeight));
    startButtonWrapper.add(startButtonPanel);
    startButtonPanel.add(btnStartGame);

    add(startingPlayerPanel);
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
      updateStartButtonState();
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
      updateStartButtonState();
      revalidate();
      repaint();
    });

    // Create Start button (initially hidden)
    btnStartGame.addActionListener(e -> {

      int player1 = player1IsAI ? getAIPlayerLevel(aiLevelPlayer1) : 0;
      int player2 = player2IsAI ? getAIPlayerLevel(aiLevelPlayer2) : 0;
      // For humans players, use the name entered in the text field
      // For AI players, use the name corresponding to the level
      // If both players are differents AI, also add a suffix number to differentiate
      // them
      if (player1 == 0) {
        namePlayer1.setText(namePlayer1.getText());
      } else {
        namePlayer1.setText(getAIName(player1));
      }

      if (player2 == 0) {
        namePlayer2.setText(namePlayer2.getText());
      } else {
        namePlayer2.setText(getAIName(player2));
      }

      if (player1 != 0 && player1 == player2) {
        namePlayer1.setText(namePlayer1.getText() + " 1");
        namePlayer2.setText(namePlayer2.getText() + " 2");
      }

      EventsHandler.getController().createModel(player1, player2, namePlayer1.getText(), namePlayer2.getText());

      // Start the game
      GameStatusHandler.startGame();

      EventsHandler.navigate("GameBoard");
    });

    // Initially enable the start button
    btnStartGame.setEnabled(true);

    // Add DocumentListener to namePlayer1 to enable/disable the start button
    namePlayer1.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        updateStartButtonState();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        updateStartButtonState();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        updateStartButtonState();
      }
    });

    // Add DocumentListener to namePlayer2 to enable/disable the start button
    namePlayer2.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        updateStartButtonState();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        updateStartButtonState();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        updateStartButtonState();
      }
    });

  }

  private int getAIPlayerLevel(JComboBox<TranslatedString> aiLevel) {
    switch (aiLevel.getSelectedIndex()) {
    case 0: // Easy
      return 2;
    case 1: // Medium
      return 3;
    case 2: // Hard
      return 4;
    default: // Player
      return 0;
    }
  }

  private String getAIName(int level) {
    switch (level) {
    case 2:
      return capitalizeFirstLetter(easyStr.getText()) + aiStr;
    case 3:
      return capitalizeFirstLetter(mediumStr.getText()) + aiStr;
    case 4:
      return capitalizeFirstLetter(hardStr.getText()) + aiStr;
    default:
      return playerStr.getText();
    }
  }

  private String capitalizeFirstLetter(String str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  private void updateStartButtonState() {
    if ((!player1IsAI && namePlayer1.getText().isEmpty()) || (!player2IsAI && namePlayer2.getText().isEmpty())) {
      btnStartGame.setEnabled(false);
    } else {
      btnStartGame.setEnabled(true);
    }
  }

  @Override
  public void updateText() {
    namePlayer1.setText(playerStr + "1");
    namePlayer2.setText(playerStr + "2");
  }

}