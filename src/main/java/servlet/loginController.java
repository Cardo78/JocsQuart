package servlet;

import DatosDB.UsuarioDAO;
import DatosDB.JugadorDAO;
import dto.Usuario;
import dto.Jugador;
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
    String url = "./jspx/login.jsp";
    
    String action = request.getParameter("action");
    String user = request.getParameter("inputUser");
    String pass = request.getParameter("inputPass");
    Usuario u = null;
    String mensaje = "Si es la primera vez que entras, introduce una clave. Sino debes indicar la clave introducida anteriormente.";
    String callout = "callout-info";
    
    try {
    	if(action !=null) {
    		switch (action) {
        		case "loguear":
        			if (user != null && pass != null) {
        		        UsuarioDAO usuarioDAO = new UsuarioDAO();
        		        u = usuarioDAO.getUsuarioName(user);
        		        if(u.getUser()== null) {
        		            mensaje = "El nº de pulsera no existe. Introduzca el nº correcto.";
        		            callout = "callout-danger";        		           
        		        }
        		        else {
        		        	if(u.getPass() == null) {
        		        		u.setPass(pass);
        		        		usuarioDAO.setUsuario(u);
        		                url = "./index.jsp";
        		                session.setAttribute("usuario", u);
        		        	}
        		        	else {
        		        		if (u.getPass().equals(pass)) {
        		                    url = "./index.jsp";
        			                session.setAttribute("usuario", u);
        		                } 
        		        		else {
        		                    mensaje = "Login Incorrecto " + user + ": Compruebe que la clave introducida es correcta.";
        		                    callout = "callout-danger";
        		                    u = null;
        		                    url = "./jspx/login.jsp";
        		                } 
        		        	 }
        		        }	
        			}
    			break;	
    			
        		case "recuperar":
        			mensaje = "Para recuperar la clave, es obligatorio indicar nº de pulsera y DNI. En caso contrario solicite que le reseteen la clave.";
        			url = "./jspx/recuperarClave.jsp";
        		break;
            		
            	
        		case "actualizar":
        			JugadorDAO jugador = new JugadorDAO();
		        	Jugador j = jugador.getJugadorPulsera(user);
		        	if(j == null) {
		        		mensaje = "El nº de pulsera no existe. Introduzca el nº correcto.";
		            	callout = "callout-danger"; 
		            	url = "./jspx/recuperarClave.jsp";
		        	}
		        	else {
		        	    String dni = request.getParameter("inputDNI");
		        		if(!j.getDni().equals(dni)) {
		        			mensaje = "El DNI indicado no coincide con el de su pulsera. Compruebe que el DNI introducido es el correcto.";
			            	callout = "callout-danger"; 
			            	url = "./jspx/recuperarClave.jsp";
		        		}
		        		else {
		        			UsuarioDAO usuarioDAO = new UsuarioDAO();
		    		        u = usuarioDAO.getUsuarioName(user);
		    		        if(u == null) {
		    		        	mensaje = "Su nº de pulsera no está correcto sobre sus datos. Vaya a administración para que se lo corrijan.";
				            	callout = "callout-danger"; 
				            	url = "./jspx/login.jsp";
		    		        }
		    		        else {
		    		        	pass = request.getParameter("inputPass");		    		        	
		    		        	u.setPass(pass);
		    		        	if(usuarioDAO.setUsuario(u)) {
		    		        		mensaje = "Clave actualizada correctamente, acceda con su nueva información.";
		    		        		callout = "callout-info";
		    		        		url = "./jspx/login.jsp";
		    		        	}		    		        				    		        			    		        	
		    		        }		    		        
		        		}
		        	}
		            	

    		        if(u == null) {
    		            
    		        }else {
    		        	
    		        }
        			url = "./jspx/login.jsp";
        		break;
        	
    		}
    	}
    } catch (Exception e) {    	
      url = "./jspx/login.jsp";
      mensaje = e.getMessage();
    } finally {
    	request.setAttribute("user", user);
    	request.setAttribute("mensaje", mensaje);
    	request.setAttribute("callout", callout);
    	request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}

