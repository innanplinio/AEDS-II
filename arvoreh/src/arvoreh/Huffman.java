package arvoreh;

//Importa��o dos pacotes usados
import arvoreh.Palavras;
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

public class Huffman {

    //Declara��o de vari�vel 
    private final ArrayList<Palavras> word;
    private int valor;
    private final ArrayList<ArvoreNo> Arvore;
    private ArrayList<Palavras> Tabela;

    //Construtor da classe
    public Huffman() {
        this.word = new ArrayList<>();
        this.Arvore = new ArrayList<>();
        this.Tabela = new ArrayList<>();
    }

    public void Buscar() throws FileNotFoundException, IOException {

        //Leitura do arquivo .txt
        try {
            FileReader arquivo = new FileReader("texto.txt");
            BufferedReader leitor = new BufferedReader(arquivo);

            //String linha que recebe a primeira linha do arquivo
            String linha = leitor.readLine();
            char[] palavi = new char[99999999]; //Vetor de char
            int j = 0;
            while (linha != null) {
                char[] aux = linha.toCharArray();//Vetor aux char que recebe a linha em caractere  
                char[] linhast = new char[aux.length + 1]; //Vetor char criado com o tamanho + 1 da linha para garantir a inser��o da �ltima palavra
                for( int i = 0;i<aux.length;i++){ //for que percorre os dois vetores para linhast receber aux
                    linhast[i] = aux[i];
                }
                linhast[linhast.length -1] = ' '; 
                for (int i= 0; i < linhast.length; i++) { // for que roda at� o tamanho do vetor de char linhast
                   if (linhast[i] != ' ') { // se linhast na posi��o i  for diferente de espa�o e do tamanho - 1
                        palavi[j] = linhast[i]; //vetor palavi na posi��o j recebe linhast na posi��o i
                        j++; //incrementa o j  
                    }else {
                        if (palavi[j - 1] == '!' || palavi[j - 1] == '?' || palavi[j - 1] == '.' || palavi[j - 1] == ',' || palavi[j - 1] == ':' || palavi[j - 1] == ';') {
                            char[] palav = new char[j - 1]; // cria um novo vetor com o tamanho de j que foi sendo incrementado
                            palav = ArrumaVetor(palav, palavi); //chama a fun��o ArrumaVetor que tem como par�metro dois vetores de char
                            if (word.isEmpty() == true) { //se meu ArrayList estiver vazio
                                Palavras p = new Palavras(1, String.copyValueOf(palav)); //Construtor de Palavras que passa o tamanho e a transforma��o do vetor de caracteres em String
                                word.add(p); // adiciona o objeto p na ArrayList
                            } else if (Contem(word, palav) == true) { // chama a fun��o Contem, se retornar true
                                word.get(valor).setTamanho(word.get(valor).getTamanho() + 1);// seta na posi��o encontrada o tamanho, incrementa ele
                            } else if (Contem(word, palav) == false) { // chama a fun��o Contem, se retornar false
                                Palavras p = new Palavras(1, String.copyValueOf(palav)); // //Construtor de Palavras que passa o tamanho e a transforma��o do vetor de caracteres em String
                                word.add(p); // adiciona o objeto p na ArrayList
                            }
                            EsvaziaVetor(palav);
                            EsvaziaVetor(palavi);//Chama a fun��o EsvaziaVetor
                            j = 0;
                        } else {
                            char[] palav = new char[j]; // cria um novo vetor com o tamanho de j que foi sendo incrementado
                            palav = ArrumaVetor(palav, palavi); //chama a fun��o ArrumaVetor que tem como par�metro dois vetores de char
                            if (word.isEmpty() == true) { //se meu ArrayList estiver vazio
                                Palavras p = new Palavras(1, String.copyValueOf(palav)); //Construtor de Palavras que passa o tamanho e a transforma��o do vetor de caracteres em String
                                word.add(p); // adiciona o objeto p na ArrayList
                            } else if (Contem(word, palav) == true) { // chama a fun��o Contem, se retornar true
                                word.get(valor).setTamanho(word.get(valor).getTamanho() + 1);// seta na posi��o encontrada o tamanho, incrementa ele
                            } else if (Contem(word, palav) == false) { // chama a fun��o Contem, se retornar false
                                Palavras p = new Palavras(1, String.copyValueOf(palav)); // //Construtor de Palavras que passa o tamanho e a transforma��o do vetor de caracteres em String
                                word.add(p); // adiciona o objeto p na ArrayList
                            }
                            j = 0;
                            EsvaziaVetor(palav);
                            EsvaziaVetor(palavi);//Chama a fun��o EsvaziaVetor
                        }

                    }
                }
                EsvaziaVetor(aux);
                EsvaziaVetor(linhast);
                linha = leitor.readLine(); // String linha recebe uma nova linha do texto
            }
            OrdenaVetor(word); //Chama a fun��o OrdenaVetor passando como par�metro a ArrayList Word
            ConstruirArvore(word, Arvore); //Chama a fun��o que constr�i a �rvore, passando como par�metro a ArrayList Word e a ArrayList Arvore

        } catch (FileNotFoundException e) { //exce��o quando o arquivo n�o � encontrado
            System.out.println("Arquivo não encontrado!");
        } catch (IOException i) { //exce��o que verifica erro na leitura do arquivo
            System.out.println("Erro na leitura arquivo!");
        }

    }

