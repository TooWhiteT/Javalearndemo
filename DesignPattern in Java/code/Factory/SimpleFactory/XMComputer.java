package Factory.SimpleFactory;
//具体产品类  小米电脑
public class XMComputer extends Computer {
    private String brandNo;//型号

    public XMComputer(String brandNo) {
        this.brandNo = brandNo;
    }

    public void start() {
        System.out.println("小米"+brandNo+"正在开机。。。");
    }

    public void stop() {
        System.out.println("小米"+brandNo+"正在关机。。。");
    }
}
