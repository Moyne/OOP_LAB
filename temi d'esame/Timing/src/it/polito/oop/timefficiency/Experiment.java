package it.polito.oop.timefficiency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Experiment<D> {
	private Map<String,Consumer<D>> algorithms=new HashMap<>();
	private Map<String,List<Double>> measures=new HashMap<>();
	private Supplier<D> generator;
	private int rep=30;
	private int nameWidth;
	private int plotWidth;
	public void addAlgorithm(String name, Consumer<D> algorithm) {
		algorithms.put(name, algorithm);
	}
	
	public void setDataGenerator(Supplier<D> generator) {
		this.generator=generator;
	}
	
	public Collection<String >algorithms(){
		return algorithms.entrySet().stream().map(e->e.getKey()).collect(Collectors.toCollection(ArrayList<String>::new));
	}

	public double perform(String name) {
		Consumer<D> toRun=algorithms.get(name);
		D data=generator.get();
		long start=System.nanoTime();
		toRun.accept(data);
		return (System.nanoTime()-start)/1000000.0;
	}
	
	public int run() {
		Set<String> names=algorithms.keySet();
		Iterator<String> it=names.iterator();
		while(it.hasNext()) {
			String name=(String) it.next();
			List<Double> times=new ArrayList<Double>();
			for(int i=0;i<rep;i++)	times.add(perform(name));
			measures.put(name, times);
		}
		return rep;
	}
	
	
	public void setRepeat(int n) {
		rep=n;
	}

	public int getRepeat() {
		return rep;
	}
	
	public Map<String,List<Double>> getMeasures(){
		return measures;
	}
	
	public void setPlotFormat(int nameWidth, int plotWidth) {
		this.nameWidth=nameWidth;
		this.plotWidth=plotWidth;
	}
	public double avg(List<Double> values) {
		double sum=0;
		for(int i=0;i<values.size();i++) {
			sum+=values.get(i);
		}
		return sum/values.size();
	}
	public String plot(Entry<String,List<Double>> data,double globalMax,double globalMin) {
		double localMax=((max(data.getValue())-globalMin)/(globalMax-globalMin))*plotWidth;
		double localMin=((min(data.getValue())-globalMin)/(globalMax-globalMin))*plotWidth;
		double localAvg=((avg(data.getValue())-globalMin)/(globalMax-globalMin))*plotWidth;
		int max=(int) localMax; int min=(int) localMin; int avg=(int) localAvg;
		System.out.println("LocalMax: "+localMax+"  -  LocalMin: "+localMin+"  -  LocalAvg: "+localAvg);
		StringBuffer x=new StringBuffer();
		x.append(String.format("%"+nameWidth+"."+nameWidth+"s", data.getKey()));
		x.append(":");
		for(int i=0;i<plotWidth;i++) {
			if(i==min)	x.append("<");
			else if(i==max)	x.append(">");
			else if(i==avg)	x.append("|");
			else if(i<max && i>min && i!=avg)	x.append("-");
			else if(i>max || i<min)	x.append(" ");
		}
		if(plotWidth==max)	x.append(">");
		return x.toString();
	}
	public double min(List<Double> x) {
		x.sort((e1,e2)->e1.compareTo(e2));
		return x.get(0);
	}
	public double max(List<Double> x) {
		x.sort((e1,e2)->e1.compareTo(e2));
		return x.get(x.size()-1);
	}
	public String plotInterval() {
		Collection<List<Double>> collectionOfValues=measures.values();
		double max=collectionOfValues.stream().map(e->max(e)).max((e1,e2)->e1.compareTo(e2)).get();
		double min=collectionOfValues.stream().map(e->min(e)).min((e1,e2)->e1.compareTo(e2)).get();
		System.out.println("Max: "+max+"  -  Min: "+min);
		return measures.entrySet().stream().map((e)->plot(e,max,min)).collect(Collectors.joining("\n"));
	}
}
