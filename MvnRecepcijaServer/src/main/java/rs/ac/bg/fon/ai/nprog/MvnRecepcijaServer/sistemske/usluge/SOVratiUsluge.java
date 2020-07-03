/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.usluge;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 *
 * @author Win10
 */
public class SOVratiUsluge extends OpstaSistemskaOperacija{

    List<UslugaNajma> usluge = new ArrayList<>();
    List<String> columns = new ArrayList<>();
    List<String> values = new ArrayList<>();

    public SOVratiUsluge(List<String> columns,List<String> values) {
        this.columns=columns;
        this.values=values;
    }

    public List<UslugaNajma> getUsluge() {
        return usluge;
    }
    
    @Override
    protected void operacija() throws Exception {
        List<OpstiDomenskiObjekat> odo = Konekcija.getInstance().getSveOpsteDomenskeObjekteSaWhere(new UslugaNajma(), columns, values);
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : odo) {
            UslugaNajma un = (UslugaNajma) opstiDomenskiObjekat;
            StavkaKataloga sk = (StavkaKataloga) Konekcija.getInstance().getOpstiDomenskiObjekatPoSlozenomPrimarnomKljucu(new StavkaKataloga(), un.getStavkaKataloga().getStavkaKatalogaID());
            un.setStavkaKataloga(sk);
            usluge.add(un);
            //vrsta sobe
        }
    }
    
}
