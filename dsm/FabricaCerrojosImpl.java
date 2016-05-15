package dsm;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class FabricaCerrojosImpl extends UnicastRemoteObject implements FabricaCerrojos {


	private ArrayList<Cerrojo> fabrica;

	public FabricaCerrojosImpl() throws RemoteException {
		fabrica = new ArrayList<Cerrojo>();
	}
	public synchronized	Cerrojo iniciar(String s) throws RemoteException {
		Iterator<Cerrojo> i = fabrica.iterator();
		CerrojoImpl aux;
		while (i.hasNext()) {
			aux = (CerrojoImpl)i.next();
			if (aux.getIdCerrojo().equals(s)) {
				return aux;
			}
		}

		CerrojoImpl newLock = new CerrojoImpl(s);
		fabrica.add(newLock);
		return newLock;
	}

}

