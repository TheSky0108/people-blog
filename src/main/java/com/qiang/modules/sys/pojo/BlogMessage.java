package com.qiang.modules.sys.pojo;


import java.io.Serializable;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.pojo
 * @Description: 博客信息表
 * @Date: 2019/7/4 0004 11:13
 **/
public class BlogMessage implements Serializable {
    private static final long serialVersionUID = 6202944650911776915L;
    /**
     * 标识符
     */
    private long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String text;

    /**
     * 标签id
     */
    private String labelValues;

    /**
     * 文章类型
     */
    private String selectType;

    /**
     * 博客分类
     */
    private String selectCategories;

    /**
     * 文章等级
     */
    private int selectGrade;

    /**
     * 原文章作者
     */
    private String originalAuthor;

    /**
     * 文章（0-公开  1-私密）
     */
    private String message;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 点赞
     */
    private Integer likes;

    /**
     * 作者名字
     */
    private String name;

    /**
     * 文章摘要
     * @return
     */
    private String articleTabloid;

    /**
     * 含HTML文章
     */
    private String articleHtmlContent;

    /**
     * 标签数组
     */
    private String[] tagValue;

    /**
     * 浏览次数
     */
    private int look;

    public int getLook() {
        return look;
    }

    public void setLook(int look) {
        this.look = look;
    }

    public String[] getTagValue() {
        return tagValue;
    }

    public void setTagValue(String[] tagValue) {
        this.tagValue = tagValue;
    }

    public String getArticleHtmlContent() {
        return articleHtmlContent;
    }

    public void setArticleHtmlContent(String articleHtmlContent) {
        this.articleHtmlContent = articleHtmlContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getArticleTabloid() {
        return articleTabloid;
    }

    public void setArticleTabloid(String articleTabloid) {
        this.articleTabloid = articleTabloid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLabelValues() {
        return labelValues;
    }

    public void setLabelValues(String labelValues) {
        this.labelValues = labelValues;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public String getSelectCategories() {
        return selectCategories;
    }

    public void setSelectCategories(String selectCategories) {
        this.selectCategories = selectCategories;
    }

    public int getSelectGrade() {
        return selectGrade;
    }

    public void setSelectGrade(int selectGrade) {
        this.selectGrade = selectGrade;
    }

    public String getOriginalAuthor() {
        return originalAuthor;
    }

    public void setOriginalAuthor(String originalAuthor) {
        this.originalAuthor = originalAuthor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLike() {
        return likes;
    }

    public void setLike(Integer likes) {
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
