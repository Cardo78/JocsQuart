package servlet;

import dto.ConfigGes;
import dto.Editorial;
import interfazDB.IConfigDAO;
import interfazDB.IDBDAO;
import interfazDB.IEditorialDAO;
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
import servlet.menuController;

@WebServlet({"/menuController"})
public class menuController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    session.setMaxInactiveInterval(1800);
    String mensajeError = "", nombre = "", urlIndex = "", urlLogo = "", urlLogopeq = "";
    String url = "./index.jsp";
    try {
      ConfigGes cfg = null;
      IDBDAO db = IDBDAO.getBD();
      if (session.getAttribute("nombre") == null) {
        IConfigDAO configuracion = db.getConfigDAO();
        cfg = configuracion.getConfig();
      } 
      if (cfg != null) {
        nombre = cfg.getNombre();
        urlIndex = cfg.getUrlIndex();
        urlLogo = cfg.getUrlLogo();
        urlLogopeq = cfg.getUrlLogopeq();
      } else {
        nombre = (String)session.getAttribute("nombre");
        urlIndex = (String)session.getAttribute("urlindex");
        urlLogo = (String)session.getAttribute("logo");
        urlLogopeq = (String)session.getAttribute("logopeq");
      } 
      IEditorialDAO editorialdb = db.getEditorialDAO();
      List<Editorial> editoriales = editorialdb.getEditoriales();
      request.setAttribute("editoriales", editoriales);
      request.setAttribute("editoriales", editoriales);
      int section = Integer.parseInt(request.getParameter("section"));
      switch (section) {
        case 0:
          url = "./index.jsp";
          break;
        case 1:
          url = "./loginController";
          break;
        case 2:
          url = "./jugadorController";
          break;
        case 3:
          url = "./juegoController";
          break;
        case 4:
          url = "./pasaporteController";
          break;
        case 5:
          url = "./alquilerController";
          break;
        case 6:
          url = "./monederoController";
          break;
        case 7:
          url = "./disponibleController";
          break;
        case 8:
          url = "./transaccionController";
          break;
        case 9:
          url = "./sorteoController";
          break;
        case 10:
          url = "./logoutController";
          break;
        case 11:
          url = "./disponibleController";
          break;
        case 12:
          url = "./configController";
          break;
      } 
      session.setAttribute("nombre", nombre);
      session.setAttribute("urlindex", urlIndex);
      session.setAttribute("logo", urlLogo);
      session.setAttribute("logopeq", urlLogopeq);
      request.setAttribute("mensaje", mensajeError);
    } catch (Exception e) {
      e.printStackTrace();
      mensajeError = e.getMessage();
      url = "./error.jsp";
      request.setAttribute("mensaje", mensajeError);
    } finally {
      request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
