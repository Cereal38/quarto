package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import src.views.game.history.MovesHistory;
import src.views.utils.EventsHandler;

public class MovesHistoryDialog extends JDialog {

    public MovesHistoryDialog(JFrame parent) {
        super(parent, "Moves History", true);
        setLayout(new BorderLayout());
        EventsHandler.setMovesHistoryDialog(this);

        // Créez une instance de MovesHistory
        MovesHistory movesHistory = new MovesHistory();

        // Ajoutez MovesHistory à la boîte de dialogue
        add(movesHistory, BorderLayout.CENTER);

        // Configurez la taille et d'autres propriétés de la boîte de dialogue
        setPreferredSize(new Dimension(400, 600));
        pack();
        setLocationRelativeTo(parent); // Centrer par rapport à la fenêtre parente
    }

    public static void main(String[] args) {
        // Pour tester la boîte de dialogue indépendamment
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        MovesHistoryDialog dialog = new MovesHistoryDialog(frame);
        dialog.setVisible(true);
    }
}
