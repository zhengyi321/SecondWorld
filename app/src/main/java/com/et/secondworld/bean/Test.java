package com.et.secondworld.bean;

import java.util.List;

public class Test {

    /**
     * message : success感谢又拍云(upyun.com)提供CDN赞助
     * status : 200
     * date : 20200125
     * time : 2020-01-25 13:43:14
     * cityInfo : {"city":"天津市","citykey":"101030100","parent":"天津","updateTime":"13:29"}
     * data : {"shidu":"46%","pm25":60,"pm10":70,"quality":"良","wendu":"2","ganmao":"极少数敏感人群应减少户外活动","forecast":[{"date":"25","high":"高温 6℃","low":"低温 -4℃","ymd":"2020-01-25","week":"星期六","sunrise":"07:25","sunset":"17:22","aqi":140,"fx":"西南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"26","high":"高温 5℃","low":"低温 -3℃","ymd":"2020-01-26","week":"星期日","sunrise":"07:24","sunset":"17:23","aqi":115,"fx":"东北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"27","high":"高温 5℃","low":"低温 -1℃","ymd":"2020-01-27","week":"星期一","sunrise":"07:24","sunset":"17:24","aqi":95,"fx":"东风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"28","high":"高温 5℃","low":"低温 -2℃","ymd":"2020-01-28","week":"星期二","sunrise":"07:23","sunset":"17:26","aqi":125,"fx":"西风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"29","high":"高温 7℃","low":"低温 -2℃","ymd":"2020-01-29","week":"星期三","sunrise":"07:22","sunset":"17:27","aqi":115,"fx":"东北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"30","high":"高温 6℃","low":"低温 -4℃","ymd":"2020-01-30","week":"星期四","sunrise":"07:22","sunset":"17:28","aqi":46,"fx":"东南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"31","high":"高温 6℃","low":"低温 -1℃","ymd":"2020-01-31","week":"星期五","sunrise":"07:21","sunset":"17:29","fx":"无持续风向","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"01","high":"高温 5℃","low":"低温 -3℃","ymd":"2020-02-01","week":"星期六","sunrise":"07:20","sunset":"17:30","fx":"北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"02","high":"高温 1℃","low":"低温 -7℃","ymd":"2020-02-02","week":"星期日","sunrise":"07:19","sunset":"17:31","fx":"北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"03","high":"高温 1℃","low":"低温 -6℃","ymd":"2020-02-03","week":"星期一","sunrise":"07:18","sunset":"17:33","fx":"东南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"04","high":"高温 3℃","low":"低温 -6℃","ymd":"2020-02-04","week":"星期二","sunrise":"07:17","sunset":"17:34","fx":"东南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"05","high":"高温 1℃","low":"低温 -5℃","ymd":"2020-02-05","week":"星期三","sunrise":"07:16","sunset":"17:35","fx":"东风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"06","high":"高温 0℃","low":"低温 -4℃","ymd":"2020-02-06","week":"星期四","sunrise":"07:15","sunset":"17:36","fx":"东风","fl":"4-5级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"07","high":"高温 0℃","low":"低温 -7℃","ymd":"2020-02-07","week":"星期五","sunrise":"07:14","sunset":"17:37","fx":"东北风","fl":"3-4级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"08","high":"高温 -2℃","low":"低温 -6℃","ymd":"2020-02-08","week":"星期六","sunrise":"07:13","sunset":"17:39","fx":"东风","fl":"3-4级","type":"阴","notice":"不要被阴云遮挡住好心情"}],"yesterday":{"date":"24","high":"高温 4℃","low":"低温 -4℃","ymd":"2020-01-24","week":"星期五","sunrise":"07:26","sunset":"17:21","aqi":55,"fx":"东北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"}}
     */

