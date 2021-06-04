/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/4 10:50
 * @Desc 短信服务商信息
 *          改造为不可变类是为了保证并发情况下，保证服务商信息不被外部改变。
 * @Since 1.0
 */
public final class SmsInfo {
    /**
     * 服务商请求地址
     */
    private final String url;
    /**
     * 短信内容最大字节数
     */
    private final Long maxSizeInBytes;

    public SmsInfo(String url, Long maxSizeInBytes) {
        this.url = url;
        this.maxSizeInBytes = maxSizeInBytes;
    }

    public String getUrl() {
        return url;
    }

    /* 禁止外部修改 */
//    public void setUrl(String url) {
//        this.url = url;
//    }

    public Long getMaxSizeInBytes() {
        return maxSizeInBytes;
    }

//    public void setMaxSizeInBytes(Long maxSizeInBytes) {
//        this.maxSizeInBytes = maxSizeInBytes;
//    }
}
