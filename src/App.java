import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner in = null;
        try {
            in = new Scanner(new File("src/input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int height = in.nextInt(), m = in.nextInt(), n = in.nextInt();
        char[][][] castle = new char[height][m][n];

        int[][][] weights = new int[height][m][n];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    weights[i][j][k] = Short.MAX_VALUE;
                }
            }
        }

        int i = 0, j = 0;
        while (in.hasNextLine() && i < height && j < m) {
            String line = in.nextLine();
            if (line.length() == m) {
                castle[i][j] = line.toCharArray();
                j++;
                if (j == m) {
                    j = 0;
                    i++;
                }
            }
        }

        int xTo = 0, yTo = 0, xFrom = 0, yFrom = 0;
        weights[0][xFrom][yFrom] = 0;
        boolean f = false;

        for (int level = 0; level < height; level++) {

            if (level == height - 1) {
                xFrom = xTo;
                yFrom = yTo;
                xTo = m - 1;
                yTo = n - 1;
            } else {
                for (int x = 0; x < m; x++) {
                    for (int y = 0; y < n; y++) {
                        if (castle[level + 1][x][y] != 'o' && castle[level][x][y] != 'o') {
                            xFrom = xTo;
                            yFrom = yTo;
                            xTo = x;
                            yTo = y;
                            f = true;
                            break;
                        }
                    }
                    if (f) {
                        break;
                    }
                }
            }


            for (int x = xFrom; x < m; x++) {
                f = false;
                for (int y = yFrom; y < n; y++) {

                    int up = 'o', down = 'o', left = 'o', right = 'o', moveWeight = 0;
                    if (x > 0) {
                        up = castle[level][x - 1][y];
                    }
                    if (up != 'o') {
                        moveWeight = 1 + weights[level][x][y];
                        if (moveWeight < weights[level][x - 1][y]) {
                            weights[level][x - 1][y] = moveWeight;
                            f = true;
                            break;
                        }
                    }
                    if (x + 1 < n) {
                        down = castle[level][x + 1][y];
                    }
                    if (down != 'o') {
                        moveWeight = 1 + weights[level][x][y];
                        if (moveWeight < weights[level][x + 1][y]) {
                            weights[level][x + 1][y] = moveWeight;
                            f = true;
                            break;
                        }
                    }
                    if (y > 0) {
                        left = castle[level][x][y - 1];
                    }
                    if (left != 'o') {
                        moveWeight = 1 + weights[level][x][y];
                        if (moveWeight < weights[level][x][y - 1]) {
                            weights[level][x][y - 1] = moveWeight;
                            f = true;
                            break;
                        }
                    }
                    if (y + 1 < m) {
                        right = castle[level][x][y + 1];
                    }
                    if (right != 'o') {
                        moveWeight = 1 + weights[level][x][y];
                        if (moveWeight < weights[level][x][y + 1]) {
                            weights[level][x][y + 1] = moveWeight;
                            f = true;
                            break;
                        }
                    }
                }
                if (f) {
                    x = -1;
                }
            }
            if (level < height - 1) weights[level + 1][xTo][yTo] = weights[level][xTo][yTo] + 1;
        }
        int steps = weights[height - 1][m - 1][n - 1];
        int time = steps * 5;
        System.out.println(time);
    }
}