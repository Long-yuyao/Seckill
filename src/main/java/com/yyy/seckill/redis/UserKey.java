package com.yyy.seckill.redis;

public class UserKey extends BasePrefix{
    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey GetByID = new UserKey("id");
    public static UserKey GetByName = new UserKey("name");
}
