package xyz.kwin.voa.spider.processors;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import xyz.kwin.voa.spider.pipelines.VOASavePipeline;

import javax.annotation.Resource;

/**
 * 抓取chinavoa.com音频文本
 *
 * @author Kwin
 * @since 2020/11/28 上午10:13
 */
@Component
public class ChinaVoaPageProcessor implements PageProcessor {

    @Resource
    private VOASavePipeline voaSavePipeline;

    private final Site site = Site.me().setDomain("chinavoa.com")
            .setRetryTimes(3).setSleepTime(3);

    @Override
    public void process(Page page) {
//        List<String> links = page.getHtml().links().regex("https://www.chinavoa.com/show-\\d+-\\d+-\\d+.html").all();
//        page.addTargetRequests(links);
        page.addTargetRequest("https://www.chinavoa.com/show-8788-242763-1.html");
        page.putField("title", page.getHtml().xpath("//div[@class='area']//div[@class='title']/h1/text()"));
        page.putField("origin", page.getHtml().xpath("//div[@class='area containter clearfix']/div[@class='xiazai']/a/text()").all());
        page.putField("mp3", page.getHtml().xpath("//div[@class='jp-help clearfix']/a[@class='mp3-yinpin fl']/@href"));
        page.putField("doc", page.getHtml().xpath("//div[@class='jp-help clearfix']/a[@class='mp3-wenben fl ']/@href"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void run() {
        Spider.create(new ChinaVoaPageProcessor()).addUrl("https://www.chinavoa.com/show-8788-242763-1.html")
                .addPipeline(new ConsolePipeline())
                .addPipeline(voaSavePipeline)
                .run();
    }

}
