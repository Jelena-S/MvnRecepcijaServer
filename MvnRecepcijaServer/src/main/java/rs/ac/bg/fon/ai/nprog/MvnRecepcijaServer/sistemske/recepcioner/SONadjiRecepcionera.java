/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.recepcioner;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 * Klasa koja predstavlja sistemsku operaciju za pretrazivanje recepcionera. Nasledjuje apstraktnu klasu OpstaSistemskaOperacija
 *
 * @author Jelena Sreckovic
 */
public class SONadjiRecepcionera extends OpstaSistemskaOperacija{
	/**
	 * Podaci o recepcioneru koji treba biti pronadjen
	 */
    Recepcioner recepcioner;

    /**
     * Parametrizovani konstruktor. Inicijalizuje sistemsku operaciju i postavlja recepcionera
     * 
     * @param recepcioner kog je potrebno naci u bazi
     */
    public SONadjiRecepcionera(Recepcioner recepcioner) {
        this.recepcioner = recepcioner;
    }

    /**
     * Metoda vraca recepcionera
     * 
     * @return recepcioner kao Recepcioner
     */
    public Recepcioner getRecepcioner() {
        return recepcioner;
    }
    
    /**
     * Metoda izvrsava sistemsku operaciju pretrazivanja recepcioenra. Pristupa bazi i nalazi podatke o recepcioneru na osnovu prosledjenog kriterijuma - korisnickog imena i lozinke
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */    
    @Override
    protected void operacija() throws Exception {
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        columns.add("username");
        columns.add("password");
        values.add("'"+recepcioner.getKorisnickoIme()+"'");
        values.add("'"+recepcioner.getLozinka()+"'");
        List<OpstiDomenskiObjekat> r = Konekcija.getInstance().getSveOpsteDomenskeObjekteSaWhere(recepcioner, columns, values);
        if(r.size()==0){
            recepcioner=null;
        } else {
        recepcioner=(Recepcioner) r.get(0);
        }
    }
    
}
