package hello.advanced.proxy.app.v3;

import org.springframework.stereotype.Service;

@Service
public class ProxyOrderServiceV3 {
    private final ProxyOrderRepositoryV3 proxyOrderRepositoryV3;

    public ProxyOrderServiceV3(ProxyOrderRepositoryV3 proxyOrderRepositoryV3) {
        this.proxyOrderRepositoryV3 = proxyOrderRepositoryV3;
    }


    public void orderItem(String itemId) {
        proxyOrderRepositoryV3.save(itemId);
    }
}
