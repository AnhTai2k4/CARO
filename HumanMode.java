import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HumanMode extends JFrame implements ActionListener {
    public JFrame frame = new JFrame("AnhTai-CARO");
    public int col= 10,row=10;
    public JButton b[][]= new JButton[col+2][row+2];
    public JButton ng= new JButton("New Game");
    public JButton undo= new JButton("Undo");
    public JButton exit= new JButton("Exit");
    public boolean visited[][] = new boolean[col+2][row+2];
    public int cnt=1;
    public int size=col*row;
    public int x_undo[] = new int [size+1];
    public int y_undo[] = new int [size+1];
    public JLabel lb= new JLabel();
    public Timer timer;
    public boolean win=false;


    public HumanMode(){
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
        for(int i=0;i<col;i++){
            for(int j=0;j<row;j++){
                b[i][j]= new JButton(" ");
                b[i][j].setActionCommand(i+ " "+ j);
                b[i][j].addActionListener(this);
                pn2.add(b[i][j]);
            }
    }
    frame.add(pn2,BorderLayout.CENTER);
    frame.setLocationRelativeTo(null); 
    frame.setVisible(true);
    
    }

    public boolean checkwin(int i,int j){
        int cnt=0,cnt1=0,cnt2=0;
        //5 hang ngang
        String s= b[i][j].getText();
        while(b[i][j].getText()==s){
            cnt1++;
            i=i+1;
        }
        i=i-cnt1;
        while(b[i-1][j].getText()==s){
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
        while(b[i][j].getText()==s){
            cnt1++;
            j=j+1;
        }
        j=j-cnt1;
        while(b[i][j-1].getText()==s){
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
        while(b[i][j].getText()==s){
            cnt1++;
            i=i+1;
            j++;
        }
        i=i-cnt1;
        j-=cnt1;
        while(b[i-1][j-1].getText()==s){
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
        while(b[i][j].getText()==s){
            cnt1++;
            i--;
            j++;
        }
        i+=cnt1;
        j-=cnt1;
        while(b[i+1][j-1].getText()==s){
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
                lb.setText("Lượt của O");
                b[i][j].setText("X");
                
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
                    new HumanMode();
                    this.dispose();
                }

            }
            else{
                lb.setText("Lượt của X");
                b[i][j].setText("O");
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
                    new HumanMode();
                    this.dispose();
                }

            }
            
        }
        

    }

    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        if(s=="New Game"){
            new HumanMode();
            this.dispose();
            
        }

        if(s=="Undo"){
            b[x_undo[cnt-1]][y_undo[cnt-1]].setText(" ");
            b[x_undo[cnt-1]][y_undo[cnt-1]].setActionCommand(x_undo[cnt-1]+" "+y_undo[cnt-1]);
            visited[x_undo[cnt-1]][y_undo[cnt-1]]=false;
            cnt--;
            if(cnt%2==0) lb.setText("Lượt của O");
            else lb.setText("Lượt của X");
            
        }
        if(s=="Exit"){
            System.exit(0);
        }
        int k= s.indexOf(" ");
        int i= Integer.parseInt(s.substring(0,k));
        int j= Integer.parseInt(s.substring(k+1,s.length()));

        addpoint(i,j);

        
        
    }

    public static void main(String[] args) {
        new HumanMode();
    }
}
