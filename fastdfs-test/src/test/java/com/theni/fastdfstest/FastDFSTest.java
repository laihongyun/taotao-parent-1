package com.theni.fastdfstest;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @program: fastdfs-test
 * @description: 文件服务器上传，下载，删除测试
 * @author: lhy
 * @create: 2020-07-17 09:32
 **/
public class FastDFSTest {
/** 上传文件测试*/
    @Test
    public void upload_file() throws IOException, MyException {
        //获取tracker_server地址文件加载路径
        String conf_file = this.getClass().getResource("/fastdfs_client.conf").getPath();
        System.out.println(conf_file);
        //初始化客户端全局信息
        ClientGlobal.init(conf_file);
//        创建存储客户端对象
        StorageClient storageClient = new StorageClient();
//        上传文件到fastdfs分布式文件系统
        String[] arr = storageClient.upload_file("C:/Users/86135/Desktop/zuiz/14.jpg", "jpg", null);
        System.out.println(Arrays.toString(arr));
    }

    /**下载文件测试*/
    @Test
    public void downloadfile() throws IOException, MyException {
        String path = this.getClass().getResource("/fastdfs_client.conf").getPath();
        ClientGlobal.init(path);
        StorageClient storageClient = new StorageClient();

        byte[] bytes = storageClient.download_file("group1", "M00/00/00/wKgokl8RDRKAObE6AAZScyFb3t4061.jpg");

        System.out.println("数组大小"+bytes.length);
//        存储获取的数据
        FileOutputStream fos = new FileOutputStream(new File("G:\\tempsave\\1.jpg"));
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
    //删除文件
    @Test
    public void deleteFile() throws IOException, MyException {
        String path = this.getClass().getResource("/fastdfs_client.conf").getPath();
        ClientGlobal.init(path);
        StorageClient storageClient = new StorageClient();
        int res = storageClient.delete_file("group1", "M00/00/00/wKgokl8RDRKAObE6AAZScyFb3t4061.jpg");
        System.out.println(res);

    }
}
