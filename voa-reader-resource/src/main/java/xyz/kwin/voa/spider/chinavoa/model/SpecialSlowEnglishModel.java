package xyz.kwin.voa.spider.chinavoa.model;

import lombok.Data;
import lombok.ToString;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * @author Kwin
 * @since 2020/12/5 下午5:00
 */
@Data
@ToString
@HelpUrl({"https://www.chinavoa.com/voa_special_english/", "https://www.chinavoa.com/list-\\d+-\\d+.html"})
@TargetUrl({"https://www.chinavoa.com/show-\\d+-\\d+-\\d+.html"})
public class SpecialSlowEnglishModel {
    @ExtractByUrl
    private String url;
    @ExtractBy("//div[@class='area']//div[@class='title']/h1/text()")
    private String title;
    @ExtractBy("//div[@class='area containter clearfix']/div[@class='xiazai']/a/text()")
    private List<String> origin;
    @ExtractBy("//div[@class='jp-help clearfix']/a[@class='mp3-yinpin fl']/@href")
    private String mp3Url;
    @ExtractBy("//div[@class='jp-help clearfix']/a[@class='mp3-wenben fl ']/@href")
    private String docUrl;
}
