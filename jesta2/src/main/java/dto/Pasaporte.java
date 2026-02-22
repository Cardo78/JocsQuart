package dto;

public class Pasaporte extends JuegoBase {
	private boolean jugado;

	public Pasaporte(JuegoBase juego) {
		this.id = juego.id;
		this.nombre = juego.nombre;
		this.duracion = juego.duracion;
		this.rutaImagen = juego.rutaImagen;
		this.maxjugadores = juego.maxjugadores;
		this.pasaporte = juego.pasaporte;
		this.bgg = juego.bgg;
		this.juegos = juego.juegos;
		this.infantil = juego.infantil;
		this.edad = juego.edad;
		this.editorial = juego.editorial;
	}

	public boolean isJugado() {
		return this.jugado;
	}

	public void setJugado(boolean jugado) {
		this.jugado = jugado;
	}
}