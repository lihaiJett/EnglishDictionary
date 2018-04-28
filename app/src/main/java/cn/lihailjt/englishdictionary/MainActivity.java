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
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Administrator on 2018/4/22 0022.
 */
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
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            Toast.makeText(MainActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,ReciteActivity.class);
            intent.putExtra("filePath",uri.getPath());
            startActivity(intent);


//            String[] proj = {MediaStore.Images.Media.DATA};
//            Cursor actualimagecursor = getContentResolver().query(uri, proj, null, null, null);
//            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            actualimagecursor.moveToFirst();
//            String img_path = actualimagecursor.getString(actual_image_column_index);
//            File file = new File(img_path);
//            Toast.makeText(MainActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();

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
