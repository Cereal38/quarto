package src.views.components;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class CustomTextField extends JPanel {

    private JTextField textField;

    public CustomTextField(TranslatedString key, String tooltipText) {
        super();
        String labelText = key.getText();

        // Create label with specified text
        JLabel label = new JLabel(labelText);

        // Create text field with specified tooltip
        textField = new JTextField(20); // Adjust size as needed
        textField.setToolTipText(tooltipText);

        // Set layout for the panel (using FlowLayout by default)
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Left-aligned with spacing

        // Add label and text field to the panel
        add(label);
        add(textField);
    }

    public String getInputText() {
        return textField.getText();
    }

    public void setText(TranslatedString key) {
        String s = key.getText();
        textField.setText(s);
    }

    public void setEditable(boolean editable) {
        textField.setEditable(editable);
    }

    public Document getDocument() {
        return textField.getDocument();
    }
}
