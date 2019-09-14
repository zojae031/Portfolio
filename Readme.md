# Protfolio

# 프로젝트 구성도
![Architecture](https://user-images.githubusercontent.com/31091115/64670125-ef867b00-d49e-11e9-8342-1aa8d699556c.png)

<hr>  

```
자신의 포트폴리오로 사용하고 싶을시

1. 자신의 Repository로 Fork
2. Issue생성 
3. Json 데이터 변경
4. RemoteDataSourceImpl.kt 파일의 urlList 수정
5. MainActivity.kt 의 showUserImage Gilde.load() url 수정 //추후 변경 예정
```

# 프로젝트 구조

#### [Repository](https://github.com/zojae031/Portfolio/tree/master/app/src/main/java/zojae031/portfolio/data)

- Repository 패턴을 적용하여 GitHub Issue (서버대용)을 크롤링한다. [RemoteDataSourceImpl](https://github.com/zojae031/Portfolio/blob/master/app/src/main/java/zojae031/portfolio/data/datasource/remote/RemoteDataSourceImpl.kt)
- 성공시 Local DataBase(Room)에 저장(Cahing)을 한다. [LocalDataSourceImpl](https://github.com/zojae031/Portfolio/blob/master/app/src/main/java/zojae031/portfolio/data/datasource/local/LocalDataSourceImpl.kt)
- 인터넷 연결이 안되어 있으면 Local에서 가지고 온다.
- 앱 실행 후 Remote에서 최초 가져온 이후는 Local에서 가지고 온다.


#### MVP Pattern

- MVP 패턴을 적용
- View와 Presenter의 1:1 대응
- Adapter 에도 MVP 패턴을 적용
- Passive한 VIew를 구성



### 1. [Main](https://github.com/zojae031/Portfolio/tree/master/app/src/main/java/zojae031/portfolio/main)

 - 내부 ViewPager와 LinearLayout을 사용한 Indicator를 이용하여 세가지 정보를 보여줌
   1. 기본정보
   2. 프로젝트
   3. 기술

### 2. [Profile](https://github.com/zojae031/Portfolio/tree/master/app/src/main/java/zojae031/portfolio/profile)

- 사용자의 기본 정보를 보여주는 공간
  - 이름
  - 나이
  - 학력
  - 전공
  - 병역
  - 취미
  - Addtional (취미를 보여주고싶다면 Json에 데이터를 넣을 시 Visable)
### 3. [Project](https://github.com/zojae031/Portfolio/tree/master/app/src/main/java/zojae031/portfolio/project)

- 사용자의 프로젝트 연혁, 경력정보를 보여주는 공간

  - 기본 이미지
  - 제목
  - 내용
  - 기술
  - 시연 영상 주소
  - 깃허브 주소

### 4. [Tec](https://github.com/zojae031/Portfolio/tree/master/app/src/main/java/zojae031/portfolio/tec)


- 사용자가 사용한 기술을 보여주는 공간

  - 기본 이미지
  - 기술 이름
  - 보여줄 소스 1 (제목)
  - 보여줄 소스 2 (제목)
```
사용된 라이브러리
1. Gilde
2. rxjava2
3. jsoup
4. gson
5. room
6. constraintlayout
7. recyclerview

```
