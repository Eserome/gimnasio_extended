package com.hibernate.gui;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hibernate.dao.ClienteDAO;
import com.hibernate.dao.ClienteEjercicioDAO;
import com.hibernate.dao.EjercicioDAO;
import com.hibernate.model.CE;
import com.hibernate.model.Cliente;
import com.hibernate.model.Ejercicio;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JList;
import java.awt.Dimension;
import javax.swing.DefaultComboBoxModel;
import com.hibernate.gui.App.zona;
import javax.swing.border.LineBorder;


/**
 * Es la clase principal contenedora de la aplicación y gestiona los campos que introduce el entrenador
 * @author a026577592b
 *
 */
public class App {

	/*
	 * Ventana que se visualiza
	 */
	private JFrame frmGimnasioSanPancracio;
	/*
	 * Scrollpane para que la tabla pueda hacer scroll
	 */
	private JScrollPane scrollLista;
	/**
	 * Campos donde el entrenador introduce la información para la creción de clientes y ejercicios
	 */
	private JTextField textField_series;
	private JTextField textField_repeticiones;
	private JTextField textField_cargaEnKg;
	private JTextField textField_nombreEjercicio;
	private JTextField textField_nombreCliente;
	private JTextField textField_apellidosCliente;
	private JTextField textField_edad;
	private JTextField textField_altura;
	private JTextField textField_peso;
	private JTextField textField_idCliente;
	private JTextField textField_idEjercicio;
	private JTextField textField_añadirClienteRutina;
	private JTextField textField_añadirEjercicioCliente;
	/**
	 * Rutas de las imagenes
	 */
	private JTextField picClientTextPath;
	private JTextField picExerciceTextPath;
	/**
	 * Tabla de ejercicios y clientes
	 */
	private JTable tablaEjercicios;
	private JTable tablaClientes;

	/* Enum para determinar el nombre de las zonas de el gimnasio, a las que los ejercicios van a pertenecer */
	public enum zona{
		AEROBICO, MUSCULACION, ZUMBA
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmGimnasioSanPancracio.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
		configuracionOrigen();
	}
	
