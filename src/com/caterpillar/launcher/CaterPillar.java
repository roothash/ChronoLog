package com.caterpillar.launcher;

/* @author kaivnlp@gmail.com
 * */
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.caterpillar.dataobject.FileData;
import com.caterpillar.parsers.FileTypeChecker;
import com.caterpillar.ui.About;
import com.caterpillar.ui.CaterpillarTableModel;

public class CaterPillar implements ActionListener, PropertyChangeListener {

	private JFrame frmChronolog;
	private JTextField inputFolder;
	private JTextField outputFolder;
	private JTable table;
	private Object[][] tbldata;
	private CaterpillarTableModel tblmodel;
	private JProgressBar progressBar;
	private static Logger logger = Logger
			.getLogger("com.caterpillar.launcher.CaterPillar");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			LoggerCat.initLogger();
			logger.setLevel(Level.ALL);
			logger.addHandler(LoggerCat.HANDLER);

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, e1.getMessage());
		} catch (Exception e1) {

			logger.log(Level.SEVERE, e1.getMessage());
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaterPillar window = new CaterPillar();
					window.frmChronolog.setVisible(true);
					window.frmChronolog.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CaterPillar() {

		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		logger.log(Level.INFO, "Initializing UI");
		frmChronolog = new JFrame();
		frmChronolog.setTitle("Chrono-Log");
		frmChronolog.getContentPane().setBackground(new Color(192, 192, 192));
		frmChronolog.setResizable(false);

		URL url = CaterPillar.class.getResource("/caterpillar.jpg");

		Image image = Toolkit.getDefaultToolkit().getImage(url);
		frmChronolog.setIconImage(image);
		frmChronolog.setBounds(100, 100, 950, 433);
		frmChronolog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChronolog.getContentPane().setLayout(null);
		/*
		 * Menubar constructions
		 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(192, 192, 192));
		menuBar.setBounds(0, 0, 932, 21);
		frmChronolog.getContentPane().add(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Close");
		mntmNewMenuItem.setBackground(new Color(192, 192, 192));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmChronolog.dispose();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenu mnAbout = new JMenu("About");
		mnAbout.setBackground(new Color(192, 192, 192));
		menuBar.add(mnAbout);

		JMenuItem mntmAboutCaterpillar = new JMenuItem("About caterpillar");
		mntmAboutCaterpillar.setBackground(new Color(192, 192, 192));
		mntmAboutCaterpillar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				About awindow = new About();
				awindow.setLocationRelativeTo(frmChronolog);
				awindow.setVisible(true);
				// frmChronolog.dispose();
			}
		});
		mnAbout.add(mntmAboutCaterpillar);

		final JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(0, 21, 942, 355);
		frmChronolog.getContentPane().add(panel);
		panel.setLayout(null);

		final JLabel lblChooseFilesFrom = new JLabel("Choose Application Logs");
		lblChooseFilesFrom.setBounds(10, 11, 145, 14);
		panel.add(lblChooseFilesFrom);

		final JButton browseBtn = new JButton("Browse");
		browseBtn.setHorizontalTextPosition(SwingConstants.LEFT);
		browseBtn.setFont(new Font("Tahoma", Font.PLAIN, 9));

		browseBtn.setBounds(385, 9, 65, 19);
		panel.add(browseBtn);

		inputFolder = new JTextField();
		inputFolder.setBackground(new Color(250, 250, 210));
		inputFolder.setBounds(180, 8, 205, 20);
		panel.add(inputFolder);
		inputFolder.setColumns(10);

		JLabel lblSelectOutputFile = new JLabel("Select Output File Name");
		lblSelectOutputFile.setBounds(10, 281, 159, 14);
		panel.add(lblSelectOutputFile);

		outputFolder = new JTextField();
		outputFolder.setBounds(10, 307, 321, 20);
		panel.add(outputFolder);
		outputFolder.setColumns(10);
		outputFolder.setText("chrono-log" + "_" + System.currentTimeMillis()
				+ ".txt");

		final JButton btnNewButton_3 = new JButton("Go!!");
		btnNewButton_3.setBackground(new Color(248, 248, 255));
		btnNewButton_3.setForeground(Color.BLACK);
		btnNewButton_3.setBounds(385, 308, 65, 19);
		panel.add(btnNewButton_3);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(10, 55, 866, 198);
		panel.add(scrollPane);

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setBorder(null);

		tblmodel = new CaterpillarTableModel();// DefaultTableModel();
		table.setModel(tblmodel);

		tblmodel.setColumns(table);
		scrollPane.setViewportView(table);

		JSeparator separator = new JSeparator();
		scrollPane.setColumnHeaderView(separator);

		final JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tblmodel.setData(new Object[0][0]);
				tblmodel.fireTableDataChanged();
			}
		});
		btnNewButton_1.setBackground(new Color(192, 192, 192));
		btnNewButton_1.setToolTipText("Clear Table");
		URL url2 = CaterPillar.class.getResource("/trash.jpg");
		Image image2 = Toolkit.getDefaultToolkit().getImage(url2);
		btnNewButton_1.setIcon(new ImageIcon(image2));
		btnNewButton_1.setBounds(879, 55, 51, 40);
		panel.add(btnNewButton_1);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 353, 942, 2);
		panel.add(separator_1);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(
				176, 224, 230), null));
		progressBar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		progressBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
			}
		});
		progressBar.setBackground(new Color(192, 192, 192));
		progressBar.setBounds(461, 378, 481, 16);
		progressBar.setForeground(SystemColor.desktop);
		frmChronolog.getContentPane().add(progressBar);

		final JLabel statusText = new JLabel("");
		statusText.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(
				173, 216, 230), null));
		statusText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		statusText.setBounds(0, 378, 458, 16);
		frmChronolog.getContentPane().add(statusText);

		browseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(".");

				fc.setMultiSelectionEnabled(true);
				if (inputFolder.getText() != null
						&& !inputFolder.getText().trim().equals(""))
					fc.setCurrentDirectory(new File(inputFolder.getText()));

				int returnVal = fc.showOpenDialog(frmChronolog);

				// Here it is closed. What is chosen?
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					inputFolder.setText(fc.getCurrentDirectory()
							.getAbsolutePath());
					inputFolder.setToolTipText(fc.getCurrentDirectory()
							.getAbsolutePath());
					File[] selFiles = fc.getSelectedFiles();
					tbldata = new Object[selFiles.length][10];
					for (int i = 0; i <= selFiles.length - 1; i++) {

						FileTypeChecker fsChecker = new FileTypeChecker(
								selFiles[i].getAbsolutePath());
						ArrayList<?> fattrib = fsChecker.getFileType();
						if (fattrib.size() > 0) {
							tbldata[i][0] = selFiles[i].getName();
							tbldata[i][1] = selFiles[i].getAbsolutePath();
							tbldata[i][2] = fattrib.get(1);
							tbldata[i][3] = fattrib.get(0);
							tbldata[i][4] = fattrib.get(2);
							tbldata[i][5] = fattrib.get(3);
							tbldata[i][6] = fattrib.get(4);
							tbldata[i][7] = fattrib.get(5);
							tbldata[i][8] = fattrib.get(6);
							tbldata[i][9] = "yes";

						} else {
							tbldata[i][0] = selFiles[i].getName();
							tbldata[i][1] = selFiles[i].getAbsolutePath();
							tbldata[i][2] = "";
							tbldata[i][3] = "";
							tbldata[i][4] = "";
							tbldata[i][5] = "";
							tbldata[i][6] = "";
							tbldata[i][7] = "";
							tbldata[i][8] = "";
							tbldata[i][9] = "no";

						}

					}
				}

				tblmodel.setData(tbldata);
				tblmodel.fireTableDataChanged();

			}

		});

		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rowCount = table.getModel().getRowCount();
				int colCount = table.getModel().getColumnCount();
				List<FileData> flist = new ArrayList<FileData>();

				btnNewButton_1.setEnabled(false);

				for (int i = 0; i <= rowCount - 1; i++) {
					String rowString = "";
					for (int j = 0; j <= colCount - 1; j++) {

						if (j == 0) {
							rowString = (String) table.getModel().getValueAt(i,
									j);
						} else {
							rowString = rowString + ","
									+ table.getModel().getValueAt(i, j);
						}
					}
					logger.log(Level.FINE, "File Row on UI " + rowString);
					FileData f1 = new FileData(rowString);
					flist.add(f1);
				}

				logger.log(Level.INFO, "Calling Sequencer with File Data List "
						+ flist);
				Sequencer chrono = new Sequencer(flist, outputFolder.getText(),
						statusText, btnNewButton_1);
				chrono.addPropertyChangeListener(new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if ("progress".equals(evt.getPropertyName())) {
							progressBar.setValue((Integer) evt.getNewValue());
							// System.out.println(evt.getNewValue());
						}

					}

				});
				chrono.execute();

			}
		});

	}

	@SuppressWarnings("serial")
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

}
