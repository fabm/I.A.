/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import java.text.ParseException;
import java.util.Scanner;

/**
 *
 * @author Jorge
 */
public class Simulador {

    /**
     * @param args the command line arguments
     */
    private static double recombinacao = 0.75;
    private static int rec; //probabilidade de reprodução introduzida pelo utilizador
    private static int pcorte1 = 0;
    private static int pcorte2 = 0;
    private static int pcorte3 = 0;
    private static String individuo1; //cromossoma do individuo 1 da mutação
    private static String individuo2; //cromossoma do individuo 2 da mutação
    private static int gene1 = 0; //mutação
    private static int gene2 = 0; //mutação
    private static int ind1 = 0; //individuo 1 da mutação
    private static int ind2 = 0; //individuo 2 da mutação
    private static double soma = 0;
    private static double media = 0;
    private static double melhor1 = 0;
    private static double melhor2 = 0;
    private static int x = 8192; //2^13 ->numero máximo para a cadeira binário
    private static Integer[] ind; //guarda o valor decimal corresponde ao cromossoma de cada ind.
    private static String[] cromossoma; //guarda o cromossoma de cada ind.
    private static double[] avaliacao; //guarada a função avaliação de cada ind.
    private static double[] qualidade; //guarda a qualidade de cada ind.
    private static double[] mediaQual; //guarda a melhor qualidade dos ind. de cada iteração
    private static double[] melhorInd; //guarda o valor do melhor ind. de todas as iterações
    private static double[] probab; //guarada o valor da probabilidade de cada ind.
    private static double[] segRoleta; //guarda o valor do segmento da roleta de cada ind.
    private static int[] indElitismo; //guarda o num dos ind. escolhidos por elitismo
    private static double[] roleta; //guarda o valor gerado pela roleta
    private static int[] indRoleta; //guarda o num dos ind. escolhdos por roleta
    private static double[] recomAleatoria; //quarda os valores aleatórios das recombinações de cada casal 
    private static String[] cromossomaFilho; //guarda o cromossoma de cada filho        
    private static int[][] pontosCorte;
    private static int cont;
    private static int[][] mutacao;//guarda os ind. e os genes onde vai existir mutação
    private static boolean vf; //para o ciclo while
    private static String[] spai; //guardar os valores do pai dos pontos de corte
    private static String[] smae; //guardar os valores da mae dos pontos de corte
    private static String pai; //guardar o pai
    private static String mae; //guarda a mae
    private static String filho1; //guarda o 1º filho
    private static String filho2; //guarda o 2º filho
    private static int comp; //comprimento do cromossoma
    private static StringBuilder sb;
    private static char g; //gene a comutar
    private static int iteracao = 1;
    private static int numInd = 0; // numero de individuos escolhido pelo utilizador
    private static int numPares = 0; //mumero de pares resultante do num de ind escolhidos
    private static int numRoleta = 0; // numero de individuos a gerar por roleta
    private static int numElitismo = 0; //numero de individuos por elitismo
    private static int nPares = 0;//numero de pares que não vão reproduzir
    
    
    public static void main(String[] args) throws ParseException {
        Scanner in = new Scanner(System.in);
        //pedir ao utilizador o numero de iterações
        vf = true;
        while(vf == true){
            System.out.println("Introduza o numero de iterações que pretenda:");
            String it = in.nextLine();
            if(it.matches("[0-9]+")){
                vf = false;
                iteracao = Integer.parseInt(it);
            }else{
                System.out.println("numero invalido");
            }
        }
         //pedir ao utilizador o numero de individuos
        vf = true;
        while (vf == true) {
            System.out.println("Quantos Individuos pretende na População?");
            String it = in.nextLine();
            if (it.matches("[0-9]+")) {
                vf = false;
                numInd = Integer.parseInt(it);
            } else {
                System.out.println("numero invalido");
            }
        }
        
        
//        //pedir ao utilizador o numero de elementos escolhidos por elitismo
//        vf = true;
//        while (vf == true) {
//            System.out.println("Quantos Individuos quer selecionar por Elitismo?");
//            String it = in.nextLine();
//            if (it.matches("[0-9]+")) {
//                vf = false;
//                numElitismo = Integer.parseInt(it);
//            } else {
//                System.out.println("numero invalido");
//            }
//        }

        //calcular quantos individuos vão ser gerados pela roleta e quantos pares vão formar
        numRoleta = numInd - 2;
        
        double par = (double) numRoleta / 2 + 0.5;
        
        if(numRoleta % 2 == 0){
            numPares = (int) par;
        }else{
            numPares = (int) par +1;
        }
        
          //pedir ao utilizador a Probabilidade de reprodução
        vf=true;
        while(vf == true){
            System.out.println("Introduza Probabilidade de Reprodução[50-80]:");
            String it = in.nextLine();
            if(it.matches("[0-9]+")){                
                
                rec = Integer.parseInt(it);
                if((rec >= 50)&&(rec <= 80)){
                    vf = false;
                }else{
                    System.out.println("O numero não se encontra dentro do limite pretendido");
                }
            }else{
                System.out.println("numero invalido");
            }
        }
       
        recombinacao = rec /100.0;
        
        //************************************************************************************************************************
        mediaQual = new double[100];
        melhorInd = new double[100];



        ind = new Integer[numInd]; //criar um vector com n possiçoes
        cromossoma = new String[numInd];


        //gerar os n indivduos aleatorios decimais e convertelos em binário e colocalos nos vectores correspondentes
        for (int i = 0; i < numInd; i++) {
            ind[i] = (int) (1 + Math.random() * x);
            cromossoma[i] = Integer.toString(ind[i], 2);
        }


//        //mostrar valores do cromossoma em decimal
//        for (int i =0; i <10;i++){
//            System.out.println(ind[i]);
//            
//        }

        //acrescentar 0 a esquerda        
        for (int i = 0; i < numInd; i++) {
            comp = cromossoma[i].length();

            for (int a = 0; a < (13 - comp); a++) {
                cromossoma[i] = "0" + cromossoma[i];
            }

        }
        for(int j=0;j<iteracao;j++){
        System.out.println("\n\n*******ITERAÇÃO "+(j+1)+"*******");
        //Escrever tabela com os cromossodas de cada individuo
        System.out.println("Individuo \t Cromossoma");
        for (int i = 0; i < numInd; i++) {
            System.out.println("     " + (i + 1) + "  \t" + cromossoma[i]);

        }



        
//        
//Função de avaliação

        avaliacao = new double[numInd];
        for (int i = 0; i < numInd; i++) {
//            DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
            double aval = (1 + ((ind[i] * 5) / ((Math.pow(2, 13)) - 1)));
//            //formatar o numero com 3 casas decimais numa String
//            String av = String.format("%.3f", aval);
//            //Converter a String para Double e guardar
//            avaliacao[i] = (Double) df.parse(av);

            aval = ((int)(aval*1000)/1000.0);
            avaliacao[i]=aval;
        }


//qualidade
        qualidade = new double[numInd];
        for (int i = 0; i < numInd; i++) {
//            DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
            double qualid = (Double) (Math.pow(avaliacao[i] - 3, 2));
//            //formatar o numero com 3 casas decimais numa String
//            String av = String.format("%.3f", qualid);
//            //Converter a String para Double e guardar
//            qualidade[i] = (Double) df.parse(av);
            qualid = ((int)(qualid*1000)/1000.0);
            qualidade[i]=qualid;
            
            
        }




        //probabilidade
           soma =0;
           
        for (int i = 0; i < numInd; i++) {
            soma += qualidade[i];
        }
        
        media = soma / numInd;
        media = ((int)(media*1000)/1000.0);
        mediaQual[j]=media;

        probab = new double[numInd];
        for (int i = 0; i < numInd; i++) {
//            DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
            double prob = qualidade[i] / soma;
//            //formatar o numero com 3 casas decimais numa String
//            String av = String.format("%.3f", prob);
//            //Converter a String para Double e guardar
//            probab[i] = (Double) df.parse(av);
            
            prob =((int) (prob*1000)/1000.0);
            probab[i] = prob;
        }


//Segmento roleta

        segRoleta = new double[numInd];
        segRoleta[0] = probab[0];
        for (int i = 1; i < numInd; i++) {
//            DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
            double rol = segRoleta[i - 1] + probab[i];
//            //formatar o numero com 3 casas decimais numa String
//            String av = String.format("%.3f", rol);
//            //Converter a String para Double e guardar
//            segRoleta[i] = (Double) df.parse(av);
            rol = ((int)(rol*1000)/1000.0);
            segRoleta[i]=rol;
        }


        //Escrever tabela com os individuos,avaliação,Qualidade,Probabilidade e Segmento Roleta
        System.out.println("\n\n");
        System.out.println("Individuo   Avaliação   Qualidade   Probabilidade   Segmento Roleta");
        for (int i = 0; i < numInd; i++) {
            System.out.println("    " + (i + 1) + "\t\t" + avaliacao[i] + "  \t" + qualidade[i] + "\t\t" + probab[i] + "\t\t" + segRoleta[i]);

        }
        System.out.println("\n\n");


//selecção
        //escolher os 2 melhores ind. para elitismo
        indElitismo = new int[2];
        for (int i = 0; i < numInd; i++) {

            if (qualidade[i] > melhor1) {
                melhor1 = qualidade[i];
                indElitismo[0] = i + 1;
            }
        }
        for (int i = 0; i < numInd; i++) {
            if ((qualidade[i] > melhor2) && (qualidade[i] < melhor1)) {
                melhor2 = qualidade[i];
                indElitismo[1] = i + 1;
            }
        }
        melhorInd[j]=melhor1;

        //escolher os restantes 8 ind. por roleta
      
        indRoleta = new int[numRoleta];
        roleta = new double[numRoleta];
        for (int i = 0; i < numRoleta; i++) {
//            DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
            double rol = (double) (Math.random());
//            //formatar o numero com 3 casas decimais numa String
//            String av = String.format("%.3f", rol);
//            //Converter a String para Double e guardar
//            roleta[i] = (Double) df.parse(av);
            rol = ((int)(rol*1000)/1000.0);
            roleta[i]=rol;
            
            
        }
        //procular o individuo que corresponde ao valor gerado pela roleta
        for (int i = 0; i < numRoleta; i++) {
            for (int a = 1; a < numInd; a++) {
                if ((roleta[i] < segRoleta[a]) && (roleta[i] > segRoleta[a - 1])) {
                    indRoleta[i] = a + 1;
                }
            }
        }
        for (int i = 0; i < numRoleta; i++) {
            if (indRoleta[i] == 0) {
                indRoleta[i] = 1;
            }
        }
        //tabela de Seleção
        System.out.println("Elitismo: " + indElitismo[0] + " e " + indElitismo[1]);
        System.out.println("Roleta: Valor   Individuo");
        for (int i = 0; i < numRoleta; i++) {
            System.out.println("\t" + roleta[i] + "\t" + indRoleta[i]);
        }



//Recombinação

        recomAleatoria = new double[numPares];

        double pr; //pr=pares da recombinação a não reproduzir
        
        pr = (double)((rec * numPares) / 100.0);
        nPares = (int) pr;

        //garantir que pelo menos um casal não reproduz
            vf = true;
            while (vf == true) {
                for (int i = 0; i < numPares; i++) {
//                DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
                    double val = (Math.random());
//                //formatar o numero com 3 casas decimais numa String
//                String av = String.format("%.2f", val);
//                //Converter a String para Double e guardar
//                recomAleatoria[i] = (Double) df.parse(av);

                    val = ((int) (val * 1000) / 1000.0);
                    recomAleatoria[i] = val;

                }
                cont = 0;
              
                    for (int i = 0; i < numPares; i++) {
                        if (recomAleatoria[i] >= recombinacao) {
                            cont++;
                        }
                    }
                    if (cont == nPares) {
                        vf = false;
                    }
                
            }

        //pontos de corte
        pontosCorte = new int[numPares][3];//cada coluna corresponde a cada casal
        for (int i = 0; i < numPares; i++) {
            //garantir que não existem 2 pontos de corte iguais
            vf = true;
            while (vf == true) {

                for (int y = 0; y < 3; y++) {
                    pontosCorte[i][y] = (int) (1 + Math.random() * 12);

                }
                cont = 0;

                if (pontosCorte[i][2] == pontosCorte[i][1]) {
                    cont++;
                } else if (pontosCorte[i][2] == pontosCorte[i][0]) {
                    cont++;
                } else if (pontosCorte[i][1] == pontosCorte[i][0]) {
                    cont++;
                }


                if (cont == 0) {
                    vf = false;
                }
            }
            if (recomAleatoria[i] >= recombinacao) {
                for (int y = 0; y < 3; y++) {
                    pontosCorte[i][y] = 0;
                    vf = false;
                }
            }
        }



        //ordenar pontos de corte
        for (int i = 0; i < numPares; i++) {
            for (int y = 0; y < 3; y++) {
                for (int a = y + 1; a < 3; a++) {

                    if (pontosCorte[i][y] > pontosCorte[i][a]) {
                        int aux = pontosCorte[i][y];
                        pontosCorte[i][y] = pontosCorte[i][a];
                        pontosCorte[i][a] = aux;

                    }
                }
            }
        }
        //escrever tabela da recombinação c/ respetivos pontos de corte
        System.out.println("Pares   Valor Aleatório[0;1]   Ponto de Corte   Filhos");
        for (int i = 0; i < 2; i++) {
            System.out.println(" " + indElitismo[i] + "             " + "-" + "                   " + "-" + "              " + "i'" + (i+1));
        }
        for(int i=0; i<numPares;i++){
            
            System.out.println(" "+indRoleta[(i*2)]+" e "+indRoleta[(i*2+1)]+"       "+recomAleatoria[i]+"                "+pontosCorte[i][0]+","+pontosCorte[i][1]+" e "+pontosCorte[i][2]+"          i'"+(i+3)+" e "+(i+4));
        }
            System.out.println("\n\n");
            System.out.println("-------Reprodução--------\n");
//        //---tabela de recombinação----
//        for(int i=0;i<4;i++){
//            System.out.println("");
//            for(int y=0;y<3;y++){
//                System.out.println(pontosCorte[i][y]);
//            }
//        }
//        
//        
//        System.out.println("---");
//        for(int i=0;i<4;i++){
//            for(int y=0;y<3;y++){
//                System.out.println(pontosCorte[i][y]);
//            }
//            System.out.println("--");
//        }
//        //--fim----



//operador de recombinação
        //filhos por elitismo
        cromossomaFilho = new String[numInd];
        for (int i = 0; i < 2; i++) {
            for (int y = 0; y < numInd; y++) {
                if (indElitismo[i] == y) {
                    cromossomaFilho[i] = cromossoma[y];
                }
            }
        }

        //filhos por roleta
        //se numero de individuos por rolete for par
            if (numRoleta % 2 == 0) {
                for (int i = 0; i < numPares; i++) {

                    int p = indRoleta[(i * 2)];
                    int m = indRoleta[(i * 2 + 1)];
                    
                    pai = cromossoma[p - 1];
                    mae = cromossoma[m - 1];

                    pcorte1 = pontosCorte[i][0];
                    pcorte2 = pontosCorte[i][1];
                    pcorte3 = pontosCorte[i][2];
                    //adicionar um espaço nos pontos de corte
                    //pai
                    sb = new StringBuilder(pai);
                    sb.insert(pai.length() - (13 - pcorte1), ' ');
                    sb.insert(pai.length() - (12 - pcorte2), ' ');
                    sb.insert(pai.length() - (11 - pcorte3), ' ');
                    pai = sb.toString();
                    System.out.println(pai);
                    //mae
                    sb = new StringBuilder(mae);
                    sb.insert(mae.length() - (13 - pcorte1), ' ');
                    sb.insert(mae.length() - (12 - pcorte2), ' ');
                    sb.insert(mae.length() - (11 - pcorte3), ' ');
                    mae = sb.toString();
                    System.out.println(mae);
                    //separar a string em varias pelos pontos de corte
                    spai = new String[4];
                    smae = new String[4];

                    spai = pai.split(" ");
                    smae = mae.split(" ");

                    filho1 = spai[0] + smae[1] + spai[2] + smae[3];
                    filho2 = smae[0] + spai[1] + smae[2] + spai[3];
                    cromossomaFilho[i * 2 + 2] = filho1;
                    cromossomaFilho[i * 2 + 3] = filho2;
                    System.out.println(filho1);
                    System.out.println(filho2);
                    System.out.println("---");
                }
            }else{ // se for impar
                for (int i = 1; i < numPares; i++) {

                    int p = indRoleta[(i * 2 - 1)];
                    int m = indRoleta[(i * 2)];
                    System.out.println("p:"+p+"|m:"+m);
                    pai = cromossoma[p - 1];
                    mae = cromossoma[m - 1];

                    pcorte1 = pontosCorte[i][0];
                    pcorte2 = pontosCorte[i][1];
                    pcorte3 = pontosCorte[i][2];
                    //adicionar um espaço nos pontos de corte
                    //pai
                    sb = new StringBuilder(pai);
                    sb.insert(pai.length() - (13 - pcorte1), ' ');
                    sb.insert(pai.length() - (12 - pcorte2), ' ');
                    sb.insert(pai.length() - (11 - pcorte3), ' ');
                    pai = sb.toString();
                    System.out.println(pai);
                    //mae
                    sb = new StringBuilder(mae);
                    sb.insert(mae.length() - (13 - pcorte1), ' ');
                    sb.insert(mae.length() - (12 - pcorte2), ' ');
                    sb.insert(mae.length() - (11 - pcorte3), ' ');
                    mae = sb.toString();
                    System.out.println(mae);
                    //separar a string em varias pelos pontos de corte
                    spai = new String[4];
                    smae = new String[4];

                    spai = pai.split(" ");
                    smae = mae.split(" ");
                    cromossomaFilho[3] = cromossoma[indRoleta[0]];
                    filho1 = spai[0] + smae[1] + spai[2] + smae[3];
                    filho2 = smae[0] + spai[1] + smae[2] + spai[3];
                    cromossomaFilho[i * 2 + 3] = filho1;
                    cromossomaFilho[i * 2 + 4] = filho2;
                    System.out.println(filho1);
                    System.out.println(filho2);
                    System.out.println("---");
                }
            }

        //tabela da recombinação
        System.out.println("\n-----Recombinação-----");
        for (int i = 0; i < numInd; i++) {
            System.out.println(cromossomaFilho[i]);
        }

//mutação

        mutacao = new int[2][2];//1ª linha para os individuos e 2ªlinha para os genes
        //gerar individuos
        for (int i = 0; i < 2; i++) {
            mutacao[0][i] = (int) (1 + Math.random() * numInd);
        }
        //gerar genes
        for (int i = 0; i < 2; i++) {
            mutacao[1][i] = (int) (1 + Math.random() * 13);
        }


//aplicar mutação no gene escolhido       

        ind1 = mutacao[0][0];
        ind2 = mutacao[0][1];
        gene1 = mutacao[1][0];
        gene2 = mutacao[1][1];
       
        for (int i = 0; i < numInd; i++) {
            if(i==(ind1-1)){
                individuo1=cromossomaFilho[i];
            }if (i==(ind2-1)){
                individuo2=cromossomaFilho[i];
            }
        }

        //1º gene
        sb = new StringBuilder(individuo1);
        g = sb.charAt(gene1-1);
        if(g=='1'){
            g = '0';
        }else{
            g = '1';
        }
        sb.deleteCharAt(gene1-1);
        sb.insert(gene1-1, g);
        individuo1 = sb.toString();
        cromossomaFilho[ind1-1] = individuo1;
        
        //2º gene
        sb = new StringBuilder(individuo2);
        g = sb.charAt(gene2-1);
        if(g=='1'){
            g = '0';
        }else{
            g = '1';
        }
        sb.deleteCharAt(gene2-1);
        sb.insert(gene2-1, g);
        individuo2 = sb.toString();
        cromossomaFilho[ind2-1] = individuo2;
        
        //tabela com a mutação
        System.out.println("\n-----Mutação------");
            System.out.println("\n Genes a alterar:");
            System.out.println("-> Individuo "+ind1+" no gene "+gene1);
            System.out.println("-> Individuo "+ind2+" no gene "+gene2+"\n");
        for(int i=0;i<numInd;i++){
            System.out.println(cromossomaFilho[i]);
        }


////restaurar vetores do ind. e do cromossoma
//        ind = new Integer[10]; //criar um vector com 10 possiçoes
//        cromossoma = new String[10];
//
        
                
        
        
        
        
        
        //passar os cromossomas do vetor dos filhos para o vetor cromossoma
        for(int i=0;i<numInd;i++){
            cromossoma[i] = cromossomaFilho[i];
        }
        //converter os cromossomas em decimal e guardar o valor correspontente no vetor ind
        for(int i=0;i<numInd;i++){
            ind[i]=Integer.parseInt(cromossoma[i], 2);
        }
       } //fim da iteração
        
       //escrever tabela de avaliação
        System.out.println("****************************************************");
        System.out.println("********Avaliação*******");
        System.out.println("Geração   Melhor Qualidade   Media");
        for (int j=0;j<iteracao;j++){
            System.out.println("   "+(j+1)+"\t   "+melhorInd[j]+"\t\t "+mediaQual[j]);
        }
        
        
    }

}
