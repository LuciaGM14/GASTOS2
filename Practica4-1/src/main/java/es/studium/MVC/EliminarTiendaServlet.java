package es.studium.MVC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/EliminarTiendaServlet")
public class EliminarTiendaServlet extends HttpServlet {
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
        try {
            // Obtener el ID de la tienda a eliminar del parámetro enviado desde el formulario
            int idTienda = Integer.parseInt(request.getParameter("idTienda"));

            // Crear una instancia del modelo
            Modelo modelo = new Modelo();

            // Verificar si la tienda tiene compras asociadas
            boolean tieneCompras = modelo.tieneComprasAsociadas(idTienda);

            if (tieneCompras) {
                // Si tiene compras asociadas, enviar un mensaje de alerta al JSP
                request.setAttribute("error", "No se puede eliminar la tienda porque tiene compras asociadas.");
            } else {
                // Si no tiene compras asociadas, eliminar la tienda utilizando el modelo
                modelo.borrarTienda(idTienda);
                request.setAttribute("mensaje", "La tienda se ha eliminado correctamente.");
            }

            // Obtener y actualizar la lista de tiendas
            ArrayList<Tienda> tiendas = modelo.listaTiendasTienda();
            HttpSession session = request.getSession();
            session.setAttribute("tiendas", tiendas);

            // Redirigir de vuelta al JSP de listado de tiendas
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Tiendas.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            // Manejar errores de conversión de número
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al procesar la solicitud");
        } catch (SQLException e) {
            // Manejar errores de SQL
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar la tienda.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Tiendas.jsp");
            dispatcher.forward(request, response);
        }
    }
}
