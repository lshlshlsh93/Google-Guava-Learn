package com.lsh.guava.utils;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;

import java.time.LocalDateTime;

/**
 * @Author lishaohui
 * @Date 2023/5/24 17:09
 */
public class MoreObject {

    static class Guava implements Comparable<Guava> {
        private final String manufacturer;
        private final String version;
        private final LocalDateTime releaseDate;

        Guava(String manufacturer, String version, LocalDateTime releaseDate) {
            this.manufacturer = manufacturer;
            this.version = version;
            this.releaseDate = releaseDate;
        }

        @Override
        public String toString() {
            // help us build toString method
            return MoreObjects.toStringHelper(this)
                    .omitNullValues()
                    .add("manufacturer", this.manufacturer)
                    .add("version", this.version)
                    .add("releaseDate", this.releaseDate)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Guava guava = (Guava) o;
            // help us build equals method
            return com.google.common.base.Objects.equal(this.manufacturer, guava.manufacturer) &&
                    com.google.common.base.Objects.equal(this.version, guava.version) &&
                    com.google.common.base.Objects.equal(this.releaseDate, guava.releaseDate);
        }

        @Override
        public int hashCode() {
            // help us build hashcode method
            return com.google.common.base.Objects.hashCode(manufacturer, version, releaseDate);
        }

        @Override
        public int compareTo(Guava o) {
            // help us build compareTo method
            return ComparisonChain
                    .start()
                    .compare(this.manufacturer, o.manufacturer)
                    .compare(this.version, o.version)
                    .compare(this.releaseDate, o.releaseDate)
                    .result();
        }
    }

    public static void main(String[] args) {
        final Guava guava = new Guava("Google", "31.1-jre", LocalDateTime.now());
        System.out.println(guava.toString());
        System.out.println(guava.hashCode());

        final Guava guava1 = new Guava("Google", "31.1-jre", LocalDateTime.now().minusDays(1));
        System.out.println(guava1.toString());
        System.out.println(guava1.compareTo(guava));
    }

}
