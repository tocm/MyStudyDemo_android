package sample.study.andy.andydemos.function.databinding.utils;


import android.annotation.TargetApi;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Build;

import org.parceler.Parcel;

import java.util.Objects;
@Parcel
public class BindableString extends BaseObservable {
    @Bindable
    String value;

    public String get() {
        return value != null ? value : "";
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void set(String value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            notifyChange();
        }
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }
}