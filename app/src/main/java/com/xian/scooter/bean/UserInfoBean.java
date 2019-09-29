package com.xian.scooter.bean;

import java.io.Serializable;
import java.util.List;


public class UserInfoBean implements Serializable{
        /**
         * identitys : [{"id":32,"name":"政务管理_综合管理平台测试","remark":null,"appId":2,"acquiesce":0,"appName":null,"roleNames":null,"identityRelRoles":null},{"id":42,"name":"巡检执行人（已废除）","remark":null,"appId":2,"acquiesce":0,"appName":null,"roleNames":null,"identityRelRoles":null},{"id":43,"name":"巡检审批人（已废除）","remark":null,"appId":2,"acquiesce":0,"appName":null,"roleNames":null,"identityRelRoles":null},{"id":44,"name":"巡检发布人（已废除）","remark":null,"appId":2,"acquiesce":0,"appName":null,"roleNames":null,"identityRelRoles":null}]
         * realName : dxc_xjfb
         * security : {"id":100128,"name":"安全办公室"}
         * environment : {"id":1426,"name":"大气办公室"}
         * appIds : [2]
         * createType : 1
         * id : 157
         * cboInfo : null
         * token : 7dcd2567bc5c4c6284a2ed2e5640f1d2
         * refreshToken : db28de378d124d98ae2bc0d7cd6cb41c
         * username : dxc_xjfb
         */

        private String realName;
        private SecurityBean security;
        private EnvironmentBean environment;
        private int createType;
        private int id;
        private Object cboInfo;
        private String token;
        private String refreshToken;
        private String username;
        private List<IdentitysBean> identitys;
        private List<Integer> appIds;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public SecurityBean getSecurity() {
            return security;
        }

        public void setSecurity(SecurityBean security) {
            this.security = security;
        }

        public EnvironmentBean getEnvironment() {
            return environment;
        }

        public void setEnvironment(EnvironmentBean environment) {
            this.environment = environment;
        }

        public int getCreateType() {
            return createType;
        }

        public void setCreateType(int createType) {
            this.createType = createType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCboInfo() {
            return cboInfo;
        }

        public void setCboInfo(Object cboInfo) {
            this.cboInfo = cboInfo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<IdentitysBean> getIdentitys() {
            return identitys;
        }

        public void setIdentitys(List<IdentitysBean> identitys) {
            this.identitys = identitys;
        }

        public List<Integer> getAppIds() {
            return appIds;
        }

        public void setAppIds(List<Integer> appIds) {
            this.appIds = appIds;
        }

        public static class SecurityBean  implements Serializable{
            /**
             * id : 100128
             * name : 安全办公室
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class EnvironmentBean  implements Serializable{
            /**
             * id : 1426
             * name : 大气办公室
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class IdentitysBean  implements Serializable{
            /**
             * id : 32
             * name : 政务管理_综合管理平台测试
             * remark : null
             * appId : 2
             * acquiesce : 0
             * appName : null
             * roleNames : null
             * identityRelRoles : null
             */

            private int id;
            private String name;
            private Object remark;
            private int appId;
            private int acquiesce;
            private Object appName;
            private Object roleNames;
            private Object identityRelRoles;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public int getAppId() {
                return appId;
            }

            public void setAppId(int appId) {
                this.appId = appId;
            }

            public int getAcquiesce() {
                return acquiesce;
            }

            public void setAcquiesce(int acquiesce) {
                this.acquiesce = acquiesce;
            }

            public Object getAppName() {
                return appName;
            }

            public void setAppName(Object appName) {
                this.appName = appName;
            }

            public Object getRoleNames() {
                return roleNames;
            }

            public void setRoleNames(Object roleNames) {
                this.roleNames = roleNames;
            }

            public Object getIdentityRelRoles() {
                return identityRelRoles;
            }

            public void setIdentityRelRoles(Object identityRelRoles) {
                this.identityRelRoles = identityRelRoles;
            }
        }
}
