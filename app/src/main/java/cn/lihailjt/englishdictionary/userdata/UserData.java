package cn.lihailjt.englishdictionary.userdata;

import android.app.Application;
import android.content.Context;

public class UserData {

    public static String CURXLSFILE = "CurXlsFile";
    public static String CURNUM = "CurNum";

    public static ProPreference get(Context context){
        return ProPreference.getProPreference(context,"UserData");
    }
}
