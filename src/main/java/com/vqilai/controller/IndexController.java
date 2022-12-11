package com.vqilai.controller;

import com.vqilai.compont.ManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private ManualService manualService;

    public static final String TOKEN = "";

    @RequestMapping("/keys")
    @ResponseBody
    public String getKeys(String num) {
        return manualService.execute(num);
    }

    @RequestMapping("/genKeys")
    @ResponseBody
    public String setKeys(String num, String keys, String token) {
        if (!TOKEN.equals(token)) {
            return "密钥不正确，无法修改";
        }

        try {
            manualService.edit(num, keys);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return "修改成功";
    }

    @RequestMapping("/appendWords")
    @ResponseBody
    public String setMinGan(String words, String token) {
        if (!TOKEN.equals(token)) {
            return "密钥不正确，无法修改";
        }
        try {
            manualService.editWords(words);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return "修改成功";
    }
}
