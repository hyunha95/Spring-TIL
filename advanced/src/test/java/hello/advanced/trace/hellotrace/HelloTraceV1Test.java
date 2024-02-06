package hello.advanced.trace.hellotrace;

import hello.advanced.advanced.trace.TraceStatus;
import hello.advanced.advanced.trace.hellotrace.HelloTraceV1;
import org.junit.jupiter.api.Test;

class HelloTraceV1Test {

    @Test
    void begin_end() throws Exception {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception() throws Exception {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus statue = trace.begin("hello");
        trace.exception(statue, new IllegalStateException());
    }

}