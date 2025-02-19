import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);

        int aux;
        do {
            System.out.println("dime que deseas hacer: " +
                    "\n 1 Gestion alquileres" +
                    "\n 2 Gestion inquilinos" +
                    "\n 3 Gestion" +
                    "\n 4 Gestion" +
                    "\n 0 Finalizar programa");

            aux=scan.nextInt();

            switch (aux){
                case 1 -> alquileres.menuAlquileres();
                case 2 -> alquileres.menuAlquileres();
                case 3 -> alquileres.menuAlquileres();
                case 4 -> alquileres.menuAlquileres();
            }
        }while (aux!=0);
    }
}
