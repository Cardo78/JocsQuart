package interfazDB;

import dto.ConfigGes;

public interface IConfigDAO {
	String Nombre() throws Exception;

	ConfigGes getConfig() throws Exception;

	boolean setConfig(ConfigGes var1) throws Exception;
}