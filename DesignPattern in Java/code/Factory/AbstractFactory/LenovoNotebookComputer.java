package Factory.AbstractFactory;

//具体产品  联想笔记本
public class LenovoNotebookComputer extends NotebookComputer{
    public String brand;

    public LenovoNotebookComputer(String brand) {
        this.brand = brand;
    }

    public void start() {
        System.out.println("联想"+brand+"笔记本启动中。。。");
    }
}
