package cn.lihailjt.englishdictionary;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
        MainActivityPermissionsDispatcher.showContactsWithCheck(MainActivity.this);
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
        Intent intent = new Intent(this,ReciteActivity.class);
        startActivity(intent);
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
