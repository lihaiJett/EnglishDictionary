package cn.lihailjt.englishdictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private static final String ARG_SECTION_NUMBER = "section_number";

    interface NextPageController {
        void toNext();
    }

    TextView word;
    TextView mean;
    TextView note;
    TextView num;
    Button btn;
    MyWord myWord;
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
    public static WordViewFragment newInstance(int sectionNumber) {
        WordViewFragment fragment = new WordViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recite, container, false);

        word = (TextView) rootView.findViewById(R.id.word);
        mean = (TextView) rootView.findViewById(R.id.mean);
        note = (TextView) rootView.findViewById(R.id.note);
        num = (TextView) rootView.findViewById(R.id.num);
        btn = (Button) rootView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        word.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        setWord(myWord);
        return rootView;
    }

    public void setWord(MyWord myWord) {
        this.myWord = myWord;
        if (word != null && myWord != null) {
            num.setText(String.valueOf(myWord.getNum()));
            word.setText(String.valueOf(myWord.getWord()));
            mean.setText(String.valueOf(myWord.getMean()));
            note.setText(String.valueOf(myWord.getNote()));

            mean.setVisibility(View.INVISIBLE);
            note.setVisibility(View.INVISIBLE);
            stage = 0;
        }
    }

    public void next() {
        switch (stage) {
            case 0:
                mean.setVisibility(View.VISIBLE);
                break;
            case 1:
                note.setVisibility(View.VISIBLE);
                break;
            default:
                if (mNextPageController != null) {
                    mNextPageController.toNext();
                }
                break;
        }
        stage++;
    }

}
