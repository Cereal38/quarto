package src.views.load.save;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import src.views.components.BorderCenterPanel;
import src.views.components.GoBackButton;
import src.views.components.ImageThemed;
import src.views.components.TranslatedLabel;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

public class LoadPage extends JPanel implements ThemeListener {
  private LoadHelper helper;
  JPanel slotsPanel;
  private ImageThemed bgImage = new ImageThemed("bg-board.png");
  private ImageThemed topbarImage = new ImageThemed("topbar.png");

  public LoadPage() {
    ThemeUtils.addThemeListener(this);
    this.helper = new LoadHelper(this);

    setLayout(new BorderLayout());

    // Create and add the "Go Back" button with an image
    GoBackButton backButton = new GoBackButton();
    // Create top panel and add the "Go Back" button to it
    JPanel topPanel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(topbarImage.getImage(), 0, 0, getWidth(), getHeight(), this);
      }
    };
    topPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
    topPanel.add(backButton, BorderLayout.WEST);

    // Add top panel to the north of LoadPage
    add(topPanel, BorderLayout.NORTH);

    // Create the title label and add it to the center of the top panel
    TranslatedLabel titleLabel = new TranslatedLabel("load-page");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(topbarImage.getImage(), 0, 0, getWidth(), getHeight(), this);
      }
    };
    labelPanel.add(titleLabel);
    topPanel.add(labelPanel, BorderLayout.CENTER);

    // Initialize the slots panel with GridLayout
    this.slotsPanel = new JPanel(new GridLayout(10, 1, 10, 10));

    // Render slots initially
    renderSlots();

    // Create a scroll pane for the slots panel
    BorderCenterPanel center = new BorderCenterPanel(slotsPanel, 10, 100, 10, 100) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
      }

    };
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

  @Override
  public void updatedTheme() {
    repaint();
  }
}
