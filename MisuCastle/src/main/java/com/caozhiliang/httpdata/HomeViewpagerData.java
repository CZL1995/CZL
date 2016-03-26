package com.caozhiliang.httpdata;

import java.util.List;

/**
 * @author CZL
 * @time 2016-03-22 0:19
 */
public class HomeViewpagerData {
    @Override
    public String toString() {
        return "HomeViewpagerData{" +
                "data=" + data +
                '}';
    }

    public DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * wenzi : 享受精美蛋糕
         * imageurl : http://xingxinga123.imwork.net/Fuwu1/image/home/home_hua3.jpg
         * id : 3
         */
        public String more;
        public List<ViewpagersEntity> viewpagers;
        /**
         * wenzi : 新品上架
         * imageurl : http://xingxinga123.imwork.net/Fuwu1/image/home/home_b.jpg
         * id : 7
         */

        public List<PicturesEntity> pictures;
        /**
         * wenzi : 变得萌萌哒
         * imageurl : http://xingxinga123.imwork.net/Fuwu1/image/home/home_img3.jpg
         * id : 11
         */

        public List<ListviewsEntity> listviews;

        public void setViewpagers(List<ViewpagersEntity> viewpagers) {
            this.viewpagers = viewpagers;
        }

        public void setPictures(List<PicturesEntity> pictures) {
            this.pictures = pictures;
        }

        public void setListviews(List<ListviewsEntity> listviews) {
            this.listviews = listviews;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "viewpagers=" + viewpagers +
                    ", pictures=" + pictures +
                    ", listviews=" + listviews +
                    '}';
        }

        public List<ViewpagersEntity> getViewpagers() {
            return viewpagers;
        }

        public List<PicturesEntity> getPictures() {
            return pictures;
        }

        public List<ListviewsEntity> getListviews() {
            return listviews;
        }

        public static class ViewpagersEntity {
            private String wenzi;
            private String imageurl;
            private String id;

            public void setWenzi(String wenzi) {
                this.wenzi = wenzi;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            public void setId(String id) {
                this.id = id;
            }

            @Override
            public String toString() {
                return "ViewpagersEntity{" +
                        "wenzi='" + wenzi + '\'' +
                        ", imageurl='" + imageurl + '\'' +
                        ", id='" + id + '\'' +
                        '}';
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

        public static class PicturesEntity {
            private String wenzi;
            private String imageurl;
            private String id;

            public void setWenzi(String wenzi) {
                this.wenzi = wenzi;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            @Override
            public String toString() {
                return "PicturesEntity{" +
                        "wenzi='" + wenzi + '\'' +
                        ", imageurl='" + imageurl + '\'' +
                        ", id='" + id + '\'' +
                        '}';
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

        public static class ListviewsEntity {
            private String wenzi;
            private String imageurl;
            private String id;

            public void setWenzi(String wenzi) {
                this.wenzi = wenzi;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            @Override
            public String toString() {
                return "ListviewsEntity{" +
                        "wenzi='" + wenzi + '\'' +
                        ", imageurl='" + imageurl + '\'' +
                        ", id='" + id + '\'' +
                        '}';
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
}
