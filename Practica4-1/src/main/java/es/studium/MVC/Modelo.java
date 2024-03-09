package es.studium.MVC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Modelo {
    private Connection conn;

    public Modelo() {
        try {
            String userName = "usuarioAplicacion";
            String password = "usuarioAplicacion";
            String url = "jdbc:mysql://localhost:3306/aplicacionTienda";
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int loginCorrecto(String username, String password) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM usuarios WHERE nombreUsuario = '"+username+"' AND claveUsuario = SHA2('"+password+"', 256);");
            rs = pstmt.executeQuery();
            if(rs.next()) {
            	System.out.println(rs.getInt("idUsuario"));
                return rs.getInt("idUsuario");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            cerrarRecursos(pstmt, rs);
        }
    }
    
    public String obtenerMesActual() {
        Calendar calendario = Calendar.getInstance();
        int mes = calendario.get(Calendar.MONTH) + 1; // Sumamos 1 porque enero es 0
        int anio = calendario.get(Calendar.YEAR);
        String[] nombresMeses = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
        return nombresMeses[mes - 1] + " " + anio;
    }
    
    public double obtenerTotalCompras() {
        double total = 0.0; // Cambiar el tipo de dato de int a double
        Calendar calendario = Calendar.getInstance();
        int mes = calendario.get(Calendar.MONTH) + 1; // Sumamos 1 porque enero es 0
        int anio = calendario.get(Calendar.YEAR);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT SUM(importeCompra) AS total FROM compras WHERE MONTH(fechaCompra) = ? AND "
                    + "YEAR(fechaCompra) = ?");
            pstmt.setInt(1, mes);
            pstmt.setInt(2, anio);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total"); // Usar getDouble() en lugar de getInt()
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(pstmt, rs);
        }
        return total;
    }



    public ArrayList<Compra> DatosCompra(int idUsuarioFK) {
        ArrayList<Compra> listaCompras = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();

            // Supongamos que tienes una fecha en formato Date
            Date fecha = new Date();
            // Crear una instancia de Calendar y establecer la fecha
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            // Obtener el mes y el año de la fecha
            int mes = calendar.get(Calendar.MONTH) + 1; // Se suma 1 porque en Calendar, enero es el mes 0
            int anio = calendar.get(Calendar.YEAR);

            String sqlStr = "SELECT idCompra, fechaCompra, importeCompra, nombreTienda, idUsuarioFK, idTiendaFK " +
                            "FROM compras " +
                            "INNER JOIN tiendas ON idTiendaFK = idTienda " +
                            "WHERE MONTH(fechaCompra) = " + mes + " AND YEAR(fechaCompra) = " + anio +
                            " AND idUsuarioFK = " + idUsuarioFK + " ORDER BY fechaCompra ASC;";
            rs = stmt.executeQuery(sqlStr);
            
            // Iterar sobre el ResultSet y agregar los resultados a la lista
            while (rs.next()) {
                int id = rs.getInt("idCompra");
                String fechaCompra = rs.getString("fechaCompra");
                double importeCompra = rs.getDouble("importeCompra");
                String nombreTienda = rs.getString("nombreTienda");
                int idUsuarioFK1 = rs.getInt("idUsuarioFK");
                int idTiendaFK = rs.getInt("idTiendaFK");

                // Añadir la compra a la lista
                listaCompras.add(new Compra(id, fechaCompra, importeCompra, nombreTienda, idTiendaFK, idUsuarioFK1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(stmt, rs);
        }
        System.out.println(listaCompras);
        return listaCompras;
    }
    
    public void modificarCompra(int idCompra, String nuevaFechaCompra, double nuevoImporteCompra, int nuevoIdTiendaFK) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("UPDATE compras SET fechaCompra = ?, importeCompra = ?, idTiendaFK = ? WHERE idCompra = ?");
            pstmt.setString(1, nuevaFechaCompra);
            pstmt.setDouble(2, nuevoImporteCompra);
            pstmt.setInt(3, nuevoIdTiendaFK);
            pstmt.setInt(4, idCompra);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(pstmt, null);
        }
    }
    
    public void eliminarCompra(int idCompra) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("DELETE FROM compras WHERE idCompra = ?");
            pstmt.setInt(1, idCompra);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(pstmt, null);
        }
    }
    
    public void crearNuevaCompra(String fechaCompra, int idTiendaFK, double importeCompra, int idUsuarioFK) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO compras (fechaCompra,idTiendaFK, importeCompra, idUsuarioFK) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, fechaCompra);
            pstmt.setInt(2, idTiendaFK);
            pstmt.setDouble(3, importeCompra);
            pstmt.setInt(4, idUsuarioFK);
            pstmt.executeUpdate();
        } finally {
            cerrarRecursos(pstmt, null);
        }
    }
    
    public ArrayList<Tienda> listaTiendasTienda() {
        ArrayList<Tienda> tiendasTienda = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT idTienda, nombreTienda FROM tiendas;");
            rs = pstmt.executeQuery();

            // Iterar sobre el ResultSet y agregar los resultados a la lista
            while (rs.next()) {
                int idTienda = rs.getInt("idTienda");
                String nombreTienda = rs.getString("nombreTienda");
                // Añadir la tienda a la lista
                tiendasTienda.add(new Tienda(idTienda, nombreTienda));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(pstmt, rs);
        }
        return tiendasTienda;
    }
    
    
    public void borrarTienda(int idTienda) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("DELETE FROM tiendas WHERE idTienda = ?");
            pstmt.setInt(1, idTienda);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(pstmt, null);
        }
    }
    
    public void crearNuevaTienda(String nombreTienda) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO tiendas (nombreTienda) VALUES (?)");
            
            pstmt.setString(1, nombreTienda);
            
            pstmt.executeUpdate();
        } finally {
            cerrarRecursos(pstmt, null);
        }
    }

    

        public ArrayList<Compra> DatosCompraPorMes(int idUsuarioFK, int mes) {
            ArrayList<Compra> compras = new ArrayList<>();
            Statement stmt = null;
            ResultSet rs = null;
            
            try {
                // Suponemos que conn es una variable de instancia ya definida y conectada a tu base de datos
                stmt = conn.createStatement();
                // Supongamos que tienes una fecha en formato Date
                Date fecha = new Date();
                // Crear una instancia de Calendar y establecer la fecha
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);
                

                // Consulta SQL directa sin usar PreparedStatement
                String sql = "SELECT idCompra, fechaCompra, importeCompra, nombreTienda, idUsuarioFK, idTiendaFK " +
                        "FROM compras " +
                        "INNER JOIN tiendas ON idTiendaFK = idTienda " +
                        "WHERE MONTH(fechaCompra) = " + mes +
                        " AND idUsuarioFK = " + idUsuarioFK + " ORDER BY fechaCompra ASC;";

                // Ejecutar la consulta
                rs = stmt.executeQuery(sql);

                // Procesar el ResultSet
                while (rs.next()) {
                    int idCompra = rs.getInt("idCompra");
                    String fechaCompra = rs.getString("fechaCompra");
                    double importeCompra = rs.getDouble("importeCompra");
                    String nombreTienda = rs.getString("nombreTienda");
                    int idTiendaFK = rs.getInt("idTiendaFK");
                    

                    // Añadir la compra a la lista
                    Compra compra = new Compra(idCompra, fechaCompra, importeCompra,nombreTienda, idTiendaFK, idUsuarioFK);
                    compras.add(compra);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Asegúrate de cerrar tus recursos
                if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
                if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
            }

            return compras;
        }
    
        
        public boolean tieneComprasAsociadas(int idTienda) throws SQLException {
		    boolean tieneCompras = false;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    try {
		        pstmt = conn.prepareStatement("SELECT COUNT(*) AS total FROM compras WHERE idTiendaFK = ?");
		        pstmt.setInt(1, idTienda);
		        rs = pstmt.executeQuery();
		        if (rs.next()) {
		            int totalCompras = rs.getInt("total");
		            tieneCompras = (totalCompras > 0);
		        }
		    } finally {
		        cerrarRecursos(pstmt, rs);
		    }
		    return tieneCompras;
		}
        
        public ArrayList<String> obtenerFechasUnicas() {
            ArrayList<String> fechas = new ArrayList<>();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                String query = "SELECT DISTINCT fechaCompra FROM compras";
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    fechas.add(rs.getString("fechaCompra"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Cerrar recursos
                cerrarRecursos(stmt, rs);
            }

            return fechas;
        }
    

	private void cerrarRecursos(Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

	
		

	
}

	


