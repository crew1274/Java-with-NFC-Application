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
          ftp.connect(url, port);//�s��FTP�A�Ⱦ�
          //�p�G�ĥ��q�{�ݤf�A�i�H�ϥ�ftp.connect(url)���覡�����s��FTP�A�Ⱦ�
        }catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * @param path FTP�W���O�s�ؿ�
     * @param filename ���W��
     * @param input ��J�y
     */
    public boolean uploadFile(String path, String filename, InputStream input) {
        boolean success = false;
        try {
            int reply;
            ftp.login(username, password);//�n��
            reply = ftp.getReplyCode();
            ftp.makeDirectory(path);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            //ftp.setFileType(FTP.BINARY_FILE_TYPE);�p�G�O�Ϥ�
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
        return success;//���\��^true
    }
    /**
     * @param remoteFileName ���bftp�W�����|
     * @param localSavePath �U���U�Ӫ����s����|
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
            throw new RuntimeException("FTP�Ȥ�ݥX���I", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace(); //�����s�����`
            }
        }
    }
    public static void main(String[] args)
    {
    	/*File imgFile = new File(�W���ɮת����|);
    	FileInputStream fis = new FileInputStream(imgFile);
    	FTPUploader uploader = new FTPUploader();
    	boolean flag = uploader.uploadFile(�W�Ǧ�ftp�����ӥؿ�, imgFile.getName(), fis);*/
    	FTP downloader = new FTP();
    	downloader.downloadFile("04.jpg","04.jpg");
    }
}