package top.varhastra.edu.Graphql.execptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import top.varhastra.edu.Graphql.types.BaseResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserException extends BaseException {
    public enum Type {
        USER_NOT_FIND,
        PERMISSION_DENIED,
        FIELD_INVALID,
        CONDITION_INVALID,
        BAD_CREDENTIAL
    }

    public UserException(Type t) { super(t.name()); }
    public UserException(Type t, String invalidField) { super(t.name(), invalidField); }
}
