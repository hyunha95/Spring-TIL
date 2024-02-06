package hello.advanced.proxy.config.v1_proxy.interface_proxy;

import hello.advanced.advanced.trace.TraceStatus;
import hello.advanced.advanced.trace.logtrace.LogTrace;
import hello.advanced.proxy.app.v1.ProxyOrderServiceV1;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements ProxyOrderServiceV1 {

    private final ProxyOrderServiceV1 target;
    private final LogTrace logTrace;

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService.orderItem()");
            // target 호출
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
