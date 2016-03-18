package liu.com.mydocuments.jsonBean;

import java.io.Serializable;

/**
 * Created by liuhui on 16/3/15.
 */
public class SceneryDetail implements Serializable{
    public int errNum;
    public String errMsg;
    public RetDataEntity retData;

    public static class RetDataEntity {
        public String id;
        public TicketDetailEntity ticketDetail;
        public static class TicketDetailEntity {
            /** h5 详情url*/
            public String loc;
            public String lastmod;
            public String changefreq;
            public String priority;
            public DataEntity data;
            public static class DataEntity {
                public DisplayEntity display;
                public static class DisplayEntity {
                    public TicketEntity ticket;
                    public static class TicketEntity {
                        /** 经典名*/
                        public String spotName;
                        /** 别名*/
                        public String alias;
                        public String spotID;
                        /** 景点描述*/
                        public String description;
                        /** h5*/
                        public String detailUrl;
                        /** 地址*/
                        public String address;
                        public String province;
                        public String city;
                        public String coordinates;
                        /** 详情图*/
                        public String imageUrl;
                        public String spotScore;
                        public String comments;
                        //public List<PriceListEntity> priceList;
                        public static class PriceListEntity {
                            public String ticketTitle;
                            public String ticketID;
                            public String priceType;
                            public String price;
                            public String normalPrice;
                            public String num;
                            public String type;
                            public String bookUrl;
                        }
                    }
                }
            }
        }
    }
}
