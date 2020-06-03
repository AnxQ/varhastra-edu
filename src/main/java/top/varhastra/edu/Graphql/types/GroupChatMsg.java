package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Group;

import java.util.List;

public class GroupChatMsg {
    String groupId;
    List<ChatMsg> msgs;

    public GroupChatMsg(String groupId, List<ChatMsg> msgs) {
        this.groupId = groupId;
        this.msgs = msgs;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<ChatMsg> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<ChatMsg> msgs) {
        this.msgs = msgs;
    }
}
