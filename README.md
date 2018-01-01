# diycode,技术咨询集中地客户端
diycode是一个技术交流基地,个人很喜欢,里面大牛很多,加上该社区推出了公开api所以就想写着一个客户端
diycode is community for technologiers.it offer the api to use.
# 说在前头
感谢二次元魔法师GcsSloop提供的diycode的sdk,该sdk是对社区api的再次封装,sdk已开源https://github.com/GcsSloop/diycode-sdk
本项目仅限交流和学习！
# 更新（工作原因停止更新）(预计开坑时间 2018年？？？？？)(延长:预计年后,在下工作繁忙)
### 2017.12.13
1.项目重建,使用PureMvc框架来进行解耦,MVC设计模式中,Modle和View层高度耦合,造成代码复制,维护性低。<br/>
2.项目UI大改变,解决图片不清晰的问题(图标)<br/>
3.设置借节流选项,为用户节约流量<br/>
4.完善所有发帖功能,提升手机的功能性<br/>
5.页面切换动画<br/>
6.帖子页面右划关闭帖子<br/>
7.增加动画连贯性,合理性<br/>
8.增加主题换色功能(现正考虑是否是只设置个夜间模式和日间模式)<br/>
#### 为什么要用PureMvc?
1.PureMvc是结合了MVP和MVC之间的特点形成的,有十年的历史了,PureMvc项目链接https://www.sogou.com/link?url=TiECA-reDTCFVZteGz9ECIEIV04mNK7t<br/>
### 2017.10.31
1.大幅优化了浏览帖子的流畅度 具体细节:使用了RichText加载MarkDown文体,比之前用webview加载完成后,流程度变得更高,在模拟器上体验明显<br/>
2.增加了大图浏览 具体细节:在帖子中的图片点击后可以浏览 自由缩放 随后的下载功能下次加入
### 2017.11.15
1.个人信息页面改版<br/>
2.首页帖子Ui改版<br/>
3.图片错位问题解决<br/>
4.新增加关注功能<br/>
5.更新节点选择查看功能<br>
##### 改版效果如下:
---
![Image text](https://raw.githubusercontent.com/Aoyihala/img/master/diycode/follow.png)
---
![Image text](https://raw.githubusercontent.com/Aoyihala/img/master/diycode/userinfo.png)
---
## 简介
Diycode 社区客户端，可以更方便的在手机上查看社区信息。由于目前sdk功能尚未完善，还存在一些已知或未知的bug,所以暂时停止开发。

本客户端开发过程是完全开放的。
（过时）使用了MarkDownView代替WebView加载有markdown语法的文本,项目地址:https://github.com/mukeshsolanki/MarkdownView-Android

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
## 基本预览
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
 
 
