import java.sql.*;
import java.util.Scanner;

public class insertSQL {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:sample.db"; // Ruta de la base de datos
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url); // Establecer conexión
            System.out.println("Conexión establecida");

            //aqui un menu si eso
            insertar(conn);
            eliminar(conn);
            actualizar(conn);
            consultar(conn);
            // consulta SELECT

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Conexión cerrada.");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void consultar(Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
// Crear una declaración
            stmt = conn.createStatement();
// Ejecutar consulta SQL
            rs = stmt.executeQuery("SELECT * FROM employees");
// Procesar los resultados



            while (rs.next()) {
                //aqui esta la magia
                System.out.println("Employee ID: " + rs.getInt("id"));
                System.out.println("Employee Name: " + rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void insertar(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del empleado: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el nombre del empleado: ");
        String nombre = scanner.nextLine();
        PreparedStatement pstmt = null;
        try {
// Preparar la sentencia SQL para insertar un nuevo empleado
            String sql = "INSERT INTO employees (id, name) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
// Ejecutar el INSERT
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Empleado insertado exitosamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al insertar empleado: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void eliminar(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del empleado que desea eliminar: ");
        int id = scanner.nextInt();
        PreparedStatement pstmt = null;
        try {
// Preparar la sentencia SQL para eliminar el empleado por ID
            String sql = "DELETE FROM employees WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
// Ejecutar el DELETE
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Empleado eliminado exitosamente.");
            } else {
                System.out.println("No se encontró un empleado con el ID proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void actualizar(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del empleado que desea actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el nuevo nombre del empleado: ");
        String nuevoNombre = scanner.nextLine();
        PreparedStatement pstmt = null;
        try {
// Preparar la sentencia SQL para actualizar el nombre del empleado
            String sql = "UPDATE employees SET name = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, id);
// Ejecutar el UPDATE
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Empleado actualizado exitosamente.");
            } else {
                System.out.println("No se encontró un empleado con el ID proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}