package es.studium.MVC;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/InformeServlet")
public class InformeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    public InformeServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fechaSeleccionada = request.getParameter("fechaSeleccionada");
        ArrayList<Compra> compras = new ArrayList<>();
        
        


        request.setAttribute("compras", compras);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Informe.jsp");
        dispatcher.forward(request, response);
    }
}

