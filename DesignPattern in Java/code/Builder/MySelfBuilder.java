package Builder;

//抽象Builder类 的 实现类   我来规定组装的内容 列出清单
public class MySelfBuilder extends Builder {

    private Computer computer = new Computer();
    public Computer create() {
        return computer;
    }

    public void builderCPU(String cpu) {
        computer.setCpu(cpu);
        System.out.println("CPU:"+cpu);
    }

    public void builderRAW(String raw) {
        computer.setRaw(raw);
        System.out.println("内存:"+raw);
    }

    public void builderMainBorad(String mainborad) {
        computer.setMainborad(mainborad);
        System.out.println("主板:"+mainborad);
    }

    public void builderSSD(String ssd) {
        computer.setSsd(ssd);
        System.out.println("固态硬盘:"+ssd);
    }

    public void builderHHD(String hhd) {
        computer.setHhd(hhd);
        System.out.println("机械硬盘:"+hhd);
    }
}
