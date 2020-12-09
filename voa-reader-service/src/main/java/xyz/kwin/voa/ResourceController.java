package xyz.kwin.voa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.kwin.voa.mapper.AttachmentMapper;
import xyz.kwin.voa.spider.chinavoa.ChinaVOAPageProcessor;

/**
 * @author Kwin
 * @since 2020/11/28 下午5:23
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private final AttachmentMapper attachmentMapper;
    private final ChinaVOAPageProcessor chinaVoaPageProcessor;

    public ResourceController(AttachmentMapper attachmentMapper, ChinaVOAPageProcessor chinaVoaPageProcessor) {
        this.attachmentMapper = attachmentMapper;
        this.chinaVoaPageProcessor = chinaVoaPageProcessor;
    }

    @GetMapping("/select")
    public Object select() {
        return attachmentMapper.selectById(1);
    }

    @GetMapping("/fetch")
    public Object fetch() {
        chinaVoaPageProcessor.run();
        return "success";
    }
}
