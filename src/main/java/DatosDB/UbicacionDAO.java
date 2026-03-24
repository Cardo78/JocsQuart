package DatosDB;

import dto.Ubicacion;
import interfazDB.IUbicacionDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO implements IUbicacionDAO {
	private final String INSERTUBICACION = "INSERT INTO gesjornadas.ubicacion (cnombre, ccolor) VALUES (?,?)";
	private final String UBICACIONES = "SELECT * FROM gesjornadas.ubicacion";
	private final String UBICACION = "SELECT * FROM gesjornadas.ubicacion WHERE id = ?";
	private final String UPDATEUBICACION = "UPDATE gesjorandas.ubicacion SET cnombre = ?, ccolor = ? WHERE id = ?";

	public Ubicacion getUbicacion(int id) throws Exception {
		Ubicacion ubicacion = new Ubicacion();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("SELECT * FROM gesjornadas.ubicacion WHERE id = ?");
				stm.setInt(1, id);
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					ubicacion.setNombre(rs.getString("cnombre"));
					ubicacion.setColor(rs.getString("ccolor"));
					ubicacion.setId(rs.getInt("id"));
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

		return ubicacion;
	}

	public List<Ubicacion> getUbicaciones() throws Exception {
		List<Ubicacion> ubicaciones = new ArrayList();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement("SELECT * FROM gesjornadas.ubicacion");
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					Ubicacion aux = new Ubicacion();
					aux.setNombre(rs.getString("cnombre"));
					aux.setColor(rs.getString("ccolor"));
					aux.setId(rs.getInt("id"));
					ubicaciones.add(aux);
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

		return ubicaciones;
	}

	public int insertUbicacion(Ubicacion ubicacion) throws Exception {
		DBBasic connect = null;
		int id = -1;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("INSERT INTO gesjornadas.ubicacion (cnombre, ccolor) VALUES (?,?)", 1);
				stm.setString(1, ubicacion.getNombre());
				stm.setString(2, ubicacion.getColor());
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

	public boolean setUbicacion(Ubicacion ubicacion) throws Exception {
		DBBasic connect = null;
		boolean resultado = false;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion
						.prepareStatement("UPDATE gesjorandas.ubicacion SET cnombre = ?, ccolor = ? WHERE id = ?");
				stm.setString(1, ubicacion.getNombre());
				stm.setString(2, ubicacion.getColor());
				stm.setInt(3, ubicacion.getId());
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