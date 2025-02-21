package com.example.wedlessInvite.common.template;

import com.example.wedlessInvite.common.logtrace.LogTrace;
import com.example.wedlessInvite.common.logtrace.TraceStatus;

import java.io.IOException;

public abstract class AbstractLogTraceTemplate<T> { //제네릭 타입 T를 사용하여 다양한 타입의 반환값을 처리

    private final LogTrace trace;

    public AbstractLogTraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message) {

        TraceStatus status = null;
        try {
            status = trace.begin(message);
            //로직 호출
            T result = call();

            trace.end(status);
            return result;
        } catch (IOException e) {
            trace.exception(status, e);  // 예외를 로그로 기록
            throw new RuntimeException("IOException occurred during execution", e); // 런타임 예외로 감싸서 던짐
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call() throws IOException;
}
