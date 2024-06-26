package src.views.players.names;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import src.views.components.CustomizedButton;
import src.views.components.CustomizedTextField;
import src.views.components.Field;
import src.views.components.ImageThemed;
import src.views.components.TranslatedString;
import src.views.listeners.LanguageChangeListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ImageUtils;
import src.views.utils.LangUtils;

/**
 * The PlayerFields class represents a component containing the fields to set up
 * a player. It allows users to enter player names and select AI levels.
 *
 * <p>
 * The class extends the JPanel class and implements the LanguageChangeListener
 * interface to listen for language change events.
 * </p>
 */

public class PlayerFields extends JPanel implements LanguageChangeListener {

  /** Constants */
  public static final int PLAYER = 0;
  public static final int EASY = 2;
  public static final int MEDIUM = 3;
  public static final int HARD = 4;

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
  private TranslatedString startingPlayer = new TranslatedString("starting-player");
  private boolean player1starts = true;
  private Field startingPlayer1;
  private Field startingPlayer2;
  private CustomizedTextField namePlayer1 = new CustomizedTextField(playerStr + "1");
  private CustomizedTextField namePlayer2 = new CustomizedTextField(playerStr + "2");
  private JComboBox<TranslatedString> aiLevelPlayer1 = new JComboBox<>(
      new TranslatedString[] { easyStr, mediumStr, hardStr });
  private JComboBox<TranslatedString> aiLevelPlayer2 = new JComboBox<>(
      new TranslatedString[] { easyStr, mediumStr, hardStr });

  /**
   * Constructs a PlayerFields panel. Sets up the layout and components for player
   * name input and AI level selection.
   */
  public PlayerFields() {

    LangUtils.addLanguageChangeListener(this);

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    setOpaque(false);

    int componentHeight = 45;
    int componentWidth = 200;
    int spacing = 10;
    int vsLabelWidth = 100;
    int startButtonWidth = 2 * componentWidth + vsLabelWidth;
    int arrowSize = 32;

    JPanel playerFieldsWrapper = new JPanel();
    playerFieldsWrapper.setOpaque(false);
    playerFieldsWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // Starting player
    JPanel startingPlayerPanel = new JPanel();
    startingPlayerPanel.setOpaque(false);
    startingPlayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    startingPlayerPanel.setPreferredSize(new Dimension(componentWidth, componentHeight));
    startingPlayer1 = new Field(startingPlayer.getText(), player1starts);
    startingPlayer2 = new Field(startingPlayer.getText(), !player1starts);
    startingPlayer1.setPreferredSize(new Dimension(componentWidth, componentHeight));
    startingPlayer2.setPreferredSize(new Dimension(componentWidth, componentHeight));
    ImageThemed doubleArrowImage = new ImageThemed("double-arrow.png");
    doubleArrowImage.setSize(arrowSize, arrowSize);
    ImageIcon doubleArrow = new ImageIcon(doubleArrowImage.getImage());
    JButton switchButton = ImageUtils.createButtonFromImage(doubleArrow);
    switchButton.setBorder(
        BorderFactory.createEmptyBorder(0, vsLabelWidth / 2 - arrowSize / 2, 0, vsLabelWidth / 2 - arrowSize / 2));
    switchButton.addActionListener(e -> {
      player1starts = !player1starts;
      startingPlayer1.setOn(player1starts);
      startingPlayer2.setOn(!player1starts);
    });
    startingPlayerPanel.add(startingPlayer1);
    startingPlayerPanel.add(switchButton);
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
    vsPanel.setPreferredSize(new Dimension(vsLabelWidth, vsLabelWidth));
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
    add(Box.createRigidArea(new Dimension(0, spacing))); // Divider
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

      // Set fields order according to the starting player
      if (player1starts) {
        EventsHandler.getController().createModel(player1, player2, namePlayer1.getText(), namePlayer2.getText());
      } else {
        EventsHandler.getController().createModel(player2, player1, namePlayer2.getText(), namePlayer1.getText());
      }

      // Reset fields
      player1IsAI = false;
      player2IsAI = false;
      btnSwitchPlayer1.setKey("switch-to-ai");
      btnSwitchPlayer2.setKey("switch-to-ai");
      player1Panel.remove(aiLevelPlayer1);
      player1Panel.add(namePlayer1, 0);
      player2Panel.remove(aiLevelPlayer2);
      player2Panel.add(namePlayer2, 0);
      namePlayer1.setText(playerStr + "1");
      namePlayer2.setText(playerStr + "2");

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

  /**
   * Retrieves the AI player level based on the selected index in the JComboBox.
   *
   * @param aiLevel The JComboBox containing AI level options.
   * @return The AI player level.
   */
  private int getAIPlayerLevel(JComboBox<TranslatedString> aiLevel) {
    switch (aiLevel.getSelectedIndex()) {
    case 0: // Easy
      return EASY;
    case 1: // Medium
      return MEDIUM;
    case 2: // Hard
      return HARD;
    default: // Player
      return PLAYER;
    }
  }

  /**
   * Generates the name of an AI player based on the selected level.
   *
   * @param level The level of the AI player.
   * @return The name of the AI player.
   */
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

  /**
   * Capitalizes the first letter of a string.
   *
   * @param str The string to be capitalized.
   * @return The string with the first letter capitalized.
   */
  private String capitalizeFirstLetter(String str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  /**
   * Updates the state of the start game button based on player input.
   */
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
    startingPlayer1.setText(startingPlayer.getText());
    startingPlayer2.setText(startingPlayer.getText());
  }

}