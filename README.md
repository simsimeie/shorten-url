# shorten-url

## 기술스펙
- Springboot(2.7.8)
- JPA
- H2 DB
- Redis
- gradle

## 특징
1. 각 인스턴스 별 메모리 repo를 두어 storten url 중복 체크를 위해 굳이 DB를 조회하지 않아도 되도록 구현
-  멀티 인스턴스 환경에서 메모리 repo 동기화를 위해 Redis pub/sub 사용

2. BloomFilter, Converter 인터페이스를 구현하여 각자 상황에 맞는 로직 구현하도록 설계
- 예시 코드로는
  - Converter는 62진법 기반 
  - BloomFilter는 단순히 62진법 기반, Shorten URL 자리수 기반으로 모든 케이스를 비교하는 구조 

## TODO
- 부하 테스트 및 실제 멀티 인스턴스 환경에서 문제 없이 돌아가는지 확인
- 입력된 Original URL에 대한 검증 로직 추가
