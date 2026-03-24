package interfazDB;

import dto.Usuario;
import java.util.List;

public interface IUsuarioDAO {
	Usuario getUsuario(int var1) throws Exception;

	boolean setUsuario(Usuario var1) throws Exception;

	int insertUsuario(Usuario var1) throws Exception;

	Usuario getUsuarioName(String var1) throws Exception;

	Usuario getUsuarioJugador(int var1) throws Exception;

	List<Usuario> getUsuarios() throws Exception;
}