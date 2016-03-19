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

    public static int[] getCountOfInvertions() {
        return countOfInvertions;
    }

    public static int getAimUser() {
        return aimUser;
    }

    public static void setAimUser(int aimUser) {
        is41_Romanchenko_02.aimUser = aimUser;
    }

    public static int[][] getInputtedArray() {
        return inputtedArray;
    }

    public static void setInputtedArray(int[][] inputtedArray) {
        is41_Romanchenko_02.inputtedArray = inputtedArray;
    }

    public static int getCountUsers() {
        return countUsers;
    }

    public static void setCountUsers(int countUsers) {
        is41_Romanchenko_02.countUsers = countUsers;
    }

    public static int getCountFilms() {
        return countFilms;
    }

    public static void setCountFilms(int countFilms) {
        is41_Romanchenko_02.countFilms = countFilms;
    }

    public static void main(String[] args) {

        Scanner read = null;
        try {
            read = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert read != null;
        setCountUsers(read.nextInt());
        setCountFilms(read.nextInt());
        setAimUser(Integer.parseInt(args[1]));
        setInputtedArray(readIntArrayFromFile(read, getCountUsers(), getCountFilms()));
        setInputtedArray(convertInArray());

        amountOfInvertions();

        int[] answers;

        answers = getCountOfInvertions().clone();
        Arrays.sort(getCountOfInvertions());
        String answerString = "";
        answerString += getAimUser() + "\n";
        for (int i = 1; i < getCountUsers(); i++)
            for (int j = 1; j < getCountUsers(); j++)
                if (answers[j] == getCountOfInvertions()[i]){
                    answers[j] = Integer.MAX_VALUE;
                    answerString += ((j + 1) + " " + getCountOfInvertions()[i] + "\n");
                    break;
                }
        File outputFile = new File("is41_Romanchenko_02_output.txt");
        try {
            outputFile.createNewFile();
            FileWriter fw = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(answerString);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void amountOfInvertions()
    {
        countOfInvertions = new int[getCountUsers()];
        for (int i = 0; i < getCountUsers(); i++)
            mergeSort(getInputtedArray()[i], 1, getCountFilms(), i);
    }

    private static int[][] readIntArrayFromFile(Scanner read, int rows, int columns){
        int returnArray[][];

        returnArray = new int[rows][columns + 1];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns + 1; j++)
                returnArray[i][j] = read.nextInt();
        return returnArray;
    }

    //n^3
    private static int[][] convertInArray(){
        int [][] returnArray = getInputtedArray();
        int [] aimArray = new int[getCountFilms()];

        System.arraycopy(returnArray[getAimUser() - 1], 1, aimArray, 0, getCountFilms() + 1 - 1);

        for (int i = 0; i < getCountUsers(); i ++)
            for (int j = 1; j < getCountFilms() + 1; j++)
                for (int key = 0; key < getCountFilms(); key++)
                    if (returnArray[i][j] == aimArray[key]) {
                        returnArray[i][j] = key + 1;
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
                getCountOfInvertions()[numberArray] += (n1 - i);
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
