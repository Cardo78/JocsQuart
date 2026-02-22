package dto.enums;

public enum Color {
	GREEN("skin-green"), BLUE("skin-blue"), BLACK("skin-black"), PURPLE("skin-purple"), RED("skin-red"), YELLOW(
			"skin-yellow"), GREENL("skin-green-light"), BLUEL("skin-blue-light"), BLACKL("skin-black-light"), PURPLEL(
					"skin-purple-light"), REDL("skin-red-light"), YELLOWL("skin-yellow-light");

	private final String cssClass;

	private Color(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getCSSClass() {
		return this.cssClass;
	}
}