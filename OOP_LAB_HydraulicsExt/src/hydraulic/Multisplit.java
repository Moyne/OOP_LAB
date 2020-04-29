package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {
	int numOutput;
	private Element[] outs;
	private double[] prop;
	/**
	 * Constructor
	 * @param name
	 * @param numOutput
	 */
	public Multisplit(String name, int numOutput) {
		super(name); //you can edit also this line
		this.numOutput=numOutput;
		outs=new Element[numOutput];
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
    	return outs;
    }

    /**
     * connect one of the outputs of this split to a
     * downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
	public void connect(Element elem, int noutput){
		outs[noutput]=elem;
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		if(proportions.length!=numOutput) {
			System.err.println("I parametri passati non sono corretti");
			return;
		}
		double sum=0.0;
		for(int i=0;i<numOutput;i++) {
			sum+=proportions[i];
		}
		if(sum!=1.0) {
			System.err.println("Errore: la somma delle proporzioni non da' 1!");
			return;
		}
		else	prop=proportions;
		return;
	}
	public double[] getProp() {
		return prop;
	}
}
