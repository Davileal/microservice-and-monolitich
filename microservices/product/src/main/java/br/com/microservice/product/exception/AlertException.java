package br.com.microservice.product.exception;

import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class AlertException extends ThrowableProblem {

    private final String entityName;
    private final String errorKey;
    private final URI type;
    private final String title;
    private final transient StatusType status;
    private final transient Map<String, Object> parameters;

    public AlertException(URI type, String defaultMessage, String entityName, String errorKey, StatusType status) {
        this.type = type;
        this.title = defaultMessage;
        this.status = status;
        this.parameters = getAlertParameters(entityName, errorKey);
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }

    public final String getEntityName() {
        return entityName;
    }

    public final String getErrorKey() {
        return errorKey;
    }

    @Override
    public URI getType() {
        return type;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public StatusType getStatus() {
        return status;
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }
}
