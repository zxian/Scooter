package com.xian.scooter.bean;

public class UserInfoBean{
        /**
         * user : {"id":"86fc35da-d8b4-46b4-b182-adeaea93e858","account":"13411111111","role_state":1,"message_set":"1","name":"1569919574901","mac_type":1,"create_time":"2019-10-01 16:46:15","create_user_id":"86fc35da-d8b4-46b4-b182-adeaea93e858","update_time":"2019-10-01 16:46:28","status":1}
         * token : 64559f018e099c411b81d8b5b0a00cfe
         */

        private UserBean user;
        private String token;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class UserBean {
            /**
             * id : 86fc35da-d8b4-46b4-b182-adeaea93e858
             * account : 13411111111
             * role_state : 1
             * message_set : 1
             * name : 1569919574901
             * mac_type : 1
             * create_time : 2019-10-01 16:46:15
             * create_user_id : 86fc35da-d8b4-46b4-b182-adeaea93e858
             * update_time : 2019-10-01 16:46:28
             * status : 1
             */

            private String id;
            private String account;
            private int role_state;
            private String message_set;
            private String name;
            private int mac_type;
            private String create_time;
            private String create_user_id;
            private String update_time;
            private int status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public int getRole_state() {
                return role_state;
            }

            public void setRole_state(int role_state) {
                this.role_state = role_state;
            }

            public String getMessage_set() {
                return message_set;
            }

            public void setMessage_set(String message_set) {
                this.message_set = message_set;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getMac_type() {
                return mac_type;
            }

            public void setMac_type(int mac_type) {
                this.mac_type = mac_type;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getCreate_user_id() {
                return create_user_id;
            }

            public void setCreate_user_id(String create_user_id) {
                this.create_user_id = create_user_id;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
}
