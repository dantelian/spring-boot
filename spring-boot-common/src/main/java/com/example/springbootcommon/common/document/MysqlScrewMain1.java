package com.example.springbootcommon.common.document;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;

public class MysqlScrewMain1 {
    private static final String DB_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://10.150.1.15:3306/gxhj_zhgl_dev?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "zzyt@123";

    private static final String FILE_OUTPUT_DIR = "C:\\Users\\ddd\\Desktop\\java\\";

    public static void main(String[] args) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(DB_CLASS_NAME);
        hikariConfig.setJdbcUrl(DB_URL);
        hikariConfig.setUsername(DB_USERNAME);
        hikariConfig.setPassword(DB_PASSWORD);

        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);

        EngineConfig engineConfig = EngineConfig.builder()
                .fileOutputDir(FILE_OUTPUT_DIR)
                .openOutputDir(true)
                .fileType(EngineFileType.WORD)
                .produceType(EngineTemplateType.freemarker)
                .fileName("name").build();

        ArrayList<String> ignoreTableName = new ArrayList<>();
        ignoreTableName.add("t_us");

        ArrayList<String> ignorePrefix = new ArrayList<>();
        ignorePrefix.add("test_");

        ArrayList<String> ignoreSuffix = new ArrayList<>();
        ignoreSuffix.add("_test");

        ProcessConfig processConfig = ProcessConfig.builder()
                .designatedTableName(new ArrayList<>())
                .designatedTablePrefix(new ArrayList<>())
                .designatedTableSuffix(new ArrayList<>())
                .ignoreTableName(ignoreTableName)
                .ignoreTablePrefix(ignorePrefix)
                .ignoreTableSuffix(ignoreSuffix)
                .build();

        Configuration config = Configuration.builder()
                .version("1.0.0")
                .description("desc")
                .dataSource(dataSource)
                .engineConfig(engineConfig)
                .produceConfig(processConfig)
                .build();

        new DocumentationExecute(config).execute();
    }

}
