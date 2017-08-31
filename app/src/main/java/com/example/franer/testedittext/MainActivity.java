package com.example.franer.testedittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements TextWatcher{

    private EditText mET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mET = (EditText) findViewById(R.id.editText);
        mET.addTextChangedListener(this);
    }

    private void updateET(String result, int cursorIndex) {
        mET.removeTextChangedListener(this);
        mET.setText(result);
        for (int x = 0, len = Math.min(cursorIndex, result.length()); x < len; x++) {//添加上光标位置以前的逗号个数
            char temp = result.charAt(x);
            switch (temp){
                case ',': cursorIndex++;
                case '.': break;
                default : continue;
            }
        }
        cursorIndex = Math.min(cursorIndex, result.length());
        mET.setSelection(cursorIndex < 0 ? result.length() : cursorIndex);
        mET.addTextChangedListener(this);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i("tiffany","start " + "count " + " after" + " s:");
        Log.i("tiffany","   " + start+ "   " + "  " + count+"   " + "  " + after + "  :"+s);

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i("franer","start " + "before " + " count" + " :s");
        Log.i("franer","   " + start+ "   " + "  " + before+"   " + "  " + count + "    :"+s);



        int cursorIndex = start + count;
        for (int x = 0; x < cursorIndex; x++) {// ,len = Math.min(s.length(), cursorIndex)
            switch (s.charAt(x)){
                case ',': cursorIndex--;
                case '.': break;
                default : continue;

            }
        }

//        Log.i("franer", "cursorIndex:"+cursorIndex);

        //0.判断用户直接输入0
        if (s.toString().startsWith("0") && s.toString().length() > 1){
            switch (String.valueOf(s.charAt(1))){
                case "0":
                    do{
                        s = s.subSequence(1,s.length());
                    } while(s.toString().startsWith("00"));

                case ".": break;

                default :
                    updateET(s.toString().substring(1), 0);
                    return;
            }
        }

        //1.首先判断是不是用户直接开始使用小数点开始输入,如果这样，直接清空EditText
        if (s.toString().startsWith(".")){
            if (s.length() == 1){
                updateET("0"+s, -1);
                return;
            } else {
                s = "0"+s;
            }
        }

        //2.首先获取干净的纯数字
        String pureNum = s.toString().replaceAll(",", "");

        //3.获取小数点的位置，分隔整数部分和小数部分
        int indexOfDot = pureNum.toString().indexOf(".");
        String intPart = indexOfDot == -1 ? pureNum : pureNum.substring(0, indexOfDot);
        String decPart = indexOfDot == -1 ? "" : pureNum.substring(indexOfDot+1);
        Log.i("franer","intPart:decPart="+intPart+":"+decPart);

        //4.判断，如果小数部分超过两位直接截掉两位后面的数字
        decPart = decPart.replace(".","");
        decPart = decPart.substring(0,Math.min(2,decPart.length()));

        //5.给整数部分添加逗号,SB表示StringBuffer, R表示是反转的
        StringBuffer intSB_R = new StringBuffer(intPart).reverse();//整数部分反转
        StringBuffer intSB_R_WithDot = new StringBuffer();
        for (int i = 0;i < intSB_R.length(); i+=3){
            intSB_R_WithDot.append(intSB_R.substring(i,Math.min(i+3,intSB_R.length())));
            switch (intSB_R.length()-i){
                case 0: case 1: case 2:case 3:
                    break;
                default:
                    intSB_R_WithDot.append(",");
                    break;
            }
        }
        String intPart_WithDot = intSB_R_WithDot.reverse().toString();

        //6.将整数部分和小数部分拼接起来,
        StringBuffer resultSB = new StringBuffer(intPart_WithDot);
        if (!TextUtils.isEmpty(decPart)||s.toString().endsWith("."))
            resultSB.append(".").append(decPart);
        updateET(resultSB.toString(), Math.min(cursorIndex, resultSB.length()));

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
