package thatfella.analyze;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import thatfella.analyze.analyzers.AnalyzerOfTweets;
import thatfella.analyze.analyzers.PieCooker;
//import thatfella.analyze.analyzers.PieCooker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class MyController {
    private AnalyzerOfTweets sa = new AnalyzerOfTweets();
    private PieCooker pc = new PieCooker();

    @Autowired
    public void setSa(AnalyzerOfTweets sa) {
        this.sa = sa;
    }

    @Autowired
    public void setPc(PieCooker pc) {
        this.pc = pc;
    }


    @RequestMapping(value = {"/ht", "/ht/"}, method = RequestMethod.GET)
    public String Result(@RequestParam(value = "hashtag", required = false) String hashtag, @RequestParam(value = "numberOfTweetsPerPage", required = false) int numberOfTweetsPerPage, Map<String, Object> reqPar, HttpServletResponse response) {
        response.setContentType("image/png");

        reqPar.put("hashtag", hashtag);
        String hst = (String) reqPar.get("hashtag");
        try {
            reqPar.put("hst", sa.searchHashtag(hst, numberOfTweetsPerPage));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "/WEB-INF/views/res.jsp";
    }


    @RequestMapping(value = "/piechart", method = RequestMethod.GET)
    public void drawPieChart(HttpServletRequest request,
                             HttpServletResponse response) {
        response.setContentType("image/png");

        PieDataset pds = pc.createDataForPie("positive", "negative", "mixed", sa.poscount, sa.negcount, sa.mixcount);
        JFreeChart jfc = null;
        try {
            jfc = pc.drawPie(pds, "Results Pie");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), jfc,
                    750, 400);
            response.getOutputStream().close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

}
