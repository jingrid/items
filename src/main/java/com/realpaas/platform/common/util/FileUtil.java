/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p>
 * 
 * <dl>
 * <dt><b>Examples:</b></dt>
 * <p>
 * 
 * <pre>
 * 
 * </pre>
 * 
 * <p>
 * <dt><b>Immutability:</b></dt>
 * <dd>
 * <b>IMMUTABLE</b> and <b>MUTABLE</b></dd>
 * 
 * <p>
 * <dt><b>Thread Safety:</b></dt>
 * <dd>
 * <b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread
 * occasion.)</dd>
 * 
 * <p>
 * <dt><b>Serialization:</b></dt>
 * <dd>
 * <b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)</dd>
 * 
 * <p>
 * <dt><b>Design Patterns:</b></dt>
 * <dd>
 * 
 * </dd>
 * 
 * <p>
 * <dt><b>Change History:</b></dt>
 * <dd>
 * Date Author Action</dd>
 * <dd>
 * Jan 29, 2013 henryleu Create the class</dd>
 * 
 * </dl>
 * 
 * @author henryleu Email/MSN: hongli_leu@126.com
 */
final public class FileUtil {
    
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * Hide default contructor
     */
    private FileUtil() {}

    public static boolean copyFile(File sourceFile, File targetDir) throws FileNotFoundException, IOException {
        File targetFile = new File( targetDir, sourceFile.getName() ); //target file's top file
        if( sourceFile.isDirectory() ) { //Copy directory
            targetFile.mkdir();
            File[] dir = sourceFile.listFiles();
            for(int i = 0; i < dir.length; i++) {
                if( !copyFile( dir[i], targetFile ) ) {
                    return false;
                }
            }
            return true;
        }
        else { //Copy file
            FileInputStream fis = new FileInputStream( sourceFile );
            FileOutputStream fos = new FileOutputStream( targetFile );
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int c = 0;
            while( (c = fis.read( buffer )) != -1 ) {
                fos.write( buffer, 0, c );
            }
            fis.close();
            fos.close();
            return true;
        }
    }
    
    public static boolean copyFile(File sourceFile, File targetDir, String targetFilename) throws FileNotFoundException, IOException {
        File targetFile = new File( targetDir, targetFilename ); //target file's top file
        if( sourceFile.isDirectory() ) { //Copy directory
            return false;
        }
        else { //Copy file
            FileInputStream fis = new FileInputStream( sourceFile );
            FileOutputStream fos = new FileOutputStream( targetFile );
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int c = 0;
            while( (c = fis.read( buffer )) != -1 ) {
                fos.write( buffer, 0, c );
            }
            fis.close();
            fos.close();
            return true;
        }
    }
    
    public static boolean cutFile(File sourceFile, File targetDir) throws FileNotFoundException, IOException  {
        if( copyFile( sourceFile, targetDir ) ) {
            deleteFile( sourceFile );
            return true;
        }
        else {
            return false;
        }
    }

    public static void deleteFile(File sourceFile) {
        if (sourceFile.isDirectory()) {
            File[] files = sourceFile.listFiles();
            for(int i = 0; i < files.length; i++) {
                if( files[i].isDirectory() ) {
                    deleteFile( files[i] );
                }
                else {
                    files[i].delete();
                }
            }
            sourceFile.delete();
        } else {
            sourceFile.delete();
        }
    }

    public static void renameFile(File sourceFile, String newFileName) {
        File targetFile = new File( sourceFile.getParent(), newFileName );
        sourceFile.renameTo( targetFile );
    }
    
}
