package com.vqilai.compont;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ManualService {

    @Autowired
    private FileTools fileTools;

    private static Map<String, String> KEYS_HOLDER = new ConcurrentHashMap<>();

    private static String WORDS = "";

    public static final int MAX_BOT_NUM = 20;

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR = new ScheduledThreadPoolExecutor(3, r -> {
        Thread thread = new Thread(r);
        thread.setName("SCHEDULED_EXECUTOR_THREAD-POOL");
        return thread;
    });

    public void scheduled() {
        SCHEDULED_EXECUTOR.scheduleAtFixedRate(() -> {
            try {
                processKeys();
                processWords();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    private void processWords() {
        WORDS = fileTools.readWordsLocal();
    }

    private void processKeys() {
        Keys keys = fileTools.readKeysLocal();
        Keys.set(KEYS_HOLDER, keys);
    }

    public String execute(String num) {
        if (CollectionUtils.isEmpty(KEYS_HOLDER)) {
            processKeys();
        }

        if (StringUtils.isEmpty(WORDS)) {
            processWords();
        }

        Entity entity = new Entity();
        entity.setWords(WORDS);
        entity.setKeys(KEYS_HOLDER.get(num));

        return JSON.toJSONString(entity);
    }

    public synchronized void edit(String num, String keys) throws Exception {
        fileTools.editKeys(num, keys);
    }

    public synchronized void editWords(String words) throws Exception {
        fileTools.editWords(words);
    }

}
