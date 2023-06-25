package defaut;

public class Principal {
	
	public static void main(String[] args) {
		Barbeiro barbeiro = new Barbeiro("Igor", 2, 5);
		
		Thread threadBarbeiro = new Thread(barbeiro);
		
		threadBarbeiro.start();
	}

}
