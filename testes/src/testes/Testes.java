/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;


import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author Jorge
 */
public class Testes {
   


    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException {
//        
//        
//      int numInd = 0;
//      boolean vf = true;
//      int roleta = 0;
//      int pares = 0;
//      int[] par;
//      
//      Scanner in = new Scanner(System.in);
//      
//        while (vf == true) {
//            System.out.println("individuos:");
//            String it = in.nextLine();
//            if (it.matches("[0-9]+")) {
//                vf = false;
//                numInd = Integer.parseInt(it);
//            } else {
//                System.out.println("numero invalido");
//            }
//        }
//        
//        roleta = numInd - 2;
//        
//        double p = (double) roleta / 2 + 0.5;
//        pares = (int) p + 1;
//        
//     //-----------   
//        par = new int[pares];
//        
//        p = (double) pares/2 +0.5;
//        int a = (int) p;
//        
//        int x=1;
//        for(int i=0;i<pares;i++){
//            par[i]=x;
//            x++;
//        }
//       
//        
//        
//        System.out.println("-----");
//        System.out.println("individuos:" + numInd);
//        System.out.println("roleta:" + roleta);
//        System.out.println("pares:" + pares);
//        System.out.println("p:" + p);
//        System.out.println("a:" + a);
//
//        if (pares % 2 == 0) {
//            for (int i = 0; i < a; i++) {
//                System.out.println(par[(i * 2)] + " e " + par[(i * 2 + 1)]);
//            }
//        } else {
//            System.out.println(par[(0)]);
//            for (int i = 1; i < (a); i++) {
//                System.out.println(par[(i * 2 - 1)] + " e " + par[(i * 2)]);
//            }
//            
//        }


        
        Scanner in = new Scanner(System.in);
        
        System.out.println("Numero Binário:");
        String it = in.nextLine();
        
        System.out.println("DEcimal"+decimal(it));
        
        
        
    }
    
    public static String decimal(String binario){
        int decimal =0;
        int i = binario.length();
        int mult = 2;
        boolean repetir = true;
        int num = 0;
        if((binario.charAt(0)) == 1){
            decimal++;
        }else{
            decimal = 0;
        }
        if(i>0){
            if(binario.charAt(1) == 1){
                decimal +=2;
            }
            if(i==1){
                repetir = false;
            }
            while (repetir){
                num = binario.charAt(i);
                if(num == 1){
                    decimal += 2*mult;
                }
                mult *= 2;
                i--;
                if(i == 1){
                    repetir = false;
                }
            }
                          
             
        }
        
        
        String resul = ""+ decimal;
             return resul;
        
        
    }
    
}
