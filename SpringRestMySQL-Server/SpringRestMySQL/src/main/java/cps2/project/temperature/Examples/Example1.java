package cps2.project.temperature.Examples;


import java.util.Scanner;

class Example1 {

    public static void main(String[] args){
        int p, q, n, ni, e;
        int k = 1;
        double d = 1.1;
        String m = "";


        Scanner sc = new Scanner(System.in);
        toPrint("Please insert any number 0 > 1 for p and q");
        toPrint("for p");
        p = sc.nextInt();
        toPrint("for q");
        q = sc.nextInt();

        n = p * q;
        ni = (p - 1)*(q - 1);
        e = 11;

        while (true){
            d = ((k * ni) + 1) / e;
            toPrint("" + d + " k = " + k);
            m = "" + d;
            toPrint(m);
            if (!m.matches("[+-]?([0-9]*[.])?[0]+")){
                break;
            }
            k += 1;
        }

        toPrint("Result: e = " + e + ", n = " + n + ", ni = " + ni + ", p = " + p + ", q= " + q + ", d = " + d + ", k = " + k);


    }

    private static void toPrint(String s) {
        System.out.println(s);
    }

}
