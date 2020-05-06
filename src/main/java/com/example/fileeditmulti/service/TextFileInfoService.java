package com.example.fileeditmulti.service;

import com.example.fileeditmulti.pojo.FileInfo;

import java.util.List;

/**
 * @author yuwensong
 * @date 2020/5/2
 */
public interface TextFileInfoService {
    /**
     * insert a new file
     * @param fileInfo
     * @return
     */
    FileInfo insertFile(FileInfo fileInfo) throws Exception;

    /**
     * update a file by primary key
     * @param fileInfo
     * @return
     */
    FileInfo updateFileByPk(FileInfo fileInfo) throws Exception;

    /**
     * delete a file by primary key
     * @param fileId
     * @return
     */
    int deleteFileByPk(String fileId) throws Exception;

    /**
     * select a file by primary key
     * @param fileId
     * @return
     */
    FileInfo selectFileByPk(String fileId) throws Exception;

    /**
     * select all file in db
     * @return
     */
    List<FileInfo> selectAllFiles() throws Exception;
}
