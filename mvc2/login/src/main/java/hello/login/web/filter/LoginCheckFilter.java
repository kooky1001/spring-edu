package hello.login.web.filter;

import java.io.IOException;

import org.springframework.util.PatternMatchUtils;

import hello.login.web.Constants;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckFilter implements Filter {

	private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String requestURI = httpServletRequest.getRequestURI();

		HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;

		try {
			log.info("인증 체크 필터 시작 {}", requestURI);

			if (isLoginCheckPath(requestURI)) {
				log.info("인증 체크 로직 실행 {}", requestURI);
				HttpSession session = httpServletRequest.getSession(false);
				if (session == null || session.getAttribute(Constants.LOGIN_MEMBER) == null) {
					log.info("미인증 사용자 요청 {}", requestURI);
					//로그인으로 redirect
					httpServletResponse.sendRedirect("/login?redirectURL=" + requestURI);
					return; // 미인증 사용자는 다음으로 진행하지 않고 끝.
				}
			}

			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			throw e; // 예외로깅처리 가능하지만, 톰캣까지 예외를 보내주어야함
		} finally {
			log.info("인증 체크 필터 종료 {}", requestURI);
		}
	}

	/**
	 * 화이트 리스트의 경우 인증체크X
	 * @param requestURI
	 */
	private boolean isLoginCheckPath(String requestURI) {
		return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
	}
}
