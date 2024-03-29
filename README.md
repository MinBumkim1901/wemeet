
## 👋 We-Meet
개발자 교육을 받으며 처음으로 협업하여 만든 팀 프로젝트 입니다!
<br>

## ‍💻 사용기술
 - 개발 환경: windows 10
 - 개발 도구: IntelliJ, Github, Ubuntu
 - 데이터 베이스: MariaDB/myBatisXml
 - 프론트 개발 언어: HTML / CSS / JS
 - 백엔드 개발 언어: Java / MariaDB / MyBatis
 - 사용 API: 카카오맵 / 다음 주소
<br>

## ✨ 프로젝트 개요
- MVC 모델을 기반으로 스프링 프레임워크를 이용하여 만든 사용자가 자유롭게 자신의 관심사나 취미를 공유하여 비슷한 취미를 가진 사람들의 모임을 가질수 있도록 해주는 웹사이트를 목표로 제작했습니다.

## ✨ 참여
- 인원: 5명(1명 중도포기)
- 기간: 23.06.10 ~ 23.08.14

## ✨ 담당 기능 소개

- <b>1. 게시판</b>
  - 게시판의 CRUD
    - 사용자는 게시글을 생성(Create), 읽기(Read), 수정(Update), 삭제(Delete)할 수 있습니다.
  - 카테고리 별 분류
    - 여러 가지 카테고리로 나뉘어져 있어서 사용자는 관심 있는 주제나 내용에 대한 글을 쉽게 찾을 수 있습니다.
  - 좋아요/신고 기능
    - 사용자는 관심있는 게시글에 대해 좋아요나 신고를 할 수 있습니다. 로그인후 이용이 가능합니다.
  - 게시글 참여 기능
    - 관심있는 게시물에 참여할수 있는 기능입니다. 참여시 자신의 프로필사진과 닉네임이 게시물에 등록됩니다.
  - 다른 게시물 표시
    - 조회시, 데이터베이스에 저장된 게시물 중 무작위로 선정한 상위 6개의 다른 게시물을 나타냅니다.<br>
      이를 통하여,사용자는 다른 주제의 게시물을 접하실 수 있습니다.
    
    
- <b>2. 1대1문의</b>
  - 1대1문의 CRUD
    - 사용자는 문의글을 생성(Create), 읽기(Read), 수정(Update), 삭제(Delete)할 수 있습니다.
  - 관리자의 답변 작성
    - 관리자만이 사용자의 답변을 작성할수 있습니다.
  
  

- <b>3. 회원관리(관리자)</b>
  - 회원조회 기능
    - DB에 등록된 사용자들의 정보를 조회하는 기능입니다. <br>이를 통해 관리자는 시스템에 등록된 회원들의 목록을 확인하고, 필요한 정보를 찾을 수 있습니다.
  - 회원삭제 기능
    - 관리자는 필요한 경우 회원을 삭제할 수 있습니다. 이를 통해 비활성 계정을 정리할 수 있습니다.

## ✨ DB 스키마 구조
<img width="450" height="400" src="./ProjectWeMeet/src/main/resources/static/resources/images/ReadMe/schema.png">

## 🔗 배포 링크
<a href="https://wemeet.minbumkim.com/" target="_blank">we-meet 웹 페이지</a>
