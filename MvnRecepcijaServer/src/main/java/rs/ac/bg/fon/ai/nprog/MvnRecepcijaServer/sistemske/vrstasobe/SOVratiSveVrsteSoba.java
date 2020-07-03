/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.vrstasobe;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 *
 * @author Win10
 */
public class SOVratiSveVrsteSoba extends OpstaSistemskaOperacija{
    
    List<VrstaSobe> vrste = new ArrayList<>();

    public SOVratiSveVrsteSoba() {
        
    }

    public List<VrstaSobe> getVrste() {
        return vrste;
    }
    
    
    
    @Override
    protected void operacija() throws Exception {
        List<OpstiDomenskiObjekat> odo = Konekcija.getInstance().getSveOpsteDomenskeObjekte(new VrstaSobe());
        
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : odo) {
            VrstaSobe vs = (VrstaSobe) opstiDomenskiObjekat;
            
            vrste.add(vs);
        }
        
    }
    
}
