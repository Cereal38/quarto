package src.views.load.save;

import src.controller.ViewModelController;
import src.views.components.BorderCenterPanel;
import src.views.components.TranslatedLabel;
import src.views.listeners.ViewModelListener;

import javax.swing.*;
import java.awt.*;

public class LoadSavePage extends JPanel {
    private boolean isSaveMode;
    ViewModelListener control;
    private JPanel slotsPanel;

    public LoadSavePage(boolean isSaveMode) {
        this.isSaveMode = isSaveMode;
        this.control = new ViewModelController();
        LoadSaveHelper help = new LoadSaveHelper(control, isSaveMode);
        // Set the layout for the LoadPage panel
        setLayout(new BorderLayout());



        // Create and add a title label at the top center aligned
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        TranslatedLabel titleLabel = new TranslatedLabel(!isSaveMode
                ? "load-page"
                : "save-page");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        labelPanel.add(titleLabel);
        add(labelPanel, BorderLayout.NORTH);

        // Create a panel for the slots (using GridLayout) 10 slots vertically with gaps
        this.slotsPanel = new JPanel(new GridLayout(10, 1, 10, 10));

        help.renderSlots(slotsPanel);
        BorderCenterPanel center = new BorderCenterPanel(slotsPanel,10,320,10,320);
        // Add the slotsPanel to a JScrollPane for scrolling if needed

        JScrollPane scrollPane = new JScrollPane(center);
        add(scrollPane, BorderLayout.CENTER);
    }


//    public static void main(String[] args) {
//        // Create and display the LoadPage panel in a JFrame for testing
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Load Page");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            LoadSavePage loadSavePage = new LoadSavePage(false);
//            frame.getContentPane().add(loadSavePage);
//
//            frame.pack();
//            frame.setVisible(true);
//        });
//    }
}
