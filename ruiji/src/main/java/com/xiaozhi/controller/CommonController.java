package com.xiaozhi.controller;

import com.xiaozhi.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author 20232
 */
@Api(value = "CommonController", tags = {"文件上传控制器"})
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    /**
     * 自定义存储路径，请查看yml文件中
     */
    @Value("${image-path.path}")
    private String basePath;


    @ApiOperation("上传图片文件接口")
    @PostMapping("/upload")
    public Result<String> upload(@ApiParam(name = "file", value = "图片资源") MultipartFile file) {
        //        获得原始文件名
        log.info("正在上传....." + file.getOriginalFilename());
        //file是一个临时文件，需要转存到指定位置
        //      生成特殊的uuid
        String uuidImageName = UUID.randomUUID().toString();
        //        获取文件后缀，lastIndexOf：返回指定字符最后一次出现的字符串中的索引
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));

        File fileName = new File(basePath);
        //判断文件是否存在
        if (!fileName.exists()) {
            log.info("文件夹不存在，创建文件夹");
//            创建文件夹
            fileName.mkdirs();
        }
        try {
            //临时文件,转存到指定位置,拼接字符串路径
            file.transferTo(new File(basePath.concat(uuidImageName.concat(suffix))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(uuidImageName.concat(suffix));
    }


    @ApiOperation("文件下载接口")
    @GetMapping("/download")
    public void download(@RequestParam String name, HttpServletResponse httpServletResponse) {
        try {
            //          输入流，下载指定位置的图片资源，concat拼接字符串
            FileInputStream fileInputStream = new FileInputStream(basePath.concat(name));
            //           输出流，输出到浏览器ServletOutputStream
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            //          设置内容类型
            httpServletResponse.setContentType("image/jpeg");
            int length = 0;
            //            缓存字节数组
            byte[] bytes = new byte[1024];
            //            读取文本，-1是结束位置
            while ((length = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
                //              输出流刷新
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
