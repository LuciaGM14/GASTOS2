package es.studium.MVC;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/MostrarComprasServlet")
public class MostrarComprasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int idUsuario = (int) session.getAttribute("idUsuario"); // Obtener el idUsuario de la sesión
        
        Modelo modelo = new Modelo();
        ArrayList<Compra> compras = modelo.DatosCompra(idUsuario); // Llamar al método DatosCompra con el idUsuario
        request.setAttribute("compras", compras);
        
        // Redirigir o despachar la solicitud al JSP para mostrar los datos de compra
        RequestDispatcher dispatcher = request.getRequestDispatcher("/MostrarCompras.jsp");
        dispatcher.forward(request, response);
    }
}