package processor;

public class transposeUtil {

    public static double[][] transpose (boolean print, double[][] matrix, int m, int n, int axis) {
        /*
        
            WARNING:--- SOME OF THE FOLLOWING COMMENTS TELL WRONG APPROACH, CAN BE MISLEADING.
            ----------------------------------------------------------------------------------
            
            ALL THE FOLLOWING APPROACHES UTILISE EXTRA SPACE---->>
            axis = 1: Main diagonal
                new[i][j] = old[j][i]
//            axis = 2: Side diagonal
//                new[i][j] = old[2*i - j][rowLen - i]
            Wrong.
            Approach I used here (A WORKAROUND):--
            transpose along side diagonal is eqvt to:
            taking transpose along vertical, horizontal (or vice-versa)...
            ...and then along main diagonal.

            axis = 3: Vertical diagonal
                Each a[i] is reversed
                new[i][j] = old[i][rowLen - 1 - j]
            axis = 4: Horizontal line
                Each a[j] is reversed
                len is property of a[i]
                In case of column array a[j], length = i, same for every column.
                new[i][j] = new[colLen - 1 - i][j]
         */
        double[][] transpose; // size changes in case of diagonal axis
        switch (axis) {
            case 1:
                transpose = new double[n][m];
                if (print) {
                    for (int i = 0; i < n; ++i) {
                        for (int j = 0; j < m; ++j) {
                            transpose[i][j] = matrix[j][i];
                            System.out.print(transpose[i][j] + " ");
                        }
                        System.out.println();
                    }
                } else {
                    for (int i = 0; i < n; ++i) {
                        for (int j = 0; j < m; ++j) {
                            transpose[i][j] = matrix[j][i];
                        }
                    }
                    return transpose;
                }
                break;
            case 2:
                transpose = new double[n][m];
//                for (int i = 0; i < n; ++i) {
//                    for (int j = 0; j < m; ++j) {
//                        transpose[i][j] = matrix[n - 1 -j][n - 1 - i];
//                        System.out.print(transpose[i][j] + " ");
//                    }
//                    System.out.println();
//                }
                // ALITER: vertical, then main diagonal...
                double[][] tempVertical = new double[m][n];
                for (int i = 0; i < m; ++i) {
                    for (int j = 0; j < n; ++j) {
                        tempVertical[i][j] = matrix[i][n - 1 - j];
                    }
                }
                double[][] tempHorizontal = new double[m][n];
                for (int i = 0; i < m; ++i) {
                    for (int j = 0; j < n; ++j) {
                        tempHorizontal[i][j] = tempVertical[m - 1 - i][j];
                    }
                }
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < m; ++j) {
                        transpose[i][j] = tempHorizontal[j][i];
                        System.out.print(transpose[i][j] + " ");
                    }
                    System.out.println();
                }
                break;
            case 3:
                transpose = new double[m][n];
                for (int i = 0; i < m; ++i) {
                    for (int j = 0; j < n; ++j) {
                        transpose[i][j] = matrix[i][n - 1 - j];
                        System.out.print(transpose[i][j] + " ");
                    }
                    System.out.println();
                }
                break;
            case 4:
                transpose = new double[m][n];
                for (int i = 0; i < m; ++i) {
                    for(int j = 0; j < n; ++j) {
                        transpose[i][j]= matrix[m - 1 - i][j];
                        System.out.println(transpose[i][j] + " ");
                    }
                    System.out.println();
                }
                break;
            default:
                break;
        }
        return null;
    }
}
