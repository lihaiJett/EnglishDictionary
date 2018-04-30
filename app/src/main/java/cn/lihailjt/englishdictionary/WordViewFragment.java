package cn.lihailjt.englishdictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.lihailjt.englishdictionary.dataprovider.MyWord;

/**
 * @author <a href="bbmgt@163.com">lihai</a>
 * @version 1.0.0
 *          Created by lihai on 2018/4/26.
 */

public class WordViewFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    interface NextPageController {
        void toNext(MyWord myWord);
    }

    Button btn;
    MyWord myWord;
    List<Pair<String, String>> fillPairs = new ArrayList<>();
    int stage;
    NextPageController mNextPageController;

    public void setNextPageController(NextPageController nextPageController) {
        mNextPageController = nextPageController;
    }

    public WordViewFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WordViewFragment newInstance(MyWord myWord) {
        WordViewFragment fragment = new WordViewFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
        fragment.setWord(myWord);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recite, container, false);

        ViewGroup textLayout = (ViewGroup) rootView.findViewById(R.id.textlayout);
        for(Pair<String,String> p: fillPairs){
            View v = inflater.inflate(R.layout.recite_item,textLayout,false);
            ((TextView) v.findViewById(R.id.key)).setText(p.first);
            ((TextView) v.findViewById(R.id.value)).setText(p.second);
            textLayout.addView(v);
        }
        ((TextView) rootView.findViewById(R.id.num)).setText(stage+"");

        btn = (Button) rootView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        //word.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        return rootView;
    }

    public void setWord(MyWord myWord) {
        this.myWord = myWord;
        fillPairs.add(new Pair<>("单词", myWord.getWord()));
        fillPairs.add(new Pair<>("英标", myWord.getPhonetic_symbol()));
        fillPairs.add(new Pair<>("释义", myWord.getMean()));
        fillPairs.add(new Pair<>("词组", myWord.getPhrase()));
        fillPairs.add(new Pair<>("派生词", myWord.getDerivative()));
        fillPairs.add(new Pair<>("考频", myWord.getFrequency()));
        fillPairs.add(new Pair<>("例句", myWord.getSentence()));
        stage = myWord.getNum();
    }

    public void next() {
        mNextPageController.toNext(myWord);
//        switch (stage) {
//            case 0:
//                mean.setVisibility(View.VISIBLE);
//                break;
//            case 1:
//                note.setVisibility(View.VISIBLE);
//                break;
//            default:
//                if (mNextPageController != null) {
//                    mNextPageController.toNext();
//                }
//                break;
//        }
//        stage++;
    }

}
