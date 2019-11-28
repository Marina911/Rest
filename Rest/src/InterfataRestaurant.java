import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.proteanit.sql.DbUtils;

public class InterfataRestaurant {

	private JFrame frame;
	private JTextField txtNumeAngajat;
	private JPasswordField txtParolaAngajat;
	private JTable tableOspatar;
	private JLabel lblMeniuBucatar;
	private JTable tableBucatar;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField dataR;
	private JTextField numeRez;
	private JTextField prenumeRez;
	private JTextField oraR;
	private JTextField telefonRez;
	private JTextField txtDate;
	private JTextField txtOra;
	private JTextField txtCodComanda;
	TextArea vizBon = new TextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfataRestaurant window = new InterfataRestaurant();
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
	public InterfataRestaurant() {
		initialize();
	}
	
	JComboBox comboBoxNrMasa = new JComboBox();
	JComboBox comboBoxFelMancare = new JComboBox();
	JComboBox comboBoxNrMasaBon = new JComboBox();
	private JTextField txtNrPortiiOspatar;
	private JTextField txtCodBuc;
	private JTextField txtCodBarman;
	private JTable tableBarman;
	private JTable tableStoc;
	private JTextField txtCantitate;
	private JTextField txtDataConsum;
	private JTextField txtNumeProdusStoc;
	private JTextField txtFunctie;
	private JTextField txtSalar;
	private JTextField txtNumeAngajat2;
	private JTextField txtDataAngajarii;
	private JTable tableAngajati;
	private JTextField txtParola;
	private JTextField nrePresRez;
	private JTable tableMeniu;
	private JTable tableRezervari;
	private JComboBox nrMasa;
	private JTextField textField;
	private JTextField textField_1;

	public void comboBoxMese() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT nrmasa FROM masa";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {

