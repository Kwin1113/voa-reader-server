package xyz.kwin.voa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.kwin.voa.mapper.ResourceMapper;

/**
 * @author Kwin
 * @since 2020/11/28 下午5:23
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceMapper resourceMapper;

    public ResourceController(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    @GetMapping("/select")
    public Object select() {
        return resourceMapper.selectById(1);
    }
}
