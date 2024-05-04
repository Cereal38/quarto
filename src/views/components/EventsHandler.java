package src.views.components;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
 * A custom class that handles events for the main frame.
 */
public class EventsHandler {

  private static CardLayout cardLayout;
  private static JPanel mainPanel;
  private static DialogPanel dialog;
  private static int theme = 0; // 0: light, 1: dark

  public static void setCardLayout(CardLayout cardLayout) {
    EventsHandler.cardLayout = cardLayout;
  }

  public static void setMainPanel(JPanel mainPanel) {
    EventsHandler.mainPanel = mainPanel;
  }

  public static void setDialog(DialogPanel dialog) {
    EventsHandler.dialog = dialog;
  }

  /**
   * Navigates to the specified destination in the application.
   *
   * @param destination the destination to navigate to
   */
  public static void navigate(String destination) {
    cardLayout.show(mainPanel, destination);
  }

  /**
   * Displays a dialog with the specified content.
   *
   * @param dialogContent the content to be displayed in the dialog
   */
  public static void showDialog(JPanel dialogContent) {
    dialog.setContent(dialogContent);
    dialog.setVisible(true);
  }

  /**
   * Hides the dialog.
   */
  public static void hideDialog() {
    dialog.setVisible(false);
  }

  /**
   * Toggles the theme of the application.
   */
  public static void toggleTheme() {
    // TODO: Theme must be fixed
    theme = (theme + 1) % 2;
    switch (theme) {
    case 0:
      UIManager.put("Panel.background", new ColorUIResource(java.awt.Color.WHITE));
      UIManager.put("Panel.foreground", new ColorUIResource(java.awt.Color.BLACK));
      UIManager.put("Button.background", new ColorUIResource(java.awt.Color.WHITE));
      UIManager.put("Button.foreground", new ColorUIResource(java.awt.Color.BLACK));
      UIManager.put("Label.foreground", new ColorUIResource(java.awt.Color.BLACK));
      UIManager.put("Button.border", BorderFactory.createLineBorder(Color.GRAY));
      break;
    case 1:
      UIManager.put("Panel.background", new ColorUIResource(java.awt.Color.DARK_GRAY));
      UIManager.put("Panel.foreground", new ColorUIResource(java.awt.Color.WHITE));
      UIManager.put("Button.background", new ColorUIResource(java.awt.Color.DARK_GRAY));
      UIManager.put("Button.foreground", new ColorUIResource(java.awt.Color.BLACK));
      UIManager.put("Label.foreground", new ColorUIResource(java.awt.Color.WHITE));
      UIManager.put("Button.border", BorderFactory.createLineBorder(Color.LIGHT_GRAY));
      break;
    }
    // Update the interface after changing the theme
    SwingUtilities.updateComponentTreeUI(mainPanel);
  }
}
