package hello.advanced.proxy.app.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProxyOrderControllerV3 {
    private final ProxyOrderServiceV3 proxyOrderServiceV3;

    public ProxyOrderControllerV3(ProxyOrderServiceV3 proxyOrderServiceV3) {
        this.proxyOrderServiceV3 = proxyOrderServiceV3;
    }

    @GetMapping("/proxy/v3/request")
    public String request(String itemId) {
        proxyOrderServiceV3.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/proxy/v3/no-log")
     public String noLog() {
        return "ok ";
    }
}
