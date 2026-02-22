package dto;

public class ConfigGes {
	private String urlLogo;
	private String urlLogopeq;
	private String urlIndex;
	private String nombre;

	public ConfigGes(String urlLogo, String urlLogopeq, String urlIndex, String nombre) {
		this.urlLogo = urlLogo;
		this.urlLogopeq = urlLogopeq;
		this.urlIndex = urlIndex;
		this.nombre = nombre;
	}

	public ConfigGes() {
	}

	public String getUrlLogo() {
		return this.urlLogo;
	}

	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}

	public String getUrlLogopeq() {
		return this.urlLogopeq;
	}

	public void setUrlLogopeq(String urlLogopeq) {
		this.urlLogopeq = urlLogopeq;
	}

	public String getUrlIndex() {
		return this.urlIndex;
	}

	public void setUrlIndex(String urlIndex) {
		this.urlIndex = urlIndex;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}