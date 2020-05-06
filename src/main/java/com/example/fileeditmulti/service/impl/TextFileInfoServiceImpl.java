package com.example.fileeditmulti.service.impl;

import com.example.fileeditmulti.dao.TextFileInfoDao;
import com.example.fileeditmulti.pojo.FileInfo;
import com.example.fileeditmulti.service.TextFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author yuwensong
 * @date 2020/5/2
 */
@Service
public class TextFileInfoServiceImpl implements TextFileInfoService {
    @Autowired
    private TextFileInfoDao textFileInfoDao;

    @Override
    public FileInfo insertFile(FileInfo fileInfo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String fileSaveName = UUID.randomUUID().toString().replaceAll("-", "");
        String versionId = UUID.randomUUID().toString().replaceAll("-", "");
        fileInfo.setVersionId(versionId);
        String filePath = sdf.format(now) + "/" + fileSaveName;
        fileInfo.setFilePath(filePath);
        String path = System.getProperty("user.dir") + "/" + sdf.format(now);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileSaveName);
        if (!file.exists()) {
            System.out.println(file.toString());
            file.createNewFile();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(fileInfo.getFileContent().getBytes("utf-8"));
            fileOutputStream.flush();
        }
        textFileInfoDao.insertFile(fileInfo);
        return fileInfo;
    }

    @Override
    public FileInfo updateFileByPk(FileInfo fileInfo) throws Exception {
        String path = System.getProperty("user.dir") + "/" + fileInfo.getFilePath();
        File file = new File(path);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(fileInfo.getFileContent().getBytes("utf-8"));
            fileOutputStream.flush();
        }
        textFileInfoDao.updateFileByPk(fileInfo);
        return fileInfo;
    }

    @Override
    public int deleteFileByPk(String fileId) throws Exception {
        return textFileInfoDao.deleteFileByPk(fileId);
    }

    @Override
    public FileInfo selectFileByPk(String fileId) throws Exception {
        FileInfo fileInfo = textFileInfoDao.selectFileByPk(fileId);
        if (fileInfo != null) {
            String path = System.getProperty("user.dir") + "/" + fileInfo.getFilePath();
            File file = new File(path);

            StringBuilder sb;
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "utf-8"))) {
                String line;
                sb = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            }
            fileInfo.setFileContent(sb.toString());

        } else {
            fileInfo = new FileInfo();
        }
        return fileInfo;
    }

    @Override
    public List<FileInfo> selectAllFiles() throws Exception {
        return textFileInfoDao.selectAllFiles();
    }
}
