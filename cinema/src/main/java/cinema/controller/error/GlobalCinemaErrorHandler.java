package cinema.controller.error;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GlobalCinemaErrorHandler {

@ExceptionHandler(NoSuchElementException.class)
@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, String> handleNoSuchElementException(NoSuchElementException ex)  {
	log.error("Exception: {} ", ex.toString());
	return Map.of("message", ex.toString()); 
	
}
}
	
//	return buildExceptionMessage(ex, webRequest);
//}
//private ExceptionMessage buildExceptionMessage(NoSuchElementException ex, WebRequest webRequest) {
//	String message = ex.toString();
//	if(webRequest instanceof ServletWebRequest swr) {
//		log.error("Exception: {}", ex.toString());
//	} else {
//		log.error("Exception: ", ex);
//	}
//	ExceptionMessage excMsg = new ExceptionMessage();
//	
//	excMsg.setMessage(message);
//	
//	return excMsg;
//}

