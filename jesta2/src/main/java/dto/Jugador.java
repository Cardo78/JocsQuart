package dto;

public class Jugador {
	private int id;
	private String pulsera;
	private String nombre;
	private String apellidos;
	private String mail;
	private String telefono;
	private String dni;
	private int cp;
	private boolean infantil;

	public Jugador() {
		this.id = -1;
	}

	public Jugador(int id, String pulsera, String nombre, String apellidos, String mail, String telefono, String dni,
			int cp, boolean infantil) {
		this.id = id;
		this.pulsera = pulsera;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.mail = mail;
		this.telefono = telefono;
		this.dni = dni;
		this.cp = cp;
		this.infantil = infantil;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPulsera() {
		return this.pulsera;
	}

	public void setPulsera(String pulsera) {
		this.pulsera = pulsera;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getCp() {
		return this.cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public boolean isInfantil() {
		return this.infantil;
	}

	public void setInfantil(boolean infantil) {
		this.infantil = infantil;
	}
}