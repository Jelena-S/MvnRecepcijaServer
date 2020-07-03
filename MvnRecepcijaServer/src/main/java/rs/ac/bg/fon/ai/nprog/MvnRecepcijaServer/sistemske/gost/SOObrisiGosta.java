/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.gost;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.Gost;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 *
 * @author Win10
 */
public class SOObrisiGosta extends  OpstaSistemskaOperacija{
    Gost gost;
    boolean uspesno;

    public SOObrisiGosta(Gost gost) {
        this.gost = gost;
    }

    public Gost getGost() {
        return gost;
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
    
    @Override
    protected void operacija() throws Exception {
        uspesno=Konekcija.getInstance().obrisiOpstiDomenskiObjekat(gost);
    }
}
