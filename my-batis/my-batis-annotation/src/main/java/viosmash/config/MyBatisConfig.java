package viosmash.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import viosmash.mapper.PersonMapper;

import javax.sql.DataSource;


public class MyBatisConfig {
    public static SqlSessionFactory buildqlSessionFactory() {
        DataSource dataSource
                = new PooledDataSource(
                        "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/my_batis_example?createDatabaseIfNotExist=true",
                "root",
                "root");

        Environment environment
                = new Environment("Development", new JdbcTransactionFactory(), dataSource);

        Configuration configuration = new Configuration(environment);
        configuration.addMapper(PersonMapper.class);
        configuration.setMapUnderscoreToCamelCase(true);
        
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        return builder.build(configuration);
    }
}
