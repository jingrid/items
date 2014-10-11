/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.dao.redis;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.testng.annotations.Test;

import com.realpaas.platform.test.AbstractTest;

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
 * 	2012-8-14	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class RedisSerializerTest extends AbstractTest {
    LongRedisSerializer longRedisSerializer = new LongRedisSerializer();
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    @Override
    public void setUp() throws Exception {

    }

    @Override
    public void tearDown() throws Exception {

    }
    
    @Test( groups={ "all", "platform", "ut", "framework", "redis" } )
    public void longSerializeAndDeserialize(){
        byte[] datablock = null;
        Long actualValue = null;
        Long v1 = 1234L;
        Long v2 = -1234L;
        Long v3 = 0x7efffffffffffffeL;
        Long v4 = 0x8000000000000000L;
        
        
        datablock = longRedisSerializer.serialize( v1 );
        actualValue = longRedisSerializer.deserialize( datablock );
        assertEquals( actualValue, v1 );

        datablock = longRedisSerializer.serialize( v2 );
        actualValue = longRedisSerializer.deserialize( datablock );
        assertEquals( actualValue, v2 );

        datablock = longRedisSerializer.serialize( v3 );
        actualValue = longRedisSerializer.deserialize( datablock );
        assertEquals( actualValue, v3 );

        datablock = longRedisSerializer.serialize( v4 );
        actualValue = longRedisSerializer.deserialize( datablock );
        assertEquals( actualValue, v4 );
    }

    @Test( groups={ "all", "platform", "ut", "framework", "redis" } )
    public void compareLongRedisSerializerPerformance(){
        int count = 200000;
        long start = 0L;
        long end = 0L;
        Long v1 = 1234L;
        byte[] datablock = longRedisSerializer.serialize( v1 );

        start = System.currentTimeMillis();
        for(int i=0; i<count; i++){
            datablock = longRedisSerializer.serialize( v1 );
        }
        end = System.currentTimeMillis();
        System.out.println( "LongRedisSerializer.serialize " + count + " from Long needs " + (end-start) );
        
        start = System.currentTimeMillis();
        for(int i=0; i<count; i++){
            longRedisSerializer.deserialize( datablock );
        }
        end = System.currentTimeMillis();
        System.out.println( "LongRedisSerializer.deserialize " + count + " to Long needs " + (end-start) );
    }
    
    @Test( groups={ "all", "platform", "ut", "framework", "redis" } )
    public void compareStringRedisSerializerPerformance(){
        int count = 200000;
        long start = 0L;
        long end = 0L;
        String v1 = "hello world";
        
        byte[] datablock = stringRedisSerializer.serialize( v1 );

        start = System.currentTimeMillis();
        for(int i=0; i<count; i++){
            datablock = stringRedisSerializer.serialize( v1 );
        }
        end = System.currentTimeMillis();
        System.out.println( "StringRedisSerializer.serialize " + count + " from String needs " + (end-start) );
        
        start = System.currentTimeMillis();
        for(int i=0; i<count; i++){
            stringRedisSerializer.deserialize( datablock );
        }
        end = System.currentTimeMillis();
        System.out.println( "StringRedisSerializer.deserialize " + count + " to String needs " + (end-start) );
    }
    
}
