package Components.Middle; /**
 * Copyright(c) 2013 All rights reserved by JU Consulting
 */

import Framework.GeneralFilter;

import java.io.IOException;


/**
 * @author Jungho Kim
 * @date 2019
 * @version 1.1
 * @description
 *      입력값으로 받은 byte들을 아무 기능도 하지 않고 그대로 전송하는 기능을 한다.
 */
public class CSMiddleFilter extends GeneralFilter {
    public CSMiddleFilter(int totalOutPort, int totalInPort) {
        super(totalOutPort, totalInPort);
    }
    @Override
    public void setFilePath(String filePath) {
    }

    @Override
    public void specificComputationForFilter() throws IOException {
        int checkBlank = 4;
        int databyte = 0;
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        boolean isCS = false;

        while (true) {
            while (databyte != '\n' && databyte != -1) {
//                databyte = in.read();
                if (databyte == ' ') {
                    numOfBlank++;
                }
                if (databyte != -1) {
                    buffer[idx++] = (byte) databyte;
                }
                if (numOfBlank == checkBlank && buffer[idx - 3] == 'C' && buffer[idx - 2] == 'S') {
                    isCS = true;
                }
            }

            if (isCS) {
                for (int i = 0; i < idx; i++) {
//                    out.write((char) buffer[i]);
                }
                isCS = false;
            }
            if (databyte == -1) {
//                out.close();
                return;
            }
            idx = 0;
            numOfBlank = 0;
            databyte = '\0';
        }
    }

}
