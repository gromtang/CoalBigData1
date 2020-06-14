package BigDataUtils;

import breeze.optimize.L1Regularization;
import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.client.Admin;新，替代下面过时的
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * HBase操作工具类：Java工具类建议采用单例模式封装
 */
public class HBaseUtils {

    HBaseAdmin admin ;
    Configuration configuration ;

    /**
     * 私有构造方法
     */
    private HBaseUtils(){
        configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum","linux01:2181");
        configuration.set("hbase.rootdir","hdfs://linux01:8020/hbase");//看看有没有

        try{
            admin = new HBaseAdmin(configuration);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static HBaseUtils instance = null;
    public static synchronized HBaseUtils getInstance() {
        if(null == instance) {
            instance = new HBaseUtils();
        }
        return instance;
    }

    /**
     * 根据表名获取到HTable实例
     */
    public HTable getTable(String tableName) {

        HTable table = null;

        try {
            table = new HTable(configuration,tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    /**
     * 添加一条记录到HBase表
     * @param tableName 表名
     * @param rowKey    rowkey
     * @param cf        columnfamily列族
     * @param column    列
     * @param value     写入表的值
     */
    public void put(String tableName, String rowKey, String cf,String column, String value){

        HTable table = getTable(tableName);
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(cf),Bytes.toBytes(column),Bytes.toBytes(value));

        try {
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    public static void main(String[] args) {
        HTable table = HBaseUtils.getInstance().getTable("wordcount");
        System.out.println(table.getName().getNameAsString());
    }
*/
}
