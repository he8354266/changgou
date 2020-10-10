package com.changgou.file.util;

import lombok.Data;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/9/2410:07
 */
@Data
public class FastDFSFile {
    public FastDFSFile(String name, byte[] content, String ext) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.author = author;
    }
    //文件名
    private String name;
    //文件内容
    private byte[] content;
    //文件扩展名
    private String ext;
    //文件md5摘要值
    private String md5;
    //文件创作者
    private String author;
}
