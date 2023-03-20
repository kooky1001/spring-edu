package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        printStartLine(request);
        printHeaders(request);
        printHeaderUtils(request);

        response.getWriter().write("ok!");
    }

    private void printStartLine(HttpServletRequest request) {
        System.out.println("----- Request Line ----- start -----");

        System.out.println("request.getMethod() = " + request.getMethod());
        System.out.println("request.getProtocol() = " + request.getProtocol());
        System.out.println("request.getScheme() = " + request.getScheme());
        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getQueryString() = " + request.getQueryString());
        System.out.println("request.isSecure() = " + request.isSecure());

        System.out.println("----- Request Line ----- end -----");
        System.out.println();
    }

    private void printHeaders(HttpServletRequest request) {
        System.out.println("----- Headers ----- start -----");

        /*
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + " = " + request.getHeader(headerName));
        }
        */
        
        request.getHeaderNames().asIterator().forEachRemaining(
                headerName -> System.out.println(headerName + " = " + request.getHeader(headerName))
        );

        System.out.println("----- Headers ----- end -----");
        System.out.println();
    }
    
    private void printHeaderUtils(HttpServletRequest request) {
        System.out.println("----- Header Utils ----- start -----");
        System.out.println("Host Util");
        System.out.println("request.getServerName() = " + request.getServerName());
        System.out.println("request.getServerPort() = " + request.getServerPort());
        System.out.println();

        System.out.println("Language Util");
        request.getLocales().asIterator().forEachRemaining(
                locale -> System.out.println(locale + " = " + locale)
        );
        System.out.println("request.getLocale() = " + request.getLocale());
        System.out.println();

        System.out.println("Cookie Util");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + " = " + cookie.getValue());
            }
        }
        System.out.println();

        System.out.println("Content Utils");
        System.out.println("request.getContentType() = " + request.getContentType());
        System.out.println("request.getContentLength() = " + request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());

        System.out.println("----- Header Utils ----- end -----");
        System.out.println();
    }
}
