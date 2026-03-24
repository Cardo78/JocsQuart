package servlet;

import dto.Editorial;
import dto.Jeseta;
import dto.Juego;
import dto.Ubicacion;
import dto.Usuario;
import dto.enums.TipoMensaje;
import interfazDB.IDBDAO;
import interfazDB.IEditorialDAO;
import interfazDB.IJuegoDAO;
import interfazDB.IMonederoDAO;
import interfazDB.IUbicacionDAO;
import interfazDB.IUsuarioDAO;
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
import servlet.configController;

@WebServlet({"/configController"})
public class configController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private final String descripcion = "Jeseta para transferir";
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    Usuario admin = (Usuario)session.getAttribute("usuario");
    String action = request.getParameter("action");
    String url = "./jspx/configuracion.jsp";
    String mensaje = null;
    TipoMensaje tipo = null;
    try {
      IDBDAO db = IDBDAO.getBD();
      IUbicacionDAO ub = db.getUbicacionDAO();
      IUsuarioDAO userDAO = db.getUsuarioDAO();
      IEditorialDAO edDAO = db.getEditorialDAO();
      IMonederoDAO monederoDAO = db.getMonederoDAO();
      List<Ubicacion> ubicaciones = ub.getUbicaciones();
      List<Usuario> usuarios = userDAO.getUsuarios();
      List<Editorial> editoriales = edDAO.getEditoriales();
      Usuario user = null;
      Editorial editorial = null;
      int jesetas = 0;
      if (action != null) {
        IJuegoDAO joc;
        List<Juego> disponibles;
        List<Ubicacion> ubicacionmarc;
        int i;
        int idUsuario;
        int idEditorialSave;
        int idEditorial;
        String str;
        switch ((str = action).hashCode()) {
          case -2072569112:
            if (!str.equals("saveUser"))
              break; 
            i = (request.getParameter("idUser") != null) ? Integer.parseInt(request.getParameter("idUser")) : -1;
            if (i > 0) {
              user = userDAO.getUsuario(i);
              user.setPass(request.getParameter("passUser"));
              user.setUser(request.getParameter("nomUser"));
              user.setColor(request.getParameter("select_color"));
              if (userDAO.setUsuario(user)) {
                mensaje = "<strong>" + user.getUser() + "</strong> modificado correctamente.";
                tipo = TipoMensaje.SUCCESS;
              } else {
                mensaje = "Ha habido un problema al guardar el usuario " + user.getUser() + "." + 
                  "<br>Revisad que los datos proporcionados son correctos.";
                tipo = TipoMensaje.WARNING;
              } 
            } else {
              user = new Usuario();
              user.setPass(request.getParameter("passUser"));
              user.setUser(request.getParameter("nomUser"));
              user.setColor(request.getParameter("select_color"));
              user.setTipo("NORMAL");
              i = userDAO.insertUsuario(user);
              if (i > 0) {
                mensaje = "<strong>" + user.getUser() + "</strong> creado correctamente.";
                tipo = TipoMensaje.SUCCESS;
              } else {
                mensaje = "Ha habido un problema al guardar el usuario " + user.getUser() + "." + 
                  "<br>Revisad que los datos proporcionados son correctos.";
                tipo = TipoMensaje.WARNING;
              } 
            } 
            jesetas = (request.getParameter("jesetas") != null) ? Integer.parseInt(request.getParameter("jesetas")) : 0;
            if (jesetas > 0) {
              int j = monederoDAO.getTotalJesetasUsuario(i);
              while (j < jesetas) {
                Jeseta jesaux = new Jeseta();
                jesaux.setDescripcion("Jeseta para transferir");
                jesaux.setTipo("TIENDA");
                jesaux.setIdUsuario(i);
                jesaux.setUsado(false);
                monederoDAO.insertJeseta(jesaux);
                j++;
              } 
            } 
            break;
          case -1687709663:
            if (!str.equals("anyadirJesetas"))
              break; 
            jesetas = (request.getParameter("jesetas") != null) ? Integer.parseInt(request.getParameter("jesetas")) : 0;
            if (jesetas > 0) {
              int j = monederoDAO.getTotalJesetasUsuario(admin.getId());
              while (j < jesetas) {
                Jeseta jesaux = new Jeseta();
                jesaux.setDescripcion("Jeseta para transferir");
                jesaux.setTipo("ADMINISTRACION");
                jesaux.setIdUsuario(admin.getId());
                jesaux.setUsado(false);
                monederoDAO.insertJeseta(jesaux);
                j++;
              } 
            } 
            break;
          case -1502126663:
            if (!str.equals("cargaEditorial"))
              break; 
            if (request.getParameter("idEditorial") == "") {
              request.setAttribute("idEditorial", Integer.valueOf(-1));
              request.setAttribute("nomEditorial", "");
              request.setAttribute("logoEditorial", "");
              request.setAttribute("ordenEditorial", Integer.valueOf(99));
              break;
            } 
            idEditorial = (request.getParameter("idEditorial") != null) ? Integer.parseInt(request.getParameter("idEditorial")) : -1;
            if (idEditorial > -1) {
              editorial = edDAO.getEditorial(idEditorial);
              request.setAttribute("idEditorial", Integer.valueOf(editorial.getId()));
              request.setAttribute("nomEditorial", editorial.getNombre());
              request.setAttribute("logoEditorial", editorial.getLogo());
              request.setAttribute("ordenEditorial", Integer.valueOf(editorial.getOrden()));
            } 
            break;
          case -1310516119:
            if (!str.equals("disponibilidades"))
              break; 
            joc = db.getJuegoDAO();
            disponibles = new ArrayList<>();
            ubicacionmarc = new ArrayList<>();
            for (Ubicacion ubicacion : ubicaciones) {
              String esta = request.getParameter("ubicacion" + ubicacion.getId());
              if (esta != null) {
                ubicacionmarc.add(ubicacion);
                List<Juego> juegos = joc.getJuegosxUbicacion(ubicacion.getId());
                for (Juego aux : juegos)
                  disponibles.add(aux); 
              } 
            } 
            url = "./jspx/disponible.jsp";
            request.setAttribute("contador", Integer.valueOf(48));
            request.setAttribute("disponibles", disponibles);
            request.setAttribute("ubicaciones", ubicacionmarc);
            break;
          case -158220135:
            if (!str.equals("cargaUser"))
              break; 
            if (request.getParameter("idUser") == "") {
              request.setAttribute("idUser", Integer.valueOf(-1));
              request.setAttribute("nomUser", "");
              request.setAttribute("passUser", "");
              request.setAttribute("colorUser", "BLACK");
              request.setAttribute("jesetasUser", Integer.valueOf(0));
              break;
            } 
            idUsuario = (request.getParameter("idUser") != null) ? Integer.parseInt(request.getParameter("idUser")) : -1;
            if (idUsuario > -1) {
              user = userDAO.getUsuario(idUsuario);
              request.setAttribute("idUser", Integer.valueOf(user.getId()));
              request.setAttribute("nomUser", user.getUser());
              request.setAttribute("passUser", user.getPass());
              request.setAttribute("colorUser", user.getColor());
              jesetas = monederoDAO.getTotalJesetasUsuario(user.getId());
              request.setAttribute("jesetasUser", Integer.valueOf(jesetas));
            } 
            break;
          case 447290570:
            if (!str.equals("saveEditorial"))
              break; 
            idEditorialSave = (request.getParameter("idEditorial") != null) ? Integer.parseInt(request.getParameter("idEditorial")) : -1;
            if (idEditorialSave != -1) {
              editorial = edDAO.getEditorial(idEditorialSave);
              editorial.setNombre(request.getParameter("nomEditorial"));
              editorial.setLogo(request.getParameter("logoEditorial"));
              editorial.setOrden((request.getParameter("ordenEditorial") != null) ? Integer.parseInt(request.getParameter("ordenEditorial")) : 99);
              if (edDAO.setEditorial(editorial)) {
                mensaje = "<strong>Editorial " + editorial.getNombre() + "</strong> creada correctamente.";
                tipo = TipoMensaje.SUCCESS;
                break;
              } 
              mensaje = "Ha habido un problema al guardar la Editorial " + editorial.getNombre() + "." + "<br>Revisad que los datos proporcionados son correctos.";
              tipo = TipoMensaje.WARNING;
              break;
            } 
            editorial = new Editorial();
            editorial.setNombre(request.getParameter("nomEditorial"));
            editorial.setLogo(request.getParameter("logoEditorial"));
            editorial.setOrden((request.getParameter("ordenEditorial") != null) ? Integer.parseInt(request.getParameter("ordenEditorial")) : 99);
            idEditorialSave = edDAO.insertEditorial(editorial);
            if (idEditorialSave > 0) {
              mensaje = "<strong>Editorial " + editorial.getNombre() + "</strong> creada correctamente.";
              tipo = TipoMensaje.SUCCESS;
              break;
            } 
            mensaje = "Ha habido un problema al guardar la Editorial " + editorial.getNombre() + "." + "<br>Revisad que los datos proporcionados son correctos.";
            tipo = TipoMensaje.WARNING;
            break;
        } 
      } 
      int cryptoAdmin = monederoDAO.getTotalJesetasUsuario(admin.getId());
      request.setAttribute("jesetasAdmin", Integer.valueOf(cryptoAdmin));
      request.setAttribute("ubicaciones", ubicaciones);
      request.setAttribute("usuarios", usuarios);
      request.setAttribute("editoriales", editoriales);
    } catch (Exception e) {
      mensaje = e.getMessage();
      tipo = TipoMensaje.ERROR;
    } finally {
      request.setAttribute("mensaje", mensaje);
      if (tipo != null)
        request.setAttribute("tipoMensaje", tipo.getCSSClass()); 
      request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  protected Ubicacion insertarUbicacion(HttpServletRequest request, IDBDAO db) throws Exception {
    int idUb = (request.getParameter("select_ubicacion") != null) ? Integer.parseInt(request.getParameter("select_ubicacion")) : -1;
    IUbicacionDAO ubean = db.getUbicacionDAO();
    Ubicacion ubicacion = null;
    if (idUb > 0)
      ubicacion = ubean.getUbicacion(idUb); 
    if (idUb == 0) {
      ubicacion = new Ubicacion();
      ubicacion.setColor("#000");
      ubicacion.setNombre(request.getParameter("nombreUbicacion"));
      idUb = ubean.insertUbicacion(ubicacion);
      if (idUb > 0)
        ubicacion.setId(idUb); 
    } 
    return ubicacion;
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