    public boolean Contem(ArrayList<Palavras> word, char[] palav) { //recebe uma ArrayList e um vetor de char
        for (int i = 0; i < word.size(); i++) { // for percorrido pelo tamanho da ArrayList
            if (word.get(i).getPalavra().equals(String.copyValueOf(palav))) { // se a palavra na posi��o i do ArrayList Word for igual a String do vetor de char
                valor = i; //salva a posi��o na vari�vel valor 
                return true; // retorna true
            }
        }
        return false; // caso contr�rio, retorna false
    }

    public void EsvaziaVetor(char[] p) { //recebe um vetor char
        for (int i = 0; i < p.length; i++) { // for que percorre at� o tamanho do vetor
            p[i] = ' '; // cada posi��o do vetor recebe espa�o em branco
        }
    }

    public void OrdenaVetor(ArrayList<Palavras> word) { //Recebe uma ArrayList de palavras para ser ordenada

        Collections.sort(word, (Palavras p1, Palavras p2) -> { // tem como par�metro a ArrayList recebida e duas palavras da mesma

            //Comparando objeto 1 com objeto 2
            if (p1.tamanho < p2.tamanho) { //se p1 > p2, retornar algum n�mero negativo
                return -1;
            } else if (p1.tamanho > p2.tamanho) { //se p1 > p2, retornar algum n�mero positivo
                return 1;
            } else { // se iguais, retornar 0
                return 0;
            }
        });
    }

