package com.speldipn.memo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Memo {

	public static final String MEMO_DIR = "/Temp/Memo/";

	// 종료 커맨드를 상수로 정의
	public static final String EXIT = "/exit";

	// 생성자에서 메모를 생산할 디렉토리를 생성해준다.
	public Memo() {
		File file = new File(MEMO_DIR);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	// 1. 명령어를 출력하는 함수
	public void showCommand() {
		System.out.println("1.쓰기 2.읽기 3.수정 4.삭제 0.프로그램 종료");
		System.out.print("명령 번호를 입력하세요 : ");
	}

	// 2. 메모를 쓰는 함수
	public void write(Scanner scanner) {
		StringBuilder content = new StringBuilder();
		System.out.println("--- 쓰기 모드(완료:/exit) ---");

		// 키보드 입력을 받아야 한다.
		while (true) {
			String line = scanner.nextLine();
			if (line.equals(EXIT)) {
				break;
			} else {
				content.append(line + "\r\n");
			}
		}

		// 작성한 내용이 있으면 파일로 쓴다.
		if (!content.toString().equals("")) {
			// 가. 현재 시간을 가져와서 파일명으로 만든다.
			long current = System.currentTimeMillis();
			// 나. 년_월_일 포맷으로 파일을 만들고 저장한다.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
			String fileName = sdf.format(current) + ".txt";

			Path path = Paths.get(MEMO_DIR, fileName);
			try {
				Files.write(path, content.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("# 메모를 등록하였습니다.");
		}
	}

	// 3. 메모를 출력하는 함수
	public void list() {
		File file = new File(MEMO_DIR);
		String list[] = file.list();
		if (list.length == 0) {
			System.out.println("[확인] 읽을 파일이 없습니다.");
		} else {
			int no = 1;
			for (String fileName : list) {
				System.out.println("[ " + no + " ] " + fileName);
				no = no + 1;
			}
			System.out.print("파일 번호을 입력하세요: ");
			Scanner scanner = new Scanner(System.in);
			String selectStr = scanner.nextLine();
			int selectNo = Integer.valueOf(selectStr);
			if (selectNo > 0 && selectNo <= list.length) {
				String fileName = list[Integer.valueOf(selectNo) - 1];

				// 입력된 파일명에 메모가 존재하는 디렉토리 경로를 붙인다.
				fileName = MEMO_DIR + fileName;
				file = new File(fileName);
				FileReader fr = null;
				BufferedReader br = null;
				try {
					fr = new FileReader(file);
					br = new BufferedReader(fr);
					String line = "";
					while ((line = br.readLine()) != null) {
						System.out.println(line);
					}
				} catch (Exception e) {
					System.out.println("[확인] 파일을 읽는중 문제가 발생하였습니다.");
				} finally {
					try {
						if (br != null) {
							br.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						if (fr != null) {
							fr.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("[확인] 파일 번호를 알 수 없습니다.");
			}
		}
	}

	// 4. 메모를 수정하는 함수
	public void modify() {
		File file = new File(MEMO_DIR);
		String list[] = file.list();
		if (list.length == 0) {
			System.out.println("[확인] 수정할 파일이 없습니다.");
		} else {
			int no = 1;
			for (String fileName : list) {
				System.out.println("[ " + no + " ] " + fileName);
				no = no + 1;
			}
			System.out.print("파일 번호을 입력하세요: ");
			Scanner scanner = new Scanner(System.in);
			String selectStr = scanner.nextLine();
			int selectNo = Integer.valueOf(selectStr);
			if (selectNo > 0 && selectNo <= list.length) {
				String fileName = list[Integer.valueOf(selectNo) - 1];

				// 입력된 파일명에 메모가 존재하는 디렉토리 경로를 붙인다.
				fileName = MEMO_DIR + fileName;
				file = new File(fileName);

				List<String> fList = new ArrayList<>();
				FileReader fr = null;
				BufferedReader br = null;
				try {
					fr = new FileReader(file);
					br = new BufferedReader(fr);
					String line = "";
					int lineNumber = 1;
					while ((line = br.readLine()) != null) {
						System.out.println(lineNumber + ": \"" + line + "\"");
						lineNumber = lineNumber + 1;
						fList.add(line);
					}
					System.out.print("수정하려는 줄의 번호를 입력하세요: ");
					boolean isChanged = false;
					int modifyLine = Integer.valueOf(scanner.nextLine());
					if (modifyLine > 0 && modifyLine <= fList.size()) {
						System.out.print(fList.get(modifyLine - 1) + " => ");
						String newLine = scanner.nextLine();
						if (newLine.length() > 0) {
							fList.set(modifyLine - 1, newLine);
							isChanged = true;
						} else {
							System.out.println("[확인] 수정된 내용이 없습니다.");
						}
					} else {
						System.out.println("[확인] 없는 줄번호입니다.");
					}

					if (isChanged) {
						FileWriter fw = null;
						BufferedWriter bw = null;
						try {
							fw = new FileWriter(file);
							bw = new BufferedWriter(fw);
							for (String str : fList) {
								fw.write(str + "\r\n");
							}
							fw.flush();

							System.out.println("# 성공적으로 수정되었습니다.");
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								if (bw != null) {
									bw.close();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							try {
								if (fw != null) {
									fw.close();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} catch (Exception e) {
					System.out.println("[확인] 파일을 쓰는중 문제가 발생하였습니다.");
				} finally {
					try {
						if (br != null) {
							br.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						if (fr != null) {
							fr.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("[확인] 파일 번호를 알 수 없습니다.");
			}
		}
	}

	// 5. 메모를 삭제하는 함수
	public void remove() {
		File file = new File(MEMO_DIR);
		String list[] = file.list();
		if (list.length == 0) {
			System.out.println("[확인] 삭제할 파일이 없습니다.");
		} else {
			int no = 1;
			for (String fileName : list) {
				System.out.println("[ " + no + " ] " + fileName);
				no = no + 1;
			}
			System.out.print("파일 번호을 입력하세요: ");
			Scanner scanner = new Scanner(System.in);
			String selectStr = scanner.nextLine();
			int selectNo = Integer.valueOf(selectStr);
			if (selectNo > 0 && selectNo <= list.length) {
				String fileName = list[Integer.valueOf(selectNo) - 1];
				fileName = MEMO_DIR + fileName;
				file = new File(fileName);
				if (file.exists() && file.isFile()) {
					boolean result = file.delete();
					if (result) {
						System.out.println("# 파일이 정상적으로 삭제되었습니다.");
					} else {
						System.out.println("[확인] 파일을 삭제할 수 없습니다.");
					}
				} else {
					System.out.println("[확인] 삭제하려는 파일이 존재하지 않습니다(" + fileName + ")");
				}
			}
		}
	}
}
