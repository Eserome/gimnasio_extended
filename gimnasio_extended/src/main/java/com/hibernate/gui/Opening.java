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
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.plaf.metal.MetalLookAndFeel;
import com.jtattoo.*;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Opening {

	private JFrame frmInicioGimnasioSan;
	private JTextField textField_Name;
	private JTextField textField_User;
	private JTextField textField_Pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Opening window = new Opening();
					window.frmInicioGimnasioSan.setVisible(true);
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
		
		try {
			UIManager.setLookAndFeel(new AcrylLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frmInicioGimnasioSan = new JFrame();
		frmInicioGimnasioSan.setTitle("Inicio Gimnasio San Pancracio");
		frmInicioGimnasioSan.getContentPane().setBackground(new Color(255, 128, 128));
		frmInicioGimnasioSan.getContentPane().setBackground(new Color(183, 111, 255));
		frmInicioGimnasioSan.setBounds(100, 100, 450, 300);
		frmInicioGimnasioSan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInicioGimnasioSan.getContentPane().setLayout(null);
		
		JLabel lblBienvenidoAlGimnasio = new JLabel("Bienvenid@!");
		lblBienvenidoAlGimnasio.setForeground(new Color(0, 255, 0));
		lblBienvenidoAlGimnasio.setFont(new Font("OCR A Extended", Font.BOLD, 28));
		lblBienvenidoAlGimnasio.setBounds(10, 10, 211, 47);
		
		frmInicioGimnasioSan.getContentPane().add(lblBienvenidoAlGimnasio);
		
		JButton btnEntrar = new JButton("ENTRAR");
		btnEntrar.setBackground(Color.WHITE);
		btnEntrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnEntrar.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnEntrar.setBackground(Color.WHITE);
			}
		});
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				App.main(null);
				frmInicioGimnasioSan.dispose();
			}
		});
		btnEntrar.setFont(new Font("Ubuntu Condensed", Font.BOLD, 22));
		btnEntrar.setBounds(255, 161, 171, 68);
		frmInicioGimnasioSan.getContentPane().add(btnEntrar);
		
		JLabel lblGimnasioSanPancracio = new JLabel("Gimnasio San Pancracio");
		lblGimnasioSanPancracio.setForeground(new Color(0, 255, 0));
		lblGimnasioSanPancracio.setFont(new Font("OCR A Extended", Font.BOLD, 28));
		lblGimnasioSanPancracio.setBounds(10, 65, 416, 47);
		frmInicioGimnasioSan.getContentPane().add(lblGimnasioSanPancracio);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Dialog", Font.BOLD, 20));
		btnRegistrarse.setBounds(255, 122, 171, 29);
		btnRegistrarse.setBackground(Color.WHITE);
		frmInicioGimnasioSan.getContentPane().add(btnRegistrarse);
		btnRegistrarse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRegistrarse.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnRegistrarse.setBackground(Color.WHITE);
			}
		});
		
		JLabel lblNewLabel = new JLabel("User:");
		lblNewLabel.setFont(new Font("OCR A Extended", Font.PLAIN, 19));
		lblNewLabel.setBounds(10, 161, 60, 29);
		frmInicioGimnasioSan.getContentPane().add(lblNewLabel);
		
		JLabel lblPass = new JLabel("Pass:");
		lblPass.setFont(new Font("OCR A Extended", Font.PLAIN, 19));
		lblPass.setBounds(10, 200, 60, 29);
		frmInicioGimnasioSan.getContentPane().add(lblPass);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("OCR A Extended", Font.PLAIN, 19));
		lblName.setBounds(10, 122, 60, 29);
		frmInicioGimnasioSan.getContentPane().add(lblName);
		
		textField_Name = new JTextField();
		textField_Name.setBounds(72, 122, 173, 29);
		frmInicioGimnasioSan.getContentPane().add(textField_Name);
		textField_Name.setColumns(10);
		
		textField_User = new JTextField();
		textField_User.setColumns(10);
		textField_User.setBounds(72, 161, 173, 29);
		frmInicioGimnasioSan.getContentPane().add(textField_User);
		
		textField_Pass = new JTextField();
		textField_Pass.setColumns(10);
		textField_Pass.setBounds(72, 200, 173, 29);
		frmInicioGimnasioSan.getContentPane().add(textField_Pass);
	}
}
