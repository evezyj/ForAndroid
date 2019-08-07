package springbatch.ItemReader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ItemReaderDbDemo {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public DataSource dataSource;
    @Autowired
    @Qualifier("DbJdbcWriter")
    public ItemWriter<? super User> dbJdbcWriter;
    /*@Bean
    public Job ItemReaderDbJob(){
        return jobBuilderFactory.get("ItemReaderDbJob").start(itemReaderDbStep()).build();
    }*/

    @Bean
    public Job ItemReaderDbJob1(){
        return jobBuilderFactory.get("ItemReaderDbJob1").start(itemReaderDbStep()).build();
    }
    @Bean
    public Step itemReaderDbStep(){
        return stepBuilderFactory.get("itemReaderDbStep")
                .<User, User>chunk(2)
                .reader(dbJdbcReader())
                .writer(dbJdbcWriter)
                .build();
    }
    @Bean
    @StepScope  //只限step範圍   JdbcPagingItemReader从数据库中读取数据
    public JdbcPagingItemReader<User> dbJdbcReader(){
        JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<User>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(2);
        //把读取到的记录转换成user对象
        reader.setRowMapper(new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setAge(rs.getInt(4));
                return user;
            }
        });
        //指定sql语句
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        provider.setSelectClause("id,username,password,age");
        provider.setFromClause("user");
        //排序 1 为初始大小
        Map<String, Order> sort = new HashMap<String, Order>(1);
        sort.put("id",Order.ASCENDING);
        provider.setSortKeys(sort);

        reader.setQueryProvider(provider);
        return reader;
    }
}
