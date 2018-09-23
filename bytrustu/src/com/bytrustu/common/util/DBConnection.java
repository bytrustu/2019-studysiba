package com.bytrustu.common.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {

	public static Connection getConnection() throws SQLException, NamingException, ClassNotFoundException{
		Context context = new InitialContext();
		
		//context의 lookup메서드를 이용해서 "java:comp/env/jdbc/Oracle11g"에 해당하는 객체를 찾아서 dataSource에 삽입

		DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
		//getConnection메서드를 이용해서 커넥션 풀로 부터 커넥션 객체를 얻어내어 connection변수에 저장
		Connection connection = dataSource.getConnection();
		return connection;
	}
	
	
}
