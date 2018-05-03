/** 
 * 文件名：ZkUtils.java 
 * 版本信息：1.0
 * 日期：2015年6月30日-下午1:55:48   
 */
package com.zhaogang.zookeeper.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;


/**
 * ZkUtils
 *
 * @author qian.xu
 * @date 2015年6月30日 下午1:55:48
 * @version 
 * 
 */

public class ZkUtils {

    private static  CuratorFramework client = ZooKeeperFactory.get() ;
    private static final String PATH = "/crud"; 
    /**
     * 新增节点
     * add(这里用一句话描述这个方法的作用)
     *
     * @return void 
     * @exception  
     *
     */
    public static void add(){
        try{
            Id id = new Id("digest", DigestAuthenticationProvider.generateDigest("xq:123")); //采用digest方式，它对应的id为username:BASE64(SHA1(password))，它需要先通过username:password形式的authentication
            ACL acl = new ACL(ZooDefs.Perms.ALL, id); 
            List<ACL> aclList = new ArrayList<ACL>();
            aclList.add(acl);
            Stat s = client.getZookeeperClient().getZooKeeper().setACL(PATH, aclList,-1);
         // 开启事务  
            CuratorTransaction transaction = client.inTransaction();
            transaction.create().forPath(PATH, "I love messi".getBytes()).and().commit();
           // 注册观察者，当节点变动时触发  
            client.getData().usingWatcher(new Watcher() {  
                
                public void process(WatchedEvent event) {  
                    System.out.println("node is changed");  
                }  
            }).inBackground().forPath(PATH);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
         // 释放客户端连接  
            CloseableUtils.closeQuietly(client); 
        }
        
    }
    /**
     * 删除节点
     * delete(这里用一句话描述这个方法的作用)
     *
     * @return void 
     * @exception  
     *
     */
    public static void delete(){
        try{
            
         // 开启事务  
            CuratorTransaction transaction = client.inTransaction();
            transaction.delete().withVersion(-1).forPath(PATH).and().commit();
//            client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(-1).forPath(PATH);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
         // 释放客户端连接  
            CloseableUtils.closeQuietly(client); 
        }
        
    }
    /**
     * 获取节点数据
     * getNodeData(这里用一句话描述这个方法的作用)
     *
     * @return
     * @return byte[] 
     * @exception  
     *
     */
    public static byte[] getNodeData(){
        byte[] nodeData = null ;
        try { 
            Stat stat = new Stat();
             nodeData = client.getData()
                .storingStatIn(stat)
                .forPath(PATH);
            System.out.println("NodeData: " + new String(nodeData));
            System.out.println("Stat: " + stat);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
         // 释放客户端连接  
            CloseableUtils.closeQuietly(client); 
        }
        return nodeData ;
       
    }
    /**
     * 更新节点数据
     * update(这里用一句话描述这个方法的作用)
     *
     * @return void 
     * @exception  
     *
     */
    public static void update(){
        try{
            
            // 开启事务  
            CuratorTransaction transaction = client.inTransaction();
            transaction.setData()
            .withVersion(-1)
            .forPath("/zk-huey/cnode", "world".getBytes()).and().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            // 释放客户端连接  
            CloseableUtils.closeQuietly(client); 
        }
    }
    /**
     * 获取子节点列表
     * getChildrenList(这里用一句话描述这个方法的作用)
     *
     * @return
     * @return List<String> 
     * @exception  
     *
     */
    public static List<String> getChildrenList(){
        List<String> children = null ;
        try{
            
            children = client.getChildren().forPath(PATH);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
         // 释放客户端连接  
            CloseableUtils.closeQuietly(client); 
        }
        return children ;
    }
    private static void initClient(){
        client = ZooKeeperFactory.get();
    }
    
    public static void watch()throws Exception{ 
        PathChildrenCache cache = new PathChildrenCache(client, PATH, false); 
        cache.start(); 
        System.out.println("监听开始/zk........"); 
        PathChildrenCacheListener plis=new PathChildrenCacheListener() { 
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) 
            throws Exception { 
              switch ( event.getType() ) 
                            { 
                                case CHILD_ADDED: 
                                { 
                                    System.out.println("Node added: " + ZKPaths.getNodeFromPath(event.getData().getPath())); 
                                    break; 
                                } 

                                case CHILD_UPDATED: 
                                { 
                                    System.out.println("Node changed: " + ZKPaths.getNodeFromPath(event.getData().getPath())); 
                                    break; 
                                } 

                                case CHILD_REMOVED: 
                                { 
                                    System.out.println("Node removed: " + ZKPaths.getNodeFromPath(event.getData().getPath())); 
                                    break; 
                                } 
                            } 

            } 
            }; 
            
          //注册监听 
            cache.getListenable().addListener(plis);
    }
}
