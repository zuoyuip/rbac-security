package com.supergenius.generator;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zuoyu
 * @project : management-security
 * @description : 代码生成器
 * @date : 2019-11-22 11:17
 **/
public class CodeGenerator {
    
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * 全局配置
     */
    private static GlobalConfig globalConfig(){
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor("zuoyu");
        globalConfig.setOutputDir(PROJECT_PATH + "/src/main/java/");
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setIdType(IdType.AUTO);
        globalConfig.setSwagger2(true);
//    是否覆盖同名文件
        globalConfig.setFileOverride(false);
//    活动记录，一种领域模型
        globalConfig.setActiveRecord(true);
//    二级缓存
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(false);
//    是否打开输出目录
        globalConfig.setOpen(true);
        return globalConfig;
    }

    /**
     * 数据源
     */
    private static DataSourceConfig dataSourceConfig(){
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/security?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        return dataSourceConfig;
    }

    /**
     * 包配置
     */
    private static PackageConfig packageConfig(){
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.supergenius");
        packageConfig.setEntity("model");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setController(null);
        return packageConfig;
    }

    /**
     * 自定义配置
     */
    private static InjectionConfig injectionConfig(){
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        // 自定义配置会被优先输出
        fileOutConfigs.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return PROJECT_PATH + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        injectionConfig.setFileOutConfigList(fileOutConfigs);
        return injectionConfig;
    }

    /**
     * 配置模板
     */
    private static TemplateConfig templateConfig(){
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别

        templateConfig.setXml(null);
        return templateConfig;
    }

    /**
     * 策略配置
     */
    private static StrategyConfig strategyConfig(){
        StrategyConfig strategyConfig = new StrategyConfig();
//    大写命名
        strategyConfig.setCapitalMode(true);
        strategyConfig.setSkipView(false);
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
//    实体注解
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
//    Lombok插件
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setEntityBuilderModel(true);
        strategyConfig.setTablePrefix("security_");
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setControllerMappingHyphenStyle(true);
//    表
        strategyConfig.setInclude(
                "security_role",
                "security_role_authority",
                "security_user",
                "security_user_role",
                "security_authority"
        );
        return strategyConfig;
    }

    /**
     * 代码生成
     */
    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
//    选择模板引擎
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.setGlobalConfig(globalConfig());
        autoGenerator.setDataSource(dataSourceConfig());
        autoGenerator.setPackageInfo(packageConfig());
        autoGenerator.setCfg(injectionConfig());
        autoGenerator.setTemplate(templateConfig());
        autoGenerator.setStrategy(strategyConfig());
        autoGenerator.execute();
    }
}
