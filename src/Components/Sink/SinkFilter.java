package Components.Sink; /**
 * Copyright(c) 2019 All rights reserved by JU Consulting
 */

import Framework.GeneralFilter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Copyright(c) 2013 All rights reserved by JU Consulting
 */


/**
 * @author Jungho Kim
 * @date 2019
 * @version 1.1
 * @description
 *      입력값으로 들어온 byte들을 읽어 CS 값만 출력값 파일에 기록하는 클래스.
 */
public class SinkFilter extends GeneralFilter {
    private String filePath;

    public SinkFilter(int totalOutPort, int totalInPort){
        super(totalOutPort, totalInPort);
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void specificComputationForFilter() throws IOException {
        int byte_read;

        try {
            FileWriter fw = new FileWriter(this.filePath);
            while(true) {
                    byte_read = in.get(0).read();

                    if(byte_read == -1){
                        fw.close();
                        System.out.println( "::Filtering is finished; Output file is created." );
                        return;
                    }
                    fw.write((char)byte_read);
            }
        } catch (Exception e) {
            closePorts();
            e.printStackTrace();
        }
    }

}
