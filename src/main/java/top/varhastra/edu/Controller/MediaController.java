package top.varhastra.edu.Controller;

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.varhastra.edu.Service.MediaResource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/media")
public class MediaController {
    @Resource private MediaResource mediaResource;

    @GetMapping("/video/{resourceId}")
    public void video(
            @PathVariable("resourceId") Long resourceId,
            @RequestParam("type") String type,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String resourcePath = MediaResource.mediaRoot +"media/" + resourceId + type;
        Path filePath = Paths.get(resourcePath);
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(MediaResource.ATTR_FILE, filePath);
            mediaResource.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }
}
