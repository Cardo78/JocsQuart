package servlet;

import dto.Editorial;
import dto.Juego;
import dto.JuegoBase;
import dto.Ubicacion;
import dto.enums.TipoMensaje;
import interfazDB.IDBDAO;
import interfazDB.IEditorialDAO;
import interfazDB.IJuegoDAO;
import interfazDB.IUbicacionDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlet.juegoController;

@WebServlet({"/juegoController"})
public class juegoController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    String url = "./jspx/buscarJuego.jsp";
    String mensaje = null;
    String mensajeEAN = null;
    TipoMensaje tipo = null;
    String nombre = "";
    try {
      if (action != null) {
        IJuegoDAO game;
        List<JuegoBase> juegos;
        int index;
        IEditorialDAO ed;
        List<Editorial> editoriales;
        IUbicacionDAO ub;
        List<Ubicacion> ubicaciones;
        IEditorialDAO edDAO;
        int idEditorial;
        Editorial edt;
        boolean resultado;
        IDBDAO db = IDBDAO.getBD();
        boolean edicion = false;
        JuegoBase juego = null;
        Juego ean = null;
        int juegoBaseindex = (request.getParameter("idBase") != null) ? Integer.parseInt(request.getParameter("idBase")) : -1;
        String str;
        switch ((str = action).hashCode()) {
          case -1307828293:
            if (!str.equals("editar"))
              break; 
            if (juegoBaseindex > 0) {
              ean = new Juego();
              ean.setId((request.getParameter("id") != null) ? Integer.parseInt(request.getParameter("id")) : -1);
              ean.setEan13(request.getParameter("codigo"));
              ean.setEntregado((request.getParameter("retirado") != null));
              int idUb = (request.getParameter("select_ubicacion") != null) ? Integer.parseInt(request.getParameter("select_ubicacion")) : -1;
              if (idUb > 0) {
                IUbicacionDAO ubean = db.getUbicacionDAO();
                ean.setUbicacion(ubean.getUbicacion(idUb));
              } else {
                ean.setUbicacion(null);
              } 
              IJuegoDAO iJuegoDAO = db.getJuegoDAO();
              if (iJuegoDAO.setJuego(ean, juegoBaseindex)) {
                juego = iJuegoDAO.getJuegoBase(juegoBaseindex);
                mensajeEAN = "CODIGO GUARDADO CORRECTAMENTE";
                edicion = true;
                tipo = TipoMensaje.SUCCESS;
              } 
              url = "./jspx/formularioJuego.jsp";
            } 
            break;
          case 108960:
            if (!str.equals("new"))
              break; 
            ed = db.getEditorialDAO();
            editoriales = ed.getEditoriales();
            request.setAttribute("editoriales", editoriales);
            ub = db.getUbicacionDAO();
            ubicaciones = ub.getUbicaciones();
            request.setAttribute("ubicaciones", ubicaciones);
            url = "./jspx/formularioJuego.jsp";
            break;
          case 3143097:
            if (!str.equals("find"))
              break; 
            nombre = request.getParameter("nombre");
            game = db.getJuegoDAO();
            juegos = game.getJuegos(nombre);
            if (!juegos.isEmpty())
              request.setAttribute("juegos", juegos); 
            url = "./jspx/buscarJuego.jsp";
            break;
          case 3417674:
            if (!str.equals("open"))
              break; 
            game = db.getJuegoDAO();
            index = (request.getParameter("id") != null) ? Integer.parseInt(request.getParameter("id")) : -1;
            if (index > 0) {
              juego = game.getJuegoBase(index);
              edicion = true;
              url = "./jspx/formularioJuego.jsp";
            } 
            break;
          case 3522941:
            if (!str.equals("save"))
              break; 
            juego = new JuegoBase();
            juego.setIdBase((request.getParameter("id") != null) ? Integer.parseInt(request.getParameter("id")) : -1);
            juego.setNombre((request.getParameter("nombre") != null) ? request.getParameter("nombre") : "");
            juego.setDuracion((request.getParameter("duracion") != null) ? Integer.parseInt(request.getParameter("duracion")) : 30);
            juego.setMaxjugadores((request.getParameter("jugMax") != null) ? Integer.parseInt(request.getParameter("jugMax")) : 3);
            juego.setBgg((request.getParameter("bgg") != null) ? request.getParameter("bgg") : "");
            juego.setPasaporte((request.getParameter("pasaporte") != null));
            juego.setRutaImagen(request.getParameter("imagen"));
            juego.setInfantil((request.getParameter("infantil") != null));
            juego.setEdad((request.getParameter("edad") != null) ? Integer.parseInt(request.getParameter("edad")) : 1);
            edDAO = db.getEditorialDAO();
            idEditorial = (request.getParameter("select_editorial") != null) ? Integer.parseInt(request.getParameter("select_editorial")) : 0;
            edt = edDAO.getEditorial(idEditorial);
            juego.setEditorial(edDAO.getEditorial(idEditorial));
            game = db.getJuegoDAO();
            resultado = false;
            if (juego.getIdBase() > 0) {
              resultado = game.setJuegoBase(juego);
              juego.setJuegos(game.getEan(juego.getIdBase(), true));
            } else {
              int id = game.insertJuego(juego);
              juego.setIdBase(id);
            } 
            if (resultado || juego.getIdBase() > 0) {
              mensaje = "JUEGO GUARDADO CORRECTAMENTE!";
              tipo = TipoMensaje.SUCCESS;
              edicion = true;
            } 
            url = "./jspx/formularioJuego.jsp";
            break;
          case 1762892848:
            if (!str.equals("nuevoCodigo"))
              break; 
            if (juegoBaseindex > 0) {
              ean = new Juego();
              ean.setEan13(request.getParameter("ean13"));
              ean.setEntregado(false);
              int idUb = (request.getParameter("select_ubicacion") != null) ? Integer.parseInt(request.getParameter("select_ubicacion")) : -1;
              if (idUb > 0) {
                IUbicacionDAO ubean = db.getUbicacionDAO();
                ean.setUbicacion(ubean.getUbicacion(idUb));
              } else {
                ean.setUbicacion(null);
              } 
              game = db.getJuegoDAO();
              int id = game.insertJuego(ean, juegoBaseindex);
              if (id > 0) {
                juego = game.getJuegoBase(juegoBaseindex);
                mensajeEAN = "CODIGO GUARDADO CORRECTAMENTE";
                edicion = true;
                tipo = TipoMensaje.SUCCESS;
              } 
              url = "./jspx/formularioJuego.jsp";
            } 
            break;
        } 
        if (edicion) {
          request.setAttribute("id", Integer.valueOf(juego.getIdBase()));
          request.setAttribute("nombre", juego.getNombre());
          request.setAttribute("duracion", Integer.valueOf(juego.getDuracion()));
          request.setAttribute("max", Integer.valueOf(juego.getMaxjugadores()));
          request.setAttribute("imagen", juego.getRutaImagen());
          request.setAttribute("bgg", juego.getBgg());
          request.setAttribute("pasaporte", Boolean.valueOf(juego.isPasaporte()));
          request.setAttribute("codigos", juego.getJuegos());
          request.setAttribute("infantil", Boolean.valueOf(juego.isInfantil()));
          request.setAttribute("idEditorial", Integer.valueOf(juego.getEditorial().getId()));
          request.setAttribute("edad", Integer.valueOf(juego.getEdad()));
          IUbicacionDAO iUbicacionDAO = db.getUbicacionDAO();
          List<Ubicacion> list = iUbicacionDAO.getUbicaciones();
          request.setAttribute("ubicaciones", list);
          IEditorialDAO iEditorialDAO = db.getEditorialDAO();
          editoriales = iEditorialDAO.getEditoriales();
          request.setAttribute("editoriales", editoriales);
        } 
      } 
    } catch (Exception e) {
      mensaje = "ERROR: ";
      mensaje = String.valueOf(mensaje) + "Causa del error: " + e.getMessage();
      tipo = TipoMensaje.ERROR;
    } finally {
      request.setAttribute("mensaje", mensaje);
      request.setAttribute("mensajeEAN", mensajeEAN);
      if (tipo != null)
        request.setAttribute("tipoMensaje", tipo.getCSSClass()); 
      request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
