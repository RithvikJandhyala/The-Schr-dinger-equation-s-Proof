public class se{
    double psi[];
    double ECurrent;
    double Emin = 1.490;
    double xMin = -15;
    double xMax = 15;
    double hZero;
    double EDelta = 0.0000001;
    double maxPsi =  0.00000001;
    int numberDivisions = 200;

    public se(){
        ECurrent= Emin;
        psi= new double [numberDivisions +1];
        psi[0]= 0;
        psi[1]= -0.000000001;
        psi[numberDivisions] = 1.0;
        hZero = (xMax-xMin) / numberDivisions;
    }
    public static void main (String [] argv)
    {
        se de = new se();
        de.calculate();
    }
    public void calculate(){
        while(Math.abs(psi[numberDivisions])> maxPsi) {
            for(int i =1; i<numberDivisions; i++){
                psi[i + 1] = calculateNextPsi(i);
            }
            if(psi[numberDivisions]> 0){
                ECurrent= ECurrent - EDelta; 
            }
            else{
                ECurrent = ECurrent + EDelta;
            }
            System.out.println("");
            System.out.println("Psi200: " + psi[numberDivisions]
                + " E: " + round(ECurrent));
        }
        
        System.out.println("\nThe ground state energy is " + round(ECurrent) + " MeV."); 
    }
    public double calculateKsquared(int n){
        double x = (hZero * n) + xMin;
        return (((0.05) * ECurrent) - ((x * x) * 5.63e-3));
    }

    public double calculateNextPsi(int n) {
        double KSqNMinusOne = calculateKsquared(n - 1);
        double KSqN = calculateKsquared(n);
        double KSqNPlusOne = calculateKsquared(n + 1);
        double nextPsi = 2.0 * (1.0 - (5.0 * hZero * hZero * KSqN / 12.0)) * psi[n];
        nextPsi = nextPsi - (1.0 + (hZero * hZero * KSqNMinusOne / 12.0)) * psi[n - 1];
        nextPsi = nextPsi / (1.0 + (hZero * hZero * KSqNPlusOne / 12.0));
        return nextPsi;
    }
    public double round(double val) {
        double divider = 100000;
        val = val * divider;
        double temp = Math.round(val);
        return (double) temp / divider;
    }
}