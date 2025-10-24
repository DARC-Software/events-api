package com.darcsoftware.eventsapi.common;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;

@Service
public class SlugService {

    /**
     * Build a venue slug from name + city, e.g. "The Palace Saloon" + "Fernandina Beach"
     * -> "the-palace-saloon-fernandina-beach".
     */
    public String slugOfVenue(String name, String city) {
        return slugFromParts(name, city);
    }

    /**
     * Build a party slug from display name, e.g. "Neon Beats" -> "neon-beats".
     */
    public String slugOfParty(String displayName) {
        return slugFromParts(displayName);
    }

    /**
     * General-purpose slug builder from 1..N parts. Null/blank parts are ignored.
     */
    public String slugFromParts(String... parts) {
        if (parts == null || parts.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            String s = normalizeSlug(p);
            if (!s.isBlank()) {
                if (sb.length() > 0) sb.append('-');
                sb.append(s);
            }
        }
        return sb.toString();
    }

    /**
     * Normalize a single string into a slug token:
     * - trim
     * - lower-case
     * - remove accents/diacritics
     * - replace non [a-z0-9]+ with '-'
     * - collapse multiple dashes
     * - trim leading/trailing dashes
     */
    public String normalizeSlug(String input) {
        if (input == null) return "";
        String s = input.trim();
        if (s.isEmpty()) return "";

        // remove accents/diacritics
        s = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");

        // lower, replace non-alnum with dashes, collapse dashes, trim dashes
        s = s.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("(^-+|-+$)", "");

        return s;
    }
}