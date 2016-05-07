package ar.edu.itba.services;


import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.interfaces.TaskDao;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProjectServiceImplTest {

//	@Autowired
	private ProjectService ps;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	TaskDao dao;
	
	@Test
	public void test(){
		System.out.println(dao == null);
		System.out.println("hola gator");
		System.out.println(ds == null);
		//System.out.println(ps != null);
	}
}
