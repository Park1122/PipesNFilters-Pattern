package Components.Middle;

import Framework.GeneralFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class DeleteMiddleFilter extends GeneralFilter {
    public DeleteMiddleFilter(int totalOutPort, int totalInPort) {
        super(totalOutPort, totalInPort);
    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void specificComputationForFilter() throws IOException {
        Vector<String> temp = new Vector<>();
        int databyte = 0;
        String line = "";
        int enter = 13;

        for (;;) {
            while (databyte != '\n' && databyte != -1) {
//                databyte = in.read();
                if (databyte == -1){
                    return;
                }
                if (databyte != enter){
                    line += (char) databyte;
                }
                if (databyte == enter) {
                    String[] split = line.split(" ");
                    Collections.addAll(temp, split);
                    if(line.contains("2013") && line.contains("CS")){
                        if(line.contains("17651")){
                            temp.remove("17651");
                        }
                        if(line.contains("17652")){
                            temp.remove("17652");
                        }
                    }

                    for (int i = 0; i < temp.size(); i++) {
//                        out.write(temp.elementAt(i).getBytes());
//                        out.write(32);
                    }
                    line = "";
                    temp.clear();
                }
            }
            databyte = '\0';
        }
    }

}
