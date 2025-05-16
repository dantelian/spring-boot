package com.example.springbootthread.common.buildThread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

public class CallableParamResultTest implements Callable<Boolean> {
    private String url;//网图下载地址
    private String name;//网图名称

    public CallableParamResultTest(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public Boolean call() {
        WebDownloader1 webDownloader = new WebDownloader1();
        webDownloader.downloader(url, name);
        System.out.println("下载了文件名：" + name);
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableParamResultTest d1 = new CallableParamResultTest("https://cn.bing.com/images/search?view=detailV2&ccid=64mezA1F&id=0567ED050842B109CEFE6D7C2E235E6513915D00&thid=OIP.64mezA1F6eYavcDWrgjHQgHaEK&mediaurl=https%3a%2f%2fimages.hdqwalls.com%2fwallpapers%2fcute-kitten-4k-im.jpg&exph=2160&expw=3840&q=Cat+Wallpaper+4K&simid=608031326407324483&FORM=IRPRST&ck=5E947A96CD5B48E39B116D48F58466AB&selectedIndex=12&ajaxhist=0&ajaxserp=0", "cat1.jpg");
        CallableParamResultTest d2 = new CallableParamResultTest("https://cn.bing.com/images/search?view=detailV2&ccid=qXtg4Nx0&id=A80C30163A6B55D16D61F27E632239424517705F&thid=OIP.qXtg4Nx0BUoeUP53fz_HKgHaFI&mediaurl=https%3a%2f%2fimages8.alphacoders.com%2f856%2f856433.jpg&exph=2658&expw=3840&q=Cat+Wallpaper+4K&simid=608046255722156270&FORM=IRPRST&ck=986D5F99CF8474477F4A1F2DB2850C9D&selectedIndex=25&ajaxhist=0&ajaxserp=0", "cat2.jpg");
        CallableParamResultTest d3 = new CallableParamResultTest("https://cn.bing.com/images/search?view=detailV2&ccid=kvYsfUHA&id=6311D8D1DC87AA4B69783A97020038B03827134D&thid=OIP.kvYsfUHAAQlEVW3Z3_EEWwHaEK&mediaurl=https%3a%2f%2fwallpapershome.com%2fimages%2fpages%2fpic_h%2f19418.jpg&exph=1080&expw=1920&q=Cat+Wallpaper+4K&simid=608016886736366855&FORM=IRPRST&ck=37C2818B80D19766E7A91B5BB7A060D6&selectedIndex=159&ajaxhist=0&ajaxserp=0", "cat3.jpg");
        //创建执行任务
        ExecutorService es = Executors.newFixedThreadPool(3);
        //提交执行
        Future<Boolean> r1 = es.submit(d1);
        Future<Boolean> r2 = es.submit(d2);
        Future<Boolean> r3 = es.submit(d3);
        //获取结果
        Boolean res1 = r1.get();
        Boolean res2 = r2.get();
        Boolean res3 = r3.get();
        System.out.println(res1);//打印结果
        System.out.println(res2);
        System.out.println(res3);
        //关闭服务
        es.shutdownNow();
    }
}
//下载器
@Slf4j
class WebDownloader1{
    public void downloader(String url,String name){
        log.info("name: {}, url: {}", name, url);
    }
}
