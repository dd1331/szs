# 참고사항
> 자바/스프링 환경이 아직 숙련도가 부족하여 최선을 다해 구현에 힘썼습니다. 깃 로그에 고민이나 의도를 담지못하여 간단한 참고사항을 추가하였습니다. 다소 부족하지만 참고해주시면 감사하겠습니다. 


## 유연하게
* RegistrationValidator
  * 현재는 MemoryRegistrationValidator로 메모리상 유저 목록을 참조
  * 추후 DB등 외부 유저목록을 활용하는 경우 RegistrationValidator 구현체를 교환하여 서비스영역에 영향없이 변경 가능
* AuthService
  * jwt를 활용한 구현체를 사용하고 있지만 추후 다른 인증방식으로 쉽게 교체 가능
* ScrapService
  * restApi를 통해 데이터를 받아오고 있지만 추후 크롤링으로 데이터 스크랩하는 구현체로 변경 가능

## 막힘없이
* 웹플럭스 활용하여 오래걸리는 요청을 비동기로 처리하여 빠른 응답
* 실패시 재시도등의 예외처리가 필요한 상태

## 자바/스프링
* 숙련도가 부족함
* 저수준/고수준으로 코드 구분없이 구현위주로 진행
* 요청/응답 데이터의 변경(Ex:비밀번호암호화)등은 dto에서 수준에서 처리하고 싶었지만 다음을 기약
* 다양한 테스트 케이스를 통해 검증이 필요하나 산출세액 계산 일부를 제외하고는 부족함

