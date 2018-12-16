package com.chinadragon.mvpdemo.entity;

/**
 * **********************************************************************
 * Author: zhangbenlong
 * Time: 2018/9/2 11:31
 * Name:
 * Overview:
 * Usage:
 * **********************************************************************
 */
public class BmiPersonInfo {
    public String height;
    public String weight;

//    public BmiPersonInfo() {
//
//    }
//
    public BmiPersonInfo(String height, String weight) {
        this.height = height;
        this.weight = weight;
    }
//
//    public String getHeight() {
//        return height;
//    }
//
//    public void setHeight(String height) {
//        this.height = height;
//    }
//
//    public String getWeight() {
//        return weight;
//    }
//
//    public void setWeight(String weight) {
//        this.weight = weight;
//    }

    @Override
    public String toString() {
        return "BmiPersonInfo{" +
                "height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
