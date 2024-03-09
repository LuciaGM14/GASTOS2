package es.studium.MVC;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/ListarTiendaServlet")
public class ListarTiendaServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los datos del formulario
    	String idCompraStr = request.getParameter("idCompra");
    	int idCompra = 0; // Valor predeterminado en caso de que el parámetro sea nulo
    	if (idCompraStr != null && !idCompraStr.isEmpty()) {
    	    idCompra = Integer.parseInt(idCompraStr);
    	}
        String nuevaFechaCompra = request.getParameter("nuevaFecha");
        String nuevoImporteStr = request.getParameter("nuevoImporte");
        double nuevoImporteCompra = 0.0; // Valor predeterminado para evitar NullPointerException
        if (nuevoImporteStr != null && !nuevoImporteStr.isEmpty()) {
            nuevoImporteCompra = Double.parseDouble(nuevoImporteStr.trim());
        }

        int nuevoIdTiendaFK = 0; // Valor predeterminado en caso de que el parámetro sea nulo
        String nuevoIdTiendaFKStr = request.getParameter("nuevoIdTienda");
        if (nuevoIdTiendaFKStr != null && !nuevoIdTiendaFKStr.isEmpty()) {
            nuevoIdTiendaFK = Integer.parseInt(nuevoIdTiendaFKStr);
        }

        // Obtener la sesión
        HttpSession session = request.getSession();
        @SuppressWarnings("unused")
        int idUsuarioFK = (int) session.getAttribute("usuarioLogueado");

        // Crear una nueva instancia del modelo
        Modelo modelo = new Modelo();

        // Llamar al método para modificar la compra en la base de datos
        modelo.modificarCompra(idCompra, nuevaFechaCompra, nuevoImporteCompra, nuevoIdTiendaFK);

        // Redirigir al usuario de vuelta a la página principal
        response.sendRedirect("Tiendas.jsp");
    }
}