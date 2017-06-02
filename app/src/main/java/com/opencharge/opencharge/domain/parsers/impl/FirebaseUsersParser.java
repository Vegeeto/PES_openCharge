package com.opencharge.opencharge.domain.parsers.impl;

import android.util.Pair;

import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.parsers.UsersParser;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.opencharge.opencharge.domain.parsers.impl.FirebasePointsParser.LON_KEY;

/**
 * Created by DmnT on 18/05/2017.
 */

public class FirebaseUsersParser implements UsersParser {

    public static final String USERNAME_KEY = "username";
    public static final String PHOTO_KEY = "photo";
    public static final String EMAIL_KEY = "email";
    public static final String MINUTES_KEY = "minutes";
    public static final String CREATS_KEY = "punts";
    public static final String RESERVATS_KEY = "puntsReservats";
    public static final String POINT_ID="first";
    public static final String POINT_NAME="second";

    @Override
    public User parseFromMap(String key, Map<String, Object> map) {
        User user = new User(key);

        //TODO aquesta part, i la resta també, s'ha d'emplenar correctament quan es sàpiga com es guarden els usuaris al firebase


        user.setUsername(parseStringKeyFromMap(USERNAME_KEY, map));
        user.setPhoto(parseStringKeyFromMap(PHOTO_KEY, map));
        user.setEmail(parseStringKeyFromMap(EMAIL_KEY, map));
        user.setMinutes(parseLongKeyFromMap(MINUTES_KEY, map).intValue());
        user.setPunts(parseArrayListFromMap(CREATS_KEY, map));
        user.setPuntsReservats(parseArrayListFromMap(RESERVATS_KEY, map));

        return user;
    }



    private ArrayList<Pair<String,String>> parseArrayListFromMap(String key, Map<String, Object> map) {
        ArrayList<Pair<String,String>> arrayList = new ArrayList();
        //Log.i("ParseArrayList",key);
        if (map.containsKey(key)) {
            ArrayList<HashMap<String,String>> totsPunts =(ArrayList)map.get(key);
            for (HashMap<String,String> element : totsPunts)
            {
                String idPunt = element.get(POINT_ID).toString();
                String nomPunt=element.get(POINT_NAME).toString();
                arrayList.add(new Pair<String,String>(idPunt,nomPunt));
            }
        }

        /*
        //TODO això és el que s'ha de treure:
        Pair<String,String> laParella = new Pair<>("-KkVEoFuPWKlEP2PZTPw","Pablo Neruda, 54");
        arrayList.add(laParella);
        Pair<String,String> laParella2=new Pair<>("-Kl4THJS4T9PiF2zbdak","d, 5");
        arrayList.add(laParella2);
        */



        return arrayList;
    }

    private Integer parseIntegerKeyFromMap(String key, Map<String, Object> map) {
        Integer value = 0;
        if (map.containsKey(key)) {
            value = (Integer)map.get(key);
        }
        return value;
    }

    private Long parseLongKeyFromMap(String key, Map<String, Object> map) {
        long value = 0;
        if (map.containsKey(key)) {
            value = (Long)map.get(key);
        }
        return value;
    }

    private String parseStringKeyFromMap(String key, Map<String, Object> map) {
        if (map.containsKey(key)) {
            return (String)map.get(key);
        }
        else {
            return null;
        }
    }
}
