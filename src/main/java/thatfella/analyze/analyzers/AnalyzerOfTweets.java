package thatfella.analyze.analyzers;

import javafx.scene.chart.PieChart;
import org.jfree.data.general.PieDataset;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.*;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.*;


public class AnalyzerOfTweets implements AnalyzeInterface {
    public List<Status> containingYourHashtag = new ArrayList<Status>();
    List<String> positive = new ArrayList<String>();
    List<String> negative = new ArrayList<String>();
    List<String> mixed = new ArrayList<String>();

    //used to draw Pie in MyController draw
    public int poscount;
    public int negcount;
    public int mixcount;

    //Operations with Hashtag: Search, qualifying - positive/negative or mixed
    public String searchHashtag(String hashtag, int numberOfTweetsPerPage) throws IOException {

        //Preparing dicts
        List<String> posdone;
        List<String> negadone;
        DictionaryPrepare dp = new DictionaryPrepare();
        posdone = dp.preppos("E://TwitAnalyze//src//main//resources//positive.txt");
        negadone = dp.prepneg("E://TwitAnalyze//src//main//resources//negative.txt");

        //Подключение к Твиттеру
        twitter4j.Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("B2XIyVCvS2vN4qmev0qpHvwsh", "KgfuK5IOowWwGiVhfSvWjScll0obKmlAX8sMcxB8Xt7lJf1mos");
        twitter.setOAuthAccessToken(new AccessToken("800966895235137536-NIYhcVxpYo8A5UmeuIDwaWWKrEsOnlW", "4CjmK3rU45eTmaH0nmsVesdbDbi1evyqWaGtr346L3yKR"));

        //это хэштег, который мы ищем
        Query yourHashtag = new Query(hashtag);
        yourHashtag.setCount(numberOfTweetsPerPage);
        //поиск по хэштегу
        try {
            QueryResult hashtagRes = twitter.search(yourHashtag);
            containingYourHashtag = hashtagRes.getTweets();

        } catch (TwitterException e) {
            e.printStackTrace();
        }
        //запись в файл Lasttweets в формате <Отметка о начале твита>-Имя Юзера-Текст Твита-Местность-Язык-Дата
        try {
            FileWriter writer = new FileWriter("E://TwitAnalyze//LATESTTWEETS.txt", false);
            // запись всей строки
            for (Status t : containingYourHashtag) {
                writer.write("TWEETSTARTSHERE#" + " \n" + "USER :" + t.getUser().getName() + " \n" + " TEXT: " + t.getText() + " \n" + " COUNTRY " + t.getUser().getLocation() + " \n" + " LANGUAGE: " + t.getLang() + " \n" + " DATE OF POST: " + t.getCreatedAt() + " \n" + " \n");
            }

            writer.close();
            System.out.println("Writing Complete!!!");
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }


// ЭТО ПОИСК СЛОВ ИЗ СЛОВАРЕЙ В РЕЗУЛЬТАТАХ ПОИСКА

        for (String aNegadone : negadone) {
            for (Status aContainingYourHashtag : containingYourHashtag)
                if (aContainingYourHashtag.getText().toLowerCase().contains(aNegadone.toLowerCase())) {
                    if (negative.contains("USER :" + aContainingYourHashtag.getUser().getName() + " " + aContainingYourHashtag.getText()) == false) {
                        negative.add("USER :" + aContainingYourHashtag.getUser().getName() + " " + aContainingYourHashtag.getText());
                    }
                }


        }

        for (String aPosdone : posdone) {
            for (Status aContainingYourHashtag : containingYourHashtag)
                if (aContainingYourHashtag.getText().toLowerCase().contains(aPosdone.toLowerCase())) {
                    if (positive.contains("USER :" + aContainingYourHashtag.getUser().getName() + " " + aContainingYourHashtag.getText()) == false) {
                        positive.add("USER :" + aContainingYourHashtag.getUser().getName() + " " + aContainingYourHashtag.getText());
                    }
                }

        }

// Searching for tweets containing both positive and negative words and adding it to MIXED collection (simultaneously clearing POSITIVE AND NEGATIVE COLLECTIONS)
        for (int p = 0; p < positive.size(); p++) {
            for (int n = 0; n < negative.size(); n++) {
                if ((negative.get(n).toLowerCase().contains(positive.get(p).toLowerCase())) || (positive.get(p).toLowerCase().contains(negative.get(n).toLowerCase()))) {
                    if ((negative.get(n).toLowerCase().contains(positive.get(p).toLowerCase())) || (positive.get(p).toLowerCase().contains(negative.get(n).toLowerCase())) == false) {
                        mixed.add(negative.get(n));
                        positive.remove(positive.get(p));
                        negative.remove(negative.get(n));
                    }
                }
            }

        }

        poscount = positive.size();
        negcount = negative.size();
        mixcount = mixed.size();
        System.out.println("TOTAL AMOUNT OF TWEETS " + yourHashtag.getCount());
        System.out.println("POSITIVE TWEETS \n");
        for (String aPositive : positive) {
            System.out.println(aPositive);
        }
        System.out.println("NEGATIVE TWEETS \n");
        for (String aNegative : negative) {
            System.out.println(aNegative);
        }

        System.out.println("MIXED TWEETS \n");
        for (String aMixed : mixed) {
            System.out.println(aMixed);
        }


        System.out.println("Containing positive  " + positive.size() + "  negative " + negative.size() + " " + "  Mixed " + mixed.size() + "\n");


        String result = "Containing positive  " + positive.size() + "  negative " + negative.size() + " mixed " + mixed.size();

        return result;

    }

}


