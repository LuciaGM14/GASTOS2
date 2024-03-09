package es.studium.MVC;

public class Usuario {
	 private int idUsuario;
	    private String nombreUsuario;
	    private String correoUsuario;
	    private String claveUsuario;

	    // Constructor
	    public  Usuario() {
	        // Constructor vacío o inicializaciones predeterminadas
	    }

	    public Usuario(int int1, String string, String string2) {
			// TODO Auto-generated constructor stub
		}

		// Getters
	    public int getIdUsuario() {
	        return idUsuario;
	    }

	    public String getNombreUsuario() {
	        return nombreUsuario;
	    }

	    public String getCorreoUsuario() {
	        return correoUsuario;
	    }

	    public String getClaveUsuario() {
	        return claveUsuario;
	    }

	    // Setters
	    public void setIdUsuario(int idUsuario) {
	        this.idUsuario = idUsuario;
	    }

	    public void setNombreUsuario(String nombreUsuario) {
	        this.nombreUsuario = nombreUsuario;
	    }

	    public void setCorreoUsuario(String correoUsuario) {
	        this.correoUsuario = correoUsuario;
	    }

	    public void setClaveUsuario(String claveUsuario) {
	        this.claveUsuario = claveUsuario;
	    }
	}
