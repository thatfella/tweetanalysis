package thatfella.analyze.analyzers;

import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.util.List;

public interface AnalyzeInterface {

    public String searchHashtag(String hashtag, int numberOfTweetsPerPage) throws IOException;


}