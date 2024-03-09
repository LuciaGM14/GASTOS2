package es.studium.MVC;


import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NuevaTiendaServlet
 */


import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Servlet implementation class GuardarNuevaTiendaServlet
 */
@WebServlet("/NuevaTiendaServlet")
public class NuevaTiendaServlet extends HttpServlet {
    /**
	 * 
	 */
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

	// NuevaTiendaServlet.java
	   protected void doPost(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {
	       // Obtener el nombre de la tienda del formulario
	       String nombreTienda = request.getParameter("nombreTienda");

	       // Crear una nueva instancia del modelo
	       Modelo modelo = new Modelo();

	       try {
	           // Llamar al método para crear una nueva tienda en la base de datos
	           modelo.crearNuevaTienda(nombreTienda);
	       } catch (SQLException e) {
	           e.printStackTrace();
	       }

	       // Redirigir al usuario de vuelta a la página de nueva tienda
	       response.sendRedirect("NuevaTienda.jsp");
	   }
}
