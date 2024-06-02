package src.views.players.names;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import src.views.components.CustomizedButton;
import src.views.components.CustomizedTextField;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ImageUtils;

public class PlayerFields extends JPanel {
  private CustomizedButton btnSwitchPlayer1 = new CustomizedButton("switch-to-ai");
  private CustomizedButton btnSwitchPlayer2 = new CustomizedButton("switch-to-ai");
  private CustomizedTextField namePlayer1 = new CustomizedTextField("Player1");
  private CustomizedTextField namePlayer2 = new CustomizedTextField("Player2");
  private CustomizedButton btnStartGame = new CustomizedButton("start");
  private boolean player1IsAI = false;
  private boolean player2IsAI = false;
  private JComboBox<String> aiLevelPlayer1 = new JComboBox<>(new String[]{"easy", "medium", "hard"});
  private JComboBox<String> aiLevelPlayer2 = new JComboBox<>(new String[]{"easy", "medium", "hard"});

  public PlayerFields() {
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
    vsPanel.setLayout(new BoxLayout(vsPanel, BoxLayout.Y_AXIS));
    vsPanel.add(Box.createVerticalGlue());
    vsPanel.add(new JLabel("VS", SwingConstants.CENTER));
    vsPanel.add(Box.createVerticalGlue());

    // Arrow button to switch players
    ImageIcon doubleArrow = ImageUtils.loadImage("double-sided-arrow.png", 50, 50);
    JButton switchButton = ImageUtils.createButtonFromImage(doubleArrow);
    switchButton.addActionListener(e -> switchPlayers());
    JPanel arrowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    arrowPanel.setOpaque(false);
    arrowPanel.add(switchButton);
    vsPanel.add(arrowPanel, 0); // Add arrow panel at the top

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

    add(playerFieldsWrapper);
    add(Box.createRigidArea(new Dimension(0, spacing))); // Divider
    add(startButtonWrapper);

    // Setup buttons
    btnSwitchPlayer1.addActionListener(e -> {
      player1IsAI = !player1IsAI;
      if (player1IsAI) {
        btnSwitchPlayer1.setKey("switch-to-player");
        player1Panel.remove(namePlayer1);
        player1Panel.add(aiLevelPlayer1, 0);
      } else {
        btnSwitchPlayer1.setKey("switch-to-ai");
        player1Panel.remove(aiLevelPlayer1);
        player1Panel.add(namePlayer1, 0);
      }
      updateStartButtonState();
      revalidate();
      repaint();
    });

    btnSwitchPlayer2.addActionListener(e -> {
      player2IsAI = !player2IsAI;
      if (player2IsAI) {
        btnSwitchPlayer2.setKey("switch-to-player");
        player2Panel.remove(namePlayer2);
        player2Panel.add(aiLevelPlayer2, 0);
      } else {
        btnSwitchPlayer2.setKey("switch-to-ai");
        player2Panel.remove(aiLevelPlayer2);
        player2Panel.add(namePlayer2, 0);
      }
      updateStartButtonState();
      revalidate();
      repaint();
    });

    btnStartGame.addActionListener(e -> {
      int player1 = player1IsAI ? getAIPlayerLevel(aiLevelPlayer1) : 0;
      int player2 = player2IsAI ? getAIPlayerLevel(aiLevelPlayer2) : 0;

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

      GameStatusHandler.startGame();
      EventsHandler.navigate("GameBoard");
    });

    btnStartGame.setEnabled(true);

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

  private void switchPlayers() {
    String tempName = namePlayer1.getText();
    namePlayer1.setText(namePlayer2.getText());
    namePlayer2.setText(tempName);

    boolean tempAIStatus = player1IsAI;
    player1IsAI = player2IsAI;
    player2IsAI = tempAIStatus;

    JComboBox<String> tempAILevel = aiLevelPlayer1;
    aiLevelPlayer1 = aiLevelPlayer2;
    aiLevelPlayer2 = tempAILevel;

    revalidate();
    repaint();
  }

  private int getAIPlayerLevel(JComboBox<String> levelList) {
    switch ((String) levelList.getSelectedItem()) {
      case "easy":
        return 1;
      case "medium":
        return 2;
      case "hard":
        return 3;
    }
    return 0;
  }

  private String getAIName(int ai) {
    switch (ai) {
      case 1:
        return "EasyAI";
      case 2:
        return "MediumAI";
      case 3:
        return "HardAI";
    }
    return "Player";
  }

  private void updateStartButtonState() {
    boolean enabled = namePlayer1.getText().trim().length() > 0 &&
            namePlayer2.getText().trim().length() > 0;
    btnStartGame.setEnabled(enabled);
  }
}
