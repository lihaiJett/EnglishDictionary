package cn.lihailjt.englishdictionary;

/**
 * Created by Administrator on 2018/4/22 0022.
 */

public class MyWord {
        private int num;
        private String word;
        private String mean;
        private String note;

    public MyWord() {

    }
    public MyWord( String word, String mean, String note) {
        this.word = word;
        this.mean = mean;
        this.note = note;
    }
    public void setMyWord( String word, String mean, String note) {
        this.word = word;
        this.mean = mean;
        this.note = note;
    }
    public void setNum(int num) {
            this.num = num;
        }
        public int getNum() {
            return num;
        }

        public void setWord(String word) {
            this.word = word;
        }
        public String getWord() {
            return word;
        }

        public void setMean(String mean) {
            this.mean = mean;
        }
        public String getMean() {
            return mean;
        }

        public void setNote(String note) {
            this.note = note;
        }
        public String getNote() {
            return note;
        }
}
