package src.views.load.save;

import src.views.components.BorderCenterPanel;
import src.views.components.GoBackButton;
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

        // Create and add the "Go Back" button with an image
        GoBackButton backButton = new GoBackButton();
        // Create top panel and add the "Go Back" button to it
        JPanel topPanel = new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(helper.getDifferentWood(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        topPanel.add(backButton, BorderLayout.WEST);

        // Add top panel to the north of LoadPage
        add(topPanel, BorderLayout.NORTH);

        // Create the title label and add it to the center of the top panel
        TranslatedLabel titleLabel = new TranslatedLabel("load-page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(helper.getWoodTexture(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        labelPanel.add(titleLabel);
        topPanel.add(labelPanel, BorderLayout.CENTER);


        // Initialize the slots panel with GridLayout
        this.slotsPanel = new JPanel(new GridLayout(10, 1, 10, 10));

        // Render slots initially
        renderSlots();

        // Create a scroll pane for the slots panel
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
}
