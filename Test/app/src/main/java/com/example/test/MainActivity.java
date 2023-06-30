package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
// 导入 Android 支持库，用于实现兼容性的 Activity

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
// 定义主活动

    EditText result;


    Button point, zeros, zero, one, two, three, four, five, six, seven, eight, nine;
    Button add, sub, mul, div;
    Button equals, del, cls;
    Button sqrt;
    // 定义按钮

    boolean clr_flag = false;
    boolean sqrt_flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);

        zeros = findViewById(R.id.zeros);
        zeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText = result.getText().toString();

                if (!currentText.isEmpty()) {
                    try {
                        Expression expression = new ExpressionBuilder(currentText).build();
                        double val = expression.evaluate();
                        // 若表达式没有问题则会正常进行计算
                        double currentValue = Double.parseDouble(currentText);
                        currentValue *= -1;
                        result.setText(String.valueOf(currentValue));
                    } catch (Exception e) {
                        // 若表达式有问题（比如 "5+"），此时弹出 Toast 消息提示错误
                        Toast.makeText(MainActivity.this, "表达式错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 为按钮 zeros 设置点击监听器，当点击时将当前结果转换为负数

        // 下面的代码为每个数字和运算符按钮设置点击监听器，并通过 id 进行查找

        zero = findViewById(R.id.zero);
        zero.setOnClickListener(this);

        point = findViewById(R.id.point);
        point.setOnClickListener(this);

        one = findViewById(R.id.one);
        one.setOnClickListener(this);

        two = findViewById(R.id.two);
        two.setOnClickListener(this);

        three = findViewById(R.id.three);
        three.setOnClickListener(this);

        four = findViewById(R.id.four);
        four.setOnClickListener(this);

        five = findViewById(R.id.five);
        five.setOnClickListener(this);

        six = findViewById(R.id.six);
        six.setOnClickListener(this);

        seven = findViewById(R.id.seven);
        seven.setOnClickListener(this);

        eight = findViewById(R.id.eight);
        eight.setOnClickListener(this);

        nine = findViewById(R.id.nine);
        nine.setOnClickListener(this);

        add = findViewById(R.id.add);
        add.setOnClickListener(this);

        sub = findViewById(R.id.sub);
        sub.setOnClickListener(this);

        mul = findViewById(R.id.mul);
        mul.setOnClickListener(this);

        div = findViewById(R.id.div);
        div.setOnClickListener(this);

        sqrt = findViewById(R.id.sqrt);
        sqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = result.getText().toString();
                // 获取当前的显示内容

                try {
                    Expression expression = new ExpressionBuilder(str).build();
                    double val = expression.evaluate();
                    // 解析并计算表达式

                    if (val < 0) {
                        Toast.makeText(MainActivity.this, "负数不能开平方根", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 如果结果是负数，提示错误信息，返回

                    double res = Math.sqrt(val);
                    result.setText(String.valueOf(res));
                    // 计算平方根，并显示结果
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "表达式错误", Toast.LENGTH_SHORT).show();
                }
                // 如果解析或计算表达式时出错，提示错误信息
            }
        });

        equals = findViewById(R.id.equals);
        equals.setOnClickListener(this);

        del = findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText = result.getText().toString();

                if (!currentText.isEmpty()) {
                    String updatedText = currentText.substring(0, currentText.length() - 1);
                    result.setText(updatedText);
                }
                // 如果当前显示内容不为空，则删除最后一个字符
            }
        });
        // 为按钮 del 设置点击监听器，当点击时删除最后一个字符

        cls = findViewById(R.id.cls);
        cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                // 清空显示内容
            }
        });
        // 为按钮 cls 设置点击监听器，当点击时清空显示内容
    }

    private boolean isOperator(char op) {
        return op == '+' || op == '-' || op == '*' || op == '/';
        // 检查一个字符是否是运算符
    }

    @Override
    public void onClick(View view) {
        // 定义 OnClickListener 处理点击事件

        String str = result.getText().toString();
        // 获取当前的显示内容

        int id = view.getId();
        // 获取被点击的视图的 id

        // 下面的代码处理数字和点的点击事件
        if (id == R.id.zeros || id == R.id.zero || id == R.id.one || id == R.id.two || id == R.id.three ||
                id == R.id.four || id == R.id.five || id == R.id.six || id == R.id.seven || id == R.id.eight || id == R.id.nine ||
                id == R.id.point) {

            if (clr_flag)
                str = "";
            // 如果需要清空显示内容，将 str 设为空字符串

            Button button = (Button) view;
            String buttonText = button.getText().toString();
            str += buttonText;
            // 获取按钮的文本，并添加到 str 后面

            result.setText(str);
            clr_flag = false;
            //下一次点击数字时不需要清空显示


        }else if (id == R.id.point) {
            // Check if the last operand already contains a decimal point
            String[] operands = str.split("[-+*/]");
            String lastOperand = operands[operands.length - 1];
            if (!lastOperand.contains(".")) {
                str += ".";
                result.setText(str);
                clr_flag = false;
            }
        }

        else if (id == R.id.add || id == R.id.sub || id == R.id.mul || id == R.id.div) {
            if (sqrt_flag)
                str = "";
            String operator = ((Button) view).getText().toString();
            String[] tokens = str.split(" ");
            // 获取按钮的文本，并将 str 分割成 tokens字符串数组
            if (tokens.length > 1) {
                String lastToken = tokens[tokens.length - 1];
                if (isOperator(lastToken.charAt(0))) {
                    tokens[tokens.length - 1] = operator;
                    StringBuilder sb = new StringBuilder();
                    for (String token : tokens) {
                        sb.append(token).append(" ");
                    }
                    str = sb.toString().trim();
                } else {
                    str += " " + operator + " ";
                }
            } else {
                str += " " + operator + " ";
            }

            result.setText(str);
            clr_flag = false;
            sqrt_flag = false;
        } else if (id == R.id.equals) {
            clr_flag = true;
            String exp = result.getText().toString();
            if (exp.isEmpty()) {
                Toast.makeText(this, "请输入一个表达式", Toast.LENGTH_SHORT).show();
                return;
            }
            // 如果当前的显示内容为空，提示错误信息，返回

            exp = exp.replace(" ", "");

            if(exp.contains("/0")){
                String[]tokens=exp.split("/");
                if(tokens.length>1&&tokens[1].equals("0")){
                    Toast.makeText(this,"除数不能为0",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            try {
                Expression e = new ExpressionBuilder(exp).build();
                double res = e.evaluate();
                result.setText(String.valueOf(res));
                // 显示结果
            } catch (Exception e) {
                Toast.makeText(this, "表达式错误", Toast.LENGTH_SHORT).show();
                return;
            }
            // 如果解析或计算表达式时出错，提示错误信息，返回
        }
    }
}
