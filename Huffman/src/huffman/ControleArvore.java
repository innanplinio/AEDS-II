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
import java.util.ArrayList;

/**
 *
 * @author sarto
 */
public class ControleArvore {

    private final ArrayList<Palavra> palavras;
    private int valor;
    private final ArrayList<ArvoreNo> Arvore;
    private ArrayList<Palavra> Tabela;

    public ControleArvore() {
        this.palavras = new ArrayList<>();
        this.Arvore = new ArrayList<>();
        this.Tabela = new ArrayList<>();
    }

    public void SearchAndBuild() throws FileNotFoundException, IOException {

        //Leitura do arquivo .txt
        try {
            FileReader arquivo = new FileReader("texto.txt");
            BufferedReader leitor = new BufferedReader(arquivo);

            String linha = leitor.readLine(); // linha = lê a linha do arquivo

            if (linha == null) {
                System.out.println("Arquivo de texto vazio !");
                return;
            }else if(linha.isEmpty()){
                System.out.println("Arquivo de contem tabulação ou enter!");
                return;
            }
            char[] aux = new char[99999999]; //Vetor de char
            int j = 0;
            while (linha != null) { // While pra transformar a String linha lida em vetor de caracteres
                char[] aux2 = linha.toCharArray();//Vetor aux2 recebe a linha em caractere (transforma String em vetor de char) 
                char[] linha2 = new char[aux2.length + 1]; //criado com o tamanho + 1 da linha para garantir a inserção da última palavra
                for (int i = 0; i < aux2.length; i++) { // linha2 recebe aux2
                    linha2[i] = aux2[i];
                }
                linha2[linha2.length - 1] = ' '; // adiciona um espaço vazio no final da linha2 como marcador 
                for (int i = 0; i < linha2.length; i++) { // 
                    if (linha2[i] != ' ') { // se linha2 na posição i  for diferente de espaço e da tamanho - 1
                        aux2[j] = linha2[i]; //vetor aux2 na posição j recebe linha2 na posição i
                        j++;
                    } else {
                        if (aux2[j - 1] == '!' || aux2[j - 1] == '?' || aux2[j - 1] == '.' || aux2[j - 1] == ',' || aux2[j - 1] == ':' || aux2[j - 1] == ';') {
                            // verifico se nao tem caracteres especiais
                            char[] word = new char[j - 1]; // cria um novo vetor para receber a palavra encontrada, excluindo a posiçao do acento
                            word = CopiaVetor(word, aux2); // copia o que esta em aux2 para word
                            if (palavras.isEmpty() == true) { //se meu ArrayList estiver vazio
                                Palavra p = new Palavra(1, String.copyValueOf(word)); //Construtor de Palavras que recebe a quantidade e a transformação do vetor de caracteres em String
                                palavras.add(p); // adiciono p(word) no array de palavras
                            } else if (Contem(palavras, word) == true) { // verifica se a palavra encontrada já existe no Array
                                palavras.get(valor).setQuantidade(palavras.get(valor).getQuantidade() + 1);// seta na posição encontrada o Quantidade e incrementa ele ??
                            } else if (Contem(palavras, word) == false) { // chama a função Contem, se retornar false
                                Palavra p = new Palavra(1, String.copyValueOf(word)); // se nao existe no array
                                palavras.add(p); // adiciona o objeto p(word) no array
                            }
                            EsvaziaVetor(word);
                            EsvaziaVetor(aux2);//Chama a função EsvaziaVetor
                            j = 0;
                        } else { // se nao encontrar nenhum caracter especial
                            char[] word = new char[j];
                            word = CopiaVetor(word, aux2);
                            if (palavras.isEmpty() == true) {
                                Palavra p = new Palavra(1, String.copyValueOf(word));
                                palavras.add(p); // adiciona o objeto p no array
                            } else if (Contem(palavras, word) == true) {
                                palavras.get(valor).setQuantidade(palavras.get(valor).getQuantidade() + 1);
                            } else if (Contem(palavras, word) == false) {
                                Palavra p = new Palavra(1, String.copyValueOf(word));
                                palavras.add(p);
                            }
                            j = 0;
                            EsvaziaVetor(word);
                            EsvaziaVetor(aux2);//Chama a função EsvaziaVetor
                        }

                    }
                }
                EsvaziaVetor(aux2);
                EsvaziaVetor(linha2);
                linha = leitor.readLine(); // String linha recebe uma nova linha do texto
            }
            OrdenaVetor(palavras); //Ordena as palavras dentro do array, de acordo com a Quantidade delas
            ConstruirArvore(palavras, Arvore); //Chama a função que constrói a Árvore

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado!");
        } catch (IOException i) {
            System.out.println("Erro na leitura arquivo!");
        }

    }

