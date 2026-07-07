# Step 0: 프로젝트 초기화 및 기본 서블릿 설정

## 1. 개요 및 목표
프로젝트의 출발점으로서 Maven 빌드 도구와 WAS(톰캣) 상에서 기동하는 가장 기초적인 웹 서블릿(Servlet) 환경을 구성합니다. 프레임워크가 없는 날것의 서블릿과 JSP가 어떻게 통신하는지 파악하고 프로젝트 기초를 수립합니다.

## 2. 변경 파일 및 구성
* `pom.xml`: 프로젝트의 기본 컴파일 정보 및 빌드 세팅, 의존성(Servlet API 등) 지정
* `src/main/webapp/WEB-INF/web.xml`: 서블릿 매핑 및 프로젝트 기본 배치 서술자(Deployment Descriptor)
* `src/main/java/org/example/springsupamemo/HelloServlet.java`: `HttpServlet`을 상속받아 브라우저의 `/hello-servlet` 요청에 직접 HTML 응답을 쓰는 전통적인 서블릿
* `src/main/webapp/index.jsp`: 프로젝트 접속 시 처음에 로드되는 화면이자 `HelloServlet`으로 안내하는 하이퍼링크 템플릿
* `.env.sample` & `.gitignore`: 로컬 설정 배제를 위한 기본 템플릿 구성

## 3. 핵심 아키텍처 흐름
```
[사용자 브라우저] 
       │ (1) /hello-servlet 요청
       ▼
[Servlet Container (Tomcat)] 
       │ (2) web.xml의 서블릿 매핑 확인 -> HelloServlet 매핑
       ▼
[HelloServlet (doGet)]
       │ (3) PrintWriter로 직접 HTML 응답 작성
       ▼
[사용자 브라우저] (렌더링)
```

## 4. 진척도 총평
이 단계는 스프링 프레임워크 없이 오직 표준 서블릿 API와 WAS 서버의 구동 구조만으로 이루어져 있습니다. 이 구조는 웹 요청과 응답을 다루는 날것의 원리를 깨닫는 데 도움이 되지만, 복잡한 비즈니스 로직이나 URL 매핑이 추가될수록 중복 코드와 유지보수 효율 저하가 극심해지는 한계가 있습니다.
