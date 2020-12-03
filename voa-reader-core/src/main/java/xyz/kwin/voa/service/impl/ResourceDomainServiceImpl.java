package xyz.kwin.voa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.kwin.voa.domain.Resource;
import xyz.kwin.voa.mapper.ResourceMapper;
import xyz.kwin.voa.service.ResourceDomainService;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/3 11:00 上午
 */
@Service
public class ResourceDomainServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceDomainService {
}
