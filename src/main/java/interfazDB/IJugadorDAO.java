package interfazDB;

import dto.Jugador;
import java.util.List;

public interface IJugadorDAO {
	Jugador getJugador(int var1) throws Exception;

	Jugador getJugadorPulsera(String var1) throws Exception;

	List<Jugador> getJugadorParametros(String var1, String var2, String var3) throws Exception;

	int insertJugador(Jugador var1) throws Exception;

	boolean setJugador(Jugador var1) throws Exception;
}