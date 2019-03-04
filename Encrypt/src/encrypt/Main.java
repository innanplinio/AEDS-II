/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encrypt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author innan
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //Verifica se arquivo a ser encriptado existe
        File file = new File("Mensagem.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        File file2 = new File("cifrado.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        Scanner in;
        try ( //Gravando Textos no arquivo
                FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw)) {
            System.out.println("Digite o texto a ser gravado no arquivo(fim para finalizar):");
            in = new Scanner(System.in);
            String text = in.nextLine();
            while (!text.toLowerCase().equals("fim")) {
                bw.write(text);
                bw.newLine();
                text = in.nextLine();
            }
        }
        FileReader fr;
        BufferedReader br;
        try (FileWriter fw2 = new FileWriter(file2); BufferedWriter bw2 = new BufferedWriter(fw2)) {
            //Recebendo palavra-chave
            System.out.println("Digite a palavra-chave: ");
            String chave = in.nextLine();
            chave = chave.toLowerCase();
            //Criptografando
            System.out.println("Encriptando...");
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String texto = br.readLine();
            ArrayList cifrado = new ArrayList();
            String strCifrado;
            int asc;
            while (texto != null) {
                texto = texto.toLowerCase();
                for (int i = 0, j = 0; i < texto.length(); i++, j++) {
                    if ((int) texto.charAt(i) != 32) {
                        if (j >= chave.length()) {
                            j = 0;
                        }
                        asc = (((int) texto.charAt(i)) - 96 + ((int) chave.charAt(j)) - 96) + 96;
                        while (asc > 122) {
                            asc = asc - 26;
                        }
                        cifrado.add((char) asc);
                        //System.out.println("asc: " + asc + " cifrado: " + cifrado);
                    } else {
                        //System.out.println(texto.charAt(i) + "ESPAÇO = " + (int) texto.charAt(i));
                        asc = (int) texto.charAt(i);
                        cifrado.add((char) asc);
                        //System.out.println("asc:ESPAÇO " + asc + " cifrado: " + cifrado);
                        j--;
                    }
                }
                strCifrado = cifrado.toString().replaceAll(",", "");
                //.replaceAll("[^0-9A-Za-z]", "")
                strCifrado = strCifrado.replace("[", "");
                strCifrado = strCifrado.replace("]", "");
                System.out.println(strCifrado);
                bw2.write(strCifrado);
                bw2.write("\n");
                cifrado.clear();
                texto = br.readLine();
            }
        }
        br.close();
        fr.close();

    }

}
