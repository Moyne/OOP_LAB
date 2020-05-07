package it.polito.oop.timefficiency;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Test;

public class TestExperiment {

	@Test
	public void test() {
		
		Consumer<Integer> strConcat = n -> {
			String res="";
			for(int i=0; i<n; ++i) {
				res += i;
			}
		};

		Consumer<Integer> strBuffer = n -> {
			StringBuffer b = new StringBuffer();
			for(int i=0; i<n; ++i) {
				b.append(i);
			}
			String res = b.toString();
		};
		
		Experiment<Integer> exp = new Experiment<>();
		
		exp.addAlgorithm("String.concat", strConcat);
		exp.addAlgorithm("StringBuffer", strBuffer);
		
		exp.setDataGenerator( () -> 10000 );
		
		double ts = exp.perform("String.concat");
		double tsb = exp.perform("StringBuffer");
		
		System.out.println(ts);
		System.out.println(tsb);

		assertTrue("Il tempo per String dovrebbe esser piu' elevato",
				   ts > tsb);
		
		int n = exp.run();
		
		assertEquals(30, n);
		
		Map<String,List<Double>> misure = exp.getMeasures();
		exp.setPlotFormat(8,100);
		System.out.println(misure);
		System.out.println(exp.plotInterval());
		assertNotNull(misure);
		assertEquals(2,misure.keySet().size());
		assertTrue(misure.containsKey("StringBuffer"));
		List<Double> lsb = misure.get("StringBuffer");
		assertEquals(30,lsb.size());
		
		
	}

}
