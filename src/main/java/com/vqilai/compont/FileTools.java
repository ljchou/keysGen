package com.vqilai.compont;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileTools {

    @Value("${file.key}")
    private String keyUrl;

    @Value("${file.word}")
    private String wordUrl;

    public Keys readKeysLocal() {
        System.out.println("--------------------keys的地址是：" + keyUrl);
        try {
            List<String> lines = Files.readAllLines(Paths.get(keyUrl), StandardCharsets.UTF_8);
            if (CollectionUtils.isEmpty(lines)) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (StringUtils.isEmpty(line)) {
                    continue;
                }
                sb.append(line);
            }

            return JSON.parseObject(sb.toString(), Keys.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void editKeys(String num, String keys) throws Exception {
        Keys keyObj = readKeysLocal();
        Class<?> clazz = keyObj.getClass();

        Method method = clazz.getMethod("setKeys"+num, String.class);
        method.invoke(keyObj, keys);

//        Field field = clazz.getDeclaredField("key1");
//        Object object = clazz.newInstance();
//        field.setAccessible(true);
//        field.set(object, "张三");

//
//        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("setKeys" + num, clazz);
//        Method setMethod = propertyDescriptor.getWriteMethod();
//        setMethod.invoke(keyObj, keys);

        // 写入文件
        String keysJson = JSONObject.toJSONString(keyObj);
        FileWriter writer = new FileWriter(keyUrl);
        writer.write(keysJson);
        writer.flush();
        writer.close();
    }

    public String readWordsLocal() {
        System.out.println("--------------------keys的地址是：" + wordUrl);
        try {
            List<String> lines = Files.readAllLines(Paths.get(wordUrl), StandardCharsets.UTF_8);
            if (CollectionUtils.isEmpty(lines)) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (StringUtils.isEmpty(line)) {
                    continue;
                }
                sb.append(line);
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void editWords(String words) throws Exception {
        String wordsStr = readWordsLocal();
        wordsStr = wordsStr + "," + words;

        // 写入文件

        FileWriter writer = new FileWriter(wordUrl);
        writer.write(wordsStr);
        writer.flush();
        writer.close();
    }
}