	/**
	 * Es la configuración incial que se ejecuta al cargar la aplicación que impide que se puedan
	 * seleccionar mas de una fila a la vez
	 */
	public void configuracionOrigen() {
		tablaEjercicios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	/**
	 * Es una función que corrige la entrada del campo altura
	 * para que cuando se introduzca una coma en vez de un punto
	 * lo adapte para que entre correctamente en la BBDD
	 * @param altura Es la altura que se va a introducir en el cliente
	 * @return devuvelve la altura con formato permitido por la base de datos
	 */
	public static String corregirAltura(String altura) {
        Pattern pattern = Pattern.compile(",");
        Matcher matcher = pattern.matcher(altura);
        String alturaCorregida = matcher.replaceFirst(".");
        return alturaCorregida;
    }

	/**
	 * Es una función que comprueba si una imagen tiene formato correcto
	 * para ser representada en la aplicación
	 * @param picPath La ruta de la imagen para comprobar si la extensión es correcta
	 * @return Devuelve true si comprueba que es una imagen a partir de la ruta del archivo
	 */
	public static boolean comprobarSiEsImagen(String picPath) {
		Pattern patternImagen = Pattern.compile(".*\\.(jpg|jpeg|png|gif|bmp)$");
        Matcher matcherImagen = patternImagen.matcher(picPath);
		
		if(matcherImagen.matches()) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "El archivo seleccionado para la imagen del cliente no está permitido (jpg|jpeg|png|gif|bmp)");
			return false;
		}
		
	}
	
	/**
	 * Es una función que permite comprobar 
	 * si los datos introducidos en los campos del apartado Ejercicios son correctos
	 * 
	 * @param textField_nombreEjercicio Es el campo de nombre del ejercicio
	 * @param textField_cargaEnKg Es el campo de la carga en Kg del ejercicio
	 * @param textField_repeticiones Es el campo de las repeticiones del ejercicio
	 * @param textField_series Es el campo de las series que tiene el ejercicio
	 * @param picExerciceTextPath Es el campo de la ruta del archivo de imagen del ejercicio
	 * @return Devuelve true si los datos introducidos en los campos del Ejercicio tienen formato correcto, sinó está establecido mensajes de dialogo indicando el fallo
	 */
	public static boolean comprobarValidezCamposEjercicio(JTextField textField_nombreEjercicio, JTextField textField_cargaEnKg, JTextField textField_repeticiones, JTextField textField_series, JTextField picExerciceTextPath) {
		boolean todoOk = false;
		
		Pattern patternNombre = Pattern.compile("^\\w{1,12}$");
		Matcher matcherNombre = patternNombre.matcher(textField_nombreEjercicio.getText());
		String nombre = textField_nombreEjercicio.getText();
		
		Pattern patternComprobarNumero = Pattern.compile("^\\d+$");
		Matcher	matcherNumero = patternComprobarNumero.matcher(textField_repeticiones.getText());
		boolean esNumero = matcherNumero.matches();
		
		Pattern patternCarga = Pattern.compile("^\\d{1,3}$");
		Matcher matcherCarga = patternCarga.matcher(textField_cargaEnKg.getText());
		
		Pattern patternRepeticiones = Pattern.compile("^\\d{1,3}$");
		Matcher matcherRepeticiones = patternRepeticiones.matcher(textField_repeticiones.getText());
		
		Pattern patternSeries = Pattern.compile("^\\d{1,3}$");
		Matcher matcherSeries = patternCarga.matcher(textField_series.getText());
		
		
		/* Inicializando los valores de repeticiones, carga y series para poder luego asignarles el valor correspondiente si es del mismo tipo */
		int repeticiones = 0;
		int carga = 0;
		int series = 0;
		
		/* Comprobación del campo nombre de ejercicio, si está relleno */
		if(!textField_nombreEjercicio.getText().isEmpty()){
			todoOk = true;
		}else {
			JOptionPane.showMessageDialog(null, "El campo nombre del ejercicio está vacío");
			return false;
		}

		if(matcherNombre.matches()) {
			todoOk=true;
		} else {
			JOptionPane.showMessageDialog(null, "El nombre del ejercicio debe contener máximo 12 carácteres");
			return false;
		}
		
		if(esNumero) {
			repeticiones = Integer.parseInt(textField_repeticiones.getText());
		} else {
			JOptionPane.showMessageDialog(null, "Las repeticiones deben de ser un valor numérico");
			return false;
		}
		
		matcherNumero = patternComprobarNumero.matcher(textField_series.getText());
		esNumero = matcherNumero.matches();
		
		if(esNumero) {
			series = Integer.parseInt(textField_series.getText());
		} else {
			JOptionPane.showMessageDialog(null, "Deberías rellenar las series con un valor numérico");
			return false;
		}
		
		matcherNumero = patternComprobarNumero.matcher(textField_cargaEnKg.getText());
		esNumero = matcherNumero.matches();
		
		if(esNumero) {
			carga = Integer.parseInt(textField_cargaEnKg.getText());
		} else {
			JOptionPane.showMessageDialog(null, "El campo carga debería ser numérico");
			return false;
		}
		
		
		
		
		if(matcherCarga.matches()) {
				if(carga >= 0 && carga <= 256) {
					todoOk=true;
				} else {
					JOptionPane.showMessageDialog(null, "La carga está fuera de los rangos (0-256) KG");
					return false;
				}
		} else {
			JOptionPane.showMessageDialog(null, "La carga debe estar comprendida entre 0-256 KG");
			return false;
		}
	
		if(matcherRepeticiones.matches() && matcherSeries.matches()) {
			if((repeticiones >= 0 && repeticiones <= 100) || (series >= 0 && series <= 100)) {
				todoOk=true;
			} else {
				todoOk=false;
				JOptionPane.showMessageDialog(null, "Las repeticiones o las series están fuera de los rangos (0-100)");
				return false;
			}
		} else {
			todoOk = false;
			JOptionPane.showMessageDialog(null, "Las repeticiones y las series deben estar comprendidas entre 0 y 100");
			return false;
		}
		
		if(comprobarSiEsImagen(picExerciceTextPath.getText())) {
			todoOk=true;
		} else if(picExerciceTextPath.getText().isEmpty()){
			picExerciceTextPath.setText("/com/hibernate/gui/ejercicioInterrogante.png");
			todoOk=true;
		} else {
			return false;
		}
		
		
		return todoOk;
			
		
		
	}
	
	/**
	 * Es una función que permite comprobar 
	 * si los datos introducidos en los campos del apartado Clientes son correctos
	 * @param altura Es la altura formateada para que entre bien en la BBDD
	 * @param textField_altura Es el campo de la altura del Cliente
	 * @param textField_edad Es el campo de la edad del Cliente
	 * @param textField_peso Es el campo del peso del Cliente
	 * @param textField_nombreCliente Es el campo del nombre del Cliente
	 * @param textField_apellidosCliente Es el campo de apellidos del Cliente
	 * @param picClientTextPath Es el campo de la ruta del archivo de la imagen del Cliente
	 * @return Devuelve true si los datos introducidos en los campos del Cliente tienen formato correcto, sinó está establecido mensajes de dialogo indicando el fallo
	 */
	public static boolean comprobarValidezCamposCliente(double altura, JTextField textField_altura, JTextField textField_edad,
			JTextField textField_peso, JTextField textField_nombreCliente, JTextField textField_apellidosCliente,
			JTextField picClientTextPath) {
		boolean todoOk = false;

		Pattern patternComprobarNumero = Pattern.compile("^\\d+$");
		Matcher matcherNumero = patternComprobarNumero.matcher(textField_edad.getText());
		boolean esNumero = matcherNumero.matches();
		
		Pattern patternAltura = Pattern.compile("^\\d?\\.?\\d{1,2}$");
		Matcher matcherAltura = patternAltura.matcher(String.valueOf(altura));


		if (!textField_nombreCliente.getText().isEmpty()) {
			todoOk = true;
		} else {
			todoOk = false;
			JOptionPane.showMessageDialog(null, "El campo nombre está vacío");
			return false;
		}

		if (!textField_apellidosCliente.getText().isEmpty()) {
			todoOk = true;
		} else {
			todoOk = false;
			JOptionPane.showMessageDialog(null, "El campo apellido está vacío");
			return false;
		}
		
		if (matcherAltura.matches() || !textField_altura.getText().isEmpty()) {
			
			if (altura <= 2.99 && altura >= 0.50) {
				todoOk = true;
			} else {
				todoOk = false;
				JOptionPane.showMessageDialog(null, "Altura fuera de los limites permitidos: (0.50-2.99)");
				return false;
			}
		} else {
			todoOk = false;
			JOptionPane.showMessageDialog(null, "El formato de la altura permitido es (0.50 a 2.99)");
			return false;
		}
		
		Pattern patternEdad = Pattern.compile("^\\d{1,2}$");
		Matcher matcherEdad = patternEdad.matcher(textField_edad.getText());
		int edad = 0;
		if(esNumero) {
			edad = Integer.parseInt(textField_edad.getText());
		} else {
			JOptionPane.showMessageDialog(null, "El campo edad no está rellenado con un dato numérico");
		}
		
		if (matcherEdad.matches() || !textField_edad.getText().isEmpty()) {
			if (edad >= 18 && edad <= 99) {
				todoOk = true;
			} else {
				todoOk = false;
				JOptionPane.showMessageDialog(null, "La edad está fuera de los rangos (18-99)");
				return false;
			}
		
		}
		
		Pattern patternPeso = Pattern.compile("^\\d{1,3}$");
		Matcher matcherPeso = patternEdad.matcher(textField_peso.getText());
		
		int peso = 0;
		try {
			peso = Integer.parseInt(textField_peso.getText());

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Deberías introducir un valor numérico en el campo peso");
			return false;
		}
	
		matcherNumero = patternComprobarNumero.matcher(textField_peso.getText());
		
		if(matcherNumero.matches()  || !textField_peso.getText().isEmpty()) {
			if (matcherPeso.matches()) {
				if (peso >= 20 && peso <= 256) {
					todoOk = true;
				} else {
					todoOk = false;
					JOptionPane.showMessageDialog(null, "El peso está fuera de los rangos (20-256) Kg");
					return false;
				}
			} else {
				todoOk = false;
				JOptionPane.showMessageDialog(null, "El peso debe estar comprendido entre 20 y 256 Kg");
				return false;
			}
		} else {
			todoOk=false;
			JOptionPane.showMessageDialog(null, "Deberías rellenar el campo peso con un valor numérico");
			return false;
		}
		
		if(comprobarSiEsImagen(picClientTextPath.getText())) {
			todoOk=true;
		} else if(picClientTextPath.getText().isEmpty()){
			picClientTextPath.setText("/com/hibernate/gui/imagenSinAvatar.png");
			todoOk=true;
		} else {
			return false;
		}
		
		
			return todoOk;
	

		
	}

	/**
	 * Es una función que muestra una imagen en un label concreto a partir de la ruta del archivo
	 * y puede ser posicionada con unos valores concretos para ajustar tamaño y ubicación
	 * @param picPath La ruta del archivo Imagen
	 * @param labelImagen El label donde se va a situar la imagen
	 * @param x Componente x
	 * @param y Componente y
	 * @param z Componente z
	 * @param e Componente e
	 */
	public static void mostrarImagen(String picPath, JLabel labelImagen, int x, int y, int z, int e) {
		try {
            BufferedImage img = ImageIO.read(new File(picPath));

            labelImagen.setBounds(x, y, z, e);

            Image dimg = img.getScaledInstance(labelImagen.getWidth(), labelImagen.getHeight(),
                    Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(dimg);

            labelImagen.setIcon(icon);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(new AcrylLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		frmGimnasioSanPancracio = new JFrame();
		frmGimnasioSanPancracio.getContentPane().setForeground(new Color(0, 255, 0));
		frmGimnasioSanPancracio.getContentPane().setBackground(new Color(183, 111, 255));
		frmGimnasioSanPancracio.setTitle("Gimnasio San Pancracio");
		frmGimnasioSanPancracio.setBounds(100, 100, 1575, 800);
		frmGimnasioSanPancracio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGimnasioSanPancracio.getContentPane().setLayout(null);
		DefaultListModel modeloListaEjercicios = new DefaultListModel();
		
		
		
		
		DefaultTableModel modelClientes = new DefaultTableModel();
		modelClientes.addColumn("id");
		modelClientes.addColumn("Nombre");
		modelClientes.addColumn("Apellidos");
		modelClientes.addColumn("Edad");
		modelClientes.addColumn("Altura");
		modelClientes.addColumn("Peso");
		
		
		DefaultTableModel modelEjercicios = new DefaultTableModel();
		modelEjercicios.addColumn("id");
		modelEjercicios.addColumn("Nombre");
		modelEjercicios.addColumn("Numero de Series");
		modelEjercicios.addColumn("Repeticiones");
		modelEjercicios.addColumn("Carga en Kg");
		modelEjercicios.addColumn("Zona");
		

		DefaultTableModel modelCE = new DefaultTableModel();
		modelCE.addColumn("cliente_id");
		modelCE.addColumn("ejercicio_id");
	
		
		tablaEjercicios = new JTable(modelEjercicios);
		tablaEjercicios.setBackground(new Color(210, 166, 255));
		tablaEjercicios.setBorder(new LineBorder(new Color(128, 0, 255)));
		tablaEjercicios.setGridColor(new Color(128, 0, 255));
		tablaEjercicios.setSelectionBackground(new Color(210, 50, 255));
		tablaEjercicios.getColumnModel().getColumn(0).setPreferredWidth(10);
		tablaEjercicios.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaEjercicios.getColumnModel().getColumn(2).setPreferredWidth(50);
		tablaEjercicios.getColumnModel().getColumn(3).setPreferredWidth(50);
		tablaEjercicios.getColumnModel().getColumn(4).setPreferredWidth(50);
		tablaEjercicios.getColumnModel().getColumn(5).setPreferredWidth(100);
		
		final DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    for(int i=0; i<=5; i++) {
	    	tablaEjercicios.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
	    }
	    
		
		
		picClientTextPath = new JTextField();
		picClientTextPath.setBounds(323, 335, 183, 24);
		frmGimnasioSanPancracio.getContentPane().add(picClientTextPath);
		picClientTextPath.setColumns(10);
		
		
		tablaClientes = new JTable(modelClientes);
		tablaClientes.setBorder(new LineBorder(new Color(128, 0, 255)));
		tablaClientes.setBackground(new Color(210, 166, 255));
		tablaClientes.setGridColor(new Color(128, 0, 255));
		tablaClientes.setSelectionBackground(new Color(210, 50, 255));
		tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(10);
		tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(50);
		tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(100);
		tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(20);
		tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(20);
		tablaClientes.getColumnModel().getColumn(5).setPreferredWidth(20);
		for(int i=0; i<=5; i++) {
			tablaClientes.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
	    }
		
		
		JTable tablaCE = new JTable(modelCE);
		
		
		JScrollPane scrollPaneClientes = new JScrollPane(tablaClientes);
		scrollPaneClientes.setBounds(33, 44, 536, 182);
		frmGimnasioSanPancracio.getContentPane().add(scrollPaneClientes);
		
		JScrollPane scrollPaneEjercicios = new JScrollPane(tablaEjercicios);
		scrollPaneEjercicios.setBounds(591, 44, 536, 182);
		frmGimnasioSanPancracio.getContentPane().add(scrollPaneEjercicios);
		
		JScrollPane scrollPaneCE = new JScrollPane(tablaCE);
		for(int i=0; i<=1; i++) {
			tablaCE.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
	    }
		scrollPaneCE.setBounds(1161, 228, 269, 107);
		frmGimnasioSanPancracio.getContentPane().add(scrollPaneCE);
		
		JComboBox comboBox_MostrarClientes = new JComboBox();
		comboBox_MostrarClientes.setBounds(337, 436, 188, 24);
		frmGimnasioSanPancracio.getContentPane().add(comboBox_MostrarClientes);
		
		JComboBox comboBox_clienteId = new JComboBox();
		
		comboBox_clienteId.setBounds(1161, 343, 269, 24);
		frmGimnasioSanPancracio.getContentPane().add(comboBox_clienteId);
				
		JComboBox comboBox_MostrarEjercicios = new JComboBox();
		comboBox_MostrarEjercicios.setBounds(1161, 379, 269, 24);
		frmGimnasioSanPancracio.getContentPane().add(comboBox_MostrarEjercicios);
		
		JButton btnActualizarTabla = new JButton("");
		btnActualizarTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modelClientes.setRowCount(0);
				List<Cliente> clientes = ClienteDAO.selectAllClientes();
				for(Cliente s: clientes) {
					Object[] row = new Object[6];
					row[0] = s.getId();
					row[1] = s.getNombre();
					row[2] = s.getApellidos();
					row[3] = s.getEdad();
					row[4] = s.getAltura();
					row[5] = s.getPeso();
					modelClientes.addRow(row);
				}
				modelEjercicios.setRowCount(0);
				List<Ejercicio> ejercicios = EjercicioDAO.selectAllEjercicios();
				for(Ejercicio ej: ejercicios) {
					Object[] row = new Object[6];
					row[0] = ej.getId();
					row[1] = ej.getNombre();
					row[2] = ej.getNumeroDeSeries();
					row[3] = ej.getRepeticiones();
					row[4] = ej.getCargaEnKg();
					row[5] = ej.getZona();
					modelEjercicios.addRow(row);
				}
				modelCE.setRowCount(0);
				List<CE> clientesejercicios = ClienteEjercicioDAO.selectAllCES();
				for(CE ce: clientesejercicios) {
					Object[] row = new Object[2];
					row[0] = ce.getCliente_id();
					row[1] = ce.getEjercicio_id();
					modelCE.addRow(row);
				}
				
				comboBox_MostrarClientes.removeAllItems();
				comboBox_clienteId.removeAllItems();
				comboBox_MostrarEjercicios.removeAllItems();
			
				List<Cliente> clientesId = ClienteDAO.selectAllClientes();
				for(Cliente c: clientesId) {
					comboBox_MostrarClientes.addItem(c.getId() + ":" + c.getNombre());

					comboBox_clienteId.addItem(c.getId() + ":" + c.getNombre());
					
				}
				
				List<Ejercicio> ejerciciosId = EjercicioDAO.selectAllEjercicios();
				for(Ejercicio ej: ejerciciosId) {
					comboBox_MostrarEjercicios.addItem(ej.getId() + ":" + ej.getNombre());
				}
				
				
				
			}
		});
		btnActualizarTabla.setBounds(1271, 626, 117, 25);
		frmGimnasioSanPancracio.getContentPane().add(btnActualizarTabla);
		btnActualizarTabla.setVisible(false);
		btnActualizarTabla.doClick();
		
		
		JButton btnGuardar = new JButton("Añadir");
		btnGuardar.setBackground(Color.WHITE);
		btnGuardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGuardar.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnGuardar.setBackground(Color.WHITE);
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					boolean valido = comprobarValidezCamposCliente(Double.parseDouble(corregirAltura(textField_altura.getText())), textField_altura, textField_edad, textField_peso, textField_nombreCliente, textField_apellidosCliente, picClientTextPath);
					
					if(valido) {
						Cliente s = new Cliente(textField_nombreCliente.getText(),textField_apellidosCliente.getText(), Integer.parseInt(textField_edad.getText()),Double.parseDouble(corregirAltura(textField_altura.getText())),Integer.parseInt(textField_peso.getText()),picClientTextPath.getText());
						ClienteDAO.insertCliente(s);
						btnActualizarTabla.doClick();
					} 
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Falta rellenar campos de Cliente");
				}
					
			
		}});
		
		btnGuardar.setBounds(33, 371, 176, 32);
		frmGimnasioSanPancracio.getContentPane().add(btnGuardar);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setBackground(Color.WHITE);
		btnActualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnActualizar.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnActualizar.setBackground(Color.WHITE);
			}
		});
		btnActualizar.setBounds(221, 371, 175, 32);
		frmGimnasioSanPancracio.getContentPane().add(btnActualizar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBackground(Color.WHITE);
		btnBorrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnBorrar.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnBorrar.setBackground(Color.WHITE);
			}
		});
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Cliente c = ClienteDAO.selectClienteByID(Integer.valueOf(textField_idCliente.getText()));
				ClienteDAO.deleteCliente(c.getId());
				btnActualizarTabla.doClick();
				
			}
		});
		btnBorrar.setBounds(408, 371, 161, 32);
		frmGimnasioSanPancracio.getContentPane().add(btnBorrar);
		
		JLabel lblClientes = new JLabel("CLIENTES");
		lblClientes.setForeground(new Color(0, 255, 0));
		lblClientes.setFont(new Font("OCR A Extended", Font.BOLD, 24));
		lblClientes.setBounds(33, 12, 161, 32);
		frmGimnasioSanPancracio.getContentPane().add(lblClientes);
		
		JButton buttonAñadirImagenCliente = new JButton("");
		
		buttonAñadirImagenCliente.setPreferredSize(new Dimension(15, 10));
		buttonAñadirImagenCliente.setBounds(516, 335, 53, 24);
		frmGimnasioSanPancracio.getContentPane().add(buttonAñadirImagenCliente);
		
		buttonAñadirImagenCliente.setIcon(new ImageIcon(App.class.getResource("/com/hibernate/gui/añadirImagen.png")));
		ImageIcon icono = new ImageIcon(App.class.getResource("/com/hibernate/gui/añadirImagen.png"));
		
		Image imagenReducida = icono.getImage().getScaledInstance(20, 20, 5);
		buttonAñadirImagenCliente.setIcon(new ImageIcon(imagenReducida));
		
		JComboBox comboBoxZona = new JComboBox();
		comboBoxZona.setModel(new DefaultComboBoxModel(zona.values()));
		comboBoxZona.setBounds(905, 303, 222, 21);
		frmGimnasioSanPancracio.getContentPane().add(comboBoxZona);
		
		
		JLabel lblClientes_1 = new JLabel("EJERCICIOS");
		lblClientes_1.setForeground(new Color(0, 255, 0));
		lblClientes_1.setFont(new Font("OCR A Extended", Font.BOLD, 24));
		lblClientes_1.setBounds(592, 12, 160, 32);
		frmGimnasioSanPancracio.getContentPane().add(lblClientes_1);
		
		JButton btnGuardarEjercicio = new JButton("Añadir");
		btnGuardarEjercicio.setBackground(Color.WHITE);
		btnGuardarEjercicio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGuardarEjercicio.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnGuardarEjercicio.setBackground(Color.WHITE);
			}
		});
		btnGuardarEjercicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					boolean valido = comprobarValidezCamposEjercicio(textField_nombreEjercicio, textField_cargaEnKg, textField_repeticiones, textField_series, picExerciceTextPath);
					
					if(valido) {
						Ejercicio ej = new Ejercicio(textField_nombreEjercicio.getText(),Integer.parseInt(textField_series.getText()), Integer.parseInt(textField_repeticiones.getText()),Integer.parseInt(textField_cargaEnKg.getText()),picExerciceTextPath.getText(), comboBoxZona.getSelectedItem().toString());
						EjercicioDAO.insertEjercicio(ej);
						btnActualizarTabla.doClick();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Falta rellenar campos del Ejercicio");
				}
				
						
					
				
				
				
			}
		});
		btnGuardarEjercicio.setBounds(591, 371, 168, 32);
		frmGimnasioSanPancracio.getContentPane().add(btnGuardarEjercicio);
		
		JButton btnActualizar_1 = new JButton("Actualizar");
		btnActualizar_1.setBackground(Color.WHITE);
		btnActualizar_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnActualizar_1.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnActualizar_1.setBackground(Color.WHITE);
			}
		});
		btnActualizar_1.setBounds(771, 371, 183, 32);
		frmGimnasioSanPancracio.getContentPane().add(btnActualizar_1);
		
		JButton btnBorrar_1 = new JButton("Borrar");
		btnBorrar_1.setBackground(Color.WHITE);
		btnBorrar_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnBorrar_1.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnBorrar_1.setBackground(Color.WHITE);
			}
		});
		btnBorrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ejercicio ej = EjercicioDAO.selectEjercicioByID(Integer.valueOf(textField_idEjercicio.getText()));
				EjercicioDAO.deleteEjercicio(ej.getId());
				btnActualizarTabla.doClick();
			}
		});
		btnBorrar_1.setBounds(966, 371, 161, 32);
		frmGimnasioSanPancracio.getContentPane().add(btnBorrar_1);
		
		JButton buttonAñadirImagenEjercicio = new JButton("");
		
		buttonAñadirImagenEjercicio.setBounds(1074, 335, 53, 24);
		frmGimnasioSanPancracio.getContentPane().add(buttonAñadirImagenEjercicio);
		
		buttonAñadirImagenEjercicio.setIcon(new ImageIcon(imagenReducida));
		
		JLabel lblEjerciciosDelCliente = new JLabel("RUTINA PARA");
		lblEjerciciosDelCliente.setForeground(new Color(0, 255, 0));
		lblEjerciciosDelCliente.setFont(new Font("OCR A Extended", Font.BOLD, 24));
		lblEjerciciosDelCliente.setBounds(33, 428, 231, 32);
		frmGimnasioSanPancracio.getContentPane().add(lblEjerciciosDelCliente);
		
		
		
		JButton btnBorrarRutina = 
				new JButton("Borrar");
		btnBorrarRutina.setBackground(Color.WHITE);
		btnBorrarRutina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnBorrarRutina.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnBorrarRutina.setBackground(Color.WHITE);
			}
		});
		btnBorrarRutina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String cliente_id = comboBox_clienteId.getSelectedItem().toString();
				String[] clienting = cliente_id.split(":");
				String idClienteExtraido = clienting[0];
				
				String ejercicio_id = comboBox_MostrarEjercicios.getSelectedItem().toString();
				String[] ejercicing = ejercicio_id.split(":");
				String idEjercicioExtraido = ejercicing[0];
				
				CE ce = new CE(Integer.parseInt(idClienteExtraido), Integer.parseInt(idEjercicioExtraido));
				ClienteEjercicioDAO.deleteCE(ce);
				btnActualizarTabla.doClick();
				
			}
		});
		btnBorrarRutina.setBounds(1442, 228, 81, 170);
		frmGimnasioSanPancracio.getContentPane().add(btnBorrarRutina);
		
		
		
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNombre.setForeground(new Color(0, 255, 0));
		lblNombre.setBounds(751, 245, 70, 15);
		frmGimnasioSanPancracio.getContentPane().add(lblNombre);
		
		JLabel lblRepeticiones = new JLabel("Repeticiones:");
		lblRepeticiones.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblRepeticiones.setForeground(new Color(0, 255, 0));
		lblRepeticiones.setBounds(751, 267, 94, 24);
		frmGimnasioSanPancracio.getContentPane().add(lblRepeticiones);
		
		JLabel lblSeries = new JLabel("Series:");
		lblSeries.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblSeries.setForeground(new Color(0, 255, 0));
		lblSeries.setBounds(895, 271, 53, 15);
		frmGimnasioSanPancracio.getContentPane().add(lblSeries);
		
		textField_series = new JTextField();
		textField_series.setBounds(943, 267, 37, 24);
		frmGimnasioSanPancracio.getContentPane().add(textField_series);
		textField_series.setColumns(10);
		
		textField_repeticiones = new JTextField();
		textField_repeticiones.setColumns(10);
		textField_repeticiones.setBounds(849, 267, 37, 25);
		frmGimnasioSanPancracio.getContentPane().add(textField_repeticiones);
		
		JLabel lblCargaEnKg = new JLabel("Carga en KG:");
		lblCargaEnKg.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblCargaEnKg.setForeground(new Color(0, 255, 0));
		lblCargaEnKg.setBounds(998, 267, 98, 20);
		frmGimnasioSanPancracio.getContentPane().add(lblCargaEnKg);
		
		textField_cargaEnKg = new JTextField();
		textField_cargaEnKg.setColumns(10);
		textField_cargaEnKg.setBounds(1090, 265, 37, 25);
		frmGimnasioSanPancracio.getContentPane().add(textField_cargaEnKg);
		
		textField_nombreEjercicio = new JTextField();
		textField_nombreEjercicio.setColumns(10);
		textField_nombreEjercicio.setBounds(824, 238, 223, 25);
		frmGimnasioSanPancracio.getContentPane().add(textField_nombreEjercicio);
		
		JLabel lblNombre_1 = new JLabel("Nombre:");
		lblNombre_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNombre_1.setForeground(new Color(0, 255, 0));
		lblNombre_1.setBounds(177, 244, 70, 15);
		frmGimnasioSanPancracio.getContentPane().add(lblNombre_1);
		
		textField_nombreCliente = new JTextField();
		textField_nombreCliente.setColumns(10);
		textField_nombreCliente.setBounds(249, 238, 147, 25);
		frmGimnasioSanPancracio.getContentPane().add(textField_nombreCliente);
		
		JLabel lblClientes_1_1 = new JLabel("AFEGIR RUTINA");
		lblClientes_1_1.setForeground(new Color(0, 255, 0));
		lblClientes_1_1.setFont(new Font("OCR A Extended", Font.BOLD, 24));
		lblClientes_1_1.setBounds(1161, 12, 227, 32);
		frmGimnasioSanPancracio.getContentPane().add(lblClientes_1_1);
		
		JLabel lblCliente = new JLabel("Cliente:id");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblCliente.setForeground(new Color(0, 255, 0));
		lblCliente.setBounds(1162, 44, 70, 15);
		frmGimnasioSanPancracio.getContentPane().add(lblCliente);
		
		JLabel lblCliente_1 = new JLabel("Ejercicio:id");
		lblCliente_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblCliente_1.setForeground(new Color(0, 255, 0));
		lblCliente_1.setBounds(1299, 44, 99, 15);
		frmGimnasioSanPancracio.getContentPane().add(lblCliente_1);
		
		textField_añadirClienteRutina = new JTextField();
		textField_añadirClienteRutina.setBounds(1162, 59, 123, 19);
		frmGimnasioSanPancracio.getContentPane().add(textField_añadirClienteRutina);
		textField_añadirClienteRutina.setColumns(10);
		
		textField_añadirEjercicioCliente = new JTextField();
		textField_añadirEjercicioCliente.setColumns(10);
		textField_añadirEjercicioCliente.setBounds(1297, 59, 123, 19);
		frmGimnasioSanPancracio.getContentPane().add(textField_añadirEjercicioCliente);
		
		JLabel lblClientes_1_1_1 = new JLabel("BORRAR RUTINA");
		lblClientes_1_1_1.setForeground(new Color(0, 255, 0));
		lblClientes_1_1_1.setFont(new Font("OCR A Extended", Font.BOLD, 24));
		lblClientes_1_1_1.setBounds(1161, 187, 227, 53);
		frmGimnasioSanPancracio.getContentPane().add(lblClientes_1_1_1);
		
		
		textField_apellidosCliente = new JTextField();
		textField_apellidosCliente.setColumns(10);
		textField_apellidosCliente.setBounds(249, 267, 320, 25);
		frmGimnasioSanPancracio.getContentPane().add(textField_apellidosCliente);
		
		JLabel lblNombre_1_1 = new JLabel("Apellidos:");
		lblNombre_1_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNombre_1_1.setForeground(new Color(0, 255, 0));
		lblNombre_1_1.setBounds(177, 270, 70, 24);
		frmGimnasioSanPancracio.getContentPane().add(lblNombre_1_1);
		
		textField_edad = new JTextField();
		textField_edad.setColumns(10);
		textField_edad.setBounds(381, 299, 53, 25);
		frmGimnasioSanPancracio.getContentPane().add(textField_edad);
		
		JLabel lblEdad = new JLabel("Edad:");
		lblEdad.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblEdad.setForeground(new Color(0, 255, 0));
		lblEdad.setBounds(337, 302, 62, 21);
		frmGimnasioSanPancracio.getContentPane().add(lblEdad);
		
		JLabel lblAltura = new JLabel("Altura(m):");
		lblAltura.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblAltura.setForeground(new Color(0, 255, 0));
		lblAltura.setBounds(177, 300, 87, 25);
		frmGimnasioSanPancracio.getContentPane().add(lblAltura);
		
		textField_altura = new JTextField();
		textField_altura.setColumns(10);
		textField_altura.setBounds(249, 301, 62, 25);
		frmGimnasioSanPancracio.getContentPane().add(textField_altura);
		
		JLabel lblPeso = new JLabel("Peso(Kg):");
		lblPeso.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblPeso.setForeground(new Color(0, 255, 0));
		lblPeso.setBounds(474, 303, 59, 21);
		frmGimnasioSanPancracio.getContentPane().add(lblPeso);
		
		textField_peso = new JTextField();
		textField_peso.setColumns(10);
		textField_peso.setBounds(526, 298, 43, 25);
		frmGimnasioSanPancracio.getContentPane().add(textField_peso);
		
		JLabel lblNombre_1_2 = new JLabel("id:");
		lblNombre_1_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNombre_1_2.setForeground(new Color(0, 255, 0));
		lblNombre_1_2.setBounds(504, 244, 31, 15);
		frmGimnasioSanPancracio.getContentPane().add(lblNombre_1_2);
		
		textField_idCliente = new JTextField();
		textField_idCliente.setColumns(10);
		textField_idCliente.setBounds(526, 239, 43, 25);
		frmGimnasioSanPancracio.getContentPane().add(textField_idCliente);
		textField_idCliente.setEnabled(false);
		
		JLabel lblNombre_1_2_1 = new JLabel("id:");
		lblNombre_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNombre_1_2_1.setForeground(new Color(0, 255, 0));
		lblNombre_1_2_1.setBounds(1065, 243, 31, 15);
		frmGimnasioSanPancracio.getContentPane().add(lblNombre_1_2_1);
		
		textField_idEjercicio = new JTextField();
		textField_idEjercicio.setEnabled(false);
		textField_idEjercicio.setColumns(10);
		textField_idEjercicio.setBounds(1090, 238, 37, 25);
		frmGimnasioSanPancracio.getContentPane().add(textField_idEjercicio);
		
		JButton btnAñadirRutina= new JButton("Añadir");
		btnAñadirRutina.setBackground(Color.WHITE);
		btnAñadirRutina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAñadirRutina.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAñadirRutina.setBackground(Color.WHITE);
			}
		});
		btnAñadirRutina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String cliente_id = textField_añadirClienteRutina.getText();
				String[] clienting = cliente_id.split(":");
				String idClienteExtraido = clienting[1];
				String ejercicio_id = textField_añadirEjercicioCliente.getText();
				String[] ejercicing = ejercicio_id.split(":");
				String idEjercicioExtraido = ejercicing[1];
				CE ce = new CE(Integer.parseInt(idClienteExtraido), Integer.parseInt(idEjercicioExtraido));
				ClienteEjercicioDAO.insertClienteEjercicio(ce);
				btnActualizarTabla.doClick();
			}
		});
		btnAñadirRutina.setBounds(1443, 59, 80, 135);
		frmGimnasioSanPancracio.getContentPane().add(btnAñadirRutina);
		
		tablaCE.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tablaCE.getSelectedRow();
				TableModel model = tablaCE.getModel();
				
				int idCliente =  Integer.parseInt( model.getValueAt(index, 0).toString());
				int idEjercicio =  Integer.parseInt( model.getValueAt(index, 1).toString());
	
				Cliente c = ClienteDAO.selectClienteByID(idCliente);
				Ejercicio ej = EjercicioDAO.selectEjercicioByID(idEjercicio);
				
				
				
				comboBox_clienteId.setSelectedItem(idCliente + ":" + ClienteDAO.selectClienteByID(idCliente).getNombre());
				comboBox_MostrarEjercicios.setSelectedItem(idEjercicio + ":" + EjercicioDAO.selectEjercicioByID(idEjercicio).getNombre());
			}
		});
		
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 410, 1539, 15);
		frmGimnasioSanPancracio.getContentPane().add(separator);
		
		JTextArea textArea_EjerciciosCliente = new JTextArea();
		textArea_EjerciciosCliente.setBounds(32, 466, 658, 274);
		frmGimnasioSanPancracio.getContentPane().add(textArea_EjerciciosCliente);
		
		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.setBackground(Color.WHITE);
		btnMostrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnMostrar.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnMostrar.setBackground(Color.WHITE);
			}
		});
		btnMostrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String cliente_id = comboBox_MostrarClientes.getSelectedItem().toString();
				String[] clienting = cliente_id.split(":");
				String idClienteExtraido = clienting[0];
				
				textArea_EjerciciosCliente.setText("LISTA DE EJERCICIOS PARA EL CLIENTE\n------------------------------------------------------------------------------------------");
				
				List<CE> listaEjerciciosCliente = ClienteEjercicioDAO.selectEjerciciosByClienteID(Integer.parseInt(idClienteExtraido));
				Cliente c = ClienteDAO.selectClienteByID(Integer.parseInt(idClienteExtraido));
				textArea_EjerciciosCliente.setText("          " + textArea_EjerciciosCliente.getText() + ": \n\n	" + c.getNombre() + " " + c.getApellidos() + "\n\n Edad: " + c.getEdad() + "	Altura: " + c.getAltura() + " metros	Peso: " + c.getPeso() + " Kg\n\n------------------------------------------------------------------------------------------");
				textArea_EjerciciosCliente.setText(textArea_EjerciciosCliente.getText() + "\nEjercicio		" + "Nº de Series	" + "Repeticiones	" + "Carga en KG\n------------------------------------------------------------------------------------------");
				
				for(CE ce : listaEjerciciosCliente) {
					Ejercicio ej = EjercicioDAO.selectEjercicioByID(ce.getEjercicio_id());
					textArea_EjerciciosCliente.setText(textArea_EjerciciosCliente.getText() + "\n" + ej.getNombre() + "		" + ej.getNumeroDeSeries() + "	" + ej.getRepeticiones() + "	" + ej.getCargaEnKg());
				}
				
				textArea_EjerciciosCliente.setText(textArea_EjerciciosCliente.getText() + "\n\nÁNIMO CON EL ENTRENE!");
			}
		});
		btnMostrar.setBounds(537, 437, 153, 25);
		frmGimnasioSanPancracio.getContentPane().add(btnMostrar);
		
		JLabel lblImagenCliente = new JLabel("Imagen del cliente");
		lblImagenCliente.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblImagenCliente.setForeground(new Color(0, 255, 0));
		lblImagenCliente.setBounds(179, 339, 146, 15);
		frmGimnasioSanPancracio.getContentPane().add(lblImagenCliente);
		
		JLabel labelImagenCliente = new JLabel("");
		labelImagenCliente.setBounds(33, 238, 132, 123);
		frmGimnasioSanPancracio.getContentPane().add(labelImagenCliente);
		
		labelImagenCliente.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel lblImagenDelEjercicio = new JLabel("Imagen del ejercicio");
		lblImagenDelEjercicio.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblImagenDelEjercicio.setForeground(new Color(0, 255, 0));
		lblImagenDelEjercicio.setBounds(751, 339, 146, 15);
		frmGimnasioSanPancracio.getContentPane().add(lblImagenDelEjercicio);
		
		picExerciceTextPath = new JTextField();
		picExerciceTextPath.setColumns(10);
		picExerciceTextPath.setBounds(902, 337, 160, 24);
		frmGimnasioSanPancracio.getContentPane().add(picExerciceTextPath);
		
		JLabel labelImagenEjercicio = new JLabel("");
		labelImagenEjercicio.setBorder(BorderFactory.createLineBorder(Color.black));
		labelImagenEjercicio.setBounds(591, 236, 132, 123);
		frmGimnasioSanPancracio.getContentPane().add(labelImagenEjercicio);
		
		JLabel labelMostrarImgCliente = new JLabel("");
		labelMostrarImgCliente.setBorder(BorderFactory.createLineBorder(Color.black));
		labelMostrarImgCliente.setBounds(1161, 82, 123, 112);
		frmGimnasioSanPancracio.getContentPane().add(labelMostrarImgCliente);
		
		JLabel labelMostrarImgEjercicio = new JLabel("");
		labelMostrarImgEjercicio.setBorder(BorderFactory.createLineBorder(Color.black));
		labelMostrarImgEjercicio.setBounds(1299, 82, 123, 112);
		frmGimnasioSanPancracio.getContentPane().add(labelMostrarImgEjercicio);
		
		JButton btnZonaZumba = new JButton("Ver clientes");
		btnZonaZumba.setBackground(Color.WHITE);
		btnZonaZumba.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnZonaZumba.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnZonaZumba.setBackground(Color.WHITE);
			}
		});
		btnZonaZumba.setBounds(1136, 668, 119, 43);
		frmGimnasioSanPancracio.getContentPane().add(btnZonaZumba);
		
		JButton btnZonaMusculacion = new JButton("Ver clientes");
		btnZonaMusculacion.setBackground(Color.WHITE);
		btnZonaMusculacion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnZonaMusculacion.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnZonaMusculacion.setBackground(Color.WHITE);
			}
		});
		btnZonaMusculacion.setBounds(1218, 541, 119, 43);
		frmGimnasioSanPancracio.getContentPane().add(btnZonaMusculacion);
		
		JButton btnZonaAerobica = new JButton("Ver clientes");
		btnZonaAerobica.setBackground(Color.WHITE);
		btnZonaAerobica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnZonaAerobica.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnZonaAerobica.setBackground(Color.WHITE);
			}
		});
		btnZonaAerobica.setBounds(1020, 541, 117, 43);
		frmGimnasioSanPancracio.getContentPane().add(btnZonaAerobica);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(App.class.getResource("/com/hibernate/gui/mapaGimnasio.png")));
		lblNewLabel.setBounds(830, 428, 600, 300);
		frmGimnasioSanPancracio.getContentPane().add(lblNewLabel);
		
		JLabel lblSeleccionaZonaA = new JLabel("Zona a la que pertenece el ej.");
		lblSeleccionaZonaA.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblSeleccionaZonaA.setForeground(new Color(0, 255, 0));
		lblSeleccionaZonaA.setBounds(751, 301, 168, 24);
		frmGimnasioSanPancracio.getContentPane().add(lblSeleccionaZonaA);
		
		
		
		
		buttonAñadirImagenCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
		        chooser.showOpenDialog(null);
		        File f= chooser.getSelectedFile();
		        String fileName= f.getAbsolutePath();
		        picClientTextPath.setText(fileName);
		        
		        mostrarImagen(picClientTextPath.getText(),labelImagenCliente,33, 238, 132, 123); 	
		        
			}
		});
		
		buttonAñadirImagenEjercicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
		        chooser.showOpenDialog(null);
		        File f= chooser.getSelectedFile();
		        String fileName= f.getAbsolutePath();
		        picExerciceTextPath.setText(fileName);
		        
		        mostrarImagen(picExerciceTextPath.getText(),labelImagenEjercicio,591, 236, 132, 123);
		        
			}
		});
		
		tablaClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tablaClientes.getSelectedRow();
				TableModel model = tablaClientes.getModel();
				
				textField_idCliente.setText(model.getValueAt(index, 0).toString());
				textField_nombreCliente.setText(model.getValueAt(index, 1).toString());
				textField_apellidosCliente.setText(model.getValueAt(index, 2).toString());
				textField_edad.setText(model.getValueAt(index, 3).toString());
				textField_altura.setText(model.getValueAt(index, 4).toString());
				textField_peso.setText(model.getValueAt(index, 5).toString());
				textField_añadirClienteRutina.setText(model.getValueAt(index, 1).toString() + ":" +  model.getValueAt(index, 0).toString());
				picClientTextPath.setText(ClienteDAO.selectClienteByID(Integer.parseInt(textField_idCliente.getText())).getPicPath());
				
				Cliente c1 = ClienteDAO.selectClienteByID(Integer.parseInt(textField_idCliente.getText()));
				if(picClientTextPath.getText().compareTo("/com/hibernate/gui/imagenSinAvatar.png") == 0) {
					labelMostrarImgCliente.setIcon(new ImageIcon(App.class.getResource("/com/hibernate/gui/imagenSinAvatar.png")));
					ImageIcon icono = new ImageIcon(App.class.getResource("/com/hibernate/gui/imagenSinAvatar.png"));
					
					Image imagenReducida = icono.getImage().getScaledInstance(123, 123, 112);
					labelMostrarImgCliente.setIcon(new ImageIcon(imagenReducida));
					labelImagenCliente.setIcon(new ImageIcon(imagenReducida));
				} else if(comprobarSiEsImagen(c1.getPicPath())) {
					picClientTextPath.setText(c1.getPicPath());
					mostrarImagen(ClienteDAO.selectClienteByID(Integer.parseInt(textField_idCliente.getText())).getPicPath(), labelMostrarImgCliente , 1161, 82, 123, 112);
					mostrarImagen(picClientTextPath.getText(),labelImagenCliente,33, 238, 132, 123); 
				} else {
					labelImagenCliente.setIcon(null);
					labelMostrarImgCliente.setIcon(null);
				}
				
				
				
			}
		});
		
		tablaEjercicios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
						int index = tablaEjercicios.getSelectedRow();
						TableModel model = tablaEjercicios.getModel();
						textField_idEjercicio.setText(model.getValueAt(index, 0).toString());
						textField_nombreEjercicio.setText(model.getValueAt(index, 1).toString());
						textField_series.setText(model.getValueAt(index, 2).toString());
						textField_repeticiones.setText(model.getValueAt(index, 3).toString());
						textField_cargaEnKg.setText(model.getValueAt(index, 4).toString());
						textField_añadirEjercicioCliente.setText(model.getValueAt(index, 1).toString() + ":" + model.getValueAt(index, 0).toString());
						picExerciceTextPath.setText(EjercicioDAO.selectEjercicioByID(Integer.parseInt(textField_idEjercicio.getText())).getPicPath());
						Ejercicio e1 = EjercicioDAO.selectEjercicioByID(Integer.parseInt(textField_idEjercicio.getText()));
						
						String zona = e1.getZona();
						if(zona.compareTo("AEROBICO")==0) {
							comboBoxZona.setSelectedIndex(0);
						} else if(zona.compareTo("MUSCULACION")==0) {
							comboBoxZona.setSelectedIndex(1);
						} else {
							comboBoxZona.setSelectedIndex(2);
						}
					
						
						if(picExerciceTextPath.getText().compareTo("/com/hibernate/gui/ejercicioInterrogante.png") == 0) {
							labelMostrarImgEjercicio.setIcon(new ImageIcon(App.class.getResource("/com/hibernate/gui/ejercicioInterrogante.png")));
							ImageIcon icono = new ImageIcon(App.class.getResource("/com/hibernate/gui/ejercicioInterrogante.png"));
							
							Image imagenReducida = icono.getImage().getScaledInstance(123, 123, 112);
							labelMostrarImgEjercicio.setIcon(new ImageIcon(imagenReducida));
							labelImagenEjercicio.setIcon(new ImageIcon(imagenReducida));
						} else if(comprobarSiEsImagen(e1.getPicPath())) {
							picExerciceTextPath.setText(e1.getPicPath());
							mostrarImagen(EjercicioDAO.selectEjercicioByID(Integer.parseInt(textField_idEjercicio.getText())).getPicPath(), labelMostrarImgEjercicio , 1299, 82, 123, 112);
							mostrarImagen(picExerciceTextPath.getText(),labelImagenEjercicio,591, 236, 132, 123); 
						} else {
							labelImagenEjercicio.setIcon(null);
							labelMostrarImgEjercicio.setIcon(null);
						}
					}
		});
		
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					try {
						double altura = Double.parseDouble(corregirAltura(textField_altura.getText()));
						boolean valido = comprobarValidezCamposCliente(altura, textField_altura, textField_edad, textField_peso, textField_nombreCliente, textField_apellidosCliente, picClientTextPath);
					
						if(valido) {
							Cliente c = ClienteDAO.selectClienteByID(Integer.valueOf(textField_idCliente.getText()));
							c.setNombre(textField_nombreCliente.getText());
							c.setApellidos(textField_apellidosCliente.getText());
							c.setEdad(Integer.valueOf(Integer.parseInt(textField_edad.getText())));
							c.setAltura(altura);
							c.setPeso(Integer.parseInt(textField_peso.getText()));
							c.setPicPath(picClientTextPath.getText());
							ClienteDAO.updateCliente(c);
							btnActualizarTabla.doClick();
							
							
							if(picClientTextPath.getText().compareTo("/com/hibernate/gui/imagenSinAvatar.png") == 0) {
								labelMostrarImgCliente.setIcon(new ImageIcon(App.class.getResource("/com/hibernate/gui/imagenSinAvatar.png")));
								ImageIcon icono = new ImageIcon(App.class.getResource("/com/hibernate/gui/imagenSinAvatar.png"));
								
								Image imagenReducida = icono.getImage().getScaledInstance(123, 123, 112);
								labelMostrarImgCliente.setIcon(new ImageIcon(imagenReducida));
								labelImagenCliente.setIcon(new ImageIcon(imagenReducida));
							} else if(comprobarSiEsImagen(c.getPicPath())) {
								picClientTextPath.setText(c.getPicPath());
								mostrarImagen(ClienteDAO.selectClienteByID(Integer.parseInt(textField_idCliente.getText())).getPicPath(), labelMostrarImgCliente , 1161, 82, 123, 112);
								mostrarImagen(picClientTextPath.getText(),labelImagenCliente,33, 238, 132, 123); 
							} else {
								labelImagenCliente.setIcon(null);
								labelMostrarImgCliente.setIcon(null);
							}
							
						}
					
					
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Faltan campos por completar");
					
				
					}
			}
		});
		
		btnActualizar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					boolean valido = comprobarValidezCamposEjercicio(textField_nombreEjercicio, textField_cargaEnKg, textField_repeticiones, textField_series, picExerciceTextPath);
				
					if(valido) {

						Ejercicio ej = EjercicioDAO.selectEjercicioByID(Integer.valueOf(textField_idEjercicio.getText()));
						ej.setNombre(textField_nombreEjercicio.getText());
						ej.setNumeroDeSeries(Integer.parseInt(textField_series.getText()));
						ej.setRepeticiones(Integer.valueOf(Integer.parseInt(textField_repeticiones.getText())));
						ej.setCargaEnKg(Integer.parseInt(textField_cargaEnKg.getText()));
						ej.setPicPath(picExerciceTextPath.getText().toString());
						ej.setZona(comboBoxZona.getSelectedItem().toString());
						EjercicioDAO.updateEjercicio(ej);
						btnActualizarTabla.doClick();
						
						if(picExerciceTextPath.getText().compareTo("/com/hibernate/gui/ejercicioInterrogante.png") == 0) {
							labelMostrarImgEjercicio.setIcon(new ImageIcon(App.class.getResource("/com/hibernate/gui/ejercicioInterrogante.png")));
							ImageIcon icono = new ImageIcon(App.class.getResource("/com/hibernate/gui/ejercicioInterrogante.png"));
							
							Image imagenReducida = icono.getImage().getScaledInstance(123, 123, 112);
							labelMostrarImgEjercicio.setIcon(new ImageIcon(imagenReducida));
							labelImagenEjercicio.setIcon(new ImageIcon(imagenReducida));
						} else if(comprobarSiEsImagen(ej.getPicPath())) {
							picExerciceTextPath.setText(ej.getPicPath());
							mostrarImagen(EjercicioDAO.selectEjercicioByID(Integer.parseInt(textField_idEjercicio.getText())).getPicPath(), labelMostrarImgEjercicio , 1299, 82, 123, 112);
							mostrarImagen(picExerciceTextPath.getText(),labelImagenEjercicio,591, 236, 132, 123); 
						} else {
							labelImagenEjercicio.setIcon(null);
							labelMostrarImgEjercicio.setIcon(null);
						}
					}
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Faltan campos por rellenar");
					System.out.println(e1);
				}
				
				
				
				
				
				
				
				
				
				
			}
		});
		
		
		
		btnZonaAerobica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				HashMap<Cliente, Ejercicio> rutinasClienteEjercicio = new HashMap<>(); 
				List<Integer> clientesZonaAerobica = new ArrayList<>();
				String zonaAerobica = "AEROBICO\n-----\n";
				for(CE ce: ClienteEjercicioDAO.selectAllCES()) {
					if(EjercicioDAO.selectEjercicioByID(ce.getEjercicio_id()).getZona().compareTo("AEROBICO") == 0) {
						if(clientesZonaAerobica.contains(ce.getCliente_id())) {
							continue;
						} else {
							zonaAerobica += (ClienteDAO.selectClienteByID(ce.getCliente_id()).getNombre() + "\n");
							clientesZonaAerobica.add(ce.getCliente_id());
						}
						
					}
					//rutinasClienteEjercicio.put(ClienteDAO.selectClienteByID(ce.getCliente_id()), EjercicioDAO.selectEjercicioByID(ce.getEjercicio_id()));
					
				}
				JOptionPane.showMessageDialog(null, zonaAerobica);
			}
			
		});
		
		btnZonaMusculacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				HashMap<Cliente, Ejercicio> rutinasClienteEjercicio = new HashMap<>(); 
				List<Integer> clientesZonaMusculacion = new ArrayList<>();
				String zonaMusculacion = "MUSCULACION\n-----\n";
				for(CE ce: ClienteEjercicioDAO.selectAllCES()) {
					if(EjercicioDAO.selectEjercicioByID(ce.getEjercicio_id()).getZona().compareTo("MUSCULACION") == 0) {
						if(clientesZonaMusculacion.contains(ce.getCliente_id())) {
							continue;
						} else {
							zonaMusculacion += (ClienteDAO.selectClienteByID(ce.getCliente_id()).getNombre() + "\n");
							clientesZonaMusculacion.add(ce.getCliente_id());
						}
					}
					//rutinasClienteEjercicio.put(ClienteDAO.selectClienteByID(ce.getCliente_id()), EjercicioDAO.selectEjercicioByID(ce.getEjercicio_id()));
					
				}
				JOptionPane.showMessageDialog(null, zonaMusculacion);
			}
		});
		
		btnZonaZumba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				HashMap<Cliente, Ejercicio> rutinasClienteEjercicio = new HashMap<>(); 
				List<Integer> clientesZonaZumba = new ArrayList<>();
				String zonaZumba ="ZUMBA\n-----\n";
				for(CE ce: ClienteEjercicioDAO.selectAllCES()) {
					if(EjercicioDAO.selectEjercicioByID(ce.getEjercicio_id()).getZona().compareTo("ZUMBA") == 0) {
						if(clientesZonaZumba.contains(ce.getCliente_id())) {
							continue;
						} else {
							zonaZumba += (ClienteDAO.selectClienteByID(ce.getCliente_id()).getNombre() + "\n");
							clientesZonaZumba.add(ce.getCliente_id());
						}
					}
					//rutinasClienteEjercicio.put(ClienteDAO.selectClienteByID(ce.getCliente_id()), EjercicioDAO.selectEjercicioByID(ce.getEjercicio_id()));
					
				}
				JOptionPane.showMessageDialog(null, zonaZumba);
			}
		});
		
		
	}
	}
