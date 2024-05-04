package src.views.components;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 * A custom class that handles events for the main frame.
 */
public class EventsHandler {

  private static CardLayout cardLayout;
  private static JPanel mainPanel;
  private static DialogPanel dialog;

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
}
