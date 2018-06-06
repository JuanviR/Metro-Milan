package igrafica.metro;

public class Mapa {
	@SuppressWarnings("unused")
	private AEstrella servicios;
	@SuppressWarnings("unused")
	private final Seleccion seleccion;

	public Mapa(Seleccion seleccion2, AEstrella servicios) {
		this.seleccion = seleccion2;
		this.servicios = servicios;
	}
}
