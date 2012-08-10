package controllers;

import play.api.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import beans.AccountService;


public class Application extends Controller {
  
  public static Result index() {
	  
	 AccountService service= Spring.getBeanOfType(AccountService.class);
	 String value="";
	 value= value + "AccountService = " + service.toString() +"\\n";

	 
	 return ok(value);
  }
  
}