package cn.lihailjt.englishdictionary.dataprovider;

/**
 * Created by Administrator on 2018/4/22 0022.
 */

public class MyWord {

    private int num;
    private String word;
    private String phonetic_symbol;
    private String mean;
    private String phrase;
    private String derivative;
    private String frequency;
    private String sentence;
    private String note;

    public void setNum(int num){
        this.num = num;
    }
    public int getNum(){
        return num;
    }

    public void setWord(String word) {
        this.word = word;
    }
    public String getWord() {
        return word;
    }

    public void setPhonetic_symbol(String phonetic_symbol) {
        this.phonetic_symbol = phonetic_symbol;
    }
    public String getPhonetic_symbol() {
        return phonetic_symbol;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
    public String getMean() {
        return mean;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
    public String getPhrase() {
        return phrase;
    }

    public void setDerivative(String derivative) {
        this.derivative = derivative;
    }
    public String getDerivative() {
        return derivative;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public String getFrequency() {
        return frequency;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
    public String getSentence() {
        return sentence;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String getNote() {
        return note;
    }
}
