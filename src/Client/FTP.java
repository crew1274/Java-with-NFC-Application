package Client;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.*;
public class FTP {
    int port = 21;
    String url ="140.116.39.225";
    String username ="nfc_user";
    String password ="50150";
    FTPClient ftp = new FTPClient();
    public void FTP(){
        try{
          ftp.connect(url, port);//連接FTP服務器
          //如果採用默認端口，可以使用ftp.connect(url)的方式直接連接FTP服務器
        }catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * @param path FTP上的保存目錄
     * @param filename 文件名稱
     * @param input 輸入流
     */
    public boolean uploadFile(String path, String filename, InputStream input) {
        boolean success = false;
        try {
            int reply;
            ftp.login(username, password);//登錄
            reply = ftp.getReplyCode();
            ftp.makeDirectory(path);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            //ftp.setFileType(FTP.BINARY_FILE_TYPE);如果是圖片
            ftp.changeWorkingDirectory(path);
            ftp.storeFile(filename, input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;//成功返回true
    }
    /**
     * @param remoteFileName 文件在ftp上的路徑
     * @param localSavePath 下載下來的文件存放路徑
     */
    public void downloadFile(String remoteFileName,String localSavePath) {
        FileOutputStream fos = null;
        try {
                ftp.connect(url, port);
                ftp.login(username, password);
                File tmp = new File(localSavePath);
                if (!tmp.exists())
                tmp.createNewFile();
            fos = new FileOutputStream(localSavePath);
            ftp.setBufferSize(1024);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.retrieveFile(remoteFileName, fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客戶端出錯！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace(); //關閉連結異常
            }
        }
    }
    public static void main(String[] args)
    {
    	/*File imgFile = new File(上傳檔案的路徑);
    	FileInputStream fis = new FileInputStream(imgFile);
    	FTPUploader uploader = new FTPUploader();
    	boolean flag = uploader.uploadFile(上傳至ftp的哪個目錄, imgFile.getName(), fis);*/
    	FTP downloader = new FTP();
    	downloader.downloadFile("04.jpg","04.jpg");
    }
}