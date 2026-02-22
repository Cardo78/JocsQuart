package servlet;

import dto.JuegoBase;
import dto.Jugador;
import dto.Pasaporte;
import dto.Usuario;
import dto.enums.TipoMensaje;
import interfazDB.IDBDAO;
import interfazDB.IJuegoDAO;
import interfazDB.IJugadorDAO;
import interfazDB.IMonederoDAO;
import interfazDB.IPasaporteDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servlet.pasaporteController;

@WebServlet({"/pasaporteController"})
public class pasaporteController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    Usuario user = (Usuario)session.getAttribute("usuario");
    boolean infantil = (request.getParameter("infantil") == null) ? false : Boolean.valueOf(request.getParameter("infantil")).booleanValue();
    String pulsera = request.getParameter("pulsera");
    String url = "./jspx/pasaporte.jsp";
    String mensaje = null;
    TipoMensaje tipo = null;
    String jesetas = null;
    String nombreJugador = null;
    List<Pasaporte> pasaporte = new ArrayList<>();
    try {
      IDBDAO db = IDBDAO.getBD();
      IJuegoDAO game = db.getJuegoDAO();
      Jugador jugador = null;
      IJugadorDAO jugadorDAO = db.getJugadorDAO();
      if (user != null && 
        user.getTipo() == "JUGADOR") {
        jugador = jugadorDAO.getJugador(user.getIdJugador());
        infantil = jugador.isInfantil();
      } 
      if (pulsera != null) {
        jugador = jugadorDAO.getJugadorPulsera(pulsera);
        infantil = jugador.isInfantil();
      } 
      IPasaporteDAO pasaporteDAO = db.getPasaporteDAO();
      List<JuegoBase> juegos = game.getPasaportes(infantil);
      for (JuegoBase juego : juegos) {
        Pasaporte juegoPasaporte = new Pasaporte(juego);
        if (jugador != null) {
          if (pasaporteDAO.getPasaporte(juego.getIdBase(), jugador.getId())) {
            juegoPasaporte.setJugado(true);
            juegoPasaporte.setNombre(String.valueOf(juego.getNombre()) + " (JUGADO)");
          } else {
            juegoPasaporte.setJugado(false);
          } 
        } else {
          juegoPasaporte.setJugado(true);
        } 
        pasaporte.add(juegoPasaporte);
      } 
      
      Collections.sort(pasaporte, Comparator.comparing(p -> p.getEditorial().getNombre()));
      

      if (jugador != null && jugador.getId() > 0) {
        IMonederoDAO monedero = db.getMonederoDAO();
        jesetas = String.valueOf(monedero.getTotalJesetas(jugador.getId()));
        nombreJugador = String.valueOf(jugador.getNombre()) + " " + jugador.getApellidos();
      } 
      request.setAttribute("pasaporte", pasaporte);
      request.setAttribute("pulsera", pulsera);
      request.setAttribute("nombreJugador", nombreJugador);
      request.setAttribute("jesetas", jesetas);
      request.setAttribute("infantil", Boolean.valueOf(infantil));
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
