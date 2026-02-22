package interfazDB;

public interface IPasaporteDAO {
	boolean setPasaporte(int var1, int var2) throws Exception;

	boolean delPasaporte(int var1, int var2) throws Exception;

	boolean isPasaporte(int var1, String var2) throws Exception;

	boolean getPasaporte(int var1, int var2) throws Exception;
}