    private String message;
    private int status;
    private String date;
    private String time;
    private CityInfoBean cityInfo;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CityInfoBean getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoBean cityInfo) {
        this.cityInfo = cityInfo;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class CityInfoBean {
        /**
         * city : 天津市
         * citykey : 101030100
         * parent : 天津
         * updateTime : 13:29
         */

        private String city;
        private String citykey;
        private String parent;
        private String updateTime;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCitykey() {
            return citykey;
        }

        public void setCitykey(String citykey) {
            this.citykey = citykey;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class DataBean {
        /**
         * shidu : 46%
         * pm25 : 60.0
         * pm10 : 70.0
         * quality : 良
         * wendu : 2
         * ganmao : 极少数敏感人群应减少户外活动
         * forecast : [{"date":"25","high":"高温 6℃","low":"低温 -4℃","ymd":"2020-01-25","week":"星期六","sunrise":"07:25","sunset":"17:22","aqi":140,"fx":"西南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"26","high":"高温 5℃","low":"低温 -3℃","ymd":"2020-01-26","week":"星期日","sunrise":"07:24","sunset":"17:23","aqi":115,"fx":"东北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"27","high":"高温 5℃","low":"低温 -1℃","ymd":"2020-01-27","week":"星期一","sunrise":"07:24","sunset":"17:24","aqi":95,"fx":"东风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"28","high":"高温 5℃","low":"低温 -2℃","ymd":"2020-01-28","week":"星期二","sunrise":"07:23","sunset":"17:26","aqi":125,"fx":"西风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"29","high":"高温 7℃","low":"低温 -2℃","ymd":"2020-01-29","week":"星期三","sunrise":"07:22","sunset":"17:27","aqi":115,"fx":"东北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"30","high":"高温 6℃","low":"低温 -4℃","ymd":"2020-01-30","week":"星期四","sunrise":"07:22","sunset":"17:28","aqi":46,"fx":"东南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"31","high":"高温 6℃","low":"低温 -1℃","ymd":"2020-01-31","week":"星期五","sunrise":"07:21","sunset":"17:29","fx":"无持续风向","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"01","high":"高温 5℃","low":"低温 -3℃","ymd":"2020-02-01","week":"星期六","sunrise":"07:20","sunset":"17:30","fx":"北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"02","high":"高温 1℃","low":"低温 -7℃","ymd":"2020-02-02","week":"星期日","sunrise":"07:19","sunset":"17:31","fx":"北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"03","high":"高温 1℃","low":"低温 -6℃","ymd":"2020-02-03","week":"星期一","sunrise":"07:18","sunset":"17:33","fx":"东南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"04","high":"高温 3℃","low":"低温 -6℃","ymd":"2020-02-04","week":"星期二","sunrise":"07:17","sunset":"17:34","fx":"东南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"05","high":"高温 1℃","low":"低温 -5℃","ymd":"2020-02-05","week":"星期三","sunrise":"07:16","sunset":"17:35","fx":"东风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"06","high":"高温 0℃","low":"低温 -4℃","ymd":"2020-02-06","week":"星期四","sunrise":"07:15","sunset":"17:36","fx":"东风","fl":"4-5级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"07","high":"高温 0℃","low":"低温 -7℃","ymd":"2020-02-07","week":"星期五","sunrise":"07:14","sunset":"17:37","fx":"东北风","fl":"3-4级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"08","high":"高温 -2℃","low":"低温 -6℃","ymd":"2020-02-08","week":"星期六","sunrise":"07:13","sunset":"17:39","fx":"东风","fl":"3-4级","type":"阴","notice":"不要被阴云遮挡住好心情"}]
         * yesterday : {"date":"24","high":"高温 4℃","low":"低温 -4℃","ymd":"2020-01-24","week":"星期五","sunrise":"07:26","sunset":"17:21","aqi":55,"fx":"东北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"}
         */

        private String shidu;
        private double pm25;
        private double pm10;
        private String quality;
        private String wendu;
        private String ganmao;
        private YesterdayBean yesterday;
        private List<ForecastBean> forecast;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public double getPm25() {
            return pm25;
        }

        public void setPm25(double pm25) {
            this.pm25 = pm25;
        }

        public double getPm10() {
            return pm10;
        }

        public void setPm10(double pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 24
             * high : 高温 4℃
             * low : 低温 -4℃
             * ymd : 2020-01-24
             * week : 星期五
             * sunrise : 07:26
             * sunset : 17:21
             * aqi : 55
             * fx : 东北风
             * fl : 3-4级
             * type : 多云
             * notice : 阴晴之间，谨防紫外线侵扰
             */

            private String date;
            private String high;
            private String low;
            private String ymd;
            private String week;
            private String sunrise;
            private String sunset;
            private int aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class ForecastBean {
            /**
             * date : 25
             * high : 高温 6℃
             * low : 低温 -4℃
             * ymd : 2020-01-25
             * week : 星期六
             * sunrise : 07:25
             * sunset : 17:22
             * aqi : 140
             * fx : 西南风
             * fl : <3级
             * type : 晴
             * notice : 愿你拥有比阳光明媚的心情
             */

            private String date;
            private String high;
            private String low;
            private String ymd;
            private String week;
            private String sunrise;
            private String sunset;
            private int aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }
}
