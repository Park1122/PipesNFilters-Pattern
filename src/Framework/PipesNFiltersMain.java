package Framework;

/**
 * Copyright(c) 2019 All rights reserved by JU Consulting
 */


import Components.Middle.SeparateMiddleFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;

/**
 * @author Jungho Kim
 * @date 2019
 * @version 1.1
 * @description
 *     
 */
public class PipesNFiltersMain {

    public static void main(String[] args) {
        try {
            // framework에서는 이 부분을 xml로 처리
            // Filter 선언 및 생성 부분
            CommonForFilter courseSourceFilter = new SourceFilter(1, 1);
            CommonForFilter studentSourceFilter = new SourceFilter(1, 1);
            CommonForFilter sinkFilter1 = new SinkFilter(1, 1);
            CommonForFilter sinkFilter2 = new SinkFilter(1, 1);
            CommonForFilter sepMiddleFilter = new SeparateMiddleFilter(2, 2);

            // 각각의 필터들을 서로 연결하는 부분
            studentSourceFilter.connectOutputTo(sepMiddleFilter, 0, 0);
            courseSourceFilter.connectOutputTo(sepMiddleFilter, 0, 1);
            sepMiddleFilter.connectOutputTo(sinkFilter1, 0, 0);
            sepMiddleFilter.connectOutputTo(sinkFilter2, 1, 0);

            // 소스필터와 싱크필터의 파일 설정
            courseSourceFilter.setFilePath("Courses.txt");
            studentSourceFilter.setFilePath("Students.txt");
            sinkFilter1.setFilePath("Output1.txt");
            sinkFilter2.setFilePath("Output2.txt");

            // 필터를 쓰레드에 할당
            Thread thread1 = new Thread(courseSourceFilter);
            Thread thread2 = new Thread(studentSourceFilter);
            Thread thread6 = new Thread(sepMiddleFilter);
            Thread thread7 = new Thread(sinkFilter1);
            Thread thread8 = new Thread(sinkFilter2);

            thread1.start();
            thread2.start();
            thread6.start();
            thread7.start();
            thread8.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}