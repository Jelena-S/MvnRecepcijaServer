/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.gost;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.Gost;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 * Klasa koja predstavlja sistemsku operaciju za kreiranje gosta. Nasledjuje apstraktnu klasu OpstaSistemskaOperacija
 *
 * @author Jelena Sreckovic
 */
public class SOKreirajGosta extends OpstaSistemskaOperacija{
	
	/**
	 * Informacije o gostu kog treba kreirati odnosno sacuvati
	 */
    Gost gost;

    /**
     * Parametrizovani konstruktor. Inicijalizuje operaciju i postavlja gosta kog treba sacuvati
     * 
     * @param gost gost koji se postavlja
     * 
     */
    public SOKreirajGosta(Gost gost) {
        this.gost = gost;
    }

    /**
     * Metoda vraca gosta
     * 
     * @return gost kao Gost
     */
    public Gost getGost() {
        return gost;
    }
    
    /**
     * Metoda izvrsava sistemsku operaciju kreiranja gosta. Pristupa bazi i cuva prosledjene podatke o gostu. Pri cuvanju generise primarni kljuc gosta
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */
    @Override
    protected void operacija() throws Exception {
        Connection connection = Konekcija.getInstance().getConnection();
        
        String query = "INSERT INTO "+gost.getImeTabele()+" ("+gost.getImenaParametaraBezID()+")"+" VALUES (?,?,?,?,?,?)";
        System.out.println(query);
        PreparedStatement preparedStatement = connection.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, gost.getImeGosta());
        preparedStatement.setString(2, gost.getPrezimeGosta());
        preparedStatement.setString(3, gost.getBrojLicneKarte());
        preparedStatement.setString(4, gost.getBrojTelefona());
        preparedStatement.setString(5, gost.getEmail());
        preparedStatement.setLong(6, gost.getRecepcioner().getRecepcionerID());
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            Long id = rs.getLong(1);
            System.out.println("ID="+id);
            gost.setGostID(id);
        } // else 
        connection.commit(); 
        preparedStatement.close();
        
    }
    
}
