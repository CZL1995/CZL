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



    public  class DatasEntity {
        public String wenzi;
        public String imageurl;



    }
}
