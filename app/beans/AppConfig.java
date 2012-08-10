package beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ImportResource("classpath:application-context.xml")
public class AppConfig {
	
	private @Value("${mongodb.hostname}") String hostname;
	private @Value("${mongodb.port}") String port;
	private @Value("${mongodb.dbname}") String dbname;	
	
	
	@Bean
	public MongoDbService mongoDbService() {
		return new MongoDbService(hostname,port,dbname);
	}
	
	@Bean
	public AccountService accountService(){
		AccountService accountService = new AccountService(mongoDbService());
		return accountService;
		
	}
	
	@Bean
	public PostService postService(){
		PostService postService = new PostService(mongoDbService());
		return postService;
	}
	
}
