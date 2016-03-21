import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Bohdan Romanchenko on 1/2/2016.
 */
public class is41_Romanchenko_02 {

    private static int[][] inputtedArray;
    private static int countOfUsers;
    private static int countOfFilms;
    private static int aimUser;
    private static int[] countInvertions;

    public static void main(String[] args) throws IOException {
        try {
            Scanner read = new Scanner(new File(args[0]));

            countOfUsers = read.nextInt();
            countOfFilms = read.nextInt();

            aimUser = Integer.parseInt(args[1]);

            countInvertions = new int[countOfUsers];

            inputtedArray = readFromFile(read);
            amountOfInvertions();
            output();
        }catch (FileNotFoundException e){
            System.err.println("Wrong input file");
        }
    }

    public static int[][] readFromFile(Scanner read){
        int returnArray[][];

        returnArray = new int[countOfUsers][countOfFilms];

        for (int i = 0; i < countOfUsers; i++){
            read.nextInt();
            for (int j = 0; j < countOfFilms; j++)
                returnArray[i][j] = read.nextInt();
        }
        return returnArray;
    }

    //n*n*log(n)
    public static void amountOfInvertions(){
        int[] comparedUsersArray = new int[countOfFilms];
        for (int i = 0; i < countOfUsers; i++) {
            for (int j = 0; j < countOfFilms; j++){
                comparedUsersArray[ inputtedArray[aimUser - 1][j] - 1 ] = inputtedArray[i][j] - 1;
            }
            countInvertions[i] = mergeSort(comparedUsersArray, 0, comparedUsersArray.length - 1);
        }
    }

    public static void output() {
        LinkedList<int[]> tempInversionsMatrix = new LinkedList<>();
        for (int i = 0; i < countInvertions.length; i++)
            tempInversionsMatrix.add(new int[]{i, countInvertions[i]});
        Arrays.sort(countInvertions);
        String answerToWrite = "";
        answerToWrite += aimUser + "\n";
        for (int i = 0; i < countOfUsers; i++)
            for (int j = 0; j < tempInversionsMatrix.size(); j++) {
                if (countInvertions[i] == tempInversionsMatrix.get(j)[1] && countInvertions[i] != 0) {
                    answerToWrite += ((tempInversionsMatrix.get(j)[0]+1)  + " " + countInvertions[i]) + "\n";
                    tempInversionsMatrix.remove(j);
                    break;
                }
            }
        try{
            PrintWriter writer = new PrintWriter(new File("is41_Romanchenko_02_output.txt"), "UTF-8");
            writer.println(answerToWrite);
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("problems with output file");
        }
    }


    private static int merge(int[] A, int firstPoint, int middlePoint, int lastPoint){
        int n1 = middlePoint - firstPoint + 1, n2 = lastPoint - middlePoint;
        int L[] = new int[n1 + 1], R[] = new int[n2 + 1];

        for (int i = 0; i < n1; i++)
            L[i] = A[firstPoint + i];
        for (int i = 0; i < n2; i++)
            R[i] = A[middlePoint + i + 1];

        L[n1] = Integer.MAX_VALUE;
        R[n2] = Integer.MAX_VALUE;
        int i = 0, j = 0;

        int counter = 0;
        for(int k = firstPoint; k <= lastPoint; k++){
            if(L[i] <= R[j]){
                A[k] = L[i];
                i++;
            }
            else {
                A[k] = R[j];
                j++;
                counter += n1 - i;
            }
        }
        return counter;
    }

    public static int mergeSort(int[] A, int leftPoint, int rightPoint){
        if(leftPoint >= rightPoint) return 0;
        int middlePoint = (leftPoint + rightPoint) / 2;
        return mergeSort(A, leftPoint, middlePoint) + mergeSort(A, middlePoint + 1, rightPoint) + merge(A, leftPoint, middlePoint, rightPoint);
    }
}