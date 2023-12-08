package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CashReceiver {
    private static Scanner sc = new Scanner(System.in);
    private int cardNumber;
    private int code;

    public int getSumm() {
        return summ;
    }

    public void setSumm(int summ) {
        this.summ = summ;
    }

    private int summ =1000;



    public void setData(){
        cardNumber = setCardNum();
        code = setCode();
    }

    public static int setCardNum(){

        System.out.println("Введите номер карты: ");
        int number = 0;
        try {
           number = sc.nextInt();
           if (number<0){
               System.out.println("Неверный ввод данных!");
               setCardNum();
           }
           var length = String.valueOf(number).length();
           if (length <16){
               System.out.println("Неверная длина номера карты! Повторите ввод");
               setCardNum();
           }


        }catch (InputMismatchException e){
            System.out.println("Неверный ввод данных!");
            setCardNum();
        }

        return number;
    }

    public static int setCode() {
        int code = 0;
        System.out.println("Введите четырёхзначный одноразовый код: ");
        try {
            code = sc.nextInt();
            if (code<0){
                System.out.println("Неверный ввод данных!");
                setCode();
            }
            var length = String.valueOf(code).length();
            if (length <4){
                System.out.println("Неверная длина кода");
                setCode();
            }

        }
        catch (InputMismatchException e){
            System.out.println("Неверный формат данных");
        }
        return code;
    }
}