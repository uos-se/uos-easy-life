# System Design

## Requirements

- PortalSessionManager는 유저의 로그인 정보 및 API call에 사용할 수 있는 portal session 정보를 관리한다.
- 즉, 이 서비스는 항상 가용한 유저의 세션을 반환할 책임이 있다.
- 내부적으로 Playwright를 사용할 것이다.
  - 반환하는 세션이 valid할 책임은 PortalSessionManager가 가진다.
- 유저의 세션은 포털 세션과는 별도로 유지한다.
  - 이를 위하여 AuthService가 요구된다.
  - AuthService는 PortalSessionManager를 통해 유저의 로그인 정보를 관리한다.
  - 단 매번 로그인을 하면 서버에 무리가 갈 수 있으므로 로그인 정보는 캐싱한다.
    - 추후 유저가 패스워드를 변경하는 경우에 대한 처리도 고려해야 한다.
