package com.example.fileeditmulti.controller;

import com.example.fileeditmulti.common.Result;
import com.example.fileeditmulti.pojo.FileInfo;
import com.example.fileeditmulti.service.TextFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuwensong
 * @date 2020/5/2
 */
@Controller
public class FileInfoController {
    private ConcurrentHashMap<String, Lock> locks = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Thread> threads = new ConcurrentHashMap<>();
    @Autowired
    private TextFileInfoService textFileInfoService;

    @RequestMapping("/")
    public String hello(Model model) {
        return "redirect:insertFile";
    }

    @RequestMapping("/editFile")
    public String editFile(String fileId, Model model) throws Exception {
        Lock lock = locks.get(fileId);

        if (lock == null) {
            return "fileNotFound";
        }

        if (lock.tryLock()) {
            try {
                FileInfo fileInfo = textFileInfoService.selectFileByPk(fileId);
                if (fileInfo == null) {
                    return "fileNotFound";
                }
                model.addAttribute("fileInfo", fileInfo);

                return "editFile";
            } finally {
                synchronized (this) {
                    lock.unlock();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                lock.lock();
                                Thread.sleep(60 * 1000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                lock.unlock();
                            }
                        }
                    });
                    threads.put(fileId, thread);
                    thread.start();
                }

            }

        } else {
            return "fileLock";
        }


    }

    @RequestMapping("/insertFile")
    public String insertFile(String fileId, Model model) throws Exception {
        return "insertFile";
    }

    @RequestMapping("/saveFile")
    @ResponseBody
    public Result<Boolean> saveFile(@RequestBody FileInfo fileInfo) {
        try {
            if (StringUtils.isEmpty(fileInfo.getFileId())) {
                textFileInfoService.insertFile(fileInfo);
                Lock lock = new ReentrantLock();
                locks.put(fileInfo.getFileId(), lock);
            } else {
                Lock lock = locks.get(fileInfo.getFileId());

                synchronized (lock) {
                    FileInfo dbInfo = textFileInfoService.selectFileByPk(fileInfo.getFileId());
                    if (dbInfo.getVersionId().equals(fileInfo.getVersionId())) {
                        String newVersionId = UUID.randomUUID().toString().replaceAll("-", "");
                        fileInfo.setVersionId(newVersionId);
                        textFileInfoService.updateFileByPk(fileInfo);
                    } else {
                        return  Result.success(false);
                    }
                }
                Thread thread = threads.get(fileInfo.getFileId());
                thread.interrupt();
            }
            return Result.success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed(false);
        }
    }

    @RequestMapping("/fileList")
    public String fileList(Model model) throws Exception {
        List<FileInfo> fileInfos = textFileInfoService.selectAllFiles();
        model.addAttribute("fileList", fileInfos);
        return "fileList";
    }


}
