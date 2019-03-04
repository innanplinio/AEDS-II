/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import huffman.Palavra;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class ArvoreHuffman {

    public static void main(String[] args) throws IOException, FileNotFoundException {

        Scanner entrada = new Scanner(System.in);
        System.out.println("O que voce deseja?:\n1....Construir Arvore Huffman\n2....Transformar arquivo compactado em texto\n0....Sair");
        int x = entrada.nextInt();
        ControleArvore h = new ControleArvore();
        while (x != 0) {
            switch (x) {
                case 1:
                    h.SearchAndBuild(); // CHAMO MINHA FUÃ‡ÃƒO BUSCAR, ESTA VERIFICA AS INCIDENCIAS DAS PALAVRA E CHAMA OUTRAS FUNÃ‡OES QUE 
                    //CRIAM A ARVORE DE INCIDENCIAS, GERAM OS CODIGOS, E ESCREVEM OS ARQUIVOS COMPACTADOS
                    break;
                case 2:
                    h.Descomprimir();//O RETORNO COMO JA DIZ O NOME FAZ O INVERSO RECEBE UMA TABBELA E UM ARQUIVO COMPACTADO 
                    //E DESCOMPACTA O MESMO CRIANDO UM ARQUIVO CHAMADO "ORETORNO.TXT"
                    break;
                default:
                    System.out.print("Opção inválida\n");
                    break;

            }
            System.out.println("Escolha uma opção:\n1....Huffman\n2....ORetorno\n0....Sair");
            x = entrada.nextInt();
        }
    }

}
