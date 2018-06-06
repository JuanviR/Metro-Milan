package igrafica.metro;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AEstrella{
	private static final double TRASBORDO = 6;
	private HashMap<Double,Estacion> abierta;
	private List<Estacion> cerrada;
	private Estacion destino;
	private double ultimoTiempo;
	private Construccion construcciones;

	public AEstrella(){
		this.ultimoTiempo = 0;
		this.destino = null;
	}

	public int letratoInt(String letra){
		switch(letra){
		case "M1": return 0;
		case "M1S": return 1;
		case "M2": return 2;
		default: return 3;
		}
	}

	private void agregarEnAbierta(String linea, Estacion padre, String hijo){
		Estacion estacionHijo = this.construcciones.getLineas()[letratoInt(linea)].get(hijo);
		if(estacionHijo != null){
			if(!this.cerrada.contains(estacionHijo)){
				double pesoDesdePadre = padre.getPesoRecorrido();
				pesoDesdePadre += padre.getNombre().equals(hijo) ? TRASBORDO + Math.random()/100 : padre.getTiempoReal(estacionHijo);
				if(estacionHijo.getPesoRecorrido() == 0 || estacionHijo.getPesoRecorrido() >= pesoDesdePadre)
				{
					estacionHijo.setPadre(padre);
					estacionHijo.setPesoRecorrido(pesoDesdePadre);
				}
				double peso = estacionHijo.getPesoRecorrido() + estacionHijo.getTiempoHeuristico(this.destino);
				this.abierta.put(peso, estacionHijo);
			}
		}
	}

	private void explorar(){
		Estacion estacionActual = null;
		List<Double> pesos = null;
		while(!this.abierta.containsValue(this.destino)){
			/* Se coge la estacion con menor peso de la lista abierta */
			pesos = new ArrayList<Double>(this.abierta.keySet());
			Collections.sort(pesos);
			estacionActual = this.abierta.remove(pesos.get(0));	
			if(this.cerrada.contains(estacionActual)) continue;

			/* Se exploran las estaciones que NO se encuentren en lista cerrada */
			String lineaActual = estacionActual.getLinea();
			agregarEnAbierta(lineaActual, estacionActual, estacionActual.getEstacionA());

			if(estacionActual.existeB())
				agregarEnAbierta(lineaActual, estacionActual, estacionActual.getEstacionB());

			if(estacionActual.existeC())
				agregarEnAbierta(lineaActual, estacionActual, estacionActual.getEstacionC());

			/* ¿Se puede realizar un trasbordo desde esta estación? */
			switch(estacionActual.getNumEnlaces()){
//			case 2:
//				lineaActual = estacionActual.getConexiones(1);
//				agregarEnAbierta(lineaActual, estacionActual, lineaActual+estacionActual.getNombre().substring(lineaActual.length()));
			case 1:
				lineaActual = estacionActual.getConexiones(0);
				agregarEnAbierta(lineaActual, estacionActual, lineaActual + " " + estacionActual.getNombre().split(" ")[1]);
			}

			/* Se inserta la estacion actual en lista cerrada para indicar que ya se ha explorado */
			this.cerrada.add(estacionActual);
		}

		/* Se coge el destino de la lista abierta y se inserta en cerrada */
		for(Map.Entry<Double,Estacion> entry : this.abierta.entrySet())
			if(entry.getValue().equals(this.destino))
				this.cerrada.add(entry.getValue());
	}

	public double getUltimoTiempo() { 
		return this.ultimoTiempo;
	}

	public List<Estacion> recorrido(Estacion origen, Estacion destino){
		List<Estacion> recorrido = new ArrayList<Estacion>();

		/* Se explora el grafo */
		this.abierta = new HashMap<Double, Estacion>();
		this.cerrada = new ArrayList<Estacion>();
		this.construcciones = new Construccion();
		this.destino = destino;
		this.abierta.put(origen.getTiempoHeuristico(destino), origen);
		explorar();

		/* Se obtiene el recorrido más óptimo */
		Estacion meta = this.cerrada.get(this.cerrada.size()-1);
		recorrido.add(meta);
		this.ultimoTiempo = meta.getPesoRecorrido();
		while(meta.existePadre()){
			recorrido.add(meta.getPadre());
			meta = meta.getPadre();
		}
		return recorrido;
	}
}