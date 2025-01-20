package ui.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class Menu extends JComponent{

    /**
     * @return the event
     */
    public ui.menu.MenuEvent getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(ui.menu.MenuEvent event) {
        this.event = event;
    }
   
    private ui.menu.MenuEvent event;
    private MigLayout layout;
    private final String[][] menuItems = new String[][]{
        {"TRANG CHỦ"},
        {"HÓA ĐƠN", "Bán hàng", "Lịch sử", "Xử lý đơn hàng hoàn" ,"Thống kê doanh thu (QL)", "Thống kê doanh thu (NV)"},
        {"SẢN PHẨM", "Danh mục", "Thêm sản phẩm", "Thống kê sản phẩm"},
        {"KHÁCH HÀNG", "Tìm kiếm khách hàng", "Thêm khách hàng"},
        {"NHÀ CUNG CẤP", "Tìm kiếm nhà cung cấp", "Thêm nhà cung cấp"},
        {"NHÂN VIÊN", "Tìm kiếm nhân viên", "Thêm nhân viên"},
        {"KHUYẾN MÃI", "Tìm kiếm khuyến mãi", "Thêm khuyến mãi"}
       };
    
    public Menu(){
        init();
    }
    
    public final void init(){
        layout = new MigLayout("Wrap 1, fillx, gapy 0, inset 2", "fill");
        setLayout(layout);
        setOpaque(true);
        //Init MenuItem
        for(int i = 0; i < menuItems.length; i++){
            addMenu(menuItems[i][0], i);
        }
        setBackground(new Color(255, 255, 255));
    }
    
    private Icon getIcon(int index) {
    String url = "src/main/java/ui/menu/" + index + ".png";
    if (url != null) {
        ImageIcon originalIcon = new ImageIcon(url);
        
        // Lấy BufferedImage từ ImageIcon
        BufferedImage image = new BufferedImage(originalIcon.getIconWidth(), originalIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();
        originalIcon.paintIcon(null, g, 0, 0);
        g.dispose();

        // Duyệt qua tất cả các pixel và đổi màu đen sang trắng
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgba = image.getRGB(x, y);
                Color col = new Color(rgba, true);

                // Nếu màu đen (hoặc gần đen), đổi thành trắng
                if (col.getRed() < 50 && col.getGreen() < 50 && col.getBlue() < 50) {
                    image.setRGB(x, y, new Color(255, 255, 255, col.getAlpha()).getRGB());
                }
            }
        }

        return new ImageIcon(image);
    } else {
        return null;
    }
}
   
    private void addMenu(String menuName, int index){
        int length = menuItems[index].length;
        MenuItem item = new MenuItem(menuName, index, length > 1);
        Icon icon = getIcon(index);
        if(icon != null){
            item.setIcon(icon);
        }
        item.addActionListener((ActionEvent e) -> {
            if(length > 1){
                if(!item.isSelected()){
                    item.setSelected(true);
                    addSubMenu(item, index, length, getComponentZOrder(item));
                } else {
                    //Hide menu
                    hideMenu(item, index);
                    item.setSelected(false);
                }
            } else {
                if(event != null){
                    event.selected(index, 0);
                }
            }
        });
        add(item);
        revalidate();
        repaint();
    }
   
    private void addSubMenu(MenuItem item, int index, int length, int indexZoder){
        JPanel panel = new JPanel(new MigLayout("Wrap 1, fillx, inset 0, gapy 0", "fill"));
        panel.setName(index + "");
        panel.setOpaque(false);
        for(int i = 1; i < length; i++){
            MenuItem subItem = new MenuItem(menuItems[index][i], i, false);
            subItem.addActionListener((ActionEvent e) -> {
                if(event != null){
                     event.selected(index, subItem.getIndex());
                }
            });
            subItem.initSubMenu(i, length);
            panel.add(subItem);
        }
        add(panel, "h 0!", indexZoder + 1); //indexZoder: put the SubMenu under the Menu that was selected
        revalidate();
        repaint();
        MenuAnimation.showMenu(panel, item, layout, true);
    }
    
    private void hideMenu(MenuItem item, int index){
        for(Component com : getComponents()){
            if(com instanceof JPanel && com.getName() != null && com.getName().equals(index + "")){
                com.setName(null);
                MenuAnimation.showMenu(com, item, layout, false);
                break;
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(102,204,255));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(g); 
    }
}
