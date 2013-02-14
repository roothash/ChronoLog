package com.caterpillar.ui;

/* @author vkaivallya@gmail.com
 * */
import java.awt.Color;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class CaterpillarTableModel extends AbstractTableModel {
	private String[] columnNames = new String[]{"Name", "Path", "TimeZone",
			"Type", "Has Stack", "Delim", "Date RegEx", "Date Style",
			"Ignore Char", ""};
	private Object[][] data = {};

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}

	public CaterpillarTableModel() {

	}

	public void setColumns(JTable table) {

		this.setUpTZColumn(table, table.getColumnModel().getColumn(2));
		this.setUpTraceColumn(table, table.getColumnModel().getColumn(4));
		this.setUpTypeColumn(table, table.getColumnModel().getColumn(3));
		this.setUpValidBox(table, table.getColumnModel().getColumn(9),
				Color.green);
		this.setUpDelimColumn(table, table.getColumnModel().getColumn(5));
		this.setUpIgnoreColumn(table, table.getColumnModel().getColumn(8));

	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		if (data != null)
			return data.length;
		else
			return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return data[arg0][arg1];
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public void setValueAt(Object value, int row, int col) {

		System.out.println("Setting value at " + row + "," + col + " to "
				+ value + " (an instance of " + value.getClass() + ")");

		data[row][col] = value;
		fireTableCellUpdated(row, col);

		System.out.println("New value of data");

	}
	public void SetIcon(JTable table, int col_index, ImageIcon icon, String name) {
		table.getTableHeader().getColumnModel().getColumn(col_index)
				.setHeaderRenderer(new CaterpillarCellRenderer());

	}

	public void setUpTZColumn(JTable table, TableColumn tzColumn) {
		// Set up the editor for the sport cells.
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("GMT-1200");
		comboBox.addItem("GMT-1100");
		comboBox.addItem("GMT-1000");
		comboBox.addItem("GMT-0930");
		comboBox.addItem("GMT-0900");
		comboBox.addItem("GMT-0800");
		comboBox.addItem("GMT-0700");
		comboBox.addItem("GMT-0600");
		comboBox.addItem("GMT-0500");
		comboBox.addItem("GMT-0430");
		comboBox.addItem("GMT-0400");
		comboBox.addItem("GMT-0330");
		comboBox.addItem("GMT-0300");
		comboBox.addItem("GMT-0200");
		comboBox.addItem("GMT-0100");
		comboBox.addItem("GMT");
		comboBox.addItem("GMT+0000");
		comboBox.addItem("GMT+0100");
		comboBox.addItem("GMT+0200");
		comboBox.addItem("GMT+0300");
		comboBox.addItem("GMT+0330");
		comboBox.addItem("GMT+0400");
		comboBox.addItem("GMT+0430");
		comboBox.addItem("GMT+0500");
		comboBox.addItem("GMT+0530");
		comboBox.addItem("GMT+0545");
		comboBox.addItem("GMT+0600");
		comboBox.addItem("GMT+0630");
		comboBox.addItem("GMT+0700");
		comboBox.addItem("GMT+0800");
		comboBox.addItem("GMT+0900");
		comboBox.addItem("GMT+0930");
		comboBox.addItem("GMT+1000");
		comboBox.addItem("GMT+1030");
		comboBox.addItem("GMT+1100");
		comboBox.addItem("GMT+1130");
		comboBox.addItem("GMT+1200");
		comboBox.addItem("GMT+1245");
		comboBox.addItem("GMT+1300");
		comboBox.addItem("GMT+1400");

		tzColumn.setCellEditor(new DefaultCellEditor(comboBox));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Select a TimeZone");
		tzColumn.setPreferredWidth(10);

		// renderer.setBackground(Color.yellow);
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		tzColumn.setCellRenderer(renderer);
	}

	public void setUpTraceColumn(JTable table, TableColumn traceColumn) {
		// Set up the editor for the sport cells.
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("1");
		comboBox.addItem("0");

		traceColumn.setCellEditor(new DefaultCellEditor(comboBox));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Does the log contain Stacktrace? 0=No,1=yes");
		// renderer.setBackground(Color.yellow);
		traceColumn.setMaxWidth(6);
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		traceColumn.setCellRenderer(renderer);
	}

	public void setUpValidBox(JTable table, TableColumn traceColumn, Color c) {
		// Set up the editor for the sport cells.
		// JCheckBox chkBx = new JCheckBox();

		// /traceColumn.setCellEditor(new DefaultCellEditor());
		DefaultTableCellRenderer renderer = new CaterpillarCellRenderer();
		renderer.setToolTipText("Parseable File Or not");
		// renderer.setBackground(c);
		traceColumn.setMaxWidth(6);
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		traceColumn.setCellRenderer(renderer);
	}

	public void setUpTypeColumn(JTable table, TableColumn typeColumn) {
		// Set up the editor for the sport cells.
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("LOG4J");
		comboBox.addItem("W3SVC");

		typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Two Log Formats are Supported");
		// renderer.setBackground(Color.yellow);
		typeColumn.setPreferredWidth(10);
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		typeColumn.setCellRenderer(renderer);
	}

	public void setUpIgnoreColumn(JTable table, TableColumn IgnoreColumn) {

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Lines with this starting char will be ignored.");
		// renderer.setBackground(c);
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		IgnoreColumn.setMaxWidth(6);
		IgnoreColumn.setCellRenderer(renderer);
	}
	public void setUpDelimColumn(JTable table, TableColumn DelimColumn) {

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Delimiter in File for different fields");
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		// renderer.setBackground(106);
		DelimColumn.setPreferredWidth(5);
		DelimColumn.setCellRenderer(renderer);
	}

	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (col < 1) {
			return false;
		} else {
			return false;
		}
	}
}