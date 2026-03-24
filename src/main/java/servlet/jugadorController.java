package servlet;

import DatosDB.JugadorDAO;
import DatosDB.UsuarioDAO;
import dto.Jeseta;
import dto.Jugador;
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
import servlet.jugadorController;

@WebServlet({"/jugadorController"})
public class jugadorController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private final String descripcion = "Jeseta de Preinscripcion";
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    String url = "./jspx/buscarUsuario.jsp";
    String mensaje = null;
    TipoMensaje tipo = null;
    String nombre = request.getParameter("nombre");
    String apellidos = request.getParameter("apellidos");
    String dni = null;
    String id = null;
    IDBDAO db = IDBDAO.getBD();
    try {
      if (action != null) {
        JugadorDAO jugadorDAO1;
        List<Jugador> jugadores;
        Jugador j;
        JugadorDAO jugadorDAO2;
        String email;
        String telefono;
        String cp;
        String pulsera;
        String valorInfantil;
        boolean infantil;
        String str1;
        switch ((str1 = action).hashCode()) {
          case 108960:
            if (!str1.equals("new"))
              break; 
            request.setAttribute("nombre", nombre);
            request.setAttribute("apellidos", apellidos);
            url = "./jspx/formularioUsuario.jsp";
            break;
          case 3143097:
            if (!str1.equals("find"))
              break; 
            dni = request.getParameter("dni");
            jugadorDAO1 = new JugadorDAO();
            jugadores = jugadorDAO1.getJugadorParametros(nombre, apellidos, dni);
            request.setAttribute("jugadores", jugadores);
            request.setAttribute("nombre", nombre);
            request.setAttribute("apellidos", apellidos);
            url = "./jspx/buscarUsuario.jsp";
            break;
          case 3417674:
            if (!str1.equals("open"))
              break; 
            dni = request.getParameter("dni");
            email = request.getParameter("email");
            telefono = request.getParameter("telefono");
            cp = request.getParameter("cp");
            pulsera = request.getParameter("pulsera");
            valorInfantil = request.getParameter("infantil");
            infantil = Boolean.parseBoolean(request.getParameter("infantil"));
            id = request.getParameter("id");
            request.setAttribute("nombre", nombre);
            request.setAttribute("apellidos", apellidos);
            request.setAttribute("dni", dni);
            request.setAttribute("email", email);
            request.setAttribute("telefono", telefono);
            request.setAttribute("cp", cp);
            request.setAttribute("pulsera", pulsera);
            request.setAttribute("infantil", Boolean.valueOf(infantil));
            request.setAttribute("id", id);
            request.setAttribute("enableEdit", Boolean.valueOf(true));
            url = "./jspx/formularioUsuario.jsp";
            break;
          case 3522941:
            if (!str1.equals("save"))
              break; 
            j = new Jugador();
            j.setNombre(request.getParameter("nombre"));
            j.setApellidos(request.getParameter("apellidos"));
            j.setDni(request.getParameter("dni"));
            j.setMail(request.getParameter("email"));
            j.setTelefono(request.getParameter("telefono"));
            j.setCp((request.getParameter("cp") == null) ? 0 : Integer.parseInt(request.getParameter("cp")));
            j.setPulsera(request.getParameter("pulsera"));
            j.setInfantil(!(request.getParameter("infantil") == null));
            id = request.getParameter("id");
            jugadorDAO2 = new JugadorDAO();
            if (id.equals("") || id.equals("-1")) {
              j.setId(jugadorDAO2.insertJugador(j));
              boolean preinscrito = !(request.getParameter("preinscrito") == null);
              IMonederoDAO monedero = db.getMonederoDAO();
              int totalJesetas = preinscrito ? 2 : 1;
              for (int i = 0; i < totalJesetas; i++) {
                Jeseta jesaux = new Jeseta();
                jesaux.setDescripcion("Jeseta de Preinscripción");
                jesaux.setTipo("LOGRO");
                jesaux.setIdJugador(j.getId());
                jesaux.setUsado(false);
                monedero.insertJeseta(jesaux);
              } 
            } else {
              j.setId(Integer.parseInt(request.getParameter("id")));
              if (!jugadorDAO2.setJugador(j))
                j.setId(-1); 
            } 
            if (j.getId() > 0) {
              UsuarioDAO usuarioDAO = new UsuarioDAO();
              Usuario user = usuarioDAO.getUsuarioJugador(j.getId());
              if (user.getId() < 0) {
                user = new Usuario();
                user.setPass(request.getParameter("pulsera"));
                user.setUser(request.getParameter("dni"));
                user.setColor("RED");
                user.setTipo("JUGADOR");
                user.setIdJugador(j.getId());
                int iusuario = usuarioDAO.insertUsuario(user);
                if (iusuario > 0) {
                  mensaje = "<strong>" + j.getNombre() + " " + j.getApellidos() + "</strong> inscrito o modificado correctamente." + "<br>Usuario para acceder: " + user.getUser() + "<br>Clave de Acceso: " + user.getPass();
                  tipo = TipoMensaje.SUCCESS;
                  boolean preinscrito = !(request.getParameter("preinscrito") == null);
                  IMonederoDAO monedero = db.getMonederoDAO();
                  int totalJesetas = preinscrito ? 2 : 1;
                  for (int i = 0; i < totalJesetas; i++) {
                    Jeseta jesaux = new Jeseta();
                    jesaux.setDescripcion("Jeseta de Preinscripcion");
                    jesaux.setTipo("LOGRO");
                    jesaux.setIdJugador(j.getId());
                    jesaux.setUsado(false);
                    monedero.insertJeseta(jesaux);
                  } 
                  break;
                } 
                mensaje = "Ha habido un problema al guardar el usuario. Jugador " + j.getNombre() + " creado correctamente pero no se ha generado usuario." + "<br>Revisad que el DNI este cumplimentado y correcto.";
                tipo = TipoMensaje.WARNING;
                break;
              } 
              user.setPass(request.getParameter("pulsera"));
              user.setUser(request.getParameter("dni"));
              if (!usuarioDAO.setUsuario(user)) {
                mensaje = "Ha habido un problema al guardar el usuario. Jugador " + j.getNombre() + " creado correctamente pero no se ha modificado los datos del usuario." + "<br>Revisad que el DNI este cumplimentado y correcto.";
                tipo = TipoMensaje.WARNING;
                break;
              } 
              mensaje = "<strong>" + j.getNombre() + " " + j.getApellidos() + "</strong> inscrito o modificado correctamente." + "<br>Usuario para acceder: " + user.getUser() + "<br>Clave de Acceso: " + user.getPass();
              tipo = TipoMensaje.SUCCESS;
              break;
            } 
            mensaje = "Error al inscribir o modificar a <strong>" + j.getNombre() + " " + j.getApellidos() + "</strong>. El usuario puede no existir o la pulsera ya existe";
            tipo = TipoMensaje.WARNING;
            break;
        } 
      } 
    } catch (Exception e) {
      mensaje = "Error con <strong>" + nombre + " " + apellidos + "</strong>. ";
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
