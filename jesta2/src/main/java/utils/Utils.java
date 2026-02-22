package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
	public static String[] dias = new String[]{"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes",
			"S�bado"};

	public static String parseDate(Calendar date) {
		if (date == null) {
			return "-";
		} else {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				dateFormat.setTimeZone(date.getTimeZone());
				return dateFormat.format(date.getTime());
			} catch (Exception var2) {
				return "";
			}
		}
	}

	public static Calendar parseDateString(String date) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date totalDate = dateFormat.parse(date);
			cal.setTime(totalDate);
		} catch (ParseException var4) {
		}

		return cal;
	}

	public String obtieneFecha(Calendar data) {
		if (data != null) {
			String horaIni = dias[data.get(7) - 1] + " " + data.get(5) + ", " + data.get(11) + ":"
					+ String.format("%02d", data.get(12));
			return horaIni;
		} else {
			return "";
		}
	}
}