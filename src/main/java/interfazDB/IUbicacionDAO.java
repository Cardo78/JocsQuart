package interfazDB;

import dto.Ubicacion;
import java.util.List;

public interface IUbicacionDAO {
	Ubicacion getUbicacion(int var1) throws Exception;

	List<Ubicacion> getUbicaciones() throws Exception;

	int insertUbicacion(Ubicacion var1) throws Exception;

	boolean setUbicacion(Ubicacion var1) throws Exception;
}