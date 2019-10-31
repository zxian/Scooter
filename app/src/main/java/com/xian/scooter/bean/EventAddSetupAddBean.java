package com.xian.scooter.bean;

import java.util.List;

public class EventAddSetupAddBean {
    /**
     * type : 4
     * name : 多选框
     * select : [{"name":"4","checked":false},{"name":"5","checked":false},{"name":"6","checked":false}]
     * checked : false
     */

    private String type;
    private String name;
    private boolean checked;
    private String input;
    private List<SelectBean> select;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<SelectBean> getSelect() {
        return select;
    }

    public void setSelect(List<SelectBean> select) {
        this.select = select;
    }

    public static class SelectBean {
        /**
         * name : 4
         * checked : false
         */

        private String name;
        private boolean checked;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
}
