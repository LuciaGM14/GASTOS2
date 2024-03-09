package es.studium.MVC;

public class DatosCompra {
    private String fecha;
    private String tienda;
    private double importe;

    public DatosCompra(String fecha, String tienda, double importe) {
        this.setFecha(fecha);
        this.setTienda(tienda);
        this.setImporte(importe);
    }

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTienda() {
		return tienda;
	}

	public void setTienda(String tienda) {
		this.tienda = tienda;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

   
}