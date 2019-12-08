/**
 * This utility is used to log the entrance and exit into most methods
 * throughout the application.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-11-23
 */


package util;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import services.Logger;

@Singleton
public class LoggingInterceptor implements Serializable {

	private static final long serialVersionUID = 7816323090992501620L;
	
	@EJB
	private Logger log;
	
	/**
	 * This method logs the entrance and exit from each method in the
	 * application.
	 * @param ctx This is the invocation context being used in this method
	 * @return Object
	 * @throws Exception
	 */
	@AroundInvoke
	public Object methodInterceptor(InvocationContext ctx) throws Exception{
		/*
		 * Logging the entrance into the method
		 */
		String input = "Entering method: " + ctx.getTarget().getClass() + "." +
				ctx.getMethod().getName() + "()";
		
		log.putLog(input);
		
		/*
		 * Logging when the user leaves the method
		 */
		input = "Leave method: " + ctx.getTarget().getClass() + "." + 
				ctx.getMethod().getName() + "()";
		
		log.putLog(input);
		
		return ctx.proceed();
	}
}
