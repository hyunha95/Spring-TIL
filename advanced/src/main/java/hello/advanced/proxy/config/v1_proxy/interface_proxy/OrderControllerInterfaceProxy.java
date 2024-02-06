package hello.advanced.proxy.config.v1_proxy.interface_proxy;

import hello.advanced.advanced.app.v1.OrderControllerV1;
import hello.advanced.advanced.trace.TraceStatus;
import hello.advanced.advanced.trace.logtrace.LogTrace;
import hello.advanced.proxy.app.v1.ProxyOrderControllerV1;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements ProxyOrderControllerV1 {

    private final ProxyOrderControllerV1 target;
    private final LogTrace logTrace;

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderController.request()");
            // target 호출
            String result = target.request(itemId);
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
}
