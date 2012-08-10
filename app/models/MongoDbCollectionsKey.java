package models;

public enum MongoDbCollectionsKey {

	ACCOUNT("Account"),
	POST("Post");
	
	
	private String name;
	
	MongoDbCollectionsKey(String name){
		this.name=name;
	}

	
	public String toString(){
		return this.name;
	}
}
