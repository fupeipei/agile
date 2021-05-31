package com.yusys.agile.issue.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;



@Slf4j
public class SqlLoadTest {
    @Test
    public void testSql() {
        log.info("test");
    }

//    @Test
//    public void testSqlFromFile() {
//        try {
//            log.info(new File(".").getAbsolutePath());
//            //this.execute("src\\test\\java\\com\\yusys\\cicd\\component\\server\\components\\sql\\sqlFile.sql");
//            this.execute("classpath:/sql/sqlFileForUnitTest.sql");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        log.info("test");
//    }


    /*
     * 传入文件执行sql语句
     *
     *  */
    public static void execute(String sqlFile,DataSource dataSource,ResourceLoader resourceLoader)  {
        Statement stmt = null;
        List<String> sqlList = new ArrayList<String>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            try {
                //sqlList = loadSql(sqlFile);
                sqlList=loadSql2(sqlFile,resourceLoader);
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                for (String sql : sqlList) {
                    System.out.println(sql);
                    stmt.addBatch(sql);
                }
                int[] rows = stmt.executeBatch();
                log.info(" Row count: " + Arrays.toString(rows));
                conn.commit();
                System.out.println(" 数据更新成功 ");
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
            } finally {
                stmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ;


    }



    private static List<String> loadSql2(String sqlFile,ResourceLoader resourceLoader) throws Exception {
        List<String> sqlList = new ArrayList<String>();
        /*
         *  读取文件的内容并写道StringBuffer中去
         *   */
        //InputStream sqlFileIn = new FileInputStream(sqlFile);


        Resource resource = resourceLoader.getResource(sqlFile);
        InputStream sqlFileIn = resource.getInputStream(); // <-- this is the difference


        //InputStream sqlFileIn = getClass().getResourceAsStream(sqlFile);

        StringBuffer sqlSb = new StringBuffer();
        byte[] buff = new byte[sqlFileIn.available()];
        int byteRead = 0;
        while ((byteRead = sqlFileIn.read(buff)) != -1) {
            sqlSb.append(new String(buff, 0, byteRead));
        }

        List<String> sql = getSql(sqlSb.toString());


        return sql;

    }


    private static List<String> getSql(String sql) {
        String s = sql;
        s = s.replaceAll("\r\n", "\r");
        s = s.replaceAll("\r", "\n");
        List<String> ret = new ArrayList<String>();
        String[] sqlarry = s.split(";"); // 用;把所有的语句都分开成一个个单独的句子
        sqlarry = filter(sqlarry);
        ret = Arrays.asList(sqlarry);
        return ret;
    }

    private static String[] filter(String[] ss) {
        List<String> strs = new ArrayList<String>();
        for (String s : ss) {
            s=StringUtils.trimToEmpty(s);
            if (s != null && !s.equals("")) {
                strs.add(s);
            }
        }
        String[] result = new String[strs.size()];
        for (int i = 0; i < strs.size(); i++) {
            result[i] = StringUtils.trimToEmpty(strs.get(i).toString());
        }
        return result;
    }

}