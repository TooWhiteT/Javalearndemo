package FactoryMethod;
//具体工厂
public class GDComputerFactor extends ComputerFactory{
    public <T extends Computer> T createComputer(Class<T> brand) {
        Computer computer = null;
        String className = brand.getName();
        try {
            computer = (Computer) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) computer;
    }
}
class Client{
    public static void main(String[] args) {
        ComputerFactory computerFactory = new GDComputerFactor();
        computerFactory.createComputer(HpComputer.class).start();
        computerFactory.createComputer(LenovoComputer.class).start();
        computerFactory.createComputer(XMComputer.class).start();
    }
}
