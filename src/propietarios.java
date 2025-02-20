import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Scanner;

public class propietarios {
    public static String auxname="propietario";
    public static String auxnametabla="propietarios";

    public static void menuPropietarios() {
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
                        "\n 2 añadir "+auxname+"" +
                        "\n 3 eliminar "+auxname+"" +
                        "\n 4 actualizar "+auxname+"" +
                        "\n 0 finalizar consulta");
                aux=scan.nextInt();

                switch (aux){
                    case 1 -> consultar(conn);
                    case 2 -> insertar(conn);
                    case 3 -> eliminar(conn);
                    case 4 -> actualizar(conn);
                }
            }while (aux!=0);




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
            rs = stmt.executeQuery("SELECT * FROM "+auxnametabla+"");
// Procesar los resultados
            ResultSetMetaData metaData = rs.getMetaData();
            // Obtener el número de columnas
            int columnCount = metaData.getColumnCount();
            // Definir los nombres de las columnas
            String[] columnas = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnas[i - 1] = metaData.getColumnName(i); // Guardar el valor de la columna en el array
            }

            JFrame frame = new JFrame("Listado de "+auxnametabla+"");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 400);


            // Crear modelo de la tabla
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
            JTable tabla = new JTable(modelo);
            while (rs.next()) {
                // Crear un array para almacenar los valores de la fila
                Object[] fila = new Object[columnCount];

                // Recorrer cada columna y guardar su valor en el array
                for (int i = 1; i <= columnCount; i++) {
                    fila[i - 1] = rs.getObject(i); // Guardar el valor de la columna en el array
                }
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
        Statement stmt = null;
        ResultSet rs = null;
        String[] columnasaux = new String[0];
        try {
// Crear una declaración
            stmt = conn.createStatement();
// Ejecutar consulta SQL
            rs = stmt.executeQuery("SELECT * FROM "+auxnametabla+"");
// Procesar los resultados
            ResultSetMetaData metaData = rs.getMetaData();
            // Obtener el número de columnas
            int columnCount = metaData.getColumnCount();
            // Definir los nombres de las columnas
            String[] columnas = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnas[i - 1] = metaData.getColumnName(i); // Guardar el valor de la columna en el array
            }
            columnasaux=columnas;

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
        Scanner scanner = new Scanner(System.in);
        String[] nombresCampos=new String[columnasaux.length];

        for (String aux:columnasaux){
            int i=0;
            System.out.println("ingrese "+aux+": ");
            nombresCampos[i++] = scanner.nextLine();

        }


        System.out.print("Ingrese la direccion: ");
        String direccion = scanner.nextLine();

        System.out.print("Ingrese el ID del propietario: ");
        int idPropietario = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la ciudad: ");
        String ciudad = scanner.nextLine();
        PreparedStatement pstmt = null;
        try {
// Preparar la sentencia SQL para insertar un nuevo empleado
            String sql = "INSERT INTO "+auxnametabla+" (direccion, id_propietario, ciudad) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
//            getColumnTypeName(int column)
            pstmt.setString(1, direccion);  // Dirección del "+auxname+"

            pstmt.setInt(2, idPropietario); // ID del propietario
            pstmt.setString(3, ciudad);


// Ejecutar el INSERT
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(""+auxname+" insertado exitosamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al insertar "+auxname+": " + e.getMessage());
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
        System.out.print("Ingrese el ID del "+auxname+" que desea eliminar: ");
        int id_piso = scanner.nextInt();
        scanner.nextLine();
        PreparedStatement pstmt = null;
        try {
// Preparar la sentencia SQL para eliminar el empleado por ID
            String sql = "DELETE FROM "+auxnametabla+" WHERE id_piso = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_piso);
// Ejecutar el DELETE
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(""+auxname+" eliminado exitosamente.");
            } else {
                System.out.println("No se encontró una "+auxname+" con el nombre proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el "+auxname+": " + e.getMessage());
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
        System.out.print("Ingrese el ID del "+auxname+" que desea actualizar: ");
        String id_piso = scanner.nextLine();
        System.out.print("Ingrese el ID del nuevo propietario: ");
        int id_propietario = scanner.nextInt();
        PreparedStatement pstmt = null;





        try {
// Preparar la sentencia SQL para actualizar el nombre del empleado
            String sql = "UPDATE "+auxnametabla+" SET id_propietario = ? WHERE id_piso = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_propietario);
            pstmt.setString(2, id_piso);
// Ejecutar el UPDATE
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Priso actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el "+auxname+" con el ID proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el "+auxname+": " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}