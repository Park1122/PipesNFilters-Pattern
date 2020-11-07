package Components.Source; /**
 * Copyright(c) 2019 All rights reserved by JU Consulting
 */

import Framework.GeneralFilter;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Copyright(c) 2013 All rights reserved by JU Consulting
 */


/**
 * @author Jungho Kim
 * @date 2019
 * @version 1.1
 * @description
 *      입력값 파일에서 텍스트를 읽어들여서 output port에 기록하는 클래스.
 */
public class SourceFilter extends GeneralFilter {
    private String filePath;

    public SourceFilter(int totalOutPort, int totalInPort){
        super(totalOutPort, totalInPort);
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath; //inputFilePath
    }

    @Override
    public void specificComputationForFilter() throws IOException {
        int byte_read;
        try { 
            @SuppressWarnings("resource")
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(new File(filePath)));
            for(;;) {
                byte_read = br.read();
                if (byte_read == -1) 
                { return; }
                out.get(0).write(byte_read);
            }
        } 
        catch (IOException e) {
            if (e instanceof EOFException) return;
            else System.out.println(e);
        }
        finally {
        	try {
                out.get(0).close();
        	} 
        	catch (IOException e) {
        		e.printStackTrace();} 
        	}
    }
}
