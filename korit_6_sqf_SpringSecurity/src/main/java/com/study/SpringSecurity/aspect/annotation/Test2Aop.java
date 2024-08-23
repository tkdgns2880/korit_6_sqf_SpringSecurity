package com.study.SpringSecurity.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
// {ElementType.METHOD} - 메소드 위에만 사용
// {ElementType.FIELD} -
public @interface Test2Aop {
}

//    @Target(ElementType.ANNOTATION_TYPE) : 어노테이션
//    @Target(ElementType.CONSTRUCTOR) : 생성자
//    @Target(ElementType.FIELD) : 필드(멤버 변수, Enum 상수)
//    @Target(ElementType.LOCALVARIABLE) : 지역변수
//    @Target(ElementType.METHOD) : 메서드
//    @Target(ElementType.PACKAGE) : 패키지
//    @Target(ElementType.PARAMETER) : 매개변수(파라미터)
//    @Target(ElementType.TYPE) : 타입(클래스, 인터페이스, Enum)
//    @Target(ElementType.TYPE_PARAMETER) : 타입 매개변수(제네릭과 같은 매개변수)
//    @Target(ElementType.TYPE_USE) : 타입이 사용되는 모든 대상

