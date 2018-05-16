package cn.lihailjt.englishdictionary;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * @author <a href="bbmgt@163.com">lihai</a>
 * @version 1.0.0
 *          Created by lihai on 2018/5/15.
 */

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        /*
注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
*/
        UMConfigure.init(getApplicationContext(), "5af82213b27b0a72330001f1", "my", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
    }
}
