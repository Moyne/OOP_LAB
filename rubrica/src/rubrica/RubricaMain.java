package rubrica;

public class RubricaMain {
	public static void main(String[] args) {
		Rubrica rubrica=new Rubrica("Name's Rubrica");
		System.out.println("Il nome della tua rubrica e': "+rubrica.getName());
		rubrica.aggiungi("Name1", "Surname1", "11111");
		rubrica.aggiungi("Name2", "Surname2", "22222");
		rubrica.aggiungi("Name3", "Surname3", "33333");
		System.out.println(rubrica.elenco());
		System.out.println(rubrica.ricerca("Moyne"));
	}

}
