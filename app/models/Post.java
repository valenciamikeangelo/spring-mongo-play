package models;

import java.util.Date;
import java.util.List;

import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.ObjectId;

import org.codehaus.jackson.annotate.JsonProperty;

public class Post {

	@Id
	public String id;
	public String title;
	public Date postedAt;
	public String content;
	public Account author;
	public List<Comment> comments;

	public Post() {

	}

	public Post(Account author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
		this.postedAt = new Date();
	}

	@JsonProperty("title")
	protected String getTitle() {
		return title;
	}

	@JsonProperty("title")
	protected void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("postedAt")
	protected Date getPostedAt() {
		return postedAt;
	}

	@JsonProperty("postedAt")
	protected void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}

	@JsonProperty("content")
	protected String getContent() {
		return content;
	}

	@JsonProperty("content")
	protected void setContent(String content) {
		this.content = content;
	}

	protected List<Comment> getComments() {
		return comments;
	}

	protected void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@ObjectId
	@JsonProperty("_id")
	protected String getId() {
		return id;
	}

	@ObjectId
	@JsonProperty("_id")
	protected void setId(String id) {
		this.id = id;
	}

}
