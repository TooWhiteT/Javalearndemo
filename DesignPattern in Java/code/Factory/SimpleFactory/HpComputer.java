package Factory.SimpleFactory;

//具体产品类  惠普电脑
public class HpComputer extends Computer {
    private String brandNo;//型号

    public HpComputer(String brandNo) {
        this.brandNo = brandNo;
    }

    public void start() {
        System.out.println("惠普"+brandNo+"正在开机。。。");
    }

    public void stop() {
        System.out.println("惠普"+brandNo+"正在关机。。。");
    }
}
