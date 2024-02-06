package hello.advanced.trace.logtrace;

import hello.advanced.advanced.trace.TraceStatus;
import hello.advanced.advanced.trace.logtrace.FieldLogTrace;
import org.junit.jupiter.api.Test;

class FieldLogTraceTest {

    FieldLogTrace trace = new FieldLogTrace();

    @Test
    void begin_end_level2() throws Exception {
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.end(status2);
        trace.end(status1);
    }
}