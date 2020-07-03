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
 *
 * @author Win10
 */
public class SOKreirajGosta extends OpstaSistemskaOperacija{
    Gost gost;

    public SOKreirajGosta(Gost gost) {
        this.gost = gost;
    }

    public Gost getGost() {
        return gost;
    }
    
    
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
        } // else { kljuc nije generisan  }
        connection.commit(); // TODO: commit uraditi u servis metodi
        preparedStatement.close();
        
    }
    
}
