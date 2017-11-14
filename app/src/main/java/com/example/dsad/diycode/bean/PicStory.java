package com.example.dsad.diycode.bean;

import java.io.Serializable;

/**
 * 图片输入的bean
 * Created by dsad on 2017/11/13.
 */

public class PicStory implements Serializable
{

    /**
     * id : 450
     * link : http://7vzpfb.com1.z0.glb.clouddn.com/bgc/16-09-11.png
     * source : 小幻bing抓取
     * sourcelink : http://www.bing.com
     * time : 2016-09-11
     * describe : 苏必利尔湖的岩石小岛鸟瞰图，桑德贝市，加拿大安大略省 (© Rolf Hicker/Getty Images)
     * title : 世界上最大的淡水湖
     * d : 水平如镜的湖面，岩石耸立的小岛，这是世界上第一大淡水湖苏必利尔湖。夏天的它仿佛蔚蓝的明镜，波光粼粼，引人入胜；冬季的它被冰雪覆盖，如同一片跌落在地上的白云。待冰雪融化时，可以听到湖面冰块融化、碰撞的声音，奇妙的仿佛人也冻结在这美丽的湖畔。
     * date : September 11
     * attribute : 加拿大，桑德贝市
     */

    private String id;
    private String link;
    private String source;
    private String sourcelink;
    private String time;
    private String describe;
    private String title;
    private String d;
    private String date;
    private String attribute;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourcelink() {
        return sourcelink;
    }

    public void setSourcelink(String sourcelink) {
        this.sourcelink = sourcelink;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
