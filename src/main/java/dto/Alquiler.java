package dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alquiler {
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private Jugador jugador;
	private JuegoBase juego;
	private Date ini;
	private Date fin;

	public Alquiler(Jugador jugador, JuegoBase juego, Date ini, Date fin) {
		this.jugador = jugador;
		this.juego = juego;
		this.ini = ini;
		this.fin = fin;
	}

	public Alquiler() {
	}

	public Jugador getJugador() {
		return this.jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public JuegoBase getJuego() {
		return this.juego;
	}

	public void setJuego(JuegoBase juego) {
		this.juego = juego;
	}

	public Date getIni() {
		return this.ini;
	}

	public void setIni(Date date) {
		this.ini = date;
	}

	public void setIni(String ini) throws ParseException {
		this.ini = this.simpleDateFormat.parse(ini);
	}

	public Date getFin() {
		return this.fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public void setFin(String fin) throws ParseException {
		this.fin = this.simpleDateFormat.parse(fin);
	}
}