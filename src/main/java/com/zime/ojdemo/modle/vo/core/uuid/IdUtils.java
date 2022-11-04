package com.zime.ojdemo.modle.vo.core.uuid;

/**
 * ID生成器工具类
 *
 * @author ruoyi
 */
public class IdUtils
{
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }


}
