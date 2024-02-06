package hello.advanced.proxy.config.v1_proxy;

import hello.advanced.advanced.trace.logtrace.LogTrace;
import hello.advanced.proxy.app.v1.*;
import hello.advanced.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.advanced.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.advanced.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {
    @Bean
    public ProxyOrderControllerV1 orderController(LogTrace logTrace) {
        ProxyOrderControllerV1Impl controllerImpl = new ProxyOrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
    }

    @Bean
    public ProxyOrderServiceV1 orderService(LogTrace logTrace) {
        ProxyOrderServiceV1Impl serviceImpl = new ProxyOrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
    }

    @Bean
    public ProxyOrderRepositoryV1 orderRepository(LogTrace logTrace) {
        ProxyOrderRepositoryV1Impl repositoryImpl = new ProxyOrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
    }
}
