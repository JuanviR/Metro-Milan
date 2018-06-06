package igrafica.metro;

public class Seleccion {
	private Construccion construccion;
	private AEstrella servicios;
	private Mapa mapa;
	private Ruta ruta;
	private String origen;
	private String destino;

	public Seleccion() {
		construccion = new Construccion();
		servicios = new AEstrella();
		mapa = new Mapa(this,servicios);
		ruta = new Ruta(this,mapa,servicios);
		origen = "";
		setDestino("");
	}
	
	public Construccion getConstrucction(){
		return construccion;
	}
	
	public AEstrella getServicios() {
		return servicios;
	}
	
	public Mapa getMapa() {
		return mapa;
	}
	
	public Ruta getRuta(){
		return ruta;
	}
	
	public String getOrigen() {
		return origen;
	}
	
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}
}
