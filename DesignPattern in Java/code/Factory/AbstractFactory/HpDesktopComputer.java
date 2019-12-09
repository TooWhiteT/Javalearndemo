package Factory.AbstractFactory;

//具体产品    惠普台式
public class HpDesktopComputer extends DesktopComputer {
    private String brand;

    public HpDesktopComputer(String brand) {
        this.brand = brand;
    }

    public void start() {
        System.out.println("惠普"+brand+"台式机启动中。。。");
    }
}
