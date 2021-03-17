package cn.xihan.age.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 23:47
 * @介绍 :
 */
public class ScreenRotateUtils {
    public static  float                     orientationDirection;
    private static ScreenRotateUtils         instance;
    private        Activity                  mActivity;
    private final  SensorManager             sm;
    private final  OrientationSensorListener listener;
    private              OrientationChangeListener changeListener;
    private final        Sensor                    sensor;

    public ScreenRotateUtils(Context context) {
        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new OrientationSensorListener();
    }

    public static ScreenRotateUtils getInstance(Context context) {
        if (instance == null) {
            instance = new ScreenRotateUtils(context);
        }
        return instance;
    }

    public void setOrientationChangeListener(OrientationChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    /**
     * 开启监听
     * 绑定切换横竖屏Activity的生命周期
     *
     * @param activity
     */
    public void start(Activity activity) {
        mActivity = activity;
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 注销监听
     */
    public void stop() {
        sm.unregisterListener(listener);
        mActivity = null;  // 防止内存泄漏
    }

    public interface OrientationChangeListener {
        void orientationChange(int orientation);
    }

    class OrientationSensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = -1;
            int DATA_X = 0;
            float x = -values[DATA_X];
            orientationDirection = -x;
            int DATA_Y = 1;
            float y = -values[DATA_Y];
            int DATA_Z = 2;
            float z = -values[DATA_Z];
            float magnitude = x * x + y * y;
            if (magnitude * 4 >= z * z) {
                float oneEightyOverPi = 57.29577957855f;
                float angle = (float) (Math.atan2(-y, x) * oneEightyOverPi);

                orientation = 90 - Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }

            // 手机系统的重力感应设置是否生效，默认无效，想要生效改成true就好了
            boolean isEffectSysSetting = true;
            try {
                int isRotate = Settings.System.getInt(mActivity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
                // 如果用户禁用掉了重力感应就直接return
                if (isRotate == 0) {
                    return;
                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            // 判断是否要进行中断信息传递
            // 是否打开传输，默认打开
            boolean isOpenSensor = true;
            changeListener.orientationChange(orientation);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

}

