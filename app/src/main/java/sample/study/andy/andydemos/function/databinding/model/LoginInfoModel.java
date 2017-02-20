package sample.study.andy.andydemos.function.databinding.model;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.util.Patterns;

import org.parceler.Parcel;

import sample.study.andy.andydemos.R;
import sample.study.andy.andydemos.function.databinding.utils.BindableBoolean;
import sample.study.andy.andydemos.function.databinding.utils.BindableString;

/**
 * Created by Andy.chen on 2016/8/31.
 */
@Parcel
public class LoginInfoModel extends BaseObservable {

    public BindableString bindEmail = new BindableString();
    public BindableString bindPassword = new BindableString();
    public BindableBoolean bindableBoolean = new BindableBoolean();
    public BindableString bindEmailErr = new BindableString();
    public BindableString bindPasswordErr = new BindableString();

    @Bindable
    private boolean loginEnable;


    public boolean isLoginEnable() {
        return loginEnable;
    }

    public void setLoginEnable(boolean loginEnable) {
        Log.d("test","setLoginEnable  = "+loginEnable);
        this.loginEnable = loginEnable;
        notifyChange();//自动通知UI
    }

    public void reSet(){
        bindableBoolean.set(false);
        bindEmail.set(null);
        bindEmailErr.set(null);
        bindPassword.set(null);
        bindPasswordErr.set(null);
    }


    public boolean validate(Resources res) {
        int emailErrorRes = 0;
        int passwordErrorRes = 0;
        if (bindEmail.get().isEmpty()) {
            emailErrorRes = R.string.error_field_required;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(bindEmail.get()).matches()) {
                emailErrorRes = R.string.error_invalid_email;
            }
        }
        if (bindPassword.get().isEmpty()) {
            passwordErrorRes = R.string.error_field_required;
        }
        bindEmailErr.set(emailErrorRes != 0 ? res.getString(emailErrorRes) : null);
        bindPasswordErr.set(passwordErrorRes != 0 ? res.getString(passwordErrorRes) : null);

        //按钮自动enable
        setLoginEnable((bindEmail.isEmpty() || bindPassword.isEmpty()) ? false : true);

        return emailErrorRes == 0 && passwordErrorRes == 0;
    }
}
