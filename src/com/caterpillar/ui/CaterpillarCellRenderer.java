package com.caterpillar.ui;

/* @author vkaivallya@gmail.com
 * */
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.caterpillar.launcher.CaterPillar;

public class CaterpillarCellRenderer extends DefaultTableCellRenderer {
	URL url = CaterPillar.class.getResource("/ok.jpg");
	Image image1 = Toolkit.getDefaultToolkit().getImage(url);
	URL url2 = CaterPillar.class.getResource("/cancel.jpg");
	Image image2 = Toolkit.getDefaultToolkit().getImage(url2);
	ImageIcon icon = new ImageIcon(image1);
	ImageIcon iconCancel = new ImageIcon(image2);

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		String isValid = (String) value;
		if (isValid.equalsIgnoreCase("yes")) {
			setIcon(icon);
		} else {
			setIcon(iconCancel);

		}
		this.setToolTipText(isValid);
		return this;
	}
}