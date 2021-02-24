import java.util.Scanner;

public class CoffeeMachine {
    final static Scanner scanner = new Scanner(System.in);

    // Current supplies
    private int waterSupply;
    private int milkSupply;
    private int beansSupply;
    private int cupsSupply;
    private int moneySupply;

    // One cup charges:
    // 0 - Espresso 1 - Latte 2 - Cappuccino
    private final int[] WATER_IN_ONE_CUP = new int[]{250, 350, 200};
    private final int[] MILK_IN_ONE_CUP = new int[]{0, 75, 100};
    private final int[] BEANS_IN_ONE_CUP = new int[]{16, 20, 12};
    private final int[] COST_OF_ONE_CUP = new int[]{4, 7, 6};


    public String AskAHuman(String Question) {
        System.out.print(Question);
        return scanner.next().trim().toLowerCase();
    }


    public CoffeeMachine(int water, int milk, int beans, int cups, int money) {
        waterSupply = water;
        milkSupply = milk;
        beansSupply = beans;
        cupsSupply = cups;
        moneySupply = money;
    }

    void sellCoffee() {
        String answer;
        String messageDeny;
        boolean denySellCoffee;

        sellCoffeeLoop:
        do {
            denySellCoffee = false;
            messageDeny = "Sorry, not enough";

            // Ask_A_Human:

            // System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:\n\r> ");
            answer = this.AskAHuman("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:\n\r> ");

            // Check the answer
            if (!answer.matches("[123]|back")) {
                System.out.println("Sorry, I don`t understand.");
                System.out.println("Please, try again.");
                continue;
            }

            // Check supplies
            switch (answer) {
                case "1":
                case "2":
                case "3":
                    if (waterSupply < this.WATER_IN_ONE_CUP[Integer.parseInt(answer) - 1]) {
                        denySellCoffee = true;
                        messageDeny += "\n water";
                    }
                    if (milkSupply < this.MILK_IN_ONE_CUP[Integer.parseInt(answer) - 1]) {
                        denySellCoffee = true;
                        messageDeny += "\n milk";
                    }
                    if (beansSupply < this.BEANS_IN_ONE_CUP[Integer.parseInt(answer) - 1]) {
                        denySellCoffee = true;
                        messageDeny += "\n coffee beans";
                    }
                    if (cupsSupply < 1) {
                        denySellCoffee = true;
                        messageDeny += "\n disposable cups";
                    }
                    break;
                case "back":
                    break sellCoffeeLoop;
            }
            if (denySellCoffee) {
                messageDeny += "!";
                System.out.println(messageDeny);
                break sellCoffeeLoop;
            } else {
                System.out.println("I have enough resources, making you a coffee!");
            }

            // Update supplies
            switch (answer) {
                case "1":
                case "2":
                case "3":
                    waterSupply -= this.WATER_IN_ONE_CUP[Integer.parseInt(answer) - 1];
                    milkSupply -= this.MILK_IN_ONE_CUP[Integer.parseInt(answer) - 1];
                    beansSupply -= this.BEANS_IN_ONE_CUP[Integer.parseInt(answer) - 1];
                    cupsSupply--;
                    moneySupply += this.COST_OF_ONE_CUP[Integer.parseInt(answer) - 1];
                    break sellCoffeeLoop;
                case "back":
                    break sellCoffeeLoop;
            }
        } while (true);
    }

    void fillMachine() {
        String answer = "";

        answer = this.AskAHuman("Write how many ml of water do you want to add:\n\r> ");
        this.waterSupply += Integer.parseInt(answer);

        answer = this.AskAHuman("Write how many ml of milk do you want to add:\n\r> ");
        this.milkSupply += Integer.parseInt(answer);

        answer = this.AskAHuman("Write how many grams of coffee beans do you want to add:\n\r> ");
        this.beansSupply += Integer.parseInt(answer);

        answer = this.AskAHuman("Write how many disposable cups of coffee do you want to add:\n\r> ");
        this.cupsSupply += Integer.parseInt(answer);
    }

    void giveOutCash() {
        System.out.println("I gave you $" + this.moneySupply);
        this.moneySupply = 0;
    }

    void printState() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(this.waterSupply + " of water");
        System.out.println(this.milkSupply + " of milk");
        System.out.println(this.beansSupply + " of coffee beans");
        System.out.println(this.cupsSupply + " of disposable cups");
        System.out.println("$" + this.moneySupply + " of money");
    }


    public static void main(String[] args) {
        CoffeeMachine macHine = new CoffeeMachine(400, 540, 120, 9, 550);
        String answer = "";

        Main_Loop:
        do {
            answer = macHine.AskAHuman("\nWrite action (buy, fill, take, remaining, exit):\n\r> ");

            // Choose action:
            switch (answer) {
                case "buy":
                    macHine.sellCoffee();
                    break;
                case "fill":
                    macHine.fillMachine();
                    break;
                case "take":
                    macHine.giveOutCash();
                    break;
                case "remaining":
                    macHine.printState();
                    break;
                case "exit":
                    break Main_Loop;
                default:
                    System.out.println("Sorry, I don`t understand.");
                    System.out.println("Please, try again.");
            }
        } while (true);

    }
}