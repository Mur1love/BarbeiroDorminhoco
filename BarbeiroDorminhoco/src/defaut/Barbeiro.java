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
	
	private int cadeiraDeEspera; // quantas pessoas podem esperar atendimento
	boolean cadeiraOcupada = false; // false = livre e true = ocupada
	int[] clientes; // numero aleatorio de clientes
	private String nome; // nome da Thread (Barbeiro)
	private int clientesNovos; // clientes randomicos ate o max de clientes
	int numClientes = 0; // numero de clientes inicia com 0
	
	// Metodo construtor para inicializar o barbeiro
	Barbeiro(String nome, int cadeiraDeEspera, int clientes){
		clientesNovos = clientes;
		this.nome = nome;
		this.cadeiraDeEspera = cadeiraDeEspera;
		System.out.println("O barbeiro " + nome + " chegou no salao.");
	}
	
	// Metodo que gera os clientes aleatoriamente e joga no vetor
	public void Clientes() {
		Random r = new Random();
		numClientes = r.nextInt(clientesNovos); // gera o total de clientes randomicos
		clientes = new int[numClientes]; //da o tamanho do vetor 
		
		// preenche o vetor com o numero de clientes
		for(int i = 0; numClientes < clientes.length; i++) {
			clientes[i] = i;
		}
	}
	
	// Metodo responsavel para quando nao ha clientes o barbeiro dorme
	public void barbeiroDorme() throws InterruptedException {
		System.out.println("Nao ha clientes no salao. O barbeiro " + nome + " dormiu...");
		Thread.sleep(2000); //Como nao ha clientes a thread espera 2 segundos
		System.out.println("A cadeira do barbeiro " + nome + " esta livre.");
		//Chama o metodo que cria clientes
		Clientes();
	}
	
	public void barbeiroAtende() throws InterruptedException {
		if (numClientes != 0) {
			if (numClientes > 1 && cadeiraOcupada == false) { // se tem mais de um cliente e a cadeira nao esta ocupada
				System.out.println("Entrou(aram) " + numClientes + " clientes no salao");
			} else { // se tem mais de um cliente e tem cadeiras ocupadas, tem clientes esperando
				System.out.println("Existe(m) " + numClientes + " cliente(s) esperando atendimento.");
			}
			//Se ha 1 cliente ele ja pode ser atendido imediatamente
			System.out.println("Um cliente ocupou a cadeira do Barbeiro " + nome);
			numClientes--; //Cliente atendito - decrementa
			System.out.println("Um cliente esta sendo atendido pelo barbeiro " + nome);
			cadeiraOcupada = true; // a cadeira do barbeiro esta ocupada
			//barbeiro atendendo, a thread espera terminar
			Thread.sleep(1000);
			
			
			//Clientes que ficaram e os clientes que foram embora
			if (numClientes > cadeiraDeEspera) {
				//verifica quantos clientes irao embora
				int c = numClientes - cadeiraDeEspera;
				//verifica quantos clientes esperam
				numClientes = numClientes - c;
				
				// enquanto o contador for menor que o numero de clientes, o vetor e zerado nas posicoes
				for (int i = 0; i < clientes.length; i++) {
					clientes[i] = 0;
				}
				//atualiza o total de clientes
				for (int j = 0; j < numClientes; j++) {
					clientes[j] = j + 1;
				}
				System.out.println(c + " cliente(s) foram embora");
				System.out.println(numClientes + " clientes estao esperando");
			}
			
			System.out.println("Um cliente ja foi atendido pelo Barbeiro "+ nome);
			
			// se o numero de clientes for igual a 1
		} else if (numClientes == 1) {
			//mostra que o barbeio esta livre
			System.out.println("A cadeira do barbeiro" + nome + " esta livre");
			System.out.println("A cadeira do barbeiro " + nome + " esta ocupada, nao ha clientes esperando.");
			Thread.sleep(1000); // faz a thread esperar 1 seg
			numClientes--; //decrementa os clientes
			//mostra que o barbeiro ja atendeu
			System.out.println("Um cliente ja foi atendido pelo Barbeiro "+ nome);
			
		} else {
			//avisa que o barbeiro esta livre
			System.out.println("A cadeira do barbeiro" + nome + " esta livre");
			//libera as cadeiras de espera
			cadeiraOcupada = false;
		}
	}

	@Override
	public void run() {//Executa a thread
		while(true) { //Sempre fica verificando
			if(numClientes <= 0) {
				try {
					barbeiroDorme();// barbeiro dorme
				} catch (InterruptedException ex) {
					System.out.println(ex);//mostra erro caso aconteca
				}
			}else {
				try {
					barbeiroAtende();// se ha clientes esperando o barbeiro atende
				} catch (InterruptedException ex) {
					System.out.println(ex); // mostra erro caso aconteca
				}
			}
		}
	}
}
