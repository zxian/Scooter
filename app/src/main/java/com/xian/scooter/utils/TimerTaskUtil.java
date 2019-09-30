package com.xian.scooter.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author : zhangzhao.ke
 * time   : 2018/08/13
 * desc   : 计时器
 */

public class TimerTaskUtil {

    private TimerTask mResetPwdTimerTask;//忘记密码页面的是时间计时器
    private Timer mResetPwdTimer;
    public int mResetPwdTime = 0;


    public int getmResetPwdTime() {
        return mResetPwdTime;
    }

    public void setmResetPwdTime(int mResetPwdTime) {
        this.mResetPwdTime = mResetPwdTime;
    }

    private static TimerTaskUtil instance;

    private TimerTaskUtil() {
    }

    public static TimerTaskUtil getInstance() {
        if (instance == null) {
            synchronized (TimerTaskUtil.class) {
                if (instance == null) {
                    instance = new TimerTaskUtil();
                }
            }
        }
        return instance;
    }


    public void startResetPwdTimer(int time) {
        mResetPwdTime = time;
        if (mResetPwdTimer == null) {
            mResetPwdTimer = new Timer();
        }
        if (mResetPwdTimerTask == null) {
            mResetPwdTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (mResetPwdTime == 0) {
                        stopResetPwdTimer();
                    } else {
                        mResetPwdTime--;
                    }
                }
            };
        }
        mResetPwdTimer.schedule(mResetPwdTimerTask, 0, 1000);
    }

    public void stopResetPwdTimer() {
        if (mResetPwdTimer != null) {
            mResetPwdTimer.cancel();
            mResetPwdTimer = null;
        }
        if (mResetPwdTimerTask != null) {
            mResetPwdTimerTask.cancel();
            mResetPwdTimerTask = null;
        }
        mResetPwdTime = 0;
    }


    private TimerTask mResetPhoneTimerTask;//忘记手机号页面的是时间计时器
    private Timer mResetPhoneTimer;
    public int mResetPhoneTime = 0;


    public int getmResetPhoneTime() {
        return mResetPhoneTime;
    }

    public void setmResetPhoneTime(int mResetPhoneTime) {
        this.mResetPhoneTime = mResetPhoneTime;
    }


    public void startResetPhoneTimer(int time) {
        mResetPhoneTime = time;
        if (mResetPhoneTimer == null) {
            mResetPhoneTimer = new Timer();
        }
        if (mResetPhoneTimerTask == null) {
            mResetPhoneTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (mResetPhoneTime == 0) {
                        stopResetPhoneTimer();
                    } else {
                        mResetPhoneTime--;
                    }
                }
            };
        }
        mResetPhoneTimer.schedule(mResetPhoneTimerTask, 0, 1000);
    }

    public void stopResetPhoneTimer() {
        if (mResetPhoneTimer != null) {
            mResetPhoneTimer.cancel();
            mResetPhoneTimer = null;
        }
        if (mResetPhoneTimerTask != null) {
            mResetPhoneTimerTask.cancel();
            mResetPhoneTimerTask = null;
        }
        mResetPhoneTime = 0;
    }

    private TimerTask mDynamicLoginTimerTask;//验证码登录页面的是时间计时器
    private Timer mDynamicLoginTimer;
    public int mDynamicLoginTime = 0;

    public int getmDynamicLoginTime() {
        return mDynamicLoginTime;
    }

    public void setmDynamicLoginTime(int mDynamicLoginTime) {
        this.mDynamicLoginTime = mDynamicLoginTime;
    }

    public void startLoginCodeTimer(int time) {
        mDynamicLoginTime = time;
        if (mDynamicLoginTimer == null) {
            mDynamicLoginTimer = new Timer();
        }
        if (mDynamicLoginTimerTask == null) {
            mDynamicLoginTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (mDynamicLoginTime == 0) {
                        stopLoginCodeTimer();
                    } else {
                        mDynamicLoginTime--;
                    }
                }
            };
        }
        mDynamicLoginTimer.schedule(mDynamicLoginTimerTask, 0, 1000);
    }

    public void stopLoginCodeTimer() {
        if (mDynamicLoginTimer != null) {
            mDynamicLoginTimer.cancel();
            mDynamicLoginTimer = null;
        }
        if (mDynamicLoginTimerTask != null) {
            mDynamicLoginTimerTask.cancel();
            mDynamicLoginTimerTask = null;
        }
        mDynamicLoginTime = 0;
    }


    private TimerTask mRegisterTimerTask;//注册页面的是时间计时器
    private Timer mRegisterTimer;
    public int mRegisterTime = 0;

    public int getmRegisterTime() {
        return mRegisterTime;
    }

    public void setmRegisterTime(int mDynamicRegisterTime) {
        this.mRegisterTime = mDynamicRegisterTime;
    }

    public void startRegisterTimer(int time) {
        mRegisterTime = time;
        if (mRegisterTimer == null) {
            mRegisterTimer = new Timer();
        }
        if (mRegisterTimerTask == null) {
            mRegisterTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (mRegisterTime == 0) {
                        stopRegisterTimer();
                    } else {
                        mRegisterTime--;
                    }
                }
            };
        }
        mRegisterTimer.schedule(mRegisterTimerTask, 0, 1000);
    }

    public void stopRegisterTimer() {
        if (mRegisterTimer != null) {
            mRegisterTimer.cancel();
            mRegisterTimer = null;
        }
        if (mRegisterTimerTask != null) {
            mRegisterTimerTask.cancel();
            mRegisterTimerTask = null;
        }
        mRegisterTime = 0;
    }

    /**
     * 停止所有计时器的时间
     */
    public void stopAllTime() {
        stopResetPwdTimer();
        stopRegisterTimer();
        stopResetPhoneTimer();
        stopLoginCodeTimer();
    }
}
