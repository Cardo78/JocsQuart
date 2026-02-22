package dto.enums;

public enum TipoMensaje {
	SUCCESS("bg-green-active"), INFO("bg-blue-active"), WARNING("bg-yellow-active"), ERROR("bg-red-active");

	private final String cssClass;

	private TipoMensaje(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getCSSClass() {
		return this.cssClass;
	}
}