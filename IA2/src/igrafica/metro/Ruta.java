package igrafica.metro;

import java.util.ArrayList;

public class Ruta {
	@SuppressWarnings("unused")
	private Seleccion seleccion;
	@SuppressWarnings("unused")
	private Mapa mapa;
	@SuppressWarnings("unused")
	private AEstrella servicios;
	private ArrayList<String> trasbordos;
	@SuppressWarnings("unused")
	private Construccion construccion;

	public Ruta(Seleccion seleccion, Mapa mapa, AEstrella servicios) {
		this.seleccion = seleccion;
		this.mapa = mapa;
		this.servicios = servicios;
		this.trasbordos = new ArrayList<String>();
		this.trasbordos.add("Cadorna");
		this.trasbordos.add("Duomo");
		this.trasbordos.add("Centrale");
		this.trasbordos.add("Loreto");
		construccion = new Construccion();
	}
	
	public ArrayList<String> getTrasbordos() {
		return trasbordos;
	}
	
}
