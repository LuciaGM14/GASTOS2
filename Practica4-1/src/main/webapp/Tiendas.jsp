<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tabla de Tiendas</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ page session="true" import="java.util.*, es.studium.MVC.*" %>
    <div class="container">
        <h1>Tabla de Tiendas</h1>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre de Tienda</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                // Obtener la lista de tiendas del modelo
                Modelo modelo = new Modelo();
                ArrayList<Tienda> listaTiendas = modelo.listaTiendasTienda();
                
                // Iterar sobre la lista de tiendas y mostrar cada una en una fila de la tabla
                for (Tienda tienda : listaTiendas) {
                %>
                <tr>
                    <td><%= tienda.getIdTienda() %></td>
                    <td><%= tienda.getNombreTienda() %></td>
                    <td>
                        <!-- Botón para editar la tienda -->
                        <form action="EditarTiendaServlet" method="post" style="display: inline;">
                            <input type="hidden" name="idTienda" value="<%= tienda.getIdTienda() %>">
                            <button type="submit" class="btn btn-primary">Editar</button>
                        </form>
                        <!-- Botón para eliminar la tienda -->
                        <form action="EliminarTiendaServlet" method="post" style="display: inline;">
                            <input type="hidden" name="idTienda" value="<%= tienda.getIdTienda() %>">
                            <button type="submit" class="btn btn-danger">Eliminar</button>
                        </form>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <!-- Botón para agregar una nueva tienda -->
        <form action="NuevaTiendaServlet" method="post">
            <button type="submit" class="btn btn-success">Agregar Nueva Tienda</button>
        </form>
    </div>
    
    <!-- Bootstrap JS and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
