package modelpackage;

import javafx.scene.control.Tooltip;

public class ToolTipWindow {



	public static Tooltip crateToolTip(String message) {

		Tooltip toolTip = new Tooltip();
		toolTip.setText(message);
		toolTip.setGraphicTextGap(0.0);

		return toolTip;
	}
}
