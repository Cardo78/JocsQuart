package DatosDB;

import dto.Juego;
import dto.JuegoBase;
import dto.Ubicacion;
import interfazDB.IEditorialDAO;
import interfazDB.IJuegoDAO;
import interfazDB.IUbicacionDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JuegoDAO implements IJuegoDAO {
	private final String INSERTJUEGOBASE = "INSERT INTO gesjornadas.juegobase (cnombre, iduracion, crutaimagen, imaximo, cbgg, bpasaporte, binfantil, iedad, editorial_id) VALUES (?,?,?,?,?,?,?,?,?)";
	private final String INSERTJUEGO = "INSERT INTO gesjornadas.juego (ccodigo, idubicacion, idjuegobase) VALUES (?,?,?)";
	private final String UPDATEJUEGOBASE = "UPDATE gesjornadas.juegobase SET cnombre = ?, iduracion = ?, crutaimagen = ?, imaximo = ?, cbgg = ?, bpasaporte = ?, binfantil = ?, iedad = ?, editorial_id = ? WHERE id = ?";
	private final String UPDATEJUEGO = "UPDATE gesjornadas.juego SET ccodigo = ?, idubicacion = ?, idjuegobase = ?, bentregado = ? WHERE id = ?";
	private final String JUEGO = "SELECT * FROM gesjornadas.juegobase WHERE id = ?";
	private final String JUEGONOMBRE = "SELECT * FROM gesjornadas.juegobase WHERE cnombre LIKE ?";
	private final String PASAPORTES = "SELECT * FROM gesjornadas.juegobase WHERE bpasaporte = true and binfantil = ?";
	private final String EAN = "SELECT juego.*, ubicacion.cnombre, ubicacion.ccolor FROM gesjornadas.juego JOIN gesjornadas.ubicacion ON juego.idubicacion = ubicacion.id WHERE idjuegobase = ?";
	private final String JUEGOEAN = "SELECT * FROM gesjornadas.juego WHERE ccodigo = ? and bentregado = false";
	private final String JUEGOUBICACION = "SELECT juego.* FROM gesjornadas.juego JOIN gesjornadas.juegobase ON juego.idjuegobase = juegobase.id WHERE idubicacion = ? and bentregado = false and bpasaporte = true and binfantil = ?";
	private final String ESTADOJUEGO = "UPDATE gesjornadas.juego SET balquilado = ? WHERE id = ?";
	private final String JUEGOID = "SELECT * FROM gesjornadas.juego WHERE id = ?";

	public int insertJuego(JuegoBase juego) throws Exception {
		DBBasic connect = null;
		int id = -1;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"INSERT INTO gesjornadas.juegobase (cnombre, iduracion, crutaimagen, imaximo, cbgg, bpasaporte, binfantil, iedad, editorial_id) VALUES (?,?,?,?,?,?,?,?,?)",
						1);
				stm.setString(1, juego.getNombre());
				stm.setInt(2, juego.getDuracion());
				stm.setString(3, juego.getRutaImagen());
				stm.setInt(4, juego.getMaxjugadores());
				stm.setString(5, juego.getBgg());
				stm.setBoolean(6, juego.isPasaporte());
				stm.setBoolean(7, juego.isInfantil());
				stm.setInt(8, juego.getEdad());
				stm.setInt(9, juego.getEditorial().getId());
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

	public int insertJuego(Juego juego, int idBase) throws Exception {
		DBBasic connect = null;
		int id = -1;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"INSERT INTO gesjornadas.juego (ccodigo, idubicacion, idjuegobase) VALUES (?,?,?)", 1);
				stm.setString(1, juego.getEan13());
				stm.setInt(2, juego.getUbicacion().getId());
				stm.setInt(3, idBase);
				stm.executeUpdate();
				ResultSet rs = stm.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
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

		return id;
	}

	public boolean setJuegoBase(JuegoBase juego) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"UPDATE gesjornadas.juegobase SET cnombre = ?, iduracion = ?, crutaimagen = ?, imaximo = ?, cbgg = ?, bpasaporte = ?, binfantil = ?, iedad = ?, editorial_id = ? WHERE id = ?");
				stm.setString(1, juego.getNombre());
				stm.setInt(2, juego.getDuracion());
				stm.setString(3, juego.getRutaImagen());
				stm.setInt(4, juego.getMaxjugadores());
				stm.setString(5, juego.getBgg());
				stm.setBoolean(6, juego.isPasaporte());
				stm.setBoolean(7, juego.isInfantil());
				stm.setInt(8, juego.getEdad());
				stm.setInt(9, juego.getEditorial().getId());
				stm.setInt(10, juego.getIdBase());
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

	public boolean setJuego(Juego juego, int idBase) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"UPDATE gesjornadas.juego SET ccodigo = ?, idubicacion = ?, idjuegobase = ?, bentregado = ? WHERE id = ?");
				stm.setString(1, juego.getEan13());
				stm.setInt(2, juego.getUbicacion().getId());
				stm.setInt(3, idBase);
				stm.setBoolean(4, juego.isEntregado());
				stm.setInt(5, juego.getId());
				int n = stm.executeUpdate();
				if (n > 0) {
					resultado = true;
				}
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

	public JuegoBase getJuegoBase(int id) throws Exception {
		JuegoBase juego = new JuegoBase();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			IEditorialDAO editorialDAO = connect.getEditorialDAO();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.juegobase WHERE id = ?");
				stm.setInt(1, id);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					juego.setNombre(rs.getString("cnombre"));
					juego.setDuracion(rs.getInt("iduracion"));
					juego.setRutaImagen(rs.getString("crutaimagen"));
					juego.setMaxjugadores(rs.getInt("imaximo"));
					juego.setBgg(rs.getString("cbgg"));
					juego.setPasaporte(rs.getBoolean("bpasaporte"));
					juego.setIdBase(rs.getInt("id"));
					juego.setInfantil(rs.getBoolean("binfantil"));
					juego.setEdad(rs.getInt("iedad"));
					juego.setEditorial(editorialDAO.getEditorial(rs.getInt("editorial_id")));
					juego.setJuegos(this.getEan(juego.getIdBase(), true));
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

		return juego;
	}

	public List<JuegoBase> getJuegos(String nombre) throws Exception {
		List<JuegoBase> juegos = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			IEditorialDAO editorialDAO = connect.getEditorialDAO();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.juegobase WHERE cnombre LIKE ?");
				stm.setString(1, "%" + nombre + "%");
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					JuegoBase juego = new JuegoBase();
					juego.setNombre(rs.getString("cnombre"));
					juego.setDuracion(rs.getInt("iduracion"));
					juego.setRutaImagen(rs.getString("crutaimagen"));
					juego.setMaxjugadores(rs.getInt("imaximo"));
					juego.setBgg(rs.getString("cbgg"));
					juego.setPasaporte(rs.getBoolean("bpasaporte"));
					juego.setInfantil(rs.getBoolean("binfantil"));
					juego.setEdad(rs.getInt("iedad"));
					juego.setEditorial(editorialDAO.getEditorial(rs.getInt("editorial_id")));
					juego.setIdBase(rs.getInt("id"));
					juego.setJuegos(this.getEan(juego.getIdBase(), true));
					juegos.add(juego);
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

		return juegos;
	}

	public List<JuegoBase> getPasaportes(boolean infantil) throws Exception {
		List<JuegoBase> juegos = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			IEditorialDAO editorialDAO = connect.getEditorialDAO();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"SELECT * FROM gesjornadas.juegobase WHERE bpasaporte = true and binfantil = ?");
				stm.setBoolean(1, infantil);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					JuegoBase juego = new JuegoBase();
					juego.setNombre(rs.getString("cnombre"));
					juego.setDuracion(rs.getInt("iduracion"));
					juego.setRutaImagen(rs.getString("crutaimagen"));
					juego.setMaxjugadores(rs.getInt("imaximo"));
					juego.setBgg(rs.getString("cbgg"));
					juego.setPasaporte(rs.getBoolean("bpasaporte"));
					juego.setInfantil(rs.getBoolean("binfantil"));
					juego.setEdad(rs.getInt("iedad"));
					juego.setEditorial(editorialDAO.getEditorial(rs.getInt("editorial_id")));
					juego.setIdBase(rs.getInt("id"));
					juegos.add(juego);
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

		return juegos;
	}

	public Juego getJuego(int id) throws Exception {
		Juego juego = new Juego();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.juego WHERE id = ?");
				stm.setInt(1, id);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					juego.setId(rs.getInt("id"));
					juego.setEan13(rs.getString("ccodigo"));
					juego.setEntregado(rs.getBoolean("bentregado"));
					juego.setAlquilado(rs.getBoolean("balquilado"));
					IUbicacionDAO ub = connect.getUbicacionDAO();
					juego.setUbicacion(ub.getUbicacion(rs.getInt("idubicacion")));
					JuegoBase jb = this.getJuegoBase(rs.getInt("idjuegobase"));
					juego.setNombre(jb.getNombre());
					juego.setDuracion(jb.getDuracion());
					juego.setRutaImagen(jb.getRutaImagen());
					juego.setMaxjugadores(jb.getMaxjugadores());
					juego.setBgg(jb.getBgg());
					juego.setPasaporte(jb.isPasaporte());
					juego.setInfantil(jb.isInfantil());
					juego.setEdad(jb.getEdad());
					juego.setEditorial(jb.getEditorial());
					juego.setIdBase(jb.getIdBase());
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

		return juego;
	}

	public Juego getJuegoEAN(String ean13) throws Exception {
		Juego juego = new Juego();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.juego WHERE ccodigo = ? and bentregado = false");
				stm.setString(1, ean13);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					juego.setId(rs.getInt("id"));
					juego.setEan13(rs.getString("ccodigo"));
					juego.setEntregado(rs.getBoolean("bentregado"));
					juego.setAlquilado(rs.getBoolean("balquilado"));
					IUbicacionDAO ub = connect.getUbicacionDAO();
					juego.setUbicacion(ub.getUbicacion(rs.getInt("idubicacion")));
					JuegoBase jb = this.getJuegoBase(rs.getInt("idjuegobase"));
					juego.setNombre(jb.getNombre());
					juego.setDuracion(jb.getDuracion());
					juego.setRutaImagen(jb.getRutaImagen());
					juego.setMaxjugadores(jb.getMaxjugadores());
					juego.setBgg(jb.getBgg());
					juego.setPasaporte(jb.isPasaporte());
					juego.setInfantil(jb.isInfantil());
					juego.setEdad(jb.getEdad());
					juego.setEditorial(jb.getEditorial());
					juego.setIdBase(jb.getIdBase());
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

		return juego;
	}

	public List<Juego> getJuegosxUbicacion(int id) throws Exception {
		List<Juego> juegos = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"SELECT juego.* FROM gesjornadas.juego JOIN gesjornadas.juegobase ON juego.idjuegobase = juegobase.id WHERE idubicacion = ? and bentregado = false and bpasaporte = true and binfantil = ?");
				stm.setInt(1, id);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					Juego juego = new Juego();
					juego.setId(rs.getInt("id"));
					juego.setEan13(rs.getString("ccodigo"));
					juego.setEntregado(rs.getBoolean("bentregado"));
					juego.setAlquilado(rs.getBoolean("balquilado"));
					IUbicacionDAO ub = connect.getUbicacionDAO();
					juego.setUbicacion(ub.getUbicacion(rs.getInt("idubicacion")));
					JuegoBase jb = this.getJuegoBase(rs.getInt("idjuegobase"));
					juego.setNombre(jb.getNombre());
					juego.setDuracion(jb.getDuracion());
					juego.setRutaImagen(jb.getRutaImagen());
					juego.setMaxjugadores(jb.getMaxjugadores());
					juego.setBgg(jb.getBgg());
					juego.setPasaporte(jb.isPasaporte());
					juego.setIdBase(jb.getIdBase());
					juego.setInfantil(jb.isInfantil());
					juego.setEdad(jb.getEdad());
					juego.setEditorial(jb.getEditorial());
					juegos.add(juego);
				}

				rs.close();
			}
		} catch (SQLException var13) {
			throw var13;
		} catch (Exception var14) {
			throw var14;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return juegos;
	}

	public List<Juego> getEan(int idBase, boolean todos) throws Exception {
		List<Juego> juegos = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				String query = "SELECT juego.*, ubicacion.cnombre, ubicacion.ccolor FROM gesjornadas.juego JOIN gesjornadas.ubicacion ON juego.idubicacion = ubicacion.id WHERE idjuegobase = ?";
				if (!todos) {
					query = query + " AND bentregado = 0";
				}

				PreparedStatement stm = connect.conexion.prepareStatement(query);
				stm.setInt(1, idBase);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					Juego juego = new Juego();
					juego.setId(rs.getInt("id"));
					juego.setEan13(rs.getString("ccodigo"));
					juego.setEntregado(rs.getBoolean("bentregado"));
					juego.setAlquilado(rs.getBoolean("balquilado"));
					Ubicacion aux = new Ubicacion();
					aux.setId(rs.getInt("idubicacion"));
					aux.setNombre(rs.getString("cnombre"));
					aux.setColor(rs.getString("ccolor"));
					juego.setUbicacion(aux);
					juegos.add(juego);
				}

				rs.close();
			}
		} catch (SQLException var14) {
			throw var14;
		} catch (Exception var15) {
			throw var15;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return juegos;
	}

	public boolean setEstadoJuego(int id, boolean estado) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("UPDATE gesjornadas.juego SET balquilado = ? WHERE id = ?");
				stm.setBoolean(1, estado);
				stm.setInt(2, id);
				int n = stm.executeUpdate();
				if (n > 0) {
					resultado = true;
				}
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

	public List<JuegoBase> getPasaportesFiltro(boolean infantil, String filtro) throws Exception {
		List<JuegoBase> juegos = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			IEditorialDAO editorialDAO = connect.getEditorialDAO();
			if (connect.Conectar()) {
				String query = "SELECT * FROM gesjornadas.juegobase WHERE bpasaporte = true and binfantil = ?";
				query = query + filtro;
				PreparedStatement stm = connect.conexion.prepareStatement(query);
				stm.setBoolean(1, infantil);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					JuegoBase juego = new JuegoBase();
					juego.setNombre(rs.getString("cnombre"));
					juego.setDuracion(rs.getInt("iduracion"));
					juego.setRutaImagen(rs.getString("crutaimagen"));
					juego.setMaxjugadores(rs.getInt("imaximo"));
					juego.setBgg(rs.getString("cbgg"));
					juego.setPasaporte(rs.getBoolean("bpasaporte"));
					juego.setInfantil(rs.getBoolean("binfantil"));
					juego.setEdad(rs.getInt("iedad"));
					juego.setEditorial(editorialDAO.getEditorial(rs.getInt("editorial_id")));
					juego.setIdBase(rs.getInt("id"));
					juegos.add(juego);
				}

				rs.close();
			}
		} catch (SQLException var14) {
			throw var14;
		} catch (Exception var15) {
			throw var15;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return juegos;
	}
}