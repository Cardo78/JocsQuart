package servlet;

import dto.Alquiler;
import dto.Jeseta;
import dto.Juego;
import dto.Jugador;
import dto.enums.TipoJeseta;
import dto.enums.TipoMensaje;
import interfazDB.IAlquilerDAO;
import interfazDB.IDBDAO;
import interfazDB.IJuegoDAO;
import interfazDB.IMonederoDAO;
import interfazDB.IPasaporteDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servlet.devolverController;

@WebServlet({"/devolverController"})
public class devolverController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private int valor;
  
  private final String descripcion = "Recibida por jugar a ";
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    HttpSession session = request.getSession(true);
    String url = "./jspx/devolverJuego.jsp";
    String mensaje = null;
    TipoMensaje tipo = null;
    String ean13 = null;
    String minutos = null;
    String pattern = "dd/MM/yyyy HH:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    long ONE_MINUTE_IN_MILLIS = 60000L;
    try {
      if (action != null) {
        ean13 = (String)session.getAttribute("ean13");
        IDBDAO db = IDBDAO.getBD();
        IJuegoDAO juego = db.getJuegoDAO();
        IAlquilerDAO alquiler = db.getAlquilerDAO();
        IPasaporteDAO pasaporte = db.getPasaporteDAO();
        IMonederoDAO monedero = db.getMonederoDAO();
        Juego j = juego.getJuegoEAN(ean13);
        List<Jugador> jugadores = new ArrayList<>();
        List<Jeseta> jesetas = new ArrayList<>();
        List<Alquiler> alquileres = alquiler.getAlquilerJuego(ean13, false);
        if (j.isPasaporte()) {
          long tiempoAlquiler;
          long calculoTiempoMinimo;
          String str;
          switch ((str = action).hashCode()) {
            case -1367724422:
              if (!str.equals("cancel"))
                break; 
              url = "./alquilerController";
              break;
            case 3143097:
              if (!str.equals("find"))
                break; 
              tiempoAlquiler = (new Date()).getTime() - ((Alquiler)alquileres.get(0)).getIni().getTime();
              calculoTiempoMinimo = Math.round(j.getDuracion() * 0.9D * ONE_MINUTE_IN_MILLIS);
              if (calculoTiempoMinimo >= tiempoAlquiler) {
                long tiempoMs = ((Alquiler)alquileres.get(0)).getIni().getTime();
                Date calculoFinAlquiler = new Date(tiempoMs + j.getDuracion() * ONE_MINUTE_IN_MILLIS);
                minutos = simpleDateFormat.format(calculoFinAlquiler);
                mensaje = "<h1>jugado insuficiente!</h1><br>Tiempo de Inicio Alquiler: " + simpleDateFormat.format(((Alquiler)alquileres.get(0)).getIni()) + "<br>Hora de devoluci" + simpleDateFormat.format(new Date()) + "<br>Tiempo duracijuego: " + j.getDuracion() + " minutos." + "<br>Total tiempo Jugado: " + (tiempoAlquiler / ONE_MINUTE_IN_MILLIS) + " minutos.";
                tipo = TipoMensaje.INFO;
              } else {
                for (Alquiler aux : alquileres) {
                  if (!pasaporte.isPasaporte(j.getIdBase(), aux.getJugador().getPulsera())) {
                    pasaporte.setPasaporte(j.getIdBase(), aux.getJugador().getId());
                    Jeseta cryptojeseta = new Jeseta();
                    cryptojeseta.setDescripcion("Recibida por jugar a " + j.getNombre());
                    cryptojeseta.setIdJugador(aux.getJugador().getId());
                    if (j.isInfantil()) {
                      cryptojeseta.setTipo(String.valueOf(TipoJeseta.INFANTIL));
                    } else {
                      cryptojeseta.setTipo(String.valueOf(TipoJeseta.JUEGO));
                    } 
                    cryptojeseta.setUsado(false);
                    cryptojeseta.setIdJeseta(monedero.insertJeseta(cryptojeseta));
                    jesetas.add(cryptojeseta);
                    jugadores.add(aux.getJugador());
                  } 
                } 
                alquiler.endAlquiler(j.getId());
                request.setAttribute("jugadores", jugadores);
                request.setAttribute("jesetas", jesetas);
              } 
              request.setAttribute("juego", j.getNombre());
              break;
            case 778101499:
              if (!str.equals("rechazado"))
                break; 
              alquiler.eliminarAlquiler(j.getId());
              request.setAttribute("juego", j.getNombre());
              request.setAttribute("jugadores", jugadores);
              break;
            case 842406380:
              if (!str.equals("confirmado"))
                break; 
              for (Alquiler aux : alquileres) {
                if (!pasaporte.isPasaporte(j.getIdBase(), aux.getJugador().getPulsera())) {
                  pasaporte.setPasaporte(j.getIdBase(), aux.getJugador().getId());
                  Jeseta cryptojeseta = new Jeseta();
                  cryptojeseta.setDescripcion("Recibida por jugar a " + j.getNombre());
                  cryptojeseta.setIdJugador(aux.getJugador().getId());
                  if (j.isInfantil()) {
                    cryptojeseta.setTipo(String.valueOf(TipoJeseta.INFANTIL));
                  } else {
                    cryptojeseta.setTipo(String.valueOf(TipoJeseta.JUEGO));
                  } 
                  cryptojeseta.setUsado(false);
                  cryptojeseta.setIdJeseta(monedero.insertJeseta(cryptojeseta));
                  jesetas.add(cryptojeseta);
                  jugadores.add(aux.getJugador());
                } 
              } 
              alquiler.endAlquiler(j.getId());
              request.setAttribute("juego", j.getNombre());
              request.setAttribute("jugadores", jugadores);
              request.setAttribute("jesetas", jesetas);
              break;
          } 
        } else {
          alquiler.endAlquiler(j.getId());
        } 
      } else {
        url = "./alquilerController";
      } 
    } catch (Exception e) {
      mensaje = "ERROR: ";
      mensaje = String.valueOf(mensaje) + "Causa del error: " + e.getMessage();
      tipo = TipoMensaje.ERROR;
    } finally {
      request.setAttribute("tiempo", minutos);
      request.setAttribute("mensaje", mensaje);
      if (tipo != null)
        request.setAttribute("tipoMensaje", tipo.getCSSClass()); 
      request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
  
  protected static Date addMinutes(Date date, int amount) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(12, amount);
    return calendar.getTime();
  }
}
