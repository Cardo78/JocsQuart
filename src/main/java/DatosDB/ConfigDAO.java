package DatosDB;

import dto.ConfigGes;
import interfazDB.IConfigDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigDAO implements IConfigDAO {
	private final String CONFIG = "SELECT * FROM gesjornadas.config";
	private final String UPDATECONFIG = "UPDATE gesjornadas.config SET cnombre = ?, curlIndex = ?, curlLogo = ?, curlLogoPeq = ?";

	public String Nombre() throws Exception {
		new ConfigGes();

		ConfigGes cfg;
		try {
			cfg = this.getConfig();
		} catch (SQLException var3) {
			throw var3;
		} catch (Exception var4) {
			throw var4;
		}

		return cfg.getNombre();
	}

	public ConfigGes getConfig() throws Exception {
		ConfigGes cfg = new ConfigGes();
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement("SELECT * FROM gesjornadas.config");
				ResultSet rs = stm.executeQuery();

				while (rs.next()) {
					cfg.setNombre(rs.getString("cnombre"));
					cfg.setUrlIndex(rs.getString("curlindex"));
					cfg.setUrlLogo(rs.getString("curllogo"));
					cfg.setUrlLogopeq(rs.getString("curllogopeq"));
				}

				rs.close();
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

		return cfg;
	}

	public boolean setConfig(ConfigGes _config) throws Exception {
		DBBasic connect = null;

		try {
			connect = new DBBasic();
			if (connect.Conectar()) {
				PreparedStatement stm = connect.conexion.prepareStatement(
						"UPDATE gesjornadas.config SET cnombre = ?, curlIndex = ?, curlLogo = ?, curlLogoPeq = ?");
				stm.setString(1, _config.getNombre());
				stm.setString(2, _config.getUrlIndex());
				stm.setString(3, _config.getUrlLogo());
				stm.setString(4, _config.getUrlLogopeq());
				stm.executeUpdate();
				return true;
			}
		} catch (SQLException var8) {
			throw var8;
		} catch (Exception var9) {
			throw var9;
		} finally {
			if (connect != null) {
				connect.desconectar();
			}

		}

		return false;
	}
}