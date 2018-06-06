package igrafica.metro;

public class Coordenada {
	
	private double latitud,longitud;
	private String letraLat, letraLon;
	
	public Coordenada(String latitud, String longitud){
		this.latitud = Double.parseDouble(latitud);
		this.longitud = Double.parseDouble(longitud);
	}
	
	public double deegreToDecimalLat(){
		return latitud;
	}
	
	public double deegreToDecimalLon(){
		return longitud;
	}
	
	public String getLetraLat(){
		return this.letraLat;
	}

	public String getLetraLon(){
		return this.letraLon;
	}
}