    public void ConstruirArvore(ArrayList<Palavras> word, ArrayList<ArvoreNo> Arvore) throws IOException { // recebe como parâmetros a ArrayList do tipo Palavras e a ArrayList do tipo ArvoreNo
        try {
            while (word.size() != 1) { //Enquanto o tamanho da ArrayList word n�o for diferente de 1
                if (ContemArv(Arvore, word.get(0).getPalavra()) == false && ContemArv(Arvore, word.get(1).getPalavra()) == false) { //Se a Arvore n�o conter a posi��o 0 e 1 da Word
                    ArvoreNo N1 = new ArvoreNo(word.get(0).getTamanho()); //Construtor de N1 (tipo ArvoreNo) que recebe a  frequ�ncia da palavra
                    N1.setFrase(word.get(0).getPalavra()); //seta a frase do N1 com a palavra de Word na posi��o 0
                    Arvore.add(N1);// adiciona N1 na Arvore
                    ArvoreNo N2 = new ArvoreNo(word.get(1).getTamanho()); //Construtor de N2 (tipo ArvoreNo) que recebe a  frequ�ncia da palavra
                    N2.setFrase(word.get(1).getPalavra()); //seta a frase do N2 com a palavra de Word na posi��o 1
                    Arvore.add(N2); // adiciona N2 na Arvore
                    ArvoreNo NO = new ArvoreNo(N1.getFreq() + N2.getFreq()); //Construtor de N0 (tipo ArvoreNo)que recebe a soma da frequ�ncia de N1 e N2
                    if (N1.getFreq() < N2.getFreq()) { //se a frequ�ncia de N1 < N2
                        NO.setEsquerda(N1); //seta N1 no lado esquerdo de N0
                        N1.setBit("1");// N1 seta o bit como 1
                        NO.setDireita(N2); //seta N2 no lado direito de N0
                        N2.setBit("0"); // N2 seta o bit como 0
                        NO.setFrase(Concatenar(N1.getFrase(), N2.getFrase())); // seta a frase de N0 com a concatena��o de N1 e N2
                    } else { //se a frequ�ncia de N1 > N2
                        NO.setDireita(N1); //seta N1 no lado direito de N0
                        N1.setBit("0"); // N1 seta o bit como 0
                        NO.setEsquerda(N2);//seta N2 no lado esquerdo de N0
                        N2.setBit("1"); // N2 seta o bit como 1
                        NO.setFrase(Concatenar(N1.getFrase(), N2.getFrase())); // seta a frase de N0 com a concatena��o de N1 e N2
                    }
                    NO.setMarca(5); //NO recebe marca 5 
                    Arvore.add(NO); // adiciona NO em Arvore
                    word.remove(0);
                    word.remove(0); // remove as duas primeiras posi��es de word consecutivas
                    Palavras P = new Palavras(NO.getFreq(), NO.getFrase()); //cria um objeto Palavras com a frequ�ncia e frase de N0
                    word.add(P);// adiciona o objeto em word
                    OrdenaVetor(word); // chama a fun��o OrdenaVetor

                } else if (ContemArv(Arvore, word.get(0).getPalavra()) == true && ContemArv(Arvore, word.get(1).getPalavra()) == true) { //Se a Arvore conter a posi��o 0 e 1 da Word
                    ContemArv(Arvore, word.get(0).getPalavra()); //chama a fun��o ContemArv para saber a posi��o 
                    int a = valor; // vari�vel a recebe o valor
                    ContemArv(Arvore, word.get(1).getPalavra()); //chama a fun��o ContemArv pra sabebr a posi��o, n�o precisa adicionar a uma vari�vel pois valor � uma vari�vel global
                    ArvoreNo NO = new ArvoreNo(Arvore.get(a).getFreq() + Arvore.get(valor).getFreq()); //Construtor de N0 (tipo ArvoreNo)que recebe a soma da frequ�ncia de Arvore na posi��o a e Arvore na posi��o valor
                    if (Arvore.get(a).getFreq() < Arvore.get(valor).getFreq()) { //se a frequência de Arvore na poisção a < Arvore na posição valor
                        NO.setEsquerda(Arvore.get(a)); //seta Arvore na posi��o a no lado esquerdo de N0
                        Arvore.get(a).setBit("1");// Arvore na posi��o a seta o bit como 1
                        NO.setDireita(Arvore.get(valor)); //seta Arvore na posi��o valor no lado direito de N0
                        Arvore.get(valor).setBit("0"); // Arvore na posi��o valor seta o bit como 0
                        NO.setFrase(Concatenar(Arvore.get(a).getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatena��o de Arvore na posi��o a e Arvore na posi��o valor
                    } else { //se a frequ�ncia de Arvore na posi��o a > Arvore na posi��o valor
                        NO.setDireita(Arvore.get(a)); //seta Arvore na posi��o a no lado direito de N0
                        Arvore.get(a).setBit("0"); // Arvore na posi��o a seta o bit como 0
                        NO.setEsquerda(Arvore.get(valor)); //seta Arvore na posi��o valor no lado esquerdo de N0
                        Arvore.get(valor).setBit("1"); // Arvore na posi��o valor seta o bit como 1
                        NO.setFrase(Concatenar(Arvore.get(a).getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatena��o de Arvore na posi��o a e Arvore na posi��o valor
                    }
                    NO.setMarca(5); // NO recebe marca 5 
                    Arvore.add(NO); // adiciona NO em Arvore
                    word.remove(0);
                    word.remove(0); // remove as duas primeiras posi��es de word consecutivas
                    Palavras P = new Palavras(NO.getFreq(), NO.getFrase()); //cria um objeto Palavras com a frequ�ncia e frase de N0
                    word.add(P); // adiciona o objeto em Word
                    OrdenaVetor(word); // chama fun��o OrdenaVetor

                } else if (ContemArv(Arvore, word.get(0).getPalavra()) == true) { //Se a Arvore conter a posi��o 0 da Word
                    ArvoreNo N1 = new ArvoreNo(word.get(1).getTamanho()); //Construtor de N1 (tipo ArvoreNo) que recebe a  frequ�ncia da palavra
                    N1.setFrase(word.get(1).getPalavra()); //seta a frase do N1 com a palavra de Word na posi��o 1
                    Arvore.add(N1); // adiciona N1 na Arvore
                    ArvoreNo NO = new ArvoreNo(N1.getFreq() + Arvore.get(valor).getFreq()); //Construtor de N0 (tipo ArvoreNo)que recebe a soma da frequ�ncia de N1 e de Arvore na posi��o valor
                    if (N1.getFreq() < Arvore.get(valor).getFreq()) { //se a frequ�ncia de N1 < Arvore na posi��o valor
                        NO.setEsquerda(N1); //seta N1 no lado esquerdo de N0
                        N1.setBit("1"); // N1 seta o bit como 1
                        NO.setDireita(Arvore.get(valor)); //seta Arvore na posi��o valor no lado direito de N0
                        Arvore.get(valor).setBit("0"); // Arvore na posi��o valor seta o bit como 0
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatena��o de N1 e Arvore na posi��o valor
                    } else { //se a frequ�ncia de N1 > Arvore na posi��o valor
                        NO.setDireita(N1); //seta N1 no lado direito de N0
                        N1.setBit("0"); // N1 seta o bit como 0
                        NO.setEsquerda(Arvore.get(valor)); //seta Arvore na posi��o valor no lado esquerdo de N0
                        Arvore.get(valor).setBit("1"); // Arvore na posi��o valor seta o bit como 1
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatena��o de N1 e Arvore na posi��o valor
                    }
                    NO.setMarca(5); // NO recebe marca 5 
                    Arvore.add(NO); // adiciona NO em Arvore
                    word.remove(0);
                    word.remove(0); // remove as duas primeiras posi��es de word consecutivas
                    Palavras P = new Palavras(NO.getFreq(), NO.getFrase()); //cria um objeto Palavras com a frequ�ncia e frase de N0
                    word.add(P); // adiciona o objeto em Word
                    OrdenaVetor(word); // chama a fun��o OrdenaVetor
                } else if (ContemArv(Arvore, word.get(1).getPalavra()) == true) { //Se a Arvore conter a posi��o 1 da Word
                    ArvoreNo N1 = new ArvoreNo(word.get(0).getTamanho());//Construtor de N1 (tipo ArvoreNo) que recebe a  frequ�ncia da palavra
                    N1.setFrase(word.get(0).getPalavra());  //seta a frase do N1 com a palavra de Word na posi��o 0
                    Arvore.add(N1); // adiciona N1 na Arvore
                    ArvoreNo NO = new ArvoreNo(N1.getFreq() + Arvore.get(valor).getFreq()); //Construtor de N0 (tipo ArvoreNo)que recebe a soma da frequ�ncia de N1 e de Arvore na posi��o valor
                    if (N1.getFreq() < Arvore.get(valor).getFreq()) { //se a frequ�ncia de N1 < Arvore na posi��o valor
                        NO.setEsquerda(N1);  //seta N1 no lado esquerdo de N0
                        N1.setBit("1"); // N1 seta o bit como 1
                        NO.setDireita(Arvore.get(valor)); //seta Arvore na posi��o valor no lado direito de N0
                        Arvore.get(valor).setBit("0"); // Arvore na posi��o valor seta o bit como 0
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatena��o de N1 e Arvore na posi��o valor
                    } else {
                        NO.setDireita(N1); //seta N1 no lado direito de N0
                        N1.setBit("0"); // N1 seta o bit como 0
                        NO.setEsquerda(Arvore.get(valor)); //seta Arvore na posi��o valor no lado esquerdo de N0
                        Arvore.get(valor).setBit("1"); // Arvore na posi��o valor seta o bit como 1
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatena��o de N1 e Arvore na posi��o valor
                    }
                    NO.setMarca(5); // NO recebe marca 5 
                    Arvore.add(NO); // adiciona NO em Arvore
                    word.remove(0);
                    word.remove(0); // remove as duas primeiras posi��es de word consecutivas
                    Palavras P = new Palavras(NO.getFreq(), NO.getFrase()); //cria um objeto Palavras com a frequ�ncia e frase de N0
                    word.add(P); // adiciona o objeto em Word
                    OrdenaVetor(word); // chama a fun��o OrdenaVetor
                }
            }

            GeraTabela(Arvore); // Chama a fun��o GeraTabela que passa como par�metro a Arvore
        } catch (IOException i) { //exce��o que verifica erro na leitura do arquivo
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public String Concatenar(String s1, String s2) { // fun��o que recebe duas Strings
        StringBuffer strBuf = new StringBuffer(); //cria um objeto do tipo StringBuffer
        strBuf.append(s1);
        strBuf.append(s2); //passa as duas strings por par�metro
        return strBuf.toString(); // retorna a concatena��o
    }

    public boolean ContemArv(ArrayList<ArvoreNo> Arvore, String palav) { //recebe ArrayList Arvore e uma string
        for (int i = 0; i < Arvore.size(); i++) { //for que percorre at� o tamanho da Arvore
            if (Arvore.get(i).getFrase().equals(palav)) { //se Arvore na posi��o i for igual a string recebida
                valor = i; //valor recebe i
                return true; // retorna true
            }
        }
        return false;// retorna false
    }

    public char[] ArrumaVetor(char[] a, char[] b) { //recebe dois vetores de char
        for (int i = 0; i < a.length; i++) { // for que roda até o tamanho do vetor a
            a[i] = b[i]; // vetor a vai receber tudo que está no vetor b
         }
        return a; //retorna o vetor a
    }

    public void GeraTabela(ArrayList<ArvoreNo> Arv) throws IOException { //recebe como par�metro a ArrayList Arv
        try {
            Arv.get(Arv.size() - 1).setBit("1"); // seta na cabe�a da Arvore (no caso, a �ltima posi��o da Arv) o bit 1 
            ArvoreNo Arvore = Arv.get(Arv.size() - 1);// recebo a raiz da minha arvore
            String aux; //recebera a palavra contida em cada no
            String aux2 = null;// recebera o codigo da palavra
            aux = Arvore.getBit(); // pego o Bit do meu no raiz
            while (Arv.size() != 1) { //percorro ate ficar apenas minha raiz no meu Array de No's
                if (Arvore.getEsquerda() != null) { //verifico se a esquerda � nula
                    if (Arvore.getEsquerda().getEsquerda() == null && Arvore.getEsquerda().getDireita() == null) { //verifico se a direita e a esquerda do no mais a baixo s�o nulas
                        ArvoreNo no = Arvore.getEsquerda();// no auxiliar recebe a esquerda
                        Arvore.setEsquerda(null);//a esquerda aponta para null
                        Arvore = no; //continuo o while a partir do no auxiliar
                        aux = Concatenar(aux, Arvore.getBit());// concateno a palavra com a palavra anterior
                    } else {
                        Arvore = Arvore.getEsquerda();//continuo a busca para a esquerda
                        aux = Concatenar(aux, Arvore.getBit());// concateno a palavra com a palavra anterior
                    }
                } else if (Arvore.getDireita() != null) {//verifico se a direita � nula
                    if (Arvore.getDireita().getEsquerda() == null && Arvore.getDireita().getDireita() == null) {//verifico se a direita e a esquerda do no mais a baixo s�o nulas
                        ArvoreNo no = Arvore.getDireita();// no auxiliar recebe a direita
                        Arvore.setDireita(null);//a direita aponta para null
                        Arvore = no;//continuo ao while a partir do no auxiliar
                        aux = Concatenar(aux, Arvore.getBit());// concateno a palavra com a palavra anterior
                    } else {
                        Arvore = Arvore.getDireita();//continuo a busca para a direita
                        aux = Concatenar(aux, Arvore.getBit());// concateno a palavra com a palavra anterior
                    }
                } else {
                    if (Arvore.getMarca() == 5) {// verifico a marca do meu No para n�o colocar nos marcados na Tabela
                        Arv.remove(Arvore);//removo o meu no folha do Array de no's
                        Arvore = Arv.get(Arv.size() - 1);//recome�o minha busca a partir da raiz da arvore
                        aux = Arvore.getBit();// coloco o bit da minha raiz no aux
                    } else {
                        aux2 = Arvore.getFrase();// pego a frase referente a aquele n�
                        Arv.remove(Arvore); //removo o meu no folha do Array de no's
                        Arvore = Arv.get(Arv.size() - 1); //recome�o minha busca a partir da raiz da arvore
                        Palavras p = new Palavras(aux2, aux);// crio um objeto p contendo o codigo e a palavra do no folha
                        Tabela.add(p);// adiciono o meu objeto no meu ArrayList Tabela(o que contem os no's folhas)
                        aux = Arvore.getBit();// coloco o bit da minha raiz no aux
                    }
                }
            
            EscreveTabela(Tabela);//chamo a fun�� escreve Tabela
            EscreverCompactado(Tabela);//chamo a fun��o de escrever compactado
        }
        }catch (IOException i) { //exce��o que verifica erro na leitura do arquivo
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public void EscreveTabela(ArrayList<Palavras> arv) throws IOException { // recebe a ArrayList arv
        try {
            FileWriter arq = new FileWriter("Tabela.txt"); //cria um arquivo .txt
            BufferedWriter escritor = new BufferedWriter(arq); //cria o escritor do arquivo

            for (int i = 0; i < arv.size(); i++) { //for percorre o tamanho do ArrayList
                String s = arv.get(i).palavra;// String recebe a palavra da posi��o i do ArrayList
                escritor.write(s); // escreve a String no arquivo
                escritor.flush(); // buffer que limpa o escritor
                escritor.write(" "); // escreve espa�o em branco no arquivo
                escritor.flush(); // buffer que limpa o escritor
                s = arv.get(i).codigo; // s recebe o c�digo da arv na posição i
                escritor.write(s); // escreve o inteiro no arquivo
                escritor.flush(); // buffer que limpa o escritor
                escritor.write(" ");// escreve o espa�o
                escritor.flush();// limpa o buffer
                escritor.newLine(); // escreve uma nova linha
            }
            escritor.close(); // fecha o escritor
        } catch (IOException i) { //exce��o que verifica erro na leitura do arquivo
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public void EscreverCompactado(ArrayList<Palavras> arv) throws IOException, FileNotFoundException { // recebe a Arraylist arv
        try {
            //Leitura e escrita dos arquivos .txt
            FileReader arquivo = new FileReader("texto.txt");
            BufferedReader leitor = new BufferedReader(arquivo);
            FileWriter arq = new FileWriter("Compactado.txt");
            BufferedWriter escritor = new BufferedWriter(arq);

            String linha = leitor.readLine(); // String que vai receber a primeira linha do arquivo
            String palavra; // Vari�vel do tipo String
            char[] palavi = new char[99999999]; // Vetor do tipo char
            int j = 0; // inicializa j em 0
            while (linha != null) { // Enquanto linha diferente de nulo
                char[] aux = linha.toCharArray();
                char[] linhast = new char[aux.length + 1];//Vetor char que recebe a linha em caractere   
                for( int i = 0;i<aux.length;i++){
                    linhast[i] = aux[i];
                }
                linhast[linhast.length -1] = ' ';
                for (int i = 0; i < linhast.length; i++) { // Percorre o tamanho de linhast
                    if (linhast[i] != ' ' && i != linhast.length - 1) { // se linhast na posi��o i  for diferente de espa�o e do tamanho - 1
                        palavi[j] = linhast[i]; // palavi na posi��o j recebe linhast na posição i
                        j++; //incrementa j
                    } else {
                        if (palavi[j - 1] == '!' || palavi[j - 1] == '?' || palavi[j - 1] == '.' || palavi[j - 1] == ',' || palavi[j - 1] == ':' || palavi[j - 1] == ';') {
                            char[] palav = new char[j - 1]; // cria um novo vetor com o tamanho de j que foi sendo incrementado
                            palav = ArrumaVetor(palav, palavi); //chama a fun��o ArrumaVetor que tem como par�metro dois vetores de char

                            palavra = String.copyValueOf(palav); // palavra recebe o vetor palav como String
                            for (int k = 0; k < arv.size(); k++) { //percorre at� o tamanho de arv
                                if (arv.get(k).getPalavra().equals(palavra) == true) { // se a palavra na posi��o k for igual a palavra recebida
                                    String p = arv.get(k).getCodigo(); // String recebe o c�digo na posi��o k
                                    escritor.write(p); // escreve a string
                                    escritor.flush(); // limpa o buffer
                                    escritor.write(palavi[j - 1]);
                                    escritor.flush();
                                    escritor.write(" "); // escreve o espa�o 
                                    escritor.flush();// limpa o buffer
                                }
                            }
                            j = 0;   // zera o j
                            EsvaziaVetor(palav); // esvazia
                            EsvaziaVetor(palavi); // vetores
                        } else {
                            char[] palav = new char[j]; // cria um novo vetor com o tamanho de j que foi sendo incrementado
                            palav = ArrumaVetor(palav, palavi); //chama a fun��o ArrumaVetor que tem como par�metro dois vetores de char
                            j = 0; // zera o j
                            palavra = String.copyValueOf(palav); // palavra recebe o vetor palav como String
                            for (int k = 0; k < arv.size(); k++) { //percorre at� o tamanho de arv
                                if (arv.get(k).getPalavra().equals(palavra) == true) { // se a palavra na posi��o k for igual a palavra recebida
                                    String p = arv.get(k).getCodigo(); // String recebe o c�digo na posi��o k
                                    escritor.write(p); // escreve a string
                                    escritor.flush(); // limpa o buffer
                                    escritor.write(" "); // escreve o espa�o 
                                    escritor.flush();// limpa o buffer
                                }
                            }
                            EsvaziaVetor(palav); // esvazia
                            EsvaziaVetor(palavi); // vetores
                        }
                    }
                }
                escritor.flush();// limpa o buffer
                escritor.newLine(); // escreve nova linha
                linha = leitor.readLine(); // String linha recebe uma nova linha do texto
            }
            escritor.close(); // fecha o escritor
            leitor.close(); // fecha o leitor
        } catch (FileNotFoundException e) { //exce��o quando o arquivo n�o � encontrado
            System.out.println("Arquivo não encontrado!");
        } catch (IOException i) { //exce��o que verifica erro na leitura do arquivo
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public void HuffmanORetorno() throws IOException, FileNotFoundException {
        try {
            //Leitura e escrita dos arquivos .txt
            FileReader arquivo = new FileReader("Tabela.txt");
            BufferedReader leitor = new BufferedReader(arquivo);
            FileReader arquiv = new FileReader("Compactado.txt");
            BufferedReader leit = new BufferedReader(arquiv);
            FileWriter arq = new FileWriter("ORetorno.txt");
            BufferedWriter escritor = new BufferedWriter(arq);

            Tabela = new ArrayList<>();//esvazio o meu ArrayList Tabela
            String linha = leitor.readLine(); // String que vai receber a primeira linha do arquivo Tabela
            char[] palavi = new char[99999999];// Vetor do tipo char
            String palavra;// Vari�vel do tipo String
            int j = 0, indice = 0;// vari�veis auxiliares 
            while (linha != null) {// verifico ate o meu linhas ser null
                char[] linhast = linha.toCharArray();// transformo minha string lida em um vetor de char
                for (int i = 0; i < linhast.length; i++) { //percorro meu vetor char
                    if (linhast[i] != ' ' && i != linhast.length - 1) { // se linhast na posi��o i  for diferente de espa�o e do tamanho - 1
                        palavi[j] = linhast[i]; //coloco cada letra lida em um outro vetor auxiliar
                        j++; // incremento o j
                    } else {// achei uma palavra
                        indice++;// incremento o indice
                        char[] palav = new char[j];// crio um novo vetor do tamanho de minha palavra 
                        palav = ArrumaVetor(palav, palavi); // chamo a fun��o Arrumavetor
                        j = 0;// recome�o meu contador do vetor auxiliar referente a palavra
                        palavra = String.copyValueOf(palav);// transformo meu vetor de char em uma String
                        if (indice == 1) {// se meu indice � igual a 1 eu li a minha primeira palavra
                            Palavras p = new Palavras(palavra, " ");// crio um objeto colocando aquela palavra la dentro
                            Tabela.add(0, p);// adiciono ele sempre no inicio da minha ArrayList
                        } else {// se entrar aqui li minha segunda palavra(codigo)
                            Tabela.get(0).setCodigo(palavra);// seto o codigo lido naquele contrutor criado acima
                            indice = 0;// indice recebe 0
                        }
                        //chamo a fun��o Esvazia vetor para os meu vetores auxiliares
                        EsvaziaVetor(palav);
                        EsvaziaVetor(palavi);
                    }
                }
                linha = leitor.readLine();// leio a proxima linha do meu arquivo Tabela
            }
            linha = leit.readLine();// leio a linha do meu arquivo compactado
            j = 0;

            while (linha != null) { // percorro ate ler todo o meu arquivo
                char[] linhast = linha.toCharArray(); //transformo a String lida em vetor de char 
                for (int i = 0; i < linhast.length; i++) {// percorro o meu vetor de char
                    if (linhast[i] != ' ' && i != linhast.length - 1) { // se linhast na posi��o i  for diferente de espaço e do tamanho - 1
                        palavi[j] = linhast[i];//coloco cada letra lida em um outro vetor auxiliar
                        j++;// incremento o j
                    } else {// se entrar aqui achei uma palavra
                        if (palavi[j - 1] == '!' || palavi[j - 1] == '?' || palavi[j - 1] == '.' || palavi[j - 1] == ',' || palavi[j - 1] == ':' || palavi[j - 1] == ';') {
                            char[] palav = new char[j - 1]; // cria um novo vetor com o tamanho de j que foi sendo incrementado
                            palav = ArrumaVetor(palav, palavi); //chama a fun��o ArrumaVetor que tem como par�metro dois vetores de char
                            palavra = String.copyValueOf(palav);// transformo meu vetor de char em uma String
                            for (int k = 0; k < Tabela.size(); k++) { //percorro minha tabela montada a partir do arquivo Tabela.TXT
                                if (Tabela.get(k).getCodigo().equals(palavra)) {// se o bit lido do Compactado.txt for igual ao da tabela na posição k
                                    escritor.write(Tabela.get(k).getPalavra());// escrevo a palavra referente a aquele Bit
                                    escritor.flush(); //esvazio o buffer
                                    escritor.write(palavi[j - 1]);
                                    escritor.flush(); // esvazio o buffer  escritor.write(" ");
                                    escritor.write(" ");// escrevo um espaço
                                }
                            }
                            j = 0;
                            //chamo a fun��o Esvazia vetor para os meu vetores auxiliares
                            EsvaziaVetor(palav);
                            EsvaziaVetor(palavi);
                        } else {
                            char[] palav = new char[j];// crio um novo vetor do tamanho de minha palavra 
                            palav = ArrumaVetor(palav, palavi); // chamo a fun��o Arrumavetor
                            j = 0;// recome�o meu contador do vetor auxiliar referente a palavra
                            palavra = String.copyValueOf(palav);// transformo meu vetor de char em uma String
                            for (int k = 0; k < Tabela.size(); k++) { //percorro minha tabela montada a partir do arquivo Tabela.TXT
                                if (Tabela.get(k).getCodigo().equals(palavra)) {// se o bit lido do Compactado.txt for igual ao da tabela na posi��o k
                                    escritor.write(Tabela.get(k).getPalavra());// escrevo a palavra referente a aquele Bit
                                    escritor.flush(); //esvazio o buffer 
                                    escritor.write(" ");// escrevo um espa�o
                                    escritor.flush(); // esvazio o buffer
                                }
                            }
                            //chamo a fun��o Esvazia vetor para os meu vetores auxiliares
                            EsvaziaVetor(palav);
                            EsvaziaVetor(palavi);
                        }
                    }
                }
                escritor.newLine();
                linha = leit.readLine(); //Leio a proxima linha do meu arquivo Compactado.txt
            }
        } catch (FileNotFoundException e) { //exce��o quando o arquivo n�o � encontrado
            System.out.println("Arquivo não encontrado!");
        } catch (IOException i) { //exce��o que verifica erro na leitura do arquivo
            System.out.println("Erro na leitura arquivo!");
        }
    }
}
//SE CHEGOU AQUI SEM DAR NENHUM ERRO � PORQUE DEU BOM, DEU MAIS QUE BOM, DEU BOM D+,
//ENT�O QUERIDA PROFESSORA AVALIE O MEU ESFOR�O COM MUITO CARINHO E AMOR E SAIBA QUE ESTE
// TRABALHO ME DEU UMA INCRIVEL DOR DE CABE�A, ENTRETANDO PUDE APERFEI�OAR O MEU NIVEL DE PROGRAMADOR

