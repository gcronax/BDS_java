import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
            rs = stmt.executeQuery("SELECT * FROM pisos");
// Procesar los resultados



//            System.out.printf("%-5s %-20s %-10s %-6s %-30s %-10s %-12s %-5s %-15s %-10s %-10s %-10s%n",
//                    "ID", "Dirección", "Puerta", "Num", "Descripción", "Superficie", "Habitaciones", "Baños", "Alquiler", "Edificio_ID", "Valor", "Propietario_ID");
//            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
//
//            while (rs.next()) {
//                System.out.printf("%-5d %-20s %-10s %-6d %-30s %-10.2f %-12d %-5d %-15.2f %-10d %-10.2f %-10d%n",
//                        rs.getInt("id_piso"),
//                        rs.getString("direccion"),
//                        rs.getString("puerta"),
//                        rs.getInt("numero"),
//                        rs.getString("descripcion"),
//                        rs.getDouble("superficie"),
//                        rs.getInt("cantidad_habitaciones"),
//                        rs.getInt("cantidad_baños"),
//                        rs.getDouble("precio_alquiler"),
//                        rs.getInt("id_edificio"),
//                        rs.getDouble("valor_piso"),
//                        rs.getInt("id_propietario"));
//            }
            JFrame frame = new JFrame("Listado de Pisos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 400);

            // Definir los nombres de las columnas
            String[] columnas = {"ID", "Dirección", "Puerta", "Número", "Descripción", "Superficie",
                    "Habitaciones", "Baños", "Alquiler", "Edificio_ID", "Valor", "Propietario_ID"};

            // Crear modelo de la tabla
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
            JTable tabla = new JTable(modelo);

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id_piso"),
                        rs.getString("direccion"),
                        rs.getString("puerta"),
                        rs.getInt("numero"),
                        rs.getString("descripcion"),
                        rs.getDouble("superficie"),
                        rs.getInt("cantidad_habitaciones"),
                        rs.getInt("cantidad_baños"),
                        rs.getDouble("precio_alquiler"),
                        rs.getInt("id_edificio"),
                        rs.getDouble("valor_piso"),
                        rs.getInt("id_propietario")
                };
                modelo.addRow(fila);
            }


            JScrollPane scrollPane = new JScrollPane(tabla);
            frame.add(scrollPane);

            // Mostrar la ventana
            frame.setVisible(true);



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
        System.out.print("Ingrese la direcion del piso: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese la puerta del piso: ");
        String puerta = scanner.nextLine();
        System.out.print("Ingrese el numero del piso: ");
        int numero  = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese la descripcion del piso: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese la superficie del piso (m²): ");
        double superficie = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Ingrese la cantidad de habitaciones: ");
        int cantidadHabitaciones = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese la cantidad de baños: ");
        int cantidadBanos = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el precio del alquiler: ");
        double precioAlquiler = scanner.nextDouble();
        scanner.nextLine();



        System.out.print("Ingrese el ID del edificio: ");
        int idEdificio = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese el valor del piso: ");
        double valorPiso = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Ingrese el ID del propietario: ");
        int idPropietario = scanner.nextInt();
        scanner.nextLine();
        PreparedStatement pstmt = null;
        try {
// Preparar la sentencia SQL para insertar un nuevo empleado
            String sql = "INSERT INTO pisos (direccion, puerta, numero, descripcion, superficie, cantidad_habitaciones, cantidad_baños, precio_alquiler, valor_piso, id_edificio, id_propietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, direccion);  // Dirección del piso
            pstmt.setString(2, puerta);     // Letra o número de la puerta
            pstmt.setInt(3, numero);// Número del piso
            pstmt.setString(4, descripcion);     // Letra o número de la puerta
            pstmt.setDouble(5, superficie); // Superficie en m²
            pstmt.setInt(6, cantidadHabitaciones); // Cantidad de habitaciones
            pstmt.setInt(7, cantidadBanos); // Cantidad de baños
            pstmt.setDouble(8, precioAlquiler); // Precio del alquiler
            pstmt.setDouble(9, valorPiso);  // Valor del piso
            pstmt.setInt(10, idEdificio);    // ID del edificio
            pstmt.setInt(11, idPropietario); // ID del propietario


// Ejecutar el INSERT
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Piso insertado exitosamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al insertar Piso: " + e.getMessage());
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
        System.out.print("Ingrese el ID del piso que desea eliminar: ");
        int id_piso = scanner.nextInt();
        scanner.nextLine();
        PreparedStatement pstmt = null;
        try {
// Preparar la sentencia SQL para eliminar el empleado por ID
            String sql = "DELETE FROM pisos WHERE id_piso = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_piso);
// Ejecutar el DELETE
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Piso eliminado exitosamente.");
            } else {
                System.out.println("No se encontró una piso con el nombre proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el piso: " + e.getMessage());
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
        System.out.print("Ingrese el ID del piso que desea actualizar: ");
        String id_piso = scanner.nextLine();
        System.out.print("Ingrese el nuevo precio del alquiler del piso: ");
        Double precio_alquiler = scanner.nextDouble();
        PreparedStatement pstmt = null;





        try {
// Preparar la sentencia SQL para actualizar el nombre del empleado
            String sql = "UPDATE pisos SET precio_alquiler = ? WHERE id_piso = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, precio_alquiler);
            pstmt.setString(2, id_piso);
// Ejecutar el UPDATE
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Priso actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el piso con el ID proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el piso: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}