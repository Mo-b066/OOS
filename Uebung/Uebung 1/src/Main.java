public class Main {
    public static void main(String[] args) {

      final int ZEILE_A = 3;
      final int SPALTE_A = 2;
      final int SPALTE_B = 3;

      int[][] a = new int[ZEILE_A][SPALTE_A];
      int[][] b = new int[SPALTE_A][SPALTE_B];
      int[][] c = new int[ZEILE_A][SPALTE_B];

      // Matrix a auffüllen

        for(int i = 0; i < ZEILE_A; i++){
            for(int j = 0; j < SPALTE_A; j++){
                a[i][j] = 10*i + j;

            }
        }
        // Matrix b auffüllen

        for(int i = 0; i < SPALTE_A; i++){
            for(int j = 0; j < SPALTE_B; j++){
                b[i][j] = i + 10*j;
            }
        }

        // Ausgabe von a:
        System.out.println("Matrix a: ");
        for(int i = 0; i < ZEILE_A; i++){
            for(int j = 0; j < SPALTE_A; j++){
                System.out.print(a[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();

        // Ausgabe von b:
        System.out.println("Matrix b: ");
        for(int i = 0; i < SPALTE_A; i++){
            for(int j = 0; j < SPALTE_B; j++){
                System.out.print(b[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();

        //Multiplikation
        //Matrix c auffüllen

        for(int i = 0; i < ZEILE_A; i++){
            for(int j = 0; j < SPALTE_B; j++){
             c[i][j] = 0;

             for(int k = 0; k < SPALTE_A; k++){

                 c[i][j] += a[i][k] * b[k][j];
             }
            }
        }
        // Ausgabe der Matrix

        System.out.println("Matrix c: ");
        for(int i = 0; i < ZEILE_A; i++){
            for(int j = 0; j < SPALTE_B; j++){
                System.out.print(c[i][j] + " ");
            }

            System.out.println();
        }




    }
}
