package org.thatfella;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thatfella.analyzers.PieCooker;
import org.thatfella.analyzers.AnalyzerOfTweets;
//import PieCooker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class MyController {


    private AnalyzerOfTweets sa;
    private PieCooker pc;

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
        sa.setConn();
        String hst = (String) reqPar.get("hashtag");

        try {

            reqPar.put("hst", sa.searchHashtag(hst, numberOfTweetsPerPage));
            return "/WEB-INF/views/res.jsp";

        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e);
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            reqPar.put("error", e.getMessage());
            return "/WEB-INF/views/error.jsp";
        }

    }

    //drawing a pie
    @RequestMapping(value = "/piechart", method = RequestMethod.GET)
    public void drawPieChart(HttpServletRequest request,
                             HttpServletResponse response) {
        response.setContentType("image/png");

        PieDataset pds = pc.createDataForPie("positive", "negative", "mixed", sa.getPoscount(), sa.getNegcount(), sa.getMixcount());
        JFreeChart jfc = null;
        try {
            jfc = pc.drawPie(pds, "Results Pie");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), jfc,
                    pc.getWidth(), pc.getHeight());
            response.getOutputStream().close();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }


}
