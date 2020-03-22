package com.atguigu.gmall.user.controller;

import java.util.Random;

public class RandomPeople {

    public static void main(String[] args) {
        Random row = new Random();
        Random col = new Random();

        int lie = col.nextInt(4) + 1;
        int han = row.nextInt(8) + 1;

        System.out.print("有请第" + lie + "列" + "第" + han + "行的同学");
    }

}
