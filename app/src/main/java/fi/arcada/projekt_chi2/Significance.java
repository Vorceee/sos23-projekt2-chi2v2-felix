package fi.arcada.projekt_chi2;

public class Significance {


    public static double chiSquared(int e1, int e2, int e3, int e4) {

        // heltalsvariabler tänkta att få de förväntade värdena


        int total = e1 + e2 + e3 + e4;
        double expected1 = (e1 + e2) * (e1 + e3) / (double) total;
        double expected2 = (e1 + e2) * (e2 + e4) / (double) total;
        double expected3 = (e3 + e4) * (e1 + e3) / (double) total;
        double expected4 = (e3 + e4) * (e2 + e4) / (double) total;
        double chiSquare = ((Math.pow((e1 - expected1), 2) / expected1) +
                (Math.pow((e2 - expected2), 2) / expected2) +
                (Math.pow((e3 - expected3), 2) / expected3) +
                (Math.pow((e4 - expected4), 2) / expected4));


        return chiSquare;
    }


    public static double getP(double chiResult) {

        double p = 0.99;

        if (chiResult >= 1.642) p = 0.2;
        if (chiResult >= 2.706) p = 0.1;
        if (chiResult >= 3.841) p = 0.05;
        if (chiResult >= 5.024) p = 0.025;
        if (chiResult >= 5.412) p = 0.02;
        if (chiResult >= 6.635) p = 0.01;
        if (chiResult >= 7.879) p = 0.005;
        if (chiResult >= 9.550) p = 0.002;

        return p;

    }

}
