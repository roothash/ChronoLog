package com.caterpillar.ui;

/* @author vkaivallya@gmail.com
 * */
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.caterpillar.launcher.CaterPillar;

public class About extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public About() {
		setResizable(false);

		setTitle("Chrono-Log");
		URL url = CaterPillar.class.getResource("/caterpillar.jpg");
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		// setIconImage(Toolkit.getDefaultToolkit().getImage("META-INF\\caterpillar.jpg"));
		setIconImage((image));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 455, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(image));
		lblNewLabel.setBounds(0, 0, 145, 142);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Chrono-Log");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(153, 11, 89, 14);
		contentPane.add(lblNewLabel_1);

		JTextPane txtpnArrangesLogsFrom = new JTextPane();
		txtpnArrangesLogsFrom.setBackground(UIManager
				.getColor("Panel.background"));
		txtpnArrangesLogsFrom
				.setText("Arranges logs from multiple files in chronological order.");
		txtpnArrangesLogsFrom.setEditable(false);
		txtpnArrangesLogsFrom.setBounds(153, 28, 244, 34);
		contentPane.add(txtpnArrangesLogsFrom);

		JLabel lblVersion = new JLabel("Version");
		lblVersion.setBounds(153, 73, 60, 14);
		contentPane.add(lblVersion);

		JLabel lblNewLabel_2 = new JLabel("0.1");
		lblNewLabel_2.setBounds(226, 73, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblDeveloper = new JLabel("Developer");
		lblDeveloper.setBounds(153, 87, 60, 14);
		contentPane.add(lblDeveloper);

		JLabel lblNewLabel_3 = new JLabel("vineetk[kaivnlp@gmail.com]");
		lblNewLabel_3.setBounds(226, 87, 189, 14);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Contribs");
		lblNewLabel_4.setBounds(152, 104, 50, 14);
		contentPane.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("naman[nyadav@egain.com]");
		lblNewLabel_5.setBounds(226, 104, 189, 14);
		contentPane.add(lblNewLabel_5);

	}
}
