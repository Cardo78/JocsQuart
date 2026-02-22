package interfazDB;

import dto.Juego;
import dto.JuegoBase;
import java.util.List;

public interface IJuegoDAO {
	int insertJuego(JuegoBase var1) throws Exception;

	int insertJuego(Juego var1, int var2) throws Exception;

	boolean setJuegoBase(JuegoBase var1) throws Exception;

	boolean setJuego(Juego var1, int var2) throws Exception;

	JuegoBase getJuegoBase(int var1) throws Exception;

	List<JuegoBase> getJuegos(String var1) throws Exception;

	List<JuegoBase> getPasaportes(boolean var1) throws Exception;

	Juego getJuego(int var1) throws Exception;

	Juego getJuegoEAN(String var1) throws Exception;

	List<Juego> getJuegosxUbicacion(int var1) throws Exception;

	List<Juego> getEan(int var1, boolean var2) throws Exception;

	boolean setEstadoJuego(int var1, boolean var2) throws Exception;

	List<JuegoBase> getPasaportesFiltro(boolean var1, String var2) throws Exception;
}