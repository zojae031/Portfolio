# Protfolio

   # 프로젝트 구성도

   ![Architecture](https://user-images.githubusercontent.com/31091115/65956380-39062c80-e485-11e9-9515-bf4d364b4b90.png)

   <hr>  

   ```
   자신의 포트폴리오로 사용하는 방법
   
   1. 자신의 Repository로 Fork
   2. Json Branch안의 데이터 변경
   3. 어플리케이션 좌측 상단 메뉴 클릭
   4. 왼쪽 하단 돋보기 클릭
   5. 자신의 깃허브 아이디 선택
   ```

   # 프로젝트 구조

   #### [Repository](https://github.com/zojae031/Portfolio/tree/master/app/src/main/java/zojae031/portfolio/data)

   - Local 에서 데이터를 가져온다.
   - 인터넷에 연결되어 있을 시 Remote에서 가져와 Local에 저장
   
   #### [Master Branch](https://github.com/zojae031/Portfolio/tree/master)
   - MVVM 패턴을 적용
   - 현재 개발중
   
   #### [MVP Branch](https://github.com/zojae031/Portfolio/tree/mvp)
   - MVP 패턴을 적용
   - View와 Presenter의 1:1 대응
   

### 1. [Main](https://github.com/zojae031/Portfolio/tree/master/app/src/main/java/zojae031/portfolio/main)

1. 내부 ViewPager와 LinearLayout을 사용한 Indicator를 이용하여 세가지 정보를 보여줌
   1. 기본정보
   2. 프로젝트
   3. 기술
2. 기본적인 데이터를 가져옴
   1. 사용자 이미지
   2. Drawable에 띄울 Notice정보

### 2. [Profile](https://github.com/zojae031/Portfolio/tree/master/app/src/main/java/zojae031/portfolio/profile)

- 사용자의 기본 정보를 보여주는 공간
  1. 이름
  2. 나이
  3. 학력
  4. 전공
  5. 병역
  6. 취미
  7. Addtional (빈 공간일시 보이지 않음)

### 3. [Project](https://github.com/zojae031/Portfolio/tree/master/app/src/main/java/zojae031/portfolio/project)  

- 사용자의 프로젝트 연혁, 경력정보를 보여주는 공간
  1. 기본 이미지
  2. 제목
  3. 내용
  4. 기술
  5. 시연 영상 주소
  6. 깃허브 주소

### 4. [Tec](https://github.com/zojae031/Portfolio/tree/master/app/src/main/java/zojae031/portfolio/tec)

- 사용자가 사용한 기술을 보여주는 공간
  1. 기본 이미지
  2. 기술 이름
  3. 기술 소스 (빈 공간일시 보이지 않음)
     1. 좌측 소스
     2. 우측 소스
     3. 왼쪽 버튼 이름
     4. 오른쪽 버튼 이름

```
사용된 라이브러리
1. Gilde
2. rxjava2
3. jsoup
4. gson
5. room
6. constraintlayout
7. recyclerview
8. play-services-ads
9. rxandroid

```
