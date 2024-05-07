package src.views;

import src.views.components.BorderCenterPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class LoadSavePage extends JPanel {
    private boolean isSaveMode ;
    //mode = 0 -> load
    //mode = 1 -> save
    public LoadSavePage(boolean isSaveMode) {
        this.isSaveMode = isSaveMode;

        // Set the layout for the LoadPage panel
        setLayout(new BorderLayout()); // Use BorderLayout to organize components

        // Create and add a title label at the top
        JLabel titleLabel = new JLabel(!isSaveMode ? "Load Page" : "Save Page", SwingConstants.CENTER); // Center-align the title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font style and size
        add(titleLabel, BorderLayout.NORTH); // Add title label to the top of the panel

        // Create a panel for the slots (using GridLayout)
        JPanel slotsPanel = new JPanel(new GridLayout(10, 1, 10, 10)); // 10 slots vertically with gaps

        // Create and add 10 slots
        for (int i = 1; i <= 10; i++) {
            JPanel slotPanel = createSlotPanel("Slot " + i, new Date()); // Create a slot panel
            slotsPanel.add(slotPanel); // Add slot panel to the slotsPanel
        }

        // Add the slotsPanel to a JScrollPane for scrolling if needed
        BorderCenterPanel center = new BorderCenterPanel(slotsPanel,10,320,10,320);
        JScrollPane scrollPane = new JScrollPane(center);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createSlotPanel(String slotTitle, Date savedDate) {
        JPanel slotPanel = new JPanel(new BorderLayout());

        // Set border for the main content panel
        Border slotBorder  = BorderFactory.createLineBorder(Color.BLACK, 1); // Black line border with thickness 1

        // Panel for the left side (slot name and date)
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Left side (slot name and date)
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel(slotTitle);
        titleLabel.setFont(titleLabel.getFont().deriveFont(20f));
        leftPanel.add(titleLabel);
        JLabel dateLabel = new JLabel("Saved Date: " + savedDate.toString());
        leftPanel.add(dateLabel);
        slotPanel.setBorder(slotBorder );
        contentPanel.add(leftPanel, BorderLayout.WEST);

        // Add the contentPanel to slotPanel in CENTER (inside the border)
        slotPanel.add(contentPanel, BorderLayout.CENTER);

        // Create a panel for buttons (Load Game and Clear Slot)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Right-aligned layout with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JButton loadGameButton = new JButton(!isSaveMode ? "Load Game" : "Save Game");
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
        BorderCenterPanel center = new BorderCenterPanel(buttonWrapperPanel, 10,10,10,50);
        slotPanel.add(center, BorderLayout.EAST);

        return slotPanel;
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
