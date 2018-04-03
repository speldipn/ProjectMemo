package com.speldipn.memo;

import java.util.Scanner;

public class memoMain {

	public static final String version = "v0.0.1";
	
	public static void main(String[] args) {
		// 메모장 만들기 프로젝트
		
		// 키보드 입력으로 명령어를 먼저 받는다.
		// - 프로그램이 시작되면 명령어 번호를 보여준다
		// 1. 쓰기 2 읽기  3. 수정  4. 삭제 5. 종료
		
		System.out.println("Memo Program(" + version + ") by Joonyoung");
		 
		Memo memo = new Memo();
		Scanner scanner = new Scanner(System.in);
		boolean runFlag = true;
		
		while(runFlag) {
			memo.showCommand();
			
			String cmd = scanner.nextLine();
			switch(cmd) {
			/* 쓰기 */case "1": memo.write(scanner); break; 
			/* 읽기 */case "2": memo.list();; break;
			/* 수정 */case "3": memo.modify(); break;
			/* 삭제 */case "4": memo.remove(); break;
			/* 종료 */case "0": runFlag = false; break;
			default: System.out.println("명령어가 잘못되었습니다.");break;		
			}
			System.out.println();
		}
		System.out.println("[ 프로그램이 종료되었습니다 ]");
	}
}
