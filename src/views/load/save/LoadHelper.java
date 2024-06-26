package src.views.load.save;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import src.structures.SlotFile;
import src.views.components.ImageThemed;
import src.views.components.RoundBorder;
import src.views.components.TranslatedButton;
import src.views.components.TranslatedString;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;

/**
 * Helper class for creating and rendering slots in the LoadPage.
 */

public class LoadHelper {
  JPanel slotsPanel;
  List<SlotFile> slotFiles;
  LoadPage loadSavePage;
  private ImageThemed slotImage = new ImageThemed("flat.png");

  public LoadHelper(LoadPage l) {
    this.slotFiles = EventsHandler.getController().getSlotFiles();
    loadSavePage = l;
  }

  /**
   * Creates a panel for a slot with the specified title, date, and ID.
   * @param slotTitle The title of the slot.
   * @param savedDate The date when the slot was saved.
   * @param id The ID of the slot.
   * @return The JPanel representing the slot.
   */
  public JPanel createSlotPanel(String slotTitle, Date savedDate, int id) {
    JPanel slotPanel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.drawImage(slotImage.getImage(), 0, 0, getWidth(), getHeight(), this);
      }

    };
    slotPanel.setOpaque(false);

    // Set rounded border for the main content panel
    Border slotBorder = new RoundBorder(Color.BLACK, 5, 15); // Black rounded border with thickness 5 and radius 15

    // Panel for the left side (slot name and date)
    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.setOpaque(false); // Ensure content panel is transparent

    // Left side (slot name and date)
    JPanel leftPanel = new JPanel(new GridLayout(2, 1));
    leftPanel.setOpaque(false); // Ensure left panel is transparent
    JLabel titleLabel = new JLabel(slotTitle);
    titleLabel.setFont(titleLabel.getFont().deriveFont(20f));
    leftPanel.add(titleLabel);
    JLabel dateLabel = new JLabel(savedDate.toString());
    leftPanel.add(dateLabel);
    contentPanel.add(leftPanel, BorderLayout.WEST);

    slotPanel.add(contentPanel, BorderLayout.CENTER);

    slotPanel.setBorder(slotBorder);

    // Create a panel for buttons (Load Game and Clear Slot)
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Right-aligned layout with spacing
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    buttonPanel.setOpaque(false); // Ensure button panel is transparent

    // Load Game button (colored green)
    TranslatedButton loadGameButton = new TranslatedButton("load-game");
    loadGameButton.setBackground(new Color(50,121,199)); // Set background color to blue
    loadGameButton.setForeground(Color.WHITE);
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
    TranslatedButton clearSlotButton = new TranslatedButton("clear-slot");
    clearSlotButton.setBackground(new Color(164,28,14)); // Set background color to red
    clearSlotButton.setForeground(Color.WHITE);
    clearSlotButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object[] options = { new TranslatedString("yes"), new TranslatedString("no") };
        int response = JOptionPane.showOptionDialog(EventsHandler.getMainPanel(),
            new TranslatedString("delete-confirm"), new TranslatedString("delete-title").getText(),
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        if (response == JOptionPane.YES_OPTION) {
          EventsHandler.getController().clearSlot(id);
          slotPanel.setVisible(false);
        }
      }
    });
    buttonPanel.add(clearSlotButton);

    // Add the buttonPanel to a new JPanel that uses BorderLayout (to handle border
    // layout separately)
    JPanel buttonWrapperPanel = new JPanel(new BorderLayout());
    buttonWrapperPanel.setOpaque(false); // Ensure button wrapper panel is transparent
    buttonWrapperPanel.add(buttonPanel, BorderLayout.NORTH); // Add buttonPanel to the top (NORTH) of the wrapper

    // Add the buttonWrapperPanel to slotPanel on the right side (EAST) but outside
    // the main content panel
    slotPanel.add(buttonWrapperPanel, BorderLayout.EAST);

    return slotPanel;
  }

  /**
   * Renders the slots panel with the given list of slot files.
   * @param slotsPanel The panel containing the slots.
   * @param slotFiles The list of slot files.
   */
  public void renderSlots(JPanel slotsPanel, List<SlotFile> slotFiles) {
    slotsPanel.removeAll();

    slotsPanel.setLayout(new GridBagLayout()); // Set layout to GridBagLayout
    slotsPanel.setOpaque(false); // Ensure the slotsPanel is transparent

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.insets = new Insets(0, 0, 10, 0);

    for (SlotFile slotFile : slotFiles) {
      JPanel slotPanel = createSlotPanel(slotFile.getFilename(), new Date(slotFile.getLastModified()),
          slotFile.getId());
      slotsPanel.add(slotPanel, gbc);
    }
    slotsPanel.revalidate();
    slotsPanel.repaint();
  }

}
