package DatosDB;

import interfazDB.IPasaporteDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasaporteDAO implements IPasaporteDAO {
	private final String DELPASAPORTE = "DELETE FROM gesjornadas.pasaporte WHERE juegoBase_id = ? AND jugador_id = ?";
	private final String INSERTPASAPORTE = "INSERT INTO gesjornadas.pasaporte (juegoBase_id, jugador_id) VALUES (?,?)";
	private final String GETPASAPORTE = "SELECT COUNT(*) as TOTAL FROM gesjornadas.pasaporte JOIN gesjornadas.jugador on pasaporte.jugador_id = jugador.id WHERE juegoBase_id = ? AND cpulsera = ?";
	private final String GETPASAPORTEJUGADOR = "SELECT COUNT(*) as TOTAL FROM gesjornadas.pasaporte WHERE juegoBase_id = ? and jugador_id = ?";

	public boolean setPasaporte(int idjuego, int idjugador) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("INSERT INTO gesjornadas.pasaporte (juegoBase_id, jugador_id) VALUES (?,?)");
				stm.setInt(1, idjuego);
				stm.setInt(2, idjugador);
				stm.executeUpdate();
			}
		} catch (SQLException var10) {
			throw var10;
		} catch (Exception var11) {
			throw var11;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return resultado;
	}

	public boolean delPasaporte(int idjuego, int idjugador) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"DELETE FROM gesjornadas.pasaporte WHERE juegoBase_id = ? AND jugador_id = ?");
				stm.setInt(1, idjuego);
				stm.setInt(2, idjugador);
				stm.executeUpdate();
				resultado = true;
			}
		} catch (SQLException var10) {
			throw var10;
		} catch (Exception var11) {
			throw var11;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return resultado;
	}

	public boolean isPasaporte(int idjuego, String pulsera) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"SELECT COUNT(*) as TOTAL FROM gesjornadas.pasaporte JOIN gesjornadas.jugador on pasaporte.jugador_id = jugador.id WHERE juegoBase_id = ? AND cpulsera = ?");
				stm.setInt(1, idjuego);
				stm.setString(2, pulsera);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					if (rs.getInt("TOTAL") > 0) {
						resultado = true;
					}
				}

				rs.close();
			}
		} catch (SQLException var11) {
			throw var11;
		} catch (Exception var12) {
			throw var12;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return resultado;
	}

	public boolean getPasaporte(int idjuego, int idjugador) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"SELECT COUNT(*) as TOTAL FROM gesjornadas.pasaporte WHERE juegoBase_id = ? and jugador_id = ?");
				stm.setInt(1, idjuego);
				stm.setInt(2, idjugador);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					if (rs.getInt("TOTAL") > 0) {
						resultado = true;
					}
				}

				rs.close();
			}
		} catch (SQLException var11) {
			throw var11;
		} catch (Exception var12) {
			throw var12;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return resultado;
	}
}