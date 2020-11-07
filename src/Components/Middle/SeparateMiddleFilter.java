package Components.Middle;

import Framework.GeneralFilter;

import java.io.IOException;
import java.util.Vector;

public class SeparateMiddleFilter extends GeneralFilter {

    public SeparateMiddleFilter(int totalOutPort, int totalInPort) {
        super(totalOutPort, totalInPort);
    }
    @Override
    public void setFilePath(String filePath){
    }

    @Override
    public void specificComputationForFilter() throws IOException {
        Vector<String> courseList = this.readCourse(); // Courses.txt파일을 읽어 vector에 담기
        this.checkStudentData(courseList); // Course 정보가 담긴 vector를 학생 체크에 파라미터로 넘겨 선수과목 체크에 사용
    }

    private void checkStudentData(Vector<String> courseList) throws IOException{
        while(true) {
            String line = null;
            int databyte = 0;
            while (databyte != '\n' && databyte != -1) {
                databyte = in.get(0).read(); // 0번 포트로 들어오는 학생 정보
                if (databyte != '\n' && databyte != -1) {
                    line += ((char)databyte); // line feed나 파일 끝이 아닌 경우에는 한 줄 만들기
                }
            }
            if(line!=null) { // line이 null이 아닌 경우
                line = line.substring(4); // 빈 줄 제거
            }

            String student = line; //  학생 정보 한 줄 읽기
            if(student == null) break; // 학생이 없으면 종료
            else if(!student.isBlank()) { // 학생이 있는 경우
                boolean isChecked = true; // 체크 여부를 flag로 설정
                for(String course : courseList){ // course 정보가 담기 vector에서 하나씩 꺼내기
                    String[] courseInfoArray = course.split(" ");
                    if (student.contains(courseInfoArray[0])) { // course id를 학생이 가지고 있는지 체크
                        for (int i = 0; i < courseInfoArray.length-3; i++) {
                            if (!student.contains(courseInfoArray[i + 3])) { // course의 선수과목 부분을 학생이 가지고 있는지 체크
                                isChecked = false; // 갖고 있지 않은 경우 flag를 false로 함
                                break;
                            }
                        }
                    }
                }
                if (isChecked) { // 선수 과목을 가진 경우
                    for (int i = 0; i < student.length(); i++) {
                        out.get(0).write(student.charAt(i)); // 0번 포트로 출력(이후 Output1.txt에 저장)
                    }
                }else{ // 선수 과목을 갖지 않은 경우
                    for (int i = 0; i < student.length(); i++) {
                        out.get(1).write(student.charAt(i)); // 1번 포트로 출력(이후 Outout2.txt에 저장)
                    }
                }
            }
        }
    }

    private Vector<String> readCourse() throws IOException{
        Vector<String> courseVector = new Vector<>();
        while(true) {
            String line = null;
            int databyte = 0;
            while (databyte != '\n' && databyte != -1) { // line feed 혹은 파일 끝이 아닌 경우
                databyte = in.get(1).read(); // 1번 입력포트로 들어온 코스 정보 하나 씩 읽어서 변수에 저장
                if (databyte != '\n' && databyte != -1) {
                    line += ((char)databyte); // 한 줄씩 만들어 line에 저장
                }
            }
            if(line!=null) {
                line = line.substring(4);
            }// 빈 줄 지우기

            String course = line; // course 정보 한 줄씩 받아오기
            if(course==null) break; // course가 없는 경우 종료
            else if (!course.isBlank()) { // course가 있는 경우
                courseVector.add(course.replace("\r", "")); // carriage return을 ""로 치환하여 벡터에 저장
            }
        }
        return courseVector;
    }
}
