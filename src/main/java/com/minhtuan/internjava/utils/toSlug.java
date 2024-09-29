package com.minhtuan.internjava.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class toSlug {

    public static String nameToSlug(String name){
        // chuyển đổi sang dạng Normalizer.Form.NFD
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);

        // loai bo dau
        String slug = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                .matcher(normalized)
                .replaceAll("");

        // loai bo ky tu dac biet, khoang trang thanh dau gach ngang

        slug = slug.replaceAll("[^\\w\\s-]", "")   // Loại bỏ ký tự đặc biệt
                .replaceAll("\\s+", "-")       // Thay khoảng trắng bằng dấu gạch ngang
                .toLowerCase();                // Đưa về chữ thường
        return slug;
    }
}
