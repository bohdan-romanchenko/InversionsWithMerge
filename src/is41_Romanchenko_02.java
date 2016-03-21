import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by nadman on 19.03.16.
 */
public class is41_Romanchenko_02 {
    private static int[][] inputtedArray;
    private static int countUsers;
    private static int countFilms;
    private static int aimUser;
    private static int[] countOfInvertions;

    public static void main(String[] args) {

        Scanner read = null;
        try {
            read = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert read != null;
        countUsers = read.nextInt();
        countFilms = read.nextInt();
        aimUser = Integer.parseInt(args[1]);
        inputtedArray = readIntArrayFromFile(read, countUsers, countFilms);
        inputtedArray = fixRaitingForEachUser();
        inputtedArray = convertInArray();
        amountOfInvertions();
        output();
    }

    private static void output(){
        int[] answers;

        answers = countOfInvertions.clone();
        Arrays.sort(countOfInvertions);
        String answerString = "";
        answerString += aimUser + "\n";
        for (int i = 1; i < countUsers; i++)
            for (int j = 0; j < countUsers; j++)
                if (answers[j] == countOfInvertions[i]){
                    answers[j] = Integer.MAX_VALUE;
                    answerString += ((j + 1) + " " + countOfInvertions[i] + "\n");
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

    private static void amountOfInvertions() {
        countOfInvertions = new int[countUsers];
        for (int i = 0; i < countUsers; i++)
            mergeSort(inputtedArray[i], 1, countFilms, i);
    }

    private static int[][] readIntArrayFromFile(Scanner read, int rows, int columns){
        int returnArray[][];

        returnArray = new int[rows][columns + 1];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns + 1; j++)
                returnArray[i][j] = read.nextInt();
        return returnArray;
    }

    private static int[][] fixRaitingForEachUser(){

        int [][] returnArray = new int[countUsers][countFilms + 1];
        for (int i = 0; i < countUsers; i++)
            System.arraycopy(inputtedArray[i], 0, returnArray[i], 0, countFilms + 1);
        for (int i = 0; i < countUsers; i++){
            for (int j = 1; j < countFilms + 1; j++){
                returnArray[i][inputtedArray[i][j]] = j;
            }
        }
        return returnArray;
    }

    //n^3. !! ISSUE !! wrong is here ;(
    private static int[][] convertInArray(){
        int [][] returnArray = new int[countUsers][countFilms + 1];
        for (int i = 0; i < countUsers; i++){
            for (int j = 0; j < countFilms + 1; j++){
                returnArray[i][j] = inputtedArray[i][j];
            }
        }
        int [] aimArray = new int[countFilms + 1];
        aimArray[0] = aimUser;
        for (int i = 1; i < countFilms + 1; i++){
            aimArray[i] = returnArray[aimUser - 1][i];
        }

        for (int i = 0; i < countUsers; i ++)
            for (int j = 1; j < countFilms + 1; j++)
                for (int key = 1; key < countFilms + 1; key++)
                    if (returnArray[i][j] == aimArray[key]) {
                        returnArray[i][j] = key;
                        break;
                    }
        return returnArray;
    }

    private static void merge(int []A, int p, int q, int r, int numberArray){
        int n1 = q - p + 1, n2 = r - q;
        int LeftArray[] = new int[n1 + 1], RightArray[] = new int[n2 + 1];

        System.arraycopy(A, p, LeftArray, 0, n1);

        for (int i = 0; i < n2; i++)
            RightArray[i] = A[q + i + 1];

        LeftArray[n1] = Integer.MAX_VALUE;
        RightArray[n2] = Integer.MAX_VALUE;
        int i = 0, j = 0;

        for(int k = p; k <= r; k++){
            if(LeftArray[i] <= RightArray[j]){
                A[k] = LeftArray[i];
                i++;
            }
            else {
                A[k] = RightArray[j];
                j++;
                countOfInvertions[numberArray] += (n1 - i);
            }
        }
    }

    public static void mergeSort(int[] A, int left, int right, int numberArray){
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(A, left, middle, numberArray);
            mergeSort(A, middle + 1, right, numberArray);
            merge(A, left, middle, right, numberArray);
        }
    }
}
