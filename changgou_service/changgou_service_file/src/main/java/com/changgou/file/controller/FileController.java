package com.changgou.file.controller;

import com.changgou.pojo.Result;
import com.changgou.pojo.StatusCode;
import com.changgou.file.util.FastDFSClient;
import com.changgou.file.util.FastDFSFile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/9/2410:05
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file)  {
        System.out.println(file);
        try {
            if (file == null) {
                throw new RuntimeException("文件不存在");
            }
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isEmpty(originalFilename)) {
                throw new RuntimeException("文件不存在");
            }
            //获取扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //获取文件内容
            byte[] content = file.getBytes();
            //创建文件上传的封装的实体类
            FastDFSFile fastDFSFile = new FastDFSFile(originalFilename, content, extName);
            //基于工具类上传
            String[] uploadResult = FastDFSClient.upload(fastDFSFile);
            //封装返回结果
            String url = FastDFSClient.getTrackerUrl() + uploadResult[0] + "/" + uploadResult[1];
            return new Result(true, StatusCode.OK, "上传成功", url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "上传失败");

        }


    }
}
