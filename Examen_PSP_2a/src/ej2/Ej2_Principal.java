package ej2;

public class Ej2_Principal implements Runnable {
	
	private static Thread hilo1, hilo2;
	private static Contador synch;

	//Constructor
	public Ej2_Principal(Contador synch) {
		
		//Instanciamos objetos necesarios en el constructor
		this.synch = synch;
		hilo1 = new Thread(this);
		hilo2 = new Thread(this);
		
	}
	
	/*
	 * Main
	 */
	public static void main(String[] args) {
		
		//Inicializamos objetos
		synch = new Contador();
		Ej2_Principal obj = new Ej2_Principal(synch);
		
		hilo1.setName("Hilo A");
		hilo2.setName("Hilo B");
		
		hilo1.start();
		hilo2.start();
		
		
	}
	
	/*
	 * Método run
	 */
	public synchronized void run() {
		try {
			if(Thread.currentThread().getName().equals("Hilo A")) {
				for(int i = 0; i < 10; i++) {
					synch.incrementarValor();
					System.out.println("Hilo A incrementa en 1: "+synch.mostrarValor());
				}
			}
			else if(Thread.currentThread().getName().equals("Hilo B")) {
				for(int i = 0; i < 10; i++) {
					synch.decrementarValor();
					System.out.println("Hilo B decrementa en 1: "+synch.mostrarValor());
				}
			}
			System.out.println("Valor del contador: " + synch.mostrarValor());
		}
		catch (Exception e) {
			System.out.println("Hilo " + Thread.currentThread().getName() + " interrumpido");
		}
	}

}
