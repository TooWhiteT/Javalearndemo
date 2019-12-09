package Factory.AbstractFactory;

//具体产品 联想台式
public class LenovoDesktopComputer extends DesktopComputer {
    private String brand;

    public LenovoDesktopComputer(String brand) {
        this.brand = brand;
    }

    public void start() {
        System.out.println("联想"+brand+"台式机启动中。。。");
    }
}
