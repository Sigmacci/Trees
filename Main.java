import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Random r = new Random();
        int arr[] =  new int[1000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(10, arr.length * 2);
        }
        //Arrays.sort(arr);
        Trees bst = new Trees();
        bst.add_to_bst(arr);
        long start = System.nanoTime();
        bst.print_in_order(bst.root);
        long finish = System.nanoTime();
        // Scanner s = new Scanner(System.in);
        // System.out.print("\nWprowadź rozmiar tablicy: ");
        // int size = s.nextInt();
        // int del[] = new int[size];
        // System.out.print("\nWprowadź elementy tablicy: ");
        // for(int i = 0; i < size; i++) {
        //     del[i] = s.nextInt();
        // }
        // s.close();
        // bst.delete(bst.root, del);
        // bst.print_in_order(bst.root);
        FileWriter fileWriter = new FileWriter("C:/users/pasha/Documents/AIDS/drzewa.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println((finish - start) / 1000000.0 + " ");
        printWriter.close();
    }

    public static void print(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}

