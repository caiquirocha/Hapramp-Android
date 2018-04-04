package com.hapramp;

import com.hapramp.steem.Communities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Ankit on 3/23/2018.
 */

public class CommunitiesUnitTest {

    public ArrayList<String> communities;
    public static final String PHOTOGRAPHY = "hapramp-photography";
    public static final String ART = "hapramp-art";
    public static final String MUSIC = "hapramp-music";
    public static final String FASHION = "hapramp-fashion";
    public static final String DANCE = "hapramp-dance";
    public static final String DRAMATICS = "hapramp-dramatics";
    public static final String TRAVEL = "hapramp-travel";
    public static final String LITERATURE = "hapramp-literature";


    @Before
    public void populateCommunityList(){

        communities = new ArrayList<>();
        communities.add(PHOTOGRAPHY);
        communities.add(ART);
        communities.add(MUSIC);
        communities.add(FASHION);
        communities.add(DANCE);
        communities.add(DRAMATICS);
        communities.add(TRAVEL);
        communities.add(LITERATURE);

    }

    @Test
    public void testMethod_doesCommunityExists(){
        ///System.out.println(String.format("%1$s/%2$s","ankit","kumar"));
        //true test
        assert (Communities.doesCommunityExists("hapramp-art"));
        assert (Communities.doesCommunityExists("hapramp-dance"));
        //false test
        assert (!Communities.doesCommunityExists("hapramp"));
        assert (!Communities.doesCommunityExists("hapramp-test"));
    }



}
