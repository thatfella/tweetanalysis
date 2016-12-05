package thatfella.analyze.analyzers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ESALE on 29.11.2016.
 */
public class DictionaryPrepare {
    BufferedReader pbr;
    FileReader pfr;
    BufferedReader nbr;
    FileReader nfr;

    public List<String> gotpos = new ArrayList<String>();
    public List<String> gotneg = new ArrayList<String>();


    public List preppos(String posFileDirection) throws IOException {
        try {
            pfr = new FileReader(posFileDirection);//"E://TwitAnalyze//src//main//resources//positive.txt"

            pbr = new BufferedReader(pfr);
            String temp;
            while ((temp = pbr.readLine()) != null) {
                gotpos.add(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gotpos;
    }


    public List prepneg(String negFileDirection) throws IOException {
        try {
            nfr = new FileReader(negFileDirection);

            nbr = new BufferedReader(nfr);
            String tempneg;
            while ((tempneg = nbr.readLine()) != null) {
                gotneg.add(tempneg);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gotneg
                ;
    }
}
