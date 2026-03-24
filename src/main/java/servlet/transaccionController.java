package servlet;

import dto.Jeseta;
import dto.Jugador;
import dto.Usuario;
import dto.enums.TipoMensaje;
import interfazDB.IDBDAO;
import interfazDB.IJugadorDAO;
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
import servlet.transaccionController;

@WebServlet({"/transaccionController"})
public class transaccionController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private final String descripcion = "Recibida de ";
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    Usuario user = (Usuario)session.getAttribute("usuario");
    String url = "./jspx/transaccion.jsp";
    String pulsera = request.getParameter("idpulsera");
    String action = request.getParameter("action");
    String mensaje = null;
    TipoMensaje tipo = null;
    List<Jeseta> jesetas = null;
    Jugador jugador = null;
    try {
      IDBDAO db = IDBDAO.getBD();
      IMonederoDAO monedero = db.getMonederoDAO();
      IJugadorDAO jugadorDAO = db.getJugadorDAO();
      jesetas = monedero.getJesetasUsuario(user.getId());
      if (action != null)
        if (pulsera != null) {
          String jesetasTransferidas, pruebaJeseta[];
          byte b;
          int i;
          String[] arrayOfString1;
          jugador = jugadorDAO.getJugadorPulsera(pulsera);
          String str1;
          switch ((str1 = action).hashCode()) {
            case -804109583:
              if (!str1.equals("confirmar"))
                break; 
              pruebaJeseta = request.getParameter("resultadoJesetas").split("-");
              for (i = (arrayOfString1 = pruebaJeseta).length, b = 0; b < i; ) {
                String valorJeseta = arrayOfString1[b];
                Jeseta jeseta = monedero.getJeseta(Integer.parseInt(valorJeseta));
                if (user.getIdJugador() != -1) {
                  Jugador jugOrigen = jugadorDAO.getJugador(user.getIdJugador());
                  jeseta.setDescripcion("Recibida de " + jugOrigen.getNombre() + " " + jugOrigen.getApellidos());
                } else {
                  jeseta.setDescripcion("Recibida de " + user.getUser());
                } 
                jeseta.setIdJugador(jugador.getId());
                jeseta.setIdUsuario(-1);
                monedero.setJeseta(jeseta);
                b++;
              } 
              mensaje = "CryptoJesetas transferidas con exito al jugador " + jugador.getNombre() + " " + jugador.getApellidos();
              tipo = TipoMensaje.INFO;
              break;
            case 1099965950:
              if (!str1.equals("revisar"))
                break; 
              jesetasTransferidas = request.getParameter("resultadoJesetas");
              mensaje = "que quiere traspasar " + (jesetasTransferidas.split("-")).length + " CryptoJesetas a " + jugador.getNombre() + " " + jugador.getApellidos() + "?";
              tipo = TipoMensaje.INFO;
              request.setAttribute("resultadoJesetas", request.getParameter("resultadoJesetas"));
              request.setAttribute("pulsera", pulsera);
              break;
          } 
        } else {
          mensaje = "Jugador inexistente, compruebe el valor introducido en pulsera.";
          tipo = TipoMensaje.WARNING;
        }  
      jesetas = monedero.getJesetasUsuario(user.getId());
      if (jesetas.isEmpty())
        jesetas = monedero.getJesetas(user.getIdJugador()); 
    } catch (Exception e) {
      mensaje = "ERROR: ";
      mensaje = String.valueOf(mensaje) + "Causa del error: " + e.getMessage();
      tipo = TipoMensaje.ERROR;
    } finally {
      request.setAttribute("jesetas", jesetas);
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
