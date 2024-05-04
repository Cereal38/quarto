package src.views.components;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 * A custom class that handles events for the main frame.
 */
public class EventsHandler {

  private static CardLayout cardLayout;
  private static JPanel mainPanel;

  public static void setCardLayout(CardLayout cardLayout) {
    EventsHandler.cardLayout = cardLayout;
  }

  public static void setMainPanel(JPanel mainPanel) {
    EventsHandler.mainPanel = mainPanel;
  }

  public static void navigate(String destination) {
    cardLayout.show(mainPanel, destination);
  }
}
