package interfazDB;

import dto.Jeseta;
import java.util.List;

public interface IMonederoDAO {
	int insertJeseta(Jeseta var1) throws Exception;

	boolean setJeseta(Jeseta var1) throws Exception;

	boolean delJeseta(Jeseta var1) throws Exception;

	List<Jeseta> getJesetas(int var1) throws Exception;

	List<Jeseta> getJesetasUsuario(int var1) throws Exception;

	int getTotalJesetas(int var1) throws Exception;

	int getTotalJesetasUsuario(int var1) throws Exception;

	Jeseta getJeseta(int var1) throws Exception;

	List<Jeseta> getSorteo(boolean var1) throws Exception;
}