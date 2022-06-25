package e120520.Client;

import javax.swing.*;
import java.awt.*;

class NumberField extends JFormattedTextField {
    private Color color = Color.LIGHT_GRAY;

    public NumberField() {
        super(""); //useful for testing purpose
        setColumns(1);
        setSize(140,140);

        update();
    }

    public void changeColor(Color c) {
        color = c;
        update();
    }
    public boolean isGreen() {
        return color.equals(Color.GREEN);
    }
    private void update() {
        setBackground(color);
    }


}
