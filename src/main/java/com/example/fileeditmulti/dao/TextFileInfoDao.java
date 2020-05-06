package com.example.fileeditmulti.dao;

import com.example.fileeditmulti.pojo.FileInfo;

import java.util.List;

/**
 * @author yuwensong
 * @date 2020/5/2
 */
public interface TextFileInfoDao {
    /**
     * insert a new file
     * @param fileInfo
     * @return
     */
    int insertFile(FileInfo fileInfo);

    /**
     * update a file by primary key
     * @param fileInfo
     * @return
     */
    int updateFileByPk(FileInfo fileInfo);

    /**
     * delete a file by primary key
     * @param fileId
     * @return
     */
    int deleteFileByPk(String fileId);

    /**
     * select a file by primary key
     * @param fileId
     * @return
     */
    FileInfo selectFileByPk(String fileId);

    /**
     * select all file in db
     * @return
     */
    List<FileInfo> selectAllFiles();
}
