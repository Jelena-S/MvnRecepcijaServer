package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.zakup;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konfiguracija.Konfiguracija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.zakup.SOKreirajZakup;

public class SOZakupSobeTest {

	ZakupSobe zs;
	Gost g;
	Soba s;
	Recepcioner r;
	VrstaSobe vs;
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
		zs = new ZakupSobe();
		
		r=new Recepcioner(Long.valueOf(11), "Jelena", "Sreckovic", "admin", "admin");
		g=new Gost(Long.valueOf(1), "Pera", "Peric", "66666666", "555555", "emailpera", r);
		vs=new VrstaSobe(Long.valueOf(1111), "Vrsta sobe 1", 2, "Opis");
		s=new Soba(Long.valueOf(111), false, vs);
		
		zs.setZakupID(Long.valueOf(11111));
		zs.setGostZakupljuje(g);
		zs.setZakupljenaSoba(s);
		zs.setRecepcioner(r);
		zs.setStatus(true);
		zs.setCena(10000);
		
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
		Date datumOd = new Date();
		Date datumDo = new Date();
		datumOd = df.parse("12.12.2020. 12:12:12");
		datumDo = df.parse("15.12.2020. 12:12:12");
		
		zs.setDatumOd(datumOd);
		zs.setDatumDo(datumDo);
		
		Statement statement = connection.createStatement();
		String upit = "INSERT INTO recepcioner VALUES(11, 'Jelena', 'Sreckovic', 'admin', 'admin')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO gost VALUES(1, 'Pera', 'Peric', '66666666', '555555','emailpera',11)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO vrstasobe VALUES(1111, 'Vrsta sobe 1', '2', 'Opis')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO soba VALUES(111, false, 1111)";
		statement.executeUpdate(upit);
		
	}

	@After
	public void tearDown() throws Exception {
		Statement statement = connection.createStatement();// proveri da li moze sa istim statementom
		String upit = "DELETE FROM zakupsobe";
		statement.executeUpdate(upit);
		upit = "DELETE FROM soba";
		statement.executeUpdate(upit);
		upit = "DELETE FROM vrstasobe";
		statement.executeUpdate(upit);
		upit = "DELETE FROM gost";
		statement.executeUpdate(upit);
		upit = "DELETE FROM recepcioner";
		statement.executeUpdate(upit);
		connection.commit();
		r = null;
		g = null;
		s = null;
		zs = null;
		vs = null;
	}

	@Test
	public void testOperacija() throws Exception {
		SOKreirajZakup so = new SOKreirajZakup(zs);
		so.izvrsenje();
		
		
		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM zakupsobe";
		ResultSet rs = statement.executeQuery(upit);
		
		List<ZakupSobe> rents = new ArrayList<>();
        try {
            while (rs.next()) {                
                Long gost = rs.getLong("gostZakupljujeID");
                Long room = rs.getLong("zakupljenaSobaID");
                Long rent = rs.getLong("zakupID");
                Date dOD = rs.getDate("datumOd");
                Date dDO = rs.getDate("datumDo");
                Double price = rs.getDouble("cena");
                Long recepcionistID = rs.getLong("recepcionerID");
                boolean status = rs.getBoolean("status");
                
                ZakupSobe z = new ZakupSobe(new Gost(gost), new Soba(room), rent, dOD, dDO, price,status,new Recepcioner(recepcionistID));
                rents.add(z);
            }
        } catch (Exception e) {
            System.out.println("Greska u ZakupSObe.Class ResultSet"); 
                 
        }
        
        ZakupSobe nadjeni = (ZakupSobe) rents.get(0);

		assertEquals(zs.getGostZakupljuje().getGostID(), nadjeni.getGostZakupljuje().getGostID());
	}

}
