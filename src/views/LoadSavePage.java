package src.views;

import src.controller.PlayersInformations;
import src.views.components.BorderCenterPanel;
import src.views.players.names.PlayersInformationsControl;

import javax.swing.*;
import java.awt.*;

public class LoadSavePage extends JPanel {
    private boolean isSaveMode;
    PlayersInformationsControl control;
    private JPanel slotsPanel;

    public LoadSavePage(boolean isSaveMode) {
        this.isSaveMode = isSaveMode;
        this.control = new PlayersInformations();
        LoadSaveHelper help = new LoadSaveHelper(control, isSaveMode);
        // Set the layout for the LoadPage panel
        setLayout(new BorderLayout()); // Use BorderLayout to organize components

        // Create and add a title label at the top
        JLabel titleLabel = new JLabel(!isSaveMode ? "Load Page" : "Save Page", SwingConstants.CENTER); // Center-align the title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font style and size
        add(titleLabel, BorderLayout.NORTH); // Add title label to the top of the panel

        // Create a panel for the slots (using GridLayout)
        this.slotsPanel = new JPanel(new GridLayout(10, 1, 10, 10)); // 10 slots vertically with gaps

        help.renderSlots(slotsPanel);
        BorderCenterPanel center = new BorderCenterPanel(slotsPanel,10,320,10,320);
        // Add the slotsPanel to a JScrollPane for scrolling if needed
        JScrollPane scrollPane = new JScrollPane(center);
        add(scrollPane, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        // Create and display the LoadPage panel in a JFrame for testing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Load Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            LoadSavePage loadSavePage = new LoadSavePage(true);
            frame.getContentPane().add(loadSavePage);

            frame.pack();
            frame.setVisible(true);
        });
    }
}
