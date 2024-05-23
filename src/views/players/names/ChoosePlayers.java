package src.views.players.names;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import src.views.components.BorderCenterPanel;
import src.views.utils.DimensionUtils;

public class ChoosePlayers extends JPanel {

  private PlayerFields player1Fields = new PlayerFields();
  private PlayerFields player2Fields = new PlayerFields();

  public ChoosePlayers() {

    setLayout(new BorderLayout());

    // Compute dimensions
    int height = DimensionUtils.getMainFrameHeight();

    JPanel fieldsPanel = new JPanel();
    fieldsPanel.setLayout(new BorderLayout());
    fieldsPanel.add(player1Fields, BorderLayout.WEST);
    fieldsPanel.add(player2Fields, BorderLayout.EAST);

    BorderCenterPanel fieldsPanelWrapper = new BorderCenterPanel(fieldsPanel, 0, 0, 0, 0);

    // Add components
    add(fieldsPanelWrapper, BorderLayout.CENTER);

  }
}