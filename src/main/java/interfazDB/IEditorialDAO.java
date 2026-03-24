package interfazDB;

import dto.Editorial;
import java.util.List;

public interface IEditorialDAO {
	Editorial getEditorial(int var1) throws Exception;

	List<Editorial> getEditoriales() throws Exception;

	int insertEditorial(Editorial var1) throws Exception;

	boolean setEditorial(Editorial var1) throws Exception;
}