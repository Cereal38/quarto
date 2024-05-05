package src.views.mainmenu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.GridCenterPanel;
import src.views.components.TranslatedButton;
import src.views.utils.EventsHandler;

public class MainMenu extends JPanel {
    private TranslatedButton btnPvP = new TranslatedButton("pvp");
    private TranslatedButton btnPvC = new TranslatedButton("pvc");
    private TranslatedButton btnLoad = new TranslatedButton("load");

    public MainMenu() {
        setLayout(new BorderLayout());

        JPanel navbar = new TopBarMainMenu();
        add(navbar, BorderLayout.NORTH);

        // Add action listeners to the buttons
        btnPvP.addActionListener(e -> {
            EventsHandler.navigate("GameBoard");
        });
        btnPvC.addActionListener(e -> {
            EventsHandler.navigate("GameBoard");
        });

        // Menu centered on the screen
        // The menu is at the middle of a 3x3 grid
        JPanel menu = new JPanel();
        JPanel gridCenter = new GridCenterPanel(menu);
        add(gridCenter, BorderLayout.CENTER);

        // Display menu items as a list
        menu.setLayout(new GridLayout(4, 1, 0, 5));

        JLabel titleLabel = new JLabel("Quarto");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        menu.add(titleLabel);
        menu.add(btnPvP);
        menu.add(btnPvC);
        menu.add(btnLoad);

    }

}
