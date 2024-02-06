package hello.advanced.proxy.app.v1;

public class ProxyOrderControllerV1Impl implements ProxyOrderControllerV1 {

    private final ProxyOrderServiceV1 proxyOrderServiceV1;

    public ProxyOrderControllerV1Impl(ProxyOrderServiceV1 proxyOrderServiceV1) {
        this.proxyOrderServiceV1 = proxyOrderServiceV1;
    }

    @Override
    public String request(String itemId) {
        proxyOrderServiceV1.orderItem(itemId);
        return "ok";
    }

    @Override
    public String noLog() {
        return "ok ";
    }
}
