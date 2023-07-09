package utils;

import java.util.TreeMap;

public class RomanNumbers {

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();
    private final static TreeMap<String, Integer> simpleMap = new TreeMap<String, Integer>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

        simpleMap.put("I", 1);
        simpleMap.put("II", 2);
        simpleMap.put("III", 3);
        simpleMap.put("IV", 4);
        simpleMap.put("V", 5);
        simpleMap.put("VI", 6);
        simpleMap.put("VII", 7);
        simpleMap.put("VIII", 8);
        simpleMap.put("IX", 9);

    }

    public static String toRoman(int number) {
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
    }

    public static int toSimpleNumber(String roman) {
        return simpleMap.get(roman);
    }

}