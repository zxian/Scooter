package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

public class FeedbackAddPar {
    private String content;
    private String feedback_user_id;

    public void setSign(){
        String sign = "content=" + content + "&feedback_user_id=" + feedback_user_id +"&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeedback_user_id() {
        return feedback_user_id;
    }

    public void setFeedback_user_id(String feedback_user_id) {
        this.feedback_user_id = feedback_user_id;
    }
}
