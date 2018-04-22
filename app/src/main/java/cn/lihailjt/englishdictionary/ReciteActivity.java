package cn.lihailjt.englishdictionary;

import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ReciteActivity extends AppCompatActivity {

    private List<MyWord> wordList = new ArrayList<>();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recite);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    showExcel();
                    ReciteActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Create the adapter that will return a fragment for each of the three
                            // primary sections of the activity.
                            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                            // Set up the ViewPager with the sections adapter.
                            mViewPager = (ViewPager) findViewById(R.id.container);
                            mViewPager.setAdapter(mSectionsPagerAdapter);
//                            mSectionsPagerAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public void showExcel() throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(Environment.getExternalStorageDirectory()+"/Dictionary.xls")));
        HSSFSheet sheet = null;
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
            sheet = workbook.getSheetAt(i);
            for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
                HSSFRow row = sheet.getRow(j);
                if (row != null) {
                    MyWord myWord = new MyWord();
                    for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
                        if (row.getCell(k) != null) { // getCell 获取单元格数据
                            System.out.print("{"+k+"}"+row.getCell(k) + "\t");
                            switch (k){
                                case 0:
                                    myWord.setWord(row.getCell(k).toString());
                                case 1:
                                    myWord.setMean(row.getCell(k).toString());
                                case 2:
                                    myWord.setNote(row.getCell(k).toString());
                            }
                        } else {
                            System.out.print("\t");
                        }
                    }
                    wordList.add(myWord);
                    myWord.setNum(wordList.size());
                }
                System.out.println(""); // 读完一行后换行
            }
            System.out.println("读取sheet表：" + workbook.getSheetName(i) + " 完成");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        TextView word;
        TextView mean;
        TextView note;
        TextView num;
        MyWord myWord;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
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
            word.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            setWord(myWord);
            return rootView;
        }
        public void setWord(MyWord myWord){
            this.myWord = myWord;
            if(word!=null) {
                num.setText(myWord.getNum());
                word.setText(myWord.getWord());
                mean.setText(myWord.getMean());
                note.setText(myWord.getNote());
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        PlaceholderFragment[] fragments = new PlaceholderFragment[5];

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(fragments[position%5]==null) {
                fragments[position % 5] = PlaceholderFragment.newInstance(1);
            }
            fragments[position%5].setWord(wordList.get(position));
            return fragments[position%5];
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return wordList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION";
                case 1:
                    return "SECTION";
                case 2:
                    return "SECTION";
            }
            return "SECTION";
        }
    }
}