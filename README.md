VoaHelper
=========

通过电脑自动抓取 voa standard english 的所有连接，redis存储url地址，最终mongodb存储标题 时间  正文  等信息，手机通过ajax向springmvc后台请求信息，获取信息并进行展示。附加了用户信息、收藏功能（建议使用ngnix静态转发 mp3音频文件）
