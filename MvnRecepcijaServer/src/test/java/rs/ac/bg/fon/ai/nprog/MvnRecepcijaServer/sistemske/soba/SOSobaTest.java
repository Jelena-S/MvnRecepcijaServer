package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.soba;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konfiguracija.Konfiguracija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.soba.SOPretraziSobe;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.soba.SOVratiSveSobe;

public class SOSobaTest {

	Soba soba1, soba2, soba3;
	VrstaSobe vrstaSobe, vrstaSobe2;
	List<Soba> sobe,sobe111;
	static Connection connection;
	List<String> columns, values;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Konekcija.getInstance().setUrl("jdbc:mysql://localhost:3306/test_recepcija");
		connection = Konekcija.getInstance().getConnection();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Konekcija.getInstance().setUrl(Konfiguracija.getInstance().getDbUrl());
		System.out.println("Vratio na pravu bazu: " + Konfiguracija.getInstance().getDbUrl());
		connection.close();
		// configuration.getinstance.geturl//////////////////////
	}

	@Before
	public void setUp() throws Exception {
		soba1 = new Soba();
		soba1.setSobaID(Long.valueOf(11));
		soba1.setStatus(true);

		vrstaSobe = new VrstaSobe();
		vrstaSobe.setVrstaSobeID(Long.valueOf(111));
		vrstaSobe.setNazivVrsteSobe("Vrsta sobe 1");
		vrstaSobe.setOpis("Opis");
		vrstaSobe.setBrojKreveta(2);

		soba1.setVrstaSobe(vrstaSobe);

		soba2 = new Soba();
		soba2.setSobaID(Long.valueOf(22));
		soba2.setStatus(true);
		soba2.setVrstaSobe(vrstaSobe);

		vrstaSobe2 = new VrstaSobe();
		vrstaSobe2.setVrstaSobeID(Long.valueOf(222));
		vrstaSobe2.setNazivVrsteSobe("Vrsta sobe 2");
		vrstaSobe2.setOpis("Opis2");
		vrstaSobe2.setBrojKreveta(2);

		soba3 = new Soba();
		soba3.setSobaID(Long.valueOf(33));
		soba3.setStatus(true);
		soba3.setVrstaSobe(vrstaSobe2);

		sobe = new ArrayList<>();
		sobe.add(soba1);
		sobe.add(soba2);
		sobe.add(soba3);
		
		sobe111 = new ArrayList<>();
		sobe111.add(soba1);
		sobe111.add(soba2);

		Statement statement = connection.createStatement();
		String upit = "INSERT INTO vrstasobe VALUES(111, 'Vrsta sobe 1', '2', 'Opis')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO vrstasobe VALUES(222, 'Vrsta sobe 2', '2', 'Opis2')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO soba VALUES(11, true, 111)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO soba VALUES(22, true,111)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO soba VALUES(33, true,222)";
		statement.executeUpdate(upit);

		columns = new ArrayList<>();
		values = new ArrayList<>();
	}

	@After
	public void tearDown() throws Exception {
		soba1 = null;
		soba2 = null;
		soba3 = null;
		sobe = null;
		vrstaSobe = null;
		vrstaSobe2 = null;
		Statement statement = connection.createStatement();// proveri da li moze sa istim statementom
		String upit = "DELETE FROM soba";
		statement.executeUpdate(upit);
		upit = "DELETE FROM vrstasobe";
		statement.executeUpdate(upit);
		connection.commit();
	}

	@Test
	public void testOperacija() throws Exception {
		SOVratiSveSobe so = new SOVratiSveSobe();
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM soba";
		ResultSet rs = statement.executeQuery(upit);

		List<Soba> rooms = new ArrayList<>();

		try {
			while (rs.next()) {
				Long id = rs.getLong("sobaID");
				boolean s = rs.getBoolean("status");
				Long roomTypeID = rs.getLong("vrstaSobeID");

				Soba r = new Soba(id, s, new VrstaSobe(roomTypeID));
				rooms.add(r);

			}
		} catch (Exception e) {
			System.out.println("Greska u VrstaSobe.Class ResultSet");

		}

		assertEquals(rooms.size(), sobe.size());
		assertEquals(rooms.get(0), sobe.get(0));

		// contains
	}

	///// pretrazi

	@Test
	public void testOperacijaVratiPoJednomKriterijumuBroj() throws Exception {
		columns.add("sobaID");
		values.add("11");
		SOPretraziSobe so = new SOPretraziSobe(columns, values);
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM soba WHERE sobaID=11";
		ResultSet rs = statement.executeQuery(upit);

		List<Soba> rooms = new ArrayList<>();

		try {
			while (rs.next()) {
				Long id = rs.getLong("sobaID");
				boolean s = rs.getBoolean("status");
				Long roomTypeID = rs.getLong("vrstaSobeID");

				Soba r = new Soba(id, s, new VrstaSobe(roomTypeID));
				rooms.add(r);

			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(1, rooms.size());
		assertEquals(soba1, rooms.get(0));
		
	}
	//po vrsti
	
	@Test
	public void testOperacijaVratiPoJednomKriterijumuVrsta() throws Exception {
		columns.add("vrstaSobeID");
		values.add("111");
		SOPretraziSobe so = new SOPretraziSobe(columns, values);
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM soba WHERE vrstaSobeID=111";
		ResultSet rs = statement.executeQuery(upit);

		List<Soba> rooms = new ArrayList<>();

		try {
			while (rs.next()) {
				Long id = rs.getLong("sobaID");
				boolean s = rs.getBoolean("status");
				Long roomTypeID = rs.getLong("vrstaSobeID");

				Soba r = new Soba(id, s, new VrstaSobe(roomTypeID));
				rooms.add(r);

			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(sobe111.size(), rooms.size());
		assertEquals(sobe111.get(0), rooms.get(0));
		assertEquals(sobe111.get(1), rooms.get(1));
		
	}
	
	
	@Test
	public void testOperacijaVratiPoDvaKriterijuma() throws Exception {
		columns.add("sobaID");
		values.add("11");
		
		columns.add("vrstaSobeID");
		values.add("111");
		SOPretraziSobe so = new SOPretraziSobe(columns, values);
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM soba WHERE sobaID=11 AND vrstaSobeID=111";
		ResultSet rs = statement.executeQuery(upit);

		List<Soba> rooms = new ArrayList<>();

		try {
			while (rs.next()) {
				Long id = rs.getLong("sobaID");
				boolean s = rs.getBoolean("status");
				Long roomTypeID = rs.getLong("vrstaSobeID");

				Soba r = new Soba(id, s, new VrstaSobe(roomTypeID));
				rooms.add(r);

			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(1, rooms.size());
		assertEquals(soba1, rooms.get(0));
		
	}
	
	//nema takvih
	
	@Test
	public void testOperacijaVratiPoJednomKriterijumuBrojNePostoji() throws Exception {
		columns.add("sobaID");
		values.add("44");
		SOPretraziSobe so = new SOPretraziSobe(columns, values);
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM soba WHERE sobaID=44";
		ResultSet rs = statement.executeQuery(upit);

		List<Soba> rooms = new ArrayList<>();

		try {
			while (rs.next()) {
				Long id = rs.getLong("sobaID");
				boolean s = rs.getBoolean("status");
				Long roomTypeID = rs.getLong("vrstaSobeID");

				Soba r = new Soba(id, s, new VrstaSobe(roomTypeID));
				rooms.add(r);

			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(0, rooms.size());
		
		
	}

}
