package top.varhastra.edu.Graphql.execptions;

public class GroupException extends BaseException{
    public enum Type {
        GROUP_NOT_FIND,
        CONDITION_INVALID,
        PERMISSION_DENIED,

        USER_EXIST,
        USER_NOT_INSIDE


    }

    public GroupException(GroupException.Type t) { super(t.name()); }
    public GroupException(GroupException.Type t, String invalidField) { super(t.name(), invalidField); }
}
