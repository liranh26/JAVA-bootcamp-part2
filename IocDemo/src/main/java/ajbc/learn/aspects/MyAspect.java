package ajbc.learn.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import ajbc.learn.dao.DaoException;

@Aspect
@Component
public class MyAspect {

	public MyAspect() {
//		System.out.println("Aspect calling to main");
	}
	
	//this is an advice method 
	//syntax: (? means optional)
	//"execution(access-modifier?, return-type?, method-pattern(arg-type, arg-type,...))"   [* means not relevant]
	@Before("execution(* ajbc.learn.dao.ProductDao.*(..))")
	public void logBeforeCalling(JoinPoint joinPoint) {
		
		System.out.println("Aspect is writing to logger method name"+ joinPoint.getSignature().getName());
		System.out.println("args are " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@Around("execution(* ajbc.learn.dao.ProductDao.getProductsByPriceRange(Double, Double))")
	public Object swapInputs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object[] args = proceedingJoinPoint.getArgs();
		
		Double min = (Double)args[0];
		Double max = (Double)args[1];
		if(max<min)
			args = new Object[] {max,min};
				
		return proceedingJoinPoint.proceed(args);
	}
	
	@AfterThrowing(throwing = "ex", pointcut = "execution(* ajbc.learn.dao.ProductDao.*(..))")
	public void convertToDaoException(Throwable ex) throws DaoException {
		throw new DaoException(ex); 
	}
	
}
