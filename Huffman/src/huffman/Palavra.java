/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

/**
 *
 * @author sarto
 */
public class Palavra {
    
    int qtd;
    String palavra;
    String codigo;
    char caracterEspecial;
    

    public Palavra(int qtd, String palavra) {
        this.qtd =qtd;
        this.palavra = palavra;
    }

    public Palavra(String palavra, String codigo) {
        this.codigo = codigo;
        this.palavra = palavra;
    }
        
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Palavra(String palavra) {
        this.palavra = palavra;
    }
    
    
    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getPalavra() {
        return palavra;
    }

    public int getQuantidade() {
        return qtd;
    }

    public void setQuantidade(int quantidade) {
        this.qtd= quantidade;
    }

    @Override
    public String toString() {
        return ("Quantidade: " + qtd + " Palavra:  " + palavra);
    }
    
    // Tabela para construção da arvore Huffman
    public String ImprimirTabela() {
        return ("Palavra: " + palavra + " codigo: " + codigo);
    }

    
    
}
