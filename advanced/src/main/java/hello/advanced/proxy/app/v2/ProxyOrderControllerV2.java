package hello.advanced.proxy.app.v2;

import hello.advanced.proxy.app.v1.ProxyOrderServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping
@ResponseBody
public class ProxyOrderControllerV2 {
    private final ProxyOrderServiceV2 proxyOrderServiceV2;

    public ProxyOrderControllerV2(ProxyOrderServiceV2 proxyOrderServiceV2) {
        this.proxyOrderServiceV2 = proxyOrderServiceV2;
    }

    @GetMapping("/proxy/v2/request")
    public String request(String itemId) {
        proxyOrderServiceV2.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/proxy/v2/no-log")
     public String noLog() {
        return "ok ";
    }
}
