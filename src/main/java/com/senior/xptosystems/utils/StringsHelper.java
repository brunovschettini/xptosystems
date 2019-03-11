package com.senior.xptosystems.utils;


import java.text.Normalizer;

public class StringsHelper {

    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

}
