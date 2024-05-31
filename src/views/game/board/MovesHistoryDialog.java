package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import src.views.game.history.MovesHistory;

public class MovesHistoryDialog extends JPanel {

  public MovesHistoryDialog() {
    setLayout(new BorderLayout());

    // Créez une instance de MovesHistory
    MovesHistory movesHistory = new MovesHistory();

    // Ajoutez MovesHistory à la boîte de dialogue
    add(movesHistory, BorderLayout.CENTER);

    // Configurez la taille et d'autres propriétés de la boîte de dialogue
    setPreferredSize(new Dimension(400, 600));
  }
}
