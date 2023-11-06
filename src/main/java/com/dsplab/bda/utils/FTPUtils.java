package com.dsplab.bda.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;

@Component
@Slf4j
public class FTPUtils {

    private static FTPClient ftpClient; //创建对象
    private static String ip; //ftp地址
    private static Integer port; //ftp默认端口号是21
    private static String userName; //用户名
    private static String passWord;	//密码
    private static final String CHARSET = "GBK"; //防止中文乱码

    @Value("${ftp.ip}")
    public void setIp(String ip) {
        FTPUtils.ip = ip;
    }
    @Value("${ftp.port}")
    public void setPort(Integer port) {
        FTPUtils.port = port;
    }
    @Value("${ftp.userName}")
    public void setUserName(String userName) {
        FTPUtils.userName = userName;
    }
    @Value("${ftp.passWord}")
    public void setPassWord(String passWord) {
        FTPUtils.passWord = passWord;
    }

    /**
     * 初始化连接
     * @return
     */
    public static boolean initFtpClient() {
        boolean isSuccess = false;
        int reply;
        ftpClient = new FTPClient();
        try {
            ftpClient.setControlEncoding(CHARSET); //Encoding不能在connect和login之后设置，否则不会生效
            ftpClient.connect(ip, port);		//连接ftp服务器
            ftpClient.login(userName, passWord);  //登陆ftp服务器
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置文件类型（二进制传输模式）
            ftpClient.enterLocalPassiveMode();// 设置被动模式(不要写在connect,login之前)
            reply = ftpClient.getReplyCode();     //获取返回码，用于判断是否连接成功
            if (!FTPReply.isPositiveCompletion(reply)) {
                log.info("未连接到FTP，用户名或密码错误。");
                throw new Exception("服务器连接失败");
            }
            isSuccess = true;
        } catch (Exception e) {
            log.error("ftp服务器创建连接失败",e);
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 关闭连接
     */
    public static void dropFtpClient() {
        try {
            ftpClient.logout(); //退出登陆
            //检测是否连接ftp服务器
            if (ftpClient.isConnected()) {
                ftpClient.disconnect(); //关闭连接
            }
        } catch (IOException e) {
            log.error("ftp服务器关闭连接失败",e);
            e.printStackTrace();
        }
    }

    /**
     * 上传文件或文件夹
     *
     * @param pathName       ftp服务保存地址（相对路径）
     * @param originFilename 待上传文件的名称（绝对地址）
     * @return
     */
    public static boolean uploadFile(String pathName, String originFilename) {
        boolean isSuccess = false;
        try {
            initFtpClient();
            File localFile = new File(originFilename);
            // 设置上传文件的类型为二进制类型
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //跳转到指定的ftp文件目录（相对路径）
            if (!ftpClient.changeWorkingDirectory(pathName)) {
                // 如果目录不存在逐层创建目录
                String[] dirs = pathName.split("/");
                String root = "";
                for (String dir : dirs) {
                    if (StringUtils.isEmpty(dir)) {
                        continue;
                    }
                    root += "/" + dir;
                    if (!ftpClient.changeWorkingDirectory(root)) {
                        if (!ftpClient.makeDirectory(root)) {
                            log.error("创建目录失败！");
                            return isSuccess;
                        } else {
                            log.info("成功创建目录");
                            ftpClient.changeWorkingDirectory(root);
                        }
                    }
                }
            }
            //递归上传文件或文件夹
            upload(localFile, ftpClient);
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dropFtpClient();
        }
        return isSuccess;
    }

    /**
     * ftp上传文件夹递归方法
     * @param localFile
     * @param ftpClient
     * @throws Exception
     */
    public static void upload(File localFile, FTPClient ftpClient){
        try {
            if (localFile.isDirectory()) {
                ftpClient.makeDirectory(localFile.getName());
                ftpClient.changeWorkingDirectory(localFile.getName());
                String[] files=localFile.list();
                for(String file : files){
                    File file1=new File(localFile.getPath() + "/" + file);
                    if (file1.isDirectory()) {
                        upload(file1, ftpClient);
                        ftpClient.changeToParentDirectory();
                    }else{
                        File file2=new File(localFile.getPath() + "/" + file);
                        FileInputStream input=new FileInputStream(file2);
                        ftpClient.storeFile(file2.getName(),input);
                        input.close();
                    }
                }
            }else{
                File file2=new File(localFile.getPath());
                FileInputStream input=new FileInputStream(file2);
                ftpClient.storeFile(file2.getName(),input);
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * fileName为空时，下载ftp目录下所有文件或子文件
     * fileName不为空时，下载ftp目录下指定文件或文件夹
     * @param pathName  ftp服务器文件目录
     * @param fileName  ftp服务器文件或文件夹名称
     * @param localPath 下载后存放的文件路径
     * @return
     */
    public static boolean downloadFile(String pathName, String fileName, String localPath) {
        boolean isSuccess = false;
        OutputStream os = null;
        try {
            //跳转到指定的ftp文件目录
            if(!ftpClient.changeWorkingDirectory(pathName)){
                log.error("文件路径不存在！下载失败！");
                return isSuccess;
            }
            //获取目录下所有的文件和文件夹
            FTPFile[] ftpFiles = ftpClient.listFiles();
            //遍历目录下所有的文件
            StringBuilder pathNameBuilder = new StringBuilder(pathName);
            if(localPath.charAt(localPath.length()-1) == '/') localPath = localPath.substring(0, localPath.length()-1);
            for (FTPFile file : ftpFiles) {
                if(fileName != null && !file.getName().equals(fileName)) continue;
                if (file.isFile()) {
                    File localFile = new File(localPath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);//下载文件到本地
                    os.close();
                } else if (file.isDirectory()) {
                    File localFile =new File(localPath + "/" + file.getName());
                    //如果本地文件夹不存在则创建
                    if  (!localFile.exists() && !localFile.isDirectory()) {
                        boolean mkdir = localFile.mkdir();
                        if(!mkdir) log.error("本地创建文件夹失败！");
                    }
                    //文件夹递归调用
                    if(pathNameBuilder.charAt(pathNameBuilder.length()-1) != '/') pathNameBuilder.append("/");
                    downloadFile(pathNameBuilder + file.getName() + "/", null, localPath+ "/" + file.getName());
                }
            }
            isSuccess = true;
        } catch (FileNotFoundException e) {
            log.error("没有找到文件");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("文件读取错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 删除文件或文件夹
     *
     * @param pathName  ftp服务器文件目录
     * @param fileName  删除的文件名,为空时则删除整个文件夹
     * @return
     */
    public static boolean deleteFile(String pathName, String fileName) {
        boolean isSuccess = false;
        try {
            initFtpClient();
            if(pathName.charAt(pathName.length()-1) == '/') pathName = pathName.substring(0, pathName.length()-1);
            if(fileName == null){
               return deleteDirectory(pathName);
            }
            if(!ftpClient.changeWorkingDirectory(pathName)){
                log.error("文件不存在！删除失败！");
                return isSuccess;
            }
            isSuccess = ftpClient.deleteFile(fileName);//删除文件
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dropFtpClient();
        }
        return isSuccess;
    }

    /**
     * 删除Ftp上的文件夹 包括其中的文件
     */
    public static boolean deleteDirectory(String pathName) {
        boolean isSuccess = false;
        try {
            FTPFile[] files = ftpClient.listFiles(pathName);
            if (null == files || files.length == 0) {
                isSuccess = true;
            }else{
                for (FTPFile file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(pathName + "/" + file.getName());
                    } else {
                        String path = pathName + "/" + file.getName();
                        if (!ftpClient.deleteFile(path)) {
                            log.error("文件未删除: "+ path);
                            return isSuccess;
                        }
                    }
                }
            }
            // 切换到父目录，不然删不掉文件夹
            ftpClient.changeWorkingDirectory(pathName.substring(0, pathName.lastIndexOf("/")));
            if(ftpClient.removeDirectory(pathName)){
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
