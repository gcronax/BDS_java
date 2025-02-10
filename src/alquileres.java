import java.sql.*;
import java.util.Scanner;

public class alquileres {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:sample.db"; // Ruta de la base de datos
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url); // Establecer conexión
            System.out.println("Conexión establecida");
            Scanner scan= new Scanner(System.in);

            int aux;
            do {
                System.out.println("dime que deseas hacer: " +
                        "\n 1 consultar datos" +
                        "\n 2 añadir piso" +
                        "\n 3 eliminar piso" +
                        "\n 4 actualizar piso" +
                        "\n 0 finalizar consulta");
                aux=scan.nextInt();

                switch (aux){
                    case 1 -> consultar(conn);
                    case 2 -> insertar(conn);
                    case 3 -> eliminar(conn);
                    case 4 -> actualizar(conn);
                }
            }while (aux!=0);


            //aqui un menu si eso
            //insertar(conn);
            //eliminar(conn);
            //actualizar(conn);
            //consultar(conn);
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
            rs = stmt.executeQuery("SELECT * FROM frutas");
// Procesar los resultados



            while (rs.next()) {
                //aqui esta la magia
                System.out.println("Fruta ID: " + rs.getInt("id"));
                System.out.println("Frutas Name: " + rs.getString("name"));
                System.out.println("Frutas Precio: " + rs.getDouble("precio"));

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
        System.out.print("Ingrese el nombre de la fruta: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el precio de las frutas: ");
        Double precio = scanner.nextDouble();
        scanner.nextLine(); // Limpiar el buffer

        PreparedStatement pstmt = null;
        try {
// Preparar la sentencia SQL para insertar un nuevo empleado
            String sql = "INSERT INTO frutas (name, precio) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, precio);
// Ejecutar el INSERT
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Fruta insertado exitosamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al insertar Fruta: " + e.getMessage());
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
        System.out.print("Ingrese el nombre de la Fruta que desea eliminar: ");
        String name = scanner.nextLine();
        PreparedStatement pstmt = null;
        try {
// Preparar la sentencia SQL para eliminar el empleado por ID
            String sql = "DELETE FROM frutas WHERE name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
// Ejecutar el DELETE
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Fruta eliminado exitosamente.");
            } else {
                System.out.println("No se encontró una fruta con el nombre proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la fruta: " + e.getMessage());
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
        System.out.print("Ingrese el nombre de la fruta que desea actualizar: ");
        String name = scanner.nextLine();
        System.out.print("Ingrese el nuevo precio de la fruta: ");
        Double nuevoPrecio = scanner.nextDouble();
        PreparedStatement pstmt = null;
        try {
// Preparar la sentencia SQL para actualizar el nombre del empleado
            String sql = "UPDATE frutas SET precio = ? WHERE name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, nuevoPrecio);
            pstmt.setString(2, name);
// Ejecutar el UPDATE
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Fruta actualizado exitosamente.");
            } else {
                System.out.println("No se encontró la fruta con el nombre proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar la fruta: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}