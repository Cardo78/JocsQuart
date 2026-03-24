package DatosDB;

import dto.Jugador;
import interfazDB.IJugadorDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JugadorDAO implements IJugadorDAO {
	private final String JUGADOR = "SELECT * FROM gesjornadas.jugador WHERE id = ?";
	private final String JUGADORPULSERA = "SELECT * FROM gesjornadas.jugador WHERE cpulsera = ?";
	private final String JUGADORPARAMETROS = "SELECT * FROM gesjornadas.jugador";
	private final String INSERTJUGADOR = "INSERT INTO gesjornadas.jugador (cpulsera, cnombre, capellidos, cemail, ctelefono, cdni, icp, binfantil) VALUES (?,?,?,?,?,?,?,?)";
	private final String UPDATEJUGADOR = "UPDATE gesjornadas.jugador SET cpulsera = ?, cnombre = ?, capellidos = ?, cemail = ?, ctelefono = ?, cdni = ?, icp = ?, binfantil = ? WHERE id = ?";
	private final String PULSERA = "cpulsera";
	private final String NOMBRE = "cnombre";
	private final String APELLIDOS = "capellidos";
	private final String MAIL = "cemail";
	private final String TELEFONO = "ctelefono";
	private final String DNI = "cdni";
	private final String CP = "icp";
	private final String INFANTIL = "binfantil";

	public Jugador getJugador(int id) throws Exception {
		Jugador player = new Jugador();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.jugador WHERE id = ?");
				stm.setInt(1, id);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					player.setPulsera(rs.getString("cpulsera"));
					player.setNombre(rs.getString("cnombre"));
					player.setApellidos(rs.getString("capellidos"));
					player.setMail(rs.getString("cemail"));
					player.setTelefono(rs.getString("ctelefono"));
					player.setDni(rs.getString("cdni"));
					player.setCp(rs.getInt("icp"));
					player.setInfantil(rs.getBoolean("binfantil"));
					player.setId(id);
				}

				rs.close();
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

		return player;
	}

	public Jugador getJugadorPulsera(String pulsera) throws Exception {
		Jugador player = new Jugador();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.jugador WHERE cpulsera = ?");
				stm.setString(1, pulsera);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					player.setId(rs.getInt("id"));
					player.setPulsera(rs.getString("cpulsera"));
					player.setNombre(rs.getString("cnombre"));
					player.setApellidos(rs.getString("capellidos"));
					player.setMail(rs.getString("cemail"));
					player.setTelefono(rs.getString("ctelefono"));
					player.setDni(rs.getString("cdni"));
					player.setCp(rs.getInt("icp"));
					player.setInfantil(rs.getBoolean("binfantil"));
				}

				rs.close();
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

		return player;
	}

	public List<Jugador> getJugadorParametros(String nombre, String apellidos, String dni) throws Exception {
		List<Jugador> resultado = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				List<String> campos = new ArrayList();
				if (!nombre.equals("")) {
					campos.add(" cnombre LIKE '%" + nombre + "%'");
				}

				if (!apellidos.equals("")) {
					campos.add(" capellidos LIKE '%" + apellidos + "%'");
				}

				if (!dni.equals("")) {
					campos.add(" cdni LIKE '%" + dni + "%'");
				}

				int i = 1;
				String comando = "SELECT * FROM gesjornadas.jugador";
				if (campos.size() >= i) {
					comando = comando + " WHERE";
				}

				for (Iterator var10 = campos.iterator(); var10.hasNext(); ++i) {
					String s = (String) var10.next();
					comando = comando + s;
					if (i < campos.size()) {
						comando = comando + " AND";
					}
				}

				PreparedStatement stm = connect.conexion.prepareStatement(comando);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					Jugador aux = new Jugador();
					aux.setId(rs.getInt("id"));
					aux.setPulsera(rs.getString("cpulsera"));
					aux.setNombre(rs.getString("cnombre"));
					aux.setApellidos(rs.getString("capellidos"));
					aux.setMail(rs.getString("cemail"));
					aux.setTelefono(rs.getString("ctelefono"));
					aux.setDni(rs.getString("cdni"));
					aux.setCp(rs.getInt("icp"));
					aux.setInfantil(rs.getBoolean("binfantil"));
					resultado.add(aux);
				}

				rs.close();
			}
		} catch (SQLException var16) {
			throw var16;
		} catch (Exception var17) {
			throw var17;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return resultado;
	}

	public int insertJugador(Jugador player) throws Exception {
		DBBasic connect = null;
		int id = -1;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"INSERT INTO gesjornadas.jugador (cpulsera, cnombre, capellidos, cemail, ctelefono, cdni, icp, binfantil) VALUES (?,?,?,?,?,?,?,?)",
						1);
				stm.setString(1, player.getPulsera());
				stm.setString(2, player.getNombre());
				stm.setString(3, player.getApellidos());
				stm.setString(4, player.getMail());
				stm.setString(5, player.getTelefono());
				stm.setString(6, player.getDni());
				stm.setInt(7, player.getCp());
				stm.setBoolean(8, player.isInfantil());
				stm.executeUpdate();
				ResultSet rs = stm.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}

				rs.close();
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

		return id;
	}

	public boolean setJugador(Jugador player) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"UPDATE gesjornadas.jugador SET cpulsera = ?, cnombre = ?, capellidos = ?, cemail = ?, ctelefono = ?, cdni = ?, icp = ?, binfantil = ? WHERE id = ?");
				stm.setString(1, player.getPulsera());
				stm.setString(2, player.getNombre());
				stm.setString(3, player.getApellidos());
				stm.setString(4, player.getMail());
				stm.setString(5, player.getTelefono());
				stm.setString(6, player.getDni());
				stm.setInt(7, player.getCp());
				stm.setBoolean(8, player.isInfantil());
				stm.setInt(9, player.getId());
				int n = stm.executeUpdate();
				if (n > 0) {
					resultado = true;
				}
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
}