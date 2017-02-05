package org.thatfella.analyzers;

import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import org.apache.log4j.Logger;

import java.io.*;

import java.util.*;


public class AnalyzerOfTweets implements AnalyzeInterface {
    public List<Status> containingYourHashtag = new ArrayList<Status>();
    List<String> positive = new ArrayList<String>();
    List<String> negative = new ArrayList<String>();
    List<String> mixed = new ArrayList<String>();

    //used to draw Pie in MyController draw
    private int poscount;
    private int negcount;
    private int mixcount;
    private String cKey;
    private String cSecret;
    private String aToken;
    private String aTokenSecret;
    private String makeHashtag;
    twitter4j.Twitter twitter;
    List<String> posdone;
    List<String> negadone;

    private DBConnect dc;

    @Autowired
    public void setDc(DBConnect dc) {
        this.dc = dc;
    }

    private Logger logger = Logger.getLogger(AnalyzerOfTweets.class);

    private DictionaryPrepare dp;

    @Autowired
    public void setDp(DictionaryPrepare dp) {
        this.dp = dp;
    }

    //preparing all stuff we need
    public void setConn() {
        //Preparing dicts
        try {
            posdone = dp.getPositiveDict(dp.getPositiveFileDirection());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            negadone = dp.getNegativeDict(dp.getNegativeFileDirection());
        } catch (IOException e) {
            e.printStackTrace();
        }
//Preparing twitter Connection
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(cKey, cSecret);
        twitter.setOAuthAccessToken(new AccessToken(aToken, aTokenSecret));
        System.out.println(cKey);
    }


    //This method analyzes tweets and finds bad/good/mixed
    public String AnalyzeHashtag(String hashtag) {
        for (String aPosdone : posdone) {
            for (String aNegadone : negadone) {
                //for positives
                for (Status aContainingYourHashtag : containingYourHashtag) {
                    String[] words = aContainingYourHashtag.getText().split("\\s+");
                    for (int i = 0; i < words.length; i++) {
                        words[i] = words[i].replaceAll("[^\\w]", "");
                        if (aPosdone.toLowerCase().equals((words[i]).toLowerCase())) {
                            if (positive.contains("USER :" + aContainingYourHashtag.getUser().getName() + " " + aContainingYourHashtag.getText()) == false) {
                                positive.add("USER :" + aContainingYourHashtag.getUser().getName() + " " + aContainingYourHashtag.getText());
                            }
                        }
                    }
                }


                //for negatives

                for (Status aContainingYourHashtag : containingYourHashtag) {
                    String[] words2 = aContainingYourHashtag.getText().split("\\s+");
                    for (int i = 0; i < words2.length; i++) {
                        words2[i] = words2[i].replaceAll("[^\\w]", "");
                        if (aNegadone.toLowerCase().equals((words2[i]).toLowerCase())) {
                            if (negative.contains("USER :" + aContainingYourHashtag.getUser().getName() + " " + aContainingYourHashtag.getText()) == false) {
                                negative.add("USER :" + aContainingYourHashtag.getUser().getName() + " " + aContainingYourHashtag.getText());

                            }
                        }
                    }
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
        return "Done";
    }


    //Operations with Hashtag: Search, qualifying - positive/negative or mixed
    public String searchHashtag(String hashtag, int numberOfTweetsPerPage) throws IOException {
        //hashtag we are looking for  makeHashtag adds # to your query
        makeHashtag = "#" + hashtag;
        Query yourHashtag = new Query(makeHashtag);
        yourHashtag.setCount(numberOfTweetsPerPage);

        //поиск по хэштегу
        try {
            QueryResult hashtagRes = twitter.search(yourHashtag);
            containingYourHashtag = hashtagRes.getTweets();

        } catch (TwitterException e) {
            e.printStackTrace();
        }
        //logging our tweets via log4j

        for (Status t : containingYourHashtag) {
            logger.info("TWEETSTARTSHERE# \n" + "TweetId" + t.getId() + "\n USER :" + t.getUser().getName() + " \n" + " TEXT: " + t.getText() + " \n" + " COUNTRY " + t.getUser().getLocation() + " \n" + " LANGUAGE: " + t.getLang() + " \n" + " DATE OF POST: " + t.getCreatedAt() + " \n" + " \n");

        }

        System.out.println("Writing Complete!!!");
//calling analyzing method
        AnalyzeHashtag(makeHashtag);
//summarizing our analysis
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
        //possibility of adding our results in databace via jdbc
        dc.addAnResult(hashtag, result);

        return result;

    }


    public void setcKey(String cKey) {
        this.cKey = cKey;
    }

    public String getcKey() {
        return cKey;
    }

    public void setcSecret(String cSecret) {
        this.cSecret = cSecret;
    }

    public String getcSecret() {
        return cSecret;
    }

    public void setaToken(String aToken) {
        this.aToken = aToken;
    }

    public String getaToken() {
        return aToken;
    }

    public void setaTokenSecret(String aTokenSecret) {
        this.aTokenSecret = aTokenSecret;
    }

    public String getaTokenSecret() {
        return aTokenSecret;
    }

    public int getPoscount() {
        return poscount;
    }

    public int getNegcount() {
        return negcount;
    }

    public int getMixcount() {
        return mixcount;
    }


}
