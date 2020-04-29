package hydraulic;

import java.util.ArrayList;
import java.util.List;


/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	protected List<Element> elements=new ArrayList<>();
	/**
	 * Adds a new element to the system
	 * @param elem
	 */
	public void addElement(Element elem){
		elements.add(elem);
	}
	
	/**
	 * returns the element added so far to the system.
	 * If no element is present in the system an empty array (length==0) is returned.
	 * 
	 * @return an array of the elements added to the hydraulic system
	 */
	public Element[] getElements(){
		Element[] elementsArr=new Element[elements.size()];
		for(int i=0;i<elements.size();i++)	elementsArr[i]=elements.get(i);
		return elementsArr;
	}
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		return null;
	}
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		for(Element a: elements) {
			if(a instanceof Source) {
				double flow=((Source) a).getFlow();
				observer.notifyFlow(a.getClass().getSimpleName(), a.getName(), SimulationObserver.NO_FLOW, flow);
				a=a.getOutput();
				if(a!=null)	this.nextElement(a, flow, observer,false);
			}
		}
	}
	protected void nextElement(Element a,double flow,SimulationObserver observer,boolean max) {
		double maxFlow=((ElementExt) a).getMaxFlow();
		if(max && flow>maxFlow) {
			((SimulationObserverExt) observer).notifyFlowError(a.getClass().getSimpleName(), a.getName(), flow, maxFlow);
			return;
		}
		if(a instanceof Multisplit) {
			Element[] splitsOut=((Split) a).getOutputs();
			double[] multiFlow=((Multisplit) a).getProp();
			double[] outFlow=new double[multiFlow.length];
			for(int i=0;i<multiFlow.length;i++)	outFlow[i]=multiFlow[i]*flow;
			observer.notifyFlow(a.getClass().getSimpleName(), a.getName(),flow, outFlow);
			for(int i=0;i<splitsOut.length;i++)	if(splitsOut[i]!=null) this.nextElement(splitsOut[i],outFlow[i],observer,max);
		}
		else if(a instanceof Split) {
			Element[] splitsOut=((Split) a).getOutputs();
			observer.notifyFlow(a.getClass().getSimpleName(), a.getName(), flow, flow/2,flow/2);
			if(splitsOut[0]!=null) this.nextElement(splitsOut[0],flow/2,observer,max);
			if(splitsOut[1]!=null) this.nextElement(splitsOut[1], flow/2,observer,max);
		}
		else if(a instanceof Tap) {
			if(((Tap) a).getOpen()) {
				observer.notifyFlow(a.getClass().getSimpleName(), a.getName(), flow, flow);
				a=a.getOutput();
				if(a!=null)	this.nextElement(a, flow,observer,max);
			}
			else{
				observer.notifyFlow(a.getClass().getSimpleName(), a.getName(), flow, 0.0);
				a=a.getOutput();
				if(a!=null) this.nextElement(a, 0.0,observer,max);
			}
		}
		else if(a instanceof Sink) {
			observer.notifyFlow(a.getClass().getSimpleName(), a.getName(), flow, SimulationObserver.NO_FLOW);
			return;
		}
		else if(a instanceof Source) {
			System.err.println("ERRORE, UNA SORGENTE NON PUO' ESSERE LUSCITA DI UN ELEMENTO!");
			return;
		}
	}

}
