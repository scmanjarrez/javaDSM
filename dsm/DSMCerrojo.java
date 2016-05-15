package dsm;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DSMCerrojo {

	private boolean exclusive;
	private ArrayList<ObjetoCompartido> obj;
	private Cerrojo lock;
	private FabricaCerrojos fab_cerr;
	private Almacen alm;
	
	public DSMCerrojo (String nom) throws RemoteException, MalformedURLException, NotBoundException {
		String servidor = System.getenv("SERVIDOR");
		String puerto = System.getenv("PUERTO");
		fab_cerr = (FabricaCerrojos) Naming.lookup("//" +servidor+ ":" + puerto + "/DSM_cerrojos");
		alm = (Almacen) Naming.lookup("//" + servidor + ":"+ puerto + "/DSM_almacen");
		lock = fab_cerr.iniciar(nom);
		obj = new ArrayList<ObjetoCompartido>();
	}

	public void asociar(ObjetoCompartido o) {
		obj.add(o);
	}
	public void desasociar(ObjetoCompartido o) {
		Iterator<ObjetoCompartido> i = obj.iterator();
		ObjetoCompartido aux;
		while(i.hasNext()){
			aux = i.next();
			if(aux.getCabecera().getNombre().equals(o.getCabecera().getNombre())){
				i.remove();
				return;
			}
		}
	}
	public boolean adquirir(boolean exc) throws RemoteException {

		lock.adquirir(exc);
		exclusive=exc;

		return actualizarObjetos();
	}

	public boolean liberar() throws RemoteException {
		if(exclusive){
			incrementarVersion();
			alm.escribirObjetos(obj);
		}
		return lock.liberar();
	}
	private void incrementarVersion(){
		Iterator<ObjetoCompartido> i = obj.iterator();
		ObjetoCompartido aux;
		while(i.hasNext()){
			aux = i.next();
			aux.getCabecera().setVersion(aux.getCabecera().getVersion()+1);
		}
	}

	private boolean actualizarObjetos(){
		List<CabeceraObjetoCompartido> lcab = new ArrayList<CabeceraObjetoCompartido>();
		Iterator<ObjetoCompartido> i = obj.iterator();
		while(i.hasNext()){
			lcab.add(i.next().getCabecera());
		}
		List<ObjetoCompartido> ret;
		try{
			ret = alm.leerObjetos(lcab);
		}catch (RemoteException e){
			return false;
		}
		if(ret != null){
			Iterator<ObjetoCompartido> j = ret.iterator();
			ObjetoCompartido aux;
			ObjetoCompartido aux1;
			while(j.hasNext()){
				aux = j.next();
				i = obj.iterator();
				while(i.hasNext()){
					aux1 = i.next();
					if(aux.getCabecera().getNombre().equals(aux1.getCabecera().getNombre())) {
						aux1.setObjeto(aux.getObjeto());
						aux1.setVersion(aux.getCabecera().getVersion());
					}
				}
			}
		}
		return true;
	}


}
