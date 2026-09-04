package edu.umg.programacion2.clase07.biblioteca.dao;

import edu.umg.programacion2.clase07.biblioteca.modelo.Libro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/prog2_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "carro210";
    

    public int crear(Libro libro) throws SQLException {
        String sql = "INSERT INTO libros (titulo, autor, isbn) VALUES (?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getIsbn());
            statement.executeUpdate();

            try (ResultSet claves = statement.getGeneratedKeys()) {
                if (claves.next()) {
                    return claves.getInt(1);
                }
                return -1;
            }
        }
    }

    public List<Libro> listarTodos() throws SQLException {
        String sql = "SELECT id, titulo, autor, isbn FROM libros ORDER BY id";
        List<Libro> libros = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                libros.add(mapearFila(resultado));
            }
        }
        return libros;
    }

    public Optional<Libro> buscarPorIsbn(String isbn) throws SQLException {
        String sql = "SELECT id, titulo, autor, isbn FROM libros WHERE isbn = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, isbn);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearFila(resultado));
                }
                return Optional.empty();
            }
        }
    }

    public List<Libro> obtenerLibrosNuncaPrestados() throws SQLException {
        String sql = "SELECT l.id, l.titulo, l.autor, l.isbn " +
                     "FROM libros l " +
                     "LEFT JOIN prestamos p ON l.id = p.libro_id " +
                     "WHERE p.id IS NULL " +
                     "ORDER BY l.id";

        List<Libro> libros = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                libros.add(mapearFila(resultado));
            }
        }
        return libros;
    }

    private Libro mapearFila(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("id");
        String titulo = resultado.getString("titulo");
        String autor = resultado.getString("autor");
        String isbn = resultado.getString("isbn");
        return new Libro(id, titulo, autor, isbn);
    }
}
