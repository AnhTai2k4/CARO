import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;


public class BotMode extends JFrame implements ActionListener {
    public JFrame frame = new JFrame("AnhTai-CARO");
    public static int col= 10;
    public static int row=10;
    public static JButton b[][]= new JButton[col+1][row+1];
    public JButton ng= new JButton("New Game");
    public JButton undo= new JButton("Undo");
    public JButton exit= new JButton("Exit");
    public boolean visited[][] = new boolean[col+1][row+1];
    public int cnt=1;
    public int size=col*row;
    public int x_undo[] = new int [size+1];
    public int y_undo[] = new int [size+1];
    public JLabel lb= new JLabel();
    public Timer timer;
    public boolean win=false;

        
    public BotMode(){
        for(int i=1;i<=col;i++){
            for(int j=1;j<=row;j++){
                visited[i][j]=false;
            }
        }
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,20));
        //Tao nut new game, undo, exit
        
        ng.addActionListener(this);
        undo.addActionListener(this);
        exit.addActionListener(this);
        lb.setText("Lượt của X");
        lb.setFont(new Font("Arial", Font.BOLD, 18)); 

        exit.setForeground(Color.red);
        undo.setForeground(Color.blue);
        panel.add(lb);
        panel.add(ng);
        panel.add(undo);
        panel.add(exit);
        panel.setBackground(Color.WHITE);

        
        frame.add(panel,BorderLayout.NORTH);

        //Tao luoi bang gridlayout
        JPanel  pn2 = new JPanel();
        pn2.setLayout(new GridLayout(col,row));
        for(int i=1;i<=col;i++){
            for(int j=1;j<=row;j++){
                b[i][j]= new JButton(" ");
                b[i][j].setActionCommand(i+ " "+ j);
                b[i][j].addActionListener(this);
                pn2.add(b[i][j]);
            }
            
        
    }

    timer = new Timer(100, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            
                Pair p= autoPoint();
                System.out.println("Bot chọn ô: " + p.getFirst() + ", " + p.getSecond());
                addpoint(p.getFirst(),p.getSecond());
                timer.stop();
            
        }
    });

    frame.add(pn2,BorderLayout.CENTER);
    frame.setLocationRelativeTo(null); 
    frame.setVisible(true);
        
    }

    public Pair autoPoint() {
        Pair p = checkPoint1();
        if (p != null) {
            System.out.println("Bot đánh để thắng tại: " + p.getFirst() + ", " + p.getSecond());
            return p;
        }
        p = checkPoint2();
        if (p != null) {
            System.out.println("Bot chặn nước đi tại: " + p.first + ", " + p.second);
            return p;
        }
        p = createRandom();
        System.out.println("Bot đánh ngẫu nhiên tại: " + p.first + ", " + p.second);
        return p;
    }
    

    //Nuoc di ngau nhien cua bot
    public Pair createRandom() {
        List<Pair> a = listohople();
        if (a.isEmpty()) {
            throw new IllegalStateException("Không còn ô hợp lệ để bot đánh");
        }
        Random random = new Random();
        return a.get(random.nextInt(a.size()));
        
    }
    


    // Tìm các ô trống hợp lệ
    public static List<Pair> listohople() {
        List<Pair> list = new ArrayList<>();
        for (int i = 1; i <= col; i++) { 
            for (int j = 1; j <= row; j++) {
                if (b[i][j] != null && b[i][j].getText().equals(" ") && hasNeighbor(i, j)) {
                    list.add(new Pair(i, j));
                    System.out.println("Thêm ô hợp lệ: (" + i + ", " + j + ")");
                }
            }
        }
        return list;
        
    }
    
    
    
        
    
    
    //Check điểm có điểm xung quanh đã đánh ko
    public static boolean hasNeighbor(int i, int j) {
        for (int m = -1; m <= 1; m++) {
            for (int n = -1; n <= 1; n++) {
                if (m == 0 && n == 0) continue; // Bỏ qua chính nó
                int c = i+m;
                int r = j+n;
                if (r >= 1 && r <= row && c >= 1 && c <= col && b[c][r] != null && !b[c][r].getText().equals(" ")) {
                return true; // Có hàng xóm đã đánh
            }
        }
    }
    return false; // Không có hàng xóm nào đã đánh
    
    }

    //Nuoc co bot co the thang
    public Pair checkPoint1(){
        for(int i=1;i<=col;i++){
            for(int j=1;j<=row;j++){
                if(visited[i][j]==false){
                    visited[i][j]=true;
                    b[i][j].setText("O");
                    if(checkwin(i, j)==true){
                        visited[i][j]=false;
                        b[i][j].setText(" ");

                        Pair p = new Pair(i, j);
                        return p;
                    }

                    visited[i][j]=false;
                    b[i][j].setText(" ");
                    

                }
            }
        }
                return null;

    }

    //Nuoc co bot phải chặn nếu không sẽ thua
    public Pair checkPoint2(){
        for(int i=1;i<=col;i++){
            for(int j=1;j<=row;j++){
                if(visited[i][j]==false){
                    b[i][j].setText("X");
                    if(checkwin(i, j)==true){
                        visited[i][j]=false;
                        b[i][j].setText(" ");
                        

                        Pair p = new Pair(i, j);
                        return p;
                    }

                    visited[i][j]=false;
                    b[i][j].setText(" ");
                    

                }
            }
        }
         return null;
    }

    public boolean checkwin(int i,int j){
        int cnt=0,cnt1=0,cnt2=0;
        //5 hang ngang
        String s= b[i][j].getText();
        while(i<=col&&b[i][j].getText()==s){
            cnt1++;
            i=i+1;
        }
        i=i-cnt1;
        while(i>=2&&b[i-1][j].getText()==s){
            cnt2++;
            i--;
        }
        i=i+cnt2;
        cnt=cnt1+cnt2;

        if(cnt>=5) {
            win=true;
            return true;}

        //5 hang doc
        cnt=cnt1=cnt2=0;
        while(j<=row&&b[i][j].getText()==s){
            cnt1++;
            j=j+1;
        }
        j=j-cnt1;
        while(j>=2&&b[i][j-1].getText()==s){
            cnt2++;
            j--;
        }
        j=j+cnt2;
        cnt=cnt1+cnt2;

        if(cnt>=5) {
            win=true;
            return true;}

        //5 duong cheo

        cnt=cnt1=cnt2=0;
        //5 duong cheo xuong
        while(j<=row&&i<=col&&b[i][j].getText()==s){
            cnt1++;
            i++;
            j++;
        }
        i=i-cnt1;
        j-=cnt1;
        while(i>=2&&j>=2&&b[i-1][j-1].getText()==s){
            cnt2++;
            i--;
            j--;
        }
        i=i+cnt2;
        j+=cnt2;
        cnt=cnt1+cnt2;

        if(cnt>=5) {
            win=true;
            return true;}

        //5 duong cheo len
        cnt=cnt1=cnt2=0;
        while(i>=1&&j<=row&&b[i][j].getText()==s){
            cnt1++;
            i--;
            j++;
        }
        i+=cnt1;
        j-=cnt1;
        while(i<col&&j>=2&&b[i+1][j-1].getText()==s){
            cnt2++;
            i++;
            j--;
        }
        i=i-cnt2;
        j+=cnt2;
        cnt=cnt1+cnt2;

        if(cnt>=5) {
            win=true;
            return true;}
        else return false;

    }

    


    public void addpoint (int i,int j){
        x_undo[cnt]=i;y_undo[cnt]=j;
        if(visited[i][j]==false&&cnt<=size){
            if(cnt%2==1){
                b[i][j].setText("X");
                System.out.print("X da danh "+i+" "+j);
                lb.setText("Lượt của O");
                
                
                b[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                b[i][j].setForeground(Color.red);
                cnt++;
                visited[i][j]=true;
                if(checkwin(i,j)==true){
                    JOptionPane.showMessageDialog(
                    null, 
                     "X thắng!", 
                    "Kết quả", 
                    JOptionPane.INFORMATION_MESSAGE
                    ); // Hiển thị hộp thoại thông báo
                    new BotMode();
                    this.dispose();
                }
            }
            else{
                b[i][j].setText("O");
                System.out.print("O da danh "+i+" "+j);
                lb.setText("Lượt của X");
                
                b[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                b[i][j].setForeground(Color.BLUE);
                cnt++;
                visited[i][j]=true;
                if(checkwin(i,j)==true){
                    JOptionPane.showMessageDialog(
                    null, 
                     "O thắng!", 
                    "Kết quả", 
                    JOptionPane.INFORMATION_MESSAGE
                    ); // Hiển thị hộp thoại thông báo
                    new BotMode();
                    this.dispose();
                }

            }
            
        }
        

    }

    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        if(s=="New Game"){
            new BotMode();
            this.dispose();
            
        }

        else if(s=="Undo"){
            b[x_undo[cnt-1]][y_undo[cnt-1]].setText(" ");
            b[x_undo[cnt-2]][y_undo[cnt-2]].setText(" ");
            b[x_undo[cnt-1]][y_undo[cnt-1]].setActionCommand(x_undo[cnt-1]+" "+y_undo[cnt-1]);
            b[x_undo[cnt-2]][y_undo[cnt-2]].setActionCommand(x_undo[cnt-2]+" "+y_undo[cnt-2]);
            visited[x_undo[cnt-1]][y_undo[cnt-1]]=false;
            visited[x_undo[cnt-2]][y_undo[cnt-2]]=false;
            cnt-=2;
            
            lb.setText("Lượt của X");
            
        }
        else if(s=="Exit"){
            new Main();
        }
        else {
            if (cnt % 2 == 1) { // Lượt người chơi
                int k = s.indexOf(" ");
                int i = Integer.parseInt(s.substring(0, k));
                int j = Integer.parseInt(s.substring(k + 1));
                
                if (!visited[i][j]) { // Đảm bảo ô chưa được đánh
                    addpoint(i, j); // Người chơi đi
                    if (cnt % 2 == 0) { // Nếu đến lượt bot
                        timer.start(); // Kích hoạt lượt bot
                    }
                } else {
                    System.out.println("Ô đã được đánh: (" + i + ", " + j + ")");
                }
            }
        }
    }

    public static void main(String[] args) {
        new BotMode();
    }
}
