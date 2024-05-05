package src.views;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseMenu extends JPanel {
    public PauseMenu() {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton abandanButton = new JButton("Abandon");
        JButton restartButton = new JButton("Restart");
        JButton saveButton = new JButton("Save");
        JButton rulesButton = new JButton("Rules");
        JButton mainMenuButton = new JButton("Main Menu");

        add(mainMenuButton, BorderLayout.SOUTH);
        add(abandanButton, BorderLayout.LINE_START);
        add(restartButton, BorderLayout.LINE_END);
        add(saveButton, BorderLayout.NORTH);
        add(rulesButton, BorderLayout.CENTER);

    }
}