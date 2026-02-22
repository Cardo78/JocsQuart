package DatosDB;

import dto.Editorial;
import interfazDB.IEditorialDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditorialDAO implements IEditorialDAO {
	private final String INSERTEDITORIAL = "INSERT INTO gesjornadas.editorial (cnombre, crutalogo, iorden) VALUES (?,?,?)";
	private final String EDITORIALES = "SELECT * FROM gesjornadas.editorial ORDER BY iorden ASC";
	private final String EDITORIAL = "SELECT * FROM gesjornadas.editorial WHERE id = ?";
	private final String UPDATEEDITORIAL = "UPDATE gesjornadas.editorial SET cnombre = ?, crutalogo = ?, iorden = ? WHERE id = ?";
	private final String LOGO = "crutalogo";
	private final String NOMBRE = "cnombre";
	private final String ORDEN = "iorden";
	private final String ID = "id";

	public Editorial getEditorial(int id) throws Exception {
		Editorial editorial = new Editorial();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.editorial WHERE id = ?");
				stm.setInt(1, id);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					editorial.setLogo(rs.getString("crutalogo"));
					editorial.setNombre(rs.getString("cnombre"));
					editorial.setOrden(rs.getInt("iorden"));
					editorial.setId(rs.getInt("id"));
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

		return editorial;
	}

	public List<Editorial> getEditoriales() throws Exception {
		List<Editorial> editoriales = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.editorial ORDER BY iorden ASC");
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					Editorial aux = new Editorial();
					aux.setNombre(rs.getString("cnombre"));
					aux.setLogo(rs.getString("crutalogo"));
					aux.setOrden(rs.getInt("iorden"));
					aux.setId(rs.getInt("id"));
					editoriales.add(aux);
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

		return editoriales;
	}

	public int insertEditorial(Editorial editorial) throws Exception {
		DBBasic connect = null;
		int id = -1;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"INSERT INTO gesjornadas.editorial (cnombre, crutalogo, iorden) VALUES (?,?,?)", 1);
				stm.setString(1, editorial.getNombre());
				stm.setString(2, editorial.getLogo());
				stm.setInt(3, editorial.getOrden());
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

	public boolean setEditorial(Editorial editorial) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"UPDATE gesjornadas.editorial SET cnombre = ?, crutalogo = ?, iorden = ? WHERE id = ?");
				stm.setString(1, editorial.getNombre());
				stm.setString(2, editorial.getLogo());
				stm.setInt(3, editorial.getOrden());
				stm.setInt(4, editorial.getId());
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