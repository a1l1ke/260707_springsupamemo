# Step 3: 서비스/리포지토리 아키텍처 및 Supabase REST API 연동

## 1. 개요 및 목표
비즈니스 로직과 데이터 보관 계층을 완전히 분리하는 **3계층 아키텍처(Controller - Service - Repository)**를 정립하고, 외부 클라우드 데이터베이스 플랫폼인 **Supabase**의 REST API를 연동하여 데이터를 영속화(Persistence)합니다. 또한 메모 등록 시 작성한 날짜와 시간(작성일시) 정보를 함께 저장 및 출력하는 기능을 통합합니다.

## 2. 변경 파일 및 구성
* [MemoEntity.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/model/MemoEntity.java)
  - 실제 영속성(DB) 계층에서 다루는 데이터를 추상화한 핵심 도메인 모델(Entity)입니다. UUID, 메모 내용, 작성일시 정보를 포함합니다.
* [MemoTableDTO.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/dto/MemoTableDTO.java)
  - Supabase 데이터베이스 조회(GET) 시 응답 JSON 포맷과 1:1로 매핑되는 수신 전용 DTO입니다. `toEntity()` 메서드를 내장하여 DTO에서 엔티티로의 변환 책임을 가지고 있습니다.
* [MemoSupabaseDTO.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/dto/MemoSupabaseDTO.java)
  - Supabase에 데이터 생성(POST) 시 전송할 전용 JSON 요청 DTO입니다.
* [SupabaseUtil.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/util/SupabaseUtil.java)
  - 스프링 컨테이너의 `@Component` 빈으로, 시스템 환경 변수(`SUPABASE_URL`, `SUPABASE_SECRET`)를 기반으로 기동합니다.
  - Java의 내장 `HttpClient` 및 Jackson `ObjectMapper`를 사용하여 REST API 통신(`POST /rest/v1/memo`, `GET /rest/v1/memo`)을 수행하고 응답 JSON을 파싱합니다.
* [MemoRepository.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/repository/MemoRepository.java)
  - `@Repository` 빈으로 데이터 액세스 전담 역할을 수행합니다. `SupabaseUtil`을 주입받아 데이터 생성 및 조회를 수행합니다.
* [MemoService.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/service/MemoService.java)
  - `@Service` 빈으로 비즈니스 연산과 데이터 흐름 제어를 수행합니다. `MemoRepository`를 주입받아 메모를 저장하고, 가져온 엔티티 리스트를 스트림 API를 사용해 화면 렌더링용 `MemoViewDTO` 리스트로 변환합니다.
* [HomeController.java](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/java/org/example/springsupamemo/controller/HomeController.java)
  - 프레젠테이션 계층으로서 HTTP 요청을 받아 `MemoService`에 작업을 요청하고 화면 출력을 위임합니다.
* [home.jsp](file:///Users/morgan/Documents/workspace/springsupamemo/src/main/webapp/WEB-INF/views/home.jsp)
  - 렌더링 루프 내부에서 메모의 내용과 함께 작성일시(`${memo.createdDate}`)를 출력하도록 갱신했습니다.

## 3. 핵심 아키텍처 흐름
```
[사용자 브라우저] (Form Submit)
       │ (1) POST / (memo = "...")
       ▼
[HomeController] (Controller 계층)
       │ (2) MemoService.save(MemoFormDTO) 호출
       ▼
[MemoService] (Service 계층 - 비즈니스 로직)
       │ (3) MemoFormDTO를 기반으로 MemoEntity 빌드
       │ (4) MemoRepository.save(MemoEntity) 호출
       ▼
[MemoRepository] (Repository 계층 - 데이터 접근)
       │ (5) SupabaseUtil.save(MemoEntity) 호출
       ▼
[SupabaseUtil] (Infrastructure 계층 - 외부 연동)
       │ (6) HTTP POST Request 빌드 & 발송 (Supabase REST API)
       ▼
[Supabase DB / Cloud] (데이터 영속화)
```

## 4. 진척도 총평
비즈니스 로직, 데이터 영속화, 외부 API 통신 책임이 깔끔하게 계층별로 분할된 상용 수준의 엔터프라이즈 아키텍처를 완성했습니다. WAS 내부 메모리(Session)에 의존하던 구조를 탈피하여 외부 데이터베이스인 Supabase를 연동함으로써 서버 인프라 상태에 무관하게 데이터가 완벽히 보관됩니다. 또한 데이터의 성격에 따라 DTO를 다각화(Form, Table, Supabase, View)하고 핵심 도메인 정보인 Entity를 따로 격리하여 시스템의 결합도를 매우 낮추었습니다.
