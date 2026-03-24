package DatosDB;

import dto.Jeseta;
import interfazDB.IMonederoDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MonederoDAO implements IMonederoDAO {
	private final String INSERTJESETA = "INSERT INTO gesjornadas.monedero (cdescripcion, busado, ctipo, jugador_id, usuario_id, dfechahora) VALUES (?,?,?,?,?,CURRENT_TIMESTAMP)";
	private final String SETJESETA = "UPDATE gesjornadas.monedero SET cdescripcion = ?, busado = ?, ctipo = ?, jugador_id = ?, usuario_id = ?, dfechahora = CURRENT_TIMESTAMP WHERE idjeseta = ?";
	private final String DELJESETA = "DELETE FROM gesjornadas.monedero WHERE idjeseta = ?";
	private final String GETJESETAS = "SELECT * FROM gesjornadas.monedero WHERE jugador_id = ?";
	private final String GETJESETASUSUARIO = "SELECT * FROM gesjornadas.monedero WHERE usuario_id = ?";
	private final String GETJESETA = "SELECT * FROM gesjornadas.monedero WHERE idjeseta = ?";
	private final String GETTOTAL = "SELECT COUNT(*) AS TOTAL FROM gesjornadas.monedero WHERE jugador_id = ?";
	private final String GETTOTALUSUARIO = "SELECT COUNT(*) AS TOTAL FROM gesjornadas.monedero WHERE usuario_id = ?";
	private final String GETSORTEO = "SELECT * FROM gesjornadas.monedero WHERE jugador_id IS NOT null AND busado = ? order by dfechahora DESC";
	private final String JESETA = "idjeseta";
	private final String DESCRIPCION = "cdescripcion";
	private final String USADO = "busado";
	private final String TIPO = "ctipo";
	private final String JUGADOR = "jugador_id";
	private final String USUARIO = "usuario_id";
	private final String FECHA = "dfechahora";

	public int insertJeseta(Jeseta jeseta) throws Exception {
		DBBasic connect = null;
		int id = -1;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"INSERT INTO gesjornadas.monedero (cdescripcion, busado, ctipo, jugador_id, usuario_id, dfechahora) VALUES (?,?,?,?,?,CURRENT_TIMESTAMP)",
						1);
				stm.setString(1, jeseta.getDescripcion());
				stm.setBoolean(2, jeseta.isUsado());
				stm.setString(3, jeseta.getTipo());
				if (jeseta.getIdJugador() == -1) {
					stm.setNull(4, 4);
				} else {
					stm.setInt(4, jeseta.getIdJugador());
				}

				if (jeseta.getIdUsuario() == -1) {
					stm.setNull(5, 4);
				} else {
					stm.setInt(5, jeseta.getIdUsuario());
				}

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

	public boolean setJeseta(Jeseta jeseta) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"UPDATE gesjornadas.monedero SET cdescripcion = ?, busado = ?, ctipo = ?, jugador_id = ?, usuario_id = ?, dfechahora = CURRENT_TIMESTAMP WHERE idjeseta = ?");
				stm.setString(1, jeseta.getDescripcion());
				stm.setBoolean(2, jeseta.isUsado());
				stm.setString(3, jeseta.getTipo());
				if (jeseta.getIdJugador() == -1) {
					stm.setNull(4, 4);
				} else {
					stm.setInt(4, jeseta.getIdJugador());
				}

				if (jeseta.getIdUsuario() == -1) {
					stm.setNull(5, 4);
				} else {
					stm.setInt(5, jeseta.getIdUsuario());
				}

				stm.setInt(6, jeseta.getIdJeseta());
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

	public boolean delJeseta(Jeseta jeseta) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("DELETE FROM gesjornadas.monedero WHERE idjeseta = ?");
				stm.setInt(1, jeseta.getIdJeseta());
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

	public List<Jeseta> getJesetas(int idjugador) throws Exception {
		List<Jeseta> jesetas = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.monedero WHERE jugador_id = ?");
				stm.setInt(1, idjugador);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					Jeseta jeseta = new Jeseta();
					jeseta.setIdJeseta(rs.getInt("idjeseta"));
					jeseta.setDescripcion(rs.getString("cdescripcion"));
					jeseta.setUsado(rs.getBoolean("busado"));
					jeseta.setTipo(rs.getString("ctipo"));
					jeseta.setIdJugador(rs.getInt("jugador_id"));
					if (rs.wasNull()) {
						jeseta.setIdJugador(-1);
					}

					jeseta.setIdUsuario(rs.getInt("usuario_id"));
					if (rs.wasNull()) {
						jeseta.setIdUsuario(-1);
					}

					Timestamp timestamp = rs.getTimestamp("dfechahora");
					jeseta.setFecha(new Date(timestamp.getTime()));
					jesetas.add(jeseta);
				}

				rs.close();
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

		return jesetas;
	}

	public int getTotalJesetas(int idjugador) throws Exception {
		int total = 0;
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT COUNT(*) AS TOTAL FROM gesjornadas.monedero WHERE jugador_id = ?");
				stm.setInt(1, idjugador);

				ResultSet rs;
				for (rs = stm.executeQuery(); rs.next(); total = rs.getInt("TOTAL")) {
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

		return total;
	}

	public Jeseta getJeseta(int idjeseta) throws Exception {
		Jeseta jeseta = new Jeseta();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.monedero WHERE idjeseta = ?");
				stm.setInt(1, idjeseta);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					jeseta.setIdJeseta(rs.getInt("idjeseta"));
					jeseta.setDescripcion(rs.getString("cdescripcion"));
					jeseta.setUsado(rs.getBoolean("busado"));
					jeseta.setTipo(rs.getString("ctipo"));
					jeseta.setIdJugador(rs.getInt("jugador_id"));
					if (rs.wasNull()) {
						jeseta.setIdJugador(-1);
					}

					jeseta.setIdUsuario(rs.getInt("usuario_id"));
					if (rs.wasNull()) {
						jeseta.setIdUsuario(-1);
					}

					Timestamp timestamp = rs.getTimestamp("dfechahora");
					jeseta.setFecha(new Date(timestamp.getTime()));
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

		return jeseta;
	}

	public int getTotalJesetasUsuario(int idusuario) throws Exception {
		int total = 0;
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT COUNT(*) AS TOTAL FROM gesjornadas.monedero WHERE usuario_id = ?");
				stm.setInt(1, idusuario);

				ResultSet rs;
				for (rs = stm.executeQuery(); rs.next(); total = rs.getInt("TOTAL")) {
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

		return total;
	}

	public List<Jeseta> getJesetasUsuario(int idusuario) throws Exception {
		List<Jeseta> jesetas = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.monedero WHERE usuario_id = ?");
				stm.setInt(1, idusuario);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					Jeseta jeseta = new Jeseta();
					jeseta.setIdJeseta(rs.getInt("idjeseta"));
					jeseta.setDescripcion(rs.getString("cdescripcion"));
					jeseta.setUsado(rs.getBoolean("busado"));
					jeseta.setTipo(rs.getString("ctipo"));
					jeseta.setIdJugador(rs.getInt("jugador_id"));
					if (rs.wasNull()) {
						jeseta.setIdJugador(-1);
					}

					jeseta.setIdUsuario(rs.getInt("usuario_id"));
					if (rs.wasNull()) {
						jeseta.setIdUsuario(-1);
					}

					Timestamp timestamp = rs.getTimestamp("dfechahora");
					jeseta.setFecha(new Date(timestamp.getTime()));
					jesetas.add(jeseta);
				}

				rs.close();
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

		return jesetas;
	}

	public List<Jeseta> getSorteo(boolean premiado) throws Exception {
		List<Jeseta> jesetas = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"SELECT * FROM gesjornadas.monedero WHERE jugador_id IS NOT null AND busado = ? order by dfechahora DESC");
				stm.setBoolean(1, premiado);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					Jeseta jeseta = new Jeseta();
					jeseta.setIdJeseta(rs.getInt("idjeseta"));
					jeseta.setDescripcion(rs.getString("cdescripcion"));
					jeseta.setUsado(rs.getBoolean("busado"));
					jeseta.setTipo(rs.getString("ctipo"));
					jeseta.setIdJugador(rs.getInt("jugador_id"));
					jeseta.setIdUsuario(rs.getInt("usuario_id"));
					if (rs.wasNull()) {
						jeseta.setIdUsuario(-1);
					}

					Timestamp timestamp = rs.getTimestamp("dfechahora");
					jeseta.setFecha(new Date(timestamp.getTime()));
					jesetas.add(jeseta);
				}

				rs.close();
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

		return jesetas;
	}
}