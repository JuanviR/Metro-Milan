package igrafica;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;

import igrafica.metro.*;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame mainFrame;
	private JPanel contentPane;
	private List<String> estaciones;
	private DefaultListModel<String> listaOrigenD;
	private DefaultListModel<String> listaDestinoD;
	private boolean flag1;
	private boolean flag2;

	protected JTextArea nodos;
	protected JTextArea textArea;

	private Seleccion seleccion;
	private Construccion construccion;
	private AEstrella servicios;
	private ArrayList<String> trasbordos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
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
	public VentanaPrincipal() {
		mainFrame = new JFrame();
		contentPane = new JPanel();
		seleccion = new Seleccion();
		servicios = seleccion.getServicios();
		construccion = seleccion.getConstrucction();
		trasbordos = seleccion.getRuta().getTrasbordos();
		estaciones = new ArrayList<String>();
		listaOrigenD = new DefaultListModel<String>();
		listaDestinoD = new DefaultListModel<String>();
		flag1 = false;
		flag2 = false;
		initialize();
	}

	public int letratoInt(String letra){
		switch(letra){
		case "M1": return 0;
		case "M1S": return 1;
		case "M2": return 2;
		/* M3 */
		default: return 3;
		}
	}

	public List<Estacion> verificarDestino(Estacion origen, String destino){
		String linea = "";
		Estacion trasbordoB = construccion.getEstacion(destino);
		List<Estacion> comparar = servicios.recorrido(origen, trasbordoB), resultado = null;
		switch(trasbordoB.getNumEnlaces())
		{
		case 4:
			linea = trasbordoB.getConexiones(3);
			resultado = servicios.recorrido(origen, construccion.getLineas()[letratoInt(linea)].get(destino));
			comparar = servicios.getUltimoTiempo() > comparar.get(0).getPesoRecorrido() ? comparar : resultado;
		case 3:
			linea = trasbordoB.getConexiones(2);
			resultado = servicios.recorrido(origen, construccion.getLineas()[letratoInt(linea)].get(destino));
			comparar = servicios.getUltimoTiempo() > comparar.get(0).getPesoRecorrido() ? comparar : resultado;
		case 2:
			linea = trasbordoB.getConexiones(1);
			resultado = servicios.recorrido(origen, construccion.getLineas()[letratoInt(linea)].get(destino));
			comparar = servicios.getUltimoTiempo() > comparar.get(0).getPesoRecorrido() ? comparar : resultado;
		case 1:
			linea = trasbordoB.getConexiones(0);
			resultado = servicios.recorrido(origen, construccion.getLineas()[letratoInt(linea)].get(destino));
			comparar = servicios.getUltimoTiempo() > comparar.get(0).getPesoRecorrido() ? comparar : resultado;
		}
		return comparar;
	}

	public void hacerRecorrido(){
		double tiempo = 0;
		List<Estacion> recorrido = new ArrayList<Estacion>();
		if(!this.trasbordos.contains(seleccion.getOrigen()) && !this.trasbordos.contains(seleccion.getDestino()))
		{
			recorrido = servicios.recorrido(construccion.getEstacion(seleccion.getOrigen()), construccion.getEstacion(seleccion.getDestino()));
			tiempo = servicios.getUltimoTiempo();
		}
		else if(this.trasbordos.contains(seleccion.getOrigen()) && !this.trasbordos.contains(seleccion.getDestino()))
		{
			String linea = "";
			Estacion trasbordoA = construccion.getEstacion(seleccion.getOrigen());
			recorrido = servicios.recorrido(trasbordoA, construccion.getEstacion(seleccion.getDestino()));
			tiempo = servicios.getUltimoTiempo();
			List<Estacion> comparar = null;
			switch(trasbordoA.getNumEnlaces()){
			case 4:
				linea = trasbordoA.getConexiones(3);
				comparar = servicios.recorrido(construccion.getLineas()[letratoInt(linea)].get(seleccion.getOrigen()), construccion.getEstacion(seleccion.getDestino()));
				recorrido = tiempo > servicios.getUltimoTiempo() ? comparar : recorrido;
				tiempo = tiempo > servicios.getUltimoTiempo() ? servicios.getUltimoTiempo() : tiempo;
			case 3:
				linea = trasbordoA.getConexiones(2);
				comparar = servicios.recorrido(construccion.getLineas()[letratoInt(linea)].get(seleccion.getOrigen()), construccion.getEstacion(seleccion.getDestino()));
				recorrido = tiempo > servicios.getUltimoTiempo() ? comparar : recorrido;
				tiempo = tiempo > servicios.getUltimoTiempo() ? servicios.getUltimoTiempo() : tiempo;
			case 2:
				linea = trasbordoA.getConexiones(1);
				comparar = servicios.recorrido(construccion.getLineas()[letratoInt(linea)].get(seleccion.getOrigen()), construccion.getEstacion(seleccion.getDestino()));
				recorrido = tiempo > servicios.getUltimoTiempo() ? comparar : recorrido;
				tiempo = tiempo > servicios.getUltimoTiempo() ? servicios.getUltimoTiempo() : tiempo;
			case 1:
				linea = trasbordoA.getConexiones(0);
				comparar = servicios.recorrido(construccion.getLineas()[letratoInt(linea)].get(seleccion.getOrigen()), construccion.getEstacion(seleccion.getDestino()));
				recorrido = tiempo > servicios.getUltimoTiempo() ? comparar : recorrido;
				tiempo = tiempo > servicios.getUltimoTiempo() ? servicios.getUltimoTiempo() : tiempo;
			}
		}
		else if(!this.trasbordos.contains(seleccion.getOrigen()) && this.trasbordos.contains(seleccion.getDestino()))
		{
			recorrido = verificarDestino(construccion.getEstacion(seleccion.getOrigen()), seleccion.getDestino());
			tiempo = recorrido.get(0).getPesoRecorrido();
		}
		else {
			List<Estacion> comparar = null;
			Estacion origen = construccion.getEstacion(seleccion.getOrigen());
			recorrido = verificarDestino(origen, seleccion.getDestino());
			tiempo = recorrido.get(0).getPesoRecorrido();
			if(origen.getNumEnlaces()>0)
			{
				for(int i = 0; i < origen.getNumEnlaces(); i++)
				{
					String linea=origen.getConexiones(i);
					origen = construccion.getLineas()[letratoInt(linea)].get(seleccion.getOrigen());
					comparar = verificarDestino(origen, seleccion.getDestino());
					recorrido = tiempo > comparar.get(0).getPesoRecorrido() ? comparar : recorrido;
					tiempo = tiempo > comparar.get(0).getPesoRecorrido() ? comparar.get(0).getPesoRecorrido() : tiempo;
				}
			}
		}
		seleccion.setOrigen("");
		seleccion.setDestino("");
		String ruta = "";
		String nodo = "";
		String nombre = "";
		String linea = "";
		//boolean trasbordo = false;
		int size = recorrido.size();
		for(int i = size - 1; i >= 0; i--){
			nombre = recorrido.get(i).getNombre();
			if(i == size - 1){
				linea = recorrido.get(i).getLinea();
				ruta = ruta + "Usted esta en " + nombre + "\n";
				ruta = ruta + "Tome la linea " + linea + " con direccion a ";	
			}
			else if(i > 0 && i != size - 1) {
				if(!recorrido.get(i).getLinea().equals(linea))
				{
					ruta = ruta + nombre + "\n";
					linea = recorrido.get(i).getLinea();
					ruta = ruta + "Haga un trasbordo\n";
					ruta = ruta + "Tome la linea " + linea + " con direccion a ";
				}
			}
			else {
				ruta = ruta + nombre + "\n";
				ruta = ruta + "Ha llegado a su destino\n";
			}
			nodo = nodo + recorrido.get(i).getNombre() +" (linea " + recorrido.get(i).getLinea() + ")\n";
		}
		ruta = ruta + "\n\nTiempo estimado: " + (int)tiempo + " minutos";
		textArea.setText(ruta);
		nodos.setText(nodo);
	}

	private void initialize(){
		setTitle("Metro de Milan");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/img/Logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1270, 677);
		setResizable(false);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon img1 = new ImageIcon(VentanaPrincipal.class.getResource("/img/mapa.png"));
		ImageIcon img2 = new ImageIcon(VentanaPrincipal.class.getResource("/img/Background.jpg"));
		ImageIcon img4 = new ImageIcon(VentanaPrincipal.class.getResource("/img/Background2.jpg"));

		JPanel finalpanel = new JPanel();
		finalpanel.setBounds(0, 0, 1266, 850);
		contentPane.add(finalpanel);
		finalpanel.setLayout(null);
		finalpanel.setVisible(false);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 1266, 850);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);

		JScrollPane scrollPaneEstaciones = new JScrollPane();
		scrollPaneEstaciones.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneEstaciones.setBounds(10, 199, 200, 232);
		finalpanel.add(scrollPaneEstaciones);

		nodos = new JTextArea();
		scrollPaneEstaciones.setViewportView(nodos);
		nodos.setEditable(false);

		JScrollPane scrollPaneRecorrido = new JScrollPane();
		scrollPaneRecorrido.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneRecorrido.setBounds(1000, 196, 200, 250);
		finalpanel.add(scrollPaneRecorrido);

		textArea = new JTextArea();
		scrollPaneRecorrido.setViewportView(textArea);
		textArea.setEditable(false);

		JLabel lblRecorrido = new JLabel("Recorrido");
		lblRecorrido.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecorrido.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRecorrido.setBounds(10, 153, 200, 35);
		finalpanel.add(lblRecorrido);

		JLabel lblGrupo2 = new JLabel("@Grupo 1");
		lblGrupo2.setBounds(10, 828, 78, 22);
		finalpanel.add(lblGrupo2);

		JLabel lblmapa2 = new JLabel("");
		lblmapa2.setBounds(220, 100, 720, 500);
		finalpanel.add(lblmapa2);
		ImageIcon mapa2 = new ImageIcon(img1.getImage().getScaledInstance(lblmapa2.getWidth(), lblmapa2.getHeight(), Image.SCALE_DEFAULT));
		lblmapa2.setIcon(mapa2);

		JLabel lblFondo2 = new JLabel("");
		lblFondo2.setBounds(0, 0, 1266, 850);
		finalpanel.add(lblFondo2);
		ImageIcon fondo2 = new ImageIcon(img4.getImage().getScaledInstance(lblFondo2.getWidth(), lblFondo2.getHeight(), Image.SCALE_DEFAULT));
		lblFondo2.setIcon(fondo2);

		JScrollPane scrollPaneOrigen = new JScrollPane();
		scrollPaneOrigen.setBounds(10, 196, 200, 250);
		mainPanel.add(scrollPaneOrigen);
		scrollPaneOrigen.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JList<String> listOrigen = new JList<String>(listaOrigenD);
		scrollPaneOrigen.setViewportView(listOrigen);

		listOrigen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				seleccion.setOrigen(listOrigen.getSelectedValue().toString());
				listaDestinoD.clear();
				estaciones = construccion.estacionesOrdenadas();
				estaciones.remove(listOrigen.getSelectedValue());
				final int longitud = estaciones.size();
				for(int i = 0; i < longitud ; ++i){
					listaDestinoD.addElement(estaciones.get(i));
				}
				flag1 = true;
			}
		});

		JScrollPane scrollPaneDestino = new JScrollPane();
		scrollPaneDestino.setBounds(1000, 196, 200, 250);
		mainPanel.add(scrollPaneDestino);
		scrollPaneDestino.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JList<String> listDestino = new JList<String>(listaDestinoD);
		listDestino.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				seleccion.setDestino(listDestino.getSelectedValue().toString());
				flag2 = true;
			}
		});
		scrollPaneDestino.setViewportView(listDestino);


		/**Boton para Calcular*/
		
		JButton btnCalcular = new JButton("Calcular Ruta");
		btnCalcular.setBounds(500, 20, 200, 50);
		mainPanel.add(btnCalcular);
		btnCalcular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(flag1 && flag2) {
					flag1 = false;
					flag2 = false;
					listOrigen.clearSelection();
					listDestino.clearSelection();
					mainPanel.setVisible(false);
					finalpanel.setVisible(true);
					hacerRecorrido();

				} else {
					JOptionPane.showMessageDialog(mainFrame, "Seleccione un origen y un destino validos.", "¡Atencion!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnCalcular.setFont(new Font("Tahoma", Font.BOLD, 20));

		
		/**Boton para Reiniciar*/
		
		JButton btnReset = new JButton("Reiniciar");
		btnReset.setBounds(500, 20, 200, 50);
		mainPanel.add(btnReset);
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainPanel.setVisible(true);
				finalpanel.setVisible(false);
			}
		});

		btnReset.setFont(new Font("Tahoma", Font.BOLD, 20));
		finalpanel.add(btnReset);


		JLabel lblMapa = new JLabel("");
		lblMapa.setBounds(220, 100, 720, 500);
		mainPanel.add(lblMapa);
		ImageIcon mapa = new ImageIcon(img1.getImage().getScaledInstance(lblMapa.getWidth(), lblMapa.getHeight(), Image.SCALE_DEFAULT));
		lblMapa.setIcon(mapa);

		JLabel lblOrigen = new JLabel("Origen");
		lblOrigen.setBounds(10, 150, 200, 35);
		mainPanel.add(lblOrigen);
		lblOrigen.setFont(new Font("Tahoma", Font.BOLD , 20));
		lblOrigen.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblDestino = new JLabel("Destino");
		lblDestino.setBounds(1000, 150, 200, 35);
		mainPanel.add(lblDestino);
		lblDestino.setHorizontalAlignment(SwingConstants.CENTER);
		lblDestino.setFont(new Font("Tahoma", Font.BOLD , 20));

		JLabel lblGrupo = new JLabel("@Grupo 1");
		lblGrupo.setBounds(10, 828, 78, 22);
		mainPanel.add(lblGrupo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 1266, 850);
		mainPanel.add(lblFondo);
		ImageIcon fondo = new ImageIcon(img2.getImage().getScaledInstance(lblFondo.getWidth(), lblFondo.getHeight(), Image.SCALE_DEFAULT));
		lblFondo.setIcon(fondo);

		estaciones = construccion.estacionesOrdenadas();

		final int longitud = estaciones.size();
		for(int i = 0; i < longitud ; ++i){
			listaOrigenD.addElement(estaciones.get(i));
		}
	}
}
