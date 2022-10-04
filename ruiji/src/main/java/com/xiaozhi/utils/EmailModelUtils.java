package com.xiaozhi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

/**
 * @author 20232
 */

@Slf4j
public class EmailModelUtils {


    /**
     * 邮件的html模板文件
     */
    private static final String EMAIL_MODEL_PATH = "email/email.html";


    public static String getEmailModel(String code) throws IOException {
        log.warn("path:{}", EMAIL_MODEL_PATH);

        //        加载资源
        Resource classPathResource = new ClassPathResource(EMAIL_MODEL_PATH);
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuffer = new StringBuilder();
        String line = null;


        try {
            inputStream = classPathResource.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            //按行读取文件
            while ((line = bufferedReader.readLine()) != null) {
                // 字符串缓存，拼接
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }

        }

        //String msg = "{0}{1}{2}{3}{4}{5}{6}{7}{8}";
        //Object [] array = new Object[]{"A","B","C","D","E","F","G","H","I",};
        //String value = MessageFormat.format(msg, array);
        //System.out.println(value);
        // 输出：ABCDEFGHI
        // 解析html文件，解析到{0}，将code写入
        return MessageFormat.format(stringBuffer.toString(), code);
    }
}
