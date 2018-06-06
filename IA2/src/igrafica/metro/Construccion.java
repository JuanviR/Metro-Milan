package igrafica.metro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Construccion {

	private HashMap<String,Estacion> A, B, C, D;

	public Construccion() {

		A = new HashMap<String,Estacion>();
		B = new HashMap<String,Estacion>();
		C = new HashMap<String,Estacion>();
		D = new HashMap<String,Estacion>();

		try {
			InputStream in = getClass().getResourceAsStream("/img/distanciaEntreEstaciones.csv"); 
			BufferedReader bin = new BufferedReader(new InputStreamReader(in));			
			String linea;
			String[] partes;
			while((linea = bin.readLine()) != null){
				partes = linea.split(";");
				switch(partes[0]){
				case "M1": A.put(partes[0]+" "+partes[1], new Estacion(partes[0],partes[0]+" "+partes[1],partes[0]+" "+partes[2],partes[3],partes[0]+" "+partes[4],partes[5],partes[6],partes[7],partes[8]));
				break;
				case "M1S": B.put(partes[0]+" "+partes[1], new Estacion(partes[0],partes[0]+" "+partes[1],partes[0]+" "+partes[2],partes[3],partes[0]+" "+partes[4],partes[5],partes[6],partes[7],partes[8]));
				break;
				case "M2": C.put(partes[0]+" "+partes[1], new Estacion(partes[0],partes[0]+" "+partes[1],partes[0]+" "+partes[2],partes[3],partes[0]+" "+partes[4],partes[5],partes[6],partes[7],partes[8]));
				break;
				case "M3": D.put(partes[0]+" "+partes[1], new Estacion(partes[0],partes[0]+" "+partes[1],partes[0]+" "+partes[2],partes[3],partes[0]+" "+partes[4],partes[5],partes[6],partes[7],partes[8]));
				break;
				}
			}
			in.close();
			bin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> estacionesOrdenadas(){
		List<String> toReturn = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		HashMap<String,Estacion>[] hashMap = new HashMap[4];
		hashMap = getLineas();
		for(int i = 0; i < 4; i++){
			for(Map.Entry<String,Estacion> entry : hashMap[i].entrySet()){
				if(!toReturn.contains(entry.getValue().getNombre()))
					toReturn.add(entry.getValue().getNombre());
			}
		}
		Collections.sort(toReturn);

		return toReturn;
	}

	public Estacion getEstacion(String nombreEstacion){
		@SuppressWarnings("unchecked")
		HashMap<String,Estacion>[] hashMap = new HashMap[4];
		hashMap = this.getLineas();
		for(int i = 0; i < 4; i++){
			if (hashMap[i].containsKey(nombreEstacion))
				return hashMap[i].get(nombreEstacion);
		}
		return null;

	}

	public HashMap<String,Estacion>[] getLineas(){
		@SuppressWarnings("unchecked")
		HashMap<String,Estacion>[] hashMap = new HashMap[4];
		hashMap[0] = A;
		hashMap[1] = B;
		hashMap[2] = C;
		hashMap[3] = D;
		return hashMap;
	}
}
