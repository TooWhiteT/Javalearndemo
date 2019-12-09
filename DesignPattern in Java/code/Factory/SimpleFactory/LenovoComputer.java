package Factory.SimpleFactory;

//具体产品类  联想电脑
public class LenovoComputer extends Computer {

    private String brandNo;//型号

    public LenovoComputer(String brandNo) {
        this.brandNo = brandNo;
    }

    public void start() {
        System.out.println("联想"+brandNo+"正在开机。。。");
    }

    public void stop() {
        System.out.println("联想"+brandNo+"正在关机。。。");
    }
}
