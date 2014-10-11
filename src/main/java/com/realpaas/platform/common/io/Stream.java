/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

/**
 * <p>
 * 
 * <dl>
 * <dt><b>Examples:</b></dt>
 * <p>
 * <pre>
 * 
 * </pre>
 * 
 * <p><dt><b>Immutability:</b></dt> 
 * <dd>
 * 	<b>IMMUTABLE</b> and <b>MUTABLE</b>
 * </dd>
 * 
 * <p><dt><b>Thread Safety:</b></dt> 
 * <dd>
 * 	<b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread occasion.)
 * </dd>
 * 
 * <p><dt><b>Serialization:</b></dt>
 * <dd>
 * 	<b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)
 * </dd>
 * 
 * <p><dt><b>Design Patterns:</b></dt>
 * <dd>
 * 	
 * </dd>
 * 
 * <p><dt><b>Change History:</b></dt>
 * <dd>
 * 	Date		Author		Action
 * </dd>
 * <dd>
 * 	Mar 19, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class Stream {
    
    private static final int DEFAULT_TRANSFORM_BUFFER_SIZE = 1024;

    public static ByteArrayInputStream convertBaos(ByteArrayOutputStream baos){
        byte[] bytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream( bytes );
        return bais;
    }
    
    public static ByteArrayOutputStream convertBais(ByteArrayInputStream bais){
        bais.reset();
        byte[] bytes = new byte[ bais.available() ];
        ByteArrayOutputStream baos = new ByteArrayOutputStream( bytes.length );
        
        try {
            IOUtils.copyLarge( bais, baos );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        
        return baos;
    }
    
    public static GZIPOutputStream gzipWrap(OutputStream os, int bufferSize){
        GZIPOutputStream result = null;
        try {
            result = new GZIPOutputStream( os, bufferSize );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        
        return result;
    }

    public static GZIPOutputStream gzipWrap(OutputStream os){
        return gzipWrap( os, DEFAULT_TRANSFORM_BUFFER_SIZE );
    }

    public static GZIPOutputStream decorateBaosToGzipos(ByteArrayOutputStreamSource stream){
        ByteArrayOutputStream baos = null; 
        GZIPOutputStream result = null;

        if( stream.getOriginalStream()==null ){
            baos = new ByteArrayOutputStream();
            stream.setOriginalStream( baos );
        }
        else{
            baos = stream.getOriginalStream();
        }
        
        try {
            result = new GZIPOutputStream( baos, DEFAULT_TRANSFORM_BUFFER_SIZE );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        
        return result;
    }
    
    public static class DecoratedStreamSource<T>{

        private T originalStream;

        public T getOriginalStream() {
            return originalStream;
        }

        public void setOriginalStream(T originalStream) {
            this.originalStream = originalStream;
        }
    }
    
    public static class DecoratedInputStreamSource<T extends InputStream> extends DecoratedStreamSource<T>{

    }
    
    public static class DecoratedOutputStreamSource<T extends OutputStream> extends DecoratedStreamSource<T>{

    }
    
    public static class ByteArrayOutputStreamSource extends DecoratedOutputStreamSource<ByteArrayOutputStream>{
        
    }
    
    public static class ByteArrayInputStreamSource extends DecoratedInputStreamSource<ByteArrayInputStream>{
        
    }

    public static CopyShareResult copyToShare(InputStream is, boolean gzip){
        SharedByteArrayOutputStream baos = new SharedByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = null;
        int originalLength = -1;
        int resultLength = -1;
        try {
            if( gzip ){
                gzipOutputStream = new GZIPOutputStream( baos );
                originalLength = IOUtils.copy( is, gzipOutputStream );
                gzipOutputStream.finish();
                gzipOutputStream.flush();
                gzipOutputStream.close();
            }
            else{
                originalLength = IOUtils.copy( is, baos );
            }
            resultLength = baos.size();
            baos.close();
        }
        catch ( IOException e ) {
            throw new RuntimeException( "Fail to copy and share", e );
        }

        return new CopyShareResult(originalLength, resultLength, baos);
    }

    public static ResultInputStream gzip(InputStream is){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = null;
        int originalLength = -1;
        
        try {
            gzipOutputStream = new GZIPOutputStream( baos );
            originalLength = IOUtils.copy( is, gzipOutputStream );
            gzipOutputStream.finish();
            gzipOutputStream.flush();
            gzipOutputStream.close();
        }
        catch ( IOException e ) {
            e.printStackTrace();
            throw new RuntimeException( "Fail to gzip-compress the input stream", e );
        }

        byte[] compressedBytes = baos.toByteArray();
        int compressedLength = compressedBytes.length;
        ByteArrayInputStream bais = new ByteArrayInputStream( compressedBytes );
        ResultInputStream result = new ResultInputStream(originalLength, compressedLength, bais);
        return result;
    }
    
    public static ResultInputStream ungzip(InputStream is){
        GZIPInputStream gzipInputStream = null;
        
        try {
            gzipInputStream = new GZIPInputStream( is );
        }
        catch ( IOException e ) {
            e.printStackTrace();
            throw new RuntimeException( "Fail to gzip-uncompress the input stream", e );
        }
        ResultInputStream result = new ResultInputStream(-1, -1, gzipInputStream);
        
        return result;
    }
    
    public static class ResultInputStream {
        private int         originalLength;
        private int         resultLength;
        private InputStream result;

        public ResultInputStream(int originalLength, int resultLength, InputStream result) {
            super();
            this.originalLength = originalLength;
            this.resultLength = resultLength;
            this.result = result;
        }

        public int getOriginalLength() {
            return originalLength;
        }

        public void setOriginalLength(int originalLength) {
            this.originalLength = originalLength;
        }

        public int getResultLength() {
            return resultLength;
        }

        public void setResultLength(int resultLength) {
            this.resultLength = resultLength;
        }

        public InputStream getResult() {
            return result;
        }

        public void setResult(InputStream result) {
            this.result = result;
        }
        
    }
    
    public static class CopyShareResult {
        private int         originalLength;
        private int         resultLength;
        private SharedByteArrayOutputStream result;

        public CopyShareResult(int originalLength, int resultLength, SharedByteArrayOutputStream result) {
            super();
            this.originalLength = originalLength;
            this.resultLength = resultLength;
            this.result = result;
        }

        public int getOriginalLength() {
            return originalLength;
        }

        public void setOriginalLength(int originalLength) {
            this.originalLength = originalLength;
        }

        public int getResultLength() {
            return resultLength;
        }

        public void setResultLength(int resultLength) {
            this.resultLength = resultLength;
        }

        public SharedByteArrayOutputStream getResult() {
            return result;
        }

        public void setResult(SharedByteArrayOutputStream result) {
            this.result = result;
        }
    }    
}
