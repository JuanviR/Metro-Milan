package igrafica.metro;

public class Estacion 
{
	private Estacion padre;
	private double pesoRecorrido;
	private Coordenada coordenada;
	private String  linea, nombre, estacionA, estacionB, estacionC;
	private String[] conexiones;
	private static final double VELOCIDAD = 0.5335; //32.01 km/h = 0.5335 km/min 
	private int num_enlaces, tiempoA, tiempoB, tiempoC;

	public Estacion(String linea, String nombre, String estacionA, String tiempoA, String estacionB, String tiempoB,String conexiones, String latitud, String longitud)
	{
		this.linea = linea;
		this.nombre = nombre;
		this.estacionA = estacionA;
		if(!tiempoA.equals("#N/A"))
			this.tiempoA = Integer.parseInt(tiempoA);
		else
			this.tiempoA = 0;
		this.estacionB = estacionB;
		if(!tiempoB.equals("#N/A"))
			this.tiempoB = Integer.parseInt(tiempoB);
		else
			this.tiempoB = 0;
		this.estacionC = "";
		this.setConexiones(conexiones);
		this.coordenada = new Coordenada(latitud, longitud);
		this.padre = null;
		this.pesoRecorrido = 0;
	}
	
	public boolean existePadre() { return this.padre != null; }
	
	public boolean existeB() { return !this.estacionB.equals("#N/A"); }
	
	public boolean existeC() { return !this.estacionC.equals(""); }
	
	public String getLinea() { return this.linea; }

	public int getNumEnlaces() { return this.num_enlaces; }

	public String getNombre() { return this.nombre; }

	public String getEstacionA() { return this.estacionA; }

	public String getEstacionB() { return this.estacionB; }
	
	public String getEstacionC() { return this.estacionC; }

	public Estacion getPadre() { return this.padre; }
	
	public double getPesoRecorrido() { return this.pesoRecorrido; }
	
	public double getTiempoHeuristico(Estacion destino) { return tiempoHeuristicoMinutos(this.haversine(destino));}

	public void setPadre(Estacion estacion) { this.padre = estacion; }
	
	public void setPesoRecorrido(double peso) { this.pesoRecorrido = peso; }
	
	public void setLinea(String linea) { this.linea = linea; }
	
	public void setEstacionC(String estacion, int tiempo) 
	{ 
		this.estacionC = estacion; 
		this.tiempoC = tiempo;
	}

	private double tiempoHeuristicoMinutos(double distancia) { return distancia / VELOCIDAD; }
	
	public boolean equals(Object o)
	{
		Estacion estacion = o instanceof Estacion ? (Estacion) o : null;
		return estacion != null && this.getNombre().equals(estacion.getNombre()) && this.getLinea().equals(estacion.getLinea());
	}
	
	public int getTiempoReal(Estacion destino)
	{
		String estacion = destino.getNombre();
		int tiempo = 0;
		if(this.estacionA.equals(estacion)) 
			tiempo = this.tiempoA;
		else if(this.estacionB.equals(estacion))
			tiempo = this.tiempoB;
		else if(this.estacionC.equals(estacion))
			tiempo = this.tiempoC;
		return tiempo;
	}

	private double haversine(Estacion destino)
	{
		double lat1 = this.coordenada.deegreToDecimalLat(), lat2 = destino.coordenada.deegreToDecimalLat();
		double lon1 = this.coordenada.deegreToDecimalLon(), lon2 = destino.coordenada.deegreToDecimalLon();
		
		double R = 6372.8, fi1 = Math.toRadians(lat1), fi2 = Math.toRadians(lat2);
		double deltaFi = Math.toRadians(lat2 - lat1);
		double deltaLambda = Math.toRadians(lon2 - lon1);
		
		double a = Math.pow(Math.sin(deltaFi/2),2) + Math.pow(Math.sin(deltaLambda/2),2) * Math.cos(fi1) * Math.cos(fi2);
		double c =  2 * Math.asin(Math.sqrt(a));
		return R * c;
	}
	public String getConexiones(int i) {
		return conexiones[i];
	}
	public void setConexiones(String conexiones) {
		if(conexiones.equals(""))
			num_enlaces = 0;
		else
		{
			this.conexiones = conexiones.split("-");
			num_enlaces = this.conexiones.length;
		}
	}
}