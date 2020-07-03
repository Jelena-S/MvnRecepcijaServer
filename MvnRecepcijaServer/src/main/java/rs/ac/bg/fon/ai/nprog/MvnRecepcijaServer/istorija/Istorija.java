package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.istorija;

import java.util.Date;

public class Istorija {
	private int operacija;
	private Date datum;
	private String greska;
	private boolean uspesno;
	
	public Istorija(int operacija, Date datum, String greska, boolean uspesno) {
		this.operacija = operacija;
		this.datum = datum;
		this.greska = greska;
		this.uspesno = uspesno;
	}
	public int getOperacija() {
		return operacija;
	}
	public void setOperacija(int operacija) {
		this.operacija = operacija;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public String getGreska() {
		return greska;
	}
	public void setGreska(String greska) {
		this.greska = greska;
	}
	public boolean isUspesno() {
		return uspesno;
	}
	public void setUspesno(boolean uspesno) {
		this.uspesno = uspesno;
	}
	@Override
	public String toString() {
		return "Istorija [operacija=" + operacija + ", datum=" + datum + ", greska=" + greska + ", uspesno=" + uspesno
				+ "]";
	}
}
