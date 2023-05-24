package com.hibernate.gui;

import java.awt.EventQueue;
import com.hibernate.gui.App;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Opening {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Opening window = new Opening();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Opening() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBienvenidoAlGimnasio = new JLabel("Bienvenido al Gimnasio");
		lblBienvenidoAlGimnasio.setFont(new Font("Dialog", Font.BOLD, 28));
		lblBienvenidoAlGimnasio.setBounds(27, 28, 428, 47);
		frame.getContentPane().add(lblBienvenidoAlGimnasio);
		
		JButton btnEntrar = new JButton("ENTRAR");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				App.main(null);
				frame.dispose();
			}
		});
		btnEntrar.setFont(new Font("Ubuntu Condensed", Font.BOLD, 22));
		btnEntrar.setBounds(128, 112, 171, 79);
		frame.getContentPane().add(btnEntrar);
	}
}
