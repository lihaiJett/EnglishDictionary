package cn.lihailjt.englishdictionary;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.lihailjt.englishdictionary.dataprovider.MyWord;
import cn.lihailjt.englishdictionary.dataprovider.WordListProvider;
import cn.lihailjt.englishdictionary.userdata.UserData;

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
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recite);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        mediaPlayer = new MediaPlayer();



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wordList = WordListProvider.getExcelData(getIntent().getStringExtra("filePath"));
                    ReciteActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            findViewById(R.id.progressBar).setVisibility(View.GONE);

                            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                            // Set up the ViewPager with the sections adapter.
                            mViewPager = (ViewPager) ReciteActivity.this.findViewById(R.id.container);
                            mViewPager.setAdapter(mSectionsPagerAdapter);

                            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    //保存当前浏览的位置
                                    UserData.get(ReciteActivity.this).setPreference(UserData.CURNUM,position);
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });
                            int cur =  UserData.get(ReciteActivity.this).getInt(UserData.CURNUM,0);
                            if(cur < mSectionsPagerAdapter.getCount()){
                                mViewPager.setCurrentItem(cur);
                            }

                            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                            fab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                                            .setAction("Action", null).show();
                                    if(mViewPager.getCurrentItem()+1 < mViewPager.getAdapter().getCount()){
                                        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                                    }
                                }
                            });
                            mSectionsPagerAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
            fragments = WordViewFragment.newInstance(wordList.get(position));
            fragments.setNextPageController(new WordViewFragment.NextPageController() {
                @Override
                public void toNext(MyWord myWord)   {
//                    if(mViewPager.getCurrentItem()+1 < mViewPager.getAdapter().getCount()){
//                        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
//                    }
                    Uri uri = Uri.parse("http://dict.youdao.com/dictvoice?audio="+myWord.getWord());
                    try {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(ReciteActivity.this,uri);
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
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
            return "SECTION";
        }
    }
}
