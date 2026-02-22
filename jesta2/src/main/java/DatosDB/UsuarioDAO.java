package DatosDB;

import dto.Usuario;
import interfazDB.IUsuarioDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuarioDAO {
	private final String USUARIO = "SELECT * FROM gesjornadas.usuario WHERE idusuario = ?";
	private final String UPDATEUSUARIO = "UPDATE gesjornadas.usuario SET cuser = ?, cpass = ?, tipo = ?, ecolor = ?, jugador_id = ? WHERE idusuario = ?";
	private final String INSERTUSUARIO = "INSERT INTO gesjornadas.usuario (cuser, cpass, tipo, ecolor, jugador_id) VALUES (?,?,?,?,?)";
	private final String USUARIONAME = "SELECT * FROM gesjornadas.usuario WHERE cuser = ?";
	private final String USUARIOJUGADOR = "SELECT * FROM gesjornadas.usuario WHERE jugador_id = ?";
	private final String USUARIOS = "SELECT * FROM gesjornadas.usuario WHERE tipo = 'NORMAL'";
	private final String ID = "idusuario";
	private final String USER = "cuser";
	private final String PASS = "cpass";
	private final String COLOR = "ecolor";
	private final String TIPO = "tipo";
	private final String JUGADOR = "jugador_id";

	public Usuario getUsuario(int id) throws Exception {
		Usuario user = new Usuario();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.usuario WHERE idusuario = ?");
				stm.setInt(1, id);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					user.setId(rs.getInt("idusuario"));
					user.setUser(rs.getString("cuser"));
					user.setPass(rs.getString("cpass"));
					user.setTipo(rs.getString("tipo"));
					user.setColor(rs.getString("ecolor"));
					user.setIdJugador(rs.getInt("jugador_id"));
					if (rs.wasNull()) {
						user.setIdJugador(-1);
					}
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

		return user;
	}

	public boolean setUsuario(Usuario user) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"UPDATE gesjornadas.usuario SET cuser = ?, cpass = ?, tipo = ?, ecolor = ?, jugador_id = ? WHERE idusuario = ?");
				stm.setString(1, user.getUser());
				stm.setString(2, user.getPass());
				stm.setString(3, user.getTipo());
				stm.setString(4, user.getColorTipo().toString());
				if (user.getIdJugador() == -1) {
					stm.setNull(5, 4);
				} else {
					stm.setInt(5, user.getIdJugador());
				}

				stm.setInt(6, user.getId());
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

	public int insertUsuario(Usuario user) throws Exception {
		DBBasic connect = null;
		int id = -1;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"INSERT INTO gesjornadas.usuario (cuser, cpass, tipo, ecolor, jugador_id) VALUES (?,?,?,?,?)",
						1);
				stm.setString(1, user.getUser());
				stm.setString(2, user.getPass());
				stm.setString(3, user.getTipo());
				stm.setString(4, user.getColorTipo().toString());
				if (user.getIdJugador() == -1) {
					stm.setNull(5, 4);
				} else {
					stm.setInt(5, user.getIdJugador());
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

	public Usuario getUsuarioName(String user) throws Exception {
		Usuario usuario = new Usuario();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.usuario WHERE cuser = ?");
				stm.setString(1, user);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					usuario.setId(rs.getInt("idusuario"));
					usuario.setUser(rs.getString("cuser"));
					usuario.setPass(rs.getString("cpass"));
					usuario.setTipo(rs.getString("tipo"));
					usuario.setColor(rs.getString("ecolor"));
					usuario.setIdJugador(rs.getInt("jugador_id"));
					if (rs.wasNull()) {
						usuario.setIdJugador(-1);
					}
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

		return usuario;
	}

	public Usuario getUsuarioJugador(int idJugador) throws Exception {
		Usuario usuario = new Usuario();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.usuario WHERE jugador_id = ?");
				stm.setInt(1, idJugador);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					usuario.setId(rs.getInt("idusuario"));
					usuario.setUser(rs.getString("cuser"));
					usuario.setPass(rs.getString("cpass"));
					usuario.setTipo(rs.getString("tipo"));
					usuario.setColor(rs.getString("ecolor"));
					usuario.setIdJugador(rs.getInt("jugador_id"));
					if (rs.wasNull()) {
						usuario.setIdJugador(-1);
					}
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

		return usuario;
	}

	public List<Usuario> getUsuarios() throws Exception {
		List<Usuario> usuarios = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.usuario WHERE tipo = 'NORMAL'");

				ResultSet rs;
				Usuario usuario;
				for (rs = stm.executeQuery(); rs.next(); usuarios.add(usuario)) {
					usuario = new Usuario();
					usuario.setId(rs.getInt("idusuario"));
					usuario.setUser(rs.getString("cuser"));
					usuario.setPass(rs.getString("cpass"));
					usuario.setTipo(rs.getString("tipo"));
					usuario.setColor(rs.getString("ecolor"));
					usuario.setIdJugador(rs.getInt("jugador_id"));
					if (rs.wasNull()) {
						usuario.setIdJugador(-1);
					}
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

		return usuarios;
	}
}