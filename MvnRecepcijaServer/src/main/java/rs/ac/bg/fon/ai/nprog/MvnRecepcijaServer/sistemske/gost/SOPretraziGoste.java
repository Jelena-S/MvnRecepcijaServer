/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.gost;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 *
 * @author Win10
 */
public class SOPretraziGoste extends OpstaSistemskaOperacija{
    List<Gost> gosti = new ArrayList<>();
    List<String> columns = new ArrayList<>();
    List<String> values = new ArrayList<>();

    public SOPretraziGoste(List<String> columns,List<String> values) {
        
        this.columns=columns;
        this.values=values;
    }

    public List<Gost> getGosti() {
        return gosti;
    }

    @Override
    protected void operacija() throws Exception {
        List<OpstiDomenskiObjekat> odo = Konekcija.getInstance().getSveOpsteDomenskeObjekteSaWhere(new Gost(), columns, values);
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : odo) {
            Gost g = (Gost) opstiDomenskiObjekat;
            Recepcioner r = (Recepcioner) Konekcija.getInstance().getOpstiDomenskiObjekatPoPrimarnomKljucu(new Recepcioner(), g.getRecepcioner().getRecepcionerID());
            g.setRecepcioner(r);
            gosti.add(g);
        }
    }
    
}
