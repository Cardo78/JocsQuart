package servlet;

import dto.Jeseta;
import dto.Jugador;
import dto.enums.TipoMensaje;
import interfazDB.IDBDAO;
import interfazDB.IJugadorDAO;
import interfazDB.IMonederoDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlet.sorteoController;

@WebServlet({"/sorteoController"})
public class sorteoController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private final String descripcion = "Ganador del premio... ";
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String mensaje = null;
    TipoMensaje tipo = null;
    String action = request.getParameter("action");
    String url = "./jspx/sorteo.jsp";
    try {
      if (action != null) {
        int index;
        Jugador jugador;
        Jeseta jeseta;
        List<Jugador> jugadores;
        IDBDAO db = IDBDAO.getBD();
        IMonederoDAO monedero = db.getMonederoDAO();
        IJugadorDAO jugadorDAO = db.getJugadorDAO();
        List<Jeseta> jesetas = null;
        String str;
        switch ((str = action).hashCode()) {
          case -980103984:
            if (!str.equals("premio"))
              break; 
            jesetas = monedero.getSorteo(false);
            index = (int)(Math.random() * jesetas.size());
            jugador = jugadorDAO.getJugador(((Jeseta)jesetas.get(index)).getIdJugador());
            jeseta = jesetas.get(index);
            jeseta.setUsado(true);
            monedero.setJeseta(jeseta);
            mensaje = "Ganador del premio... ";
            tipo = TipoMensaje.SUCCESS;
            request.setAttribute("jeseta", jeseta);
            request.setAttribute("jugador", jugador);
            break;
          case -571470108:
            if (!str.equals("resultados"))
              break; 
            jesetas = monedero.getSorteo(true);
            jugadores = new ArrayList<>();
            for (Jeseta premio : jesetas) {
              Jugador premiado = jugadorDAO.getJugador(premio.getIdJugador());
              jugadores.add(premiado);
            } 
            request.setAttribute("jesetas", jesetas);
            request.setAttribute("jugadores", jugadores);
            url = "./jspx/resultadoSorteo.jsp";
            break;
        } 
      } 
    } catch (Exception e) {
      mensaje = "ERROR: ";
      mensaje = String.valueOf(mensaje) + "Causa del error: " + e.getMessage();
      tipo = TipoMensaje.ERROR;
    } finally {
      request.setAttribute("mensaje", mensaje);
      if (tipo != null)
        request.setAttribute("tipoMensaje", tipo.getCSSClass()); 
      request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
