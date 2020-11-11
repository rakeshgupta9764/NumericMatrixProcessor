package processor;

import static java.lang.System.out;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean insideMenu = true;
        while (insideMenu) {
            out.println("1. Add matrices\n2. Multiply matrix by a constant\n" +
                    "3. Multiply matrices\n4. Transpose matrix\n" +
                    "5. Calculate a determinant\n6. Inverse matrix\n0. Exit\nYour choice: ");
            int menuChoice = sc.nextInt();
            switch (menuChoice) {
                case 0:
                    insideMenu = false;
                    break;
                case 1:
                    addMatrix();
                    break;
                case 2:
                    multiplyScalar();
                    break;
                case 3:
                    multiplyMatrices();
                    break;
                case 4:
                    transposeMatrix();
                    break;
                case 5:
                    calcDeterminant();
                    break;
                case 6:
                    inverseMatrix();
                    break;
                default:
                    break;
            }
        }
    }

    private static void inverseMatrix() {
        out.println("Enter matrix size:");
        int size = sc.nextInt();
        // assume square matrix....ignore errors.
        sc.next();
        out.println("Enter matrix:");
        double[][] a = new double[size][size];
        double[][] cofactorMatrix = new double[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                a[i][j] = sc.nextDouble();
            }
        }

        // formula for inverse of a matrix is:
        // Inverse(A) = (1 / detA) * CofactorMatrix(A)_transposed;  (transposed along main diagonal)
        double detA = det(a);
        // The division by detA can be done before transpose too, cuz transpose just reorders the elements of matrix.
        for (int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                cofactorMatrix[i][j] = cofactor(a, i, j);
                cofactorMatrix[i][j] /= detA;
            }
        }
        cofactorMatrix = transposeUtil.transpose(false, cofactorMatrix, size, size, 1);
        // Here 'false' means 'don't print the elements in the util method', instead return from there to store it here.
        // See the transposeUtil class.

        System.out.println("The result is:");
        for (int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                out.print(cofactorMatrix[i][j] + " ");
            }
            out.println();
        }
    }

    private static double det(double[][] p) {
        if (p.length == 1) {
            return p[0][0];
        }
        if (p.length == 2) {
            return p[0][0] * p[1][1] - p[0][1] * p[1][0];
        }
        double value = 0;

        // Approach:
        // Multiply the elements of the first row with their corresponding cofactors and add them together.
        for (int i = 0; i < p.length; ++i) {
            // Here, i is used to iterate over columns. Row value is zero (first row).
            value += p[0][i] * cofactor(p, 0, i);
        }
        return value;
    }

    private static double cofactor(double[][] p, int row, int col) {
        // Exclude first row and column 'i'...
        // ...and form a new matrix of order p.length - 1.
        double[][] pConverted = new double[p.length - 1][p.length - 1];

        // Iterators for the new matrix.
        int newRow = 0;
        int newCol = 0;

        // The following loops store the new matrix of order 1 less than the parent matrix...
        // this is intuitive cuz there is always one i and one j for which the copying of values...
        // will be skipped...(see the if conditions).
        // The way we iterate over the new matrix (pConverted) can be modified to look more graceful.
        for (int i = 0; i < p.length; ++i) {
            if (i == row) {
                continue;
            }
            for (int j = 0; j < p.length; ++j) {
                if (j == col) {
                    continue;
                }
                pConverted[newRow][newCol++] = p[i][j];
            }
            newRow++;   // increase the row number
            newCol = 0; // reset the column number
        }
        // det(pConverted) only is known as minor of (i, j).
        // Cofactor also has the sign of the position.
        // We have to return cofactor.
        return Math.pow(-1, row + col) * det(pConverted);

        // Watch out for unusual values like "-0.0". Might have to do some workaround for it...
        // like checking for zero value,etc.
    }

    private static void calcDeterminant() {
        out.println("Enter matrix size: ");
        // Assume that it is a square matrix.
        int m = sc.nextInt();
        sc.nextInt();
        out.println("Enter matrix: ");
        double[][] a = new double[m][m];
        for (int i = 0; i < m; ++i) {
            for(int j = 0; j < m; ++j) {
                a[i][j] = sc.nextDouble();
            }
        }
        out.println("The result is: ");
        out.println(det(a));
    }

    private static void transposeMatrix() {
        out.println("1. Main diagonal\n2. Side diagonal\n3. Vertical line" +
                "\n4.Horizontal line\nYour choice: ");
        int axisChoice = sc.nextInt();
        out.println("Enter matrix size: ");
        int m = sc.nextInt();
        int n = sc.nextInt();
        out.println("Enter matrix: ");
        double[][] a = new double[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = sc.nextDouble();
            }
        }
        out.println("The result is:");
        transposeUtil.transpose(true, a, a.length, a[0].length, axisChoice);

    }

    private static void multiplyMatrices() {
        out.println("Enter size of first matrix: ");
        int m = sc.nextInt();
        int n = sc.nextInt();
        out.println("Enter first matrix: ");
        double[][] a = new double[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = sc.nextDouble();
            }
        }
        out.println("Enter size of second matrix: ");
        int p = sc.nextInt();
        int q = sc.nextInt();
        if (n != p) {
            out.println("ERROR");
            return;
        }
        out.println("Enter second matrix: ");
        double[][] b = new double[p][q];
        for (int i = 0; i < p; ++i) {
            for (int j = 0; j < q; ++j) {
                b[i][j] = sc.nextDouble();
            }
        }
        out.println("The result is: ");
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < q; ++j) {
                double ans = 0;
                for (int k = 0; k < n; ++k) {
                    ans += (a[i][k] * b[k][j]);
                }
                out.print(ans + " ");
            }
            out.println();
        }
    }
    private static void multiplyScalar(){
        out.println("Enter size of matrix: ");
        int m = sc.nextInt();
        int n = sc.nextInt();
        out.println("Enter matrix: ");
        double[][] a = new double[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = sc.nextDouble();
            }
        }
        out.println("Enter constant: ");
        int k = sc.nextInt();
        out.println("The result is: ");
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                out.println(k * a[i][j] + " ");
            }
            out.println();
        }
    }

    private static void addMatrix() {
        out.println("Enter size of first matrix: ");
        int m = sc.nextInt();
        int n = sc.nextInt();
        out.println("Enter first matrix: ");
        double[][] a = new double[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = sc.nextDouble();
            }
        }
        out.println("Enter size of second matrix: ");
        int p = sc.nextInt();
        int q = sc.nextInt();
        if (m != p && n != q) {
            out.println("ERROR");
            return;
        }
        out.println("Enter second matrix: ");
        double[][] b = new double[p][q];
        for (int i = 0; i < p; ++i) {
            for (int j = 0; j < q; ++j) {
                b[i][j] = sc.nextDouble();
            }
        }
        out.println("The result is:");
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < q; ++j) {
                out.println(a[i][j] + b[i][j] + " ");
            }
            out.println();
        }
    }
}
