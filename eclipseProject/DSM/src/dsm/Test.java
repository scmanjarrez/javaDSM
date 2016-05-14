package dsm;

import java.util.ArrayList;
import java.util.Iterator;

public class Test {

//	public static void main(String[] args) throws RemoteException {
//		// TODO Auto-generated method stub
//		FabricaCerrojosImpl fab = new FabricaCerrojosImpl();
//		
//		Cerrojo c;
//		System.out.println(fab.iniciar("elmundo"));
//		System.out.println(fab.iniciar("elmundo"));
//		System.out.println(fab.iniciar("elmundo1"));
//		System.out.println(fab.iniciar("elmundo2"));
//
//		
//		CerrojoImpl caux;
//
//		ArrayList<Cerrojo> lista = fab.getFabrica();
//		for (int i = 0; i < lista.size(); i++) {
//			caux = (CerrojoImpl) lista.get(i);
//			System.out.println("i="+i+" id="+caux.getIdCerrojo());
//		}
//
//	}
	
	public static void main(String[] args) {
		ArrayList<Prueba> list = new ArrayList<Prueba>();
		for (int i = 0; i <= 30; i++) {
			list.add(new Prueba(i,i));
		}
		System.out.println(list);
		
//		ListIterator<Prueba> it = list.listIterator();
//		int reverse = 30;
//		
//		Prueba aux;
//		while(it.hasNext())
//		{
//			aux=it.next();
//			aux.vel=reverse--;
//		}
		Iterator<Prueba> it = list.iterator();
		int reverse = 30;
		
		Prueba aux;
		while(it.hasNext())
		{
			aux=it.next();
			aux.vel=reverse--;
		}
		
		System.out.println(list);
		
	}
	private static class Prueba{
		int val;
		int vel;
		public Prueba(int val, int vel) {
			this.val = val;
			this.vel = vel;
		}
		@Override
		public String toString() {
			return "Prueba [val=" + val + ", vel=" + vel + "]";
		}
		
	}

}