    public boolean Contem(ArrayList<Palavra> palavras, char[] p) { //recebe uma ArrayList e um vetor de char
        for (int i = 0; i < palavras.size(); i++) {
            if (palavras.get(i).getPalavra().equals(String.copyValueOf(p))) { // se a palavra na posição i do ArrayList for igual a String do vetor de char
                valor = i; //salva a posição na variável valor
                return true;
            }
        }
        return false;
    }

    public void EsvaziaVetor(char[] p) {
        for (int i = 0; i < p.length; i++) {
            p[i] = ' '; // cada posição do vetor recebe espaço em branco
        }
    }

    public void OrdenaVetor(ArrayList<Palavra> array) {

        Collections.sort(array, (Palavra p1, Palavra p2) -> { // tem como parâmetro o ArrayList recebido e duas palavras dele

            //Comparando objeto 1 com objeto 2 pela Quantidade deles
            if (p1.qtd < p2.qtd) {
                return -1;
            } else if (p1.qtd > p2.qtd) {
                return 1;
            } else { // se iguais, retornar 0
                return 0;
            }
        });
    }

    public char[] CopiaVetor(char[] a, char[] b) { // recebe dois vetores 
        for (int i = 0; i < a.length; i++) {
            a[i] = b[i]; // vetor a vai receber tudo que esta no vetor b
        }
        return a;
    }

