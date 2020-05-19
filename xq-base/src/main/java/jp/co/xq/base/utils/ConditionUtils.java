package jp.co.xq.base.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.CaseFormat;

import jp.co.xq.base.example.ConditionTestExample;

/**
 * 動的検索条件作成ツール
 *
 * @param <T>
 * @author tian w
 */
public class ConditionUtils<T> {

  private T t;

  static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  static final String FIELD_FOR_REPLACE = "_FIELD_";

  static final String CONDITION_SUFFIX = "Condition";

  static final String CRITERIA_NAME = "Criteria";

  static Map<String, String> CONDITION_PAIR = new HashMap() {
    {
      put("is null", "and" + FIELD_FOR_REPLACE + "IsNull");
      put("is not null", "and" + FIELD_FOR_REPLACE + "IsNotNull");

      put("=", "and" + FIELD_FOR_REPLACE + "EqualTo");
      put("<>", "and" + FIELD_FOR_REPLACE + "NotEqualTo");
      put("&lt;&gt;", "and" + FIELD_FOR_REPLACE + "NotEqualTo");

      put(">=", "and" + FIELD_FOR_REPLACE + "GreaterThanOrEqualTo");
      put("&gt;=", "and" + FIELD_FOR_REPLACE + "GreaterThanOrEqualTo");

      put("<=", "and" + FIELD_FOR_REPLACE + "LessThanOrEqualTo");
      put("&lt;=", "and" + FIELD_FOR_REPLACE + "LessThanOrEqualTo");

      put(">", "and" + FIELD_FOR_REPLACE + "GreaterThan");
      put("&gt;", "and" + FIELD_FOR_REPLACE + "GreaterThan");

      put("<", "and" + FIELD_FOR_REPLACE + "LessThan");
      put("&lt;", "and" + FIELD_FOR_REPLACE + "LessThan");

      put("like", "and" + FIELD_FOR_REPLACE + "Like");
      put("not like", "and" + FIELD_FOR_REPLACE + "NotLike");
      put("in", "and" + FIELD_FOR_REPLACE + "In");
      put("not in", "and" + FIELD_FOR_REPLACE + "NotIn");
      put("between", "and" + FIELD_FOR_REPLACE + "Between");
      put("not between", "and" + FIELD_FOR_REPLACE + "NotBetween");
    }
  };

  public T getT() {
    return t;
  }

  public ConditionUtils(T key) {
    this.t = key;
  }

  static {

  }

  public T createExampleByMap(Map<String, Object> conditionMap)
      throws Exception {
    // メソッド取得処理
    Method[] methods = t.getClass().getDeclaredMethods();
    // 検索条件のcreateCriteriaを呼び出す
    Method method = getMethod("createCriteria", methods);
    Object criteria = method.invoke(t);

    // ———————————————————————————————————————条件検索作成START———————————————————————————————————————
    Class[] classes = t.getClass().getDeclaredClasses();
    Class classTypeCriteria = null;
    for (int i = 0; i < classes.length; i++) {
      if (classes[i].getSimpleName().equals(CRITERIA_NAME)) {
        classTypeCriteria = classes[i];
        break;
      }
    }

    Method[] criteriaMethods = classTypeCriteria.getDeclaredMethods();
    for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
      // field字段 condition与本身同时存在的时候
      if (conditionMap.get(entry.getKey() + CONDITION_SUFFIX) != null
          && !entry.getKey().contains(CONDITION_SUFFIX)) {
        if (entry.getValue() == null
            || StringUtils.isEmpty(entry.getValue().toString())) {
          continue;
        }
        // 获取条件式
        // 获取条件
        String condition = conditionMap
            .get(entry.getKey() + CONDITION_SUFFIX)
              .toString();
        if (condition != null) {
          String methodName = CONDITION_PAIR.get(condition);
          if (methodName != null) {
            methodName = methodName
                .replace(FIELD_FOR_REPLACE,
                    toUpperCaseFirstOne(entry.getKey()));
          }
          // 获取方法名
          Method conditionMethod = getMethod(methodName, criteriaMethods);
          // 获取参数类型
          Class paramType = null;
          int paramCount = conditionMethod.getParameterCount();
          // Example固定処理
          if (paramCount == 1) {
            paramType = conditionMethod.getParameterTypes()[0];
            String param = entry.getValue().toString();
            switch (paramType.getSimpleName()) {
            case "Double":
              conditionMethod.invoke(criteria, Double.parseDouble(param));
              break;
            case "BigDecimal":
              conditionMethod.invoke(criteria, new BigDecimal(param));
              break;
            case "Long":
              conditionMethod.invoke(criteria, Long.parseLong(param));
              break;
            case "String":
              if (methodName.toLowerCase().contains("like")) {
                param = "%" + param + "%";
              }
              conditionMethod.invoke(criteria, param);
              break;
            case "Date":
              conditionMethod.invoke(criteria, format.parse(param));
              break;
            case "Integer":
              conditionMethod.invoke(criteria, Integer.parseInt(param));
              break;
            case "Byte":
              conditionMethod.invoke(criteria, Byte.parseByte(param));
              break;
            case "List":
              // List固定由String与","隔开
              conditionMethod.invoke(criteria, Arrays.asList(param.split(",")));
              break;
            default:
              break;
            }
          } else if (paramCount == 2) {
            // betweenの場合
            paramType = conditionMethod.getParameterTypes()[0];
            String[] paramAry = entry.getValue().toString().split(",");
            switch (paramType.getSimpleName()) {
            case "Double":
              conditionMethod
                  .invoke(criteria, Double.parseDouble(paramAry[0]),
                      Double.parseDouble(paramAry[1]));
              break;
            case "BigDecimal":
              conditionMethod
                  .invoke(criteria, new BigDecimal(paramAry[0]),
                      new BigDecimal(paramAry[1]));
              break;
            case "Long":
              conditionMethod
                  .invoke(criteria, Long.parseLong(paramAry[0]),
                      Long.parseLong(paramAry[1]));
              break;
            case "String":
              conditionMethod.invoke(criteria, paramAry[0], paramAry[1]);
              break;
            case "Date":
              conditionMethod
                  .invoke(criteria, format.parse(paramAry[0]),
                      format.parse(paramAry[1]));
              break;
            case "Integer":
              conditionMethod
                  .invoke(criteria, Integer.parseInt(paramAry[0]),
                      Integer.parseInt(paramAry[1]));
              break;
            case "Byte":
              conditionMethod
                  .invoke(criteria, Byte.parseByte(paramAry[0]),
                      Byte.parseByte(paramAry[1]));
              break;
            case "List":
              conditionMethod
                  .invoke(criteria, Arrays.asList(paramAry[0].split(",")),
                      Arrays.asList(paramAry[1].split(",")));
              break;
            default:
              break;
            }
          }
        }
      }
    }
    // ———————————————————————————————————————条件検索作成処理
    // END———————————————————————————————————————

