/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.zakup;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 *
 * @author Win10
 */
public class SOKreirajZakup extends OpstaSistemskaOperacija{
    ZakupSobe zakup;

    public SOKreirajZakup(ZakupSobe zakup) {
        this.zakup = zakup;
    }

    public ZakupSobe getZakup() {
        return zakup;
    }

    @Override
    protected void operacija() throws Exception {
        Connection connection = Konekcija.getInstance().getConnection();
        
        String query = "INSERT INTO "+zakup.getImeTabele()+" ("+zakup.getImenaParametara()+")"+" VALUES (?,?,?,?,?,?,?,?)";
        System.out.println(query);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, zakup.getGostZakupljuje().getGostID());
        preparedStatement.setLong(2, zakup.getZakupljenaSoba().getSobaID());
        preparedStatement.setLong(3, zakup.getZakupID());
        preparedStatement.setDate(4, new Date(zakup.getDatumOd().getTime()));
        preparedStatement.setDate(5, new Date(zakup.getDatumDo().getTime()));
        preparedStatement.setDouble(6, zakup.getCena());
        preparedStatement.setLong(7, zakup.getRecepcioner().getRecepcionerID());
        preparedStatement.setBoolean(8, zakup.isStatus());
        preparedStatement.executeUpdate();
         // else { kljuc nije generisan  }
        //connection.commit(); // TODO: commit uraditi u servis metodi
        preparedStatement.close();
    }
    
    
}
