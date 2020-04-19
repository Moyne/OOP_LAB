package rubrica;

public class Rubrica {
	private String name;
	static final int maxVoci=100;
	private int nVoci;
	private Voce[] voci;
	public Rubrica(String name) {
		this.name=name;
		voci=new Voce[maxVoci];
	}
	public String getName() {
		return this.name;
	}
	public void aggiungi(String name, String surname, String number) {
		if(nVoci>=maxVoci) {
			System.out.println("La rubrica e' piena"); return;
		}
		voci[nVoci]= new Voce(name,surname,number);
		nVoci++;
	}
	public String voce(int n) {
		return voci[n].getString();
	}
	public String primo() {
		if(nVoci==0)	return "La rubrica e' vuota";
		return this.voce(0);
	}
	public String elenco() {
		String elenco="(";
		for(int i=0;i<nVoci;i++) {
			if(i<nVoci-1)	elenco=elenco+this.voce(i)+",";
			else	elenco=elenco+this.voce(i);
		}
		return elenco+")";
	}
	public String ricerca(String search) {
		for(int i=0;i<nVoci;i++) {
			if(voci[i].search(search))	return voci[i].getString();
		}
		return "Voce non trovata";
	}
}
