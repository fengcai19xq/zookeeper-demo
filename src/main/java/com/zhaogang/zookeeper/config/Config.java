/** 
 * 文件名：Config.java 
 * 版本信息：1.0
 * 日期：2015年6月30日-下午1:20:16   
 */
package com.zhaogang.zookeeper.config;


/**
 * Config
 *
 * @author qian.xu
 * @date 2015年6月30日 下午1:20:16
 * @version 
 * 
 */

public interface Config {

    byte[] getConfig(String path) throws Exception;
}
