import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthStyle;

//一阶隐马尔科夫模型
//做一个真核生物细胞色素c特别功能区域识别模型
//模型构建    状态转移矩阵
public class HMM {
    


    public static void main(String[] args) {
        //读入三个文件
        HMM hmm=new HMM("/Users/summerchaser/Desktop/50+.txt","/Users/summerchaser/Desktop/50-.txt","/Users/summerchaser/Desktop/test.txt","/Users/summerchaser/Desktop/hmm.txt",0.5,0.5);
        //得到一个完整结果输出 - 不做输出框 直接到文件 
        String result=hmm.HMModel();
        System.out.println("---");
        System.out.println(result);

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



    static String train1_path,test_path,train2_path,hmm_path;

    static ArrayList<String> train1=new ArrayList<String>();
    static ArrayList<String> train2=new ArrayList<String>();
    static ArrayList<String> test=new ArrayList<String>();
    static ArrayList<String> hmm=new ArrayList<String>();
    
    static char base[]= {'A','T','G','C'};
    static String hmm_base[]= {"A+","T+","G+","C+","A-","T-","G-","C-"};
    
    static int length=base.length;
    static double TransMatrix1[][];
    static double TransMatrix2[][];
    static int CountMatrix1[][];
    static int CountMatrix2[][];
    static double p = 0.5 ;
    static double q = 0.5;
    static double HMMTransMatrix[][];
    static String result="";
    static String hmm_str;
    static double hmm_probability[][];//放节点概率的
    static int hmm_change[][];//记录状态转移


    
    public HMM(String train1_path,String train2_path,String test_path,String hmm_path,double p,double q)
    {
        this.train1_path=train1_path;
        this.train2_path=train2_path;
        this.test_path=test_path;
        this.hmm_path=hmm_path;
    }

    public String HMModel()
    {

        //正例
        File file_train1=new File(train1_path);
        File file_train2=new File(train2_path);
        File file_test=new File(test_path);
        File file_hmm=new File(hmm_path);
        Scanner scan1=null;
        try {
            scan1=new Scanner(file_train1);
            while(scan1.hasNext())
            {
                train1.add(scan1.nextLine());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            scan1.close();          
        }       
        
        //反例
        Scanner scan2=null;
        try {
            scan2=new Scanner(file_train2);
            while(scan2.hasNext())
            {
                train2.add(scan2.nextLine());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            scan2.close();          
        }       
        
        //测试集
        Scanner scan3=null;
        try {
            scan3=new Scanner(file_test);
            while(scan3.hasNext())
            {
                test.add(scan3.nextLine());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            scan3.close();          
        }
        
        //hmm集
        Scanner scan4=null;
        try {
            scan4=new Scanner(file_hmm);
            while(scan4.hasNext())
            {
                hmm.add(scan4.nextLine());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            scan4.close();          
        }
        //读入结束
        
        //初始+-转移矩阵、计数矩阵
        CountMatrix1=new int [length][length];
        CountMatrix2=new int [length][length];
        TransMatrix1=new double [length][length];
        TransMatrix2=new double [length][length];

        
        for (int i=0;i<length;i++)
        {
            for (int j=0;j<length;j++)
            {
                CountMatrix1[i][j]=0;
                CountMatrix2[i][j]=0;
                TransMatrix1[i][j]=0;
                TransMatrix2[i][j]=0;
            }
        }

        
        //计算+计数矩阵
        for (int i=0;i<train1.size();i++)
        {
            int len=train1.get(i).length();
            for (int j=0;j<len-1;j++)
            {
                char c1=train1.get(i).charAt(j);
                char c2=train1.get(i).charAt(j+1);
                int index1=FindChar(c1);
                int index2=FindChar(c2);
                CountMatrix1[index1][index2]=CountMatrix1[index1][index2]+1;
            }
        }
        
        //计算-计数矩阵
        for (int i=0;i<train2.size();i++)
        {
            int len=train2.get(i).length();
            for (int j=0;j<len-1;j++)
            {
                char c1=train2.get(i).charAt(j);
                char c2=train2.get(i).charAt(j+1);
                int index1=FindChar(c1);
                int index2=FindChar(c2);
                CountMatrix2[index1][index2]=CountMatrix2[index1][index2]+1;
            }
        }
        //获取hmm
        hmm_str = hmm.get(0);
        System.out.println("-=-2=-2=1=111111111111111111111121111111111");
        System.out.println(hmm_str);
        //计算+转移矩阵
        for (int i=0;i<length;i++)
        {
            int sum=0;
            for (int j=0;j<length;j++)
            {
                sum=sum+CountMatrix1[i][j];
            }
            for (int j=0;j<length;j++)
            {
                TransMatrix1[i][j]=(double)CountMatrix1[i][j]/(double)sum;
            }
        }
        
        //计算-转移矩阵
        for (int i=0;i<length;i++)
        {
            int sum=0;
            for (int j=0;j<length;j++)
            {
                sum=sum+CountMatrix2[i][j];
            }
            for (int j=0;j<length;j++)
            {
                TransMatrix2[i][j]=(double)CountMatrix2[i][j]/(double)sum;
            }
        }
        System.out.println("计数矩阵+：");
        result+="-------------------------------\n";
        result=result+"正例计数矩阵："+"\n";
        for (int i=0;i<length;i++)
        {
            for (int j=0;j<length;j++)
            {
                System.out.print(CountMatrix1[i][j]+"\t");
                result=result+CountMatrix1[i][j]+"\t";
            }
            System.out.println("");
            result=result+"\n";
        }
        System.out.println("");
        System.out.println("计数矩阵-：");
        result+="-------------------------------\n";
        result=result+"负例计数矩阵："+"\n";
        for (int i=0;i<length;i++)
        {
            for (int j=0;j<length;j++)
            {
                System.out.print(CountMatrix2[i][j]+"\t");
                result=result+CountMatrix2[i][j]+"\t";
            }
            System.out.println("");
            result=result+"\n";
        }
        System.out.println("");
        System.out.println("正转移矩阵：");
        result+="-------------------------------\n";
        result+="正转移矩阵：";
        result+="\n";
        for (int i=0;i<length;i++)
        {
            for (int j=0;j<length;j++)
            {
                System.out.print(TransMatrix1[i][j]+"\t");
                result+=TransMatrix1[i][j]+"\t";
            }
            System.out.println("");
            result+="\n";
        }
        System.out.println("");
        System.out.println("负转移矩阵：");
        result+="-------------------------------\n";
        result+="负转移矩阵：";
        result+="\n";
        for (int i=0;i<length;i++)
        {
            for (int j=0;j<length;j++)
            {
                System.out.print(TransMatrix2[i][j]+"\t");
                result+=TransMatrix2[i][j]+"\t";
            }
            System.out.println("");
            result+="\n";
        }
        System.out.println("");
        result+="\n";
        
        //计算得分
        for (int i=0;i<test.size();i++)
        {
            System.out.println("第"+i+"条测试序列得分:"+Score(test.get(i)));
            result+="-------------------------------\n";
            result+="第"+i+"条测试序列得分:\n"+Score(test.get(i));
            result+="\n";
            //得分以0为分界线
            if (Score(test.get(i))>0)
            {
                System.out.println("得分大于0 认为是功能序列");
                result+="得分大于0\n认为是功能序列";
                result+="\n";
                
            }
            else
            {
                System.out.println("得分小于0 认为不是功能序列");
                result+="得分小于0\n认为不是功能序列";
                result+="\n";
            }
        }
        
        //HMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
        HMMTransMatrix=new double [2*length][2*length];
        //初始化
        for (int i=0;i<2*length;i++)
        {
            for (int j=0;j<2*length;j++)
            {
                HMMTransMatrix[i][j]=0;
            }
        }
        //计算
        result+="-------------------------------------\n";
        result+="隐马尔科夫链部分 \n";
        result+="HMM转移矩阵\n";
        result+="-------------------------------------\n";
        
        
        for (int i=0;i<length;i++)
        {
            for (int j=0;j<length;j++)
            {
                HMMTransMatrix[i][j]=TransMatrix1[i][j]*p;
                HMMTransMatrix[i][j+length]=(double)(1-p)/(double)length;
                HMMTransMatrix[i+length][j]=(double)(1-q)/(double)length;
                HMMTransMatrix[i+length][j+length]=TransMatrix2[i][j]*q;
            }
        }
        
        for (int i=0;i<2*length;i++)
        {
            for (int j=0;j<2*length;j++)
            {
                System.out.print(HMMTransMatrix[i][j]+"\t");
                result+=HMMTransMatrix[i][j]+"\t";
            }
            result+="\n";
        }
        
        hmm();

        
        return result;
    }
    
    public int FindChar(char c)
    {
        for (int i=0;i<base.length;i++)
        {
            if (c==base[i])
            {
                return i;
            }
        }
        return base.length+1;
    }

    //计算一阶马尔科夫得分值
    public double Score(String X)
    {

        double SX=0;
        for (int i=0;i<X.length()-1;i++)
        {
            char c1=X.charAt(i);
            char c2=X.charAt(i+1);
            SX=SX+Math.log(TransMatrix1[FindChar(c1)][FindChar(c2)]/TransMatrix2[FindChar(c1)][FindChar(c2)])/Math.log(2);
        }
        SX=SX/(double)X.length();
        return SX;
    }
    
    public double HMMScore(String X)
    {
        double SX=1;
        for (int i=0;i<X.length()-1;i++)
        {
            char c1=X.charAt(i);
            char c2=X.charAt(i+1);
            int index1=FindChar(c1);
            int index2=FindChar(c2);
            SX=SX*MAX(HMMTransMatrix[index1][index2],HMMTransMatrix[index1][index2+length],HMMTransMatrix[index1+length][index2],HMMTransMatrix[index1+length][index2+length]);     
        }
        return SX;
    }
    
    public double MAX(double a,double b,double c,double d)
    {
        double max=a;
        if (b>max)
        {
            max=b;
        }
        if (c>max)
        {
            max=c;
        }
        if (d>max)
        {
            max=d;
        }
        return max;
    }
    
    public static void hmm() {
        hmm_probability = new double [2][hmm_str.length()];
        hmm_probability[0][0] = hmm_probability[1][0] = 1;
        hmm_change = new int [2][hmm_str.length()];
        for (int i=0;i<hmm_str.length()-1;i++) {
       
            String pos_current = hmm_str.charAt(i) + "+";
            String neg_current = hmm_str.charAt(i) + "-";
            String pos_next = hmm_str.charAt(i+1) + "+";
            String neg_next = hmm_str.charAt(i+1) + "-";
            //i+1 +概率
            //+到+ -到+
            double p1 = hmm_probability[0][i]*HMMTransMatrix[findIndex(pos_current)][findIndex(pos_next)];
            double p2 = hmm_probability[1][i]*HMMTransMatrix[findIndex(neg_current)][findIndex(pos_next)];

//            System.out.println(p1);
//            System.out.println(p2);
            //i为+ 
            if(p1>p2) {
                hmm_probability[0][i+1]=p1;
                hmm_change[0][i] = 0;
                
            }else {
                hmm_probability[0][i+1]=p2;
                hmm_change[0][i] = 1;
                
            }
        
          //i+1 -概率
           //+到- -到-
            double p3 = hmm_probability[0][i]*HMMTransMatrix[findIndex(pos_current)][findIndex(neg_next)];
            double p4 = hmm_probability[1][i]*HMMTransMatrix[findIndex(neg_current)][findIndex(neg_next)];
            //i为+ 
            if(p3>p4) {
                hmm_probability[1][i+1]=p3;
                hmm_change[1][i+1] = 0;
                
            }else {
                hmm_probability[1][i+1]=p4;
                hmm_change[1][i+1] = 1;
                
            }
            
            
            
      
            
        }
        result+="--------------------------------------\n";
        for (int i = 0;i<2;i++) {
            for (int j=0;j<hmm_str.length()-1;j++) {
                System.out.print(hmm_probability[i][j]+" ");
                //result+=hmm_probability[i][j]+" ";
            }
            System.out.println("");
            //result+="\n";
        }
        System.out.println("");
        result+="\n";
        result+="--------------------------------------\n";
      
        for (int i = 0;i<2;i++) {
            for (int j=0;j<hmm_str.length()-1;j++) {
                System.out.print(hmm_change[i][j]+" ");
                result+=hmm_change[i][j]+" ";
            }
            System.out.println("");
            result+="\n";
        }
        System.out.println("");
        result+="\n";
        

        int max = 0;
        if ( hmm_probability[0][hmm_str.length()-1] < hmm_probability[1][hmm_str.length()-1]) {
            max = 1;
        }
    
        StringBuffer f = new StringBuffer();
        if (max==0) {
            f.insert(0, '+');
        }else {
            f.insert(0, '-');
        }
        
        int current = max;

        for (int i=hmm_str.length()-1;i>0;i--) {
            if (hmm_change[current][i]==0) {
                f.insert(0, '+');
            }else {
                f.insert(0, '-');
            }
            
            current = hmm_change[current][i];
        }
        result+="-------------------------------------\n";
        result+="识别结果\n";
        System.out.println(hmm_str);
        result+=hmm_str+"\n";
        
        System.out.println(f.toString());
        result+=f.toString()+"\n";
        result+="\n";result+="\n";result+="\n";result+="\n";result+="\n";result+="\n";result+="\n";result+="\n";
        
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static int findIndex(String str) {
        for (int i=0;i<hmm_base.length;i++) {
            if(hmm_base[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}