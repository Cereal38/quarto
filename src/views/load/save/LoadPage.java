package src.views.load.save;

import src.views.components.BorderCenterPanel;
import src.views.components.TranslatedLabel;
import src.views.utils.EventsHandler;

import javax.swing.*;
import java.awt.*;

public class LoadPage extends JPanel {
    private LoadHelper helper;
    JPanel slotsPanel;

    public LoadPage() {
        this.helper = new LoadHelper(this);

        setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        TranslatedLabel titleLabel = new TranslatedLabel("load-page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        labelPanel.add(titleLabel);
        add(labelPanel, BorderLayout.NORTH);

        this.slotsPanel = new JPanel(new GridLayout(10, 1, 10, 10));

        renderSlots(); // Render slots initially

        BorderCenterPanel center = new BorderCenterPanel(slotsPanel, 10, 320, 10, 320);
        JScrollPane scrollPane = new JScrollPane(center);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void renderSlots() {
        helper.renderSlots(slotsPanel, EventsHandler.getController().getSlotFiles());
    }

    public LoadHelper getHelper() {
        return helper;
    }
    public void refreshPage() {
        // Clear existing slotsPanel content
        slotsPanel.removeAll();
        // Render slots again
        renderSlots();
        // Revalidate and repaint the panel
        revalidate();
        repaint();
    }
    //    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Load Page");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            LoadSavePage loadSavePage = new LoadSavePage();
//            frame.getContentPane().add(loadSavePage);
//
//            frame.pack();
//            frame.setVisible(true);
//        });
//    }
}
