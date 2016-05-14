package dsm;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class CerrojoImpl extends UnicastRemoteObject implements Cerrojo {

	private String idCerrojo;
	private boolean locked;
	private int escritores;
	private int lectores;

	CerrojoImpl() throws RemoteException {
	}

	public synchronized void adquirir(boolean exc) throws RemoteException {
		if (exc) {
			while (escritores != 0 || lectores >0) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			escritores++;
			locked=true;
		} else {
			while(escritores != 0){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			lectores++;
			locked=true;
		}
		//System.out.println("estado actual de escrit=" + escritores + " y lect=" + lectores);
	}

	public synchronized boolean liberar() throws RemoteException {
		if (!locked) {
			return false;
		} else {
			if (escritores == 1) {// lectores==0?
				escritores = 0;
				locked = false;
				notifyAll();
				return true;
			}
			if (lectores > 0) {//&&escritores == 0?
				lectores--;
				if (lectores == 0) {
					locked = false;
					notifyAll();
					return true;
				}
			} 
			return true;
		}

	}

	public String getIdCerrojo() {
		return idCerrojo;
	}

	public void setIdCerrojo(String idCerrojo) {
		this.idCerrojo = idCerrojo;
	}

}
