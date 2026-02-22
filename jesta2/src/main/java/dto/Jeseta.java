package dto;

import dto.enums.TipoJeseta;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Jeseta {
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private int idJeseta;
	private String descripcion;
	private TipoJeseta tipo;
	private boolean usado;
	private int idJugador;
	private int idUsuario;
	private boolean seleccionado;
	private Date fecha;

	public Jeseta(int idJeseta, String descripcion, TipoJeseta tipo, boolean usado, int idJugador, int idUsuario,
			boolean seleccionado, Date fecha) {
		this.idJeseta = idJeseta;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.usado = usado;
		this.idJugador = idJugador;
		this.idUsuario = idUsuario;
		this.seleccionado = seleccionado;
		this.fecha = fecha;
	}

	public Jeseta() {
		this.idJeseta = -1;
		this.idJugador = -1;
		this.idUsuario = -1;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdJeseta() {
		return this.idJeseta;
	}

	public void setIdJeseta(int idJeseta) {
		this.idJeseta = idJeseta;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return this.tipo.name();
	}

	public void setTipo(String tipo) {
		this.tipo = TipoJeseta.valueOf(tipo);
	}

	public String getImagen() {
		return this.tipo.getCSSClass();
	}

	public boolean isUsado() {
		return this.usado;
	}

	public void setUsado(boolean usado) {
		this.usado = usado;
	}

	public int getIdJugador() {
		return this.idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

	public boolean isSeleccionado() {
		return this.seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setIni(String fecha) throws ParseException {
		this.fecha = this.simpleDateFormat.parse(fecha);
	}
}