				if(comboBoxNrMasa.getItemCount() != 4 ) {
					comboBoxNrMasa.addItem(rs.getString("nrmasa"));
				//	comboBoxNrMasaBon.addItem(rs.getString("nrMasa"));
					System.out.println(rs.getString("nrmasa"));
				}
				
			}
	
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void comboBoxMeseB() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT nrmasa FROM masa";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {

				if(comboBoxNrMasaBon.getItemCount() != 4 ) {
					comboBoxNrMasaBon.addItem(rs.getString("nrmasa"));
					System.out.println(rs.getString("nrmasa"));
				}
				
			}
	
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void bon() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT nume_fel_meniu, den_fel, pret" + 
					" FROM fel f, meniu m" + 
					" WHERE f.codm = m.codm " + 
					" AND den_fel IN (SELECT felComandat" + 
					" FROM comanda" + 
					" WHERE stareComanda = 'Terminata'" + 
					" AND stareComandaOspatar = 'Servita'" + 
					" AND nrmasa = ?)";
			PreparedStatement pst = connection.prepareStatement(query);
			String nr = (String) comboBoxNrMasaBon.getSelectedItem();
			pst.setString(1, nr);
			ResultSet rs = pst.executeQuery();
			
			int refs = 1325 + (int) (Math.random()*4238);
			
			Calendar time = Calendar.getInstance();
			time.getTime();
			SimpleDateFormat tTime = new SimpleDateFormat("HH:mm:ss");
			tTime.format(time.getTime());
			SimpleDateFormat tDate = new SimpleDateFormat("dd-MM-yyyy");
			tDate.format(time.getTime());
			
			vizBon.append("\tSistem Gestiune Restaurat - PIE - GRUPA1_CEA_MAI_TARE:\n\n"+
						"Reference:\t\t\t   "+refs+
						"\nData si ora:        "+tDate.format(time.getTime())+", "+tTime.format(time.getTime())+"\n"+
						"Ospatar:     "+txtNumeAngajat.getText()+"\n"+
						"Numar masa:       "+nr+
						"\n__________________________________________________________\n"
						);
			int total = 0;
			while(rs.next()) {
				String nume_fel_meniu = rs.getString("nume_fel_meniu");
				String den_fel = rs.getString("den_fel");
				String pret = rs.getString("pret");
				
				int pretInt = Integer.parseInt(pret);
				if(pretInt != 0) {
					total += pretInt;
				}
				
		     	vizBon.append("\nCategorie fel:"+nume_fel_meniu+",    |     Fel: "+den_fel+",   |    Pret: " +pret+"\n");
			}
			
			vizBon.append("\n_____________________________________________________________\n");
			vizBon.append("Total de plata:                  |                     "+total+"\n");
			vizBon.append("\n_____________________________________________________________\n");
			vizBon.append("\t\t~Multumim, va mai asteptam!~\n");
	
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
		
		
	};
	
	JComboBox comboBox_1 = new JComboBox();
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	public void comboBoxComenziAcasa() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT den_fel FROM fel";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();


				while(rs.next()) {
					
					if(comboBox_1.getItemCount() != 6 ) {
						comboBox_1.addItem(rs.getString("den_fel"));
						System.out.println(rs.getString("den_fel"));
					}
			}
	
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void comboBoxStoc() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT den_prod_stoc FROM stoc";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {

				if(comboBoxNrMasa.getItemCount() != 4 ) {
					comboBoxNrMasa.addItem(rs.getString("nrmasa"));
					System.out.println(rs.getString("nrmasa"));
				}
				
			}
	
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	
	public void comboBoxFelMancare() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT den_fel FROM fel";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				if(comboBoxFelMancare.getItemCount() != 13 ) {
					comboBoxFelMancare.addItem(rs.getString("den_fel"));
					System.out.println(rs.getString("den_fel"));
				}
			}
	
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableOspatar() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT * FROM comanda WHERE stareComandaOspatar = 'In curs de servire'";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableOspatar.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableBucatar() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT * FROM comanda WHERE stareComanda = 'In curs de preparare' AND felComandat IN ('Capriciosa', 'Omleta', 'Diavola', 'Degetele de pui', 'Piept de pui la gratar', 'Friptura pui si cartofi pai','Carbonara')";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableBucatar.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableStoc() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT den_prod_stoc, cant, dataConsum FROM stoc WHERE expirat IS NULL";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableStoc.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableStocExpirat() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT den_prod_stoc, cant, dataConsum FROM stoc WHERE expirat IS NOT NULL";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableStoc.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableStocEpuizat() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT den_prod_stoc, cant, dataConsum FROM stoc WHERE cant = 0";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableStoc.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableBarman() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT * FROM comanda WHERE stareComanda = 'In curs de preparare' AND felComandat IN ('Ursus', 'Mojito', 'Lipton', 'Prigat', 'Ursus-fara alcool', 'Tuborg-bruna')";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableBarman.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableAngajati() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT nume_angajat, parola_angajat, functie, salar, data_angajarii FROM angajati, salgr WHERE data_plecarii IS NULL AND angajati.salg = salgr.salg";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableAngajati.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableAngajatiActivi() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT nume_angajat, parola_angajat, functie, salar, data_angajarii FROM angajati, salgr WHERE data_plecarii IS NULL AND angajati.salg = salgr.salg";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableAngajati.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableAngajatiInactivi() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT nume_angajat, parola_angajat, functie, salar, data_angajarii FROM angajati, salgr WHERE data_plecarii IS NOT NULL AND angajati.salg = salgr.salg";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableAngajati.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableArhivaAngajati() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT nume_angajat, parola_angajat, functie, salar, data_angajarii FROM angajati, salgr WHERE angajati.salg = salgr.salg";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableAngajati.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableMeniu() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT nume_fel_meniu, den_fel, gramaj, calorii, pret " + 
							"FROM meniu, fel " + 
							"WHERE meniu.codm = fel.codm";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableMeniu.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};
	
	public void refreshTableRezervari() {
		try {
			Connection connection = ConexiuneBD.getConnection();
			String query = "SELECT * FROM rezervare";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			tableRezervari.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
	};

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1085, 546);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel inregistrare = new JPanel();
		frame.getContentPane().add(inregistrare, "name_147272842642215");
		inregistrare.setLayout(null);
		
		JLabel label = new JLabel("Inregistrare aplicatie restaurant");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 30));
		label.setBounds(55, 13, 502, 45);
		inregistrare.add(label);
		
		JLabel lblNumeAngajat = new JLabel("Nume Angajat:");
		lblNumeAngajat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNumeAngajat.setBounds(55, 122, 139, 34);
		inregistrare.add(lblNumeAngajat);
		
		JLabel lblParolaAngajat = new JLabel("Parola:");
		lblParolaAngajat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblParolaAngajat.setBounds(122, 178, 72, 34);
		inregistrare.add(lblParolaAngajat);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(45, 96, 521, 13);
		inregistrare.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(45, 246, 521, 13);
		inregistrare.add(separator_1);
		
		JPanel meniuOspatar = new JPanel();
		meniuOspatar.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				refreshTableOspatar();
			}
		});
		
		JPanel meniuAlimente = new JPanel();
		
		JPanel meniuManager = new JPanel();
		meniuManager.setLayout(null);
		frame.getContentPane().add(meniuManager, "name_219597416826788");
		
		JLabel lblMeniuManager = new JLabel("Meniu Manager");
		lblMeniuManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblMeniuManager.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblMeniuManager.setBounds(0, 26, 1028, 57);
		meniuManager.add(lblMeniuManager);
		
		JMenuBar menuBar_3 = new JMenuBar();
		menuBar_3.setBounds(0, 0, 1028, 26);
		meniuManager.add(menuBar_3);
		
		JPanel meniuBucatar = new JPanel();
		JPanel meniuBarman = new JPanel();
		
		JButton button_52 = new JButton("Meniu Barman");
		button_52.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuManager.setVisible(false);
				meniuBucatar.setVisible(true);
			}
		});
		menuBar_3.add(button_52);
		
		JButton button_53 = new JButton("Meniu Bucatar");
		button_53.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuManager.setVisible(false);
				meniuBarman.setVisible(true);
			}
		});
		menuBar_3.add(button_53);
		
		JButton button_54 = new JButton("Meniu Ospatar");
		button_54.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuManager.setVisible(false);
				meniuOspatar.setVisible(true);
			}
		});
		menuBar_3.add(button_54);
		
		JPanel comenziAcasa = new JPanel();
		
		JButton button_51 = new JButton("Comenzi acasa");
		button_51.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuManager.setVisible(false);
				comenziAcasa.setVisible(true);
			}
		});
		menuBar_3.add(button_51);
		
		JPanel rezervariMese = new JPanel();
		JPanel stoc = new JPanel();
		
		JButton button_50 = new JButton("Rezervari mese");
		button_50.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuManager.setVisible(false);
				rezervariMese.setVisible(true);
			}
		});
		menuBar_3.add(button_50);
		
		JButton btnMeniuAlimente = new JButton("Meniu Alimente");
		btnMeniuAlimente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuManager.setVisible(false);
				meniuAlimente.setVisible(true);
			}
		});
		menuBar_3.add(btnMeniuAlimente);
		
		JButton button_14 = new JButton("Inapoi");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				meniuManager.setVisible(false);
				inregistrare.setVisible(true);		
			}
		});
		button_14.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_14.setBounds(16, 432, 103, 40);
		meniuManager.add(button_14);
		
		JButton button_15 = new JButton("Exit");
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame formLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}	
			}
		});
		button_15.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_15.setBounds(131, 432, 103, 40);
		meniuManager.add(button_15);
		
		
		JButton btnStocManager = new JButton("Stoc");
		btnStocManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stoc.setVisible(true);
				meniuManager.setVisible(false);
			}
		});
		btnStocManager.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnStocManager.setBounds(48, 165, 183, 92);
		meniuManager.add(btnStocManager);
		
		
		JPanel angajati = new JPanel();
		angajati.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				refreshTableAngajati();
			}
		});
		angajati.setLayout(null);
		frame.getContentPane().add(angajati, "name_225724169230982");

		JButton btnAngajati = new JButton("Angajati");
		btnAngajati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				angajati.setVisible(true);
				meniuManager.setVisible(false);
			}
		});
		btnAngajati.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAngajati.setBounds(288, 165, 183, 92);
		meniuManager.add(btnAngajati);
		frame.getContentPane().add(meniuOspatar, "name_147312922538750");
		meniuOspatar.setLayout(null);
		comboBoxFelMancare();
		comboBoxMese();
		comboBoxMeseB();
		comboBoxComenziAcasa();
		
		
		
		meniuBucatar.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				refreshTableBucatar();
			}
		});
	
		frame.getContentPane().add(meniuBucatar, "name_152554770518681");
		meniuBucatar.setLayout(null);
		
		
		meniuBarman.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				refreshTableBarman();
			}
		});
		meniuBarman.setLayout(null);
		frame.getContentPane().add(meniuBarman, "name_187865449193580");
		
		JLabel lblMeniuBarman = new JLabel("Meniu Barman");
		lblMeniuBarman.setHorizontalAlignment(SwingConstants.CENTER);
		lblMeniuBarman.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblMeniuBarman.setBounds(0, 26, 1028, 57);
		meniuBarman.add(lblMeniuBarman);
		
		JMenuBar menuBar_2 = new JMenuBar();
		menuBar_2.setBounds(0, 0, 1028, 26);
		meniuBarman.add(menuBar_2);
		
		JButton button_27 = new JButton("Meniu Ospatar");
		button_27.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(true);
				meniuBarman.setVisible(false);	
			}
		});
		menuBar_2.add(button_27);
		
		JButton btnMeniuBucatar = new JButton("Meniu Bucatar");
		btnMeniuBucatar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuBucatar.setVisible(true);
				meniuBarman.setVisible(false);	
			}
		});
		menuBar_2.add(btnMeniuBucatar);
		
		JButton button_30 = new JButton("Stoc");
		button_30.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stoc.setVisible(true);
				meniuBarman.setVisible(false);	
			}
		});
		

		
		frame.getContentPane().add(comenziAcasa, "name_420984339433458");
		comenziAcasa.setLayout(null);
		
		menuBar_2.add(button_30);
		
		JButton button_31 = new JButton("Comenzi acasa");
		button_31.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comenziAcasa.setVisible(true);
				meniuBarman.setVisible(false);	
			}
		});
		
		
		frame.getContentPane().add(rezervariMese, "name_424316750581288");
		rezervariMese.setLayout(null);
		
		menuBar_2.add(button_31);
		
		JButton button_32 = new JButton("Rezervari mese");
		button_32.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rezervariMese.setVisible(true);
				meniuBarman.setVisible(false);	
			}
		});
		menuBar_2.add(button_32);
		
		JButton button_64 = new JButton("Meniu Alimente");
		button_64.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuBarman.setVisible(false);
				meniuAlimente.setVisible(true);
			}
		});
		menuBar_2.add(button_64);
		
		JButton button_11 = new JButton("Inapoi");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuManager.setVisible(false);
				inregistrare.setVisible(true);	
			}
		});
		button_11.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_11.setBounds(16, 432, 103, 40);
		meniuBarman.add(button_11);
		
		JButton button_12 = new JButton("Exit");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame formLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}	
			}
		});
		button_12.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_12.setBounds(131, 432, 103, 40);
		meniuBarman.add(button_12);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(16, 113, 232, 286);
		meniuBarman.add(panel_3);
		
		JLabel label_2 = new JLabel("Cod comanda terminata:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 19));
		label_2.setBounds(12, 13, 208, 26);
		panel_3.add(label_2);
		
		txtCodBarman = new JTextField();
		txtCodBarman.setColumns(10);
		txtCodBarman.setBounds(67, 52, 98, 47);
		panel_3.add(txtCodBarman);
		
		JButton button_13 = new JButton("Validare");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Connection connection = ConexiuneBD.getConnection();
					String query = "UPDATE comanda SET stareComanda = 'Terminata' WHERE codc = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, txtCodBarman.getText());
					
					pst.execute();
					
					connection.close();
				}catch(Exception exception) {
					System.out.println(exception);
				}
				
				refreshTableBarman();
			}
		});
		button_13.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_13.setBounds(36, 123, 153, 40);
		panel_3.add(button_13);
		

		JLabel label_10 = new JLabel("Tabela Comenzi in asteptare");
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_10.setBounds(539, 89, 280, 22);
		meniuBarman.add(label_10);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(260, 113, 779, 286);
		meniuBarman.add(scrollPane_2);
		
		tableBarman = new JTable();
		tableBarman.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) tableBarman.getModel();
				int selectedRowIndex = tableBarman.getSelectedRow();
				
				txtCodBarman.setText(model.getValueAt(selectedRowIndex, 0).toString());
			}
		});
		scrollPane_2.setViewportView(tableBarman);
		
		JButton btnOkLogIn = new JButton("OK");
		btnOkLogIn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String numeAngajat = txtNumeAngajat.getText();
				String parolaAngajat = txtParolaAngajat.getText();
				
				try {
					Connection connect = ConexiuneBD.getConnection();
					Statement stmt = connect.createStatement();
					String sql = "SELECT functie "
							   + " FROM angajati"
							   + " WHERE nume_angajat = '" + numeAngajat
							   + "' AND parola_angajat = '" + parolaAngajat + "'";
					
					ResultSet rs = stmt.executeQuery(sql);
					
					while(rs.next()) {
						String profesie = rs.getString("functie");
						System.out.println(profesie);
						
					if(profesie.equals("ospatar") ){
						
						meniuOspatar.setVisible(true);
						inregistrare.setVisible(false);		
						
					}else if(profesie.equals("bucatar")) {
						
						meniuBucatar.setVisible(true);
						inregistrare.setVisible(false);
						
					}else if(profesie.equals("barman")) {
						
						meniuBarman.setVisible(true);
						inregistrare.setVisible(false);
						
					}else if(profesie.equals("manager")) {
						
						meniuManager.setVisible(true);
						inregistrare.setVisible(false);
						
					}else if(profesie.equals("alimentareStoc")) {
						
						stoc.setVisible(true);
						inregistrare.setVisible(false);
					}	
					else{
						
						JOptionPane.showMessageDialog( null, "Cont invalid!");
					}
					}
					
					connect.close();
				}catch(Exception exception) {
					System.out.println(exception);
					}		
			}

		});
		btnOkLogIn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnOkLogIn.setBounds(44, 272, 108, 43);
		inregistrare.add(btnOkLogIn);
		
		JButton btnExitLogIn = new JButton("Exit");
		btnExitLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFrame formLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}	
			}
		});
		btnExitLogIn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExitLogIn.setBounds(469, 272, 97, 43);
		inregistrare.add(btnExitLogIn);
		
		JButton btnResetLogIn = new JButton("Reset");
		btnResetLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtNumeAngajat.setText(null);
				txtParolaAngajat.setText(null);
			}
		});
		btnResetLogIn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnResetLogIn.setBounds(253, 272, 108, 43);
		inregistrare.add(btnResetLogIn);
		
		txtNumeAngajat = new JTextField();
		txtNumeAngajat.setColumns(10);
		txtNumeAngajat.setBounds(284, 122, 245, 31);
		inregistrare.add(txtNumeAngajat);
		
		txtParolaAngajat = new JPasswordField();
		txtParolaAngajat.setBounds(284, 178, 245, 33);
		inregistrare.add(txtParolaAngajat);
		
	
		meniuAlimente.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				refreshTableMeniu();
			}
		});
		meniuAlimente.setLayout(null);
		frame.getContentPane().add(meniuAlimente, "name_460637619423526");
		
		JButton btnAccesatiMeniulRestaurantului = new JButton("Accesati meniul restaurantului nostru");
		btnAccesatiMeniulRestaurantului.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inregistrare.setVisible(false);
				meniuAlimente.setVisible(true);
			}
		});
		btnAccesatiMeniulRestaurantului.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAccesatiMeniulRestaurantului.setBounds(45, 342, 521, 34);
		inregistrare.add(btnAccesatiMeniulRestaurantului);
		
		JLabel lblNewLabel = new JLabel("Meniu Ospatar");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(0, 25, 1028, 45);
		meniuOspatar.add(lblNewLabel);
		
		JMenuBar menuBarOspatar = new JMenuBar();
		menuBarOspatar.setBounds(0, 0, 1028, 26);
		meniuOspatar.add(menuBarOspatar);
		
		JButton btnNewButton = new JButton("Meniu Bucatar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(false);
				meniuBucatar.setVisible(true);	
			}
		});
		menuBarOspatar.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Stoc");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(false);
				stoc.setVisible(true);	
			}
		});
		
		
		JButton btnMeniuBarman = new JButton("Meniu Barman");
		btnMeniuBarman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(false);
				meniuBarman.setVisible(true);	
			}
		});
		menuBarOspatar.add(btnMeniuBarman);
		
		menuBarOspatar.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Comenzi acasa");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(false);
				comenziAcasa.setVisible(true);	
			}
		});
		
		
		menuBarOspatar.add(btnNewButton_2);
		
		JButton btnRezervariMese = new JButton("Rezervari mese");
		btnRezervariMese.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(false);
				rezervariMese.setVisible(true);	
			}
		});
		menuBarOspatar.add(btnRezervariMese);
		
		JButton button_62 = new JButton("Meniu Alimente");
		button_62.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(false);
				meniuAlimente.setVisible(true);
			}
		});
		menuBarOspatar.add(button_62);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(253, 120, 741, 270);
		meniuOspatar.add(scrollPane);
		
		tableOspatar = new JTable();
		tableOspatar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) tableOspatar.getModel();
				int selectedRowIndex = tableOspatar.getSelectedRow();
				
				txtCodComanda.setText(model.getValueAt(selectedRowIndex, 0).toString());
				comboBoxFelMancare.setSelectedItem(model.getValueAt(selectedRowIndex, 1).toString());
				txtNrPortiiOspatar.setText(model.getValueAt(selectedRowIndex, 2).toString());
				txtOra.setText(model.getValueAt(selectedRowIndex, 3).toString());
				txtDate.setText(model.getValueAt(selectedRowIndex, 4).toString());
				comboBoxNrMasa.setSelectedItem(model.getValueAt(selectedRowIndex, 5).toString());
				
			}
		});
		scrollPane.setViewportView(tableOspatar);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date date = new Date();
					txtDate.setText(dateFormat.format(date));
					
					DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					txtOra.setText(timeFormat.format(cal.getTime()));
					
					try {
						Connection connection = ConexiuneBD.getConnection();
						String query = "INSERT INTO comanda(codc, felComandat, numarPortii, orac, datac, nrmasa) VALUES (?,?,?,?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						
						pst.setString(1, txtCodComanda.getText());
						pst.setString(2,(String) comboBoxFelMancare.getSelectedItem());
						pst.setString(3, txtNrPortiiOspatar.getText());
						pst.setString(4, txtOra.getText());
						pst.setString(5, txtDate.getText());
						pst.setString(6, (String) comboBoxNrMasa.getSelectedItem());
						
						pst.execute();
						
						
						connection.close();
					}catch(Exception exception) {
						System.out.println(exception);
					}
					
					refreshTableOspatar();
				}
		});
		btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnInsert.setBounds(12, 421, 103, 40);
		meniuOspatar.add(btnInsert);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				txtDate.setText(dateFormat.format(date));
				
				DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				txtOra.setText(timeFormat.format(cal.getTime()));
				
				try {
					Connection connection = ConexiuneBD.getConnection();
					String query = "UPDATE comanda SET codc = ? , felComandat = ?, numarPortii = ?, orac = ? , datac = ? , nrmasa = ? WHERE codc = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, txtCodComanda.getText());
					pst.setString(2, (String) comboBoxFelMancare.getSelectedItem());
					pst.setString(3, txtNrPortiiOspatar.getText());
					pst.setString(4, txtOra.getText());
					pst.setString(5, txtDate.getText());
					pst.setString(6, (String) comboBoxNrMasa.getSelectedItem());
					pst.setString(7, txtCodComanda.getText());
					
				
					pst.execute();
					
					connection.close();
				}catch(Exception exception) {
					System.out.println(exception);
				}
				refreshTableOspatar();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnUpdate.setBounds(127, 421, 103, 40);
		meniuOspatar.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection connection = ConexiuneBD.getConnection();
					String query = "DELETE FROM comanda WHERE codc = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, txtCodComanda.getText());
					
					pst.execute();
					
					connection.close();
				}catch(Exception exception) {
					System.out.println(exception);
				}
				refreshTableOspatar();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDelete.setBounds(253, 421, 103, 40);
		meniuOspatar.add(btnDelete);
		
		JButton btnInapoi = new JButton("Inapoi");
		btnInapoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuManager.setVisible(false);
				inregistrare.setVisible(true);	
			}
		});
		btnInapoi.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnInapoi.setBounds(496, 421, 103, 40);
		meniuOspatar.add(btnInapoi);
		
		JLabel lblNewLabel_1 = new JLabel("Tabela Comenzilor");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(524, 98, 184, 22);
		meniuOspatar.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(12, 120, 232, 270);
		meniuOspatar.add(panel);
		panel.setLayout(null);
		
		JLabel lblOraComanda = new JLabel("Ora:");
		lblOraComanda.setBounds(12, 162, 132, 26);
		panel.add(lblOraComanda);
		lblOraComanda.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblDataComanda = new JLabel("Data:");
		lblDataComanda.setBounds(12, 123, 147, 26);
		panel.add(lblDataComanda);
		lblDataComanda.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblFel = new JLabel("Fel:");
		lblFel.setBounds(12, 45, 103, 26);
		panel.add(lblFel);
		lblFel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		txtDate = new JTextField();
		txtDate.setColumns(10);
		txtDate.setBounds(104, 123, 116, 26);
		panel.add(txtDate);
		
		txtOra = new JTextField();
		txtOra.setColumns(10);
		txtOra.setBounds(104, 162, 116, 26);
		panel.add(txtOra);
		
		JLabel lblCodcomanda = new JLabel("Cod:");
		lblCodcomanda.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCodcomanda.setBounds(12, 13, 103, 26);
		panel.add(lblCodcomanda);
		
		txtCodComanda = new JTextField();
		txtCodComanda.setColumns(10);
		txtCodComanda.setBounds(104, 18, 116, 22);
		panel.add(txtCodComanda);
		
		JLabel lblNrMasa = new JLabel("Nr Masa:");
		lblNrMasa.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNrMasa.setBounds(12, 201, 147, 26);
		panel.add(lblNrMasa);
		comboBoxNrMasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxMese();
			}
		});
		
		JPanel meniuBon = new JPanel();
		meniuBon.setLayout(null);
		frame.getContentPane().add(meniuBon, "name_641282799312500");
	
		comboBoxNrMasa.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboBoxNrMasa.setBounds(102, 201, 118, 26);
		panel.add(comboBoxNrMasa);
		
		JLabel lblNrPortii = new JLabel("Nr Portii:");
		lblNrPortii.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNrPortii.setBounds(12, 84, 147, 26);
		panel.add(lblNrPortii);
		
		txtNrPortiiOspatar = new JTextField();
		txtNrPortiiOspatar.setBounds(104, 84, 116, 26);
		panel.add(txtNrPortiiOspatar);
		txtNrPortiiOspatar.setColumns(10);
		
		
		comboBoxFelMancare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBoxFelMancare();
			}
		});
		comboBoxFelMancare.setBounds(104, 45, 116, 26);
		panel.add(comboBoxFelMancare);
		comboBoxFelMancare.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnExitOspatar = new JButton("Exit");
		btnExitOspatar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame formLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}	
			}
		});
		btnExitOspatar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExitOspatar.setBounds(611, 421, 103, 40);
		meniuOspatar.add(btnExitOspatar);
		
		JButton btnValidareOspatar = new JButton("Gata");
		btnValidareOspatar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection connection = ConexiuneBD.getConnection();
					String query = "UPDATE comanda SET stareComandaOspatar = 'Servita' WHERE codc = ? AND stareComanda = 'Terminata'";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, txtCodComanda.getText());
					
					pst.execute();
					
					connection.close();
				}catch(Exception exception) {
					System.out.println(exception);
				}
				refreshTableOspatar();
			}
		});
		btnValidareOspatar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnValidareOspatar.setBounds(368, 421, 103, 40);
		meniuOspatar.add(btnValidareOspatar);
		
		JButton btnBon = new JButton("Bon");
		btnBon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(false);
				meniuBon.setVisible(true);	
			}
		});
		
		btnBon.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnBon.setBounds(724, 421, 103, 40);
		meniuOspatar.add(btnBon);
		
		

		
		lblMeniuBucatar = new JLabel("Meniu Bucatar");
		lblMeniuBucatar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMeniuBucatar.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblMeniuBucatar.setBounds(0, 26, 1028, 57);
		meniuBucatar.add(lblMeniuBucatar);
		
		JMenuBar menuBarBucatar = new JMenuBar();
		menuBarBucatar.setBounds(0, 0, 1028, 26);
		meniuBucatar.add(menuBarBucatar);
		
		JButton btnMeniuOspatar = new JButton("Meniu Ospatar");
		btnMeniuOspatar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(true);
				meniuBucatar.setVisible(false);
			}
		});
		menuBarBucatar.add(btnMeniuOspatar);
		
		JButton button_28 = new JButton("Meniu Barman");
		button_28.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuBarman.setVisible(true);
				meniuBucatar.setVisible(false);
			}
		});
		menuBarBucatar.add(button_28);
		
		JButton button_1 = new JButton("Stoc");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stoc.setVisible(true);
				meniuBucatar.setVisible(false);
			}
		});
		menuBarBucatar.add(button_1);
		
		JButton button = new JButton("Comenzi acasa");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comenziAcasa.setVisible(true);
				meniuBucatar.setVisible(false);
			}
		});
		menuBarBucatar.add(button);
		
		JButton button_26 = new JButton("Rezervari mese");
		button_26.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rezervariMese.setVisible(true);
				meniuBucatar.setVisible(false);
			}
		});
		menuBarBucatar.add(button_26);
		
		JButton button_63 = new JButton("Meniu Alimente");
		button_63.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuBucatar.setVisible(false);
				meniuAlimente.setVisible(true);
			}
		});
		menuBarBucatar.add(button_63);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(260, 113, 756, 286);
		meniuBucatar.add(scrollPane_1);
		
		tableBucatar = new JTable();
		tableBucatar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				

				DefaultTableModel model = (DefaultTableModel) tableBucatar.getModel();
				int selectedRowIndex = tableBucatar.getSelectedRow();
				
				txtCodBuc.setText(model.getValueAt(selectedRowIndex, 0).toString());
				
			}
		});
		scrollPane_1.setViewportView(tableBucatar);
		
		JButton btnInapoiBuc = new JButton("Inapoi");
		btnInapoiBuc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuManager.setVisible(false);
				inregistrare.setVisible(true);	
			}
		});
		btnInapoiBuc.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnInapoiBuc.setBounds(16, 432, 103, 40);
		meniuBucatar.add(btnInapoiBuc);
		
		JButton btnExitBucatar = new JButton("Exit");
		btnExitBucatar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame formLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}	
			}
		});
		btnExitBucatar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExitBucatar.setBounds(131, 432, 103, 40);
		meniuBucatar.add(btnExitBucatar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(16, 113, 232, 286);
		meniuBucatar.add(panel_1);
		
		JLabel lblCodComandaTerminata = new JLabel("Cod comanda terminata:");
		lblCodComandaTerminata.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCodComandaTerminata.setBounds(12, 13, 208, 26);
		panel_1.add(lblCodComandaTerminata);
		
		txtCodBuc = new JTextField();
		txtCodBuc.setColumns(10);
		txtCodBuc.setBounds(67, 52, 98, 47);
		panel_1.add(txtCodBuc);
		
		JButton btnValidareBucatar = new JButton("Validare");
		btnValidareBucatar.setBounds(36, 123, 153, 40);
		panel_1.add(btnValidareBucatar);
		btnValidareBucatar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Connection connection = ConexiuneBD.getConnection();
					Statement stmt = connection.createStatement();
					Statement stmt2 = connection.createStatement();
					Statement stmt3 = connection.createStatement();
					String query = "UPDATE comanda SET stareComanda = 'Terminata' WHERE codc = ?";
					String query2 = "SELECT den_prod_stoc, cant FROM stoc";
					String query3 = "SELECT den_fel, morcov, cartofi, lamai, fasole, malai, carnePui, oua, paine" + 
									" FROM retete r, fel f" + 
									" WHERE r.codFel = f.codf";
					String query4 = "SELECT felComandat, numarPortii FROM comanda";					
							
					ResultSet rs = stmt.executeQuery(query2);
					ResultSet rs2 = stmt2.executeQuery(query3);
					ResultSet rs3 = stmt3.executeQuery(query4);
					PreparedStatement pst = connection.prepareStatement(query);
					PreparedStatement pst2 = connection.prepareStatement(query2);
					PreparedStatement pst3 = connection.prepareStatement(query3);
					PreparedStatement pst4 = connection.prepareStatement(query4);
					pst.setString(1, txtCodBuc.getText());
					
					int stocMorcov = 0;
					int stocPieptPui = 0;
					int stocCartofi = 0;
					int stocLamai = 0;
					int stocMenta = 0;
					int stocFasole = 0;
					int stocMalai = 0;
					int stocCarnePui = 0;
					int stocCarnePorc = 0;
					int stocOua = 0;
					int stocPaine = 0;
					
					while(rs.next()) {
						if(rs.getString("den_prod_stoc").equals("morcov")) {
							stocMorcov = Integer.parseInt(rs.getString("cant"));
						}else if(rs.getString("den_prod_stoc").equals("piept de pui")) {
							stocPieptPui = Integer.parseInt(rs.getString("cant"));
						}else if(rs.getString("den_prod_stoc").equals("cartofi")) {
							stocCartofi = Integer.parseInt(rs.getString("cant"));
						}else if(rs.getString("den_prod_stoc").equals("lamai")) {
							stocLamai = Integer.parseInt(rs.getString("cant"));
						}else if(rs.getString("den_prod_stoc").equals("menta")) {
							stocMenta = Integer.parseInt(rs.getString("cant"));
						}else if(rs.getString("den_prod_stoc").equals("fasole")) {
							stocFasole = Integer.parseInt(rs.getString("cant"));
						}else if(rs.getString("den_prod_stoc").equals("malai")) {
							stocMalai = Integer.parseInt(rs.getString("cant"));
						}else if(rs.getString("den_prod_stoc").equals("carne pui")) {
							stocCarnePui = Integer.parseInt(rs.getString("cant"));
						}else if(rs.getString("den_prod_stoc").equals("carne porc")) {
							stocCarnePorc = Integer.parseInt(rs.getString("cant"));
						}else if(rs.getString("den_prod_stoc").equals("oua")) {
							stocOua = Integer.parseInt(rs.getString("cant"));
						}else if(rs.getString("den_prod_stoc").equals("paine")) {
							stocPaine = Integer.parseInt(rs.getString("cant"));
						}
					}
					
					Statement stmtUpdateCapOua = connection.createStatement();
					Statement stmtUpdateCapPui = connection.createStatement();
					String queryUpdateFinalStocCapr = "UPDATE stoc SET cant = ? WHERE den_prod_stoc = 'oua' ";
					String queryCapriciosaPui = "UPDATE stoc SET cant = ? WHERE den_prod_stoc =  'carne pui'";
					
					PreparedStatement pstUpdateFinal = connection.prepareStatement(queryUpdateFinalStocCapr);
					PreparedStatement pstUpdateCaprPui = connection.prepareStatement(queryCapriciosaPui);
					
					
					
					
					int alimenteRamaseCapriciosaPui = 0;
					int alimenteRamaseCapriciosaOua = 0;
					int alimenteRamaseOmleta = 0;
					int alimenteRameseCartofiPai = 0;
					int portii = 0;
					
					while(rs3.next()) {
						if(rs3.getString("felComandat").equals("Capriciosa")) {
							portii = Integer.parseInt(rs3.getString("numarPortii"));
						
							System.out.print("\nportii::::: "+portii);
							
						}
					}
					
					while(rs2.next()) {
						if(rs2.getString("den_fel").equals("Capriciosa")) {
							int cantCarnePuiCapriciosa = Integer.parseInt(rs2.getString("carnePui")) * portii;
							int cantitateOuaCapriciosa = Integer.parseInt(rs2.getString("oua")) * portii;
							 

							System.out.print("\nstocCarnePui "+stocCarnePui);
							System.out.print("\nalimenteRamaseCapriciosaPui "+stocCarnePui);
							System.out.print("\ncantitateOuaCapriciosa "+ cantitateOuaCapriciosa);
							
							if(stocCarnePui > cantCarnePuiCapriciosa && stocOua > cantitateOuaCapriciosa) {
								alimenteRamaseCapriciosaPui = stocCarnePui - cantCarnePuiCapriciosa;
								pstUpdateCaprPui.setString(1, String.valueOf(cantCarnePuiCapriciosa));
								System.out.print("\nalimenteRamaseCapriciosaPui "+alimenteRamaseCapriciosaPui);
								alimenteRamaseCapriciosaOua = stocOua - cantitateOuaCapriciosa;
								pstUpdateFinal.setString(1, String.valueOf(alimenteRamaseCapriciosaOua));
								System.out.print("\nalimenteRamaseCapriciosaOua "+alimenteRamaseCapriciosaOua);
								pst.execute();
								pstUpdateFinal.execute();
								pstUpdateCaprPui.execute();
								
								
							}else{
								JOptionPane.showMessageDialog(null, "Nu mai sunt produse in stoc pentru acest fel de mancare!->Capr");
							}
							
						}else if(rs2.getString("den_fel").equals("Omleta")) {
							int cantAlimenteOmleta = Integer.parseInt(rs2.getString("oua")) * portii;
							//alimenteRamaseOmleta = stocOua - cantAlimenteOmleta;	
							if(stocOua > cantAlimenteOmleta) {
								alimenteRamaseOmleta = stocOua - cantAlimenteOmleta;
								System.out.print("\nalimenteRamaseCapriciosaOua "+alimenteRamaseCapriciosaOua);
								pst.execute();
							}else{
								JOptionPane.showMessageDialog(null, "Nu mai sunt produse in stoc pentru acest fel de mancare!->omleta");
							}
						}else if(rs2.getString("den_fel").equals("Friptura pui si cartofi pai")) {
							int cantitateCartofi = Integer.parseInt(rs2.getString("cartofi")) * portii;
							//int cantitateCarneP = Integer.parseInt(rs2.getString("carnePui")) * portii;
							//alimenteRameseCartofiPai = stocCartofi - cantitateCartofi;
							if(stocCartofi > cantitateCartofi) {
								alimenteRameseCartofiPai = stocCartofi - cantitateCartofi;
								System.out.print("\nalimenteRamaseCapriciosaOua "+alimenteRamaseCapriciosaOua);
								pst.execute();
							}else{
								JOptionPane.showMessageDialog(null, "Nu mai sunt produse in stoc pentru acest fel de mancare!->cartofi");
							}
						}
					}
					
				//	pst.execute();
					pst2.execute();
					pst3.execute();
					pst4.execute();
					connection.close();
				}catch(Exception exception) {
					System.out.println(exception);
				}
				
				refreshTableBucatar();
			}
		});
		
		
		btnValidareBucatar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblTabelaComenziIn = new JLabel("Tabela Comenzi in asteptare");
		lblTabelaComenziIn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTabelaComenziIn.setBounds(539, 89, 280, 22);
		meniuBucatar.add(lblTabelaComenziIn);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1028, 26);
		comenziAcasa.add(menuBar);
		
		JButton button_9 = new JButton("Stoc");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stoc.setVisible(true);
				comenziAcasa.setVisible(false);	
			}
		});
		menuBar.add(button_9);
		
		JButton button_10 = new JButton("Meniu Bucatar");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuBucatar.setVisible(true);
				comenziAcasa.setVisible(false);	
			}
		});
		menuBar.add(button_10);
		
		JButton button_29 = new JButton("Meniu Ospatar");
		button_29.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(true);
				comenziAcasa.setVisible(false);	
			}
		});
		
		JButton btnMeniuBarman_1 = new JButton("Meniu Barman");
		btnMeniuBarman_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuBarman.setVisible(true);
				comenziAcasa.setVisible(false);	
			}
		});
		menuBar.add(btnMeniuBarman_1);
		menuBar.add(button_29);
		
		JButton button_36 = new JButton("Rezervari mese");
		button_36.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rezervariMese.setVisible(true);
				comenziAcasa.setVisible(false);	
			}
		});
		menuBar.add(button_36);
		
		JButton button_65 = new JButton("Meniu Alimente");
		button_65.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comenziAcasa.setVisible(false);
				meniuAlimente.setVisible(true);
			}
		});
		menuBar.add(button_65);
		
		JLabel lblNewLabel_2 = new JLabel("Comenzi la domiciliu");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_2.setBounds(0, 24, 1028, 42);
		comenziAcasa.add(lblNewLabel_2);
		
		JLabel lblNume = new JLabel("Nume:");
		lblNume.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNume.setBounds(63, 234, 118, 26);
		comenziAcasa.add(lblNume);
		
		JLabel lablNumeProdComenziDom = new JLabel("Produs:");
		lablNumeProdComenziDom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lablNumeProdComenziDom.setBounds(63, 139, 130, 26);
		comenziAcasa.add(lablNumeProdComenziDom);
		
		JLabel lblPrenume = new JLabel("Prenume:");
		lblPrenume.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPrenume.setBounds(63, 286, 143, 26);
		comenziAcasa.add(lblPrenume);
		
		JLabel lblAdresa = new JLabel("Adresa pentru comanda:");
		lblAdresa.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAdresa.setBounds(407, 234, 251, 26);
		comenziAcasa.add(lblAdresa);
		
		JLabel lblTelefon = new JLabel("Telefon:");
		lblTelefon.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTelefon.setBounds(407, 139, 130, 26);
		comenziAcasa.add(lblTelefon);
		
		JLabel lblDataComenzii = new JLabel("Data comenzii:");
		lblDataComenzii.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDataComenzii.setBounds(407, 185, 143, 26);
		comenziAcasa.add(lblDataComenzii);
		
		JLabel lblCantitateComandaDomiciliu = new JLabel("Cantitate:");
		lblCantitateComandaDomiciliu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCantitateComandaDomiciliu.setBounds(63, 185, 130, 26);
		comenziAcasa.add(lblCantitateComandaDomiciliu);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 textField_6.setText(null);
				 textField_7.setText(null);
				 textField_8.setText(null);
				 textField_10.setText(null);
				 textField_1.setText(null);
				 textField_9.setText(null);
				 textField.setText(null);
				 textField_11.setText(null);				
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnReset.setBounds(278, 417, 103, 40);
		comenziAcasa.add(btnReset);
		
		JButton button_8 = new JButton("Inapoi");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comenziAcasa.setVisible(false);
				meniuOspatar.setVisible(true);
			}
		});
		button_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_8.setBounds(426, 417, 103, 40);
		comenziAcasa.add(button_8);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(205, 185, 147, 26);
		comenziAcasa.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(205, 234, 147, 26);
		comenziAcasa.add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(205, 286, 147, 26);
		comenziAcasa.add(textField_8);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(568, 139, 143, 26);
		comenziAcasa.add(textField_9);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(568, 185, 143, 26);
		comenziAcasa.add(textField_10);
		
		textField_11 = new JTextField();
		textField_11.setHorizontalAlignment(SwingConstants.LEFT);
		textField_11.setColumns(10);
		textField_11.setBounds(407, 273, 304, 90);
		comenziAcasa.add(textField_11);
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxComenziAcasa();
			}
		});
		

		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboBox_1.setBounds(205, 142, 147, 26);
		comenziAcasa.add(comboBox_1);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotal.setBounds(762, 139, 143, 26);
		comenziAcasa.add(lblTotal);
		
		JLabel label_3 = new JLabel("Metoda de plata:");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_3.setBounds(762, 185, 155, 26);
		comenziAcasa.add(label_3);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("CASH");
		comboBox.addItem("CARD");
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboBox.setBounds(929, 185, 118, 26);
		comenziAcasa.add(comboBox);
		
		JButton btnInchide = new JButton("Exit");
		btnInchide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame formLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
			}
			}
		});
		btnInchide.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnInchide.setBounds(568, 417, 103, 40);
		comenziAcasa.add(btnInchide);
		
		JButton btnInsert_2 = new JButton("Insert");
		btnInsert_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				textField_10.setText(dateFormat.format(date));
				
				DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				textField_1.setText(timeFormat.format(cal.getTime()));
				
				try {
					Connection connection = ConexiuneBD.getConnection();
					String query = "INSERT INTO comenzi_acasa(produs, cant, nume, prenume, data, ora, telefon, total, variantaPlata, adresa) VALUES (?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, String.valueOf(comboBox_1.getSelectedItem()));
					pst.setString(2, textField_6.getText());
					pst.setString(3, textField_7.getText());
					pst.setString(4, textField_8.getText());
					pst.setString(5, textField_10.getText());
					pst.setString(6, textField_1.getText());
					pst.setString(7, textField_9.getText());
					pst.setString(8, textField.getText());
					pst.setString(9, String.valueOf(comboBox.getSelectedItem()));
					pst.setString(10, textField_11.getText());
					
					pst.execute();
					
					
					connection.close();
				}catch(Exception exception) {
					System.out.println(exception);
				}
				
			}
		});
		btnInsert_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnInsert_2.setBounds(154, 417, 103, 40);
		comenziAcasa.add(btnInsert_2);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(917, 139, 143, 26);
		comenziAcasa.add(textField);
		
		JLabel lblOraComenzii = new JLabel("Ora comenzii:");
		lblOraComenzii.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOraComenzii.setBounds(762, 234, 143, 26);
		comenziAcasa.add(lblOraComenzii);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(923, 234, 143, 26);
		comenziAcasa.add(textField_1);
		
		
		
		nrMasa = new JComboBox();
		nrMasa.addItem(1);
		nrMasa.addItem(2);
		nrMasa.addItem(3);
		nrMasa.addItem(4);
		nrMasa.setFont(new Font("Tahoma", Font.PLAIN, 20));
		nrMasa.setBounds(176, 162, 147, 26);
		rezervariMese.add(nrMasa);
		
		JLabel lblMasa = new JLabel("Masa:");
		lblMasa.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMasa.setBounds(34, 159, 130, 26);
		rezervariMese.add(lblMasa);
		
		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblData.setBounds(34, 205, 130, 26);
		rezervariMese.add(lblData);
		
		dataR = new JTextField();
		dataR.setColumns(10);
		dataR.setBounds(176, 205, 147, 26);
		rezervariMese.add(dataR);
		
		JLabel label_6 = new JLabel("Nume:");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_6.setBounds(34, 254, 118, 26);
		rezervariMese.add(label_6);
		
		numeRez = new JTextField();
		numeRez.setColumns(10);
		numeRez.setBounds(176, 254, 147, 26);
		rezervariMese.add(numeRez);
		
		prenumeRez = new JTextField();
		prenumeRez.setColumns(10);
		prenumeRez.setBounds(176, 306, 147, 26);
		rezervariMese.add(prenumeRez);
		
		JLabel label_7 = new JLabel("Prenume:");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_7.setBounds(34, 306, 143, 26);
		rezervariMese.add(label_7);
		
		JLabel lblRezervareMasa = new JLabel("Rezervare masa:");
		lblRezervareMasa.setHorizontalAlignment(SwingConstants.CENTER);
		lblRezervareMasa.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblRezervareMasa.setBounds(0, 24, 1028, 42);
		rezervariMese.add(lblRezervareMasa);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar_1.setBounds(0, 0, 1028, 26);
		rezervariMese.add(menuBar_1);
		
		JButton button_6 = new JButton("Meniu Barman");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuBarman.setVisible(true);
				rezervariMese.setVisible(false);	
			}
		});
		menuBar_1.add(button_6);
		
		JButton btnComenziAcasa = new JButton("Comenzi acasa");
		btnComenziAcasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comenziAcasa.setVisible(true);
				rezervariMese.setVisible(false);	
			}
		});
		
		JButton button_34 = new JButton("Meniu Ospatar");
		button_34.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuOspatar.setVisible(true);
				rezervariMese.setVisible(false);	
			}
		});
		menuBar_1.add(button_34);
		
		JButton button_35 = new JButton("Meniu Bucatar");
		button_35.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meniuBucatar.setVisible(true);
				rezervariMese.setVisible(false);	
			}
		});
		menuBar_1.add(button_35);
		menuBar_1.add(btnComenziAcasa);
		
		JButton button_37 = new JButton("Stoc");
		button_37.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stoc.setVisible(true);
				rezervariMese.setVisible(false);	
			}
		});
		menuBar_1.add(button_37);
		
		JButton button_66 = new JButton("Meniu Alimente");
		button_66.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rezervariMese.setVisible(false);
				meniuAlimente.setVisible(true);
			}
		});
		menuBar_1.add(button_66);
		
		oraR = new JTextField();
		oraR.setColumns(10);
		oraR.setBounds(577, 208, 143, 26);
		rezervariMese.add(oraR);
		
		telefonRez = new JTextField();
		telefonRez.setColumns(10);
		telefonRez.setBounds(577, 162, 143, 26);
		rezervariMese.add(telefonRez);
		
		JLabel lblOra = new JLabel("Ora:");
		lblOra.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOra.setBounds(416, 208, 143, 26);
		rezervariMese.add(lblOra);
		
		JLabel label_9 = new JLabel("Telefon:");
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_9.setBounds(416, 162, 130, 26);
		rezervariMese.add(label_9);
		
		JButton btnInsert_3 = new JButton("Insert");
		btnInsert_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				txtDate.setText(dateFormat.format(date));
				
				DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				txtOra.setText(timeFormat.format(cal.getTime()));
				
				try {
					Connection connection = ConexiuneBD.getConnection();
					String query = "INSERT INTO rezervare(nrmasa, datarez, orarez, nr_pers_rez, nume, prenume, telefon) VALUES (?,?,?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, String.valueOf(nrMasa.getSelectedItem()));
					pst.setString(2, txtDate.getText());
					pst.setString(3, txtOra.getText());
					pst.setString(4, nrePresRez.getText());
					pst.setString(5, numeRez.getText());
					pst.setString(6, prenumeRez.getText());
					pst.setString(7, telefonRez.getText());
					
					pst.execute();
					
					
					connection.close();
				}catch(Exception exception) {
					System.out.println(exception);
				}
			}
		});
		btnInsert_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnInsert_3.setBounds(34, 421, 103, 40);
		rezervariMese.add(btnInsert_3);
		
		JButton button_38 = new JButton("Inapoi");
		button_38.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rezervariMese.setVisible(false);
				meniuOspatar.setVisible(true);
			}
		});
		button_38.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_38.setBounds(295, 421, 103, 40);
		rezervariMese.add(button_38);
		
		JButton button_39 = new JButton("Exit");
		button_39.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame formLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
			}
			}
		});
		button_39.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_39.setBounds(432, 421, 103, 40);
		rezervariMese.add(button_39);
		
		JButton button_40 = new JButton("Reset");
		button_40.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dataR.setText(null);
				oraR.setText(null);
				nrePresRez.setText(null);
				prenumeRez.setText(null);
				numeRez.setText(null);
				telefonRez.setText(null);
			}
		});
		button_40.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_40.setBounds(162, 421, 103, 40);
		rezervariMese.add(button_40);
		
		JLabel lblNrPers = new JLabel("Nr pers:");
		lblNrPers.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNrPers.setBounds(416, 254, 143, 26);
		rezervariMese.add(lblNrPers);
		
		nrePresRez = new JTextField();
		nrePresRez.setColumns(10);
		nrePresRez.setBounds(577, 254, 143, 26);
		rezervariMese.add(nrePresRez);
		
		JPanel meniuRezervari = new JPanel();
		meniuRezervari.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				refreshTableRezervari();
			}
		});

		meniuRezervari.setLayout(null);
		frame.getContentPane().add(meniuRezervari, "name_461653194704962");
		
		JButton btnVeziListaRezervarilor = new JButton("Vezi lista rezervarilor");
		btnVeziListaRezervarilor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rezervariMese.setVisible(false);
				meniuRezervari.setVisible(true);
			}
		});
		btnVeziListaRezervarilor.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnVeziListaRezervarilor.setBounds(34, 109, 289, 26);
		rezervariMese.add(btnVeziListaRezervarilor);
		
		
		stoc.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				refreshTableStoc();
			}
		});
		frame.getContentPane().add(stoc, "name_418291008201856");
		stoc.setLayout(null);
		

			JMenuBar menuBarStoc = new JMenuBar();
			menuBarStoc.setBounds(0, 0, 1067, 26);
			stoc.add(menuBarStoc);
			
			JButton btnMeniuOspatar_1 = new JButton("Meniu Ospatar");
			btnMeniuOspatar_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stoc.setVisible(false);
					meniuOspatar.setVisible(true);	
				}
			});
			menuBarStoc.add(btnMeniuOspatar_1);
			
			JButton button_7 = new JButton("Meniu Bucatar");
			button_7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stoc.setVisible(false);
					meniuBucatar.setVisible(true);	
				}
			});
			menuBarStoc.add(button_7);
			
			JButton button_2 = new JButton("Comenzi acasa");
			button_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stoc.setVisible(false);
					comenziAcasa.setVisible(true);	
				}
			});
			
			JButton button_33 = new JButton("Meniu Barman");
			button_33.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stoc.setVisible(false);
					meniuBarman.setVisible(true);	
				}
			});
			menuBarStoc.add(button_33);
			menuBarStoc.add(button_2);
			
			JButton btnRezervariMese_1 = new JButton("Rezervari mese");
			btnRezervariMese_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stoc.setVisible(false);
					rezervariMese.setVisible(true);	
				}
			});
			menuBarStoc.add(btnRezervariMese_1);
			
			JButton button_69 = new JButton("Meniu Alimente");
			button_69.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stoc.setVisible(false);
					meniuAlimente.setVisible(true);	
				}
			});
			menuBarStoc.add(button_69);
			
			
			
			JButton btnInsert_1 = new JButton("Insert");
			btnInsert_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						Connection connection = ConexiuneBD.getConnection();
						String query = "INSERT INTO stoc(den_prod_stoc, cant, dataConsum) VALUES (?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						
						pst.setString(1, txtNumeProdusStoc.getText());
						pst.setString(2, txtCantitate.getText());
						pst.setString(3, txtDataConsum.getText());
						
						pst.execute();
						
						
						connection.close();
					}catch(Exception exception) {
						System.out.println(exception);
					}
					
					refreshTableStoc();
					
				}
			});
			btnInsert_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnInsert_1.setBounds(12, 401, 103, 40);
			stoc.add(btnInsert_1);
			
			JButton btnUpdate_1 = new JButton("Update");
			btnUpdate_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						Connection connection = ConexiuneBD.getConnection();
						String query = "UPDATE stoc SET den_prod_stoc = ? , cant = ? , dataConsum = ? WHERE den_prod_stoc = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						
						pst.setString(1, txtNumeProdusStoc.getText());
						pst.setString(2, txtCantitate.getText());
						pst.setString(3, txtDataConsum.getText());		
						pst.setString(4, txtNumeProdusStoc.getText());
					
						pst.execute();
						
						connection.close();
					}catch(Exception exception) {
						System.out.println(exception);
					}
					refreshTableStoc();
				}
			});
			btnUpdate_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnUpdate_1.setBounds(127, 401, 103, 40);
			stoc.add(btnUpdate_1);
			
			JButton btnDelete_1 = new JButton("Delete");
			btnDelete_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Connection connection = ConexiuneBD.getConnection();
						String query = "DELETE FROM stoc WHERE den_prod_stoc = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						
						pst.setString(1, txtNumeProdusStoc.getText());
						
						pst.execute();
						
						connection.close();
					}catch(Exception exception) {
						System.out.println(exception);
					}
					refreshTableStoc();
				}
			});
			btnDelete_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnDelete_1.setBounds(240, 401, 103, 40);
			stoc.add(btnDelete_1);
			
			JScrollPane scrollPane_3 = new JScrollPane();
			scrollPane_3.setBounds(358, 121, 670, 267);
			stoc.add(scrollPane_3);
			
			tableStoc = new JTable();
			tableStoc.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					DefaultTableModel model = (DefaultTableModel) tableStoc.getModel();
					int selectedRowIndex = tableStoc.getSelectedRow();
					
					txtNumeProdusStoc.setText(model.getValueAt(selectedRowIndex, 0).toString());
					txtCantitate.setText(model.getValueAt(selectedRowIndex, 1).toString());
					txtDataConsum.setText(model.getValueAt(selectedRowIndex, 2).toString());
					
				}
			});
			scrollPane_3.setViewportView(tableStoc);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_2.setBounds(12, 121, 331, 267);
			stoc.add(panel_2);
			panel_2.setLayout(null);
			
			JLabel lblCantitate = new JLabel("Cantitate:");
			lblCantitate.setBounds(12, 64, 143, 27);
			panel_2.add(lblCantitate);
			lblCantitate.setFont(new Font("Tahoma", Font.PLAIN, 20));
			
			JLabel lblDataConsumului = new JLabel("Data consumului:");
			lblDataConsumului.setBounds(12, 104, 156, 26);
			panel_2.add(lblDataConsumului);
			lblDataConsumului.setFont(new Font("Tahoma", Font.PLAIN, 20));
			
			txtCantitate = new JTextField();
			txtCantitate.setBounds(201, 67, 118, 27);
			panel_2.add(txtCantitate);
			txtCantitate.setColumns(10);
			
			txtDataConsum = new JTextField();
			txtDataConsum.setBounds(222, 107, 97, 26);
			panel_2.add(txtDataConsum);
			txtDataConsum.setColumns(10);
			
			JButton btnStocTotal = new JButton("Vezi doar produsele expirate");
			btnStocTotal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					refreshTableStocExpirat();
				}
			});
			btnStocTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnStocTotal.setBounds(12, 150, 307, 26);
			panel_2.add(btnStocTotal);
			
			JButton btnVeziDoarProdusele_1 = new JButton("Vezi doar produsele epuizate");
			btnVeziDoarProdusele_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					refreshTableStocEpuizat();
				}
			});
			btnVeziDoarProdusele_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnVeziDoarProdusele_1.setBounds(12, 189, 307, 26);
			panel_2.add(btnVeziDoarProdusele_1);
			
			JLabel lblNumeProdus = new JLabel("Nume produs:");
			lblNumeProdus.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblNumeProdus.setBounds(12, 24, 143, 27);
			panel_2.add(lblNumeProdus);
			
			txtNumeProdusStoc = new JTextField();
			txtNumeProdusStoc.setColumns(10);
			txtNumeProdusStoc.setBounds(201, 27, 118, 27);
			panel_2.add(txtNumeProdusStoc);
			
			JButton btnVeziToateProdusele = new JButton("Vezi toate produsele");
			btnVeziToateProdusele.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					refreshTableStoc();
				}
			});
			btnVeziToateProdusele.setBounds(12, 228, 307, 26);
			panel_2.add(btnVeziToateProdusele);
			btnVeziToateProdusele.setFont(new Font("Tahoma", Font.PLAIN, 20));
			
			JLabel lblStoc = new JLabel("Stoc - Aprovizionare");
			lblStoc.setHorizontalAlignment(SwingConstants.CENTER);
			lblStoc.setFont(new Font("Tahoma", Font.PLAIN, 30));
			lblStoc.setBounds(0, 73, 1067, 37);
			stoc.add(lblStoc);
			
			JButton button_4 = new JButton("Inapoi");
			button_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meniuManager.setVisible(true);
					stoc.setVisible(false);	
				}
			});
			button_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_4.setBounds(355, 401, 103, 40);
			stoc.add(button_4);
			
			JButton button_21 = new JButton("Exit");
			button_21.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame formLoginSystem = new JFrame("Exit");
					if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
						System.exit(0);
				}
					}
			});
			button_21.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_21.setBounds(477, 401, 103, 40);
			stoc.add(button_21);
			
			JButton btnVeziGrafic = new JButton("Vezi grafic");
			btnVeziGrafic.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					DefaultPieDataset pieDataset = new DefaultPieDataset();
					
					try {
						Connection connect = ConexiuneBD.getConnection();
						Statement stmt = connect.createStatement();
						String sql = "select sum(cant) as cant, den_prod_stoc " + 
									"from stoc " + 
									"Where expirat is null "+
									"group by (den_prod_stoc) ";
						
						ResultSet rs = stmt.executeQuery(sql);
						
						while(rs.next()) {
							String cant = rs.getString("cant");
							String prod = rs.getString("den_prod_stoc");
							
							System.out.println(cant);
							
							pieDataset.setValue(prod, new Integer(cant));
							
						
						}
						connect.close();
					}catch(Exception exception) {
						System.out.println(exception);
						}		
					
					JFreeChart chart = ChartFactory.createPieChart("Grafic", pieDataset, true, true, true);
					PiePlot plot = (PiePlot) chart.getPlot();
					ChartFrame frame = new ChartFrame("Pie Chart", chart);
					frame.setVisible(true);
					frame.setSize(450,500);
					
				}
			});
			btnVeziGrafic.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnVeziGrafic.setBounds(618, 401, 176, 40);
			stoc.add(btnVeziGrafic);
			
			JButton btnPdfRaport = new JButton("PDF Raport");
			btnPdfRaport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Document doc = new Document();
					
					try {
						PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream("RaportStoc.pdf"));
						doc.open();
						doc.add(new Paragraph("Acesta este raportul pentru stoc                                                 " + new Date().toString(), FontFactory.getFont(FontFactory.TIMES_BOLD)));
						doc.add(new Paragraph(" "));
						
				
						PdfPTable table = new PdfPTable(5);
						table.setWidthPercentage(105);
						table.setSpacingBefore(10f);
						table.setSpacingAfter(10f);
						
						float[] colWidth = {2f,2f,2f,2f,2f};
						table.setWidths(colWidth);
						
						PdfPCell c1 = new PdfPCell(new Paragraph("codProdus"));
						PdfPCell c2 = new PdfPCell(new Paragraph("Aliment"));
						PdfPCell c3 = new PdfPCell(new Paragraph("Cantitate Ramasa"));
						PdfPCell c4 = new PdfPCell(new Paragraph("Data Consum"));
						PdfPCell c5 = new PdfPCell(new Paragraph("Expirat"));
						
						PdfPCell cell = new PdfPCell(new Paragraph("Raport Stoc:"));
						
						cell.setColspan(5);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBackgroundColor(BaseColor.RED);
						cell.setPadding(10.0f);
						
						c1.setBackgroundColor(BaseColor.GREEN);
						c2.setBackgroundColor(BaseColor.GREEN);
						c3.setBackgroundColor(BaseColor.GREEN);
						c4.setBackgroundColor(BaseColor.GREEN);
						c5.setBackgroundColor(BaseColor.GREEN);
						
						table.addCell(cell);
						
						table.addCell(c1);
						table.addCell(c2);
						table.addCell(c3);
						table.addCell(c4);
						table.addCell(c5);
						
						try {
							Connection connect = ConexiuneBD.getConnection();
							Statement stmt = connect.createStatement();
							String sql = "SELECT  cods, den_prod_stoc, cant, dataConsum, expirat " + 
										 "from stoc ";
							
							ResultSet rs = stmt.executeQuery(sql);
							
							while(rs.next()) {
								String cods = rs.getString("cods");
								String den_prod_stoc = rs.getString("den_prod_stoc");
								String cant = rs.getString("cant");
								String dataConsum = rs.getString("dataConsum");
								String expirat = rs.getString("expirat");
								
								c1 = new PdfPCell(new Paragraph(cods));
								c2 = new PdfPCell(new Paragraph(den_prod_stoc));
								c3 = new PdfPCell(new Paragraph(cant));
								c4 = new PdfPCell(new Paragraph(dataConsum));
								c5 = new PdfPCell(new Paragraph(expirat));
							
								table.addCell(c1);
								table.addCell(c2);
								table.addCell(c3);
								table.addCell(c4);
								table.addCell(c5);
															
							}
							
							doc.add(table);
							
							doc.add(new Paragraph(" "));
							doc.add(new Paragraph("Graficul stocului:"));
							doc.add(new Paragraph(" "));
							
							com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("PieChar.png");
							image.scaleAbsolute(480, 300);
							doc.add(image);
														
							doc.close();
							pdf.close();
							
							
							connect.close();
						}catch(Exception exception) {
							System.out.println(exception);
							}	
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (DocumentException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				
				}
			});
			btnPdfRaport.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnPdfRaport.setBounds(830, 401, 198, 40);
			stoc.add(btnPdfRaport);
			
			
			
			JMenuBar menuBar_4 = new JMenuBar();
			menuBar_4.setBounds(0, 0, 1028, 26);
			angajati.add(menuBar_4);
			
			JButton button_16 = new JButton("Meniu Barman");
			button_16.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					angajati.setVisible(false);
					meniuBarman.setVisible(true);
				}
			});
			menuBar_4.add(button_16);
			
			JButton button_19 = new JButton("Meniu Bucatar");
			button_19.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					angajati.setVisible(false);
					meniuBucatar.setVisible(true);
				}
			});
			menuBar_4.add(button_19);
			
			JButton button_20 = new JButton("Meniu Ospatar");
			button_20.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					angajati.setVisible(false);
					meniuOspatar.setVisible(true);
				}
			});
			menuBar_4.add(button_20);
			
			JButton button_23 = new JButton("Comenzi acasa");
			button_23.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					angajati.setVisible(false);
					comenziAcasa.setVisible(true);
				}
			});
			menuBar_4.add(button_23);
			
			JButton button_24 = new JButton("Rezervari mese");
			button_24.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					angajati.setVisible(false);
					rezervariMese.setVisible(true);
				}
			});
			menuBar_4.add(button_24);
			
			JButton button_25 = new JButton("Stoc");
			button_25.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					angajati.setVisible(false);
					stoc.setVisible(true);
				}
			});
			menuBar_4.add(button_25);
			
			JButton button_61 = new JButton("Meniu Alimente");
			button_61.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					angajati.setVisible(false);
					meniuAlimente.setVisible(true);
				}
			});
			menuBar_4.add(button_61);
			
			JButton button_3 = new JButton("Insert");
			button_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent paramActionEvent) {
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date date = new Date();
					txtDataAngajarii.setText(dateFormat.format(date));
					
					try {
						Connection connection = ConexiuneBD.getConnection();
						String query = "INSERT INTO angajati(nume_angajat, parola_angajat, functie, data_angajarii) VALUES (?,?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						
						pst.setString(1, txtNumeAngajat2.getText());
						pst.setString(2, txtParola.getText());
						pst.setString(3, txtFunctie.getText());
						pst.setString(4, txtDataAngajarii.getText());
						
						pst.execute();
						
						
						connection.close();
					}catch(Exception exception) {
						System.out.println(exception);
					}
					
					refreshTableAngajati();
				}
			});
			button_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_3.setBounds(12, 401, 103, 40);
			angajati.add(button_3);
			
			JButton button_17 = new JButton("Inapoi");
			button_17.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meniuManager.setVisible(true);
					angajati.setVisible(false);	
				}
			});
			button_17.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_17.setBounds(496, 401, 103, 40);
			angajati.add(button_17);
			
			JPanel panel_5 = new JPanel();
			panel_5.setLayout(null);
			panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_5.setBounds(12, 69, 331, 319);
			angajati.add(panel_5);
			
			JLabel lblFunctie = new JLabel("Functie:");
			lblFunctie.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblFunctie.setBounds(12, 81, 143, 27);
			panel_5.add(lblFunctie);
			
			JLabel lblSalar = new JLabel("Salar:");
			lblSalar.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblSalar.setBounds(12, 121, 156, 26);
			panel_5.add(lblSalar);
			
			txtFunctie = new JTextField();
			txtFunctie.setColumns(10);
			txtFunctie.setBounds(201, 84, 118, 27);
			panel_5.add(txtFunctie);
			
			txtSalar = new JTextField();
			txtSalar.setColumns(10);
			txtSalar.setBounds(201, 124, 118, 26);
			panel_5.add(txtSalar);
			
			JButton btnVeziDoarAngajatii = new JButton("Vezi doar angajatii activi");
			btnVeziDoarAngajatii.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent paramActionEvent) {
					refreshTableAngajatiActivi();
				}
			});
			btnVeziDoarAngajatii.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnVeziDoarAngajatii.setBounds(12, 202, 307, 26);
			panel_5.add(btnVeziDoarAngajatii);
			
			JButton btnVeziDoarAngajatii_1 = new JButton("Vezi doar angajatii inactivi");
			btnVeziDoarAngajatii_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent paramActionEvent) {
					refreshTableAngajatiInactivi();
				}
			});
			btnVeziDoarAngajatii_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnVeziDoarAngajatii_1.setBounds(12, 241, 307, 26);
			panel_5.add(btnVeziDoarAngajatii_1);
			
			JLabel lblNumeAngajat_1 = new JLabel("Nume angajat:");
			lblNumeAngajat_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblNumeAngajat_1.setBounds(12, 13, 143, 27);
			panel_5.add(lblNumeAngajat_1);
			
			txtNumeAngajat2 = new JTextField();
			txtNumeAngajat2.setColumns(10);
			txtNumeAngajat2.setBounds(201, 16, 118, 27);
			panel_5.add(txtNumeAngajat2);
			
			JButton btnVeziArjivaAngajatilor = new JButton("Vezi arhiva angajatilor");
			btnVeziArjivaAngajatilor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent paramActionEvent) {
					refreshTableArhivaAngajati();
				}
			});
			btnVeziArjivaAngajatilor.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnVeziArjivaAngajatilor.setBounds(12, 280, 307, 26);
			panel_5.add(btnVeziArjivaAngajatilor);
			
			txtDataAngajarii = new JTextField();
			txtDataAngajarii.setColumns(10);
			txtDataAngajarii.setBounds(201, 163, 118, 26);
			panel_5.add(txtDataAngajarii);
			
			JLabel lblDataAngajarii = new JLabel("Data angajarii:");
			lblDataAngajarii.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblDataAngajarii.setBounds(12, 160, 156, 26);
			panel_5.add(lblDataAngajarii);
			
			JLabel lblParola = new JLabel("Parola:");
			lblParola.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblParola.setBounds(12, 42, 156, 26);
			panel_5.add(lblParola);
			
			txtParola = new JTextField();
			txtParola.setColumns(10);
			txtParola.setBounds(201, 45, 118, 26);
			panel_5.add(txtParola);
			
			JLabel lblAngajati = new JLabel("Angajati");
			lblAngajati.setHorizontalAlignment(SwingConstants.CENTER);
			lblAngajati.setFont(new Font("Tahoma", Font.PLAIN, 30));
			lblAngajati.setBounds(0, 25, 1067, 37);
			angajati.add(lblAngajati);
			
			JScrollPane scrollPane_4 = new JScrollPane();
			scrollPane_4.setBounds(355, 69, 673, 319);
			angajati.add(scrollPane_4);
			
			tableAngajati = new JTable();
			tableAngajati.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent paramMouseEvent) {
					DefaultTableModel model = (DefaultTableModel) tableAngajati.getModel();
					int selectedRowIndex = tableAngajati.getSelectedRow();
					
					txtNumeAngajat2.setText(model.getValueAt(selectedRowIndex, 0).toString());
					txtParola.setText(model.getValueAt(selectedRowIndex, 1).toString());
					txtFunctie.setText(model.getValueAt(selectedRowIndex, 2).toString());
					txtSalar.setText(model.getValueAt(selectedRowIndex, 3).toString());
					txtDataAngajarii.setText(model.getValueAt(selectedRowIndex, 4).toString());
				}
			});
			scrollPane_4.setViewportView(tableAngajati);
			
			JButton button_5 = new JButton("Update");
			button_5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent paramActionEvent) {
					
					try {
						Connection connection = ConexiuneBD.getConnection();
						String query = "UPDATE angajati SET nume_angajat = ? , parola_angajat = ? , functie = ?, data_angajarii = ? WHERE nume_angajat = ?";
						PreparedStatement pst = connection.prepareStatement(query);
				
						
						pst.setString(1, txtNumeAngajat2.getText());
						pst.setString(2, txtParola.getText());
						pst.setString(3, txtFunctie.getText());
						pst.setString(4, txtDataAngajarii.getText());
						pst.setString(5, txtNumeAngajat2.getText());
						
						pst.execute();
						
						connection.close();
					}catch(Exception exception) {
						System.out.println(exception);
					}
					refreshTableAngajati();
				}
			});
			button_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_5.setBounds(127, 401, 103, 40);
			angajati.add(button_5);
			
			JButton button_18 = new JButton("Delete");
			button_18.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent paramActionEvent) {
					
					try {
						Connection connection = ConexiuneBD.getConnection();
						String query = "DELETE FROM angajati WHERE nume_angajat = ? AND data_angajarii = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						
						pst.setString(1, txtNumeAngajat2.getText());
						pst.setString(2, txtDataAngajarii.getText());
						
						pst.execute();
						
						connection.close();
					}catch(Exception exception) {
						System.out.println(exception);
					}
					refreshTableAngajati();
				}
			});
			button_18.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_18.setBounds(240, 401, 103, 40);
			angajati.add(button_18);
			
			JButton btnConcediat = new JButton("Concediat");
			btnConcediat.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent paramActionEvent) {
					
					Date date = new Date();
					String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
					
					try {
						Connection connection = ConexiuneBD.getConnection();
						String query = "UPDATE angajati SET data_plecarii = '"+ dateFormat +"' WHERE nume_angajat = ?";
						PreparedStatement pst = connection.prepareStatement(query);
					
						pst.setString(1, txtNumeAngajat2.getText());
						
						
						pst.execute();
						
						connection.close();
					}catch(Exception exception) {
						System.out.println(exception);
					}
					refreshTableAngajati();
					
				}
			});
			btnConcediat.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnConcediat.setBounds(355, 401, 129, 40);
			angajati.add(btnConcediat);
			
			JButton button_22 = new JButton("Exit");
			button_22.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame formLoginSystem = new JFrame("Exit");
					if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
						System.exit(0);
					}
				}
			});
			button_22.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_22.setBounds(611, 401, 103, 40);
			angajati.add(button_22);
			
			JButton btnVeziGrafic_1 = new JButton("Vezi grafic");
			btnVeziGrafic_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					
					
					try {
						Connection connect = ConexiuneBD.getConnection();
						Statement stmt = connect.createStatement();
						String sql = "select nume_angajat, salar " + 
									"from angajati,salgr " + 
									"where salgr.salg=angajati.salg" ;
						JDBCCategoryDataset dataset = new JDBCCategoryDataset(ConexiuneBD.getConnection(), sql);
						
						
						JFreeChart chart = ChartFactory.createLineChart("Grafic", "Angajat", "Salar", dataset, PlotOrientation.VERTICAL, false, true, true);
						BarRenderer render = null;
						CategoryPlot plot = null;
						render = new BarRenderer();
						ChartFrame frame = new ChartFrame("Query Char", chart);
						frame.setVisible(true);
						frame.setSize(650,650);
						
						ResultSet rs = stmt.executeQuery(sql);
						
						while(rs.next()) {
							String cant = rs.getString("cant");
							String prod = rs.getString("den_prod_stoc");
							
							System.out.println(cant);
							
											
						
						}
						connect.close();
					}catch(Exception exception) {
						System.out.println(exception);
						}		
					
				}
				
			});
			btnVeziGrafic_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnVeziGrafic_1.setBounds(726, 401, 137, 40);
			angajati.add(btnVeziGrafic_1);
			
			JButton btnGenereazaPdf = new JButton("PDF Raport");
			btnGenereazaPdf.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					Document doc = new Document();
					
					try {
						PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream("RaportAngajati.pdf"));
						doc.open();
						doc.add(new Paragraph("Acesta este raportul pentru angajati!                                                     " + new Date().toString(), FontFactory.getFont(FontFactory.TIMES_BOLD)));
						doc.add(new Paragraph(" "));
						
				
						PdfPTable table = new PdfPTable(6);
						table.setWidthPercentage(105);
						table.setSpacingBefore(10f);
						table.setSpacingAfter(10f);
						
						float[] colWidth = {2f,2f,2f,2f,2f,2f};
						table.setWidths(colWidth);
						
						PdfPCell c1 = new PdfPCell(new Paragraph("codAngajat"));
						PdfPCell c2 = new PdfPCell(new Paragraph("Nume"));
						PdfPCell c3 = new PdfPCell(new Paragraph("Parola"));
						PdfPCell c4 = new PdfPCell(new Paragraph("Functie"));
						PdfPCell c5 = new PdfPCell(new Paragraph("Data Angajare"));
						PdfPCell c6 = new PdfPCell(new Paragraph("Data Plecare"));
						
						PdfPCell cell = new PdfPCell(new Paragraph("Raport Angajati"));
						
						cell.setColspan(6);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBackgroundColor(BaseColor.RED);
						cell.setPadding(10.0f);
						
						c1.setBackgroundColor(BaseColor.GREEN);
						c2.setBackgroundColor(BaseColor.GREEN);
						c3.setBackgroundColor(BaseColor.GREEN);
						c4.setBackgroundColor(BaseColor.GREEN);
						c5.setBackgroundColor(BaseColor.GREEN);
						c6.setBackgroundColor(BaseColor.GREEN);
						
						table.addCell(cell);
						
						table.addCell(c1);
						table.addCell(c2);
						table.addCell(c3);
						table.addCell(c4);
						table.addCell(c5);
						table.addCell(c6);
						
						try {
							Connection connect = ConexiuneBD.getConnection();
							Statement stmt = connect.createStatement();
							String sql = "SELECT  coda, nume_angajat, parola_angajat, functie, data_angajarii, data_plecarii " + 
										 "from angajati ";
							
							ResultSet rs = stmt.executeQuery(sql);
							
							while(rs.next()) {
								String coda = rs.getString("coda");
								String nume_angajat = rs.getString("nume_angajat");
								String parola_angajat = rs.getString("parola_angajat");
								String functie = rs.getString("functie");
								String data_angajarii = rs.getString("data_angajarii");
								String data_plecarii = rs.getString("data_plecarii");
								
								c1 = new PdfPCell(new Paragraph(coda));
								c2 = new PdfPCell(new Paragraph(nume_angajat));
								c3 = new PdfPCell(new Paragraph(parola_angajat));
								c4 = new PdfPCell(new Paragraph(functie));
								c5 = new PdfPCell(new Paragraph(data_angajarii));
								c6 = new PdfPCell(new Paragraph(data_plecarii));
							
								table.addCell(c1);
								table.addCell(c2);
								table.addCell(c3);
								table.addCell(c4);
								table.addCell(c5);
								table.addCell(c6);
															
							}
							
							doc.add(table);
							
							doc.add(new Paragraph(" "));
							doc.add(new Paragraph("Graficul salarizarii:"));
							doc.add(new Paragraph(" "));
							
							com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("LineChar.png");
							image.scaleAbsolute(480, 300);
							doc.add(image);
														
							doc.close();
							pdf.close();
							
							
							connect.close();
						}catch(Exception exception) {
							System.out.println(exception);
							}	
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				}
			});
			btnGenereazaPdf.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnGenereazaPdf.setBounds(876, 401, 137, 40);
			angajati.add(btnGenereazaPdf);
			
			
			
			JLabel lblMeniu = new JLabel("Meniu");
			lblMeniu.setHorizontalAlignment(SwingConstants.CENTER);
			lblMeniu.setFont(new Font("Tahoma", Font.PLAIN, 30));
			lblMeniu.setBounds(0, 26, 1028, 57);
			meniuAlimente.add(lblMeniu);
			
			JMenuBar menuBar_5 = new JMenuBar();
			menuBar_5.setBounds(0, 0, 1028, 26);
			meniuAlimente.add(menuBar_5);
			
			JButton button_48 = new JButton("Meniu Ospatar");
			button_48.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meniuOspatar.setVisible(true);
					meniuAlimente.setVisible(false);	
				}
			});
			menuBar_5.add(button_48);
			
			JButton button_47 = new JButton("Meniu Bucatar");
			button_47.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meniuBucatar.setVisible(true);
					meniuAlimente.setVisible(false);
				}
			});
			menuBar_5.add(button_47);
			
			JButton btnMeniuBarman_2 = new JButton("Meniu Barman");
			btnMeniuBarman_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meniuBarman.setVisible(true);
					meniuAlimente.setVisible(false);
				}
			});
			menuBar_5.add(btnMeniuBarman_2);
			
			JButton button_44 = new JButton("Rezervari mese");
			button_44.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rezervariMese.setVisible(true);
					meniuAlimente.setVisible(false);
				}
			});
			menuBar_5.add(button_44);
			
			JButton button_45 = new JButton("Comenzi acasa");
			button_45.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					comenziAcasa.setVisible(true);
					meniuAlimente.setVisible(false);
				}
			});
			menuBar_5.add(button_45);
			
			JButton button_46 = new JButton("Stoc");
			button_46.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stoc.setVisible(true);
					meniuAlimente.setVisible(false);
				}
			});
			menuBar_5.add(button_46);
			
			JButton button_41 = new JButton("Inapoi");
			button_41.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inregistrare.setVisible(true);
					meniuAlimente.setVisible(false);
				}
			});
			button_41.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_41.setBounds(16, 432, 103, 40);
			meniuAlimente.add(button_41);
			
			JButton button_42 = new JButton("Exit");
			button_42.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame formLoginSystem = new JFrame("Exit");
					if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
						System.exit(0);
				}
			}
			});
			button_42.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_42.setBounds(131, 432, 103, 40);
			meniuAlimente.add(button_42);
			
			JScrollPane scrollPane_5 = new JScrollPane();
			scrollPane_5.setBounds(16, 113, 1023, 286);
			meniuAlimente.add(scrollPane_5);
			
			tableMeniu = new JTable();
			scrollPane_5.setViewportView(tableMeniu);
			
			
			
			JLabel lblRezervarile = new JLabel("Rezervarile");
			lblRezervarile.setHorizontalAlignment(SwingConstants.CENTER);
			lblRezervarile.setFont(new Font("Tahoma", Font.PLAIN, 30));
			lblRezervarile.setBounds(0, 26, 1028, 57);
			meniuRezervari.add(lblRezervarile);
			
			JMenuBar menuBar_6 = new JMenuBar();
			menuBar_6.setBounds(0, 0, 1028, 26);
			meniuRezervari.add(menuBar_6);
			
			JButton button_55 = new JButton("Meniu Ospatar");
			button_55.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meniuOspatar.setVisible(true);
					meniuRezervari.setVisible(false);
				}
			});
			menuBar_6.add(button_55);
			
			JButton button_56 = new JButton("Meniu Bucatar");
			button_56.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meniuBucatar.setVisible(true);
					meniuRezervari.setVisible(false);
				}
			});
			menuBar_6.add(button_56);
			
			JButton button_57 = new JButton("Meniu Barman");
			button_57.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meniuBarman.setVisible(true);
					meniuRezervari.setVisible(false);
				}
			});
			menuBar_6.add(button_57);
			
			JButton button_58 = new JButton("Rezervari mese");
			button_58.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rezervariMese.setVisible(true);
					meniuRezervari.setVisible(false);
				}
			});
			menuBar_6.add(button_58);
			
			JButton button_59 = new JButton("Comenzi acasa");
			button_59.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					comenziAcasa.setVisible(true);
					meniuRezervari.setVisible(false);
				}
			});
			menuBar_6.add(button_59);
			
			JButton button_60 = new JButton("Stoc");
			button_60.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stoc.setVisible(true);
					meniuRezervari.setVisible(false);
				}
			});
			menuBar_6.add(button_60);
			
			JButton button_68 = new JButton("Meniu Alimente");
			button_68.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meniuRezervari.setVisible(false);
					meniuAlimente.setVisible(true);
				}
			});
			menuBar_6.add(button_68);
			
			JButton button_43 = new JButton("Inapoi");
			button_43.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inregistrare.setVisible(true);
					meniuRezervari.setVisible(false);
				}
			});
			button_43.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_43.setBounds(16, 432, 103, 40);
			meniuRezervari.add(button_43);
			
			JButton button_49 = new JButton("Exit");
			button_49.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame formLoginSystem = new JFrame("Exit");
					if (JOptionPane.showConfirmDialog(formLoginSystem, "Esti sigur ca vrei sa iesi din aplicatie?", "Aplicatie restaurant",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
						System.exit(0);
					}
				}
			});
			button_49.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_49.setBounds(131, 432, 103, 40);
			meniuRezervari.add(button_49);
			
			JScrollPane scrollPane_6 = new JScrollPane();
			scrollPane_6.setBounds(16, 113, 1023, 286);
			meniuRezervari.add(scrollPane_6);
			
			tableRezervari = new JTable();
			scrollPane_6.setViewportView(tableRezervari);
			
			
			
			JLabel lblMeniuBon = new JLabel("Meniu Bon");
			lblMeniuBon.setHorizontalAlignment(SwingConstants.CENTER);
			lblMeniuBon.setFont(new Font("Tahoma", Font.PLAIN, 30));
			lblMeniuBon.setBounds(0, 26, 1028, 57);
			meniuBon.add(lblMeniuBon);
			
			JMenuBar menuBar_7 = new JMenuBar();
			menuBar_7.setBounds(0, 0, 1028, 26);
			meniuBon.add(menuBar_7);
			
			JButton button_67 = new JButton("Inapoi");
			button_67.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					meniuOspatar.setVisible(true);
					meniuBon.setVisible(false);	
					vizBon.setText("");
				}
			});
			button_67.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_67.setBounds(16, 432, 103, 40);
			meniuBon.add(button_67);
			
			JButton button_70 = new JButton("Exit");
			button_70.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_70.setBounds(131, 432, 103, 40);
			meniuBon.add(button_70);
			
			comboBoxNrMasaBon.setFont(new Font("Tahoma", Font.PLAIN, 20));
			comboBoxNrMasaBon.setBounds(106, 121, 142, 26);
			meniuBon.add(comboBoxNrMasaBon);
			
			JLabel label_1 = new JLabel("Nr Masa:");
			label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			label_1.setBounds(16, 121, 147, 26);
			meniuBon.add(label_1);
			
			JButton btnValideaza = new JButton("Valideaza");
			btnValideaza.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					vizBon.setText("");
					bon();

				}
			});
			btnValideaza.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnValideaza.setBounds(258, 121, 156, 26);
			meniuBon.add(btnValideaza);
			
			JScrollPane scrollPane_7 = new JScrollPane();
			scrollPane_7.setBounds(16, 157, 442, 265);
			meniuBon.add(scrollPane_7);
			
			
			scrollPane_7.setViewportView(vizBon);
			
			JPanel meniuAprovizionare = new JPanel();
			meniuAprovizionare.setLayout(null);
			frame.getContentPane().add(meniuAprovizionare, "name_710072535440000");
			
			JMenuBar menuBar_8 = new JMenuBar();
			menuBar_8.setBounds(0, 0, 1067, 26);
			meniuAprovizionare.add(menuBar_8);
			
			JButton button_71 = new JButton("Insert");
			button_71.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_71.setBounds(12, 401, 103, 40);
			meniuAprovizionare.add(button_71);
			
			JButton button_72 = new JButton("Update");
			button_72.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_72.setBounds(127, 401, 103, 40);
			meniuAprovizionare.add(button_72);
			
			JButton button_73 = new JButton("Delete");
			button_73.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_73.setBounds(240, 401, 103, 40);
			meniuAprovizionare.add(button_73);
			
			JScrollPane scrollPane_8 = new JScrollPane();
			scrollPane_8.setBounds(358, 121, 670, 267);
			meniuAprovizionare.add(scrollPane_8);
			
			JPanel panel_6 = new JPanel();
			panel_6.setLayout(null);
			panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_6.setBounds(12, 121, 331, 267);
			meniuAprovizionare.add(panel_6);
			
			JLabel label_4 = new JLabel("Cantitate:");
			label_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
			label_4.setBounds(12, 64, 143, 27);
			panel_6.add(label_4);
			
			JLabel label_5 = new JLabel("Data consumului:");
			label_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
			label_5.setBounds(12, 104, 156, 26);
			panel_6.add(label_5);
			
			textField_2 = new JTextField();
			textField_2.setColumns(10);
			textField_2.setBounds(201, 67, 118, 27);
			panel_6.add(textField_2);
			
			textField_3 = new JTextField();
			textField_3.setColumns(10);
			textField_3.setBounds(222, 107, 97, 26);
			panel_6.add(textField_3);
			
			JButton button_74 = new JButton("Vezi doar produsele expirate");
			button_74.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_74.setBounds(12, 150, 307, 26);
			panel_6.add(button_74);
			
			JButton button_75 = new JButton("Vezi doar produsele epuizate");
			button_75.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_75.setBounds(12, 189, 307, 26);
			panel_6.add(button_75);
			
			JLabel label_8 = new JLabel("Nume produs:");
			label_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
			label_8.setBounds(12, 24, 143, 27);
			panel_6.add(label_8);
			
			textField_4 = new JTextField();
			textField_4.setColumns(10);
			textField_4.setBounds(201, 27, 118, 27);
			panel_6.add(textField_4);
			
			JButton button_76 = new JButton("Vezi toate produsele");
			button_76.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_76.setBounds(12, 228, 307, 26);
			panel_6.add(button_76);
			
			JLabel lblAprovizionare = new JLabel("Aprovizionare");
			lblAprovizionare.setHorizontalAlignment(SwingConstants.CENTER);
			lblAprovizionare.setFont(new Font("Tahoma", Font.PLAIN, 30));
			lblAprovizionare.setBounds(0, 73, 1067, 37);
			meniuAprovizionare.add(lblAprovizionare);
			
			JButton button_77 = new JButton("Inapoi");
			button_77.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_77.setBounds(355, 401, 103, 40);
			meniuAprovizionare.add(button_77);
			
			JButton button_78 = new JButton("Exit");
			button_78.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_78.setBounds(477, 401, 103, 40);
			meniuAprovizionare.add(button_78);
			
			JButton button_79 = new JButton("Vezi grafic");
			button_79.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_79.setBounds(602, 401, 130, 40);
			meniuAprovizionare.add(button_79);
			
			JButton button_80 = new JButton("PDF Raport");
			button_80.setFont(new Font("Tahoma", Font.PLAIN, 20));
			button_80.setBounds(742, 401, 138, 40);
			meniuAprovizionare.add(button_80);
	}
}
