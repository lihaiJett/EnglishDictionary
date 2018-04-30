package cn.lihailjt.englishdictionary.userdata;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author <a href="bbmgt@163.com">lihai</a>
 * @version 1.0.0
 *          Created by lihai on 2018/4/26.
 */

public class ProPreference {
    private String SPFileName = "default";//配置文件名
    private Context context; //上下文引用
    private static ProPreference proPreference;

    private ProPreference(Context _context,String name){
        this.context = _context.getApplicationContext();
        this.SPFileName = name;
    }

    /**
     * 获取操作ProPreference对象
     * @param _context
     * @return
     */
    public static ProPreference getProPreference(Context _context, String name){
        if(proPreference==null){
            synchronized(ProPreference.class){
                if(proPreference==null)
                    proPreference=new ProPreference(_context.getApplicationContext(),name);
            }
        }
        return proPreference;
    }

    /**
     * 设置字符串型参数
     * @param name
     * @param value
     * @return
     */
    public boolean setPreference (String name,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString(name, value);
        return editor.commit();//提交修改
    }
    /**
     * 设置布尔型参数
     * @param name
     * @param value
     * @return
     */
    public boolean setPreference (String name,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean(name, value);
        return editor.commit();//提交修改
    }
    /**
     * 设置int型参数
     * @param name
     * @param value
     * @return
     */
    public boolean setPreference (String name,int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putInt(name, value);
        return editor.commit();//提交修改
    }
    /**
     * 设置float型参数
     * @param name
     * @param value
     * @return
     */
    public boolean setPreference (String name,float value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putFloat(name, value);
        return editor.commit();//提交修改
    }
    /**
     * 获得bool型参数
     * @param name
     * @param def 默认值
     * @return
     */
    public boolean getBoolean (String name,boolean def){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE); //私有数据
        return sharedPreferences.getBoolean(name, def);
    }
    /**
     * 获得String型参数
     * @param name
     * @param def 默认值
     * @return
     */
    public String getString(String name,String def){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE); //私有数据
        return sharedPreferences.getString(name,def);
    }
    /**
     * 获得int型参数
     * @param name
     * @param def
     * @return
     */
    public int getInt(String name,int def){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE); //私有数据
        return sharedPreferences.getInt(name,def);
    }
    /**
     * 获得float型参数
     * @param name
     * @param def
     * @return
     */
    public float getFloat(String name,float def){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE); //私有数据
        return (float)sharedPreferences.getFloat(name,def);
    }

}
