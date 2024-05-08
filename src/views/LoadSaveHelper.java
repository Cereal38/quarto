package src.views;

import src.views.components.RoundBorder;
import src.views.components.TranslatedString;
import src.views.players.names.PlayersInformationsControl;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;

public class LoadSaveHelper {
    PlayersInformationsControl control;
    boolean isSaveMode;
    JPanel slotsPanel;
    Map<String, Long> slotFileDates;

    LoadSaveHelper(PlayersInformationsControl c, boolean isSaveMode) {
        this.control = c;
        this.isSaveMode = isSaveMode;
    }

    public JPanel createSlotPanel(String slotTitle, Date savedDate, int index) {
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

        TranslatedString loadTranslate = new TranslatedString("load-game");
        TranslatedString saveTranslate = new TranslatedString("save-game");

        JButton loadGameButton = new JButton(!isSaveMode ? loadTranslate.getText() : saveTranslate.getText());
        if (isSaveMode && !control.isSlotFileEmpty(index)){
            loadGameButton.setVisible(false);
        } else if (isSaveMode && control.isSlotFileEmpty(index)){
            loadGameButton.setVisible(true);
        } else if (!isSaveMode && control.isSlotFileEmpty(index)){
            loadGameButton.setVisible(false);
        } else if (!isSaveMode && !control.isSlotFileEmpty(index)){
            loadGameButton.setVisible(true);
        }
        loadGameButton.setBackground(Color.GREEN); // Set background color to green
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (isSaveMode){
//                   control.saveGame();
               } else {
                   control.loadGame(index);
               }
            }
        });
        buttonPanel.add(loadGameButton);

        // Clear Slot button (colored red)
        TranslatedString clearTranslate = new TranslatedString("clear-slot");
        JButton clearSlotButton = new JButton(clearTranslate.getText());
        clearSlotButton.setBackground(Color.RED); // Set background color to red
        clearSlotButton.setForeground(Color.WHITE);
        if (control.isSlotFileEmpty(index)) {
            clearSlotButton.setVisible(false);
        } else {
            clearSlotButton.setVisible(true);
        }
        clearSlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO : methode to delete
                // control.clearSlot(index)
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
        this.slotFileDates = control.getSlotFileDates();

        // Iterate over the entries of slotFileDates map to create and render slot panels
        int index = 0;
        for (Map.Entry<String, Long> entry : slotFileDates.entrySet()) {
            String slotName = entry.getKey();
            Long lastModified = entry.getValue();

            JPanel slotPanel = createSlotPanel(slotName, new Date(lastModified), index); // Create a slot panel
            slotsPanel.add(slotPanel); // Add slot panel to the slotsPanel
            index++;
        }
    }
}
