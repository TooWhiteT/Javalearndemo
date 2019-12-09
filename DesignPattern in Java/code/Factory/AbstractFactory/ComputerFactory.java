package Factory.AbstractFactory;

//抽象工厂
public abstract class ComputerFactory {
    //两个抽象生产方法
    public abstract DesktopComputer createDesktopComputer(String brand);
    public abstract NotebookComputer createNotebookComputer(String brand);
}
class Test{
    public static void main(String[] args) {
        ComputerFactory lenovo = new LenovoFactory();
        lenovo.createDesktopComputer("-冰川9代").start();
        lenovo.createDesktopComputer("-冰川8代").start();
        lenovo.createNotebookComputer("ThinkPad E480").start();
        lenovo.createNotebookComputer("ThinkPad X1").start();
        ComputerFactory hp = new HpFactory();
        hp.createDesktopComputer("-火神I").start();
        hp.createDesktopComputer("-火神X").start();
        hp.createNotebookComputer("游戏本").start();
        hp.createNotebookComputer("极客本").start();
    }
}
