package com.lsh.guava.collections.table;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.TreeBasedTable;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * @author lishaohui
 * @date 2023/7/26 9:37
 */
public class TableDemoTest {

    /*
        ArrayTable
        HashBasedTable
        ImmutableTable
        TreeBasedTable
     */

    @Test
    public void testHashBasedTable() {
        var hashBasedTable = HashBasedTable.create();
        hashBasedTable.put("language", "java", "1.0");
        hashBasedTable.put("language", "scala", "2.0");
        hashBasedTable.put("language", "react", "18.0");
        hashBasedTable.put("db", "mysql", "8.0");
        hashBasedTable.put("db", "mongodb", "7.0");
        System.out.println(hashBasedTable); // {language={java=1.0, scala=2.0, react=18.0}, db={mysql=8.0, mongodb=7.0}}

        var languageRow = hashBasedTable.row("language");
        System.out.println(languageRow);
        System.out.println(hashBasedTable.row("language").get("java"));


        var columnJava = hashBasedTable.column("java");
        System.out.println(columnJava); // {language=1.0}


        var cellSet = hashBasedTable.cellSet();
        System.out.println(cellSet);

    }

}