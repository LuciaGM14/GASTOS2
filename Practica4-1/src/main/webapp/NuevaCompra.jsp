<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" import="java.util.*, es.studium.MVC.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nueva Compra</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Nueva Compra</h1>
        <form action="CrearCompraServlet" method="post" id="formularioCompra">
            <div class="form-group">
                <label for="fecha">Fecha de Compra:</label>
                <input type="date" id="fecha" name="fecha" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="tienda">Tienda:</label>
                <select id="tienda" name="tienda" class="form-control" required>
                    <!-- Aquí se generan las opciones para seleccionar la tienda -->
                    <%
                    // Obtener la lista de tiendas del request
                    Modelo modelo = new Modelo();
                    ArrayList<Tienda> tiendas = modelo.listaTiendasTienda();

                    // Iterar sobre la lista de tiendas y generar las opciones en el ComboBox
                    for (Tienda tienda : tiendas) {
                    %>
                    <option value="<%= tienda.getIdTienda() %>"><%= tienda.getNombreTienda() %></option>
                    <%
                    }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label for="importe">Importe:</label>
                <input type="number" id="importe" name="importe" step="0.01" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Guardar Compra</button>
            <button type="button" class="btn btn-secondary" onclick="limpiarFormulario()">Limpiar Formulario</button>
        </form>
    </div>
    
    <!-- Bootstrap JS and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <!-- Script para limpiar los campos del formulario -->
    <script>
        function limpiarFormulario() {
            document.getElementById("formularioCompra").reset();
        }
    </script>
</body>
</html>
