package dto;

import dto.enums.Color;
import dto.enums.TipoUser;

public class Usuario {
	private int id;
	private String user;
	private String pass;
	private TipoUser tipo;
	private Color color;
	private int idJugador;

	public Usuario(int id, String user, String pass, TipoUser tipo, Color color, int idJugador) {
		this.id = id;
		this.user = user;
		this.pass = pass;
		this.tipo = tipo;
		this.color = color;
		this.idJugador = idJugador;
	}

	public Usuario() {
		this.id = -1;
		this.idJugador = -1;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTipo() {
		return this.tipo.name();
	}

	public void setTipo(String tipo) {
		this.tipo = TipoUser.valueOf(tipo);
	}

	public String getColor() {
		return this.color.getCSSClass();
	}

	public Color getColorTipo() {
		return this.color;
	}

	public void setColor(String tcolor) {
		this.color = Color.valueOf(tcolor);
	}

	public int getIdJugador() {
		return this.idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}
}