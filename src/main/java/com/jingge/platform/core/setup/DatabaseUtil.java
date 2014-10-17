package com.jingge.platform.core.setup;

import java.sql.*;
import javax.sql.*;

/**
 * <p>
 * DatabaseUtil provides some basic methods for handling common jdbc operations
 * 
 * <dl>
 * <dt><b>Examples:</b></dt>
 * <p>
 * 
 * <pre>
 * </pre>
 * 
 * <p>
 * <dt><b>Thread Safety:</b></dt>
 * <dd> <b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be
 * used on multi-thread occasion.) </dd>
 * 
 * <p>
 * <dt><b>Serialization:</b></dt>
 * <dd> <b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no
 * need to be serializable.) </dd>
 * 
 * <p>
 * <dt><b>Design Patterns:</b></dt>
 * <dd>
 * 
 * </dd>
 * 
 * <p>
 * <dt><b>Change History:</b></dt>
 * <dd> Date Author Action </dd>
 * <dd> Jan 22, 2011 Henry.Lv Create the class </dd>
 * 
 * </dl>
 * 
 * @author Henry.Lv MSN/Email: hongli_leu@126.com
 * @see
 * @see
 */
public class DatabaseUtil {
    
    public static void close(RowSet rs) {
        try {
            if(rs != null) {
                rs.close();
            }
        }
        catch (Exception e) {
            // ignore
            e.printStackTrace();
        }
    }
    
    public static void close(ResultSet rs) {
        try {
            if(rs != null) {
                rs.close();
            }
        }
        catch (Exception e) {
            // ignore
            e.printStackTrace();
        }
    }
    
    public static void close(Statement stmt) {
        try {
            if(stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            // ignore
            e.printStackTrace();
        }
    }
    
    public static void close(PreparedStatement ps) {
        try {
            if(ps != null) {
                ps.close();
            }
        }
        catch (Exception e) {
            // ignore
            e.printStackTrace();
        }
    }
    
    public static void close(Connection conn) {
        try {
            if(conn != null) {
                conn.close();
            }
        }
        catch (Exception e) {
            // ignore
            e.printStackTrace();
        }
    }
    
    public static java.sql.Date getJavaSqlDate() {
        java.util.Date javaUtilDate = new java.util.Date();
        return new java.sql.Date(javaUtilDate.getTime());
    }
    
    public static String getTrimmedString(ResultSet resultSet, int index) throws SQLException {
        String value = resultSet.getString(index);
        
        if(value != null) {
            value = value.trim();
        }
        
        return value;
    }
    
    public static String getTrimmedString(ResultSet resultSet, String columnName) throws SQLException {
        String value = resultSet.getString(columnName);
        
        if(value != null) {
            value = value.trim();
        }
        
        return value;
    }
    
}
