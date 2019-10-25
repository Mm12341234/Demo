package com.example.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author Liumq
 * @Date   2019/05/23
 * @Describe 该类存放的是包名，类名
 */
public class Configuration implements Serializable {
    private String author;
    private String packageName;
    private Path path;
    private Map property;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPackageName() {
        return packageName == null ? "" : packageName + ".";
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Map getProperty() {
        return property;
    }

    public void setProperty(Map property) {
        this.property = property;
    }

    public static class Path {
        private String controller;
        private String service;
        private String interf;
        private String dao;
        private String entity;
        private String mapper;
        private String html;
        private String js;

        public Path() {
        }

        public Path(String controller, String service, String interf,String dao, String entity, String mapper,String html,String js) {
            this.controller = controller;
            this.service = service;
            this.interf = interf;
            this.dao = dao;
            this.entity = entity;
            this.mapper = mapper;
            this.html=html;
            this.js=js;
        }

        public String getController() {
            return controller == null ? "" : controller;
        }

        public void setController(String controller) {
            this.controller = controller;
        }

        public String getService() {
            return service == null ? "" : service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getInterf() {
            return interf;
        }

        public void setInterf(String interf) {
            this.interf = interf;
        }

        public String getDao() {
            return dao == null ? "" : dao;
        }

        public void setDao(String dao) {
            this.dao = dao;
        }

        public String getEntity() {
            return entity == null ? "" : entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getMapper() {
            return mapper == null ? "" : mapper;
        }

        public void setMapper(String mapper) {
            this.mapper = mapper;
        }

        public String getHtml() {
            return html==null ? "" : html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getJs() {
            return js==null?"":js;
        }

        public void setJs(String js) {
            this.js = js;
        }
    }

}
