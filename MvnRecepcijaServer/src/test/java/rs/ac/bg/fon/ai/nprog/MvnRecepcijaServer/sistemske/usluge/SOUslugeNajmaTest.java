package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.usluge;

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

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.Katalog;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.Soba;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.StavkaKataloga;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.UslugaNajma;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.VrstaSobe;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konfiguracija.Konfiguracija;

public class SOUslugeNajmaTest {
	
	UslugaNajma un1, un2, un3;
	StavkaKataloga sk1, sk2, sk3;
	Katalog k;
	VrstaSobe vs1, vs2;
	static Connection connection;
	List<String> columns, values;
	List<UslugaNajma> usluge, usluge6;

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
		un1 = new UslugaNajma();
		un1.setUslugaNajmaID(Long.valueOf(1));
		un1.setNazivUslugeNajma("NocenjeApartman");
		un1.setCenaUsluge(10000);
		
		vs1 = new VrstaSobe();
		vs1.setVrstaSobeID(Long.valueOf(11));
		vs1.setNazivVrsteSobe("Apartman");
		vs1.setOpis("Opisopis");
		vs1.setBrojKreveta(2);
		
		un1.setVrstaSobe(vs1);
		
		k = new Katalog();
		k.setKatalogID(Long.valueOf(1111));
		k.setNazivKataloga("Smestaj");
		
		sk1 = new StavkaKataloga();
		sk1.setStavkaKatalogaID(Long.valueOf(111));
		sk1.setNazivStavkeKataloga("Nocenje-Apartman");
		sk1.setKatalog(k);
		
		un1.setStavkaKataloga(sk1);
		
		un2 = new UslugaNajma();
		un2.setUslugaNajmaID(Long.valueOf(2));
		un2.setNazivUslugeNajma("punPansionApartman");
		un2.setCenaUsluge(20000);
		un2.setVrstaSobe(vs1);
		
		sk2 = new StavkaKataloga();
		sk2.setStavkaKatalogaID(Long.valueOf(222));
		sk2.setNazivStavkeKataloga("PunPansion-Apartman");
		sk2.setKatalog(k);
		
		un2.setStavkaKataloga(sk2);
		
		un3 = new UslugaNajma();
		un3.setUslugaNajmaID(Long.valueOf(3));
		un3.setNazivUslugeNajma("NocenjeDvokrevetna");
		un3.setCenaUsluge(8000);
		
		vs2 = new VrstaSobe();
		vs2.setVrstaSobeID(Long.valueOf(22));
		vs2.setNazivVrsteSobe("Dvokrevetna");
		vs2.setOpis("Opisopis2");
		vs2.setBrojKreveta(2);
		
		un3.setVrstaSobe(vs2);
		
		sk3 = new StavkaKataloga();
		sk3.setStavkaKatalogaID(Long.valueOf(333));
		sk3.setNazivStavkeKataloga("Nocenje-Dvokrevetna");
		sk3.setKatalog(k);
		
		un3.setStavkaKataloga(sk3);
		
		columns = new ArrayList<>();
		values = new ArrayList<>();
		
		usluge6 = new ArrayList<>();
		usluge6.add(un1);
		usluge6.add(un2);
		
		Statement statement = connection.createStatement();
		String upit = "INSERT INTO katalog VALUES(1111, 'Smestaj')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO stavkakataloga VALUES(1111, 111 ,'Nocenje-Apartman')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO stavkakataloga VALUES(1111, 222 ,'PunPansion-Apartman')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO stavkakataloga VALUES(1111, 333 ,'Nocenje-Dvokrevetna')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO vrstasobe VALUES(11, 'Apartman', 2, 'Opisopis')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO vrstasobe VALUES(22, 'Dvokrevetna',2, 'Opisopis2')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO usluganajma VALUES(111,1, 'NocenjeApartman', 10000, 11)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO usluganajma VALUES(222,2, 'punPansionApartman',20000, 11)";
		statement.executeUpdate(upit);
		upit = "INSERT INTO usluganajma VALUES(333,3, 'NocenjeDvokrevetna',8000, 22)";
		statement.executeUpdate(upit);
		
		
	}

	@After
	public void tearDown() throws Exception {
		un1 = null;
		un2 = null;
		un3 = null;
		sk1 = null;
		sk2 = null;
		sk3 = null;
		k = null;
		vs1 = null;
		vs2 = null;
		Statement statement = connection.createStatement();
		String upit = "DELETE FROM usluganajma";
		statement.executeUpdate(upit);
		upit = "DELETE FROM stavkakataloga";
		statement.executeUpdate(upit);
		upit = "DELETE FROM katalog";
		statement.executeUpdate(upit);
		upit = "DELETE FROM vrstasobe";
		statement.executeUpdate(upit);
		connection.commit();
	}

	@Test
	public void testOperacija() throws Exception {
		columns.add("vrstaSobeID");
		values.add("11");
		
		SOVratiUsluge so = new SOVratiUsluge(columns, values);
		so.izvrsenje();
		
		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM usluganajma WHERE vrstaSobeID=11";
		ResultSet rs = statement.executeQuery(upit);

		List<UslugaNajma> lista = new ArrayList<>();
		
		try {
            while (rs.next()) {     
                Long stavkaID = rs.getLong("stavkaKatalogaID");
                Long uslugaID = rs.getLong("uslugaNajmaID");
                String name = rs.getString("nazivUslugeNajma");
                Double price = rs.getDouble("cenaUsluge");
                Long vrstaSobeID = rs.getLong("vrstaSobeID");
                
               UslugaNajma un = new UslugaNajma(new StavkaKataloga(stavkaID),uslugaID, name, price, new VrstaSobe(vrstaSobeID));
               lista.add(un);
            }
        } catch (Exception e) {
            System.out.println("Greska u UslugaNajma.Class ResultSet");      
        }
		
		assertEquals(2, lista.size());
		assertEquals(usluge6.size(), lista.size());
		assertEquals(un1.getUslugaNajmaID(), lista.get(0).getUslugaNajmaID());
	}

}
