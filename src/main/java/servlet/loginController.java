package servlet;

import DatosDB.UsuarioDAO;
import dto.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servlet.loginController;

@WebServlet({"/loginController"})
public class loginController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    session.setMaxInactiveInterval(1800);
    String url = "./index.jsp";
    String user = request.getParameter("inputUser");
    String pass = request.getParameter("inputPass");
    Usuario u = null;
    String mensaje = "";
    try {
      if (user != null && pass != null) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        u = usuarioDAO.getUsuarioName(user);
        if (u.getUser() != null) {
          if (u.getPass().equals(pass)) {
            url = "./index.jsp";
          } else {
            mensaje = "Login Incorrecto " + user + ": Compruebe los datos introducidos.";
            u = null;
            url = "./jspx/login.jsp";
          } 
        } else {
          mensaje = "Login Incorrecto " + user + ": Compruebe los datos introducidos.";
          u = null;
          url = "./jspx/login.jsp";
        } 
      } else {
        url = "./jspx/login.jsp";
      } 
      session.setAttribute("usuario", u);
    } catch (Exception e) {
      url = "./jspx/login.jsp";
      mensaje = e.getMessage();
    } finally {
      request.setAttribute("mensaje", mensaje);
      request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
