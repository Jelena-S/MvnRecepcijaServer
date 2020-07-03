package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.ZakupSobe;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konfiguracija.Konfiguracija;


public class Konekcija {
	private Konfiguracija konfiguracija;
    private Connection konekcija;
    public static Konekcija instance;

    private Konekcija() throws SQLException {
        try {
            String url = konfiguracija.getInstance().getDbUrl();
            String username = konfiguracija.getInstance().getUsername();
            String password = konfiguracija.getInstance().getPassword();
            
            konekcija = DriverManager.getConnection(url, username, password);
            konekcija.setAutoCommit(false);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new SQLException("Konekcija nije kreirana!");
        }
    }
    

    public void setUrl(String url) {
    	try {
    		konekcija.close();
    		konekcija = DriverManager.getConnection(url, "root", "");
    		konekcija.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Connection getConnection() {
        return konekcija;
    }

    public static Konekcija getInstance() throws SQLException {
        if (instance == null) {
            instance = new Konekcija();
        }
        return instance;
    }
    
    
    
    public void closeConnection() {
        try {
        	konekcija.close();
        } catch (SQLException ex) {
            System.out.println("Konekcija ne moze da se zatvori!");
        }
    }

    public void commit() {
        try {
        	konekcija.commit();
        } catch (SQLException ex) {
            System.out.println("Commit ne moze da se uradi!");
        }
    }

    public void rollback() {
        try {
        	konekcija.rollback();
        } catch (SQLException ex) {
            System.out.println("Rollback ne moze da se uradi!");
        }
    }

    public int vratiID (ZakupSobe o) throws SQLException { //ZA SAD NEK BUDE ZA ZAKUP
        try {
        int max = 0;
        String query = "SELECT max(zakupID) as max FROM "+o.getImeTabele();
        Statement statement = konekcija.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            while(rs.next()) {
                max = rs.getInt("max");
            }
        
        
        return ++max;
        } catch (SQLException ex) {
            System.out.println("Greska u postavljanju ResultSet-a na klasu " + o.getImeTabele());
            ex.printStackTrace();
            throw ex;
        }
    }
    
    public  List<OpstiDomenskiObjekat> getSveOpsteDomenskeObjekte(OpstiDomenskiObjekat o) throws SQLException {
        try {
            String query = "SELECT * FROM " + o.getImeTabele();
            System.out.println(query);
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(query);
            List<OpstiDomenskiObjekat> list = o.konvertujRSUListu(rs);
            s.close();
            System.out.println("ResultSet uspesno postavljen!");
            
            return list;
        } catch (SQLException ex) {
            System.out.println("Greska u postavljanju ResultSet-a na klasu " + o.getImeTabele());
            ex.printStackTrace();
            throw ex;
        }
    }
    
    public List<OpstiDomenskiObjekat> getSveOpsteDomenskeObjekteSaWhere(OpstiDomenskiObjekat o, List<String> columns, List<String> values) throws SQLException{
        try {
        String query = "SELECT * FROM " + o.getImeTabele() + " WHERE ";
        if(columns.size()!=1){
        for(int i = 0; i < columns.size()-1; i++) {
            query+=columns.get(i)+"="+values.get(i)+" and ";
        }
        }
        query+=columns.get(columns.size()-1)+"="+values.get(columns.size()-1);
            System.out.println(query);
        Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(query);
            List<OpstiDomenskiObjekat> list = o.konvertujRSUListu(rs);
            s.close();
            System.out.println("ResultSet uspesno postavljen!");
            return list;
        } catch (SQLException ex) {
            System.out.println("Greska u postavljanju ResultSet-a na klasu " + o.getImeTabele());
            ex.printStackTrace();
            throw ex;
        }
    }
    
    public List<OpstiDomenskiObjekat> getSveOpsteDomenskeObjekteJOIN(OpstiDomenskiObjekat o, List<String> columns, List<String> values, String join) throws SQLException{
        try {
            String query = "SELECT * FROM " + join + " WHERE ";
            if(columns.size()!=1){
            	for(int i = 0; i < columns.size()-1; i++) {
            		query+=columns.get(i)+"="+values.get(i)+" and ";
            	}
            }
            query+=columns.get(columns.size()-1)+"="+values.get(columns.size()-1);
            System.out.println(query);
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(query);
            List<OpstiDomenskiObjekat> list = o.konvertujRSUListu(rs);
            s.close();
            System.out.println("ResultSet uspesno postavljen!");
            return list;
        } catch (SQLException ex) {
            System.out.println("Greska JOIN u postavljanju ResultSet-a na klasu " + o.getImeTabele());
            ex.printStackTrace();
            throw ex;
            }
        }

    public  OpstiDomenskiObjekat getOpstiDomenskiObjekatPoPrimarnomKljucu(OpstiDomenskiObjekat o, Long id) throws SQLException {
        String query;

        if (o.getSlozeniPrimarniKljuc()== null) {
            query = "SELECT * FROM " + o.getImeTabele()+ " WHERE " + o.getImePrimarnogKljuca()+ "=" + id;
        } else {
            query = "SELECT * FROM " + o.getImeTabele()+ " WHERE " + o.getSlozeniPrimarniKljuc();
        }
        System.out.println(query);
        Statement s = (Statement) konekcija.createStatement();
        ResultSet rs = s.executeQuery(query);
        List<OpstiDomenskiObjekat> list = o.konvertujRSUListu(rs);
        s.close();
        return list.get(0);
    }
    
    public  OpstiDomenskiObjekat getOpstiDomenskiObjekatPoSlozenomPrimarnomKljucu(OpstiDomenskiObjekat o, Long id) throws SQLException {
        String query;
        String[] ids = o.getImenaParametara().split(",");
        
            query = "SELECT * FROM " + o.getImeTabele()+ " WHERE " + ids[0] + "=" +id +" AND " + ids[1]+"="+id;
        
        System.out.println("by primarykey: "+query);
        Statement s = (Statement) konekcija.createStatement();
        ResultSet rs = s.executeQuery(query);
        List<OpstiDomenskiObjekat> list = o.konvertujRSUListu(rs);
        s.close();
        return list.get(0);
    }

    public  boolean obrisiOpstiDomenskiObjekat(OpstiDomenskiObjekat o) throws SQLException {
        try {
            String query;
            
            if (o.getSlozeniPrimarniKljuc()== null) {
                query = "DELETE FROM " + o.getImeTabele()+ " WHERE " + o.getImePrimarnogKljuca()+ "=" + o.getVrednostPrimarnogKljuca();
            } else {
                query = "DELETE FROM " + o.getImeTabele()+ " WHERE " + o.getSlozeniPrimarniKljuc();
            }
            System.out.println(query);
            Statement s = (Statement) konekcija.createStatement();
            s.executeUpdate(query);
            commit();
            s.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Ne moze da se obrise objekat: " + o.getImeTabele());
            throw ex;
        }
    }

    public  void obrisiOpstiDomenskiObjekti(List<OpstiDomenskiObjekat> list) throws SQLException {
        for (OpstiDomenskiObjekat o : list) {
            obrisiOpstiDomenskiObjekat(o);
        }
    }

    public  boolean sacuvajOpstiDomenskiObjekat(OpstiDomenskiObjekat o) throws SQLException {
        try {
            String query = "";
            query = "INSERT INTO " + o.getImeTabele()+ "(" + o.getImenaParametara()+ ")" + " VALUES (" + o.getParametre()+ ")";
            System.out.println(query);
            Statement s = (Statement) konekcija.createStatement();
            int i = s.executeUpdate(query);
            s.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Ne moze da se sacuva objekat: " + o.getImeTabele());
            throw ex;
        }
    }

    public  void sacuvajOpstiDomenskiObjekti(List<OpstiDomenskiObjekat> list) throws SQLException {
        for (OpstiDomenskiObjekat o : list) {
            sacuvajOpstiDomenskiObjekat(o);
        }
    }

    public  boolean azurirajOpstiDomenskiObjekat(OpstiDomenskiObjekat o) throws SQLException {
        try {
            String query = "";
            if (o.getSlozeniPrimarniKljuc()== null) {
                query = "UPDATE " + o.getImeTabele()+ " SET " + o.getUpitAzuriranje()+ " WHERE " + o.getImePrimarnogKljuca()+ "=" + o.getVrednostPrimarnogKljuca();
            } else {
                query = "UPDATE " + o.getImeTabele()+ " SET " + o.getUpitAzuriranje()+ " WHERE " + o.getSlozeniPrimarniKljuc();
            }
            System.out.println(query);
            Statement s = (Statement) konekcija.createStatement();
            int i = s.executeUpdate(query);
            System.out.println(i);
            s.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Ne moze da se azurira objekat: " + o.getImeTabele());
            throw ex;
        }
    }
}
