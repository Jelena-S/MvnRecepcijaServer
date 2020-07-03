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
 *
 * @author Win10
 */
public class SONadjiRecepcionera extends OpstaSistemskaOperacija{

    Recepcioner recepcioner;

    public SONadjiRecepcionera(Recepcioner recepcioner) {
        this.recepcioner = recepcioner;
    }

    public Recepcioner getRecepcioner() {
        return recepcioner;
    }
    
    
    
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
