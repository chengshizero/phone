package com.example.administrator.phone;

/**
 * Created by Administrator on 2016/12/11.
 */
public class Friend {
    public int _id;
    public String name;
    public String phone;

    public Friend() {
       super();
    }

    public Friend(int _id, String name, String phone) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
