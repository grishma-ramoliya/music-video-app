package com.example.musicvideoapp;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Decompress {
    private String zip;
    private String loc;

    public Decompress(String zipFile, String location) {
        zip = zipFile;
        loc = location;

        dirChecker("","");
    }

    public void unzip() {
        try  {
            FileInputStream fin = new FileInputStream(zip);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    dirChecker("/storage/emulated/0/Download/",ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(loc + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        } catch(Exception e) {
            Log.e("Decompress", "unzip", e);
        }

    }

    private static void dirChecker(String destination, String dir) {
        File f = new File(destination, dir);

        if (!f.isDirectory()) {
            boolean success = f.mkdirs();
            if (!success) {
                Log.w("Fail to create folder", "Failed to create folder " + f.getName());
            }
        }
    }
}
