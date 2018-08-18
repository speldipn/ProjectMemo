# ProjectMemo

자바 학습을 목적으로 만든 콘솔용 메모 프로그램

## 기능
* 쓰기
* 읽기
* 수정
* 삭제
* 종료

## 프로그램 구조
### Memo 클래스

* 사용할 수 있는 기능들을 표시  
````java
	public void showCommand()	
````

* Scanner 클래스를 사용하여 콘솔로부터 메모내용을 입력받는다. 
````java
public void write(Scanner scanner)
````

* 파일명을 사용하여 수정할 파일의 존재유무를 확인하고, 있다면 그 내용을 출력되고 수정가능하다.
````java
public void modify()
````

* 파일명을 사용하여 파일을 제거한다.
````java
public void remove()
````
