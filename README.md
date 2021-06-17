# 미니게임천국 프로젝트



# [프로젝트 소개]

바쁘다 바빠 현대사회 시간을 많이 쓰는 게임은 필요없다!

미니게임을 모아놓은 미니게임 천국만 있다면 짜투리 시간을 게임에 활용 가능합니다



### 주요 기능

- 지뢰찾기 
- 햄버거 만들기
- 테트리스
- 스네이크
- 2048
- 니편내편



#### 개발 환경

JDK 1.8

IDE : eclipse



#### 프로젝트 기간

2021.06.08 ~ 2021.06.17  (10일)



### 프로젝트 시현 영상

<div>
	<a href="https://www.youtube.com/watch?v=4aJZRGXHV_A" target="_blank"><image src = "https://img.youtube.com/vi/4aJZRGXHV_A/mqdefault.jpg"></a>	
</div>



## [javadoc 참조하려면 누르세요](https://2miri.github.io/java-mini-project//doc/index.html)



# [팀프로젝트를 하면서]



### 본인이 맡았던 기능 소개

- 게임 고르는 첫 화면

- 게임 시작 전 게임 시작 / 게임 방법 화면

- 지뢰찾기

- 햄버거만들기

  

### 만족스러운 점

기존에 있던 게임을 만드는 것이었기에 기존 게임방식을 그대로 구현해내는 것이 목표였는데

기존 게임과 동일하게 만들어내었다. (목표 달성)



클래스 내부에서 중복으로 사용되거나 길어지는 내용들은

모두 메서드로 따로 만들어두었고, 메서드 안에서 사용할 일이있으면 해당 메서드를 선언하였다.

이렇게 하니 에러난 곳을 찾기도 쉽고 정정하는 시간이 많이 줄어들었다.



배우지 못했던 그래픽을 구현하느라 구글링도 하고 유튜브에서 강의도 보면서

스스로 작성했고 시행착오가 있었지만 결과적으로 프로젝트를 에러없이 완료한 점.



기존에 배운 map / ArrayList / 다차원 배열 / 타이머 등을 잘 활용하여

기본적인 세팅에는 큰 어려움이 없었다.



### 힘들었던 점

#### 1. 패널 전환 문제

프레임은 하나인데 패널 여러개 띄워놓고 setVisible로 설정하면

속도가 느릴 것 같아서 기존의 패널을 지우고 새로운 패널을 넣도록 진행했다.

문제는 프레임에 시작패널은 지우고 게임화면패널로 전환하니

다시 시작버튼을 누르려고 하니까 이미 기존에 있던 게임이 진행된 화면으로만 생성 되었다



게임 패널에 부모로 삼을 자료형을 필드에 변수로 선언시키고,

게임패널 생성자에 오버로드 하여 자료형을 넣도록 하니 문제가 해결 되었다.



#### 2. 지뢰찾기

버튼을 클릭 했을 때 0번이 나오면 주변 3x3칸이 모두 펼쳐지고

그 칸 안에서 또 0번이 있으면 해당 0번의 주변 3x3칸도 펼쳐지게 해야하는데,

해당 메서드를 다시 생성시키는 재귀함수를 사용했으나 무한 루프 되었다. 



이 부분에 관해서는 클릭을 했는지의 변수를 필드에 따로 선언한 뒤,

사용자가 버튼을 누른것은 클릭했을 때 true로 지정하고,

0번의 주위는 사용자가 클릭하지 않았으니 false이다.

0 번을 눌렀을 때 주위 3x3에 클릭한 boolean형 변수가 false일때

0번을 눌렀을 때의 메소드를 다시 소환한다.

3x3중에 0이 없으면 재귀함수는 진행되지 않고, 0 번이 있을때만 진행되어 해결 할 수 있었다.



#### 3. 햄버거 만들기

햄버거 게임 화면 상단에 햄버거 문제를 낸다.

문제에 해당하는 햄버거 속재료가 랜덤으로 쌓인 햄버거 이미지가 표시되어야 하고,

사용자가 그 햄버거를 맞출 동안 문제가 변하지 않아야 하는데

게임을 실행하면 문제로 제출된 햄버거가 계속 랜덤으로 모양이 바뀌었다.



게임이 해당 문제를 푸는 동안에는 문제가 변하지 않아야 하므로 

문제에 관련된 패널만 보조클래스로 따로 빼서 작성하였다.

보조 클래스에는 repaint();를 작성하지 않았고 메인 클래스 생성자 내부에 딱 한번만 실행시켜주었다.

사용자가 문제를 맞춰 새로운 문제를 낼 때에는 아예 기존 메인클래스를 지우고 

메인클래스를 새롭게 생성하는 방식으로 해결했다.



### 아쉬운 점

그래픽 부분에서 이미지를 사용하는데에 있어서

원하는 그림을 찾기 쉽지 않았고, 완성작의 퀄리티가 아쉽다.

아직 스킬이 좋지 못해서 개발에 소요되는 시간에 비해  진행되는 속도가 아쉬웠다.



### 느낀 점

학원을 다니고 2달도 채 되지 않았는데 미니 프로젝트를 시작한다고 했을 때에는 

내가 과연 이걸 해낼 수 있을까?라는 조바심이었지만

미니 프로젝트를 진행하면서 느낀점은 역시나 내가 이걸 해낼 수 있을까? 였다.

넣고 싶은 기능이 있는데 열심히 구글링을 하는데 구글링만으로도 시간이 한참 소요되었다.

들인 시간에 비해 진도가 더디다는걸 느꼈고 그럴 때마다 자존심이 상했다.

서당개도 풍월을 읊는 마당에 사람인 내가 구글링을 해도 에러가 뜰 때는 

정말 컴퓨터를 부수고 싶었지만 프로그래머는 정말 시간과의 싸움이라고 했던가? 

포기하지 않고 잠도 안자고 새벽까지  이방법 저방법 다 써보면서 해결해나아갔다.

이 프로젝트를 하면서 프로그래머로써 갖춰야할 태도와 열정을 배웠다.

다음 프로젝트 때에는 지금보다 분명히 더 발전해서 시간대비 퀄리티가 높은 사람이 되고싶다.



### 프로젝트 사용 화면

![game screenshot](https://user-images.githubusercontent.com/83326164/122423253-bf9bce00-cfc8-11eb-8e28-aa1483b2fd71.png)
