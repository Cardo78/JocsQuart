package dto;

import java.util.ArrayList;
import java.util.List;

public class JuegoBase {
	protected int id;
	protected String nombre;
	protected int duracion;
	protected String rutaImagen;
	protected int maxjugadores;
	protected boolean pasaporte;
	protected String bgg;
	protected List<Juego> juegos;
	protected boolean infantil;
	protected int edad;
	protected Editorial editorial;

	public JuegoBase(int id, String nombre, int duracion, String rutaImagen, int maxjugadores, boolean pasaporte,
			String bgg, List<Juego> juegos, boolean infantil, int edad, Editorial editorial) {
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.rutaImagen = rutaImagen;
		this.maxjugadores = maxjugadores;
		this.pasaporte = pasaporte;
		this.bgg = bgg;
		this.juegos = juegos;
		this.infantil = infantil;
		this.edad = edad;
		this.editorial = editorial;
	}

	public JuegoBase() {
		this.id = -1;
		this.juegos = new ArrayList();
	}

	public int getIdBase() {
		return this.id;
	}

	public void setIdBase(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDuracion() {
		return this.duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getRutaImagen() {
		return this.rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public int getMaxjugadores() {
		return this.maxjugadores;
	}

	public void setMaxjugadores(int maxjugadores) {
		this.maxjugadores = maxjugadores;
	}

	public boolean isPasaporte() {
		return this.pasaporte;
	}

	public void setPasaporte(boolean pasaporte) {
		this.pasaporte = pasaporte;
	}

	public String getBgg() {
		return this.bgg;
	}

	public void setBgg(String bgg) {
		this.bgg = bgg;
	}

	public List<Juego> getJuegos() {
		return this.juegos;
	}

	public void setJuegos(List<Juego> juegos) {
		this.juegos = juegos;
	}

	public boolean isInfantil() {
		return this.infantil;
	}

	public void setInfantil(boolean infantil) {
		this.infantil = infantil;
	}

	public int getEdad() {
		return this.edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Editorial getEditorial() {
		return this.editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}
}