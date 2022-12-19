package com.bsoftgroup.springcloudmssecurity.config;

import com.bsoftgroup.springcloudmssecurity.bean.ConnectionData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        ResponseEntity<ConnectionData> responseEntity = new RestTemplate().getForEntity("http://localhost:8084/mproperties/connection-data", ConnectionData.class);
        ConnectionData response = responseEntity.getBody();

        if (Objects.nonNull(response)) {
            dataSource.setDriverClassName(response.driverClassname());
            dataSource.setUrl(response.url());
            dataSource.setUsername(response.username());
            dataSource.setPassword(response.password());
            return dataSource;
        }
        return null;
    }

}
