package top.varhastra.edu.Graphql.execptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BaseException
        extends RuntimeException
        implements GraphQLError {
    private String invalidField = null;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, String invalidField) {
        super(msg);
        this.invalidField = invalidField;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        if (invalidField != null)
            return Collections.singletonMap("invalidField", invalidField);
        else
            return null;
    }
}
