package Entity;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * 操作regedit注册表
 * 方法名中有sys的说明是对系统注册表进行操作
 */
public class PrefsHandler {
    private static PrefsHandler prefsHandler = null;
    private static final String node_name = "com.james_yang.demo";

    private PrefsHandler() {
    }

    public static PrefsHandler getInstance() {
        if (prefsHandler == null)
            prefsHandler = new PrefsHandler();
        return prefsHandler;
    }

    //均在固定地址下面建立注册表   相同的键值对不会存入两次，所以必须判断用户名是否重复
    public void setRegisterList(String key, String value) {
        //Preferences Prefs = Preferences.userNodeForPackage(this.getClass());  //当前包作为名称
        Preferences Prefs = Preferences.userRoot().node(node_name);
        Prefs.put(key, value);
        try {
            Prefs.flush();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    public void set_sys_RegisterList(String key, String value) {
        Preferences Prefs = Preferences.systemRoot().node(node_name);
        Prefs.put(key, value);
        try {
            Prefs.flush();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    public String findRegisterList(String key) {
        Preferences Prefs = Preferences.userRoot().node(node_name);
        return Prefs.get(key, "");
    }

    public String find_sys_RegisterList(String key) {
        Preferences Prefs = Preferences.systemRoot().node(node_name);
        return Prefs.get(key, "");
    }

    public boolean deleteRegisterList(String key, String value) {
        Preferences Prefs = Preferences.userRoot().node(node_name);
        String temp = findRegisterList(key);
        if (temp.equals(value)) { //说明找到了可以删除
            Prefs.remove(key);
            try {
                if (Prefs.keys().length == 0)  //如果没有新的信息了则删除本节点
                    Prefs.removeNode();
                Prefs.flush();  //删除时必须保证立刻修改注册表
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }
            return true;
        } else
            return false;  //说明不能删
    }

    public boolean delete_sys_RegisterList(String key, String value) {
        Preferences Prefs = Preferences.systemRoot().node(node_name);
        String temp = find_sys_RegisterList(key);
        if (temp.equals(value)) { //说明找到了可以删除
            Prefs.remove(key);
            try {
                if (Prefs.keys().length == 0)  //如果没有新的信息了则删除本节点
                    Prefs.removeNode();
                Prefs.flush();  //删除时必须保证立刻修改注册表
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }
            return true;
        } else
            return false;  //说明不能删
    }
}
