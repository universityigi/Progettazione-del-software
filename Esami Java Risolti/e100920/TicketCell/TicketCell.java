package e100920.TicketCell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TicketCell extends JLabel {
    private boolean selected = false;

    public TicketCell(int value) {
        super(String.valueOf(value));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(true);
    }

    public int getValue() {
       return Integer.parseInt(getText());
    }


    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            setBackground(Color.GREEN);
        } else {
            setBackground(Color.WHITE);
        }
    }

    public boolean isSelected() {
        return selected;
    }

}
