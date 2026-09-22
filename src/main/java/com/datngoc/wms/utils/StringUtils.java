package com.datngoc.wms.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {

    public static String toSlug(String input) {
        if (input == null) {
            return null;
        }
        
        // Remove accents
        String nfdNormalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(nfdNormalizedString).replaceAll("")
                .replace('đ', 'd')
                .replace('Đ', 'D');

        // To lower case, trim, replace spaces with hyphens, and remove non-alphanumeric characters
        return result.toLowerCase()
                .trim()
                .replaceAll("\\s+", "-")       // Replace one or more spaces with a single hyphen
                .replaceAll("[^a-z0-9-]", ""); // Remove all other non-alphanumeric/non-hyphen characters
    }

    public static String toUpperSlug(String input) {
        String slug = toSlug(input);
        if (slug == null || slug.isBlank()) {
            return "SAN-PHAM";
        }
        // Giới hạn độ dài slug tối đa 40 ký tự để mã SKU không bị quá dài
        if (slug.length() > 40) {
            slug = slug.substring(0, 40).replaceAll("-$", "");
        }
        return slug.toUpperCase();
    }
}
