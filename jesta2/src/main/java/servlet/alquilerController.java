package servlet;

import dto.Alquiler;
import dto.Juego;
import dto.Jugador;
import dto.enums.TipoMensaje;
import interfazDB.IAlquilerDAO;
import interfazDB.IDBDAO;
import interfazDB.IJuegoDAO;
import interfazDB.IJugadorDAO;
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
import javax.servlet.http.HttpSession;
import servlet.alquilerController;

@WebServlet({"/alquilerController"})
public class alquilerController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    String url = "./jspx/alquilarJuego.jsp";
    String mensaje = null;
    TipoMensaje tipo = null;
    String ean13 = null;
    Juego j = null;
    String jesetas = null;
    boolean alquilerExistente = false;
    try {
      jesetas = request.getParameter("jesetas");
      if (action != null) {
        int maxJ;
        List<String> pulseras;
        int i;
        List<String> morosos, pulserasConAlquilerS;
        List<Boolean> pulserasConAlquilerB;
        List<String> jugadoresIds;
        int total;
        IDBDAO db = IDBDAO.getBD();
        IAlquilerDAO alquiler = null;
        IJugadorDAO jugador = null;
        IJuegoDAO juego = null;
        if (action.startsWith("forzarDevolucion")) {
          jugador = db.getJugadorDAO();
          juego = db.getJuegoDAO();
          alquiler = db.getAlquilerDAO();
          j = juego.getJuegoEAN(ean13);
          String pulsera = action.substring(16);
          Jugador jug = jugador.getJugadorPulsera(pulsera);
          ean13 = alquiler.forzarEndAlquilerJugador(jug.getId());
          List<Alquiler> alquileres = alquiler.getAlquilerJuego(ean13, false);
          if (alquileres.size() == 0)
            juego.setEstadoJuego(j.getId(), false); 
          action = "alquilar";
        } 
        String str;
        switch ((str = action).hashCode()) {
          case 3143097:
            if (!str.equals("find"))
              break; 
            ean13 = request.getParameter("codigo");
            juego = db.getJuegoDAO();
            j = juego.getJuegoEAN(ean13);
            if (j.isAlquilado()) {
              HttpSession session = request.getSession(true);
              session.setAttribute("ean13", ean13);
              url = "./devolverController";
            } 
            break;
          case 1961408675:
            if (!str.equals("alquilar"))
              break; 
            maxJ = (request.getParameter("maxJugadores") == null) ? 30 : 
              Integer.parseInt(request.getParameter("maxJugadores"));
            pulseras = new ArrayList<>(maxJ);
            for (i = 0; i < maxJ; i++) {
              String codigoPulsera = request.getParameter("pulsera" + String.valueOf(i));
              if (!codigoPulsera.equals(""))
                pulseras.add(codigoPulsera); 
            } 
            alquiler = db.getAlquilerDAO();
            jugador = db.getJugadorDAO();
            juego = db.getJuegoDAO();
            morosos = new ArrayList<>(maxJ);
            pulserasConAlquilerS = new ArrayList<>(maxJ);
            pulserasConAlquilerB = new ArrayList<>(maxJ);
            jugadoresIds = new ArrayList<>();
            for (String pulsera : pulseras) {
              List<Alquiler> alquileres = alquiler.getAlquilerJugador(pulsera, false);
              Jugador comprueba = jugador.getJugadorPulsera(pulsera);
              if (comprueba.getId() > 0) {
                if (alquileres.size() > 0) {
                  alquilerExistente = true;
                  Jugador moroso = jugador.getJugadorPulsera(pulsera);
                  morosos.add(String.valueOf(moroso.getNombre()) + " " + moroso.getApellidos());
                  pulserasConAlquilerS.add(pulsera);
                  pulserasConAlquilerB.add(Boolean.valueOf(true));
                } else {
                  pulserasConAlquilerS.add(pulsera);
                  pulserasConAlquilerB.add(Boolean.valueOf(false));
                } 
                jugadoresIds.add(String.valueOf(jugador.getJugadorPulsera(pulsera).getId()));
                continue;
              } 
              mensaje = "La pulsera " + pulsera + " no estregistrada correctamente. ";
              tipo = TipoMensaje.WARNING;
            } 
            request.setAttribute("pulserasConAlquilerB", pulserasConAlquilerB);
            request.setAttribute("pulserasConAlquilerS", pulserasConAlquilerS);
            ean13 = request.getParameter("ean13");
            j = juego.getJuegoEAN(ean13);
            if (alquilerExistente) {
              if (mensaje == null)
                mensaje = ""; 
              mensaje = String.valueOf(mensaje) + "Los siguientes usuarios ya tienen un alquiler activo: " + morosos.toString();
              tipo = TipoMensaje.WARNING;
              break;
            } 
            total = alquiler.setAlquiler(j.getId(), jugadoresIds);
            j = null;
            if (mensaje == null)
              mensaje = ""; 
            if (total > 0) {
              mensaje = String.valueOf(mensaje) + "ALQUILADO! TOTAL JUGADORES: " + String.valueOf(total);
              tipo = TipoMensaje.SUCCESS;
            } else {
              mensaje = String.valueOf(mensaje) + "NO SE HA ALQUILADO EL JUEGO, JUGADORES ALQUILADOS: " + String.valueOf(total);
              tipo = TipoMensaje.WARNING;
            } 
            ean13 = "";
            break;
        } 
      } 
    } catch (Exception e) {
      mensaje = "ERROR: ";
      mensaje = String.valueOf(mensaje) + "Causa del error: " + e.getMessage();
      tipo = TipoMensaje.ERROR;
    } finally {
      request.setAttribute("mensaje", mensaje);
      request.setAttribute("juego", j);
      if (tipo != null)
        request.setAttribute("tipoMensaje", tipo.getCSSClass()); 
      request.setAttribute("jesetas", jesetas);
      request.setAttribute("ean13", ean13);
      request.setAttribute("alquilerExistente", Boolean.valueOf(alquilerExistente));
      request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
