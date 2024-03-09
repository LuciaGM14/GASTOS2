package es.studium.MVC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/GuardarNuevaTiendaServlet")
public class GuardarNuevaTiendaServlet extends HttpServlet {
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
        // Obtener el nombre de la tienda del parámetro enviado desde el formulario
        String nombreTienda = request.getParameter("nombreTienda");

        // Obtener el ID de usuario de la sesión
        HttpSession session = request.getSession();
        @SuppressWarnings("unused")
		int idUsuario = (int) session.getAttribute("usuarioLogueado");

        // Crear una nueva instancia del modelo
        Modelo modelo = new Modelo();

        try {
            // Llamar al método para crear una nueva tienda en la base de datos
            modelo.crearNuevaTienda(nombreTienda);

            // Actualizar la lista de tiendas en la sesión
            ArrayList<Tienda> tiendas = modelo.listaTiendasTienda();
            session.setAttribute("tiendas", tiendas);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Redirigir al usuario de vuelta al listado de tiendas
        response.sendRedirect("Tiendas.jsp");
    }
}
