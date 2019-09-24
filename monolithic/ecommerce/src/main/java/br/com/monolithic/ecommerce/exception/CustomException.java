package br.com.monolithic.ecommerce.exception;

import org.zalando.problem.Status;

import java.net.URI;

public class CustomException extends AlertException {

    public CustomException(String defaultMessage, Status httpStatus) {
        super(URI.create("/problem/problem-with-data-request"), defaultMessage, null, null, httpStatus);
    }

}
