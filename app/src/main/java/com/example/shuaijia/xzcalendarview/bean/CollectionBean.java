package com.example.shuaijia.xzcalendarview.bean;

import java.util.List;

/**
 * Created by JiaShuai on 2017/4/25.
 */

public class CollectionBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private String code;
        private String gmt;
        private String message;
        private String returnCode;
        private String thisMonthPhaseAmt;
        private String thisMonthRepaidPhaseAmt;
        private String toDayPhaseAmt;
        private String toDayRepaidPhaseAmt;
        private List<PhaseList> phaseList;//待回款
        private List<PhaseList> repaidList;//已回款

        public class PhaseList {
            private String phaseDate;
            private List<PhaseData> phaseData;
            private String dayPhaseAmt; //待回款
            private String dayRepaidAmt;//已回款

            public String getDayRepaidAmt() {
                return dayRepaidAmt;
            }

            public String getDayPhaseAmt() {
                return dayPhaseAmt;
            }

            public String getPhaseDate() {
                return phaseDate;
            }

            public List<PhaseData> getPhaseData() {
                return phaseData;
            }

            public class PhaseData {

                private String loanNo;
                private String loanProductType;
                private String userCode;
                private String currentamount;//待回款
                private String duedate;
                private String isrepaid;
                private String loanTitle;
                private String phaseTotalNum;
                private String phaseType;
                private String phaseNum;
                private String repaidamount;//已回款
                private String orderId;//
                private String loanId;//
                private String loanType;//

                public String getLoanNo() {
                    return loanNo;
                }

                public String getLoanProductType() {
                    return loanProductType;
                }

                public String getUserCode() {
                    return userCode;
                }

                public String getCurrentamount() {
                    return currentamount;
                }

                public String getDuedate() {
                    return duedate;
                }

                public String getIsrepaid() {
                    return isrepaid;
                }

                public String getLoanTitle() {
                    return loanTitle;
                }

                public String getPhaseTotalNum() {
                    return phaseTotalNum;
                }

                public String getPhaseType() {
                    return phaseType;
                }

                public String getPhaseNum() {
                    return phaseNum;
                }

                public String getRepaidamount() {
                    return repaidamount;
                }

                public String getOrderId() {
                    return orderId;
                }

                public String getLoanId() {
                    return loanId;
                }

                public String getLoanType() {
                    return loanType;
                }
            }
        }

        public String getCode() {
            return code;
        }

        public String getGmt() {
            return gmt;
        }

        public String getMessage() {
            return message;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public String getThisMonthPhaseAmt() {
            return thisMonthPhaseAmt;
        }

        public String getThisMonthRepaidPhaseAmt() {
            return thisMonthRepaidPhaseAmt;
        }

        public String getToDayPhaseAmt() {
            return toDayPhaseAmt;
        }

        public String getToDayRepaidPhaseAmt() {
            return toDayRepaidPhaseAmt;
        }

        public List<PhaseList> getPhaseList() {
            return phaseList;
        }

        public List<PhaseList> getRepaidList() {
            return repaidList;
        }
    }
}
