package es.studium.MVC;


import java.io.IOException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/ServletPrincipal")
public class ServletPrincipal extends HttpServlet {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Modelo modelo = new Modelo();
        ArrayList<Tienda> tiendas = modelo.listaTiendasTienda();
        
        // Guardar las tiendas en la sesión
        HttpSession sesion = request.getSession();
        sesion.setAttribute("tiendas", tiendas);
        
        // Redirigir a Principal.jsp
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/Principal.jsp");
        requestDispatcher.forward(request, response);
    }

    	
   
    }