    // ========================================ソート処理
    // START======================================
    // ソート処理
    Object sidx = conditionMap.get("sidx");
    Object order = conditionMap.get("order");
    if (sidx != null && order != null) {
      Method methodOrder = getMethod("setOrderByClause", methods);
      // 【特殊】orderByパラメータをDBカラム名に変更処理
      String orderColumn = CaseFormat.LOWER_CAMEL
          .to(CaseFormat.LOWER_UNDERSCORE, sidx.toString());
      methodOrder.invoke(t, orderColumn + " " + order.toString());
    }
    // ========================================ソート処理
    // END======================================

    // ======================================改ページ処理
    // START======================================
    Object limit = conditionMap.get("limit");
    if (limit != null) {
      Method methodLimit = getMethod("setLimit", methods);
      methodLimit.invoke(t, Integer.parseInt(limit.toString()));
    }
    Object page = conditionMap.get("page");
    if (page != null) {
      Method methodLimit = getMethod("setOffset", methods);
      methodLimit
          .invoke(t, Integer.parseInt(limit.toString())
              * (Integer.parseInt(page.toString()) - 1));
    }
    // ======================================改ページ処理
    // END======================================

    return t;
  }

  /**
   * メソッド取得処理
   *
   * @param simpleName
   * @param criteriaMethods
   * @return
   */
  private Method getMethod(String simpleName, Method[] criteriaMethods) {

    for (int i = 0; i < criteriaMethods.length; i++) {
      if (criteriaMethods[i].getName().equalsIgnoreCase(simpleName)) {
        return criteriaMethods[i];
      }
    }
    return null;
  }

  /**
   * 最初アルファベットを大文字にする
   *
   * @param s
   * @return
   */
  String toUpperCaseFirstOne(String s) {
    if (StringUtils.isEmpty(s)) {
      return s;
    }
    if (Character.isUpperCase(s.charAt(0))) {
      return s;
    } else {
      return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
  }

  public static void main(String[] args) throws Exception {

    // for (int i = 0; i < 20; i++) {
    ConditionTestExample conditionTestExample = new ConditionTestExample();
    ConditionUtils<ConditionTestExample> conditionUtils = new ConditionUtils<>(
        conditionTestExample);
    Map map = new HashMap();
    map.put("id", "1");
    map.put("idCondition", "=");

    map.put("Username", "admin");
    map.put("UsernameCondition", "like");

    map.put("salt", "sad,asd,asd,asd,asd,as,das,d");
    map.put("saltCondition", "in");

    map.put("password", "sad,asd");
    map.put("passwordCondition", "between");

    map.put("createTime", "2017-12-28 00:00:00,2017-12-29 00:00:00");
    map.put("createTimeCondition", "between");

    // ページ数
    map.put("page", "2");
    // orderByカラム
    map.put("sidx", "id");
    // 順番
    map.put("order", "desc");
    // 一ページのデータ数
    map.put("limit", "10");
    long startDate = Calendar.getInstance().getTimeInMillis();
    conditionUtils.createExampleByMap(map);
    System.err.println(conditionUtils.createExampleByMap(map));
    System.err.println(Calendar.getInstance().getTimeInMillis() - startDate);
    // }

  }
}