    public void ConstruirArvore(ArrayList<Palavra> palavras, ArrayList<ArvoreNo> Arvore) throws IOException { // recebe  ArrayList do tipo Palavra e o ArrayList do tipo ArvoreNo
        try {
            while (palavras.size() != 1) { //Enquanto o tamanho do ArrayList palavras for diferente de 1
                if (ContemArv(Arvore, palavras.get(0).getPalavra()) == false && ContemArv(Arvore, palavras.get(1).getPalavra()) == false) { //Se o Arvore não contem as duas primeiras palavras do arraylist palavras, executa
                    ArvoreNo N1 = new ArvoreNo(palavras.get(0).getQuantidade()); //Construtor de N1 (tipo ArvoreNo) que recebe a frequência da palavra
                    N1.setFrase(palavras.get(0).getPalavra()); //seta a frase do N1 com a palavra do array na posição 0
                    Arvore.add(N1);// adiciona N1 na Arvore
                    ArvoreNo N2 = new ArvoreNo(palavras.get(1).getQuantidade()); //Construtor de N2 (tipo ArvoreNo) que recebe a frequência da palavra
                    N2.setFrase(palavras.get(1).getPalavra()); //seta a frase do N2 com a palavra do array na posição 1
                    Arvore.add(N2); // adiciona N2 na Arvore
                    ArvoreNo NO = new ArvoreNo(N1.getFreq() + N2.getFreq()); //Construtor de N0 (tipo ArvoreNo)que recebe a soma da frequÊncia de N1 e N2
                    if (N1.getFreq() < N2.getFreq()) { //se a frequência de N1 < N2
                        NO.setEsquerda(N1); //seta N1 no lado esquerdo de N0
                        N1.setBit("1");// N1 seta o bit como 1
                        NO.setDireita(N2); //seta N2 no lado direito de N0
                        N2.setBit("0"); // N2 seta o bit como 0
                        NO.setFrase(Concatenar(N1.getFrase(), N2.getFrase())); // a frase de N0 se torna a concatenação das frases de N1 e N2
                    } else { //se a frequência de N1 > N2
                        NO.setDireita(N1); //seta N1 no lado direito de N0
                        N1.setBit("0"); // N1 seta o bit como 0
                        NO.setEsquerda(N2);//seta N2 no lado esquerdo de N0
                        N2.setBit("1"); // N2 seta o bit como 1
                        NO.setFrase(Concatenar(N1.getFrase(), N2.getFrase())); // seta a frase de N0 com a concatenação de N1 e N2
                    }
                    NO.setMarca(5); //NO recebe marca 5 ??
                    Arvore.add(NO); // adiciona NO em Arvore
                    palavras.remove(0);
                    palavras.remove(0); // remove as duas primeiras posições de palavras consecutivas
                    Palavra P = new Palavra(NO.getFreq(), NO.getFrase()); //cria um objeto Palavra com a frequência e frase de N0
                    palavras.add(P);// adiciona em palavras
                    OrdenaVetor(palavras);

                } else if (ContemArv(Arvore, palavras.get(0).getPalavra()) == true && ContemArv(Arvore, palavras.get(1).getPalavra()) == true) { //Se a Arvore conter a posição 0 e 1 da palavra
                    ContemArv(Arvore, palavras.get(0).getPalavra()); //chama a função ContemArv para saber a posição 
                    int a = valor;
                    ContemArv(Arvore, palavras.get(1).getPalavra()); //chama a função ContemArv pra saber a posição, não precisa adicionar a uma variável pois valor é uma variável global
                    ArvoreNo NO = new ArvoreNo(Arvore.get(a).getFreq() + Arvore.get(valor).getFreq()); //Construtor de N0 (tipo ArvoreNo)que recebe a soma da frequência de Arvore na posição a e Arvore na posição valor
                    if (Arvore.get(a).getFreq() < Arvore.get(valor).getFreq()) { //se a frequencia de Arvore na poisao a for < Arvore na posicao valor
                        NO.setEsquerda(Arvore.get(a)); //seta Arvore na posição a no lado esquerdo de N0
                        Arvore.get(a).setBit("1");// Arvore na posição a seta o bit como 1
                        NO.setDireita(Arvore.get(valor)); //seta Arvore na posição valor no lado direito de N0
                        Arvore.get(valor).setBit("0"); // Arvore na posição valor seta o bit como 0
                        NO.setFrase(Concatenar(Arvore.get(a).getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatenação de Arvore na posição a e Arvore na posição valor
                    } else { //se a frequência de Arvore na posição a > Arvore na posição valor
                        NO.setDireita(Arvore.get(a)); //seta Arvore na posição a no lado direito de N0
                        Arvore.get(a).setBit("0"); // Arvore na posição a seta o bit como 0
                        NO.setEsquerda(Arvore.get(valor)); //seta Arvore na posição valor no lado esquerdo de N0
                        Arvore.get(valor).setBit("1"); // Arvore na posição valor seta o bit como 1
                        NO.setFrase(Concatenar(Arvore.get(a).getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatenação de Arvore na posição a e Arvore na posição valor
                    }
                    NO.setMarca(5); // NO recebe marca 5 
                    Arvore.add(NO); // adiciona NO em Arvore
                    palavras.remove(0);
                    palavras.remove(0); // remove as duas primeiras posições de palavras consecutivas
                    Palavra P = new Palavra(NO.getFreq(), NO.getFrase()); //cria um objeto Palavra com a frequência e frase de N0
                    palavras.add(P); // adiciona o objeto em Word
                    OrdenaVetor(palavras); // chama função OrdenaVetor

                } else if (ContemArv(Arvore, palavras.get(0).getPalavra()) == true) { //Se a Arvore conter a posição 0 da Palavra
                    ArvoreNo N1 = new ArvoreNo(palavras.get(1).getQuantidade()); //Construtor de N1 (tipo ArvoreNo) que recebe a  frequência da palavra
                    N1.setFrase(palavras.get(1).getPalavra()); //seta a frase do N1 com a palavra do array na posição 1
                    Arvore.add(N1);
                    ArvoreNo NO = new ArvoreNo(N1.getFreq() + Arvore.get(valor).getFreq()); //Construtor de N0 (tipo ArvoreNo)que recebe a soma da frequência de N1 e de Arvore na posição valor
                    if (N1.getFreq() < Arvore.get(valor).getFreq()) { //se a frequência de N1 < Arvore na posição valor
                        NO.setEsquerda(N1); //seta N1 no lado esquerdo de N0
                        N1.setBit("1");
                        NO.setDireita(Arvore.get(valor)); //seta Arvore na posição valor no lado direito de N0
                        Arvore.get(valor).setBit("0"); // Arvore na posição valor seta o bit como 0
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatenação de N1 e Arvore na posição valor
                    } else { //se a frequência de N1 > Arvore na posição valor
                        NO.setDireita(N1); //seta N1 no lado direito de N0
                        N1.setBit("0");
                        NO.setEsquerda(Arvore.get(valor)); //seta Arvore na posição valor no lado esquerdo de N0
                        Arvore.get(valor).setBit("1"); // Arvore na posição valor seta o bit como 1
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatenação de N1 e Arvore na posição valor
                    }
                    NO.setMarca(5); // NO recebe marca 5 
                    Arvore.add(NO); // adiciona NO em Arvore
                    palavras.remove(0);
                    palavras.remove(0); // remove as duas primeiras posições de palavras consecutivas
                    Palavra P = new Palavra(NO.getFreq(), NO.getFrase()); //cria um objeto Palavra com a frequência e frase de N0
                    palavras.add(P);
                    OrdenaVetor(palavras);
                } else if (ContemArv(Arvore, palavras.get(1).getPalavra()) == true) { //Se a Arvore conter a posição 1 da palavra
                    ArvoreNo N1 = new ArvoreNo(palavras.get(0).getQuantidade());//Construtor de N1 (tipo ArvoreNo) que recebe a  frequência da palavra
                    N1.setFrase(palavras.get(0).getPalavra());  //seta a frase do N1 com a palavra de Word na posição 0
                    Arvore.add(N1);
                    ArvoreNo NO = new ArvoreNo(N1.getFreq() + Arvore.get(valor).getFreq()); //Construtor de N0 (tipo ArvoreNo)que recebe a soma da frequência de N1 e de Arvore na posição valor
                    if (N1.getFreq() < Arvore.get(valor).getFreq()) { //se a frequência de N1 < Arvore na posição valor
                        NO.setEsquerda(N1);  //seta N1 no lado esquerdo de N0
                        N1.setBit("1");
                        NO.setDireita(Arvore.get(valor)); //seta Arvore na posição valor no lado direito de N0
                        Arvore.get(valor).setBit("0"); // Arvore na posição valor seta o bit como 0
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatenação de N1 e Arvore na posição valor
                    } else { // se a frequencia de N1 > Arvore na posicao valor
                        NO.setDireita(N1); //seta N1 no lado direito de N0
                        N1.setBit("0");
                        NO.setEsquerda(Arvore.get(valor)); //seta Arvore na posição valor no lado esquerdo de N0
                        Arvore.get(valor).setBit("1"); // Arvore na posição valor seta o bit como 1
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatenação de N1 e Arvore na posição valor
                    }
                    NO.setMarca(5); // NO recebe marca 5 
                    Arvore.add(NO); // adiciona NO em Arvore
                    palavras.remove(0);
                    palavras.remove(0); // remove as duas primeiras posições de palavras consecutivas
                    Palavra P = new Palavra(NO.getFreq(), NO.getFrase()); //cria um objeto Palavras com a frequência e frase de N0
                    palavras.add(P);
                    OrdenaVetor(palavras);
                }
            }
            GeraTabela(Arvore); // Chama a função GeraTabela que passa como parâmetro a Arvore
        } catch (IOException i) {
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public String Concatenar(String s1, String s2) {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(s1);
        strBuf.append(s2); //passa as duas strings por parâmetro
        return strBuf.toString(); // retorna a concatenação
    }

    // verifica se a palavra ja esta na arvore
    public boolean ContemArv(ArrayList<ArvoreNo> Arvore, String palavra) { //recebe Arvore e uma string (palavra)
        for (int i = 0; i < Arvore.size(); i++) {
            if (Arvore.get(i).getFrase().equals(palavra)) { //se Arvore na posição i for igual a string recebida
                valor = i;
                return true;
            }
        }
        return false;
    }

    public void GeraTabela(ArrayList<ArvoreNo> Arv) throws IOException { //recebe como parâmetro a ArrayList Arv
        try {
            Arv.get(Arv.size() - 1).setBit("1"); // seta na raiz da Arvore (ultima posicao do array) o bit 1 
            ArvoreNo Arvore = Arv.get(Arv.size() - 1);// recebo a raiz da minha arvore
            String aux; //recebera a palavra contida em cada No
            String aux2 = null;// recebera o codigo da palavra
            aux = Arvore.getBit(); // pego o Bit do No raiz
            while (Arv.size() != 1) { //percorro ate ficar apenas minha raiz no meu Array de No's
                if (Arvore.getEsquerda() != null) { //verifico se a esquerda é nula
                    if (Arvore.getEsquerda().getEsquerda() == null && Arvore.getEsquerda().getDireita() == null) { //verifico se a direita e a esquerda do no mais a baixo são nulas
                        ArvoreNo no = Arvore.getEsquerda();// No auxiliar recebe a esquerda
                        Arvore.setEsquerda(null);// faço o lado esquerdo apontar para null
                        Arvore = no; //continuo o while a partir do No auxiliar
                        aux = Concatenar(aux, Arvore.getBit());// concateno a palavra com a palavra anterior
                    } else {
                        Arvore = Arvore.getEsquerda();//continuo a busca para a esquerda
                        aux = Concatenar(aux, Arvore.getBit());// concateno a palavra com a palavra anterior
                    }
                } else if (Arvore.getDireita() != null) {//verifico se a direita é nula
                    if (Arvore.getDireita().getEsquerda() == null && Arvore.getDireita().getDireita() == null) {//verifico se a direita e a esquerda do no mais a baixo são nulas
                        ArvoreNo no = Arvore.getDireita();// No auxiliar recebe a direita
                        Arvore.setDireita(null);//faço a direita apontar para null
                        Arvore = no;
                        aux = Concatenar(aux, Arvore.getBit());// concateno a palavra com a palavra anterior
                    } else {
                        Arvore = Arvore.getDireita();//continuo a busca para a direita
                        aux = Concatenar(aux, Arvore.getBit());// concateno a palavra com a palavra anterior
                    }
                } else {
                    if (Arvore.getMarca() == 5) {// verifico a marca do meu No para não colocar No's marcados na Tabela
                        Arv.remove(Arvore);//removo o meu No folha do Array de No's
                        Arvore = Arv.get(Arv.size() - 1);//recomeço minha busca a partir da raiz da arvore
                        aux = Arvore.getBit();// coloco o bit da minha raiz no aux
                    } else {
                        aux2 = Arvore.getFrase();// pego a frase referente à aquele No
                        Arv.remove(Arvore); //removo o meu No folha do Array de No's
                        Arvore = Arv.get(Arv.size() - 1); //recomeço minha busca a partir da raiz da arvore
                        Palavra p = new Palavra(aux2, aux);// crio uma palavra com o codigo e a palavra do No folha
                        Tabela.add(p);// adiciono essa palavra no meu ArrayList Tabela(o que contem os No's folhas)
                        aux = Arvore.getBit();// coloco o bit da minha raiz no aux
                    }
                }
                
                
            }
            EscreveTabela(Tabela);
                EscreverCompactado(Tabela);

        } catch (IOException i) {
            System.out.println("Erro na leitura arquivo!");
        }
    }


//    IMPORTANTE !!!!!!!!!
//    IMPORTANTE !!!!!!!!!
//    IMPORTANTE !!!!!!!!!
//    IMPORTANTE !!!!!!!!!
    //REFAZER OS COMENTARIOS DAQUI PRA BAIXO
    public void EscreveTabela(ArrayList<Palavra> arv) throws IOException { // recebe a ArrayList arv
        try {
            FileWriter tabela = new FileWriter("Tabela.txt"); //cria um arquivo .txt
            BufferedWriter bw = new BufferedWriter(tabela); //cria o escritor do arquivo

            for (int i = 0; i < arv.size(); i++) { //for percorre o tamanho do ArrayList
                String s = arv.get(i).palavra;// String recebe a palavra da posi��o i do ArrayList
                bw.write(s); // escreve a String no arquivo
                bw.flush(); // buffer que limpa o escritor
                bw.write(" "); // escreve espa�o em branco no arquivo
                bw.flush(); // buffer que limpa o escritor
                s = arv.get(i).codigo; // s recebe o c�digo da arv na posição i
                bw.write(s); // escreve o inteiro no arquivo
                bw.flush(); // buffer que limpa o escritor
                bw.write(" ");// escreve o espa�o
                bw.flush();// limpa o buffer
                bw.newLine(); // escreve uma nova linha
            }
            bw.close(); // fecha o escritor
        } catch (IOException i) { //exce��o que verifica erro na leitura do arquivo
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public void EscreverCompactado(ArrayList<Palavra> arv) throws IOException, FileNotFoundException { // recebe a Arraylist arv
        try {
            //Leitura e escrita dos arquivos .txt
            FileReader arquivo = new FileReader("texto.txt");
            BufferedReader leitor = new BufferedReader(arquivo);
            FileWriter saida = new FileWriter("Saida.txt");
            BufferedWriter bw = new BufferedWriter(saida);
            String recebeLinha = leitor.readLine(); // String que vai receber a primeira linha do arquivo
            String palavra; // Vari�vel do tipo String
            char[] aux = new char[99999999]; // Vetor do tipo char
            int j = 0; // inicializa j em 0
            while (recebeLinha != null) { // Enquanto linha diferente de nulo
                char[] aux2 = recebeLinha.toCharArray();
                char[] stringLinha = new char[aux2.length + 1];//Vetor char que recebe a linha em caractere   
                for (int i = 0; i < aux2.length; i++) {
                    stringLinha[i] = aux2[i];
                }
                stringLinha[stringLinha.length - 1] = ' ';
                for (int i = 0; i < stringLinha.length; i++) { // Percorre o tamanho de stringLinha
                    if (stringLinha[i] != ' ' && i != stringLinha.length - 1) { // se stringLinha na posi��o i  for diferente de espa�o e do tamanho - 1
                        aux[j] = stringLinha[i]; // aux na posi��o j recebe stringLinha na posição i
                        j++; //incrementa j
                    } else {
                        if (aux[j - 1] == '!' || aux[j - 1] == '?' || aux[j - 1] == '.' || aux[j - 1] == ',' || aux[j - 1] == ':' || aux[j - 1] == ';') {
                            char[] vetPalavra = new char[j - 1]; // cria um novo vetor com o tamanho de j que foi sendo incrementado
                            vetPalavra = CopiaVetor(vetPalavra, aux); //chama a fun��o ArrumaVetor que tem como par�metro dois vetores de char

                            palavra = String.copyValueOf(vetPalavra); // palavra recebe o vetor vetPalavra como String
                            for (int k = 0; k < arv.size(); k++) { //percorre at� o tamanho de arv
                                if (arv.get(k).getPalavra().equals(palavra) == true) { // se a palavra na posi��o k for igual a palavra recebida
                                    String p = arv.get(k).getCodigo(); // String recebe o c�digo na posi��o k
                                    bw.write(p); // escreve a string
                                    bw.flush(); // limpa o buffer
                                    bw.write(aux[j - 1]);
                                    bw.flush();
                                    bw.write(" "); // escreve o espa�o 
                                    bw.flush();// limpa o buffer
                                }
                            }
                            j = 0;   // zera o j
                            EsvaziaVetor(vetPalavra); // esvazia
                            EsvaziaVetor(aux); // vetores
                        } else {
                            char[] vetPalavra = new char[j]; // cria um novo vetor com o tamanho de j que foi sendo incrementado
                            vetPalavra = CopiaVetor(vetPalavra, aux); //chama a fun��o ArrumaVetor que tem como par�metro dois vetores de char
                            j = 0; // zera o j
                            palavra = String.copyValueOf(vetPalavra); // palavra recebe o vetor vetPalavra como String
                            for (int k = 0; k < arv.size(); k++) { //percorre at� o tamanho de arv
                                if (arv.get(k).getPalavra().equals(palavra) == true) { // se a palavra na posi��o k for igual a palavra recebida
                                    String p = arv.get(k).getCodigo(); // String recebe o c�digo na posi��o k
                                    bw.write(p); // escreve a string
                                    bw.flush(); // limpa o buffer
                                    bw.write(" "); // escreve o espa�o 
                                    bw.flush();// limpa o buffer
                                }
                            }
                            EsvaziaVetor(vetPalavra); // esvazia
                            EsvaziaVetor(aux); // vetores
                        }
                    }
                }
                bw.flush();// limpa o buffer
                bw.newLine(); // escreve nova linha
                recebeLinha = leitor.readLine(); // String linha recebe uma nova linha do texto
            }
            bw.close(); // fecha o bw
            leitor.close(); // fecha o leitor
        } catch (FileNotFoundException e) { //exce��o quando o arquivo n�o � encontrado
            System.out.println("Arquivo não encontrado!");
        } catch (IOException i) { //exce��o que verifica erro na leitura do arquivo
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public void Descomprimir() throws IOException, FileNotFoundException {
        try {
            //Inicialização dos arquivos para descompressão
            FileReader tabela = new FileReader("Tabela.txt");
            BufferedReader readtabela = new BufferedReader(tabela);
            FileReader saida = new FileReader("Saida.txt");
            BufferedReader readsaida = new BufferedReader(saida);
            FileWriter retorno = new FileWriter("Descomprimido.txt");
            BufferedWriter escritor = new BufferedWriter(retorno);

            Tabela = new ArrayList<>();
            String recebelinha = readtabela.readLine();
            char[] aux = new char[99999999];
            String palavra;
            int j = 0, indice = 0;
            while (recebelinha != null) {
                char[] stringlinha = recebelinha.toCharArray();
                for (int i = 0; i < stringlinha.length; i++) {
                    if (stringlinha[i] != ' ' && i != stringlinha.length - 1) {
                        aux[j] = stringlinha[i];
                        j++;
                    } else {
                        indice++;
                        char[] vetPalavra = new char[j];
                        vetPalavra = CopiaVetor(vetPalavra, aux);
                        j = 0;
                        palavra = String.copyValueOf(vetPalavra);
                        if (indice == 1) {
                            Palavra p = new Palavra(palavra, " ");
                            Tabela.add(0, p);
                        } else {
                            Tabela.get(0).setCodigo(palavra);
                            indice = 0;
                        }

                        EsvaziaVetor(vetPalavra);
                        EsvaziaVetor(aux);
                    }
                }
                recebelinha = readtabela.readLine();
            }
            recebelinha = readsaida.readLine();
            j = 0;

            while (recebelinha != null) {
                char[] stringlinha = recebelinha.toCharArray();
                for (int i = 0; i < stringlinha.length; i++) {
                    if (stringlinha[i] != ' ' && i != stringlinha.length - 1) {
                        aux[j] = stringlinha[i];
                        j++;
                    } else {
                        if (aux[j - 1] == '!' || aux[j - 1] == '?' || aux[j - 1] == '.' || aux[j - 1] == ',' || aux[j - 1] == ':' || aux[j - 1] == ';') {
                            char[] vetPalavra = new char[j - 1];
                            vetPalavra = CopiaVetor(vetPalavra, aux);
                            palavra = String.copyValueOf(vetPalavra);
                            for (int k = 0; k < Tabela.size(); k++) {
                                if (Tabela.get(k).getCodigo().equals(palavra)) {
                                    escritor.write(Tabela.get(k).getPalavra());
                                    escritor.flush();
                                    escritor.write(aux[j - 1]);
                                    escritor.flush();
                                    escritor.write(" ");
                                }
                            }
                            j = 0;
                            EsvaziaVetor(vetPalavra);
                            EsvaziaVetor(aux);
                        } else {
                            char[] vetPalavra = new char[j];
                            vetPalavra = CopiaVetor(vetPalavra, aux);
                            j = 0;
                            palavra = String.copyValueOf(vetPalavra);
                            for (int k = 0; k < Tabela.size(); k++) {
                                if (Tabela.get(k).getCodigo().equals(palavra)) {
                                    escritor.write(Tabela.get(k).getPalavra());
                                    escritor.flush();
                                    escritor.write(" ");
                                    escritor.flush();
                                }
                            }
                            EsvaziaVetor(vetPalavra);
                            EsvaziaVetor(aux);
                        }
                    }
                }
                escritor.newLine();
                recebelinha = readsaida.readLine();
            }
        } catch (FileNotFoundException e) { //exce��o quando o arquivo não é encontrado
            System.out.println("Arquivo não encontrado!");
        } catch (IOException i) { //exceção que verifica erro na leitura do arquivo
            System.out.println("Erro na leitura do Arquivo!");
        }
    }

}
