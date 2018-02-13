package ej1;

public class Ejercicio1 implements Runnable {

	private static Thread hilo1, hilo2, hilo3;
	
	//Método main
	public static void main(String[] args) {
		
		//Instanciando este objeto ya crea los 3 hilos
		Ejercicio1 obj = new Ejercicio1();
		
		//Asignamos nombres a los hilos
		hilo1.setName("hilo1");
		hilo2.setName("hilo2");
		hilo3.setName("hilo3");
		
		//Asignamos prioridades 
		hilo1.setPriority(10);
		hilo2.setPriority(1);
		hilo3.setPriority(2);
		
		//"Lanzamos" los hilos
		hilo1.start();
		hilo2.start();
		hilo3.start();
		
		//Queremos esperar que finalicen los 3 hilos antes de continuar
		try {
			hilo1.join();
			hilo2.join();
			hilo3.join();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		//Mostramos cuando finaliza el hilo principal
		System.out.println("El hilo principal ha terminado");

	}

	//Constructor
	public Ejercicio1() {
		
		//Instanciamos los hilos al crear un objeto de esta clase
		hilo1 = new Thread(this);
		hilo2 = new Thread(this);
		hilo3 = new Thread(this);
	}
	
	public void run() {
		
		try {
			//Bucle con 5 iteraciones
			for(int i=0; i<5; i++) {
				
				//Según el hilo, se mostrará un mensaje u otro
				if(Thread.currentThread().getName().equals("hilo1")) {
					System.out.println("Soy Batman, el mejor!!");
				}
				else if(Thread.currentThread().getName().equals("hilo2")) {
					System.out.println("Soy Spiderman, el yogurín del grupo");
				}
				else if(Thread.currentThread().getName().equals("hilo3")) {
					System.out.println("Para superhéroe yo, Ironman");
				}
				
				Thread.yield();
			}
		}
		catch(Exception e) {
			System.out.println("Hilo " + Thread.currentThread() + "interrumpido");
		}
		
		//Mostramos cuando finaliza cada hilo
		System.out.println("Finaliza el hilo: "+Thread.currentThread().getName());
		
	}

	
	
	
}
