package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.vrstasobe;

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

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.VrstaSobe;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konfiguracija.Konfiguracija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.vrstasobe.SOVratiSveVrsteSoba;

public class SOVrstaSobeTest {

	VrstaSobe vrstaSobe, vrstaSobe1;
	static Connection connection;
	List<VrstaSobe> vrste;

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
		vrstaSobe = new VrstaSobe();
		vrstaSobe.setVrstaSobeID(Long.valueOf(11));
		vrstaSobe.setNazivVrsteSobe("Vrsta sobe 1");
		vrstaSobe.setOpis("Opis");
		vrstaSobe.setBrojKreveta(2);
		
		vrstaSobe1 = new VrstaSobe();
		vrstaSobe1.setVrstaSobeID(Long.valueOf(22));
		vrstaSobe1.setNazivVrsteSobe("Vrsta sobe 2");
		vrstaSobe1.setOpis("Opis2");
		vrstaSobe1.setBrojKreveta(2);
		
		vrste = new ArrayList<>();
		
		vrste.add(vrstaSobe);
		vrste.add(vrstaSobe1);
		
		Statement statement = connection.createStatement();
		String upit = "INSERT INTO vrstasobe VALUES(11, 'Vrsta sobe 1', '2', 'Opis')";
		statement.executeUpdate(upit);
		upit = "INSERT INTO vrstasobe VALUES(22, 'Vrsta sobe 2', '2', 'Opis2')";
		statement.executeUpdate(upit);
		
	}

	@After
	public void tearDown() throws Exception {
		vrstaSobe=null;
		vrstaSobe1=null;
		vrste=null;
		Statement statement = connection.createStatement();// proveri da li moze sa istim statementom
		String upit = "DELETE FROM vrstasobe";
		statement.executeUpdate(upit);
		connection.commit();
	}

	@Test
	public void testOperacija() throws Exception {
		SOVratiSveVrsteSoba so = new SOVratiSveVrsteSoba();
		so.izvrsenje();
		
		Statement statement = connection.createStatement();
		String upit = "SELECT * FROM vrstasobe";
		ResultSet rs = statement.executeQuery(upit);

		List<VrstaSobe> rents = new ArrayList<>();
		
		try {
            while (rs.next()) {                
                Long vrstaID = rs.getLong("vrstaSobeID");
                String naziv = rs.getString("nazivVrsteSobe");
                int broj = rs.getInt("brojKreveta");
                String opiss = rs.getString("opis");
                
                VrstaSobe vs = new VrstaSobe(vrstaID, naziv, broj, opiss); 
                rents.add(vs);
                
                
            }
        } catch (Exception e) {
            System.out.println("Greska u VrstaSobe.Class ResultSet"); 
                 
        }
		
		assertEquals(rents.size(), vrste.size());
		assertEquals(rents.get(0), vrste.get(0));
		//contains
	}

}
