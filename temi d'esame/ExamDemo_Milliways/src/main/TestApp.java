package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import it.polito.oop.milliways.Hall;
import it.polito.oop.milliways.MilliwaysException;
import it.polito.oop.milliways.Party;
import it.polito.oop.milliways.Race;
import it.polito.oop.milliways.Restaurant;

public class TestApp {

	@Test
	public void test() throws MilliwaysException {
	    Restaurant milliways = new Restaurant();

		// Create a fews standard races
		Race race1 = milliways.defineRace("Amoeboid Zingatularians");
		Race race2 = milliways.defineRace("Betelgeusians");
		Race race3 = milliways.defineRace("Blagulon Kappans");
		Race race4 = milliways.defineRace("Golgafrinchans");
		Race race5 = milliways.defineRace("Jatravartids");
		Race race6 = milliways.defineRace("Krikkiters");
		Race race7 = milliways.defineRace("Silastic Armourfiends of Striterax");
		assertNotNull("No race defined", race1);
		
		// Add some random requirements
		for (Race r : new Race[] { race1, race2, race3, race4, race5 })
			r.addRequirement("Class M atmosphere (nitrogen-oxygen)");
		for (Race r : new Race[] { race6, race7 })
			r.addRequirement("Class X atmosphere (methane)");
		for (Race r : new Race[] { race1, race3, race5 })
			r.addRequirement("Low gravity (less than 2g)");

		race3.addRequirement("Soothing music");
		race5.addRequirement("Vegeterian meals");
		race7.addRequirement("Live food");

		// Create party
		Party party1 = milliways.createParty();
		party1.addCompanions(race1, 1);
		party1.addCompanions(race2, 2);
		party1.addCompanions(race1, 2);
		assertNotNull("No party defined", party1);

		assertEquals(5,party1.getNum());
		assertEquals("[Class M atmosphere (nitrogen-oxygen), Low gravity (less than 2g)]",
					  party1.getRequirements().toString());

		// Prepare halls
		Hall main_hall = milliways.defineHall(42);
		assertNotNull("No hall defined", main_hall);

		for (String facility : new String[] { "Class M atmosphere (nitrogen-oxygen)", 
											  "Soothing music",
											  "Low gravity (less than 2g)" })
			main_hall.addFacility(facility);
		assertEquals(42,main_hall.getId());
		assertEquals("[Class M atmosphere (nitrogen-oxygen), Low gravity (less than 2g), Soothing music]",
					 main_hall.getFacilities().toString());

		Hall h1 = milliways.seat(party1);
		assertEquals("{Betelgeusians=2, Amoeboid Zingatularians=3}",
					 party1.getDescription().toString());
		assertEquals(42,h1.getId());

		
		assertEquals("{Amoeboid Zingatularians=3, Betelgeusians=2}",
				milliways.statComposition().toString());

		Party party2 = milliways.createParty();
		party2.addCompanions(race1, 1);
		party2.addCompanions(race2, 100);
		party2.addCompanions(race3, 2);
		
		Hall h2 = milliways.seat(party2);
		assertEquals("{Blagulon Kappans=2, Betelgeusians=100, Amoeboid Zingatularians=1}",
				     party2.getDescription().toString());
        assertEquals(42,h2.getId());
		
		assertEquals("{Amoeboid Zingatularians=4, Betelgeusians=102, Blagulon Kappans=2}",
				milliways.statComposition().toString());
		
		Hall hall2 = milliways.defineHall(2);
		main_hall.addFacility("Live food");
		hall2.addFacility("Live food");
		hall2.addFacility("Class X atmosphere (methane)");
		
		assertEquals("[Live food, Class M atmosphere (nitrogen-oxygen), Class X atmosphere (methane), Low gravity (less than 2g), Soothing music]",
					 milliways.statFacility().toString());
		
	      Hall hall3 = milliways.defineHall(3);
	      hall3.addFacility("Heavy gravity (>2g)");
	      hall3.addFacility("Class M atmosphere (oxygen)");

			assertEquals("{2=[2, 3], 4=[42]}",
					milliways.statHalls().toString());
	}

}
