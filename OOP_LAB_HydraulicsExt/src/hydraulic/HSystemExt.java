package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystemExt extends HSystem{
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		String layout="";
		for(Element a: elements) {
			if(a instanceof Source) {
				layout+="["+a.getName()+"]"+a.getClass().getSimpleName()+" ";
				a=a.getOutput();
				if(a!=null)	{
					layout+="-> ";
					layout+=this.nextLayout(a,layout.length());
				}
				else layout+="*";
			}
		}
		return layout;
	}
	private String nextLayout(Element a,int lenght) {
		String x="";
		if(a instanceof Split) {
			x+="["+a.getName()+"]"+a.getClass().getSimpleName()+" ";
			lenght+=x.length();
			String y="\n";
			for(int i=0;i<2;i++) {
				for(int j=0;j<lenght;j++)	y+=" ";
				if(i==0)	y+="|\n";
			}
			Element[] outs=((Split) a).getOutputs();
			for(int i=0;i<outs.length;i++) {
				if(outs[i]!=null)	
				{
					x+="+-> ";
					lenght+=x.length();
					x+=this.nextLayout(outs[i],lenght);
				}
				else x+="*";
				if(i<outs.length-1)	x+=y;
			}
		}
		else {
			x+="["+a.getName()+"]"+a.getClass().getSimpleName()+" ";
			a=a.getOutput();
			if(a!=null) {
				x+="-> ";
				lenght+=x.length();
				x+=this.nextLayout(a,lenght);
			}
			else	x+="*";
		}
		return x;
	}
	
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public void deleteElement(String name) {
		for(Element a:elements) {
			if(a.getName().equals(name)) {
				for(Element b: elements)	
					if(b.getOutput().equals(a))	{
						if(a instanceof Split)	b.connect(null);
						else	b.connect(a.getOutput());
						elements.remove(a);
						return;
					}
				elements.remove(a);
				return;
			}
		}
	}

	/**
	 * starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		if(enableMaxFlowCheck) {
			for(Element a: elements) {
				if(a instanceof Source) {
					double flow=((Source) a).getFlow();
					observer.notifyFlow(a.getClass().getSimpleName(), a.getName(), SimulationObserver.NO_FLOW, flow);
					a=a.getOutput();
					if(a!=null)	super.nextElement(a, flow, observer,true);
				}
			}
		}
		else super.simulate(observer);
	}
	
}
