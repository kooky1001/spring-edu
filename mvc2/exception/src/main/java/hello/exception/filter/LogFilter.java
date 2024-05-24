package hello.exception.filter;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("LogFilter init");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
		IOException,
		ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
		String requestURI = httpServletRequest.getRequestURI();

		String uuid = UUID.randomUUID().toString();

		try {
			log.info("REQUEST [{}][{}][{}]", uuid, httpServletRequest.getDispatcherType(), requestURI);
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			throw e;
		}finally {
			log.info("RESPONSE [{}][{}][{}]", uuid, httpServletRequest.getDispatcherType(), requestURI);
		}
	}

	@Override
	public void destroy() {
		log.info("LogFilter destroy");
	}
}
