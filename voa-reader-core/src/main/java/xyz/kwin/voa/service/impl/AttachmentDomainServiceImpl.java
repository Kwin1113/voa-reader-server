package xyz.kwin.voa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.kwin.voa.domain.Attachment;
import xyz.kwin.voa.mapper.AttachmentMapper;
import xyz.kwin.voa.service.AttachmentDomainService;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/1 11:55 上午
 */
@Service
public class AttachmentDomainServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentDomainService {
}
