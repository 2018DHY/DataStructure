package com.quake.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author AKA二夕
 * @create 2020-07-13 16:05
 */
public class PolandNotation {
    public static void main(String[] args) {
        String suffixExpression = "30 4 + 5 * 6 -";
        List<String> list = getListString(suffixExpression);
        System.out.println("rpnList =" + list);
        int res = calculate(list);
        System.out.println("计算结果是：" + res);
    }


    // 将中缀表达式转换成对应的List
    public static List<String> toInfixExpressionList(String s) {
        // 定义一个List，存放在中缀表达式中对应的内容
        List<String> ls = new ArrayList<>();
        int i = 0;// 这是一个指针，用于遍历中缀表达式字符串
        String str;// 用于多位数的拼接
        char c;// 每遍历一个字符，就放入到c
        do {
            // 如果是一个非数字，就需要加入到ls
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                ls.add("" + s);
                i++;// i需要后移
            } else {// 如果是一个数，需要考虑多位数的情况
                str = "";// 先将str置空“ ”
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {
                    str += c;
                    i++;
                }
                ls.add(str);
            }
        } while (i < s.length());
        return ls;// 返回
    }

    // 依次将一个逆波兰表达式的数据和运算符放入到ArrayList中
    public static List<String> getListString(String suffixExpression) {
        // 将suffixExpression按照空格“ ”分开
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList<>();
        for (String ele : split) {
            list.add(ele);
        }
        return list;
    }

    // 完成对逆波兰表达式的运算
    /*
    运算规则：
        1）从左至右扫描，将3和4压入栈；
        2）遇到+运算符，因此pop出4和3（4为栈顶元素，3为次栈顶元素），计算3+4的值，得7，再将7入栈
        3）将5入栈；
        4）接下来是运算 *运算符，因此pop出5和7，计算7*5=35，将35入栈；
        5）将6入栈；
        6）最后是-运算符，计算出35-6的值，即29，由此得出最终结果
     */
    public static int calculate(List<String> ls) {
        // 创建一个栈（在这里只需要一个栈即可）
        Stack<String> stack = new Stack<String>();
        // 遍历ls
        for (String item : ls) {
            // 使用正则表达式来取出数
            if (item.matches("\\d+")) {// 匹配的是多位数
                // 入栈
                stack.push(item);
            } else {
                // pop出两个数，并运算，再入栈
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num2 + num1;
                } else if (item.equals("-")) {
                    res = num2 - num1;
                } else if (item.equals("*")) {
                    res = num2 * num1;
                } else if (item.equals("/")) {
                    res = num2 / num1;
                } else {
                    throw new RuntimeException("输入的运算符有误！！！");
                }
                // 把res入栈
                stack.push("" + res);// 加上双引号即可转换为字符串形式
            }
        }
        // 最后留在stack中的数据就是运算结果
        return Integer.parseInt(stack.pop());
    }
}
