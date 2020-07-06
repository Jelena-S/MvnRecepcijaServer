package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.gost;

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

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.gost.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konfiguracija.Konfiguracija;

public class SOGostTest {

	Gost g, g1, g2, g3;
	Recepcioner recepcioner;
	List<Gost> gosti, gostiPera;
	List<String> columns, values;
	static Connection connection;
	
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
	}

	@Before
	public void setUp() throws Exception {
		g = new Gost();
		g.setImeGosta("Mika");
		g.setPrezimeGosta("Mikic");
		g.setBrojTelefona("654646");
		g.setBrojLicneKarte("68484");
		g.setEmail("468484");
		g.setGostID(Long.valueOf(100));
		recepcioner = new Recepcioner(Long.valueOf(11), "Jelena", "Sreckovic", "admin", "admin");
		g.setRecepcioner(recepcioner);

		Statement statement = connection.createStatement();
		String upit = "INSERT INTO recepcioner VALUES(11, 'Jelena', 'Sreckovic', 'admin', 'admin')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO gost VALUES(100, 'Pera', 'Peric', '6876', '78678','email',11)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO gost VALUES(101, 'Zika', 'Zikic', '55555555', '4444444','emailpera',11)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO gost VALUES(102, 'Pera', 'Peric', '999999', '888888','emailperic',11)";
		statement.executeUpdate(upit);

		g1 = new Gost();
		g1.setImeGosta("Pera");
		g1.setPrezimeGosta("Peric");
		g1.setBrojTelefona("78678"); 
		g1.setBrojLicneKarte("6876");
		g1.setEmail("email");
		g1.setRecepcioner(recepcioner);
		g1.setGostID(Long.valueOf(100));
		
		g2 = new Gost();
		g2.setImeGosta("Zika");
		g2.setPrezimeGosta("Zikic");
		g2.setBrojTelefona("4444444");
		g2.setBrojLicneKarte("55555555");
		g2.setEmail("emailpera");
		g2.setRecepcioner(recepcioner);
		g2.setGostID(Long.valueOf(101));
		
		g3 = new Gost();
		g3.setImeGosta("Pera");
		g3.setPrezimeGosta("Peric");
		g3.setBrojTelefona("888888");
		g3.setBrojLicneKarte("999999");
		g3.setEmail("emailperic");
		g3.setRecepcioner(recepcioner);
		g3.setGostID(Long.valueOf(102));

		gosti = new ArrayList<>();
		gosti.add(g1);
		gosti.add(g2);
		gosti.add(g3);

		connection.commit();
		
		columns = new ArrayList<>();
		values = new ArrayList<>();
		
		gostiPera = new ArrayList<>();
		gostiPera.add(g1);
		gostiPera.add(g3);
	}

	@After
	public void tearDown() throws Exception {
		Statement statement = connection.createStatement();
		String upit = "DELETE FROM gost";
		statement.executeUpdate(upit);
		upit = "DELETE FROM recepcioner";
		statement.executeUpdate(upit);
		connection.commit();
		recepcioner = null;
		g = null;
		g1 = null;
		g2 = null;
		g3 = null;
		gosti=null;
		gostiPera = null;
		columns = null;
		values = null;
	}

	//Sistemska operaciaj kreiraj gosta
	
	@Test
	public void testOperacijaKreiraj() throws Exception {
		SOKreirajGosta so = new SOKreirajGosta(g);
		so.izvrsenje();
		System.out.println("" + so.getGost().getGostID());

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM gost";
		ResultSet rs = statement.executeQuery(upit);

		List<OpstiDomenskiObjekat> guests = new ArrayList<>();
		try {
			while (rs.next()) {
				Long guestID = rs.getLong("gostID");
				String name = rs.getString("imeGosta");
				String lastname = rs.getString("prezimeGosta");
				String cardID = rs.getString("brojLicneKarte");
				String phoneNumber = rs.getString("brojTelefona");
				String mail = rs.getString("email");
				Long recepcionistID = rs.getLong("recepcionerID");

				Gost g = new Gost(guestID, name, lastname, cardID, phoneNumber, mail, new Recepcioner(recepcionistID));
				guests.add(g);
				System.out.println(g);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		Gost nadjeni = (Gost) guests.get(3);

		assertEquals(g, nadjeni);
	}

	//Sistemska operacija izmeni gosta

	@Test
	public void testOperacijaIzmeni() throws Exception {
		SOIzmeniGosta so = new SOIzmeniGosta(g);
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM gost";
		ResultSet rs = statement.executeQuery(upit);

		List<OpstiDomenskiObjekat> guests = new ArrayList<>();
		try {
			while (rs.next()) {
				Long guestID = rs.getLong("gostID");
				String name = rs.getString("imeGosta");
				String lastname = rs.getString("prezimeGosta");
				String cardID = rs.getString("brojLicneKarte");
				String phoneNumber = rs.getString("brojTelefona");
				String mail = rs.getString("email");
				Long recepcionistID = rs.getLong("recepcionerID");

				Gost g = new Gost(guestID, name, lastname, cardID, phoneNumber, mail, new Recepcioner(recepcionistID));
				guests.add(g);
				System.out.println(g);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		Gost nadjeni = (Gost) guests.get(0);

		assertEquals(g, nadjeni);
	}

	//Sistemska operacija obrisi gosta

	@Test
	public void testOperacijaObrisi() throws Exception {
		SOObrisiGosta so = new SOObrisiGosta(g);
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM gost";
		ResultSet rs = statement.executeQuery(upit);

		List<OpstiDomenskiObjekat> guests = new ArrayList<>();
		try {
			while (rs.next()) {
				Long guestID = rs.getLong("gostID");
				String name = rs.getString("imeGosta");
				String lastname = rs.getString("prezimeGosta");
				String cardID = rs.getString("brojLicneKarte");
				String phoneNumber = rs.getString("brojTelefona");
				String mail = rs.getString("email");
				Long recepcionistID = rs.getLong("recepcionerID");

				Gost g = new Gost(guestID, name, lastname, cardID, phoneNumber, mail, new Recepcioner(recepcionistID));
				guests.add(g);
				System.out.println(g);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertFalse(guests.contains(g));
	}

	//Sistemska operacija vrati sve goste

	@Test
	public void testOperacijaVratiSve() throws Exception {
		SOVratiSveGoste so = new SOVratiSveGoste();
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM gost";
		ResultSet rs = statement.executeQuery(upit);

		List<Gost> guests = new ArrayList<>();
		try {
			while (rs.next()) {
				Long guestID = rs.getLong("gostID");
				String name = rs.getString("imeGosta");
				String lastname = rs.getString("prezimeGosta");
				String cardID = rs.getString("brojLicneKarte");
				String phoneNumber = rs.getString("brojTelefona");
				String mail = rs.getString("email");
				Long recepcionistID = rs.getLong("recepcionerID");

				Gost g = new Gost(guestID, name, lastname, cardID, phoneNumber, mail, new Recepcioner(recepcionistID));
				guests.add(g);
				System.out.println(g);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(guests.size(), gosti.size());
		assertEquals(guests.get(0), gosti.get(0));
	}

	//Sistemska operacija vrati goste po kriterijumu

	@Test
	public void testOperacijaVratiPoJednomKriterijumu() throws Exception {
		columns.add("imeGosta");
		values.add("'Pera'");
		SOPretraziGoste so = new SOPretraziGoste(columns, values);
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM gost WHERE imeGosta='Pera'";
		ResultSet rs = statement.executeQuery(upit);

		List<Gost> guests = new ArrayList<>();
		try {
			while (rs.next()) {
				Long guestID = rs.getLong("gostID");
				String name = rs.getString("imeGosta");
				String lastname = rs.getString("prezimeGosta");
				String cardID = rs.getString("brojLicneKarte");
				String phoneNumber = rs.getString("brojTelefona");
				String mail = rs.getString("email");
				Long recepcionistID = rs.getLong("recepcionerID");

				Gost g = new Gost(guestID, name, lastname, cardID, phoneNumber, mail, new Recepcioner(recepcionistID));
				guests.add(g);
				System.out.println(g);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(2, guests.size());
		assertEquals(gostiPera.get(0), guests.get(0));
	}
	
	@Test
	public void testOperacijaVratiPoJednomKriterijumuNemaTakvih() throws Exception {
		columns.add("imeGosta");
		values.add("'Jelena'");
		SOPretraziGoste so = new SOPretraziGoste(columns, values);
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM gost WHERE imeGosta='Jelena'";
		ResultSet rs = statement.executeQuery(upit);

		List<Gost> guests = new ArrayList<>();
		try {
			while (rs.next()) {
				Long guestID = rs.getLong("gostID");
				String name = rs.getString("imeGosta");
				String lastname = rs.getString("prezimeGosta");
				String cardID = rs.getString("brojLicneKarte");
				String phoneNumber = rs.getString("brojTelefona");
				String mail = rs.getString("email");
				Long recepcionistID = rs.getLong("recepcionerID");

				Gost g = new Gost(guestID, name, lastname, cardID, phoneNumber, mail, new Recepcioner(recepcionistID));
				guests.add(g);
				System.out.println(g);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(0, guests.size());
	}
	
	@Test
	public void testOperacijaVratiPoDvaKriterijuma() throws Exception {
		columns.add("imeGosta");
		values.add("'Pera'");
		
		columns.add("prezimeGosta");
		values.add("'Peric'");
		
		SOPretraziGoste so = new SOPretraziGoste(columns, values);
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM gost WHERE imeGosta='Pera' AND prezimeGosta='Peric'";
		ResultSet rs = statement.executeQuery(upit);

		List<Gost> guests = new ArrayList<>();
		try {
			while (rs.next()) {
				Long guestID = rs.getLong("gostID");
				String name = rs.getString("imeGosta");
				String lastname = rs.getString("prezimeGosta");
				String cardID = rs.getString("brojLicneKarte");
				String phoneNumber = rs.getString("brojTelefona");
				String mail = rs.getString("email");
				Long recepcionistID = rs.getLong("recepcionerID");

				Gost g = new Gost(guestID, name, lastname, cardID, phoneNumber, mail, new Recepcioner(recepcionistID));
				guests.add(g);
				System.out.println(g);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(2, guests.size());
		assertEquals(gostiPera.get(0), guests.get(0));
	}
	
	@Test
	public void testOperacijaVratiPoTriKriterijuma() throws Exception {
		columns.add("imeGosta");
		values.add("'Pera'");
		
		columns.add("prezimeGosta");
		values.add("'Peric'");
		
		columns.add("brojLicneKarte");
		values.add("'6876'");
		
		SOPretraziGoste so = new SOPretraziGoste(columns, values);
		so.izvrsenje();

		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM gost WHERE imeGosta='Pera' AND prezimeGosta='Peric' AND brojLicneKarte='6876'";
		ResultSet rs = statement.executeQuery(upit);

		List<Gost> guests = new ArrayList<>();
		try {
			while (rs.next()) {
				Long guestID = rs.getLong("gostID");
				String name = rs.getString("imeGosta");
				String lastname = rs.getString("prezimeGosta");
				String cardID = rs.getString("brojLicneKarte");
				String phoneNumber = rs.getString("brojTelefona");
				String mail = rs.getString("email");
				Long recepcionistID = rs.getLong("recepcionerID");

				Gost g = new Gost(guestID, name, lastname, cardID, phoneNumber, mail, new Recepcioner(recepcionistID));
				guests.add(g);
				System.out.println(g);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(1, guests.size());
		assertEquals(gostiPera.get(0), guests.get(0));
	}

}
