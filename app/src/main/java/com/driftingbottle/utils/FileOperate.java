package com.driftingbottle.utils;

import android.os.Environment;


import com.driftingbottle.App;

import java.io.File;

/**
 * Created by jidan on 18-3-7.
 */

public class FileOperate {
    public final int iLogFolder = 0;
    public final int iCrashFolder = 1;
    public static String createFolder(int type){
        String strFolder ="";
        switch (type){
            case 0:
               strFolder =  "/data/data/" +  App.globalContext().getPackageName() + "/log";
                break;
            case 1:
                strFolder = "/sdcard/crash/";
                break;
            case 3:
                strFolder =  "/data/data/" +  App.globalContext().getPackageName() + "/media";
                break;
            default:
                break;
        }
        File file = new File(strFolder);
        if(!file.exists()){
            file.mkdirs();
        }
       return strFolder;
    }
}
