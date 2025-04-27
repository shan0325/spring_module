### Application Module 
- 'app' 이라는 네이밍을 넣어서 사용
- 어플리케이션 모듈은 common, domain 모듈 의존성을 추가하여 사용
- Application Service
  - UseCase Interface : 하나의 유스케이스(업무) 단위
    - UserCreateUseCase
    - UserUpdateUseCase
    - UserDeleteUseCase
  - Service : 복합 유스케이스(업무) 구현체 
    - UserService
  - Facade Service : 복합 유스케이스 단위
    - UserFacadeService

### Common Module
- 순수 자바 클래스만 정의하는 모듈
- Type(Dto, Enum, Exception 등), Util과 같은 공통적인 기능을 제공하는 모듈
- 외부 라이브러리 의존성 금지

### Domain Module
- Domain Service Module
  - 여러 도메인 / 저장소 간 상호작용 조율
- Infrastructure Modules (RDB/Redis)
  - 단일 저장소 책임 원칙
  - 저장소 특화 기능 구현
  - 기본적인 유효성 검증 및 데이터 접근 로직, 불변식 검증
  - 하나의 모듈은 최대 하나의 Infrastructure에 대한 책임만을 갖거나 가지지 않는다.
  - 도메인 모듈을 조합한 더 큰 단위의 도메인 모듈이 존재할 수 있다.