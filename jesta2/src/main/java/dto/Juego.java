package dto;

public class Juego extends JuegoBase {
	private int id;
	private boolean entregado;
	private String ean13;
	private Ubicacion ubicacion;
	private boolean alquilado;

	public Juego(int id, boolean entregado, String ean13, Ubicacion ubicacion) {
		this.id = id;
		this.entregado = entregado;
		this.ean13 = ean13;
		this.ubicacion = ubicacion;
	}

	public Juego() {
		this.id = -1;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isEntregado() {
		return this.entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	public String getEan13() {
		return this.ean13;
	}

	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}

	public Ubicacion getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public boolean isAlquilado() {
		return this.alquilado;
	}

	public void setAlquilado(boolean alquilado) {
		this.alquilado = alquilado;
	}
}