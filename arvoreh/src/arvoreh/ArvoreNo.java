package arvoreh;

import java.util.ArrayList;

public class ArvoreNo {

    private int freq;
    private String Frase;
    private ArvoreNo Direita, Esquerda;
    private String bit;
    private int marca;

    public ArvoreNo(int freq) {
        this.freq = freq;
        this.Direita = null;
        this.Esquerda = null;
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
        return Direita;
    }

    public ArvoreNo getEsquerda() {
        return Esquerda;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public void setDireita(ArvoreNo Direita) {
        this.Direita = Direita;
    }

    public void setEsquerda(ArvoreNo Esquerda) {
        this.Esquerda = Esquerda;
    }

}
