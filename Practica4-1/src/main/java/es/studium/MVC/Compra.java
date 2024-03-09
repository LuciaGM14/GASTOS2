package es.studium.MVC;

public class Compra {    
    private int idCompra;
    private String fechaCompra;
    private double importeCompra;
    private String nombreTienda;
    private int idTiendaFK;
    private int idUsuarioFK;
    
  
    public Compra(int idCompra, String fechaCompra, double importeCompra, String nombreTienda, int idTiendaFK, int idUsuarioFK) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.importeCompra = importeCompra;
        this.nombreTienda = nombreTienda;
        this.idTiendaFK = idTiendaFK;
        this.idUsuarioFK = idUsuarioFK;
    }
    

	public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getImporteCompra() {
        return importeCompra;
    }

    public void setImporteCompra(double importeCompra) {
        this.importeCompra = importeCompra;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public int getIdTiendaFK() {
        return idTiendaFK;
    }

    public void setIdTiendaFK(int idTiendaFK) {
        this.idTiendaFK = idTiendaFK;
    }

    public int getIdUsuarioFK() {
        return idUsuarioFK;
    }

    public void setIdUsuarioFK(int idUsuarioFK) {
        this.idUsuarioFK = idUsuarioFK;
    }
}
