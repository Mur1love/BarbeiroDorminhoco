package defaut;

public class Principal {
	
	public static void main(String[] args) {
		//instancia a classe barbeiro
		Barbeiro barbeiro = new Barbeiro("Lucas", 1, 5);// nome do barbbeiro, cadeiras de espera e o maximo de clientes aleatorios
		//instancia a a thread com a classe barbeiro
		Thread threadBarbeiro = new Thread(barbeiro); 
		
		threadBarbeiro.start();//executa a Thread
	}

}
