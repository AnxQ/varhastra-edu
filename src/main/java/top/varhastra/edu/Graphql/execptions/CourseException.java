package top.varhastra.edu.Graphql.execptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Map;

public class CourseException extends BaseException{
    public enum Type {
        COURSE_NOT_FIND,
        FIELD_INVALID,
        CONDITION_INVALID,
        PERMISSION_DENIED,
        DUPLICATED_ENROLL,
        USER_NOT_INSIDE
    }

    public CourseException(CourseException.Type t) { super(t.name()); }
    public CourseException(CourseException.Type t, String invalidField) { super(t.name(), invalidField); }

}
