package com.zzb.demo.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 3 常用命令
3.1. 启动ZK服务:        bin/zkServer.sh start
3.2. 查看ZK服务状态:  bin/zkServer.sh status
3.3  停止ZK服务:        bin/zkServer.sh stop
3.4. 重启ZK服务:        bin/zkServer.sh restart 
3.5  连接服务器          zkCli.sh -server 127.0.0.1:2181
3.6  查看根目录 ls /
3.7  创建 testnode节点，关联字符串"zz"         create /zk/testnode "zz"
3.8  查看节点内容  get /zk/testnode 
3.9  设置节点内容  set /zk/testnode abc
4.0  删除节点      delete /zk/testnode
 *
 */
public class Demo {
    /** 超时 */
    public static final int SESSION_TIMEOUT = 1000;
    /**
     * ZooKeeper
     */
    private ZooKeeper zk;

    
    /**
     * watcher
     */
    Watcher watch = new Watcher(){
        public void process(WatchedEvent event) {
            System.out.println(event);
        }
        
    };
    
    /**
     * 功能描述: <br>
     * zk 实例
     * @throws IOException 
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void createZKInstance() throws IOException{
        this.zk=new ZooKeeper("localhost:2181", SESSION_TIMEOUT, watch);
    }
    
    
    /**
     * 功能描述: <br>
     * close
     *
     * @throws InterruptedException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void zkClose() throws InterruptedException{
        if(this.zk !=null){
            this.zk.close();
        }
    }
    
    
    private void zkOperations() throws KeeperException, InterruptedException{
        // 创建路径
        createPath("/test", "test string".getBytes());
        
        // 修改数据
        zk.setData("/test", "修改数据".getBytes(), -1);
        print(new String(zk.getData("/test",false,null)));
        
        // 删除数据
        zk.delete("/test", -1);
        print(zk.exists("/test", false));
    }
    
    /**
     * 功能描述: <br>
     * 创建path
     *
     * @param path
     * @param value
     * @throws KeeperException
     * @throws InterruptedException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void createPath(String path,byte[] value) throws KeeperException, InterruptedException{
//        zk.delete(path, -1);
        // 创建路径
        zk.create(path, value, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        print(new String(zk.getData(path,false,null)));
    }
    
    /**
     * 功能描述: <br>
     * main 入口
     *
     * @param args
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void main(String[] args) {
        Demo app = new Demo();
        try {
            app.createZKInstance();
            app.createPath("/znode", "znode".getBytes());
            app.zkOperations();
            
            Thread.sleep(30000);
//            app.zkClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 功能描述: <br>
     * print 
     *
     * @param obj
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void print(Object obj){
        System.out.println(obj);
    }
}
