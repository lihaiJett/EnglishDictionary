package cn.lihailjt.englishdictionary;

import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * @author <a href="bbmgt@163.com">lihai</a>
 * @version 1.0.0
 *          Created by lihai on 2018/5/15.
 */

public abstract class AnalyticsActivity extends AppCompatActivity {
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
