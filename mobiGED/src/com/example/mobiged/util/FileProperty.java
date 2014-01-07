package com.example.mobiged.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileProperty {

    private File file = null;
    
    public FileProperty(String filename) {
        file = new File(filename);
    }


    public long getDate() {
        return this.file.lastModified();
    }

    public String getFormatedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
        Date d = new Date(this.file.lastModified());
        return sdf.format(d);
    }

    //taille en ko
    public int getSize() {
        return (int) (this.file.length() / 1024) + 1 ;
    }

    public String getFormatedSize() {
        int size = (int) (this.file.length() / 1024) + 1;
        if (size > 1024) {
            return (size / 1024) + " Mo";
        } else {
            return size + " ko";
        }
    }


    public static String getFileExt(String filename) {
        int pos = filename.lastIndexOf(".");
        if (pos > -1) {
            return filename.substring(pos);
        } else {
            return filename;
        }
    }
    
    
}
