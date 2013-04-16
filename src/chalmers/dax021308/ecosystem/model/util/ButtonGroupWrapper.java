package chalmers.dax021308.ecosystem.model.util;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

public class ButtonGroupWrapper extends ButtonGroup {

	public String getSelectedButtonText() {
		for (Enumeration<AbstractButton> buttons = this.getElements(); buttons
				.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				return button.getText();
			}
		}

		return null;
	}

	public void selectButtonByText(String s) {
		for (Enumeration<AbstractButton> buttons = this.getElements(); buttons
				.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.getText().equals(s)) {
				button.setSelected(true);
			}
		}
	}
}
