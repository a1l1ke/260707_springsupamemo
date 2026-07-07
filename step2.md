# Step 2: 메모 입력 폼 및 HttpSession 기반 메모 저장 기능 추가

## 1. 개요 및 목표
정적 화면을 넘어 사용자가 텍스트 창에 직접 입력한 메모 데이터를 전송하고 이를 저장하여 화면에 지속적으로 갱신해 보여주는 **동적 웹 상호작용**을 구현합니다. 이 단계에서는 복잡한 외부 DB 연동 없이 WAS의 내부 세션(`HttpSession`)을 임시 저장소로 삼아 데이터의 전송, 바인딩, 보관, 출력을 아우르는 흐름을 정립합니다.

## 2. 변경 파일 및 구성
* [MemoFormDTO.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/dto/MemoFormDTO.java)
  - 사용자가 작성하는 메모 입력 폼 데이터를 매핑하여 받는 용도의 불변 Record 객체입니다.
* [MemoViewDTO.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/dto/MemoViewDTO.java)
  - 저장되어 뷰 템플릿(JSP) 상에 바인딩 및 렌더링될 메모 정보 모델로, EL(Expression Language) 파서와의 호환성을 고려해 일반적인 Getter 구조와 Lombok 애노테이션을 적용했습니다.
* [style.css](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/webapp/assets/style.css)
  - 뷰 템플릿 내의 인라인 `<style>` 설정을 제거하고 외부 정적 스타일시트 파일로 분리하여 유지보수성을 극대화합니다.
* [HomeController.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/controller/HomeController.java)
  - `@PostMapping` 요청을 선언하여 메모 등록 시 폼 데이터를 `@ModelAttribute`를 통해 수신합니다.
  - `HttpSession`에 리스트(`List<MemoViewDTO>`) 객체를 바인딩하여 메모 데이터를 등록 및 관리하고, 저장 완료 후 `redirect:/`를 반환해 새로고침 시 폼 중복 제출을 원천 차단(PRG 패턴)합니다.
* [home.jsp](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/webapp/WEB-INF/views/home.jsp)
  - 메모를 전송하는 `<form>` 양식을 생성하고 외부 CSS를 링크했습니다.
  - JSTL core 태그 라이브러리(`<c:forEach>`)를 선언하여 세션에 보관된 메모 목록(`memoList`)을 동적으로 반복 렌더링합니다.

## 3. 핵심 아키텍처 흐름
### 메모 등록 및 PRG (Post-Redirect-Get) 데이터 흐름
```
[사용자 브라우저]
       │ (1) POST / (memo = "새 메모")
       ▼
[HomeController (memoForm)]
       │ (2) @ModelAttribute MemoFormDTO 바인딩
       │ (3) HttpSession에서 기존 memoList 획득 및 새 메모 추가
       │ (4) 세션에 memoList 업데이트
       │ (5) return "redirect:/" (302 Redirect 응답)
       ▼
[사용자 브라우저]
       │ (6) GET / 자동 재요청 (302에 의해 유도)
       ▼
[HomeController (home)]
       │ (7) return "home"
       ▼
[home.jsp] (JSP)
       │ (8) 세션에서 memoList를 꺼내 <c:forEach>로 출력
       ▼
[사용자 브라우저] (최종 렌더링 화면)
```

## 4. 진척도 총평
사용자의 동작과 서버 데이터 흐름이 본격적으로 연결되었습니다. 데이터를 전달하는 전용 그릇인 **DTO(Data Transfer Object) 패턴**을 최초 도입해 컨트롤러와 데이터를 주고받는 계약을 정의했으며, 새로고침 시 이중 전송을 막는 **PRG 패턴**을 통해 웹 애플리케이션의 기본적인 안전성을 확보했습니다. 다만, 메모리가 서버 세션에 임시 보관되므로 서버가 재부팅되면 모든 메모가 증발하는 데이터 지속성 한계를 가지고 있습니다.
