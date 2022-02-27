package com.yyy.seckill.redis;

public class OderKey extends BasePrefix{

    public OderKey(String prefix) {
        super(prefix);
    }
    public static OderKey getMiaoshaOrderByUidGid = new OderKey("moug");
}
