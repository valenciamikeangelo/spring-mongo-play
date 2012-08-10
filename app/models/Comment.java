package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;


public class Comment{
	
	public String id;
	public Account commenter;
    public Date postedAt;
    public String content;
    
    public Comment(){
    	
    }
    
    public Comment(Account commenter, String content) {
        this.commenter = commenter;
        this.content = content;
        this.postedAt = new Date();
    }
 
}
