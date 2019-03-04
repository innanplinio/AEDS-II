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
import java.util.ArrayList;

public class ArvoreNo {

    private int freq;
    private String Frase;
    private ArvoreNo Dir, Esq;
    private String bit;
    private int marca;

    public ArvoreNo(int freq) {
        this.freq = freq;
        this.Dir = null;
        this.Esq = null;
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }    
    
    public String getBit() {
        return bit;
    }

    public void setBit(String bit) {
        this.bit = bit;
    }

    public int getFreq() {
        return freq;
    }

    public void setFrase(String Frase) {
        this.Frase = Frase;
    }

    public String getFrase() {
        return Frase;
    }

    public ArvoreNo getDireita() {
        return Dir;
    }

    public ArvoreNo getEsquerda() {
        return Esq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public void setDireita(ArvoreNo Direita) {
        this.Dir = Direita;
    }

    public void setEsquerda(ArvoreNo Esquerda) {
        this.Esq = Esquerda;
    }
    
}
