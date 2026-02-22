package dto.enums;

public enum TipoJeseta {
	ADMINISTRACION("https://thumbs.gfycat.com/DarlingAcceptableHapuku-max-1mb.gif"), JUEGO(
			"https://art.pixilart.com/e27b3c9f2800bd0.gif"), TIENDA(
					"https://media.tenor.com/Kl02bHunplcAAAAM/svino-stikers-money.gif"), LOGRO(
							"https://usagif.com/wp-content/uploads/gifs/coin-flip-59.gif"), INFANTIL(
									"https://previews.123rf.com/images/djvstock/djvstock1803/djvstock180303707/96984297-icono-de-moneda-dinero-lindo-sobre-ilustraci%C3%B3n-de-vector-de-dise%C3%B1o-colorido-fondo-blanco.jpg");

	private final String cssClass;

	private TipoJeseta(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getCSSClass() {
		return this.cssClass;
	}
}