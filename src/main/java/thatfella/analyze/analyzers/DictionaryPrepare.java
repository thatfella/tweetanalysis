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

    private String positiveFileDirection;
    private String negativeFileDirection;
    String pd = getPositiveFileDirection();

    public List getPositiveDict(String positiveFileDirection) throws IOException {
        return loadDictionary(getPositiveFileDirection());
    }

    public List getNegativeDict(String negativeFileDirection) throws IOException {
        return loadDictionary(getNegativeFileDirection());
    }

    private List<String> loadDictionary(String fileDirection) throws IOException {
        List<String> dict = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileDirection));
            String temp;
            while ((temp = br.readLine()) != null) {
                dict.add(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dict;
    }

    public void setPositiveFileDirection(String positiveFileDirection) {
        this.positiveFileDirection = positiveFileDirection;
    }

    public String getPositiveFileDirection() {
        return positiveFileDirection;
    }

    public void setNegativeFileDirection(String negativeFileDirection) {
        this.negativeFileDirection = negativeFileDirection;
    }

    public String getNegativeFileDirection() {
        return negativeFileDirection;
    }
}
