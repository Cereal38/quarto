package src.views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class SlotPanelCreator {

    private JPanel createSlotPanel(String slotTitle, Date savedDate) {
        JPanel slotPanel = new JPanel(new BorderLayout());

        // Set border for the main content panel
        Border slotBorder = BorderFactory.createLineBorder(Color.BLACK, 1); // Black line border with thickness 1
        slotPanel.setBorder(slotBorder);

        // Panel for the left side (slot name and date)
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Left side (slot name and date)
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel(slotTitle);
        titleLabel.setFont(titleLabel.getFont().deriveFont(20f));
        leftPanel.add(titleLabel);
        JLabel dateLabel = new JLabel("Saved Date: " + savedDate.toString());
        leftPanel.add(dateLabel);
        contentPanel.setBorder(slotBorder); // Use the same border as slotPanel
        contentPanel.add(leftPanel, BorderLayout.WEST);

        // Add the contentPanel to slotPanel in CENTER (inside the border)
        slotPanel.add(contentPanel, BorderLayout.CENTER);

        // Create a panel for buttons (Load Game and Clear Slot)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Right-aligned layout

        // Load Game button (colored green)
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setBackground(Color.GREEN); // Set background color to green
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to be performed when Load Game button is clicked (currently empty)
            }
        });

        // Set border for Load Game button matching the slotPanel's border
        Border buttonBorder = BorderFactory.createEmptyBorder(
                slotBorder.getBorderInsets(slotPanel).top, // top
                slotBorder.getBorderInsets(slotPanel).left, // left
                slotBorder.getBorderInsets(slotPanel).bottom, // bottom
                slotBorder.getBorderInsets(slotPanel).right // right
        );
        loadGameButton.setBorder(buttonBorder);

        buttonPanel.add(loadGameButton);

        // Clear Slot button (colored red)
        JButton clearSlotButton = new JButton("Clear Slot");
        clearSlotButton.setBackground(Color.RED); // Set background color to red
        clearSlotButton.setForeground(Color.WHITE); // Set text color to white
        clearSlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to be performed when Clear Slot button is clicked (currently empty)
            }
        });

        // Set border for Clear Slot button matching the slotPanel's border
        clearSlotButton.setBorder(buttonBorder);

        buttonPanel.add(clearSlotButton);

        // Add the buttonPanel to a new JPanel that uses BorderLayout (to handle border layout separately)
        JPanel buttonWrapperPanel = new JPanel(new BorderLayout());
        buttonWrapperPanel.add(buttonPanel, BorderLayout.NORTH); // Add buttonPanel to the top (NORTH) of the wrapper

        // Add the buttonWrapperPanel to slotPanel on the right side (EAST) but outside the main content panel
        slotPanel.add(buttonWrapperPanel, BorderLayout.EAST);

        return slotPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Slot Panels Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Create slot panels and add them to the frame
        SlotPanelCreator creator = new SlotPanelCreator();
        for (int i = 1; i <= 10; i++) {
            JPanel slotPanel = creator.createSlotPanel("Slot " + i, new Date());
            frame.add(slotPanel);
        }

        frame.pack();
        frame.setVisible(true);
    }
}
