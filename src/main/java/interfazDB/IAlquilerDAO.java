package interfazDB;

import dto.Alquiler;
import java.util.List;

public interface IAlquilerDAO {
	List<Alquiler> getAlquileres(boolean var1) throws Exception;

	List<Alquiler> getAlquilerJuego(String var1, boolean var2) throws Exception;

	List<Alquiler> getAlquilerJugador(String var1, boolean var2) throws Exception;

	int setAlquiler(int var1, List<String> var2) throws Exception;

	int endAlquiler(int var1) throws Exception;

	boolean endAlquilerJugador(int var1) throws Exception;

	String forzarEndAlquilerJugador(int var1) throws Exception;

	boolean eliminarAlquiler(int var1) throws Exception;

	Alquiler getAlquiler(int var1) throws Exception;
}