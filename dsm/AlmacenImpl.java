package dsm;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AlmacenImpl extends UnicastRemoteObject implements Almacen {

	ArrayList<ObjetoCompartido> almacen;
	
	public AlmacenImpl() throws RemoteException {
		almacen = new ArrayList<ObjetoCompartido>();
	}

	public synchronized	List<ObjetoCompartido> leerObjetos(List<CabeceraObjetoCompartido> lcab)
			throws RemoteException {
		
		List<ObjetoCompartido> lista = new ArrayList<ObjetoCompartido>();
		Iterator<CabeceraObjetoCompartido> i = lcab.iterator();
		
		ObjetoCompartido aux;
		while(i.hasNext()){
			aux=obtenerUltimaVersion(i.next());
			if(aux != null){
				lista.add(aux);
			}
		}
		if(lista.size() == 0) {
			return null;
		}
		
		return lista;
	}
	public synchronized void escribirObjetos(List<ObjetoCompartido> loc)
			throws RemoteException  {
		Iterator<ObjetoCompartido> i = loc.iterator();
		while(i.hasNext()) {
			almacen.add(i.next());
		}
	}
	
	private ObjetoCompartido obtenerUltimaVersion(CabeceraObjetoCompartido cab){
		Iterator<ObjetoCompartido> i = almacen.iterator();
		ObjetoCompartido aux;
		ArrayList<Integer> index = new ArrayList<Integer>();
		ArrayList<Integer> version = new ArrayList<Integer>();

		for (int j = 0; i.hasNext(); j++) {
			aux = i.next();
			if(aux.getCabecera().getNombre().equals(cab.getNombre())){
				if(cab.getVersion()<aux.getCabecera().getVersion()) {
					index.add(j);
					version.add(aux.getCabecera().getVersion());
				}
			}
		}
		if(version.size()==0) {
			return null;
		}
		
		int uIndex = index.get(0);
		int uVersion = version.get(0);
		for (int j = 0; j < version.size(); j++) {
			if(uVersion<version.get(j)) {
				uIndex = index.get(j);
				uVersion = version.get(j);
			} 
		}
		
		return almacen.get(uIndex);
	}
	
}

