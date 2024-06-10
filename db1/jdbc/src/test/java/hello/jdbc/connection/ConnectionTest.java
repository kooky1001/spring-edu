package hello.jdbc.connection;

import static hello.jdbc.connection.ConnectionConst.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ConnectionTest {

	@Test
	void driverManager() throws SQLException {
		Connection conn1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Connection conn2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		log.info("conn1: {}, class: {}", conn1, conn1.getClass());
		log.info("conn2: {}, class: {}", conn2, conn2.getClass());
	}

	@Test
	void dataSourceDriverManager() throws SQLException {
		//DriverManagerDataSource - 항상 새로운 커넥션 획득
		DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		useDataSource(dataSource);
	}

	@Test
	void dataSourceConnectionPool() throws SQLException, InterruptedException {
		//커넥션 풀링: HikariProxyConnection(Proxy -> JdbcConnection(Target)
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWORD);
		dataSource.setMaximumPoolSize(10);
		dataSource.setPoolName("myPool");

		useDataSource(dataSource);
		Thread.sleep(1000);
	}

	private void useDataSource(DataSource dataSource) throws SQLException {
		Connection conn1 = dataSource.getConnection();
		Connection conn2 = dataSource.getConnection();
		log.info("conn1: {}, class: {}", conn1, conn1.getClass());
		log.info("conn2: {}, class: {}", conn2, conn2.getClass());
	}

}
