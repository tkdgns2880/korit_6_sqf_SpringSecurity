package com.study.SpringSecurity.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// Aspect(어너테이션)을 사용하기 위한 기본틀 (TestAspect)클레스명만 달라진다
// @Around("pointCut()") 기본틀
// AOP - 필터랑 흡사하다
// 요청이 들어오면 GET 이 호출되고 서비스안에들어 있는 aopTest 가 호출이 되는데
// Aspect가 먼저 동작을 하며 전처리 실행 후 서비스안에 aopTest 가 실행된다

// AOP 메소드의 핵심 - proceedingJoinPoint.proceed()
@Component
@Aspect
@Order(value = 1) // 누가 먼저 실행될지 순서를 정해주는 어너테이션 (숫자가 작을수록 먼저 실행)
public class TestAspect2 {

    // 정의을 넣을 해당 위치에 집어넣는것
    @Pointcut("@annotation(com.study.SpringSecurity.aspect.annotation.Test2Aop)")
    // String 으로 시작하는 TestService 메서드 안에 aop *(aop로 시작하는 모든 메소드)를 (..)(매개변수(0))개 이상을 실행해주는 애너테이션 정의
    // String (리턴자료형) 대신 * 를 넣으면 모든메서드를 호출한다
    // TestService 대신 * 을 넣으면 service 안에 모든 메소드를 호출한다
    private void pointCut() {

    }
    // 모든 공개 메서드 실행
//    execution(public * *(..))
//
//    // set 다음 이름으로 시작하는 모든 메서드 실행
//    execution(* set*(..))
//
//    // AccountService 인터페이스에 의해 정의된 모든 메서드의 실행
//    execution(* com.xyz.service.AccountService.*(..))
//
//    // service 패키지에 정의된 메서드 실행
//    execution(* com.xyz.service.*.*(..))
//
//    // 서비스 패키지 또는 해당 하위 패키지 중 하나에 정의된 메서드 실행
//    execution(* com.xyz.service..*.*(..))


    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {


        //Signature signature = proceedingJoinPoint.getSignature();
        CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
        System.out.println(signature.getName());
        System.out.println(signature.getDeclaringTypeName());

//        for(Object obj : proceedingJoinPoint.getArgs()) {
//            System.out.println(obj);
//        }

        Object[] args = proceedingJoinPoint.getArgs();
        // getArgs = Object의 [] 배열이다
        String[] paramNames = signature.getParameterNames();
        // getParameterNames = String의 [] 배열이다

        for(int i = 0; i < args.length; i++) {
            System.out.println(paramNames[i] + ": " + args[i]);
        }

        System.out.println("전처리2"); // 부가기능
        Object result = proceedingJoinPoint.proceed(); // 핵심기능 호출
        // proceed - 예외가 일어나면 throws Throwable 문법이다
        System.out.println("후처리2"); // 부가기능
        return result;
    } // 전처리 실행 후 result 에 값만 들어간 뒤 후처리가 실행되고 리턴으로 result 값을 Service에서 다시 실행한다
}
// proceedingJoinPoint 의 getArgs() - 매개변수
// proceedingJoinPoint.getArgs() - for 문으로 돌릴수 있다
// proceedingJoinPoint.getSignature()
// getSignature - Signature 객체
// getExceptionTypes 동일한 ExceptionTypes 더라도 객체 하나하나에게 따로 명령을 할 수 있다