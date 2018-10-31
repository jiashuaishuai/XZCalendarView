package com.example.shuaijia.xzcalendarview.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JiaShuai on 2017/4/25.
 */

public class CollectionDateBean implements Serializable {
    public Data data;

    public Data getData() {
        return data;
    }

    public class Data implements Serializable {
        private String code;
        private String gmt;
        private String msg;
        private List<YearData> data;//年数组

        public String getCode() {
            return code;
        }

        public String getGmt() {
            return gmt;
        }

        public String getMsg() {
            return msg;
        }

        public List<YearData> getData() {
            return data;
        }

        public class YearData implements Serializable {
            private String year;
            private List<MonthData> monthList;//月数组

            public String getYear() {
                return year;
            }

            public List<MonthData> getMonthList() {
                return monthList;
            }

            public class MonthData implements Serializable {
                private String year;
                private String month;
                private List<DayData> dayList;//日数组

                public String getYear() {
                    return year;
                }

                public String getMonth() {
                    return month;
                }

                public List<DayData> getDayList() {
                    return dayList;
                }

                public class DayData implements Serializable {
                    private String dueDate;

                    public String getDueDate() {
                        return dueDate;
                    }
                }
            }
        }
    }
}
