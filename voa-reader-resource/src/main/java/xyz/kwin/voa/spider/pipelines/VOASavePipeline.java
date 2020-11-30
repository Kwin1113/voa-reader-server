package xyz.kwin.voa.spider.pipelines;

import com.google.common.collect.Sets;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;
import java.util.Set;

/**
 * VOA保存操作流
 *
 * @author Kwin
 * @since 2020/11/28 上午11:52
 */
public class VOASavePipeline implements Pipeline {

    private static final String URL_REGEX = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

    public static final String TITLE = "title";
    public static final String TAG = "tag";

    public static final String MP3 = "mp3";
    public static final String DOC = "doc";
    public static final String TXT = "txt";

    private static final Set<String> FILE_FORMAT_SET;

    static {
        FILE_FORMAT_SET = Sets.newHashSet(MP3, DOC, TXT);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.getRequest().getUrl();
        System.out.println("request url: " + url);

        String title = null;
        String tag = null;
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            // 获取标题
            if (TITLE.equals(entry.getKey())) {
                title = entry.getValue().toString();
            }
            // 获取标签
            else if (TAG.equals(entry.getKey())) {
                tag = entry.getValue().toString();
            }
            // 存在可下载的文件格式（爬虫解析时指定）
            else if (FILE_FORMAT_SET.contains(entry.getKey())) {
                if (entry.getValue().toString().matches(URL_REGEX)) {
                    // TODO do save action...
                }
            }
        }
    }

}
