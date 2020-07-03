package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.komponenta.tabela.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.Recepcioner;

public class OnlineRecepcionerTableModel extends AbstractTableModel{
	
	Recepcioner recepcioner;
    List<Recepcioner> onlineRecepcioneri;
    private String[] imenaKolona = new String[]{"Korisnicko ime","Vreme prijave"};

    public OnlineRecepcionerTableModel(List<Recepcioner> onlineRecepcioneri) {
        this.onlineRecepcioneri = onlineRecepcioneri;
    }

    public OnlineRecepcionerTableModel() {
    	onlineRecepcioneri=new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return onlineRecepcioneri.size();
    }

    @Override
    public int getColumnCount() {
        return imenaKolona.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return imenaKolona[columnIndex];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Recepcioner r = onlineRecepcioneri.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return r.getKorisnickoIme();
            case 1: SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
            	return sdf.format(new Date());
            default: return "greskicaa";
        }
    }
    
     public List<Recepcioner> getOnlineReceptionists() {
        return onlineRecepcioneri;
    }

    public void setonlineReceptionists(List<Recepcioner> onlineRecepcioneri) {
        this.onlineRecepcioneri = onlineRecepcioneri;
        fireTableDataChanged();
    }

    public void dodaj(Recepcioner r) {
    	onlineRecepcioneri.add(r);
        fireTableDataChanged();
    }
    
    public void obrisi(Recepcioner r) {
    	onlineRecepcioneri.remove(r);
        fireTableDataChanged();
    }
}
