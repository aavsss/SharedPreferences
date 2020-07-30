package com.example.sharedpreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reocurrence {

    private int time;
    private String period;
    private int many;
    private ArrayList<String> daysOfWeek;
    private String end;
    private String month;

    public Reocurrence(int time, String period, int many,  ArrayList<String> daysOfWeek, String end){
        this.time = time;
        this.period = period;
        this.many = many;
        this.daysOfWeek = daysOfWeek;
        this.end = end;
    }

    public Reocurrence(String input){
        String timeString = "";
        String periodString = "";
        String manyString = "";
        String endString = "";
        String monthString = "";
        ArrayList<String> daysRepeated = new ArrayList<>();

        Pattern timePattern = Pattern.compile("[0-9]*th");
        Matcher timeMatcher = timePattern.matcher(input);
        if(timeMatcher.find()){
            timeString = timeMatcher.group();
            int size = timeString.length();
            timeString = timeString.substring(0, size - 2);
            time = Integer.parseInt(timeString);
        }

        Pattern periodPattern = Pattern.compile("ry [a-zA-z]+");
        Matcher periodMatcher = periodPattern.matcher(input);
        if(periodMatcher.find()){
            periodString = periodMatcher.group();
            periodString = periodString.substring(3, periodString.length());
            period = periodString;
        }

        Pattern manyPattern = Pattern.compile("[0-9]+ a");
        Matcher manyMatcher = manyPattern.matcher(input);
        if(manyMatcher.find()){
            manyString = manyMatcher.group();
            manyString = manyString.substring(0, manyString.length() - 2);
            many = Integer.parseInt(manyString);
        }

        Pattern endPattern = Pattern.compile("on [a-zA-z]+");
        Matcher endMatcher = endPattern.matcher(input);
        if(endMatcher.find()){
            endString = endMatcher.group();
            endString = endString.substring(3, endString.length());
            end = endString;
        }

        Pattern dayPattern = Pattern.compile("[a-zA-z]+day");
        Matcher dayMatcher = dayPattern.matcher(input);
        while(dayMatcher.find()){
            daysRepeated.add(dayMatcher.group());
        }

        daysOfWeek = daysRepeated;

        Pattern monthPattern = Pattern.compile("Month: [a-zA-z]+");
        Matcher monthMatcher = monthPattern.matcher(input);
        if(monthMatcher.find()){
            monthString = monthMatcher.group();
            monthString = monthString.substring(7);
            month = monthString;
        }
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public ArrayList<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(ArrayList<String> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public int getMany(){
        return many;
    }

    public void setMany(int many){
        this.many = many;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getMonth(){
        return month;
    }

    public void setMonth(String month){
        this.month = month;
    }

    public String nextMonthString(){
        String result = time + "th of every " + period + ". " + many + " a " + period + ". Ends on " + end + ".";
        StringBuilder daysOf = new StringBuilder(" ");
        for(String days : daysOfWeek){
            daysOf.append(days).append(",");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        int nextMonth = calendar.get(Calendar.MONTH);
        return result + daysOf + (nextMonth); //Month also starts from 0
    }

    public boolean checkReocurrence(){
        Calendar calendarFromString = Calendar.getInstance();
        calendarFromString.set(Calendar.DATE, time);
        calendarFromString.set(Calendar.MONTH, getIntOfMonth(month)-1);
        System.out.println("/// checkReocurrence " + calendarFromString.get(Calendar.DATE) + " " + calendarFromString.get(Calendar.MONTH) + " " + calendarFromString.get(Calendar.YEAR));

        Calendar currentCalender = Calendar.getInstance();
        if(currentCalender.after(calendarFromString) || currentCalender.equals(calendarFromString)){
            System.out.println("/// Make changes here");
            //Create a new receipt here.
            return true;
        }else{
            System.out.println("/// Wait, do not make changes yet.");
            return false;
        }
    }

    public int getIntOfMonth(String month){
        int result = 0;
        switch (month){
            case "January":
                result = 0;
                break;
            case "February":
                result = 1;
                break;
            case "March":
                result = 2;
                break;
            case "April":
                result = 3;
                break;
            case "May":
                result = 4;
                break;
            case "June":
                result = 5;
                break;
            case "July":
                result = 6;
                break;
            case "August":
                result = 7;
                break;
            case "September":
                result = 8;
                break;
            case "October":
                result = 9;
                break;
            case "November":
                result = 10;
                break;
            case "December":
                result = 11;
                break;
        }
        return result;
    }
}
