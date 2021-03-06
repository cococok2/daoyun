package com.team28.daoyunapp.activity;

import android.view.KeyEvent;

import com.team28.daoyunapp.R;
import com.team28.daoyunapp.utils.MMKVUtils;
import com.team28.daoyunapp.utils.TokenUtils;
import com.team28.daoyunapp.utils.Utils;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.widget.activity.BaseSplashActivity;
import com.xuexiang.xutil.app.ActivityUtils;

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * 启动页【无需适配屏幕大小】
 *
 * @author xuexiang
 * @since 2019-06-30 17:32
 */
public class SplashActivity extends BaseSplashActivity implements CancelAdapt {

    @Override
    protected long getSplashDurationMillis () {
        return 500;
    }

    /**
     * activity启动后的初始化
     */
    @Override
    protected void onCreateActivity () {
        initSplashView (R.drawable.xui_config_bg_splash);
        startSplash (false);
    }

    /**
     * 启动页结束后的动作
     */
    @Override
    protected void onSplashFinished () {
        boolean isAgree = MMKVUtils.getBoolean ("key_agree_privacy", false);
        if (isAgree) {
            if (TokenUtils.hasToken ()) {
                ActivityUtils.startActivity (MainActivity.class);
            } else {
                ActivityUtils.startActivity (LoginActivity.class);
            }
            finish ();
        } else {
            Utils.showPrivacyDialog (this, ( dialog, which ) -> {
                dialog.dismiss ();
                MMKVUtils.put ("key_agree_privacy", true);
                if (TokenUtils.hasToken ()) {
                    ActivityUtils.startActivity (MainActivity.class);
                } else {
                    ActivityUtils.startActivity (LoginActivity.class);
                }
                finish ();
            });
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event ) {
        return KeyboardUtils.onDisableBackKeyDown (keyCode) && super.onKeyDown (keyCode, event);
    }
}
