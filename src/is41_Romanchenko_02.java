import java.io.*;
import java.util.*;

/**
 * Created by Bohdan Romanchenko on 19/04/2016.
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

            aimUser = Integer.parseInt(args[0].replaceAll("\\D+",""));

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
        int[] answers;

        answers = countInvertions.clone();
        Arrays.sort(countInvertions);
        String answerString = "";
        answerString += aimUser + "\n";
        for (int i = 1; i < countOfUsers; i++)
            for (int j = 0; j < countOfUsers; j++)
                if (answers[j] == countInvertions[i]){
                    answers[j] = Integer.MAX_VALUE;
                    answerString += ((j + 1) + " " + countInvertions[i] + "\n");
                    break;
                }
        File outputFile = new File("is41_Romanchenko_02_output.txt");
        try {
            FileWriter fw = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(answerString);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
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