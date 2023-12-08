package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankCardAcceptor extends VendingMachine {
    private static Scanner sc = new Scanner(System.in);
    private int cardNumber;
    private int code;

    public BankCardAcceptor(int amount) {
        super(amount);

    }

    @Override
    public void setData(){
        cardNumber = setCardNum();
        code = setCode();
    }

    public static int setCardNum() {
        System.out.println("Введите десятизначный номер карты: ");
        int number = 0;
        try {
            number = sc.nextInt();
            if (number < 0 || String.valueOf(number).length() < 8) {
                System.out.println("Неверный ввод данных!");
                number = setCardNum();
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Неверный ввод данных!");
            sc.nextLine();
            number = setCardNum();
        }
        return number;
    }

    public static int setCode() {
        System.out.println("Введите четырёхзначный одноразовый код: ");
        int code = 0;
        try {
            code = sc.nextInt();
            if (code < 0 || String.valueOf(code).length() != 4) {
                System.out.println("Неверный ввод данных!");
                code = setCode();
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Неверный ввод данных!");
            sc.nextLine();
            code = setCode();
        }
        return code;
    }

}