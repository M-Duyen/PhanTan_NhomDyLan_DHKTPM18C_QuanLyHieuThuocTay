package ui.model;

public class ModelDataPS_Circle {
    String name;
    int count;

    public ModelDataPS_Circle(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ModelDataPS_Circle1{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
