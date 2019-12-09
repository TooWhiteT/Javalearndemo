package Factory.SimpleFactory;

//工厂类    加工生产产品
public class ComputerFactory {
    public static Computer createComputer(BrandEnum brand,String brandNo) {
        Computer computer = null;
        switch (brand) {
            case LENOVO:
                computer = new LenovoComputer(brandNo);
                break;
            case HP:
                computer = new HpComputer(brandNo);
                break;
            case XM:
                computer = new XMComputer(brandNo);
                break;
        }
        return computer;
    }
}
class Test{
    public static void main(String[] args) {
        ComputerFactory factory = new ComputerFactory();
        factory.createComputer(BrandEnum.LENOVO,"小新潮 IDEA9000").start();
        factory.createComputer(BrandEnum.LENOVO,"ThinkPad E480").start();
        factory.createComputer(BrandEnum.HP,"暗夜精灵II").start();
        factory.createComputer(BrandEnum.HP,"暗夜精灵III极光竞技者").start();
        factory.createComputer(BrandEnum.XM,"2019代15.6").start();
        factory.createComputer(BrandEnum.XM,"2019代13.6").start();
    }
}
