package mountainhuts;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	private String name;
	private String[] ranges;
	private Map<String,Municipality> municipality=new HashMap<>();
	private Map<String,MountainHut> mountainHut=new HashMap<>();
	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name=name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		this.ranges=ranges;
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		String[] range;
		if(ranges==null) {
			System.err.println("Altitudini non presenti");
			return "0-INF";
		}
		for(String x:ranges) {
			range=x.split("-");
			if(altitude>=Integer.parseInt(range[0]) && altitude<=Integer.parseInt(range[1]))	return range[0]+"-"+range[1];
		}
		return "0-INF";
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		if(municipality.containsKey(name))	return municipality.get(name);
		Municipality mun=new Municipality(name,province,altitude);
		municipality.put(name, mun);
		return mun;
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return municipality.values();
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		if(mountainHut.containsKey(name))	return mountainHut.get(name);
		if(municipality==null) {
			System.err.println("ERRORE: Comune non presente nei parametri per la creazione del rifugio!");
			return null;
		}
		MountainHut hut=new MountainHut(name,category,bedsNumber,municipality);
		mountainHut.put(name, hut);
		return hut;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		if(mountainHut.containsKey(name))	return mountainHut.get(name);
		if(municipality==null) {
			System.err.println("ERRORE: Comune non presente nei parametri per la creazione del rifugio!");
			return null;
		}
		MountainHut hut=new MountainHut(name,altitude,category,bedsNumber,municipality);
		mountainHut.put(name, hut);
		return hut;
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return mountainHut.values();
	}

	/**
	 * Factory methods that creates a new region by loadomg its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 * @throws IOException 
	 */
	public static Region fromFile(String name, String file) throws IOException {
		Region region=new Region(name);
		List<String> data=Region.readData(file);
		Pattern headerPattern=Pattern.compile("([^;]+);([^;]+);([^;]+);([^;]+);([^;]+);([^;]+);([^;]+)");
		Pattern dataPattern=Pattern.compile("([^;]+);([^;]+);([0-9]+);([^;]+);([0-9]*);([^;]+);([0-9]+)");
		if(!headerPattern.matcher(data.get(0)).matches()) throw new IOException("Parametri(header del file) non sufficienti/sbagliati nel file");
		for(int i=1;i<data.size();i++) {
			Matcher match=dataPattern.matcher(data.get(i));
			if(!match.matches()) {
				System.err.println("LA RIGA: "+data.get(i)+" NON E' CORRETTA E SARA' SALTATA");
			}
			else {
				Municipality mun=region.createOrGetMunicipality(match.group(2), match.group(1), Integer.parseInt(match.group(3)));
				if(match.group(5).equals(""))	region.createOrGetMountainHut(match.group(4), match.group(6), Integer.parseInt(match.group(7)), mun);
				else region.createOrGetMountainHut(match.group(4), Integer.parseInt(match.group(5)), match.group(6), Integer.parseInt(match.group(7)), mun);
			}
		}
		return region;
		
	}

	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	@SuppressWarnings("unused")
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		return municipality.values().stream().collect(Collectors.groupingBy(e->e.getProvince(), Collectors.counting()));
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return mountainHut.values().stream().collect
				(Collectors.groupingBy(e->e.getMunicipality().getProvince(),
									Collectors.groupingBy(e->e.getMunicipality().getName(),Collectors.counting())));
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		return mountainHut.values().stream().collect(Collectors.groupingBy(e->{
			if(e.getAltitude().isPresent())  return this.getAltitudeRange(e.getAltitude().get());
			else return this.getAltitudeRange(e.getMunicipality().getAltitude());
		}, Collectors.counting()));
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return mountainHut.values().stream().collect
				(Collectors.groupingBy(e->e.getMunicipality().getProvince(),Collectors.summingInt(e->e.getBedsNumber())));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		return mountainHut.values().stream().collect(Collectors.groupingBy(e->{
			if(e.getAltitude().isPresent())  return this.getAltitudeRange(e.getAltitude().get());
			else return this.getAltitudeRange(e.getMunicipality().getAltitude());
				},Collector.of(ArrayList::new,(List<Optional<Integer>>a,MountainHut b)->a.add(Optional.ofNullable(b.getBedsNumber())),
					(List<Optional<Integer>> a,List<Optional<Integer>> b)->{
					a.addAll(b);
					return a;
				},a->{a.sort((e1,e2)->e2.get().compareTo(e1.get()));return a.get(0);})));
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		 return municipality.values().stream().collect(Collectors.groupingBy(e->{
			 long z=0;
			for(MountainHut x: mountainHut.values()) {
				if(x.getMunicipality().getName().compareTo(e.getName())==0)	z++;
			}
			return z;
		 }, Collector.of(ArrayList<String>::new, (List<String>a ,Municipality b)->a.add(b.getName()),(a,b)->{a.addAll(b);return a;},a->{ a.sort((e1,e2)->e1.compareTo(e2));return a;})));
	}

}
