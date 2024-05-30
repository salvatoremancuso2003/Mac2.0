/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.util;

import static rc.so.util.Engine.insertTR;
import java.io.File;
import static java.io.File.separator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author rcosco
 */
public class FTP {

    /**
     *
     * @param server
     * @param port
     * @param user
     * @param pass
     * @return
     */
    public static FTPClient ftpConnect(String server, int port, String user, String pass) {
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(server, port);
            ftpClient.enterLocalPassiveMode();
            boolean log = ftpClient.login(user, pass);
            if (log) {
                if (ftpClient.isConnected()) {
                    return ftpClient;
                }
            }
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param ftpClient
     * @param dir
     * @return
     */
    public static boolean ftpChangeDir(FTPClient ftpClient, String dir) {
        if (ftpClient.isConnected()) {
            try {
                boolean es = ftpClient.changeWorkingDirectory(dir);
                return es;
            } catch (IOException ex) {
            }
        }
        return false;
    }

    /**
     *
     * @param ftpClient
     * @param outdir
     * @param filtername
     * @param delete
     * @return
     */
    public static ArrayList<File> ftpDownloadFiles(FTPClient ftpClient, File outdir, String filtername, boolean delete) {
        ArrayList<File> lista = new ArrayList<>();
        try {
            FTPFile[] listfiles = ftpClient.listFiles();
            if (listfiles.length > 0) {
                for (FTPFile listfile : listfiles) {
                    boolean check = true;
                    FTPFile fileftp = listfile;
                    String filename = fileftp.getName();
                    if (filtername != null) {
                        if (!filename.toLowerCase().contains(filtername.toLowerCase())) {
                            check = false;
                        }
                    }
                    if (check) {
                        File fileout = new File(outdir.getPath() + separator + filename);
                        try (OutputStream os = new FileOutputStream(fileout)) {
                            boolean es = ftpClient.retrieveFile(filename, os);
                            if (es) {
                                long destsize = fileout.length();
                                long originalsize = fileftp.getSize();
                                long perce = originalsize * 5 / 100;
                                long range = originalsize - perce;
                                if (destsize > range) {
                                    lista.add(fileout);
                                    if (delete) {
                                        ftpClient.deleteFile(filename);
                                    }
                                } else {
                                    fileout.delete();
                                }
                            } else {
                                fileout.delete();
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
        }
        return lista;
    }

    /**
     *
     * @param ftpClient
     * @param filename
     * @return
     */
    public static boolean ftpDeleteFile(FTPClient ftpClient, String filename) {
        try {
            return ftpClient.deleteFile(filename);
        } catch (IOException ex) {
        }
        return false;
    }

    /**
     *
     * @param ftpClient
     * @param fileup
     * @return
     */
    public static boolean ftpUploadFile(FTPClient ftpClient, File fileup) {
        try {
            String firstRemoteFile = deleteWhitespace(fileup.getName()) ;
            boolean done;
            try (InputStream inputStream = new FileInputStream(fileup)) {
                done = ftpClient.storeFile(firstRemoteFile, inputStream);
            }
            if (done) {
                long originalsize = fileup.length();
                FTPFile[] filenames = ftpClient.listFiles(firstRemoteFile);
                for (FTPFile filename : filenames) {
                    long destsize = filename.getSize();
                    long perce = originalsize * 5 / 100;
                    long range = originalsize - perce;
                    if (destsize > range) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return false;
    }
    
    /**
     *
     * @param ftpClient
     * @return
     */
    public static boolean ftpDisconnect(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
            return true;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return false;
    }
}
