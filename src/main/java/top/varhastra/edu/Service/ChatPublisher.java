package top.varhastra.edu.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.*;
import top.varhastra.edu.Graphql.types.ChatMsg;
import top.varhastra.edu.Graphql.types.ChatMsgInput;
import top.varhastra.edu.Graphql.types.GroupChatMsg;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
public class ChatPublisher {

    private final Logger log = LoggerFactory.getLogger(ChatPublisher.class);
    private final ReplayProcessor<GroupChatMsg> chatSource;

    ChatPublisher() {
        chatSource = ReplayProcessor.createTimeout(Duration.ofHours(2));
    }

    public Flux<GroupChatMsg> getPublisher(List<String> groupIds) {
        return chatSource.filter(groupChatMsg -> groupIds.contains(groupChatMsg.getGroupId()));
    }

    public void sendMsg(ChatMsgInput msgInput, Long senderId) {
        chatSource.onNext(
                new GroupChatMsg(msgInput.getGroupId(), Collections.singletonList(
                        new ChatMsg(msgInput.getType(), msgInput.getMsg(), senderId.toString()))));
    }
}
