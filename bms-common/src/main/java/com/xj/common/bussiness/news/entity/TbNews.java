package com.xj.common.bussiness.news.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 栏目管理
 * </p>
 *
 * @author xj
 * @since 2017-01-05
 */
@TableName("tb_news")
public class TbNews extends Model<TbNews> {

    private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	@TableField(value="nav_id")
	private Integer navId;
	/**
	 * 
	 */
	private String title;
	/**
	 * 关键词
	 */
	@TableField(value="key_words")
	private String keyWords;
	/**
	 * 封面图
	 */
	@TableField(value="cover_img")
	private String coverImg;
	/**
	 * 是否显示  1是  2否
	 */
	@TableField(value="is_show")
	private String isShow;
	/**
	 * 
	 */
	private String descs;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	@TableField(value="create_time")
	private Date createTime;
	/**
	 * 
	 */
	@TableField(value="create_user")
	private String createUser;
	/**
	 * 
	 */
	@TableField(value="update_time")
	private Date updateTime;
	/**
	 * 
	 */
	@TableField(value="update_user")
	private String updateUser;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNavId() {
		return navId;
	}

	public void setNavId(Integer navId) {
		this.navId = navId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
