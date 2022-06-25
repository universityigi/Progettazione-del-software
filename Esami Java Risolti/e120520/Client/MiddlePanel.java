package e120520.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class MiddlePanel extends JPanel {
    private final ArrayList<NumberField> fieldList = new ArrayList<>();

    public MiddlePanel() {
        for (int i=0; i<5; i++) {
            NumberField n = new NumberField();
            fieldList.add(n);
            add(n);
        }
        setSize(140,700);
    }

    public int countGreenNumbers() {
        int res = 0;
        for (NumberField n : fieldList) {
            if (n.isGreen()) res += 1;
        }
        return res;
    }

    public boolean isFilled() {
        boolean res = true;
        for (NumberField n : fieldList) {
            res = res && !n.getText().equals("");
        }
        return res;
    }

    public void resetNumbers() {
        for (NumberField n : fieldList) {
            n.setText("");
        }
    }
    public void disableChange() {
        for (NumberField n : fieldList) {
            n.setEditable(false);
        }
    }
    public void enableChange() {
        for (NumberField n : fieldList) {
            n.setEditable(true);
        }
    }

    public void resetColor() {
        for (NumberField n : fieldList) {
            n.changeColor(Color.LIGHT_GRAY);
        }
    }

    public ArrayList<NumberField> getFieldList() {
        return fieldList;
    }
}
