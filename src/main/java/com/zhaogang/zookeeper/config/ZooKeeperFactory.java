/** 
 * 文件名：ZooKeeperFactory.java 
 * 版本信息：1.0
 * 日期：2015年6月30日-下午1:22:47   
 */
package com.zhaogang.zookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 * ZooKeeperFactory
 *
 * @author qian.xu
 * @date 2015年6月30日 下午1:22:47
 * @version 
 * 
 */

public class ZooKeeperFactory {

    public static final String CONNECT_STRING = "127.0.0.1:2181";
    
    public static final int MAX_RETRIES = 3;
 
    public static final int BASE_SLEEP_TIMEMS = 3000;
 
    public static final String NAME_SPACE = "cfg";
 
    public static CuratorFramework get() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIMEMS, MAX_RETRIES);
        
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .retryPolicy(retryPolicy)
                .namespace(NAME_SPACE)
                .build();
        client.start();
        
        return client;
    }
}


