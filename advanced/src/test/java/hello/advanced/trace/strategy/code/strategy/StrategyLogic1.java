package hello.advanced.trace.strategy.code.strategy;

import hello.advanced.advanced.trace.strategy.code.strategy.Strategy;

public class StrategyLogic1 implements Strategy {
    @Override
    public void call() {
        System.out.println("비즈니스 로직1 실행");
    }
}
