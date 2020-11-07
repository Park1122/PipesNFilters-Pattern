package Components.Middle;

import Framework.GeneralFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class AddMiddleFilter extends GeneralFilter {
    public AddMiddleFilter(int totalOutPort, int totalInPort) {
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
        int cr = 13; // carriage return

        for (;;) {
            while (databyte != '\n' && databyte != -1) {
//                databyte = in.read();
                if (databyte == -1){
                    return;
                }
                if (databyte != cr){
                    line += (char) databyte;
                }

                if (databyte == cr) {
                    String[] split = line.split(" ");
                    Collections.addAll(temp, split);
                    if (!line.contains("12345")) {
                        temp.add("12345");
                    }
                    if (!line.contains("23456")) {
                        temp.add("23456");
                    }
                    if (!line.contains("17651")) {
                        temp.add("17651");
                    }

                    for (int i = 0; i < temp.size(); i++) {
//                        out.write(temp.elementAt(i).getBytes());
                        if(i == temp.size()-1){
//                            out.write(cr);
                        }else {
//                            out.write(32);
                        }
                    }
                    line = "";
                    temp.clear();
                }
            }
            databyte = '\0';
        }
    }

}
