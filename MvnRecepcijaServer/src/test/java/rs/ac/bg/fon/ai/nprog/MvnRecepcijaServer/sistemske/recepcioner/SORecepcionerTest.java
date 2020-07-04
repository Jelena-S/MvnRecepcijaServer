package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.recepcioner;

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

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.recepcioner.SONadjiRecepcionera;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konfiguracija.Konfiguracija;

public class SORecepcionerTest {

	Recepcioner recepcioner, recepcioner1, recepcioner2, recepcioner3;
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
		// configuration.getinstance.geturl//////////////////////
	}

	@Before
	public void setUp() throws Exception {
		recepcioner = new Recepcioner();
		recepcioner.setRecepcionerID(Long.valueOf(11));
		recepcioner.setImeRecepcionera("Jelena");
		recepcioner.setPrezimeRecepcionera("Sreckovic");
		recepcioner.setKorisnickoIme("admin");
		recepcioner.setLozinka("admin");
		
		Statement statement = connection.createStatement();
		String upit = "INSERT INTO recepcioner VALUES(11, 'Jelena', 'Sreckovic', 'admin', 'admin')";
		statement.executeUpdate(upit);
		
		recepcioner1 = new Recepcioner();
		recepcioner1.setRecepcionerID(Long.valueOf(22));
		recepcioner1.setImeRecepcionera("Mika");
		recepcioner1.setPrezimeRecepcionera("Mikic");
		recepcioner1.setKorisnickoIme("mika");
		recepcioner1.setLozinka("mika97");
		
		recepcioner2 = new Recepcioner();
		recepcioner2.setRecepcionerID(Long.valueOf(33));
		recepcioner2.setImeRecepcionera("Zika");
		recepcioner2.setPrezimeRecepcionera("Zikic");
		recepcioner2.setKorisnickoIme("admin");
		recepcioner2.setLozinka("zika97");
		
		recepcioner3 = new Recepcioner();
		recepcioner3.setRecepcionerID(Long.valueOf(44));
		recepcioner3.setImeRecepcionera("Zika");
		recepcioner3.setPrezimeRecepcionera("Zikic");
		recepcioner3.setKorisnickoIme("zika");
		recepcioner3.setLozinka("admin");
	}

	@After
	public void tearDown() throws Exception {
		recepcioner=null;
		recepcioner1=null;
		recepcioner2=null;
		recepcioner3=null;
		Statement statement = connection.createStatement();// proveri da li moze sa istim statementom
		String upit = "DELETE FROM recepcioner";
		statement.executeUpdate(upit);
		connection.commit();
	}

	@Test
	public void testOperacija() throws Exception {
		SONadjiRecepcionera so = new SONadjiRecepcionera(recepcioner);
		so.izvrsenje();
		
		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM recepcioner WHERE username='admin' AND password='admin'";
		ResultSet rs = statement.executeQuery(upit);

		List<Recepcioner> recepcioneri = new ArrayList<>();
		try {
			while (rs.next()) {
				Long recepcionerID = rs.getLong("recepcionerID");
				String name = rs.getString("imeRecepcionera");
				String lastname = rs.getString("prezimeRecepcionera");
				String username = rs.getString("username");
				String password = rs.getString("password");

				Recepcioner r = new Recepcioner(recepcionerID, name, lastname, username, password);
				recepcioneri.add(r);
				System.out.println(r);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		Recepcioner nadjeni = recepcioneri.get(0);

		assertEquals(recepcioner.getKorisnickoIme(), nadjeni.getKorisnickoIme());
		assertEquals(recepcioner.getRecepcionerID(), nadjeni.getRecepcionerID());
	}
	
	//ne postoji
	
	@Test
	public void testOperacijaRecepcionerNePostoji() throws Exception {
		SONadjiRecepcionera so = new SONadjiRecepcionera(recepcioner1);
		so.izvrsenje();
		
		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM recepcioner WHERE username='mika' AND password='mika97'";
		ResultSet rs = statement.executeQuery(upit);

		List<Recepcioner> recepcioneri = new ArrayList<>();
		try {
			while (rs.next()) {
				Long recepcionerID = rs.getLong("recepcionerID");
				String name = rs.getString("imeRecepcionera");
				String lastname = rs.getString("prezimeRecepcionera");
				String username = rs.getString("username");
				String password = rs.getString("password");

				Recepcioner r = new Recepcioner(recepcionerID, name, lastname, username, password);
				recepcioneri.add(r);
				System.out.println(r);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(0, recepcioneri.size());
		
	}
	

	//pogresno jedno/drugo
	
	@Test
	public void testOperacijaRecepcionerNePostojiPogresnoUsername() throws Exception {
		SONadjiRecepcionera so = new SONadjiRecepcionera(recepcioner2);
		so.izvrsenje();
		
		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM recepcioner WHERE username='admin' AND password='zika97'";
		ResultSet rs = statement.executeQuery(upit);

		List<Recepcioner> recepcioneri = new ArrayList<>();
		try {
			while (rs.next()) {
				Long recepcionerID = rs.getLong("recepcionerID");
				String name = rs.getString("imeRecepcionera");
				String lastname = rs.getString("prezimeRecepcionera");
				String username = rs.getString("username");
				String password = rs.getString("password");

				Recepcioner r = new Recepcioner(recepcionerID, name, lastname, username, password);
				recepcioneri.add(r);
				System.out.println(r);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(0, recepcioneri.size());
		
	}
	
	@Test
	public void testOperacijaRecepcionerNePostojiPogresnoPassword() throws Exception {
		SONadjiRecepcionera so = new SONadjiRecepcionera(recepcioner3);
		so.izvrsenje();
		
		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM recepcioner WHERE username='zika' AND password='admin'";
		ResultSet rs = statement.executeQuery(upit);

		List<Recepcioner> recepcioneri = new ArrayList<>();
		try {
			while (rs.next()) {
				Long recepcionerID = rs.getLong("recepcionerID");
				String name = rs.getString("imeRecepcionera");
				String lastname = rs.getString("prezimeRecepcionera");
				String username = rs.getString("username");
				String password = rs.getString("password");

				Recepcioner r = new Recepcioner(recepcionerID, name, lastname, username, password);
				recepcioneri.add(r);
				System.out.println(r);
			}
		} catch (Exception e) {
			System.out.println("Greska u Gost.Class ResultSet");

		}

		assertEquals(0, recepcioneri.size());
		
	}

}
