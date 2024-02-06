package hello.advanced.trace.strategy.code.strategy;

import hello.advanced.advanced.trace.strategy.code.strategy.ContextV2;
import hello.advanced.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ContextV2Test {

    /**
     * 전략 패턴 사용
     */
    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

}