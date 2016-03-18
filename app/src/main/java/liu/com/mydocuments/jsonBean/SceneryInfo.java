package liu.com.mydocuments.jsonBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuhui on 16/3/17.
 */
public class SceneryInfo implements Serializable{
    private int errNum;
    private String errMsg;
    private RetDataEntity retData;

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setRetData(RetDataEntity retData) {
        this.retData = retData;
    }

    public int getErrNum() {
        return errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public RetDataEntity getRetData() {
        return retData;
    }

    public static class RetDataEntity {
        private int pageNo;
        private int pageSize;
        private int totalRecord;
        /**
         * productId : 1242120342
         * spotName : Dream liner 787飞行体验中心
         */

        private List<TicketListEntity> ticketList;

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public void setTicketList(List<TicketListEntity> ticketList) {
            this.ticketList = ticketList;
        }

        public int getPageNo() {
            return pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public List<TicketListEntity> getTicketList() {
            return ticketList;
        }

        public static class TicketListEntity {
            private String productId;
            private String spotName;

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public void setSpotName(String spotName) {
                this.spotName = spotName;
            }

            public String getProductId() {
                return productId;
            }

            public String getSpotName() {
                return spotName;
            }
        }
    }
}
