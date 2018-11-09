import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HMMGUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtuserssummerchaserdesktoptxt;
    private JTextField txtuserssummerchaserdesktoptxt_1;
    private JTextField txtuserssummerchaserdesktoptesttxt;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField txtuserssummerchaserdesktophmmtxt;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HMMGUI frame = new HMMGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public HMMGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 689, 345);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JLabel label = new JLabel("正训练集 ：");
        
        txtuserssummerchaserdesktoptxt = new JTextField();
        txtuserssummerchaserdesktoptxt.setText("/Users/summerchaser/Desktop/50+.txt");
        txtuserssummerchaserdesktoptxt.setColumns(10);
        
        JLabel label_1 = new JLabel("负训练集：");
        
        txtuserssummerchaserdesktoptxt_1 = new JTextField();
        txtuserssummerchaserdesktoptxt_1.setText("/Users/summerchaser/Desktop/50-.txt");
        txtuserssummerchaserdesktoptxt_1.setColumns(10);
        
        JLabel label_2 = new JLabel("测试集 ：");
        
        txtuserssummerchaserdesktoptesttxt = new JTextField();
        txtuserssummerchaserdesktoptesttxt.setText("/Users/summerchaser/Desktop/test.txt");
        txtuserssummerchaserdesktoptesttxt.setColumns(10);
        
        JButton btnNewButton = new JButton("运行 （输出结果到result.txt）");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                HMM hmm=new HMM("/Users/summerchaser/Desktop/50+.txt","/Users/summerchaser/Desktop/50-.txt","/Users/summerchaser/Desktop/test.txt","/Users/summerchaser/Desktop/hmm.txt",0.5,0.5);
                //得到一个完整结果输出 - 不做输出框 直接到文件 
                String result=hmm.HMModel();
            
               // System.out.println(result);
                
                try {
                    // 请在这修改文件输出路径
                    File fo = new File("/Users/summerchaser/Desktop/result.txt");
                    FileWriter fileWriter = new FileWriter(fo);
                    fileWriter.write(result);
                    fileWriter.close(); // 关闭数据流
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JButton button = new JButton("运行 （输出结果到result.txt）");
        
        JLabel label_3 = new JLabel("正训练集 ：");
        
        textField = new JTextField();
        textField.setText("/Users/summerchaser/Desktop/50+.txt");
        textField.setColumns(10);
        
        JLabel label_4 = new JLabel("负训练集：");
        
        JLabel label_5 = new JLabel("测试集 ：");
        
        textField_1 = new JTextField();
        textField_1.setText("/Users/summerchaser/Desktop/50-.txt");
        textField_1.setColumns(10);
        
        textField_2 = new JTextField();
        textField_2.setText("/Users/summerchaser/Desktop/test.txt");
        textField_2.setColumns(10);
        
        JLabel lblHmm = new JLabel("HMM测试长序列 ：");
        
        txtuserssummerchaserdesktophmmtxt = new JTextField();
        txtuserssummerchaserdesktophmmtxt.setText("/Users/summerchaser/Desktop/hmm.txt");
        txtuserssummerchaserdesktophmmtxt.setColumns(10);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGap(0, 689, Short.MAX_VALUE)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(23)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addComponent(button, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
                            .addGroup(gl_panel.createSequentialGroup()
                                .addComponent(label_3)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE))
                            .addGroup(gl_panel.createSequentialGroup()
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                    .addComponent(label_4)
                                    .addComponent(label_5))
                                .addGap(18)
                                .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(textField_1)
                                    .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE))))
                        .addGroup(gl_panel.createSequentialGroup()
                            .addComponent(lblHmm, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(txtuserssummerchaserdesktophmmtxt, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(178, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(19)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_3)
                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_4)
                        .addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_5))
                    .addGap(18)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblHmm)
                        .addComponent(txtuserssummerchaserdesktophmmtxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(38)
                    .addComponent(button)
                    .addContainerGap(79, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(23)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                            .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(label)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(txtuserssummerchaserdesktoptxt, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE))
                            .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                    .addComponent(label_1)
                                    .addComponent(label_2))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(txtuserssummerchaserdesktoptxt_1)
                                    .addComponent(txtuserssummerchaserdesktoptesttxt, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)))))
                    .addContainerGap(228, Short.MAX_VALUE))
                .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, 689, GroupLayout.PREFERRED_SIZE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label)
                        .addComponent(txtuserssummerchaserdesktoptxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_1)
                        .addComponent(txtuserssummerchaserdesktoptxt_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(txtuserssummerchaserdesktoptesttxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_2))
                    .addGap(72)
                    .addComponent(btnNewButton)
                    .addContainerGap(79, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
    }
}
