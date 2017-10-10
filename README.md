# diycode,技术咨询集中地客户端
diycode是一个技术交流基地,个人很喜欢,里面大牛很多,加上该社区推出了公开api所以就想写着一个客户端
# 说在前头
感谢二次元魔法师GcsSloop提供的diycode的sdk,该sdk是对社区api的再次封装,sdk已开源https://github.com/GcsSloop/diycode-sdk
本项目仅限交流和学习！
## 简介
Diycode 社区客户端，可以更方便的在手机上查看社区信息。由于目前sdk功能尚未完善，还存在一些已知或未知的bug,所以暂时停止开发。

本客户端开发过程是完全开放的。想查看源代码请移步:https://github.com/Aoyihala/diycode/

使用了MarkDownView代替WebView加载有markdown语法的文本,项目地址:https://github.com/mukeshsolanki/MarkdownView-Android

## 已实现功能
----------------------------
>1.用户登录和注册以及注销。
---
>2.查看帖子,回复和回复楼中楼
---
>3.收藏帖子,发现sdkbug后改为收藏至本地
--
>4.查看用户详情(主要为用户发过的帖子)
--
>5.酷站
---
>6.缓存清理(自写二级缓存用户头像,节省流量)
---
>7.发帖ui完成80%
---
>8.查看消息通知
---
##预览
-----------------------------
![Image text](https://raw.githubusercontent.com/Aoyihala/img/master/diycode/home.png)
----
![Image text](https://raw.githubusercontent.com/Aoyihala/img/master/diycode/home2.png)
---
![Image text](https://raw.githubusercontent.com/Aoyihala/img/master/diycode/topic1.png)
---
![Image text](https://raw.githubusercontent.com/Aoyihala/img/master/diycode/topic2.png)
----
## 主要页面结构
> 1.总体:viewpager+fragment+tablayout
---
> 2.主页:CoordinatorLayout依赖布局和DrawerLayout完成首页展示和侧滑菜单展示
---
> 3.帖子详情:Coordinatorlayout+MarkDownView展示
---
## 待解决问题
>1.图片加载不全(用户头像不显示)
--
>2.tab滑动稍卡盾
