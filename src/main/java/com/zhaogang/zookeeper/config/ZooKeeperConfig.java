/** 
 * 文件名：ZooKeeperConfig.java 
 * 版本信息：1.0
 * 日期：2015年6月30日-下午1:22:02   
 */
package com.zhaogang.zookeeper.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;


/**
 * ZooKeeperConfig
 *
 * @author qian.xu
 * @date 2015年6月30日 下午1:22:02
 * @version 
 * 
 */

public class ZooKeeperConfig implements Config{

    public byte[] getConfig(String path) throws Exception {
        CuratorFramework client = ZooKeeperFactory.get();
        if (!exists(client, path)) {
            throw new RuntimeException("Path " + path + " does not exists.");
        }
        return client.getData().forPath(path);
    }
     
    private boolean exists(CuratorFramework client, String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        return !(stat == null);
    }
}
