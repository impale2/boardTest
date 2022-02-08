package org.company.myapp.advice;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

//aop구현
@Component //스프링이 자동 객체 생성
@Aspect
public class LoggingAdvice {
	//org.spring.myapp.controller하위의 모든 클래스와 메소드를 실행하기 전에
	//반환형 패지키명.클래스명.메소드명(..)
	@Before("execution(* org.company.myapp.controller.*.*(..))")
	public void beforeLog(JoinPoint jp) {
		//적용대상(joinpoint)의  메소드명 + 매개변수를  출력
		System.out.println(jp.getSignature().toShortString() + " : " + Arrays.toString(jp.getArgs()));
	}
	
	//리턴값 출력
	//정상수행후
	@AfterReturning(pointcut = "execution(* org.company.myapp.service.*.*(..))", returning = "rObj")
	public void afterLog(JoinPoint jp, Object rObj) {
		System.out.println(jp.getSignature().toShortString());  //실행된 메소드명
		System.out.println("리턴값:" + rObj.toString());
	}
	
	//실행대상을 시작과 끝을 체크
//	@Around("execution(* org.company.app.service.*.*(..))")
//	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
//		//시작시간
//		long startTime = System.currentTimeMillis(); //시스템의 시간을 1/1000초 단위로 읽기
//		
//		System.out.println(pjp.getSignature().toShortString());  //실행된 메소드명
//		Object result =  pjp.proceed(); //실행되야할 메소드
//		
//		//끝시간
//		long endTime = System.currentTimeMillis();
//		//소요시간
//		System.out.println("소요시간:" +(endTime-startTime) );
//		
//		return result; //실행되야할 메소드를 호출한 메소드에게 리턴값 전달
//	}
	
	
	
	
}

