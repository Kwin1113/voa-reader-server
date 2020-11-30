package xyz.kwin.voa.spider.processors;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 抓取chinavoa.com音频文本
 *
 * @author Kwin
 * @since 2020/11/28 上午10:13
 */
public class ChinaVoaPageProcessor implements PageProcessor {

    private final Site site = Site.me().setDomain("chinavoa.com")
            .setRetryTimes(3).setSleepTime(3);

    @Override
    public void process(Page page) {
//        List<String> links = page.getHtml().links().regex("https://www.chinavoa.com/show-\\d+-\\d+-\\d+.html").all();
//        page.addTargetRequests(links);
        page.addTargetRequest("https://www.chinavoa.com/show-8788-242763-1.html");
        page.putField("mp3", page.getHtml().xpath("//div[@class='jp-help clearfix']/a[@class='mp3-yinpin fl']/@href"));
        page.putField("txt", page.getHtml().xpath("//div[@class='jp-help clearfix']/a[@class='mp3-wenben fl ']/@href"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ChinaVoaPageProcessor()).addUrl("http://www.chinavoa.com")
                .addPipeline(new ConsolePipeline())
                .addPipeline(new FilePipeline())
                .run();
    }
}
