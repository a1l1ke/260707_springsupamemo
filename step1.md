# Step 1: Spring MVC 전환 및 Java 기반 설정 도입

## 1. 개요 및 목표
날것의 서블릿 기반 구성을 걷어내고, 현대적인 **Spring MVC 프레임워크** 기반으로 프로젝트를 전면 전환합니다. 배포 서술자(`web.xml`)의 복잡한 설정을 완전히 비우고, 자바 코드로 웹 컨텍스트와 스프링 컨테이너를 구동하는 Java Config 방식을 도입합니다.

## 2. 변경 파일 및 구성
* [WebAppInitializer.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/config/WebAppInitializer.java)
  - `WebApplicationInitializer` 인터페이스를 구현하여 서블릿 컨테이너(WAS) 기동 시 자동으로 스프링 `DispatcherServlet`을 구동하고 URL 매핑을 `/`로 연동합니다.
* [WebConfig.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/config/WebConfig.java)
  - `@Configuration`, `@EnableWebMvc`, `@ComponentScan`을 적용하여 Spring MVC 기능을 활성화하고 빈(Bean) 스캔 대상을 지정합니다.
  - JSP 파일을 처리하기 위한 `InternalResourceViewResolver` 빈과 정적 자원(assets)을 핸들링하기 위한 `addResourceHandlers`를 선언합니다.
* [HomeController.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/controller/HomeController.java)
  - 스프링 컨트롤러 `@Controller`와 `@GetMapping` 애노테이션을 사용하여 `/` 경로의 웹 요청을 매핑하고, 뷰 이름 `"home"`을 반환합니다.
* [home.jsp](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/webapp/WEB-INF/views/home.jsp)
  - 기존의 `index.jsp`를 지우고 `WEB-INF/views`로 격리하여 브라우저에서 직접 jsp 파일에 접근할 수 없도록 물리적 안전성을 확보하고, 정적 아이콘 자산을 링크합니다.
* [icons8-memo-96.png](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/webapp/assets/icons8-memo-96.png)
  - 메모 앱의 파비콘 및 화면 자산으로 활용할 아이콘 리소스를 추가했습니다.

## 3. 핵심 아키텍처 흐름
```
[사용자 브라우저]
       │ (1) GET / 요청
       ▼
[DispatcherServlet] (프론트 컨트롤러)
       │ (2) Handler Mapping 조회 -> HomeController 매핑
       ▼
[HomeController (home)]
       │ (3) View Name "home" 반환
       ▼
[ViewResolver (InternalResourceViewResolver)]
       │ (4) prefix(/WEB-INF/views/) + suffix(.jsp) 조립 -> "/WEB-INF/views/home.jsp"
       ▼
[home.jsp] (JSP 뷰 템플릿)
       │ (5) HTML 렌더링 후 응답
       ▼
[사용자 브라우저]
```

## 4. 진척도 총평
스프링 MVC의 중추 역할을 수행하는 `DispatcherServlet`이 최초 진입점으로서 모든 웹 요청을 일괄 수신 및 처리해주는 **Front Controller 패턴**이 도입되었습니다. 이로써 기존의 개별 서블릿 마다 복잡하게 작성해야 했던 자원 관리 및 요청 분배 코드가 획기적으로 개선되었으며, 자바 설정을 통해 유연하고 타입 안정적인 구성을 확보했습니다.
