package hello.advanced.proxy.app.v1;

public class ProxyOrderServiceV1Impl implements ProxyOrderServiceV1 {

    private final ProxyOrderRepositoryV1 proxyOrderRepositoryV1;

    public ProxyOrderServiceV1Impl(ProxyOrderRepositoryV1 proxyOrderRepositoryV1) {
        this.proxyOrderRepositoryV1 = proxyOrderRepositoryV1;
    }

    @Override
    public void orderItem(String itemId) {
        proxyOrderRepositoryV1.save(itemId);
    }
}
