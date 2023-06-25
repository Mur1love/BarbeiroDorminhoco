package defaut;
import java.util.Random;

/*	
 * Referencia: https://youtu.be/L0beC76j-kA
 * Nome do Autor: @Hingo
 * 
 * Codigo adaptado.
 * 
 * 
 */

public class Barbeiro implements Runnable {
	
	private int cadeiraDeEspera;
	boolean cadeiraOcupada = false;
	int[] clientes;
	private String nome; 
	private int clientesNovos;
	int numClientes = 0;
	
	Barbeiro(String nome, int cadeiraDeEspera, int clientes){
		clientesNovos = clientes;
		this.nome = nome;
		this.cadeiraDeEspera = cadeiraDeEspera;
		System.out.println("O barbeiro " + nome + " chegou no salao.");
	}
	
	public void Clientes() {
		Random r = new Random();
		numClientes = r.nextInt(clientesNovos);
		clientes = new int[numClientes];
		
		for(int i = 0; numClientes < clientes.length; i++) {
			clientes[i] = i;
		}
	}
	
	public void barbeiroDorme() throws InterruptedException {
		System.out.println("Nao ha clientes no salao. O barbeiro " + nome + " dormiu...");
		Thread.sleep(2000); //Como nao ha clientes a thread espera 2 segundos
		System.out.println("A cadeira do barbeiro " + nome + " esta livre.");
		//Chama o metodo que cria clientes
		Clientes();
	}
	
	public void barbeiroAtende() throws InterruptedException {
		if (numClientes != 0) {
			if (numClientes > 1 && cadeiraOcupada == false) {
				System.out.println("Entrou(aram) " + numClientes + " clientes no salao");
			} else {
				System.out.println("Existe(m) " + numClientes + " cliente(s) esperando atendimento.");
			}
			//Se ha 1 cliente ele ja pode ser atendido imediatamente
			System.out.println("Um cliente ocupou a cadeira do Barbeiro " + nome);
			numClientes--; //Cliente atendito - decrementa
			System.out.println("Um cliente esta sendo atendido pelo barbeiro " + nome);
			cadeiraOcupada = true;
			//barbeiro atendendo, a thread espera terminar
			Thread.sleep(1000);
			
			
			//Clientes que ficaram e os clientes que foram embora
			if (numClientes > cadeiraDeEspera) {
				int c = numClientes - cadeiraDeEspera;
				numClientes = numClientes - c;
				
				for (int i = 0; i < clientes.length; i++) {
					clientes[i] = 0;
				}
				for (int j = 0; j < numClientes; j++) {
					clientes[j] = j + 1;
				}
				System.out.println(c + "clientes foram embora");
				System.out.println(numClientes + " clientes estao esperando");
			}
			System.out.println("Um cliente ja foi atendido pelo Barbeiro "+ nome);
		} else if (numClientes == 1) {
			System.out.println("A cadeira do barbeiro" + nome + " esta livre");
			System.out.println("A cadeira do barbeiro " + nome + " esta ocupada, nao ha clientes esperando.");
			Thread.sleep(1000);
			numClientes--;
			System.out.println("Um cliente ja foi atendido pelo Barbeiro "+ nome);
			
		} else {
			System.out.println("A cadeira do barbeiro" + nome + " esta livre");
			cadeiraOcupada = false;
		}
	}

	@Override
	public void run() {//Executa a thread
		while(true) { //Sempre fica verificando
			if(numClientes <= 0) {
				try {
					barbeiroDorme();
				} catch (InterruptedException ex) {
					System.out.println(ex);
				}
			}else {
				try {
					barbeiroAtende();
				} catch (InterruptedException ex) {
					System.out.println(ex);
				}
			}
		}
	}
}
