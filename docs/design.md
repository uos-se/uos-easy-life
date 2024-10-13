# System Design

시스템의 기본 디자인은 조회(Read)와 동기화(Sync)로 구분하여 구성한다.

- 조회란 우리 서비스에 저장된 데이터를 단순히 읽어 보여주는 것이다. 이 연산은 UOS 서비스에 어떠한 부하도 주지 않는다.
- 동기화란 UOS 서버로부터 우리 서버의 데이터를 업데이트하는 것이다. 이는 UOS 서버에 부하를 주는 연산이다.
- 동기화는 그러므로 수동으로만 진행하며, 비동기로 큐잉하여 진행해야 하는 연산이다.
  - 큐잉된 데이터는 call stack 등 implicit한 구조가 아니라 Task에 해당하는 자료구조와 List 등으로 명료해야 하며, 이것은 Thread-safe하게 구현되어야 한다.
  - 또 비동기는 단순히 작업 리턴이 지연되는 것이 아니라 작업 제출 및 작업 상태 확인에 대한 두 연산으로 구성되어야 한다.

## Requirements

- PortalSessionManager는 유저의 로그인 정보 및 API call에 사용할 수 있는 portal session 정보를 관리한다.
- 즉, 이 서비스는 항상 가용한 유저의 세션을 반환할 책임이 있다.
- 내부적으로 Playwright를 사용할 것이다.
  - 반환하는 세션이 valid할 책임은 PortalSessionManager가 가진다.
- 유저의 세션은 포털 세션과는 별도로 유지한다.
  - 이를 위하여 AuthService가 요구된다.
  - AuthService는 PortalSessionManager를 통해 유저의 로그인 정보를 관리한다.
  - 단 매번 로그인을 하면 UOS 서버에 무리가 갈 수 있으므로 로그인 정보는 캐싱한다.
    - 추후 유저가 패스워드를 변경하는 경우에 대한 처리도 고려해야 한다.
- UserService는 여러 유저의 작업을 처리할 때 UOS 서비스(Wise, Portal, UClass)에 따라 큐잉을 사용해야 한다.
  - Thread-Safeness에 유의.
