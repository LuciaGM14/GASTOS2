package es.studium.MVC;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init() throws ServletException {
        try {
            InitialContext initContext = new InitialContext();
            @SuppressWarnings("unused")
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/aplicacionTienda");
        } catch (Exception e) {
            throw new ServletException("Error al inicializar el pool de conexiones", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nextPage = "";
        Modelo modelo = new Modelo();
        int idUsuarioFK = modelo.loginCorrecto(username, password);
        if (idUsuarioFK != -1){
        	request.getSession().setAttribute("usuarioLogueado", idUsuarioFK);
        	nextPage = "/ServletPrincipal";
        } else {
            request.setAttribute("error", "Credenciales incorrectas");
            nextPage = "/Login.jsp";
        }
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(nextPage);
        requestDispatcher.forward(request, response);
    	
    }
}
