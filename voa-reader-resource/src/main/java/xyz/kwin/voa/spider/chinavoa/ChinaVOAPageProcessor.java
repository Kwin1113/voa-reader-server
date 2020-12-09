package xyz.kwin.voa.spider.chinavoa;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import xyz.kwin.voa.spider.chinavoa.model.SpecialSlowEnglishModel;
import xyz.kwin.voa.spider.pipelines.VOASavePipeline;

import javax.annotation.Resource;

/**
 * 抓取chinavoa.com音频文本
 *
 * @author Kwin
 * @since 2020/11/28 上午10:13
 */
@Component
public class ChinaVOAPageProcessor implements PageProcessor {

    @Resource
    private VOASavePipeline voaSavePipeline;

    private static final Site site = Site.me().setDomain("chinavoa.com")
            .setRetryTimes(3).setSleepTime(5);

    /**
     * chinaVOA爬虫逻辑
     * 1. 进入首页http://www.chinavoa.com
     * 2. 快速导航栏标签 //ul[@class='quick-menu']/li/a/@href
     * 3. 进入标签分类，找到英语分类 //div[@class='area']/div[@id='rightContainer']/dl/dd/a/@href
     * 4. 找到列表 //div[@class='pindao-left']/span[@id='list']/ul/li/a/@href
     * 5. 找到分页标签进行翻页 //div[@class='pagelist']/a/@href
     * 6. 重复4-6
     */
    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return site;
    }

    public void run() {
        Spider.create(new ChinaVOAPageProcessor()).addUrl("https://www.chinavoa.com/show-8788-242763-1.html")
                .addPipeline(new ConsolePipeline())
                .addPipeline(voaSavePipeline)
                .run();
    }

    public static void main(String[] args) {
        OOSpider.create(Site.me().setSleepTime(3000), new ChinaVOAPageModelPipeline(resourceDomainService, attachmentDomainService), SpecialSlowEnglishModel.class)
                .addUrl("https://www.chinavoa.com/voa_special_english/")
                .thread(1).run();
    }

}
