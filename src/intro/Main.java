package intro;
public class Main {

    public static void main(String[] args) {
  
        IProductService productService = new ProductManager(new FakeBankService());
        productService.sell(new Product(1, "Laptop", 1000),
                new Officer( 1, "Engin"));

    }
}

class Customer implements IEntity {
    private int id;
    private String name;
    private boolean isStudent;

    public Customer(int id, String name, boolean isStudent) {
        this.id = id;
        this.name = name;
        this.isStudent = isStudent;
    }
}

interface IEntity {
}

class Product implements IEntity {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

	public double getPrice() {
	
		return price;
	}
}

class ProductManager implements IProductService {
    private IBankService bankService;

    public ProductManager(IBankService bankService) {
        this.bankService = bankService;
    }

    public void sell(Product product, ICustomer customer) {
        double price = product.getPrice();
        price = customer.getPrice(price);
        price = bankService.convertRate(new CurrencyRate(1, price));
        System.out.println(price);
    }
}

interface IProductService {
    void sell(Product product, ICustomer customer);
}

class FakeBankService implements IBankService {

    public double convertRate(CurrencyRate currencyRate) {
  
        return currencyRate.getPrice() / 5.30;
    }
}

interface IBankService {
    double convertRate(CurrencyRate currencyRate);
}

class CurrencyRate {
    private double price;
    private int currency;

    public CurrencyRate(int currency, double price) {
        this.currency = currency;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getCurrency() {
        return currency;
    }
}


class CentralBankServiceAdapter implements IBankService {
    public double convertRate(CurrencyRate currencyRate) {
        CentralBankService centralBankService = new CentralBankService();
        return centralBankService.convertCurrency(currencyRate);
    }
}


class CentralBankService {
    public double convertCurrency(CurrencyRate currencyRate) {
        return currencyRate.getPrice() / 5.10;
    }
}

interface ICustomer { 
    int getId();
    String getName();
    double getPrice(double price);
}

class Officer implements ICustomer {
    private int id;
    private String name;

    public Officer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice(double price) {
        return price * 0.80;
    }
}

class Student implements ICustomer {
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice(double price) {
        return price * 0.50;
    }
}
