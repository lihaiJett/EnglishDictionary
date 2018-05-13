package cn.lihailjt.englishdictionary;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.lihailjt.englishdictionary.http.Retrofits;
import cn.lihailjt.englishdictionary.http.shanbei.ShanbeiApi;
import cn.lihailjt.englishdictionary.userdata.UserData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        MainActivityPermissionsDispatcher.showContactsWithCheck(MainActivity.this);

        Button btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,1);
            }
        });

        findViewById(R.id.playSound).setOnClickListener(v -> {
            String word = ((TextView) findViewById(R.id.word)).getText().toString();
            if(!word.trim().isEmpty()) {
                MyMediaplayer.get().playWordSound(MainActivity.this, word);
            }
//            Retrofits.get(ShanbeiApi.class)
//                    .getServerInfo()
//                    //指定在IO线程进行网络请求
//                    .subscribeOn(Schedulers.io())
//                    //指定订阅者在UI线程响应
//                    .observeOn(AndroidSchedulers.mainThread())
//                    //处理结果
//                    .subscribe(result -> {
////                        Toast.makeText(this,
////                        if (result.isSuccess()) {
////                            Toast.makeText(this,
////                                    result.getData().toString(),
////                                    Toast.LENGTH_SHORT)
////                                    .show();
////                        }else {
////                            Toast.makeText(this,
////                                    String.format("失败：%s", result.getMessage()),
////                                    Toast.LENGTH_SHORT)
////                                    .show();
////                        }
//                    });
        });

        onSet();
    }

    private void onSet(){
        final String url = UserData.get(MainActivity.this).getString(UserData.CURXLSFILE,"");
        if(url.endsWith(".xls")){
            findViewById(R.id.quick).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.curPath)).setText(url);
            ((TextView)findViewById(R.id.curNum)).setText(String.valueOf(UserData.get(MainActivity.this).getInt(UserData.CURNUM,0)+1) );
            (findViewById(R.id.entry)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,ReciteActivity.class);
                    intent.putExtra("filePath",url);
                    startActivity(intent);
                }
            });
        }else{
            findViewById(R.id.quick).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri
            Toast.makeText(MainActivity.this, uri.getPath(), Toast.LENGTH_SHORT).show();
            UserData.get(MainActivity.this).setPreference(UserData.CURXLSFILE,uri.getPath());
            UserData.get(MainActivity.this).setPreference(UserData.CURNUM,0);
            onSet();
        }
    }
    /** 
     * 如果用户拒绝该权限执行的方法 
     */
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onDeniedTakeCamera() {
        Toast.makeText(getApplicationContext(), "权限获取失败", Toast.LENGTH_SHORT).show();
    }
    /** 
     * 如果用户选择了让设备“不再询问”，而调用的方法 
     */
    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onNeverAskTakeCamera() {
        Toast.makeText(getApplicationContext(), "请到应用设置中开启权限", Toast.LENGTH_SHORT).show();
    }
    /** 
     * 如果用户拥有该权限执行的方法 
     */
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showContacts() {
        Toast.makeText(getApplicationContext(), "成功获取权限", Toast.LENGTH_SHORT).show();
    }
    /** 
     * 权限请求回调，提示用户之后，用户点击“允许”或者“拒绝”之后调用此方法 
     *
     * @param requestCode  定义的权限编码 
     * @param permissions  权限名称 
     * @param grantResults 允许/拒绝 
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
