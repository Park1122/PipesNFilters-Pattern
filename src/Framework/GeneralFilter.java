package Framework; /**
 * Copyright(c) 2019 All rights reserved by JU Consulting
 */

import java.io.EOFException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * @author Jungho Kim
 * @date 2019
 * @version 1.1
 * @description
 *      Framework.CommonForFilter 인터페이스를 구현한 추상클래스. 모든 Filter는 본 Filter를 상속받아 구현하게 된다.
 */
public abstract class GeneralFilter implements CommonForFilter {
    // 여러 개의 입력, 출력 포트를 가지기 위해 리스트로 구현
    protected ArrayList<PipedInputStream> in = new ArrayList<>();
    protected ArrayList<PipedOutputStream> out = new ArrayList<>();

    public GeneralFilter(int totalOutPort, int totalInPort) { // 총 출력포트 개수, 입력 포트 개수가 파라미터로 들어온다.
        // 출력 포트를 파라미터로 받은 값을 기준으로 생성
        for(int i=0; i<totalOutPort; i++)
            out.add(i, new PipedOutputStream());
        // 입력 포트를 파라미터로 받은 값을 기준으로 생성
        for(int i=0; i<totalInPort; i++)
            in.add(i, new PipedInputStream());
    }

    /**********Implementation of public methods defined in Framework.CommonForFilter interface************/

    // 출력포트를 다음 필터와 다음 필터의 입력포트를 파라미터로 입력하여 서로 연결
    public void connectOutputTo(CommonForFilter nextFilter, int numOfOutputPort, int numOfInputPort) throws IOException {
        out.get(numOfOutputPort).connect(nextFilter.getPipedInputStream().get(numOfInputPort));
    }
    // 입력포트를 이전 필터와 이전 필터의 출력포트를 파라미터로 입력하여 서로 연결
    public void connectInputTo(CommonForFilter previousFilter, int numOfInputPort, int numOfOutputPort) throws IOException {
        in.get(numOfInputPort).connect(previousFilter.getPipedOutputStream().get(numOfOutputPort));
    }
    
    public ArrayList<PipedInputStream> getPipedInputStream() {
        return in;
    }
    
    public ArrayList<PipedOutputStream> getPipedOutputStream() {
        return out; 
    }

/**********Implementation of public methods defined in Runnable interface************/    
    public void run() {
        try { 
            specificComputationForFilter(); 
        } catch (IOException e) {
            if (e instanceof EOFException) return;
            else System.out.println(e);
        } finally {
            try {
                // for문을 통해 출력포트와 입력포트를 리스트에서 꺼내어 종료
                for (PipedOutputStream pipedOutputStream : out) {
                    pipedOutputStream.close();
                }
                for (PipedInputStream pipedInputStream : in) {
                    pipedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }

/**********Implementation of protected methods************/    
    
    /**
     * Filter가 작동을 정지하기 전에 Input/Output Stream port를 닫는다.
     */
    protected void closePorts() {
        try {
            for (PipedOutputStream pipedOutputStream : out) {
                pipedOutputStream.close();
            }
            for (PipedInputStream pipedInputStream : in) {
                pipedInputStream.close();
            }
//            in.close();
//            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
    }
    
/**********Abstract method that should be implemented************/    
    
    /**
     * 각 Filter의 특수한 기능이 이곳에 기록되며, 이 메소드는 run()에 의해 호출됨으로써 Filter가 기능하게 된다.
     * @throws IOException
     */
    abstract public void specificComputationForFilter() throws IOException;

}
