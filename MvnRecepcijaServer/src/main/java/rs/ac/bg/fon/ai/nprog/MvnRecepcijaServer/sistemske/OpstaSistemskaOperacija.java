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
 * Predstavlja apstraktnu klasu koju nasledjuju sve sistemske operacije.
 *
 * @author Jelena Sreckovic
 */
public abstract class OpstaSistemskaOperacija {
	
	/**
	 * Metoda koja poziva izvrsenje sistemske operacije. Uzima konekciju, izvrsava operaciju i ako je operacija uspesno izvrsena, cuva promene u bazi. U suprotnom, odbacuje promene. 
	 * 
	 * @throws Exception ako se operacija ne izvrsi uspesno
	 */
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
    
    /**
     * Metoda izvrsava sistemsku operaciju
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */
    protected abstract void operacija() throws Exception;
}
