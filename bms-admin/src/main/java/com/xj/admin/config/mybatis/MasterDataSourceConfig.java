/*package com.xj.admin.config.mybatis;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

*//**
 * 多数据案列
 * @author xjher
 *  master.datasource.url=jdbc:mysql://localhost:3306/bms?useUnicode=true&characterEncoding=utf8
	master.datasource.username=root
	master.datasource.password=root
	master.datasource.driverClassName=com.mysql.jdbc.Driver
	 
	## cluster 数据源配置
	cluster.datasource.url=jdbc:mysql://localhost:3306/cms?useUnicode=true&characterEncoding=utf8
	cluster.datasource.username=root
	cluster.datasource.password=root
	cluster.datasource.driverClassName=com.mysql.jdbc.Driver
 *
 *//*
@Configuration
@MapperScan("com.xj.**.mapper*")
public class MasterDataSourceConfig {
	
	 	@Value("${master.datasource.url}")
	    private String aurl;
	 
	    @Value("${master.datasource.username}")
	    private String auser;
	 
	    @Value("${master.datasource.password}")
	    private String apassword;
	 
	    @Value("${master.datasource.driverClassName}")
	    private String adriverClass;
	    
	    @Value("${cluster.datasource.url}")
	    private String burl;
	 
	    @Value("${cluster.datasource.username}")
	    private String buser;
	 
	    @Value("${cluster.datasource.password}")
	    private String bpassword;
	 
	    @Value("${cluster.datasource.driverClassName}")
	    private String bdriverClass;
	 
	    @Bean("dataSource1")
	    @Primary
	    public DataSource dataSource1() {
	        DruidDataSource dataSource = new DruidDataSource();
	        dataSource.setDriverClassName(adriverClass);
	        dataSource.setUrl(aurl);
	        dataSource.setUsername(auser);
	        dataSource.setPassword(apassword);
	        return dataSource;
	    }
	    @Bean("dataSource2")
	    public DataSource dataSource2() {
	        DruidDataSource dataSource = new DruidDataSource();
	        dataSource.setDriverClassName(bdriverClass);
	        dataSource.setUrl(burl);
	        dataSource.setUsername(buser);
	        dataSource.setPassword(bpassword);
	        return dataSource;
	    }
	
	*//**
	 * @return
	 *//*
	@Bean("dataSource")
	public DynamicDataSource dataSource(){
		DynamicDataSource ds = new DynamicDataSource();
		Map<Object,Object> dss = new HashMap<>();
		dss.put("dataSource1", dataSource1());
		dss.put("dataSource2", dataSource2());
		ds.setTargetDataSources(dss);
		ds.setDefaultTargetDataSource(dataSource1());
		return ds;
	}

	@Autowired
	private MybatisProperties properties;

	@Autowired
	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	@Autowired(required = false)
	private Interceptor[] interceptors;

	@Autowired(required = false)
	private DatabaseIdProvider databaseIdProvider;
	
	
	*//**
	 *	 mybatis-plus分页插件
	 *//*
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor page = new PaginationInterceptor();
		page.setDialectType("mysql");
		return page;
	}
	
	*//**
	 *	 mybatis-plus乐观锁插件
	 *//*
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		OptimisticLockerInterceptor oLocker = new OptimisticLockerInterceptor();
		return oLocker;
	}
	*//**
	 * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定
	 * 配置文件和mybatis-boot的配置文件同步
	 * @return
	 *//*
	@Bean
	public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
		MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
		mybatisPlus.setDataSource(dataSource());
		mybatisPlus.setVfs(SpringBootVFS.class);
		if (StringUtils.hasText(this.properties.getConfigLocation())) {
			mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
		}
		mybatisPlus.setConfiguration(properties.getConfiguration());
		if (!ObjectUtils.isEmpty(this.interceptors)) {
			mybatisPlus.setPlugins(this.interceptors);
		}
		MybatisConfiguration mc = new MybatisConfiguration();
		mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		mybatisPlus.setConfiguration(mc);
		if (this.databaseIdProvider != null) {
			mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
		}
		if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
			mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
		}
		if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
			mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
		}
		if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
			mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
		}
		//ID自增
		GlobalConfiguration global = new GlobalConfiguration(new AutoSqlInjector());
		global.setIdType(IdType.AUTO.getKey());
		global.setDbColumnUnderline(true);
		mybatisPlus.setGlobalConfig(global);
		return mybatisPlus;
	}
}
*/