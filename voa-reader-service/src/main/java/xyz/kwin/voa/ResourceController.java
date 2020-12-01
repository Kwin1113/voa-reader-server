package xyz.kwin.voa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.kwin.voa.mapper.ResourceMapper;
import xyz.kwin.voa.spider.processors.ChinaVoaPageProcessor;

/**
 * @author Kwin
 * @since 2020/11/28 下午5:23
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceMapper resourceMapper;
    private final ChinaVoaPageProcessor chinaVoaPageProcessor;

    public ResourceController(ResourceMapper resourceMapper, ChinaVoaPageProcessor chinaVoaPageProcessor) {
        this.resourceMapper = resourceMapper;
        this.chinaVoaPageProcessor = chinaVoaPageProcessor;
    }

    @GetMapping("/select")
    public Object select() {
        return resourceMapper.selectById(1);
    }

    @GetMapping("/fetch")
    public Object fetch() {
        chinaVoaPageProcessor.run();
        return "success";
    }
}
