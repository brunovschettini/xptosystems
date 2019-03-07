package com.senior.xptosystems.xptosystems.utils;


import java.text.Normalizer;

public class StringHelper {

    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

}
