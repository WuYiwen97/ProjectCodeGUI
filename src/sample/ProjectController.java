package sample;


import DataBase.DBController;
import SBFL.Dstar;
import SBFL.Ochiai;
import SMFL.Metallaxis;
import Utils.FileUtils;
import Utils.ShellUtils;
import Utils.StringUtils;
import handle.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {
    @FXML
    FlowPane flowpane1;

    @FXML
    FlowPane flowpane2;

    @FXML
    TextField version;

    @FXML
    Button runshell;

    @FXML
    TextArea run_result;

    @FXML
    Button output_buggy_lines;

    @FXML
    TextArea output_result;

    @FXML
    Button dstar;

    @FXML
    Button ochiai;

    @FXML
    Button metallaxis_dstar;

    @FXML
    Button metallaxis_ochiai;

    @FXML
    TextArea SBFL_result;

    @FXML
    TextArea SMFL_result;

    @FXML
    TextField SBFL_state;

    @FXML
    TextField SMFL_state;

    @FXML
    TextField anaylsis_lines;

    @FXML
    Button metallaxis_only;
    @FXML
    TextField metallaxis_state;
    @FXML
    TextArea metallaxis_result;
    @FXML
    PieChart bingtu;

    @FXML
    PieChart bingtu2;


    @FXML
    PieChart bingtu3;




    @FXML
    LineChart<String, Double> zhexian;

    @FXML
    LineChart<String, Double> zhexian2;

    @FXML
    LineChart<String,Double> zhexian3;




    int[] btnnum = new int[]{176,65,106,38,27};// button id  的数量
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        ObservableList<Node> children1 = flowpane1.getChildren();
        //为每个project生成不一样多的button
        for (int i = 0; i < children1.size(); i++) {
            Button b = (Button) children1.get(i);
            //得到 project name
            String projectname=b.getText();
            b.setUserData(i);
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Button b = (Button) event.getSource();
                    int i = Integer.valueOf(b.getUserData().toString());
                    flowpane2.getChildren().clear();
                    for (int j = 0; j < btnnum[i]; j++) {
                        String bugid= String.valueOf(j+1);
                        Button button = new Button(bugid);
                        button.setPrefWidth(55);
                        button.setUserData(bugid);
                        //加一个button
                        flowpane2.getChildren().add(button);
                        button.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Button b = (Button) actionEvent.getSource();
                                version.setText(projectname+"-"+b.getUserData().toString());
                            }
                        });
                    }
                }
            });

        }

        // 初始化界面内容
        bingtu.setData(getBingtu());
        bingtu2.setData(getBingtu());
        bingtu3.setData(getBingtu());

        zhexian.setData(getZhexian());
        zhexian.createSymbolsProperty();
        zhexian.setCreateSymbols(true);

        zhexian2.setData(getZhexian());
        zhexian2.createSymbolsProperty();
        zhexian2.setCreateSymbols(true);

        zhexian3.setData(getZhexian());
        zhexian3.createSymbolsProperty();
        zhexian3.setCreateSymbols(true);



        flowpane2.getChildren().clear();
        //初始化的时候未选择版本
        int i=0;
		/*for (int j = 0; j < btnnum[i]; j++) {
			//bug id
			String bugid= String.valueOf(j+1);
			Button button = new Button(bugid);
			button.setPrefWidth(55);
			button.setUserData(bugid);
			flowpane2.getChildren().add(button);
			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent actionEvent) {
					Button b = (Button) actionEvent.getSource();
					version.setText("please choose project name");
				}
			});
		}*/

		//点击run按钮 执行shell脚本
        runshell.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String versionText = version.getText();
                System.out.println("\""+versionText+"\"");
                if (versionText.equals("")) {
                    run_result.setText("你点了run按钮,但是没有卵用\n你需要选择projectname+bugID\n或者手动输入");
                }
                else {
                    //判断是不是 project+ID
                    String[] versionID_BUGID=versionText.split("-");
                    if (versionID_BUGID.length ==2){
                        String versionID=versionID_BUGID[0];
                        if (!StringUtils.isNumeric(versionID_BUGID[1])){
                            run_result.setText("bugid 必须是数字");
                        }
                        else {
                            int bugID= Integer.parseInt(versionID_BUGID[1]);

                            if (bugID<1){
                                run_result.setText("bug版本必须大于0");
                            }
                            else if((versionID.equals("Closure") &&bugID<177) || (versionID.equals("Lang")&&bugID<66) ||(versionID.equals("Math")&&bugID<107) ||(versionID.equals("Mockito") &&bugID<39)|| (versionID.equals("Time")&&bugID<28)){
                                //终于正确了
                                run_result.setText("choose "+versionText+" \nwaiting.....");//这句话应该输出啊
                                try {
                                    ShellUtils.Run_Gzoltar(versionID,bugID);
                                    ShellUtils.Run_Killmap(versionID,bugID);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                StringBuffer msg = new StringBuffer("");
                                msg.append(versionText+" run success"+"\n");
                                msg.append("生成矩阵和killmap文件在相应目录");
                                run_result.setText(msg.toString());

                            }
                            else {
                                run_result.setText("没有这个版本哦");
                            }
                        }
                    }
                    else {
                        run_result.setText("我怀疑你是乱写的 = =\n格式: project-id");
                    }
                }
            }
        });


        //点击output按钮 输出本来错误的行和错误内容
        output_buggy_lines.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String versionText = version.getText();
                System.out.println("\""+versionText+"\"");
                if (versionText.equals("")) {
                    output_result.setText("你点了按钮,但是没有卵用\n你需要选择projectname+bugID\n或者手动输入");
                }
                else {
                    //判断是不是 project+ID
                    String[] versionID_BUGID=versionText.split("-");
                    if (versionID_BUGID.length ==2){
                        String versionID=versionID_BUGID[0];
                        if (!StringUtils.isNumeric(versionID_BUGID[1])){
                            output_result.setText("bugid 必须是数字");
                        }
                        else {
                            int bugID= Integer.parseInt(versionID_BUGID[1]);

                            if (bugID<1){
                                output_result.setText("bug版本必须大于0");
                            }
                            else if((versionID.equals("Closure") &&bugID<177) || (versionID.equals("Lang")&&bugID<66) ||(versionID.equals("Math")&&bugID<107) ||(versionID.equals("Mockito") &&bugID<39)|| (versionID.equals("Time")&&bugID<28)){
                                //终于正确了 找到文件输出内容
                                //统计时间
                                //long startTime=System.currentTimeMillis();
                                List<String> buggylines=FileUtils.buggylinesFile(versionID,bugID);
                                //long endTime=System.currentTimeMillis();
                                //long runTime=endTime-startTime;
                                //DBController.addDstarRunTime(versionID,bugID,runTime);
                                StringBuffer msgBuggy=new StringBuffer("");
                                for (String bug:buggylines){
                                    msgBuggy.append(bug+"\n");
                                }
                                output_result.setText(msgBuggy.toString());
                            }
                            else {
                                output_result.setText("没有这个版本哦");
                            }
                        }
                    }
                    else {
                        output_result.setText("我怀疑你是乱写的 = =\n格式: project-id");
                    }
                }
            }
        });



        //点击Dstar按钮 或者 Ochiai按钮
        // 入库
        dstar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO 检查文件是否存在  检查是否输入需要分析的行
                String versionText = version.getText();
                System.out.println("\""+versionText+"\"");

                //初始化要分析的行
                int line=0;
                //先判断是否选择 版本
                if (versionText.equals("")) {
                    SBFL_state.setText("选择projectname+bugID或者输入");
                    SBFL_result.setText("");
                }
                else {
                    //判断是不是 project+ID
                    String[] versionID_BUGID=versionText.split("-");
                    if (versionID_BUGID.length ==2){
                        String versionID=versionID_BUGID[0];  //project name
                        if (!StringUtils.isNumeric(versionID_BUGID[1])){
                            SBFL_state.setText("bugid 必须是数字");
                            SBFL_result.setText("");
                        }
                        else {
                            int bugID= Integer.parseInt(versionID_BUGID[1]); //bug id

                            if (bugID<1){
                                SBFL_state.setText("bug版本必须大于0");
                                SBFL_result.setText("");
                            }
                            else if((versionID.equals("Closure") &&bugID<177) || (versionID.equals("Lang")&&bugID<66) ||(versionID.equals("Math")&&bugID<107) ||(versionID.equals("Mockito") &&bugID<39)|| (versionID.equals("Time")&&bugID<28)){
                                //终于正确了 检查 是否输入要分析的行
                                String anaylsisline=anaylsis_lines.getText();
                                System.out.println(anaylsisline);
                                if (StringUtils.isBlank(anaylsisline)){
                                    SBFL_state.setText("输入要分析的行");
                                    SBFL_result.setText("");
                                }
                                else if (!StringUtils.isNumeric(anaylsisline)){
                                    SBFL_state.setText("行  必须是数字");
                                    SBFL_result.setText("");
                                }
                                else{
                                    //行正确的了 版本也正确了 查看文件是否存在
                                    line =Integer.parseInt(anaylsis_lines.getText());
                                    if (!FileUtils.gzoltarFileExist(versionID,bugID)){
                                        SBFL_state.setText("no file exist");
                                        SBFL_result.setText("");
                                    }
                                    //TO 同时操作时间
                                    else{
                                        long startTime=System.currentTimeMillis();
                                        //TODO如果文件存在 读取文件 并分析 输出
                                        List<String []> stringListMatrix=MatrixHandler.readMatrixFileByLines(versionID,bugID);
                                        System.out.println("size is === it means ???测试用例    "+stringListMatrix.size());
                                        String[] first=stringListMatrix.get(1);
                                        //多少行程序  -1 最后是 +/- 号 需要删除
                                        System.out.println("row is ====it means ???多少行程序    "+(first.length-1));
                                        //转换成矩阵
                                        String matrix[][]=MatrixHandler.changeToMatrix(stringListMatrix);
                                        //SBFl处理矩阵计算可疑度 Ochiai or Dstar
                                        //List<Double> resultList = Ochiai.calculateOchiai(matrix);
                                        List<Double> resultList= Dstar.calculateDstar(matrix);

                                        //可疑度对象
                                        List<Suspicious> suspiciousList=SuspiciousHandler.listToObject(resultList);
                                        //给出前50最高可疑度的代码行  排序
                                        // 从小到大 怀疑度 增加
                                        List<Suspicious> suspiciousListAfterSort= LineSort.sortLine(suspiciousList);
                                         /*for(Suspicious suspicious:suspiciousListAfterSort){
                                            System.out.println("line="+suspicious.getId()+"suspicious"+suspicious.getResult());
                                          }*/
                                         //读取spectra文件
                                        List<String> stringListSpectra= SpectraHandler.readSpectraFileByLines(versionID,bugID);
                                        //spectralist 转换成spectra对象
                                        List<Spectra> spectraList= SpectraHandler.fileInSpectra(stringListSpectra);
                                        //要把可疑度值一起 传递来
                                        List<Spectra> spectraID=SpectraHandler.ochiaiToSpectra(spectraList,suspiciousListAfterSort,line);
                                        for (Spectra spectra:spectraID){
                                            System.out.println("需要变异的是"+spectra.getFilename()+"\t"+spectra.getLine());
                                        }


                                        //统计时间
                                        long endTime=System.currentTimeMillis();
                                        long runtime=endTime-startTime;
                                        try {
                                            DBController.addDstarRunTime(versionID,bugID,runtime);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        StringBuffer msgDstarOutPut=new StringBuffer("");
                                        //FIXME 显示一下有多少行，结束rm了
                                        msgDstarOutPut.append("程序有 "+(first.length-1)+" 行\n");

                                        //信息都存在在 spectraID 里面
                                        /**
                                         *   这里要放入数据库中 方便killmap查询
                                         * @param spectraID
                                         *
                                         */
                                        try {
                                            DBController.addSpectraByDstar(versionID,bugID,spectraID);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }

                                        // 展示 图  表

                                        //spectraID 显示
                                        bingtu.setData(getBingtu(spectraID));

                                        //表
                                        zhexian.setData(getZhexian(spectraID));




                                        for (Spectra spectra:spectraID){
                                            msgDstarOutPut.append(spectra.getFilename()+"#"+spectra.getLine()+"\n");
                                        }
                                        SBFL_state.setText("success");
                                        SBFL_result.setText(msgDstarOutPut.toString());

                                    }
                                }
                            }
                            else {
                                SBFL_state.setText("没有这个版本哦");
                                SBFL_result.setText("");
                            }
                        }
                    }
                    else {
                        SBFL_state.setText("别乱写= =");
                        SBFL_result.setText("");
                    }
                }

            }
        });

        ochiai.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO 检查文件是否存在  检查是否输入需要分析的行
                String versionText = version.getText();
                System.out.println("\""+versionText+"\"");

                //初始化要分析的行
                int line=0;
                //先判断是否选择 版本
                if (versionText.equals("")) {
                    SBFL_state.setText("选择projectname+bugID或者输入");
                    SBFL_result.setText("");
                }
                else {
                    //判断是不是 project+ID
                    String[] versionID_BUGID=versionText.split("-");
                    if (versionID_BUGID.length ==2){
                        String versionID=versionID_BUGID[0];  //project name
                        if (!StringUtils.isNumeric(versionID_BUGID[1])){
                            SBFL_state.setText("bugid 必须是数字");
                            SBFL_result.setText("");
                        }
                        else {
                            int bugID= Integer.parseInt(versionID_BUGID[1]); //bug id

                            if (bugID<1){
                                SBFL_state.setText("bug版本必须大于0");
                                SBFL_result.setText("");
                            }
                            else if((versionID.equals("Closure") &&bugID<177) || (versionID.equals("Lang")&&bugID<66) ||(versionID.equals("Math")&&bugID<107) ||(versionID.equals("Mockito") &&bugID<39)|| (versionID.equals("Time")&&bugID<28)){
                                //终于正确了 检查 是否输入要分析的行
                                String anaylsisline=anaylsis_lines.getText();
                                System.out.println(anaylsisline);
                                if (StringUtils.isBlank(anaylsisline)){
                                    SBFL_state.setText("输入要分析的行");
                                    SBFL_result.setText("");
                                }
                                else if (!StringUtils.isNumeric(anaylsisline)){
                                    SBFL_state.setText("行  必须是数字");
                                    SBFL_result.setText("");
                                }
                                else{
                                    //行正确的了 版本也正确了 查看文件是否存在
                                    line =Integer.parseInt(anaylsis_lines.getText());
                                    if (!FileUtils.gzoltarFileExist(versionID,bugID)){
                                        SBFL_state.setText("no file exist");
                                        SBFL_result.setText("");
                                    }
                                    //TO 同时操作时间
                                    else{
                                        long startTime=System.currentTimeMillis();
                                        //TODO如果文件存在 读取文件 并分析 输出
                                        List<String []> stringListMatrix=MatrixHandler.readMatrixFileByLines(versionID,bugID);
                                        System.out.println("size is === it means ???测试用例    "+stringListMatrix.size());
                                        String[] first=stringListMatrix.get(1);
                                        //多少行程序  -1 最后是 +/- 号 需要删除
                                        System.out.println("row is ====it means ???多少行程序    "+(first.length-1));
                                        //转换成矩阵
                                        String matrix[][]=MatrixHandler.changeToMatrix(stringListMatrix);
                                        //SBFl处理矩阵计算可疑度 Ochiai or Dstar
                                        List<Double> resultList = Ochiai.calculateOchiai(matrix);
                                        //List<Double> resultList= Dstar.calculateDstar(matrix);

                                        //可疑度对象
                                        List<Suspicious> suspiciousList=SuspiciousHandler.listToObject(resultList);
                                        //给出前50最高可疑度的代码行  排序
                                        // 从小到大 怀疑度 增加
                                        List<Suspicious> suspiciousListAfterSort= LineSort.sortLine(suspiciousList);
                                         /*for(Suspicious suspicious:suspiciousListAfterSort){
                                            System.out.println("line="+suspicious.getId()+"suspicious"+suspicious.getResult());
                                          }*/
                                        //读取spectra文件
                                        List<String> stringListSpectra= SpectraHandler.readSpectraFileByLines(versionID,bugID);
                                        //spectralist 转换成spectra对象
                                        List<Spectra> spectraList= SpectraHandler.fileInSpectra(stringListSpectra);
                                        //要把可疑度值一起 传递来
                                        List<Spectra> spectraID=SpectraHandler.ochiaiToSpectra(spectraList,suspiciousListAfterSort,line);
                                        for (Spectra spectra:spectraID){
                                            System.out.println("需要变异的是"+spectra.getFilename()+"\t"+spectra.getLine());
                                        }


                                        //统计时间
                                        long endTime=System.currentTimeMillis();
                                        long runtime=endTime-startTime;
                                        try {
                                            DBController.addOchiaiRunTime(versionID,bugID,runtime);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        StringBuffer msgDstarOutPut=new StringBuffer("");

                                        //信息都存在在 spectraID 里面
                                        /**
                                         *   这里要放入数据库中 方便killmap查询
                                         * @param spectraID
                                         *
                                         */
                                        try {
                                            DBController.addSpectraByOchiai(versionID,bugID,spectraID);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }

                                        //TODO  展示 图  表

                                        //spectraID 显示
                                        bingtu.setData(getBingtu(spectraID));

                                        //表
                                        zhexian.setData(getZhexian(spectraID));




                                        for (Spectra spectra:spectraID){
                                            msgDstarOutPut.append(spectra.getFilename()+"#"+spectra.getLine()+"\n");
                                        }
                                        SBFL_state.setText("success");
                                        SBFL_result.setText(msgDstarOutPut.toString());

                                    }
                                }
                            }
                            else {
                                SBFL_state.setText("没有这个版本哦");
                                SBFL_result.setText("");
                            }
                        }
                    }
                    else {
                        SBFL_state.setText("别乱写= =");
                        SBFL_result.setText("");
                    }
                }

            }
        });

        // 再运行SMFL  SMFL是在SBFL基础上的
        // 必须从数据库里面找
        metallaxis_dstar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO 检查文件是否存在  检查是否输入需要分析的行
                String versionText = version.getText();
                System.out.println("\""+versionText+"\"");

                //初始化要分析的行 这里可能不需要
                int line=0;
                //先判断是否选择 版本
                if (versionText.equals("")) {
                    SMFL_state.setText("不要乱动= =");
                    SMFL_result.setText("");
                }
                else {
                    //判断是不是 project+ID
                    String[] versionID_BUGID=versionText.split("-");
                    if (versionID_BUGID.length ==2){
                        String versionID=versionID_BUGID[0];  //project name
                        if (!StringUtils.isNumeric(versionID_BUGID[1])){
                            SMFL_state.setText("bugid 必须是数字");
                            SMFL_result.setText("");
                        }
                        else {
                            int bugID= Integer.parseInt(versionID_BUGID[1]); //bug id

                            if (bugID<1){
                                SMFL_state.setText("bug版本必须大于0");
                                SMFL_result.setText("");
                            }
                            else if((versionID.equals("Closure") &&bugID<177) || (versionID.equals("Lang")&&bugID<66) ||(versionID.equals("Math")&&bugID<107) ||(versionID.equals("Mockito") &&bugID<39)|| (versionID.equals("Time")&&bugID<28)){
                                //终于正确了 检查 是否输入要分析的行
                                String anaylsisline=anaylsis_lines.getText();
                                System.out.println(anaylsisline);
                                if (StringUtils.isBlank(anaylsisline)){
                                    SMFL_state.setText("输入要分析的行");
                                    SMFL_result.setText("");
                                }
                                else if (!StringUtils.isNumeric(anaylsisline)){
                                    SMFL_state.setText("行  必须是数字");
                                    SMFL_result.setText("");
                                }
                                else{
                                    //行正确的了 版本也正确了 查看文件是否存在
                                    line =Integer.parseInt(anaylsis_lines.getText());
                                    if (!FileUtils.killmapFileExist(versionID,bugID)){
                                        SMFL_state.setText("no file exist");
                                        SMFL_result.setText("");
                                    }
                                    else{
                                        //TODO如果文件存在 读取文件 并分析 输出

                                        List<String> stringListMutants=MutantsHandler.readMutantsFileByLines(versionID,bugID);
                                        List<Mutants> mutantsList=MutantsHandler.fileToMutants(stringListMutants);

                                        List<String> stringListKillmap=KillmapHandler.readKillmapFileByLines(versionID,bugID);
                                        List<Killmap> killmapList=KillmapHandler.fileToKillmap(stringListKillmap);

                                        /**
                                         * 行对应变异 要找到变异的ID
                                         * 在s_d_D or s_d_O数据库里面找
                                         */
                                        System.out.println("===========");
                                        try {

                                            //统计时间
                                            long startTime=System.currentTimeMillis();


                                            List<Spectra> spectraID=DBController.findSpectraByDstar(versionID,bugID,line);

                                            //数据存放在mutants里面
                                            List<Mutants> mutantsIDList=MutantsHandler.spectraToMutants(spectraID,mutantsList);
                                            System.out.println(mutantsIDList.size());
                                            for (Mutants a:mutantsIDList){
                                                System.out.println(a.getID()+"\t"+a.getFilename()+"\t"+a.getCodeline());
                                            }
                                            //计算 killmaplist 和 mustantsIDList 一起
                                            //Metall
                                            List<Mutants> mutantsSuspiciousList= Metallaxis.calculateMetallaxis(killmapList,mutantsIDList);

                                            for (Mutants a:mutantsSuspiciousList){
                                                System.out.println(a.getID()+"\t"+a.getFilename()+"\t"+a.getCodeline()+"\t"+a.getSuspicion());
                                            }

                                            System.out.println("sort mutants suspicious");
                                            List<Mutants> mutantsAfterSort = LineSort.sortMutants(mutantsSuspiciousList);
                                            for (Mutants a:mutantsAfterSort){
                                                System.out.println(a.getID()+"\t"+a.getFilename()+"\t"+a.getCodeline()+"\t"+a.getSuspicion());
                                            }

                                            //变异体对应到行
                                            List<Spectra> spectraFinall=SpectraHandler.getSpectraFinall(mutantsAfterSort,spectraID);
                                            List<Spectra> spectrasFinallSort=LineSort.sortSpectra(spectraFinall);
                                            for (Spectra spectra:spectrasFinallSort){
                                                System.out.println("变异后的排行"+spectra.getFilename()+"\t"+spectra.getLine()+"\t"+spectra.getSuspicious());
                                            }

                                            //时间加入数据库
                                            long endTime=System.currentTimeMillis();
                                            long runtime=endTime-startTime;
                                            DBController.addMetall_DstarRunTime(versionID,bugID,runtime);

                                            //加入数据库中 显示
                                            DBController.addSpectraByMetallDstar(versionID,bugID,spectrasFinallSort);


                                            //piechart显示
                                            bingtu2.setData(getBingtu2(spectrasFinallSort));

                                            //表
                                            zhexian2.setData(getZhexian2(spectrasFinallSort));



                                            StringBuffer msgFinal=new StringBuffer("");
                                            for(Spectra spectra:spectrasFinallSort){
                                                msgFinal.append(spectra.getFilename()+"#"+spectra.getLine()+"\n");
                                            }
                                            SMFL_state.setText("success");
                                            SMFL_result.setText(msgFinal.toString());

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }





                                    }
                                }
                            }
                            else {
                                SMFL_state.setText("没有这个版本哦");
                                SMFL_result.setText("");
                            }
                        }
                    }
                    else {
                        SMFL_state.setText("别乱写= =");
                        SMFL_result.setText("");
                    }
                }

            }
        });

       // ochiai M_ochiai
        metallaxis_ochiai.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO 检查文件是否存在  检查是否输入需要分析的行
                String versionText = version.getText();
                System.out.println("\""+versionText+"\"");

                //初始化要分析的行 这里可能不需要
                int line=0;
                //先判断是否选择 版本
                if (versionText.equals("")) {
                    SMFL_state.setText("不要乱动= =");
                    SMFL_result.setText("");
                }
                else {
                    //判断是不是 project+ID
                    String[] versionID_BUGID=versionText.split("-");
                    if (versionID_BUGID.length ==2){
                        String versionID=versionID_BUGID[0];  //project name
                        if (!StringUtils.isNumeric(versionID_BUGID[1])){
                            SMFL_state.setText("bugid 必须是数字");
                            SMFL_result.setText("");
                        }
                        else {
                            int bugID= Integer.parseInt(versionID_BUGID[1]); //bug id

                            if (bugID<1){
                                SMFL_state.setText("bug版本必须大于0");
                                SMFL_result.setText("");
                            }
                            else if((versionID.equals("Closure") &&bugID<177) || (versionID.equals("Lang")&&bugID<66) ||(versionID.equals("Math")&&bugID<107) ||(versionID.equals("Mockito") &&bugID<39)|| (versionID.equals("Time")&&bugID<28)){
                                //终于正确了 检查 是否输入要分析的行
                                String anaylsisline=anaylsis_lines.getText();
                                System.out.println(anaylsisline);
                                if (StringUtils.isBlank(anaylsisline)){
                                    SMFL_state.setText("输入要分析的行");
                                    SMFL_result.setText("");
                                }
                                else if (!StringUtils.isNumeric(anaylsisline)){
                                    SMFL_state.setText("行  必须是数字");
                                    SMFL_result.setText("");
                                }
                                else{
                                    //行正确的了 版本也正确了 查看文件是否存在
                                    line =Integer.parseInt(anaylsis_lines.getText());
                                    if (!FileUtils.killmapFileExist(versionID,bugID)){
                                        SMFL_state.setText("no file exist");
                                        SMFL_result.setText("");
                                    }
                                    else{
                                        //TODO如果文件存在 读取文件 并分析 输出

                                        List<String> stringListMutants=MutantsHandler.readMutantsFileByLines(versionID,bugID);
                                        List<Mutants> mutantsList=MutantsHandler.fileToMutants(stringListMutants);

                                        List<String> stringListKillmap=KillmapHandler.readKillmapFileByLines(versionID,bugID);
                                        List<Killmap> killmapList=KillmapHandler.fileToKillmap(stringListKillmap);

                                        /**
                                         * 行对应变异 要找到变异的ID
                                         * 在s_d_D or s_d_O数据库里面找
                                         */
                                        System.out.println("===========");
                                        try {

                                            //统计时间
                                            long startTime=System.currentTimeMillis();


                                            List<Spectra> spectraID=DBController.findSpectraByOchiai(versionID,bugID,line);

                                            //数据存放在mutants里面
                                            List<Mutants> mutantsIDList=MutantsHandler.spectraToMutants(spectraID,mutantsList);
                                            System.out.println(mutantsIDList.size());
                                            for (Mutants a:mutantsIDList){
                                                System.out.println(a.getID()+"\t"+a.getFilename()+"\t"+a.getCodeline());
                                            }
                                            //TODO killmaplist 和 mustantsIDList 一起
                                            List<Mutants> mutantsSuspiciousList= Metallaxis.calculateMetallaxis(killmapList,mutantsIDList);

                                            /*for (Mutants a:mutantsSuspiciousList){
                                                System.out.println(a.getID()+"\t"+a.getFilename()+"\t"+a.getCodeline()+"\t"+a.getSuspicion());
                                            }*/

                                            System.out.println("sort mutants suspicious");
                                            List<Mutants> mutantsAfterSort = LineSort.sortMutants(mutantsSuspiciousList);
                                            for (Mutants a:mutantsAfterSort){
                                                System.out.println(a.getID()+"\t"+a.getFilename()+"\t"+a.getCodeline()+"\t"+a.getSuspicion());
                                            }

                                            //变异体对应到行
                                            List<Spectra> spectraFinall=SpectraHandler.getSpectraFinall(mutantsAfterSort,spectraID);
                                            List<Spectra> spectrasFinallSort=LineSort.sortSpectra(spectraFinall);
                                            for (Spectra spectra:spectrasFinallSort){
                                                System.out.println("变异后的排行"+spectra.getFilename()+"\t"+spectra.getLine()+"\t"+spectra.getSuspicious());
                                            }

                                            //时间加入数据库
                                            long endTime=System.currentTimeMillis();
                                            long runtime=endTime-startTime;
                                            DBController.addMetall_OchiaiRunTime(versionID,bugID,runtime);

                                            //加入数据库中 显示
                                            DBController.addSpectraByMetallOchiai(versionID,bugID,spectrasFinallSort);


                                            //piechart显示
                                            bingtu2.setData(getBingtu2(spectrasFinallSort));

                                            //表
                                            zhexian2.setData(getZhexian2(spectrasFinallSort));



                                            StringBuffer msgFinal=new StringBuffer("");
                                            for(Spectra spectra:spectrasFinallSort){
                                                msgFinal.append(spectra.getFilename()+"#"+spectra.getLine()+"\n");
                                            }
                                            SMFL_state.setText("success");
                                            SMFL_result.setText(msgFinal.toString());

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }





                                    }
                                }
                            }
                            else {
                                SMFL_state.setText("没有这个版本哦");
                                SMFL_result.setText("");
                            }
                        }
                    }
                    else {
                        SMFL_state.setText("别乱写= =");
                        SMFL_result.setText("");
                    }
                }

            }
        });

        // 只有metall
        metallaxis_only.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String versionText = version.getText();
                System.out.println("\""+versionText+"\"");

                //初始化要分析的行 这里可能不需要
                int line=0;
                //先判断是否选择 版本
                if (versionText.equals("")) {
                    metallaxis_state.setText("不要乱动= =");
                    metallaxis_result.setText("");
                }
                else {
                    //判断是不是 project+ID
                    String[] versionID_BUGID=versionText.split("-");
                    if (versionID_BUGID.length ==2){
                        String versionID=versionID_BUGID[0];  //project name
                        if (!StringUtils.isNumeric(versionID_BUGID[1])){
                            metallaxis_state.setText("bugid 必须是数字");
                            metallaxis_result.setText("");
                        }
                        else {
                            int bugID= Integer.parseInt(versionID_BUGID[1]); //bug id

                            if (bugID<1){
                                metallaxis_state.setText("bug版本必须大于0");
                                metallaxis_result.setText("");
                            }
                            else if((versionID.equals("Closure") &&bugID<177) || (versionID.equals("Lang")&&bugID<66) ||(versionID.equals("Math")&&bugID<107) ||(versionID.equals("Mockito") &&bugID<39)|| (versionID.equals("Time")&&bugID<28)){
                                //终于正确了 检查 是否输入要分析的行
                                String anaylsisline=anaylsis_lines.getText();
                                System.out.println(anaylsisline);
                                if (StringUtils.isBlank(anaylsisline)){
                                    metallaxis_state.setText("输入要分析的行");
                                    metallaxis_result.setText("");
                                }
                                else if (!StringUtils.isNumeric(anaylsisline)){
                                    metallaxis_state.setText("行  必须是数字");
                                    metallaxis_result.setText("");
                                }
                                else{
                                    //行正确的了 版本也正确了 查看文件是否存在
                                    line =Integer.parseInt(anaylsis_lines.getText());
                                    if (!FileUtils.killmapFileExist(versionID,bugID)){
                                        metallaxis_state.setText("no file exist");
                                        metallaxis_result.setText("");
                                    }
                                    else{
                                        //TODO如果文件存在 读取文件 并分析 输出

                                        //读取spectra文件
                                        List<String> stringListSpectra= SpectraHandler.readSpectraFileByLines(versionID,bugID);
                                        //spectralist 转换成spectra对象
                                        List<Spectra> spectraList= SpectraHandler.fileInSpectra(stringListSpectra);


                                        List<String> stringListMutants=MutantsHandler.readMutantsFileByLines(versionID,bugID);
                                        List<Mutants> mutantsList=MutantsHandler.fileToMutants(stringListMutants);

                                        List<String> stringListKillmap=KillmapHandler.readKillmapFileByLines(versionID,bugID);
                                        List<Killmap> killmapList=KillmapHandler.fileToKillmap(stringListKillmap);

                                        /**
                                         * 只SMFL 直接对应spectra所有
                                         */
                                        System.out.println("===========");

                                            //统计时间
                                            long startTime=System.currentTimeMillis();
                                            List<Mutants> onlyMetallsuspicious= Metallaxis.calculateMetallaxis(killmapList,mutantsList);
                                            List<Mutants> onlyMutantsAfterSort = LineSort.sortMutants(onlyMetallsuspicious);
                                            List<Spectra> spectraOnlyMetallMutants= SpectraHandler.getSpectraFinall(onlyMutantsAfterSort,spectraList);
                                            List<Spectra> spectraOnlyMetallMutantsFinall=LineSort.sortSpectra(spectraOnlyMetallMutants); //result

                                        //结果要显示多少行
                                        List<Spectra> spectrasFinallSort=SpectraHandler.getLine(spectraOnlyMetallMutantsFinall,line);

                                        long endTime=System.currentTimeMillis();
                                        long runTime=endTime-startTime;
                                        try {
                                            DBController.addMetallRunTime(versionID,bugID,runTime);
                                            DBController.addSpectraByMetallaxis(versionID,bugID,spectrasFinallSort);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }


                                        //piechart显示
                                            bingtu3.setData(getBingtu3(spectrasFinallSort));

                                            //表
                                            zhexian3.setData(getZhexian3(spectrasFinallSort));



                                            StringBuffer msgFinal=new StringBuffer("");
                                            for(Spectra spectra:spectrasFinallSort){
                                                msgFinal.append(spectra.getFilename()+"#"+spectra.getLine()+"\n");
                                            }
                                            metallaxis_state.setText("success");
                                            metallaxis_result.setText(msgFinal.toString());

                                    }
                                }
                            }
                            else {
                                metallaxis_state.setText("没有这个版本哦");
                                metallaxis_result.setText("");
                            }
                        }
                    }
                    else {
                        metallaxis_state.setText("别乱写= =");
                        metallaxis_result.setText("");
                    }
                }

            }
        });





    }

    private ObservableList<PieChart.Data> getBingtu() {
        return FXCollections.observableArrayList(new PieChart.Data("a", 13),
                new PieChart.Data("b", 25), new PieChart.Data("c", 10),
                new PieChart.Data("d", 22), new PieChart.Data("e", 30));
    }

    private ObservableList<XYChart.Series<String, Double>> getZhexian() {
        ObservableList<XYChart.Series<String, Double>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<String, Double> series1 = new LineChart.Series<String, Double>();
        series1.setName("line");

        for (int i = 0; i < 10; i++) {

            series1.getData().add(new XYChart.Data<String, Double>("Data" + i, i*1.0));
        }
        lineChartData.add(series1);
        return lineChartData;
    }



    //结果展示饼图

    private ObservableList<PieChart.Data> getBingtu(List<Spectra> spectraID) {
        ObservableList<PieChart.Data> pieDataObsList=FXCollections.observableArrayList();
        for (int i = 0; i < spectraID.size(); i++) {
            String name=spectraID.get(i).getLine()+"";
            double value=spectraID.get(i).getSuspicious();
            pieDataObsList.add(new PieChart.Data(name,value));

        }
        return pieDataObsList;
    }



    private ObservableList<PieChart.Data> getBingtu2(List<Spectra> spectraID) {
        ObservableList<PieChart.Data> pieDataObsList=FXCollections.observableArrayList();
        for (int i = 0; i < spectraID.size(); i++) {
            String name=spectraID.get(i).getLine()+"";
            double value=spectraID.get(i).getSuspicious();
            pieDataObsList.add(new PieChart.Data(name,value));

        }
        return pieDataObsList;
    }
    private ObservableList<PieChart.Data> getBingtu3(List<Spectra> spectraID) {
        ObservableList<PieChart.Data> pieDataObsList=FXCollections.observableArrayList();
        for (int i = 0; i < spectraID.size(); i++) {
            String name=spectraID.get(i).getLine()+"";
            double value=spectraID.get(i).getSuspicious();
            pieDataObsList.add(new PieChart.Data(name,value));

        }
        return pieDataObsList;
    }


    private ObservableList<XYChart.Series<String, Double>> getZhexian(List<Spectra> spectraID) {
        ObservableList<XYChart.Series<String, Double>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<String, Double> series1 = new LineChart.Series<String, Double>();
        series1.setName("line");
        for (int i = 0; i < spectraID.size(); i++) {
            String line = String.valueOf(spectraID.get(i).getLine());
            Double susp=spectraID.get(i).getSuspicious();

            series1.getData().add(new XYChart.Data<String, Double>(line,susp));
        }
        lineChartData.add(series1);
        return lineChartData;
    }


    private ObservableList<XYChart.Series<String, Double>> getZhexian2(List<Spectra> spectraID) {
        ObservableList<XYChart.Series<String, Double>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<String, Double> series1 = new LineChart.Series<String, Double>();
        series1.setName("line");

        for (int i = 0; i < spectraID.size(); i++) {
            String line = String.valueOf(spectraID.get(i).getLine());
            Double susp=spectraID.get(i).getSuspicious();

            series1.getData().add(new XYChart.Data<String, Double>(line,susp));
        }
        lineChartData.add(series1);
        return lineChartData;
    }


    private ObservableList<XYChart.Series<String, Double>> getZhexian3(List<Spectra> spectraID) {
        ObservableList<XYChart.Series<String, Double>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<String, Double> series1 = new LineChart.Series<String, Double>();
        series1.setName("line");

        for (int i = 0; i < spectraID.size(); i++) {
            String line = String.valueOf(spectraID.get(i).getLine());
            Double susp=spectraID.get(i).getSuspicious();

            series1.getData().add(new XYChart.Data<String, Double>(line,susp));
        }
        lineChartData.add(series1);
        return lineChartData;
    }

}
