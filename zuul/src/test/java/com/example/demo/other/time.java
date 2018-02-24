package com.example.demo.other;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class time {


    private static final SimpleDateFormat SDFX = new SimpleDateFormat("HH:mm:ss");

    private static final SimpleDateFormat SDFXX = new SimpleDateFormat("HH:mm:ss");

    @Test
    public void t1() throws ParseException {

        Long c1 = System.nanoTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date beginTime = sdf.parse("2018-02-22 16:36:00");
        Date endTime = sdf.parse("2018-02-24 23:59:59");

        List<HashMap<String, Date>> listMap = generateWorkTimeSet(beginTime, endTime);

        Long c2 = System.nanoTime();

        int i = 0;
        StringBuilder sb = new StringBuilder();
        int size = listMap.size();
        for (; i < size; i++) {
            sb
                    .append("beginTime: ")
                    .append(sdf.format(listMap.get(i).get("beginTime")))
                    .append(" => endTime: ")
                    .append(sdf.format(listMap.get(i).get("endTime") ))
                    .append("\n");

            if((i != 0 && i % 10000 == 0) || i >= (size - 1)) {
                System.out.println(sb);
                sb.delete(0, sb.length());
            }
        }

        System.out.println("总条数: " + i);
        System.out.println("耗时: " + (c2 -c1) + " (纳秒)");
        System.out.println("耗时: " + new BigDecimal(new Long(c2 -c1).toString()).
                divide(new BigDecimal("1000000"), 6, BigDecimal.ROUND_HALF_UP) + " (毫秒)");
    }

    /**
     * 在时间段内 按粒度 获得时间点的集合
     * @param beginTime
     * @param endTime
     * @param timeNode
     * @param type
     * @return
     * @throws ParseException
     */
    public List<Date> generateDataTimeNodeSet(
            Date beginTime,
            Date endTime,
            Date timeNode,
            int type) throws ParseException {

        List<Date> list = new LinkedList<>();

        Calendar btc = Calendar.getInstance();
        Calendar etc = Calendar.getInstance();
        Calendar tntc = Calendar.getInstance();
        btc.setTime(beginTime);
        etc.setTime(endTime);
        tntc.setTime(timeNode);

        switch(type) {
//		case Calendar.MILLISECOND : {
//
//		}break;
//		case Calendar.SECOND : {
//
//		}break;
//		case Calendar.MINUTE : {
//
//		}break;
            case Calendar.HOUR_OF_DAY : {
                long tnx = SDFXX.parse(SDFXX.format(tntc.getTime())).getTime();
                long bx = SDFXX.parse(SDFXX.format(btc.getTime())).getTime();

                if(tnx >= bx) {
                    btc.set(Calendar.HOUR_OF_DAY, tntc.get(Calendar.HOUR_OF_DAY));
                    btc.set(Calendar.MINUTE, tntc.get(Calendar.MINUTE));
                    btc.set(Calendar.SECOND, tntc.get(Calendar.SECOND));
                    btc.set(Calendar.MILLISECOND, tntc.get(Calendar.MILLISECOND));
                }else {
                    btc.set(Calendar.HOUR_OF_DAY, tntc.get(Calendar.HOUR_OF_DAY));
                    btc.set(Calendar.MINUTE, tntc.get(Calendar.MINUTE));
                    btc.set(Calendar.SECOND, tntc.get(Calendar.SECOND));
                    btc.set(Calendar.MILLISECOND, tntc.get(Calendar.MILLISECOND));
                    btc.add(Calendar.DAY_OF_MONTH, 1);
                }

                while(btc.compareTo(etc) <= 0) {
                    list.add(btc.getTime());
                    btc.add(Calendar.DAY_OF_MONTH, 1);
                }

            }break;
//		case Calendar.DAY_OF_MONTH : {
//
//		}break;
//		case Calendar.MONTH : {
//
//		}break;
//		case Calendar.YEAR : {
//
//		}break;
            default : throw new RuntimeException("NOT FOUND OF THE TYPE.");
        }


        return list;
    }

    /**
     * 将两时间集合 按顺序 获得首尾对
     * @param beginTimeSet
     * @param endTimeSet
     * @param listMap
     */
    public void fillToListMap(
            List<Date> beginTimeSet,
            List<Date> endTimeSet,
            List<HashMap<String, Date>> listMap) {
        int bts = beginTimeSet.size();
        int ets = endTimeSet.size();
        int length = bts < ets ? bts : ets;
        for (int i = 0; i < length; i++) {
            HashMap<String, Date> mapx = new HashMap<>(2,1);
            mapx.put("beginTime", beginTimeSet.get(i));
            mapx.put("endTime", endTimeSet.get(i));
            listMap.add(mapx);
            if(i + 1 < bts) {
                HashMap<String, Date> mapxx = new HashMap<>(2,1);
                mapxx.put("beginTime", endTimeSet.get(i));
                mapxx.put("endTime", beginTimeSet.get(i + 1));
                listMap.add(mapxx);
            }
        }
    }

    /**
     * 第一个设置为小的并将两时间集合 按顺序 获得首尾对
     * @param beginTimeSet
     * @param endTimeSet
     * @param listMap
     */
    public void fillToListMapAndCom(
            List<Date> beginTimeSet,
            List<Date> endTimeSet,
            List<HashMap<String, Date>> listMap) {
        if(beginTimeSet.size() > 0 && endTimeSet.size() > 0) {
            if(beginTimeSet.get(0).compareTo(endTimeSet.get(0)) < 0) {
                fillToListMap(beginTimeSet, endTimeSet, listMap);
            }else {
                fillToListMap(endTimeSet, beginTimeSet, listMap);
            }
        }
    }

    /**
     * 加上开始时间和结束时间 第一个设置为小的并将两时间集合 按顺序 获得首尾对
     * @param beginTimeSet
     * @param endTimeSet
     * @param listMap
     * @param beginTime
     * @param endTime
     */
    public void fillToListMapAndComAndPutST(
            List<Date> beginTimeSet,
            List<Date> endTimeSet,
            List<HashMap<String, Date>> listMap,
            Date beginTime,
            Date endTime) {

        if(beginTimeSet.size() <= 0) {
            if(beginTime.compareTo(endTime) < 0) {
                beginTimeSet.add(beginTime);
                endTimeSet.add(endTime);
            }
        }else {
            if(beginTimeSet.get(0).compareTo(beginTime) > 0) {
                beginTimeSet.add(0, beginTime);
            }
            if(beginTimeSet.get(beginTimeSet.size() - 1).compareTo(endTime) < 0) {
                beginTimeSet.add(endTime);
            }
        }


        fillToListMapAndCom(beginTimeSet, endTimeSet, listMap);
    }

    /**
     * 获得一个时间段中的工作时间集合
     * @param beginTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public List<HashMap<String, Date>> generateWorkTimeSet(Date beginTime, Date endTime) throws ParseException {


        List<Date> beginTimeSet = generateDataTimeNodeSet(
                beginTime,
                endTime,
                SDFX.parse("08:00:00"),
                Calendar.HOUR_OF_DAY
        );

        List<Date> endTimeSet = generateDataTimeNodeSet(
                beginTime,
                endTime,
                SDFX.parse("20:00:00"),
                Calendar.HOUR_OF_DAY
        );

        List<HashMap<String, Date>> listMap = new LinkedList<>();

        fillToListMapAndComAndPutST(beginTimeSet, endTimeSet, listMap, beginTime, endTime);

        return listMap;
    }
}
