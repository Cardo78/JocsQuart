package servlet;

import dto.Jeseta;
import dto.Usuario;
import dto.enums.TipoMensaje;
import interfazDB.IDBDAO;
import interfazDB.IMonederoDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servlet.monederoController;

@WebServlet({"/monederoController"})
public class monederoController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    Usuario user = (Usuario)session.getAttribute("usuario");
    String url = "./jspx/monedero.jsp";
    String mensaje = null;
    TipoMensaje tipo = null;
    List<Jeseta> jesetas = null;
    try {
      IDBDAO db = IDBDAO.getBD();
      IMonederoDAO monedero = db.getMonederoDAO();
      jesetas = monedero.getJesetas(user.getIdJugador());
      if (!jesetas.isEmpty())
        request.setAttribute("jesetas", jesetas); 
    } catch (Exception e) {
      mensaje = "ERROR: ";
      mensaje = String.valueOf(mensaje) + "Causa del error: " + e.getMessage();
      tipo = TipoMensaje.ERROR;
    } finally {
      request.setAttribute("mensaje", mensaje);
      if (tipo != null)
        request.setAttribute("tipoMensaje", tipo.getCSSClass()); 
      request.setAttribute("jesetas", jesetas);
      request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doGet(req, resp);
  }
}
