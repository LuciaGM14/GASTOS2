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

@WebServlet("/NuevaCompraServlet")
public class NuevaCompraServlet extends HttpServlet {
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
        // Obtener la sesión actual o crear una nueva si no existe
        HttpSession session = request.getSession(true);
        
        // Guardar algún dato en la sesión si es necesario
        session.setAttribute("usuarioLogueado", (int) session.getAttribute("usuarioLogueado"));
        
        // Redirigir al usuario a la página de nueva compra
        response.sendRedirect(request.getContextPath() + "/NuevaCompra.jsp");
    }
}
