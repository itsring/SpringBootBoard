package com.bitc.board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
/* ���� ������ �ڹ� Ŭ���� ����� �ҽ��ڵ�� �����ϵ��� ���ִ� ������̼��̴�.
 * ������ ���� �������� ���� ������ ������ �� ���� */
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	///////////////////hikari Cp�� �̿��� ������ ���̽� ���� ////////////////
	@Bean
	/*�������Ͽ��� ����� �Ӽ��� �����ϱ� ���� ������̼� 
	 * prefix�� ������ ������ ������ �ִ� �Ӽ��� �����ͼ� ���*/
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	
	@Bean
	public DataSource dataSource() throws Exception{
		/*������ ������ ������ ������ ���� �����ͺ��̽��� ���� */
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	//////////////////////mybatis ����///////////////////////////
	/*mybatis : ��ü ���� ����� �ڹ��� ������ �����ͺ��̽� ���α׷����� ���� ���� �� �� �ֵ��� �����ִ� ������ ���̽� �����ӿ�ũ(ORM)
	���� : ������ ����, ���̳����� ������ ����, ���α׷� �ҽ��ڵ�� SQL ������ �и��Ͽ� �ҽ��ڵ��� ���Ἲ �� ������������ ���
	ORM : Object Relational Mapping�� ���Ӹ�, ��ü�� ������ �����ͺ��̽��� �����͸� �ڵ����� ����(����)�ϴ� �����ӿ�ũ
	- ��ü���� Ʈ�α׷����� Ŭ������ ����ϰ�, ������ �����ͺ��̽��� ���̺��� ����ϱ� ������ ��ü�𵨰� ������ �����ͺ��̽� �𵨰��� ����ġ�� ������
	- ORM�� ���ؼ� ��ü�� �𵨰��� ����ġ�� �ذ��ϴµ� ����� 
	- ��ü�� ���ؼ� ���������� �����ͺ��̽��� ��Ʈ�� �� �� ���� 
	- ��� ���α׷����� �����ͺ��̽����� �������  ����ϴ� ����� ��� �����ؼ� ����� �� ���� */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		/* mybatis ��� ���� */
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		/* mybatis���� ����ϴ� ������ ���̽� ���� */
		sqlSessionFactoryBean.setDataSource(dataSource);
		/* mybatis�� �ڹ�Ŭ������ ������ xml ���� ��ġ ���� 
		 * classpath : src/main/resources ������ �ǹ���*/ 
//		/mapper/**/ - resources ���� �Ʒ��� mapper���� �ؿ� �ִ� ��� ����(**)
// /sql-*.xml - �̸��� sql-�� �����ϰ� Ȯ���ڰ� xml�� �������		
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		/* mybatis �߰� ���� */
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		/* ���� mybatis�� ����� */
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	/*������ ���� ���Ͽ��� mybatis ���� �Ӽ� ��������
	 * mybatis���� ��� */
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
}





