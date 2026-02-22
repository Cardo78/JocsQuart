package interfazDB;

import DatosDB.DBBasic;
import java.io.IOException;

public abstract class IDBDAO {
	public abstract IConfigDAO getConfigDAO();

	public abstract IUsuarioDAO getUsuarioDAO();

	public abstract IJugadorDAO getJugadorDAO();

	public abstract IUbicacionDAO getUbicacionDAO();

	public abstract IJuegoDAO getJuegoDAO();

	public abstract IAlquilerDAO getAlquilerDAO();

	public abstract IPasaporteDAO getPasaporteDAO();

	public abstract IMonederoDAO getMonederoDAO();

	public abstract IEditorialDAO getEditorialDAO();

	public static IDBDAO getBD() throws IOException {
		return new DBBasic();
	}
}