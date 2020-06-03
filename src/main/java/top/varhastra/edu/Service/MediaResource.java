package top.varhastra.edu.Service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;

@Service
public class MediaResource extends ResourceHttpRequestHandler {
    public final static String ATTR_FILE = "NON-STATIC-FILE";
    public final static String mediaRoot = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1);

    @Override
    protected Resource getResource(HttpServletRequest request) {
        final Path filePath = (Path) request.getAttribute(ATTR_FILE);
        return new FileSystemResource(filePath);
    }
}
