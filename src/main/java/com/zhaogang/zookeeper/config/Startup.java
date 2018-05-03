/** 
 * 文件名：Startup.java 
 * 版本信息：1.0
 * 日期：2015年6月30日-下午1:25:50   
 */
package com.zhaogang.zookeeper.config;



/**
 * Startup
 *
 * @author qian.xu
 * @date 2015年6月30日 下午1:25:50
 * @version 
 * 
 */

public class Startup {

    public static void main(String[] args) {
//        new ClassPathXmlApplicationContext("classpath:/zk.xml");
//        ZkUtils.add();
        ZkUtils.getNodeData();
    }
}
