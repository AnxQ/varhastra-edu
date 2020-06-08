package top.varhastra.edu.Graphql.resolvers;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Graphql.execptions.CourseException;
import top.varhastra.edu.Graphql.execptions.CourseException.Type;

import top.varhastra.edu.Graphql.types.BaseResult;
import top.varhastra.edu.Graphql.types.CommentInput;
import top.varhastra.edu.Graphql.types.CourseChangeInput;
import top.varhastra.edu.Graphql.types.ScoreInput;
import top.varhastra.edu.Service.CourseService;
import top.varhastra.edu.Service.UserService;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CourseMutation implements GraphQLMutationResolver {
    @Resource private UserService userService;
    @Resource private CourseService courseService;

    public BaseResult courseChange(CourseChangeInput input, DataFetchingEnvironment environment) {
        User opUser = userService.getCurrentUser(environment);
        long courseId = Long.parseLong(input.getCourseId());
        if (input.getType().equals("join"))
            courseService.joinCourse(
                    input.getUserIds().stream().map(Long::parseLong).collect(Collectors.toList()),
                    courseId, opUser);
        else if (input.getType().equals("leave"))
            courseService.joinCourse(
                    input.getUserIds().stream().map(Long::parseLong).collect(Collectors.toList()),
                    courseId, opUser);
        else
            throw new CourseException(Type.CONDITION_INVALID);
        return new BaseResult("success");
    }

    public BaseResult commentChange(CommentInput input, DataFetchingEnvironment environment) {
        User opUser = userService.getCurrentUser(environment);
        long courseId = Long.parseLong(input.getCourseId());
        if (!input.getCommentId().isEmpty()) {
            if (input.getDetails().isEmpty())
                courseService.removeComment(Long.parseLong(input.getCommentId()), opUser);
            else
                courseService.editComment(input.getDetails(), opUser, Long.parseLong(input.getCommentId()));
        } else {
            courseService.addComment(
                    input.getDetails(),
                    opUser,
                    courseId,
                    Objects.isNull(input.getReplyTo()) ?
                            null : input.getReplyTo().isEmpty() ?
                            null : Long.parseLong(input.getReplyTo())
            );
        }
        return new BaseResult("success");
    }

    public BaseResult score(ScoreInput input, DataFetchingEnvironment environment) {
        User opUser = userService.getCurrentUser(environment);
        Long courseId = Long.parseLong(input.getCourseId());
        courseService.addSentiment(input.getScore(), courseId);
        return new BaseResult("success");
    }
}
