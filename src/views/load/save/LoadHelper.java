package src.views.load.save;

import src.structures.SlotFile;
import src.views.components.RoundBorder;
import src.views.components.TranslatedString;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class LoadHelper {
    JPanel slotsPanel;
    List<SlotFile> slotFiles;
    LoadPage loadSavePage;

    public LoadHelper(LoadPage l) {
        this.slotFiles = EventsHandler.getController().getSlotFiles();
        loadSavePage = l;
    }

    public JPanel createSlotPanel(String slotTitle, Date savedDate, int id) {
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
        TranslatedString date = new TranslatedString("saved-date");
        JLabel dateLabel = new JLabel(date.getText() + savedDate.toString());
        leftPanel.add(dateLabel);
        contentPanel.add(leftPanel, BorderLayout.WEST);

        slotPanel.add(contentPanel, BorderLayout.CENTER);

        slotPanel.setBorder(slotBorder);

        // Create a panel for buttons (Load Game and Clear Slot)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Right-aligned layout with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Load Game button (colored green)
        TranslatedString loadTranslate = new TranslatedString("load-game");
        JButton loadGameButton = new JButton(loadTranslate.getText());
        loadGameButton.setBackground(Color.GREEN); // Set background color to green
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Load the game
                EventsHandler.getController().loadGame(id);
                EventsHandler.navigate("GameBoard");
                GameStatusHandler.informListeners();
            }
        });
        buttonPanel.add(loadGameButton);

        // Clear Slot button (colored red)
        TranslatedString clearTranslate = new TranslatedString("clear-slot");
        JButton clearSlotButton = new JButton(clearTranslate.getText());
        clearSlotButton.setBackground(Color.RED); // Set background color to red
        clearSlotButton.setForeground(Color.WHITE);
        clearSlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to clear this slot?",
                        "Confirm Clear Slot",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE

                );

                if (response == JOptionPane.YES_OPTION) {
                    EventsHandler.getController().clearSlot(id);
                    slotPanel.setVisible(false);
                }
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

    public void renderSlots(JPanel slotsPanel, List<SlotFile> slotFiles) {
        slotsPanel.removeAll();

        for (SlotFile slotFile : slotFiles) {
            JPanel slotPanel = createSlotPanel(slotFile.getFilename(), new Date(slotFile.getLastModified()), slotFile.getId());
            slotsPanel.add(slotPanel);
        }

        slotsPanel.revalidate();
        slotsPanel.repaint();
    }
}
