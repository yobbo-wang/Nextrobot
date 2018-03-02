package wang.yobbo.common.appengine.plugin;

/**
 * 自定义查询枚举
 */
public enum SearchOperator {
    eq("等于", "="),
    ne("不等于", "!="),
    gt("大于", ">"),
    gte("大于等于", ">="),
    lt("小于", "<"),
    lte("小于等于", "<="),
    prefixLike("前缀模糊匹配", "like"),
    prefixNotLike("前缀模糊不匹配", "not like"),
    suffixLike("后缀模糊匹配", "like"),
    suffixNotLike("后缀模糊不匹配", "not like"),
    like("模糊匹配", "like"),
    notLike("不匹配", "not like"),
    isNull("空", "is null"),
    isNotNull("非空", "is not null"),
    in("包含", "in"),
    notIn("不包含", "not in"),
    custom("自定义默认的", (String)null);

    private final String desc;
    private final String rule;

    private SearchOperator(String desc, String rule) {
        this.desc = desc;
        this.rule = rule;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getRule() {
        return this.rule;
    }
}
