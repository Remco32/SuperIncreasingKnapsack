package com.company;

import java.io.*;
import java.util.Arrays;


public class Main {

    static int multiplier = 7;
    static int modulator = 399353;
    static int multiplicativeInverse = 114101;
    static int[] privateKey = {1, 3, 37, 42, 90, 182, 366, 722, 1481, 3210, 6143, 12345, 24666, 50000, 100001, 200000};

    static byte blockSize = 2;

    public static void main(String[] args) throws IOException {
        //TODO parsen input
        long input;

        File file = new File("encrypted");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuffer stringBuffer = new StringBuffer();
            char data[] = new char[blockSize];

            while ((br.read(data, 0, blockSize) != -1)) {

                //TODO final char of series is incorrect, should be empty space or something. Encrypted text is uneven.
                System.out.println("Hex: " + Arrays.toString(data));
                String line = new String(data);
                input = Long.parseLong(line, 16);
                System.out.println("Decimal: " + input);

                //input * multInv % mod
                input = input * multiplicativeInverse % modulator;

                //create array for the resulting bitpattern, initialized to 0
                int[] bitPattern = new int[16];

                //decrypt value with private key
                //Go through the key, starting with the last and highest value
                for (int i = privateKey.length - 1; i >= 0; i--) {
                    //Check if the value fits in this bit of key
                    if (input >= privateKey[i]) {
                        //Subtract the value of the key of the input
                        input -= privateKey[i];
                        //Set the corresponding bit to 1
                        bitPattern[i] = 1;
                    }
                }

                System.out.println("Bitpattern after decryption is " + Arrays.toString(bitPattern));


                //split bitpatterns into 2
                int[] pattern1;
                int[] pattern2;

                pattern1 = Arrays.copyOfRange(bitPattern, 0, 8);
                pattern2 = Arrays.copyOfRange(bitPattern, 8, 16);

                System.out.println("Bitpattern1 = " + Arrays.toString(pattern1));
                System.out.println("Bitpattern2 = " + Arrays.toString(pattern2));


                //turn bitpattern into int


                int count = 0;
                for(int i = 0; i < pattern1.length; i++) {
                    count += pattern1[i] * Math.pow(i+1, 2);
                }
                System.out.println("Bitpattern1 as int = " + count);

                //turn int into ASCII

                char ch = (char) count;
                System.out.println("ASCII is " + ch);

                count = 0;
                for(int i = 0; i < pattern2.length; i++) {
                    count += pattern2[i] * Math.pow(i+1, 2);
                }
                System.out.println("Bitpattern2 as int = " + count);

                //turn int into ASCII

                ch = (char) count;
                System.out.println("ASCII is " + ch);

                System.out.println();

            }
        }

    }
}
