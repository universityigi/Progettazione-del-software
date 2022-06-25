package e120919.PedinaButton;

import javax.swing.*;
import java.awt.*;

public class PedinaButton extends JPanel {

	public PedinaButton() {
		setBackground(Color.LIGHT_GRAY);
		setVisible(true);
		setOpaque(true);
		setSize(50,50);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public void changeColor(Color c) {
		setBackground(c);
	}

	public boolean checkColor(Color c) {
		return getBackground().equals(c);
	}
}
