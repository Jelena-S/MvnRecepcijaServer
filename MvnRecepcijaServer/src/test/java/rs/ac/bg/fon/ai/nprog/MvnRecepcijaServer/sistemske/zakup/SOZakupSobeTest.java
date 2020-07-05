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

	ZakupSobe zs, zs2, zs3, zs4;
	Gost g, g2, g3, g4;
	Soba s,s2,s3;
	Recepcioner r;
	VrstaSobe vs, vs2;
	static Connection connection;
	List<ZakupSobe> zakupi, zakupi1111;
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
		
		
		zs4 = new ZakupSobe();
		
		g4=new Gost(Long.valueOf(4), "Jelena", "Sreckovic", "123456789", "123123", "jeca", r);
		
		zs4.setZakupID(Long.valueOf(44444));
		zs4.setGostZakupljuje(g4);
		zs4.setZakupljenaSoba(s);
		zs4.setRecepcioner(r);
		zs4.setStatus(true);
		zs4.setCena(10000);
		
		datumOd = df.parse("12.12.2020. 12:12:12");
		datumDo = df.parse("15.12.2020. 12:12:12");
		
		zs4.setDatumOd(datumOd);
		zs4.setDatumDo(datumDo);
		
		
		zs2 = new ZakupSobe();
		
		g2=new Gost(Long.valueOf(2), "Pera", "Mikic", "99999999", "44444", "emailmikic", r);
		s2=new Soba(Long.valueOf(222), false, vs);
		
		zs2.setZakupID(Long.valueOf(22222));
		zs2.setGostZakupljuje(g2);
		zs2.setZakupljenaSoba(s2);
		zs2.setRecepcioner(r);
		zs2.setStatus(true);
		zs2.setCena(10000);
		
		datumOd = df.parse("10.11.2020. 12:12:12");
		datumDo = df.parse("15.11.2020. 12:12:12");
		
		zs2.setDatumOd(datumOd);
		zs2.setDatumDo(datumDo);
		
		//////////////////////////////////
		zs3 = new ZakupSobe();
		
		g3=new Gost(Long.valueOf(3), "Zika", "Zikic", "111111", "11111", "emailzika", r);
		vs2=new VrstaSobe(Long.valueOf(2222), "Vrsta sobe 2", 3, "Opis2");
		s3=new Soba(Long.valueOf(333), false, vs2);
		
		zs3.setZakupID(Long.valueOf(33333));
		zs3.setGostZakupljuje(g3);
		zs3.setZakupljenaSoba(s3);
		zs3.setRecepcioner(r);
		zs3.setStatus(true);
		zs3.setCena(10000);
		
		datumOd = df.parse("20.12.2020. 12:12:12");
		datumDo = df.parse("25.12.2020. 12:12:12");
		
		zs3.setDatumOd(datumOd);
		zs3.setDatumDo(datumDo);
		
		Statement statement = connection.createStatement();
		String upit = "INSERT INTO recepcioner VALUES(11, 'Jelena', 'Sreckovic', 'admin', 'admin')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO gost VALUES(1, 'Pera', 'Peric', '66666666', '555555','emailpera',11)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO gost VALUES(2, 'Pera', 'Mikic', '99999999', '44444','emailmikic',11)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO gost VALUES(3, 'Zika', 'Zikic', '111111', '11111','emailzika',11)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO gost VALUES(4, 'Jelena', 'Sreckovic', '123123123', '123123','jeca',11)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO vrstasobe VALUES(1111, 'Vrsta sobe 1', '2', 'Opis')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO vrstasobe VALUES(2222, 'Vrsta sobe 2', '3', 'Opis2')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO soba VALUES(111, false, 1111)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO soba VALUES(222, false, 1111)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO soba VALUES(333, false, 2222)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO zakupsobe VALUES(4,111,44444,'2020-12-12 12:12:12','2020-12-15 12:12:12',10000.0,11,true)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO zakupsobe VALUES(2,222,22222,'2020-11-10 12:12:12','2020-11-15 12:12:12',10000.0,11,true)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO zakupsobe VALUES(3,333,33333,'2020-12-20 12:12:12','2020-12-20 12:12:12',10000.0,11,true)";
		statement.executeUpdate(upit);
		
		zakupi = new ArrayList<>();
		zakupi.add(zs2);
		zakupi.add(zs3);
		zakupi.add(zs4);
		
		columns = new ArrayList<>();
		values = new ArrayList<>();
		
		zakupi1111 = new ArrayList<>();
		zakupi1111.add(zs2);
		zakupi1111.add(zs4);
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
	public void testOperacijaKreiraj() throws Exception {
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

        assertEquals(zs, nadjeni);
        assertEquals(zs.getGostZakupljuje().getGostID(), nadjeni.getGostZakupljuje().getGostID());
	}
	
	@Test
	public void testOperacijaVratiSve() throws Exception{
		SOVratiSveZakupe so = new SOVratiSveZakupe();
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
        
        assertEquals(rents.size(), zakupi.size());
		assertEquals(rents.get(0), zakupi.get(0));
	}
	
	@Test
	public void testOperacijaPretrazi() throws Exception{
		columns.add("vrstaSobeID");
		values.add("1111");
		SOPretraziZakupe so = new SOPretraziZakupe(columns,values);
		so.izvrsenje();
		
		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM gost JOIN zakupsobe on gost.gostID=zakupsobe.gostZakupljujeID join soba on zakupsobe.zakupljenaSobaID=soba.sobaID WHERE vrstaSobeID=1111";
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
        
        assertEquals(rents.size(), zakupi1111.size());
		assertEquals(rents.get(0), zakupi1111.get(0));
	}

}
