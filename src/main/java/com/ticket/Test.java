package com.ticket;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.parse(new File("d:\\test.html"), "utf-8");
        Elements xx = doc.select("[xml:space]");
        System.out.println(xx.get(0).attr("src"));
    }

}
