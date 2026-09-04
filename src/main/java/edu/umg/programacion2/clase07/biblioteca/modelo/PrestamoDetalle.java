package edu.umg.programacion2.clase07.biblioteca.modelo;

public class PrestamoDetalle {

    private final String tituloLibro;
    private final String usuario;
    private final String fechaPrestamo;

    public PrestamoDetalle(String tituloLibro, String usuario, String fechaPrestamo) {
        this.tituloLibro = tituloLibro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getTituloLibro() { return tituloLibro; }
    public String getUsuario() { return usuario; }
    public String getFechaPrestamo() { return fechaPrestamo; }

    @Override
    public String toString() {
        return String.format("\"%s\" prestado a %s desde %s", tituloLibro, usuario, fechaPrestamo);
    }
}