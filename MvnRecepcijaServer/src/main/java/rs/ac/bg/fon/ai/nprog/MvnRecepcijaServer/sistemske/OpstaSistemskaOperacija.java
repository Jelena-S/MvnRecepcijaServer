/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Win10
 */
public abstract class OpstaSistemskaOperacija {
    public final void izvrsenje() throws Exception{
        try {
            Konekcija.getInstance().getConnection();
            operacija();
            Konekcija.getInstance().commit();
            
        } catch (Exception ex) {
            Konekcija.getInstance().rollback();
            Logger.getLogger(OpstaSistemskaOperacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected abstract void operacija() throws Exception;
}
