import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class tempCodeRunnerFile extends JFrame implements ActionListener {
    JFrame frame = new JFrame("AnhTai-Caro");
    JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Vẽ ảnh nền lên JPanel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    };
    JLabel label = new JLabel("Mời chọn chế độ");
    
    JButton b1 = new JButton();
    JButton b2 = new JButton();
    Image backgroundImage;

    public tempCodeRunnerFile() {
        frame.setLayout(new BorderLayout());
        b1.setText("Chơi với người");
        b1.setFont(new Font("Arial", Font.BOLD, 25));
        b2.setText("Chơi với máy");
        b2.setFont(new Font("Arial", Font.BOLD, 25));
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        label.setForeground(Color.WHITE);
        // Đảm bảo đường dẫn ảnh đúng
        backgroundImage = new ImageIcon("cach-choi-co-caro-7.jpg").getImage();

        // Đặt kích thước cho các nút
        b1.setPreferredSize(new Dimension(300, 40));
        b2.setPreferredSize(new Dimension(300, 40));
        b1.setMaximumSize(new Dimension(300, 60));
        b2.setMaximumSize(new Dimension(300, 60));

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set BoxLayout và căn chỉnh các thành phần
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 60));
        b1.setAlignmentX(Component.CENTER_ALIGNMENT);
        b2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thêm khoảng cách giữa các thành phần
        panel.add(Box.createVerticalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(b1);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(b2);
        panel.add(Box.createVerticalGlue());

        // Thêm panel vào frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s=e.getActionCommand();
        if(s=="Chơi với người") {
            new HumanMode();
        }

        if(s=="Chơi với máy"){
            new BotMode();

        }
    }

    public static void main(String[] args) {
        new tempCodeRunnerFile();
    }
}
