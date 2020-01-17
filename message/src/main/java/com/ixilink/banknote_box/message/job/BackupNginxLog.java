package com.ixilink.banknote_box.message.job;

import com.ixilink.banknote_box.common.util.CmdUtil;
import com.ixilink.banknote_box.common.util.DateFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.Date;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-12-02 15:54
 */
@Slf4j
@Configuration
@EnableScheduling
public class BackupNginxLog {

    @Scheduled(cron = "59 59 23 * * ?")
    public void sc1() {
        String packages = DateFormatUtil.dateToStr(new Date(), "yyyy\\M\\d");
        String path = System.getProperty("user.dir");
        String filePath = path+"\\nginx";
        File pack = new File(filePath,"logs\\"+packages);
        if (!pack.exists()){
            if (!pack.mkdirs()){
                log.error("备份日志创建文件夹失败");
            }
        }
        CmdUtil.executeLocalCmd("move "+filePath+"\\logs\\access.log "+pack.getPath(),new File(filePath));
        CmdUtil.executeLocalCmd("move "+filePath+"\\logs\\error.log "+pack.getPath(),new File(filePath));
        CmdUtil.executeLocalCmd("nginx -s reopen",new File(filePath));
    }

}
