package hello.advanced.proxy.app.v1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RequestMapping // 스프링은 @Controller 또는 @RequestMapping이 있어야 스프링 컨테이너로 인식
public interface ProxyOrderControllerV1 {

    @GetMapping("/proxy/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/proxy/v1/no-log")
    String noLog();

}
