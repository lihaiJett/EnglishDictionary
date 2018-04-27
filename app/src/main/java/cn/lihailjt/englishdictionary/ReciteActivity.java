package cn.lihailjt.englishdictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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





        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getExcelData(getIntent().getStringExtra("filePath"));
                    ReciteActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                            // Set up the ViewPager with the sections adapter.
                            mViewPager = (ViewPager) ReciteActivity.this.findViewById(R.id.container);
                            mViewPager.setAdapter(mSectionsPagerAdapter);

//                            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                                @Override
//                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                                }
//
//                                @Override
//                                public void onPageSelected(int position) {
//                                    ProPreference pr = ProPreference.getProPreference(ReciteActivity.this,"UserData");
//                                    pr.setPreference("CurNum", position);
//                                }
//
//                                @Override
//                                public void onPageScrollStateChanged(int state) {
//
//                                }
//                            });
//                            ProPreference pr = ProPreference.getProPreference(ReciteActivity.this,"UserData");
//                            int cur = pr.getInt("CurNum", 0);
//                            if(cur < mSectionsPagerAdapter.getCount()){
//                                mViewPager.setCurrentItem(cur);
//                            }

//                            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//                            fab.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                                            .setAction("Action", null).show();
//                                }
//                            });
//                            mSectionsPagerAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public void getExcelData(String filePath) throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
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
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //WordViewFragment[] fragments = new WordViewFragment[5];

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            WordViewFragment fragments;
            fragments = WordViewFragment.newInstance(1);
            fragments.setNextPageController(new WordViewFragment.NextPageController() {
                @Override
                public void toNext() {
                    if(mViewPager.getCurrentItem()+1 < mViewPager.getAdapter().getCount()){
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                    }
                }
            });
            fragments.setWord(wordList.get(position));
//            WordViewFragment p = WordViewFragment.newInstance(1);
//            Log.e("!!!!!!!!",position+"!");
//            p.setWord(wordList.get(position));
            return fragments;


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
