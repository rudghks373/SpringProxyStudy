# SpringProxyStudy
Srping을 이용한 Proxy Pattern Application 개발 학습
- bytebuddy API 사용





## 1부. JVM 이 해 하 기

1. 자 바, JVM, JDK, JRE
 
![the-java-code-manipulation 001](https://user-images.githubusercontent.com/32234263/103335400-2c251e00-4ab8-11eb-900b-437460190981.png)


**JVM (Java Virtual Machine)**

- 자 바 가 상 머 신 으 로 자 바 바 이 트 코 드(.class 파 일)를 O S에 특 화 된 코 드 로 변 환(인 터 프 리 터 와 JIT 컴 파 일 러)하 여 실 행 한 다.

- 바 이 트 코 드 를 실 행 하 는 표 준(JVM 자 체 는 표 준)이 자 구 현 체(특 정 밴 더 가 구 현 한 JVM)다.

- JVM 스 팩: [ h ttps://docs.oracle.com/javase/specs/jvms/se11/html/](https://docs.oracle.com/javase/specs/jvms/se11/html/)

- JVM 밴 더: 오 라 클, 아 마 존, Azul, ...

- 특 정 플 랫 폼 에 종 속 적.

  

**JRE (Java Runtime Environment): JVM + 라 이 브 러 리**

  

- 자 바 애 플 리 케 이 션 을 실 행 할 수 있 도 록 구 성 된 배 포 판.

- JVM과 핵 심 라 이 브 러 리 및 자 바 런 타 임 환 경 에 서 사 용 하 는 프 로 퍼 티 세 팅 이 나 리 소 스 파 일 을 가 지 고 있 다.

- 개 발 관 련 도 구 는 포 함 하 지 않 는 다. (그 건 J DK에 서 제 공)

  

**JDK (Java Development Kit): JRE + 개 발 툴**

  
- JRE + 개 발 에 필 요 할 툴

- 소 스 코 드 를 작 성 할 때 사 용 하 는 자 바 언 어 는 플 랫 폼 에 독 립 적.

- 오 라 클 은 자 바 11부 터 는 JDK만 제 공 하 며 JRE를 따 로 제 공 하 지 않 는 다.

- Write Once Run Anywhere

  

***자 바***

  

- 프 로 그 래 밍 언 어

- JDK에 들 어 있 는 자 바 컴 파 일 러(javac)를 사 용 하 여 바 이 트 코 드(.class 파 일)로 컴 파 일 할 수 있 다.

- 자 바 유 료 화? 오 라 클 에 서 만 든 Oracle JDK 11 버 전 부 터 상 용 으 로 사 용 할 때 유 료.

- <https://medium.com/@javachampions/java-is-still-free-c02aef8c9e04>

  

**JVM 언 어**

  

- JVM 기 반 으 로 동 작 하 는 프 로 그 래 밍 언 어

- 클 로 저, 그 루 비, JRuby, Jython, Kotlin, Scala, ...

  

참 고

  

- JIT 컴 파 일 러: [ h ttps://aboullaite.me/understanding-jit-compiler-just-in-time-compiler/](https://aboullaite.me/understanding-jit-compiler-just-in-time-compiler/)

- JDK, JRE 그 리 고 JVM: [ h ttps://howtodoinjava.com/java/basics/jdk-jre-jvm/](https://howtodoinjava.com/java/basics/jdk-jre-jvm/)

- <https://en.wikipedia.org/wiki/List_of_JVM_languages>

## **2. JVM 구 조**

  

![the-java-code-manipulation 002](https://user-images.githubusercontent.com/32234263/103335418-36471c80-4ab8-11eb-8494-bd508bb3ce81.png)


  

**클 래 스 로 더 시 스 템**

  

- .class 에 서 바 이 트 코 드 를 읽 고 메 모 리 에 저 장

- 로 딩: 클 래 스 읽 어 오 는 과 정

- 링 크: 레 퍼 런 스 를 연 결 하 는 과 정

- 초 기 화: static 값 들 초 기 화 및 변 수 에 할 당

  

**메 모 리**

  

- 메 모 스 영 역 에 는 클 래 스 수 준 의 정 보 (클 래 스 이 름, 부 모 클 래 스 이 름, 메 소 드, 변 수) 저 장. 공 유 자 원 이 다.

- 힙 영 역 에 는 객 체 를 저 장. 공 유 자 원 이 다.

- 스 택 영 역 에 는 쓰 레 드 마 다 런 타 임 스 택 을 만 들 고, 그 안 에 메 소 드 호 출 을 스 택 프 레 임 이 라 부 르 는 블 럭 으 로 쌓 는 다. 쓰 레 드 종 료 하 면 런 타 임 스 택 도 사 라 진 다.

- PC(Program Counter) 레 지 스 터: 쓰 레 드 마 다 쓰 레 드 내 현 재 실 행 할 스 택 프 레 임 을 가 리 키 는 포 인 터 가 생 성 된 다.

- 네 이 티 브 메 소 드 스 택

-  [https://javapapers.com/core-java/java-jvm-run-time-data-areas/#Program_Counter_PC_](https://javapapers.com/core-java/java-jvm-run-time-data-areas/#Program_Counter_PC_Register) [Register](https://javapapers.com/core-java/java-jvm-run-time-data-areas/#Program_Counter_PC_Register)

  

**실 행 엔 진**

  

- 인 터 프 리 터: 바 이 크 코 드 를 한 줄 씩 실 행.

- JIT 컴 파 일 러: 인 터 프 리 터 효 율 을 높 이 기 위 해, 인 터 프 리 터 가 반 복 되 는 코 드 를 발 견 하 면 JIT 컴 파 일 러 로 반 복 되 는 코 드 를 모 두 네 이 티 브 코 드 로 바 꿔 둔 다. 그 다 음 부 터

  

인 터 프 리 터 는 네 이 티 브 코 드 로 컴 파 일 된 코 드 를 바 로 사 용 한 다.

  

- GC(Garbage Collector): 더 이 상 참 조 되 지 않 는 객 체 를 모 아 서 정 리 한 다.

  

JNI(Java Native Interface)

  

- 자 바 애 플 리 케 이 션 에 서 C, C++, 어 셈 블 리 로 작 성 된 함 수 를 사 용 할 수 있 는 방 법 제 공

- Native 키 워 드 를 사 용 한 메 소 드 호 출

-  [https://medium.com/@bschlining/a-simple-java-native-interface-jni-example-in-java-and-](https://medium.com/@bschlining/a-simple-java-native-interface-jni-example-in-java-and-scala-68fdafe76f5f) [scala-68fdafe76f5f](https://medium.com/@bschlining/a-simple-java-native-interface-jni-example-in-java-and-scala-68fdafe76f5f)

  

네 이 티 브 메 소 드 라 이 브 러 리

  

- C, C++로 작 성 된 라 이 브 러 리

  

참 고

  

- <https://www.geeksforgeeks.org/jvm-works-jvm-architecture/>

- <https://dzone.com/articles/jvm-architecture-explained>

- <http://blog.jamesdbloom.com/JVMInternals.html>

## 3. 클 래 스 로 더

  

![the-java-code-manipulation 003](https://user-images.githubusercontent.com/32234263/103335431-3e9f5780-4ab8-11eb-9695-b4142b8906ea.png)


  

**클 래 스 로 더**

  

- 로 딩, 링 크, 초 기 화 순 으 로 진 행 된 다.

**로 딩**

- 클 래 스 로 더 가 .class 파 일 을 읽 고 그 내 용 에 따 라 적 절 한 바 이 너 리 데 이 터 를 만 들 고 “메 소 드” 영 역 에 저 장.

- 이 때 메 소 드 영 역 에 저 장 하 는 데 이 터

- FQCN

- 클 래 스 | 인 터 페 이 스 | 이 늄

- 메 소 드 와 변 수

- 로 딩 이 끝 나 면 해 당 클 래 스 타 입 의 Class 객 체 를 생 성 하 여 “ 힙" 영 역 에 저 장.

**링 크**

- Verify, Prepare, Reolve(optional) 세 단 계 로 나 눠 져 있 다.

- 검 증: .class 파 일 형 식 이 유 효 한 지 체 크 한 다.

- Preparation: 클 래 스 변 수(static 변 수)와 기 본 값 에 필 요 한 메 모 리

- Resolve: 심 볼 릭 메 모 리 레 퍼 런 스 를 메 소 드 영 역 에 있 는 실 제 레 퍼 런 스 로 교 체 한 다.

  

**초 기 화**

- Static 변 수 의 값 을 할 당 한 다. (static 블 럭 이 있 다 면 이 때 실 행 된 다.)

- 클 래 스 로 더 는 계 층 구 조 로 이 뤄 져 있 으 면 기 본 적 으 로 세 가 지 클 래 스 로 더 가 제 공 된 다.

- 부 트 스 트 랩 클 래 스 로 더 - J AVA\_HOME\lib에 있 는 코 어 자 바 A PI를 제 공 한 다. 최 상 위 우 선 순 위 를 가 진 클 래 스 로 더

- 플 랫 폼 클 래 스 로 더 - JAVA\_HOME\lib\ext 폴 더 또 는 java.ext.dirs 시 스 템 변 수 에 해 당 하 는 위 치 에 있 는 클 래 스 를 읽 는 다.

- 애 플 리 케 이 션 클 래 스 로 더 - 애 플 리 케 이 션 클 래 스 패 스(애 플 리 케 이 션 실 행 할 때 주 는 -classpath 옵 션 또 는 java.class.path 환 경 변 수 의 값 에 해 당 하 는 위 치)에 서 클 래 스 를 읽 는 다.
