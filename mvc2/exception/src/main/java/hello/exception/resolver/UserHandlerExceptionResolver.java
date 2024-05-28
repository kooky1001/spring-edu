package hello.exception.resolver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.exception.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) {

		try {
			if (ex instanceof UserException) {
				log.info("UserException resolver to 400");
				String acceptHeader = request.getHeader("accept");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

				if (acceptHeader != null && acceptHeader.contains("application/json")) {
					Map<String, Object> errorResult = new HashMap<>();
					errorResult.put("ex", ex.getClass());
					errorResult.put("message", ex.getMessage());
					String result = objectMapper.writeValueAsString(errorResult);

					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(result);
					return new ModelAndView();
				} else {
					// TEXT/HTML
					return new ModelAndView("error/500");
				}
			}
		} catch (Exception e) {
			log.error("resolver ex", e);
		}

		return null;
	}
}
