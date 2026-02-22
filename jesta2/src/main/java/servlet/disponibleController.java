package servlet;

import dto.Alquiler;
import dto.JuegoBase;
import dto.Jugador;
import dto.Pasaporte;
import dto.Usuario;
import dto.enums.TipoMensaje;
import interfazDB.IAlquilerDAO;
import interfazDB.IDBDAO;
import interfazDB.IJuegoDAO;
import interfazDB.IJugadorDAO;
import interfazDB.IPasaporteDAO;
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
import servlet.disponibleController;

@WebServlet({"/disponibleController"})
public class disponibleController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    Usuario user = (Usuario)session.getAttribute("usuario");
    String action = (request.getParameter("action") != null) ? request.getParameter("action") : "find";
    String url = "./jspx/disponible.jsp";
    if (user.getTipo() != "JUGADOR" && user.getTipo() != "")
      action = "localizar"; 
    String mensaje = null;
    TipoMensaje tipo = null;
    String nombre = "";
    try {
      IDBDAO db = IDBDAO.getBD();
      IJuegoDAO game = db.getJuegoDAO();
      if (action != null) {
        List<JuegoBase> juegos;
        int index;
        String contador;
        int total;
        IJugadorDAO jugadorDAO;
        Jugador jugador;
        boolean infantil;
        boolean jugados;
        String filtros;
        String descripcion;
        String edad;
        String duracion;
        List<Pasaporte> disponibles;
        List<String> totales;
        IPasaporteDAO pasaporteDAO;
        List<JuegoBase> totaljuegos;
        int i;
        String str1;
        switch ((str1 = action).hashCode()) {
          case -1204567283:
            if (!str1.equals("localizar"))
              break; 
            nombre = request.getParameter("nombre");
            juegos = game.getJuegos(nombre);
            if (!juegos.isEmpty())
              request.setAttribute("juegos", juegos); 
            index = (request.getParameter("id") != null) ? Integer.parseInt(request.getParameter("id")) : -1;
            if (index > 0) {
              String ean = (request.getParameter("ean") != null) ? request.getParameter("ean") : "";
              if (!ean.equals("")) {
                IAlquilerDAO alqDB = db.getAlquilerDAO();
                List<Alquiler> alquileres = alqDB.getAlquilerJuego(ean, false);
                request.setAttribute("alquileres", alquileres);
              } 
            } 
            url = "./jspx/localizador.jsp";
            break;
          case 3143097:
            if (!str1.equals("find"))
              break; 
            contador = (request.getParameter("contador") != null) ? request.getParameter("contador") : "30";
            total = contador.equals("") ? 30 : Integer.parseInt(contador);
            jugadorDAO = db.getJugadorDAO();
            jugador = jugadorDAO.getJugador(user.getIdJugador());
            infantil = !(request.getParameter("infantil") == null);
            jugados = !(request.getParameter("jugados") == null);
            filtros = "";
            descripcion = (request.getParameter("descripcion") == null) ? "" : request.getParameter("descripcion");
            if (descripcion != "")
              filtros = String.valueOf(filtros) + " and cnombre like '%" + descripcion + "%'"; 
            edad = (request.getParameter("jugadores") == null) ? "" : request.getParameter("jugadores");
            if (edad != "")
              filtros = String.valueOf(filtros) + " and imaximo >= " + edad; 
            duracion = (request.getParameter("duracion") == null) ? "" : request.getParameter("duracion");
            if (duracion != "")
              filtros = String.valueOf(filtros) + " and iduracion <= " + duracion; 
            disponibles = new ArrayList<>();
            totales = new ArrayList<>();
            pasaporteDAO = db.getPasaporteDAO();
            totaljuegos = game.getPasaportesFiltro(infantil, filtros);
            i = 0;
            if (i < total)
              for (JuegoBase juego : totaljuegos) {
                if (i > total)
                  break; 
                Pasaporte juegoPasaporte = new Pasaporte(juego);
                if (jugador != null) {
                  if (pasaporteDAO.getPasaporte(juego.getIdBase(), jugador.getId())) {
                    if (jugados) {
                      juegoPasaporte.setJugado(false);
                      juegoPasaporte.setNombre(String.valueOf(juego.getNombre()) + " (JUGADO)");
                      disponibles.add(juegoPasaporte);
                      totales.add("0");
                    } 
                  } else {
                    juegoPasaporte.setJugado(true);
                    disponibles.add(juegoPasaporte);
                    totales.add("0");
                  } 
                } else {
                  juegoPasaporte.setJugado(true);
                  disponibles.add(juegoPasaporte);
                  totales.add("0");
                } 
                i++;
              }  
            if (disponibles.size() < 30) {
              contador = "1";
            } else {
              contador = String.valueOf(total);
            } 
            url = "./jspx/disponible.jsp";
            request.setAttribute("disponibles", disponibles);
            request.setAttribute("cantidad", totales);
            request.setAttribute("contador", contador);
            request.setAttribute("infantil", Boolean.valueOf(infantil));
            request.setAttribute("jugados", Boolean.valueOf(jugados));
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
