package com.caozhiliang.httpdata;

import java.util.List;

/**
 * @author CZL
 * @time 2016-03-24 0:47
 */
public class asd {


    /**
     * wenzi : 享受精美蛋糕
     * imageurl : http://192.168.0.102:8080/home/home_hua3.jpg
     * id : 3
     */

    public List<DatasEntity> datas;

    public void setDatas(List<DatasEntity> datas) {
        this.datas = datas;
    }

    public List<DatasEntity> getDatas() {
        return datas;
    }

    @Override
    public String toString() {
        return "HomeViewpagerData{" +
                "datas=" + datas +
                '}';
    }

    public  class DatasEntity {
        public String wenzi;
        public String imageurl;

        @Override
        public String toString() {
            return "DatasEntity{" +
                    "wenzi='" + wenzi + '\'' +
                    ", imageurl='" + imageurl + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }

        public String id;

        public void setWenzi(String wenzi) {
            this.wenzi = wenzi;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWenzi() {
            return wenzi;
        }

        public String getImageurl() {
            return imageurl;
        }

        public String getId() {
            return id;
        }
    }
}
