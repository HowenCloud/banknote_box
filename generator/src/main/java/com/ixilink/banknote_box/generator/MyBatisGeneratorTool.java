package com.ixilink.banknote_box.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyBatisGeneratorTool {
    public static void main(String[] args) throws Exception {

        try {
            MyBatisGeneratorTool generatorSqlmap = new MyBatisGeneratorTool();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void generator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        String genCfg = "myGeneratorConfig.xml";
        System.out.println(MyBatisGeneratorTool.class.getResource(genCfg).getFile());
        File configFile = new File(MyBatisGeneratorTool.class.getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException | XMLParserException e) {
            System.err.println("报错了!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;
        try {
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        } catch (InvalidConfigurationException e) {
            System.err.println("报错了!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
        try {
            assert myBatisGenerator != null;
            myBatisGenerator.generate(null);
        } catch (SQLException | IOException | InterruptedException e) {
            System.err.println("报错了!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }
}