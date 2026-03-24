package DatosDB;

import dto.Alquiler;
import interfazDB.IAlquilerDAO;
import interfazDB.IJuegoDAO;
import interfazDB.IJugadorDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AlquilerDAO implements IAlquilerDAO {
	private final String INSERTALQUILER = "INSERT INTO gesjornadas.alquiler (juego_id, jugador_id, dfechaini) VALUES (?,?,CURRENT_TIMESTAMP)";
	private final String UPDATEALQUILER = "UPDATE gesjornadas.alquiler SET dfechaFin = CURRENT_TIMESTAMP WHERE juego_id = ? AND dfechaFin IS NULL";
	private final String UPDATEPASAPORTE = "UPDATE gesjornadas.alquiler SET dfechaFin = CURRENT_TIMESTAMP WHERE jugador_id = ? AND dfechaFin IS NULL";
	private final String ALQUILERJUGADOR = "SELECT alquiler.* FROM gesjornadas.alquiler JOIN gesjornadas.jugador ON alquiler.jugador_id = jugador.id WHERE cpulsera = ?";
	private final String DELALQUILER = "DELETE FROM gesjornadas.alquiler WHERE juego_id = ? and dfechafin is null";
	private final String ALQUILERES = "SELECT * FROM gesjornadas.alquiler";
	private final String ALQUILERJUEGO = "SELECT alquiler.* FROM gesjornadas.alquiler JOIN gesjornadas.juego ON alquiler.juego_id = juego.id WHERE ccodigo = ?";
	private final String GETEANALQUILER = "SELECT ccodigo FROM gesjornadas.alquiler JOIN gesjornadas.juego on alquiler.juego_id = juego.id WHERE jugador_id = ? AND dfechaFin IS NULL";
	private final String GETALQUILER = "SELECT * FROM gesjornadas.alquiler where juego_id = ?";
	private DBBasic connect;
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public int setAlquiler(int idjuego, List<String> jugadores) throws Exception {
		DBBasic connect = null;
		int resultado = -1;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"INSERT INTO gesjornadas.alquiler (juego_id, jugador_id, dfechaini) VALUES (?,?,CURRENT_TIMESTAMP)",
						1);

				for (Iterator var7 = jugadores.iterator(); var7.hasNext(); ++resultado) {
					String s = (String) var7.next();
					stm.setInt(1, idjuego);
					stm.setInt(2, Integer.parseInt(s));
					stm.addBatch();
				}

				int[] res = stm.executeBatch();
				resultado = res.length;
				if (resultado > 0) {
					IJuegoDAO juegoDAO = connect.getJuegoDAO();
					juegoDAO.setEstadoJuego(idjuego, true);
				}
			}
		} catch (SQLException var12) {
			throw var12;
		} catch (Exception var13) {
			throw var13;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return resultado;
	}

	public int endAlquiler(int idjuego) throws Exception {
		DBBasic connect = null;
		int resultado = -1;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"UPDATE gesjornadas.alquiler SET dfechaFin = CURRENT_TIMESTAMP WHERE juego_id = ? AND dfechaFin IS NULL");
				stm.setInt(1, idjuego);
				resultado = stm.executeUpdate();
				IJuegoDAO juegoDAO = connect.getJuegoDAO();
				juegoDAO.setEstadoJuego(idjuego, false);
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

	public boolean endAlquilerJugador(int idjugador) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"UPDATE gesjornadas.alquiler SET dfechaFin = CURRENT_TIMESTAMP WHERE jugador_id = ? AND dfechaFin IS NULL");
				stm.setInt(1, idjugador);
				stm.executeUpdate();
				resultado = true;
			}
		} catch (SQLException var9) {
			throw var9;
		} catch (Exception var10) {
			throw var10;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return resultado;
	}

	public List<Alquiler> getAlquilerJuego(String EAN13, boolean finalizados) throws Exception {
		List<Alquiler> alquileres = new ArrayList();
		this.connect = null;

		try {
			this.connect = new DBBasic();
			IJuegoDAO juegoDAO = this.connect.getJuegoDAO();
			IJugadorDAO jugadorDAO = this.connect.getJugadorDAO();
			if (this.connect.Conectar()) {
				String query = "SELECT alquiler.* FROM gesjornadas.alquiler JOIN gesjornadas.juego ON alquiler.juego_id = juego.id WHERE ccodigo = ?";
				if (!finalizados) {
					query = query + " AND dfechafin is null";
				}

				PreparedStatement stm = this.connect.conexion.prepareStatement(query);
				stm.setString(1, EAN13);

				ResultSet rs;
				Alquiler aux;
				for (rs = stm.executeQuery(); rs.next(); alquileres.add(aux)) {
					aux = new Alquiler();
					aux.setJuego(juegoDAO.getJuego(rs.getInt("juego_id")));
					aux.setJugador(jugadorDAO.getJugador(rs.getInt("jugador_id")));
					Timestamp timestamp = rs.getTimestamp("dfechaini");
					aux.setIni(new Date(timestamp.getTime()));
					if (rs.getDate("dfechafin") != null) {
						timestamp = rs.getTimestamp("dfechafin");
						aux.setFin(new Date(timestamp.getTime()));
					}
				}

				rs.close();
			}
		} catch (SQLException var15) {
			throw var15;
		} catch (Exception var16) {
			throw var16;
		} finally {
			if (this.connect != null) {
				this.connect.desconectar();
			}

		}

		return alquileres;
	}

	public List<Alquiler> getAlquilerJugador(String pulsera, boolean finalizados) throws Exception {
		List<Alquiler> alquileres = new ArrayList();
		this.connect = null;

		try {
			this.connect = new DBBasic();
			IJuegoDAO juegoDAO = this.connect.getJuegoDAO();
			IJugadorDAO jugadorDAO = this.connect.getJugadorDAO();
			if (this.connect.Conectar()) {
				String query = "SELECT alquiler.* FROM gesjornadas.alquiler JOIN gesjornadas.jugador ON alquiler.jugador_id = jugador.id WHERE cpulsera = ?";
				if (!finalizados) {
					query = query + " AND dfechafin is null";
				}

				PreparedStatement stm = this.connect.conexion.prepareStatement(query);
				stm.setString(1, pulsera);

				ResultSet rs;
				Alquiler aux;
				for (rs = stm.executeQuery(); rs.next(); alquileres.add(aux)) {
					aux = new Alquiler();
					aux.setJuego(juegoDAO.getJuego(rs.getInt("juego_id")));
					aux.setJugador(jugadorDAO.getJugador(rs.getInt("jugador_id")));
					Timestamp timestamp = rs.getTimestamp("dfechaini");
					aux.setIni(new Date(timestamp.getTime()));
					if (rs.getDate("dfechafin") != null) {
						timestamp = rs.getTimestamp("dfechafin");
						aux.setFin(new Date(timestamp.getTime()));
					}
				}

				rs.close();
			}
		} catch (SQLException var15) {
			throw var15;
		} catch (Exception var16) {
			throw var16;
		} finally {
			if (this.connect != null) {
				this.connect.desconectar();
			}

		}

		return alquileres;
	}

	public List<Alquiler> getAlquileres(boolean finalizados) throws Exception {
		List<Alquiler> alquileres = new ArrayList();
		this.connect = null;

		try {
			this.connect = new DBBasic();
			IJuegoDAO juegoDAO = this.connect.getJuegoDAO();
			IJugadorDAO jugadorDAO = this.connect.getJugadorDAO();
			if (this.connect.Conectar()) {
				String query = "SELECT * FROM gesjornadas.alquiler";
				if (!finalizados) {
					query = query + " AND dfechafin is null";
				}

				PreparedStatement stm = this.connect.conexion.prepareStatement(query);

				ResultSet rs;
				Alquiler aux;
				for (rs = stm.executeQuery(); rs.next(); alquileres.add(aux)) {
					aux = new Alquiler();
					aux.setJuego(juegoDAO.getJuego(rs.getInt("juego_id")));
					aux.setJugador(jugadorDAO.getJugador(rs.getInt("jugador_id")));
					Timestamp timestamp = rs.getTimestamp("dfechaini");
					aux.setIni(new Date(timestamp.getTime()));
					if (rs.getDate("dfechafin") != null) {
						timestamp = rs.getTimestamp("dfechafin");
						aux.setFin(new Date(timestamp.getTime()));
					}
				}

				rs.close();
			}
		} catch (SQLException var14) {
			throw var14;
		} catch (Exception var15) {
			throw var15;
		} finally {
			if (this.connect != null) {
				this.connect.desconectar();
			}

		}

		return alquileres;
	}

	public String forzarEndAlquilerJugador(int idjugador) throws Exception {
		String ean = "";

		try {
			this.connect = new DBBasic();
			if (this.connect.Conectar()) {
				PreparedStatement stm = this.connect.conexion.prepareStatement(
						"SELECT ccodigo FROM gesjornadas.alquiler JOIN gesjornadas.juego on alquiler.juego_id = juego.id WHERE jugador_id = ? AND dfechaFin IS NULL");
				stm.setInt(1, idjugador);

				ResultSet rs;
				for (rs = stm.executeQuery(); rs.next(); ean = rs.getString("ccodigo")) {
				}

				if (!ean.equals("")) {
					this.endAlquilerJugador(idjugador);
				}

				rs.close();
			}
		} catch (SQLException var9) {
			throw var9;
		} catch (Exception var10) {
			throw var10;
		} finally {
			if (this.connect != null) {
				this.connect.desconectar();
			}

		}

		return ean;
	}

	public boolean eliminarAlquiler(int idjuego) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("DELETE FROM gesjornadas.alquiler WHERE juego_id = ? and dfechafin is null");
				stm.setInt(1, idjuego);
				stm.executeUpdate();
				resultado = true;
				IJuegoDAO juegoDAO = connect.getJuegoDAO();
				juegoDAO.setEstadoJuego(idjuego, false);
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

	public Alquiler getAlquiler(int idjuego) throws Exception {
		Alquiler alquiler = new Alquiler();
		this.connect = null;

		try {
			this.connect = new DBBasic();
			IJuegoDAO juegoDAO = this.connect.getJuegoDAO();
			IJugadorDAO jugadorDAO = this.connect.getJugadorDAO();
			if (this.connect.Conectar()) {
				PreparedStatement stm = this.connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.alquiler where juego_id = ?");
				stm.setInt(1, idjuego);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					alquiler.setJuego(juegoDAO.getJuego(rs.getInt("juego_id")));
					alquiler.setJugador(jugadorDAO.getJugador(rs.getInt("jugador_id")));
					Timestamp timestamp = rs.getTimestamp("dfechaini");
					alquiler.setIni(new Date(timestamp.getTime()));
					if (rs.getDate("dfechafin") != null) {
						timestamp = rs.getTimestamp("dfechafin");
						alquiler.setFin(new Date(timestamp.getTime()));
					}
				}

				rs.close();
			}
		} catch (SQLException var12) {
			throw var12;
		} catch (Exception var13) {
			throw var13;
		} finally {
			if (this.connect != null) {
				this.connect.desconectar();
			}

		}

		return alquiler;
	}
}