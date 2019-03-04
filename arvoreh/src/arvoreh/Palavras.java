package arvoreh;

public class Palavras {

    int tamanho;
    String palavra;
    String codigo;
    char acentuacao;

    public Palavras(int tamanho, String palavra) {
        this.tamanho = tamanho;
        this.palavra = palavra;
    }

    public Palavras(String palavra, String codigo) {
        this.codigo = codigo;
        this.palavra = palavra;
    }
        
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Palavras(String palavra) {
        this.palavra = palavra;
    }
    
    
    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getPalavra() {
        return palavra;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        return ("Tamanho: " + tamanho + " Palavra:  " + palavra);
    }

    public String ImprimirTabela() {
        return ("Palavra: " + palavra + " Código: " + codigo);
    }

}
