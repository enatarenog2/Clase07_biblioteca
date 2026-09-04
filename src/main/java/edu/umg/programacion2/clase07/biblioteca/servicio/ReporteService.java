package edu.umg.programacion2.clase07.biblioteca.servicio;

import edu.umg.programacion2.clase07.biblioteca.dao.LibroDAO;
import edu.umg.programacion2.clase07.biblioteca.dao.PrestamoDAO;
import edu.umg.programacion2.clase07.biblioteca.modelo.Libro;
import edu.umg.programacion2.clase07.biblioteca.modelo.PrestamoDetalle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteService {

    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;

    public ReporteService() {
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();
    }

    public ReporteService(LibroDAO libroDAO, PrestamoDAO prestamoDAO) {
        this.libroDAO = libroDAO;
        this.prestamoDAO = prestamoDAO;
    }

    public List<Libro> librosNuncaPrestados() throws SQLException {
        return libroDAO.obtenerLibrosNuncaPrestados();
    }

    public Map<String, Integer> contarPrestamosActivosPorTitulo() throws SQLException {
        Map<String, Integer> conteo = new HashMap<>();
        List<PrestamoDetalle> activos = prestamoDAO.listarPrestamosActivosConLibro();

        for (PrestamoDetalle detalle : activos) {
            String titulo = detalle.getTituloLibro();
            conteo.put(titulo, conteo.getOrDefault(titulo, 0) + 1);
        }

        return conteo;
    }
    
}
