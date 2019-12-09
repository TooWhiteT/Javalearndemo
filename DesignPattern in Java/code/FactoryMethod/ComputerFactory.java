package FactoryMethod;

//抽象工厂
public abstract class ComputerFactory {
    public abstract <T extends Computer> T createComputer(Class<T> brand);
}
