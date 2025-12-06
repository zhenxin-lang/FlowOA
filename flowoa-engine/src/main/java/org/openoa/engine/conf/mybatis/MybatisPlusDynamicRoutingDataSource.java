package org.openoa.engine.conf.mybatis;//package org.openoa.engine.conf.mybatis;
//
//import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
//import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//

//@Configuration
//public class MybatisPlusDynamicRoutingDataSource {
//    @Bean(name = "jimuofficeDynamicRoutingDatasource")
//    public DynamicRoutingDataSource dataSource(DynamicDataSourceProperties properties) {
//        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
//        dataSource.setPrimary(properties.getPrimary());
//        dataSource.setStrict(properties.getStrict());
//        dataSource.setStrategy(properties.getStrategy());
//        dataSource.setP6spy(properties.getP6spy());
//        dataSource.setSeata(properties.getSeata());
//        return dataSource;
//    }
//}
