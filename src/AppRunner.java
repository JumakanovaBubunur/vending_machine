import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private CoinAcceptor coinAcceptor;



    private static VendingMachine paymentType;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        setPaymentType();

    }

    public static VendingMachine choosePaymentType(VendingMachine[] paymentTypes) throws InputMismatchException, NumberFormatException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите номер способа оплаты: \n (1) Банковской картой\n (2): Монетами ");
        int num = sc.nextInt();
            if (num<=0 || num>2){
                System.out.println("Такой команды нет!");
                choosePaymentType(paymentTypes);
            }
        return paymentTypes[num-1];
    }

    public static void setPaymentType(){
        VendingMachine[] paymentTypes = {new BankCardAcceptor(50), new CoinAcceptor(50)};
        try {
            paymentType = choosePaymentType(paymentTypes);
        }catch (InputMismatchException | NumberFormatException e){
            System.out.println("Неверный формат ввода данных");
            setPaymentType();
        }
        if (paymentType == paymentTypes[0]){
            paymentTypes[0].setData();
        }
    }
    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
        System.exit(0);
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Денег всего: " + paymentType.getAmount());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (paymentType.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);

        if ("a".equalsIgnoreCase(action)) {
            if (paymentType == coinAcceptor) {
                paymentType.setAmount(paymentType.getAmount() + 10);
                print("Вы пополнили баланс на 10");
            } else {
                System.out.println("Невозможно пополнить баланс на карте");
            }
        } else if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            print("Завершение программы...");
        } else {
            try {
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                        paymentType.setAmount(paymentType.getAmount() - products.get(i).getPrice());
                        print("Вы купили " + products.get(i).getName());
                        break;
                    }
                }
            } catch (IllegalArgumentException e) {
                print("Недопустимая буква. Попробуйте еще раз.");
            }
        }
    }


    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
