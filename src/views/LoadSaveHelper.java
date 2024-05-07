package src.views;

import src.views.components.RoundBorder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;

public class LoadSaveHelper {
    LoadSaveControl control ;
    boolean isSaveMode;
    JPanel slotsPanel;

    LoadSaveHelper (LoadSaveControl c, boolean isSaveMode){
        this.control = c;
        this.isSaveMode = isSaveMode;
    }

    public JPanel createSlotPanel(String slotTitle, Date savedDate) {
        JPanel slotPanel = new JPanel(new BorderLayout());

        // Set rounded border for the main content panel
        Border slotBorder = new RoundBorder(Color.BLACK, 5, 15); // Black rounded border with thickness 5 and radius 15

        // Panel for the left side (slot name and date)
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Left side (slot name and date)
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel(slotTitle);
        titleLabel.setFont(titleLabel.getFont().deriveFont(20f));
        leftPanel.add(titleLabel);
        JLabel dateLabel = new JLabel("Saved Date: " + savedDate.toString());
        leftPanel.add(dateLabel);
        contentPanel.add(leftPanel, BorderLayout.WEST);

        slotPanel.add(contentPanel, BorderLayout.CENTER);

        slotPanel.setBorder(slotBorder);


        // Create a panel for buttons (Load Game and Clear Slot)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Right-aligned layout with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setBackground(Color.GREEN); // Set background color to green

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to be performed when Load Game button is clicked (currently empty)
            }
        });
        buttonPanel.add(loadGameButton);

        // Clear Slot button (colored red)
        JButton clearSlotButton = new JButton("Clear Slot");
        clearSlotButton.setBackground(Color.RED); // Set background color to red
        clearSlotButton.setForeground(Color.WHITE);
        clearSlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to be performed when Clear Slot button is clicked (currently empty)
            }
        });
        buttonPanel.add(clearSlotButton);

        // Add the buttonPanel to a new JPanel that uses BorderLayout (to handle border layout separately)
        JPanel buttonWrapperPanel = new JPanel(new BorderLayout());
        buttonWrapperPanel.add(buttonPanel, BorderLayout.NORTH); // Add buttonPanel to the top (NORTH) of the wrapper

        // Add the buttonWrapperPanel to slotPanel on the right side (EAST) but outside the main content panel
        slotPanel.add(buttonWrapperPanel, BorderLayout.EAST);

        return slotPanel;
    }

    public void renderSlots(JPanel panel) {
        this.slotsPanel = panel;
        Map<String, Long> slotFileDates = control.getSlotFileDates();

        // Iterate over the entries of slotFileDates map to create and render slot panels
        for (Map.Entry<String, Long> entry : slotFileDates.entrySet()) {
            String slotName = entry.getKey();
            Long lastModified = entry.getValue();

            JPanel slotPanel = createSlotPanel(slotName, new Date(lastModified)); // Create a slot panel
            slotsPanel.add(slotPanel); // Add slot panel to the slotsPanel
        }
    }
}
