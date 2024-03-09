

    package es.studium.MVC;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/CrearCompraServlet")
public class CrearCompraServlet extends HttpServlet {
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
        String fechaCompraStr = request.getParameter("fecha");
        
        // Crear una instancia del formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date fechaCompra = sdf.parse(fechaCompraStr); // Convertir la cadena de texto a Date
            String fechaCompraFormateada = sdf.format(fechaCompra); // Formatear la fecha

            // Obtener el importe del formulario
            String importeStr = request.getParameter("importe");
            // Convertir el importe a tipo double
            double importeCompra = Double.parseDouble(importeStr);

            // Obtener el ID del usuario de la sesión
            HttpSession sesion = request.getSession();
            int idUsuarioFK = (int) sesion.getAttribute("usuarioLogueado");

            // Obtener el ID de la tienda seleccionada del formulario
            int idTiendaSeleccionada = Integer.parseInt(request.getParameter("tienda"));

            // Crear una instancia del modelo
            Modelo modelo = new Modelo();

            // Intentar crear la nueva compra en la base de datos
            modelo.crearNuevaCompra(fechaCompraFormateada, idTiendaSeleccionada, importeCompra, idUsuarioFK);

            // Actualizar la lista de compras en la sesión
            ArrayList<Compra> compras = modelo.DatosCompra(idUsuarioFK);
            sesion.setAttribute("listadoCompra", compras);

            // Redirigir al usuario de vuelta a la página principal
            response.sendRedirect(request.getContextPath() + "/Principal.jsp");
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
            // Manejar cualquier error que ocurra al crear la compra
            // Aquí podrías redirigir a una página de error o mostrar un mensaje al usuario
            // Por ejemplo:
            response.getWriter().println("Error al crear la compra.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si se hace una petición GET al servlet, simplemente redirigimos de nuevo a la página principal
        response.sendRedirect(request.getContextPath() + "/Principal.jsp");
    }
}
