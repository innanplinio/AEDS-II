package arvoreh;

import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.in;
import java.util.Scanner;

public class ArvoreH {

    public static void main(String[] args) throws IOException, FileNotFoundException {

        Scanner entrada = new Scanner(System.in);
        System.out.println("Escolha uma op��o:\n1....Huffman\n2....ORetorno\n0....Sair");
        int a = entrada.nextInt();
        Huffman h = new Huffman();
        while (a != 0) {
            switch (a) {
                case 1:
                    h.Buscar(); // CHAMO MINHA FUÇÃO BUSCAR, ESTA VERIFICA AS INCIDENCIAS DAS PALAVRA E CHAMA OUTRAS FUNÇOES QUE 
                    //CRIAM A ARVORE DE INCIDENCIAS, GERAM OS CODIGOS, E ESCREVEM OS ARQUIVOS COMPACTADOS
                    break;
                case 2:
                    h.HuffmanORetorno();//O RETORNO COMO JA DIZ O NOME FAZ O INVERSO RECEBE UMA TABBELA E UM ARQUIVO COMPACTADO 
                    //E DESCOMPACTA O MESMO CRIANDO UM ARQUIVO CHAMADO "ORETORNO.TXT"
                    break;
                default:
                    System.out.print("Op��o inv�lida");
                    break;

            }
            System.out.println("Escolha uma op��o:\n1....Huffman\n2....ORetorno\n0....Sair");
            a = entrada.nextInt();
        }
    }

}
