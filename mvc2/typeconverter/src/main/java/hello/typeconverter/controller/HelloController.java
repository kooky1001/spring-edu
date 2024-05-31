package hello.typeconverter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.typeconverter.type.IpPort;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

	@GetMapping("/hello-v1")
	public String helloV1(HttpServletRequest request) {
		String data = request.getParameter("data"); //문자 타입으로 조회
		Integer intValue = Integer.valueOf(data); //숫자 타입으로 변경
		System.out.println("intValue = " + intValue);
		return "Hello World";
	}

	@GetMapping("/hello-v2")
	public String helloV2(@RequestParam(value = "data", required = false) Integer data) {
		System.out.println("data = " + data);
		return "Hello World";
	}

	@GetMapping("/ip-port")
	public String ipPort(@RequestParam("ipPort") IpPort ipPort) {
		System.out.println("ipPort.getIp() = " + ipPort.getIp());
		System.out.println("ipPort.getPort() = " + ipPort.getPort());
		return "Hello World";
	}

}
