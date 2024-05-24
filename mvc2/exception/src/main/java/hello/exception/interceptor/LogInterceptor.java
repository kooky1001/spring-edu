package hello.exception.interceptor;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogInterceptor implements HandlerInterceptor {

	public static final String LOG_ID = "logId";
	private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		String requestURI = request.getRequestURI();
		String uuid = UUID.randomUUID().toString();
		request.setAttribute(LOG_ID, uuid);

		log.info("REQUEST [{}] [{}] [{}] [{}]", uuid, request.getDispatcherType(), requestURI, handler);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		log.info("postHandle [{}]", modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		String requestURI = request.getRequestURI();
		String logId = (String)request.getAttribute(LOG_ID);
		log.info("RESPONSE [{}] [{}] [{}]", logId, request.getDispatcherType(), requestURI);
		if (ex != null) {
			log.error("afterCompletion error!", ex);
		}
	}
}
