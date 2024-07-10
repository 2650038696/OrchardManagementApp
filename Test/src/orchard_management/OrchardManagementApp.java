package orchard_management;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.List;



// 数据库管理类
class DatabaseManager {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/orchard_management";
        String user = "UserName";                       //更改为自己数据库的用户名
        String password = "Password";                //更改为自己数据库的密码
        return DriverManager.getConnection(url, user, password);
    }
}


public class OrchardManagementApp {
    public static void main(String[] args) {
        new MainMenu();
    }
}

//主菜单界面

class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("果园管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton plotManagementButton = new JButton("地块管理");
        JButton treeManagementButton = new JButton("果树品种管理");
        JButton recordManagementButton = new JButton("灌溉施肥记录管理");
        JButton exitButton = new JButton("退出系统");

        Font chineseFont = new Font("Microsoft YaHei", Font.PLAIN, 32);
        plotManagementButton.setFont(chineseFont);
        treeManagementButton.setFont(chineseFont);
        recordManagementButton.setFont(chineseFont);
        exitButton.setFont(chineseFont);

        // 设置按钮背景色和前景色
        plotManagementButton.setBackground(Color.WHITE);
        plotManagementButton.setForeground(Color.BLUE);
        treeManagementButton.setBackground(Color.WHITE);
        treeManagementButton.setForeground(Color.BLUE);
        recordManagementButton.setBackground(Color.WHITE);
        recordManagementButton.setForeground(Color.BLUE);
        exitButton.setBackground(new Color(43, 184, 52));
        exitButton.setForeground(Color.WHITE);

        // 设置按钮边框样式
        plotManagementButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        treeManagementButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        recordManagementButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        exitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        plotManagementButton.addActionListener(e -> new PlotManagement());
        treeManagementButton.addActionListener(e -> new TreeManagement());
        recordManagementButton.addActionListener(e -> new RecordManagementGUI());
        exitButton.addActionListener(e -> System.exit(0));

        setLayout(new GridLayout(4, 1));
        add(plotManagementButton);
        add(treeManagementButton);
        add(recordManagementButton);
        add(exitButton);

        setVisible(true);
    }

}


//地块管理界面
class PlotManagement extends JFrame {
    private JTextArea resultArea;

    // 地块管理界面
    public PlotManagement() {
        setTitle("地块管理");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JButton addButton = new JButton("添加地块");
        JButton deleteButton = new JButton("删除地块");
        JButton viewButton = new JButton("查看全部地块");
        JButton searchButton = new JButton("精准查找地块");
        JButton updateButton = new JButton("更新地块信息");

        // 设置按钮背景色和前景色
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(Color.BLUE);
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(Color.BLUE);
        viewButton.setBackground(Color.WHITE);
        viewButton.setForeground(Color.BLUE);
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLUE);
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(Color.BLUE);

        // 设置按钮字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 24);
        addButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        viewButton.setFont(buttonFont);
        searchButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);

        addButton.addActionListener(e -> openAddPlotDialog());
        deleteButton.addActionListener(e -> deletePlot());
        viewButton.addActionListener(e -> viewPlots());
        searchButton.addActionListener(e -> searchPlots());
        updateButton.addActionListener(e -> updatePlot());

        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(updateButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.WEST);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        setVisible(true);
    }

    //添加地块
    private void openAddPlotDialog() {
        JDialog addDialog = new JDialog(this, "添加地块", true);
        addDialog.setSize(400, 300);
        addDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        JTextField idField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField areaField = new JTextField();
        JTextField sunlightDurationField = new JTextField();
        JTextField soilFeaturesField = new JTextField();

        // 设置标签文字居中对齐
        JLabel idLabel = new JLabel("地块ID：", SwingConstants.CENTER);
        JLabel locationLabel = new JLabel("地块位置：", SwingConstants.CENTER);
        JLabel areaLabel = new JLabel("地块面积：", SwingConstants.CENTER);
        JLabel sunlightDurationLabel = new JLabel("日照时长：", SwingConstants.CENTER);
        JLabel soilFeaturesLabel = new JLabel("土壤特征：", SwingConstants.CENTER);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(areaLabel);
        panel.add(areaField);
        panel.add(sunlightDurationLabel);
        panel.add(sunlightDurationField);
        panel.add(soilFeaturesLabel);
        panel.add(soilFeaturesField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("添加");
        JButton cancelButton = new JButton("取消");

        // 设置按钮背景色和前景色
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(Color.BLUE);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLUE);

        // 设置按钮字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 16);
        addButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);

        addButton.addActionListener(e -> {
            String id = idField.getText();
            String location = locationField.getText();
            String areaStr = areaField.getText();
            String sunlightDurationStr = sunlightDurationField.getText();
            String soilFeatures = soilFeaturesField.getText();

            Float area = areaStr.isEmpty() ? null : Float.valueOf(areaStr);
            Float sunlightDuration = sunlightDurationStr.isEmpty() ? null : Float.valueOf(sunlightDurationStr);

            if (area != null && area <= 0 || sunlightDuration != null && sunlightDuration < 0) {
                resultArea.append("输入的数据不符合常识，地块面积和日照时长必须为正值\n");
                return;
            }

            String sql = "INSERT INTO plots (id, location, area, sunlight_duration, soil_features) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, id);
                pstmt.setString(2, location.isEmpty() ? null : location);
                if (area == null) {
                    pstmt.setNull(3, Types.FLOAT);
                } else {
                    pstmt.setFloat(3, area);
                }
                if (sunlightDuration == null) {
                    pstmt.setNull(4, Types.FLOAT);
                } else {
                    pstmt.setFloat(4, sunlightDuration);
                }
                pstmt.setString(5, soilFeatures.isEmpty() ? null : soilFeatures);
                pstmt.executeUpdate();

                resultArea.append("地块添加成功\n");

            } catch (SQLException ex) {
                resultArea.append("数据库操作失败：" + ex.getMessage() + "\n");
            }

            addDialog.dispose();
        });

        cancelButton.addActionListener(e -> addDialog.dispose());

        panel.add(addButton);
        panel.add(cancelButton);

        addDialog.add(panel);
        addDialog.setVisible(true);
    }


    //删除地块
    private void deletePlot() {
        String id = JOptionPane.showInputDialog("请输入要删除的地块ID：");

        String sql = "DELETE FROM plots WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                resultArea.append("地块ID不存在\n");
            } else {
                resultArea.append("地块删除成功\n");
            }

        } catch (SQLException e) {
            resultArea.append("数据库操作失败：" + e.getMessage() + "\n");
        }
    }

    //查询全部地块
    private void viewPlots() {
        String sql = "SELECT * FROM plots";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                resultArea.append("地块数据为空\n");
                return;
            }

            resultArea.append("地块信息：\n");
            while (rs.next()) {
                String id = rs.getString("id");
                String location = rs.getString("location");
                Float area = rs.getFloat("area");
                Float sunlightDuration = rs.getFloat("sunlight_duration");
                String soilFeatures = rs.getString("soil_features");
                resultArea.append("ID: " + id + ", 位置: " + location + ", 面积: " + area +
                        ", 日照时长: " + sunlightDuration + ", 土壤特征: " + soilFeatures + "\n");
            }

        } catch (SQLException e) {
            resultArea.append("数据库操作失败：" + e.getMessage() + "\n");
        }
    }

    //精准查询地块
    private void searchPlots() {
        String[] options = {"按ID", "按位置", "按面积大小"};
        int choice = JOptionPane.showOptionDialog(this, "请选择查找方式：",
                "查找地块", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        String sql = "";
        switch (choice) {
            case 0:
                String id = JOptionPane.showInputDialog("请输入地块ID：");
                sql = "SELECT * FROM plots WHERE id = ?";
                executePlotQuery(sql, id);
                break;
            case 1:
                String location = JOptionPane.showInputDialog("请输入地块位置：");
                sql = "SELECT * FROM plots WHERE location LIKE ?";
                executePlotQuery(sql, "%" + location + "%");
                break;
            case 2:
                String minAreaStr = JOptionPane.showInputDialog("请输入地块面积下限：");
                String maxAreaStr = JOptionPane.showInputDialog("请输入地块面积上限：");
                float minArea = Float.parseFloat(minAreaStr);
                float maxArea = Float.parseFloat(maxAreaStr);
                sql = "SELECT * FROM plots WHERE area BETWEEN ? AND ?";
                executePlotQuery(sql, minArea, maxArea);
                break;
            default:
                resultArea.append("无效的选择，请重新选择\n");
        }
    }
    private void executePlotQuery(String sql, Object... params) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    resultArea.append("没有找到符合条件的地块。\n");
                    return;
                }
                resultArea.append("查找到的地块信息：\n");
                while (rs.next()) {
                    String id = rs.getString("id");
                    String location = rs.getString("location");
                    Float area = rs.getFloat("area");
                    Float sunlightDuration = rs.getFloat("sunlight_duration");
                    String soilFeatures = rs.getString("soil_features");
                    resultArea.append("ID: " + id + ", 位置: " + location + ", 面积: " + area +
                            ", 日照时长: " + sunlightDuration + ", 土壤特征: " + soilFeatures + "\n");
                }
            }
        } catch (SQLException e) {
            resultArea.append("数据库操作失败：" + e.getMessage() + "\n");
        }
    }

    //更新地块
    private void updatePlot() {
        String id = JOptionPane.showInputDialog("请输入要更新的地块ID：");
        if (!isPlotIdExists(id)) {
            resultArea.append("地块ID不存在\n");
            return;
        }

        String location = JOptionPane.showInputDialog("请输入新的地块位置（或按Enter键跳过）：");
        String areaStr = JOptionPane.showInputDialog("请输入新的地块面积（或按Enter键跳过）：");
        String sunlightDurationStr = JOptionPane.showInputDialog("请输入新的日照时长（或按Enter键跳过）：");
        String soilFeatures = JOptionPane.showInputDialog("请输入新的土壤特征（或按Enter键跳过）：");

        Float area = areaStr.isEmpty() ? null : Float.valueOf(areaStr);
        Float sunlightDuration = sunlightDurationStr.isEmpty() ? null : Float.valueOf(sunlightDurationStr);

        StringBuilder sqlBuilder = new StringBuilder("UPDATE plots SET ");
        boolean needsComma = false;
        if (!location.isEmpty()) {
            sqlBuilder.append("location = ?");
            needsComma = true;
        }
        if (area != null) {
            if (needsComma) sqlBuilder.append(", ");
            sqlBuilder.append("area = ?");
            needsComma = true;
        }
        if (sunlightDuration != null) {
            if (needsComma) sqlBuilder.append(", ");
            sqlBuilder.append("sunlight_duration = ?");
            needsComma = true;
        }
        if (!soilFeatures.isEmpty()) {
            if (needsComma) sqlBuilder.append(", ");
            sqlBuilder.append("soil_features = ?");
        }
        sqlBuilder.append(" WHERE id = ?");

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {

            int paramIndex = 1;
            if (!location.isEmpty()) {
                pstmt.setString(paramIndex++, location);
            }
            if (area != null) {
                pstmt.setFloat(paramIndex++, area);
            }
            if (sunlightDuration != null) {
                pstmt.setFloat(paramIndex++, sunlightDuration);
            }
            if (!soilFeatures.isEmpty()) {
                pstmt.setString(paramIndex++, soilFeatures);
            }
            pstmt.setString(paramIndex, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                resultArea.append("地块ID不存在\n");
            } else {
                resultArea.append("地块更新成功\n");
            }

        } catch (SQLException e) {
            resultArea.append("数据库操作失败：" + e.getMessage() + "\n");
        }
    }
    private boolean isPlotIdExists(String id) {
        String sql = "SELECT 1 FROM plots WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            resultArea.append("数据库操作失败：" + e.getMessage() + "\n");
        }
        return false;
    }

}

// 果树管理界面
class TreeManagement extends JFrame {
    private JTextArea resultArea;

    //果树管理界面
    public TreeManagement() {
        setTitle("果树品种管理");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton addButton = new JButton("添加果树品种");
        JButton deleteButton = new JButton("删除果树品种");
        JButton viewButton = new JButton("查看全部果树品种");
        JButton searchButton = new JButton("精准查找果树品种");
        JButton updateButton = new JButton("更新果树品种信息");

        // 设置按钮背景色和前景色
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(Color.BLUE);
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(Color.BLUE);
        viewButton.setBackground(Color.WHITE);
        viewButton.setForeground(Color.BLUE);
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLUE);
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(Color.BLUE);

        // 设置按钮字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 24);
        addButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        viewButton.setFont(buttonFont);
        searchButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);

        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(updateButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(panel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> addTreeVariety());
        deleteButton.addActionListener(e -> deleteTreeVariety());
        viewButton.addActionListener(e -> viewTreeVarieties());
        searchButton.addActionListener(e -> searchTreeVarieties());
        updateButton.addActionListener(e -> updateTreeVariety());

        setVisible(true);
    }


    //添加果树
    private void addTreeVariety() {
        JDialog dialog = new JDialog(this, "添加果树品种", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField plantingTimeField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField plotIdField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField areaField = new JTextField();
        JTextField yieldPerYearField = new JTextField();
        JTextField qualityRatioField = new JTextField();

        // 创建居中对齐的 JLabel 并添加到面板中
        JLabel idLabel = new JLabel("果树品种ID：");
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(idLabel);
        panel.add(idField);

        JLabel nameLabel = new JLabel("果树品种名称：");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel plantingTimeLabel = new JLabel("种植时间（YYYY-MM-DD）：");
        plantingTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(plantingTimeLabel);
        panel.add(plantingTimeField);

        JLabel ageLabel = new JLabel("果树年龄：");
        ageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(ageLabel);
        panel.add(ageField);

        JLabel plotIdLabel = new JLabel("地块ID：");
        plotIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(plotIdLabel);
        panel.add(plotIdField);

        JLabel quantityLabel = new JLabel("果树数量：");
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(quantityLabel);
        panel.add(quantityField);

        JLabel areaLabel = new JLabel("占地面积：");
        areaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(areaLabel);
        panel.add(areaField);

        JLabel yieldPerYearLabel = new JLabel("年产量：");
        yieldPerYearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(yieldPerYearLabel);
        panel.add(yieldPerYearField);

        JLabel qualityRatioLabel = new JLabel("品质比例：");
        qualityRatioLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(qualityRatioLabel);
        panel.add(qualityRatioField);


        JButton saveButton = new JButton("保存");

        // 设置按钮背景色和前景色
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(Color.BLUE);

        panel.add(saveButton);

        dialog.add(panel);

        // 设置按钮字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 16);
        saveButton.setFont(buttonFont);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        panel.add(buttonPanel);

        saveButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String plantingTime = plantingTimeField.getText().trim().isEmpty() ? null : plantingTimeField.getText().trim();
            Integer age = ageField.getText().trim().isEmpty() ? null : Integer.valueOf(ageField.getText().trim());
            String plotId = plotIdField.getText().trim();
            Integer quantity = quantityField.getText().trim().isEmpty() ? null : Integer.valueOf(quantityField.getText().trim());
            Float area = areaField.getText().trim().isEmpty() ? null : Float.valueOf(areaField.getText().trim());
            Float yieldPerYear = yieldPerYearField.getText().trim().isEmpty() ? null : Float.valueOf(yieldPerYearField.getText().trim());
            Float qualityRatio = qualityRatioField.getText().trim().isEmpty() ? null : Float.valueOf(qualityRatioField.getText().trim());

            if (age != null && age < 0 || quantity != null && quantity <= 0 || area != null && area <= 0
                    || yieldPerYear != null && yieldPerYear < 0 || qualityRatio != null && (qualityRatio < 0 || qualityRatio > 1)) {
                resultArea.append("输入的数据不符合常识\n");
                return;
            }

            if (area != null) {
                float remainingArea = getRemainingArea(plotId);
                if (remainingArea < area) {
                    resultArea.append("地块剩余面积不足，剩余面积为：" + remainingArea + "\n");
                    dialog.dispose();
                    return;
                }
            }

            String sql = "INSERT INTO tree_varieties (id, name, planting_time, age, plot_id, quantity, area, yield_per_year, quality_ratio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, id);
                pstmt.setString(2, name);
                if (plantingTime == null) {
                    pstmt.setNull(3, Types.DATE);
                } else {
                    pstmt.setDate(3, Date.valueOf(plantingTime));
                }
                if (age == null) {
                    pstmt.setNull(4, Types.INTEGER);
                } else {
                    pstmt.setInt(4, age);
                }
                pstmt.setString(5, plotId);
                if (quantity == null) {
                    pstmt.setNull(6, Types.INTEGER);
                } else {
                    pstmt.setInt(6, quantity);
                }
                if (area == null) {
                    pstmt.setNull(7, Types.FLOAT);
                } else {
                    pstmt.setFloat(7, area);
                }
                if (yieldPerYear == null) {
                    pstmt.setNull(8, Types.FLOAT);
                } else {
                    pstmt.setFloat(8, yieldPerYear);
                }
                if (qualityRatio == null) {
                    pstmt.setNull(9, Types.FLOAT);
                } else {
                    pstmt.setFloat(9, qualityRatio);
                }
                pstmt.executeUpdate();

                resultArea.append("果树品种添加成功\n");
                dialog.dispose();

            } catch (SQLException ex) {
                resultArea.append("数据库操作失败：" + ex.getMessage() + "\n");
            }
        });

        dialog.setVisible(true);
    }

    //判断地块剩余面积即其他问题
    private static float getRemainingArea(String plotId) {
        String sql = "SELECT p.area - IFNULL(SUM(tv.area), 0) AS remaining_area " +
                "FROM plots p LEFT JOIN tree_varieties tv ON p.id = tv.plot_id " +
                "WHERE p.id = ? " +
                "GROUP BY p.id";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plotId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    float remainingArea = rs.getFloat("remaining_area");
                    if (remainingArea < 0) {
                        System.out.println("地块ID不存在或者其他错误");
                        return -999; // 返回一个负数，表示错误或异常情况
                    }
                    return remainingArea;
                } else {
                    System.out.println("地块ID不存在");
                    return -999; // 返回一个负数，表示错误或异常情况
                }
            }

        } catch (SQLException e) {
            System.out.println("数据库操作失败：" + e.getMessage());
            return -999; // 返回一个负数，表示错误或异常情况
        }
    }

    //删除果树
    private void deleteTreeVariety() {
        String id = JOptionPane.showInputDialog(this, "请输入要删除的果树品种ID：");
        if (id == null || id.trim().isEmpty()) {
            resultArea.append("果树品种ID不能为空\n");
            return;
        }

        String sql = "DELETE FROM tree_varieties WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                resultArea.append("果树品种ID不存在\n");
            } else {
                resultArea.append("果树品种删除成功\n");
            }

        } catch (SQLException e) {
            resultArea.append("数据库操作失败：" + e.getMessage() + "\n");
        }
    }

    //查询全部果树
    private void viewTreeVarieties() {
        String sql = "SELECT * FROM tree_varieties";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                resultArea.append("没有果树品种信息\n");
                return;
            }

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                Date plantingTime = rs.getDate("planting_time");
                Integer age = rs.getInt("age");
                String plotId = rs.getString("plot_id");
                Integer quantity = rs.getInt("quantity");
                Float area = rs.getFloat("area");
                Float yieldPerYear = rs.getFloat("yield_per_year");
                Float qualityRatio = rs.getFloat("quality_ratio");
                resultArea.append("ID: " + id + ", 品种名称: " + name + ", 种植时间: " + plantingTime +
                        ", 年龄: " + age + ", 地块ID: " + plotId + ", 数量: " + quantity +
                        ", 占地面积: " + area + ", 年产量: " + yieldPerYear + ", 品质比例: " + qualityRatio + "\n");
            }

        } catch (SQLException e) {
            resultArea.append("数据库操作失败：" + e.getMessage() + "\n");
        }
    }

    //精准查询果树
    private void searchTreeVarieties() {
        String[] options = {"通过ID查找", "通过名称查找", "通过地块ID查找"};
        int choice = JOptionPane.showOptionDialog(this, "选择查找方式：", "查找果树品种",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        String sql = "";
        Object param = null;

        switch (choice) {
            case 0:
                String id = JOptionPane.showInputDialog(this, "请输入果树品种ID：");
                if (id == null || id.trim().isEmpty()) {
                    resultArea.append("果树品种ID不能为空\n");
                    return;
                }
                sql = "SELECT * FROM tree_varieties WHERE id = ?";
                param = id;
                break;
            case 1:
                String name = JOptionPane.showInputDialog(this, "请输入果树品种名称：");
                if (name == null || name.trim().isEmpty()) {
                    resultArea.append("果树品种名称不能为空\n");
                    return;
                }
                sql = "SELECT * FROM tree_varieties WHERE name LIKE ?";
                param = "%" + name + "%";
                break;
            case 2:
                String plotId = JOptionPane.showInputDialog(this, "请输入地块ID：");
                if (plotId == null || plotId.trim().isEmpty()) {
                    resultArea.append("地块ID不能为空\n");
                    return;
                }
                sql = "SELECT * FROM tree_varieties WHERE plot_id = ?";
                param = plotId;
                break;
        }

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (param instanceof String) {
                pstmt.setString(1, (String) param);
            }

            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                resultArea.append("没有找到相关果树品种\n");
                return;
            }

            while (rs.next()) {
                String treeId = rs.getString("id");
                String treeName = rs.getString("name");
                Date plantingTime = rs.getDate("planting_time");
                Integer age = rs.getInt("age");
                String treePlotId = rs.getString("plot_id");
                Integer quantity = rs.getInt("quantity");
                Float area = rs.getFloat("area");
                Float yieldPerYear = rs.getFloat("yield_per_year");
                Float qualityRatio = rs.getFloat("quality_ratio");
                resultArea.append("ID: " + treeId + ", 品种名称: " + treeName + ", 种植时间: " + plantingTime +
                        ", 年龄: " + age + ", 地块ID: " + treePlotId + ", 数量: " + quantity +
                        ", 占地面积: " + area + ", 年产量: " + yieldPerYear + ", 品质比例: " + qualityRatio + "\n");
            }

        } catch (SQLException e) {
            resultArea.append("数据库操作失败：" + e.getMessage() + "\n");
        }
    }

    //更新果树
    private void updateTreeVariety() {
        String id = JOptionPane.showInputDialog("请输入要更新的果树品种ID：");
        if (!isTreeVarietyIdExists(id)) {
            resultArea.append("果树品种ID不存在\n");
            return;
        }

        String name = JOptionPane.showInputDialog("请输入新的果树品种名称（或按Enter键跳过）：");
        String plantingTimeStr = JOptionPane.showInputDialog("请输入新的种植时间（YYYY-MM-DD）（或按Enter键跳过）：");
        String ageStr = JOptionPane.showInputDialog("请输入新的果树年龄（或按Enter键跳过）：");
        String plotId = JOptionPane.showInputDialog("请输入新的地块ID（或按Enter键跳过）：");
        String quantityStr = JOptionPane.showInputDialog("请输入新的果树数量（或按Enter键跳过）：");
        String areaStr = JOptionPane.showInputDialog("请输入新的占地面积（或按Enter键跳过）：");
        String yieldPerYearStr = JOptionPane.showInputDialog("请输入新的年产量（或按Enter键跳过）：");
        String qualityRatioStr = JOptionPane.showInputDialog("请输入新的品质比例（0-1）（或按Enter键跳过）：");

        Integer age = ageStr.isEmpty() ? null : Integer.valueOf(ageStr);
        Integer quantity = quantityStr.isEmpty() ? null : Integer.valueOf(quantityStr);
        Float area = areaStr.isEmpty() ? null : Float.valueOf(areaStr);
        Float yieldPerYear = yieldPerYearStr.isEmpty() ? null : Float.valueOf(yieldPerYearStr);
        Float qualityRatio = qualityRatioStr.isEmpty() ? null : Float.valueOf(qualityRatioStr);

        if ((age != null && age < 0) || (quantity != null && quantity <= 0) || (area != null && area <= 0) ||
                (yieldPerYear != null && yieldPerYear < 0) || (qualityRatio != null && (qualityRatio < 0 || qualityRatio > 1))) {
            resultArea.append("输入的数据不符合常识\n");
            return;
        }

        if (area != null) {
            float remainingArea = getRemainingArea(plotId, id);
            if (remainingArea < area) {
                resultArea.append("地块剩余面积不足，剩余面积为：" + remainingArea + "\n");
                return;
            }
        }

        StringBuilder sqlBuilder = new StringBuilder("UPDATE tree_varieties SET ");
        List<Object> parameters = new ArrayList<>();

        if (!name.isEmpty()) {
            sqlBuilder.append("name = ?, ");
            parameters.add(name);
        }
        if (!plantingTimeStr.isEmpty()) {
            sqlBuilder.append("planting_time = ?, ");
            try {
                parameters.add(Date.valueOf(plantingTimeStr));
            } catch (IllegalArgumentException ex) {
                resultArea.append("种植时间格式不正确，应为YYYY-MM-DD\n");
                return;
            }
        }
        if (age != null) {
            sqlBuilder.append("age = ?, ");
            parameters.add(age);
        }
        if (!plotId.isEmpty()) {
            sqlBuilder.append("plot_id = ?, ");
            parameters.add(plotId);
        }
        if (quantity != null) {
            sqlBuilder.append("quantity = ?, ");
            parameters.add(quantity);
        }
        if (area != null) {
            sqlBuilder.append("area = ?, ");
            parameters.add(area);
        }
        if (yieldPerYear != null) {
            sqlBuilder.append("yield_per_year = ?, ");
            parameters.add(yieldPerYear);
        }
        if (qualityRatio != null) {
            sqlBuilder.append("quality_ratio = ?, ");
            parameters.add(qualityRatio);
        }

        if (parameters.isEmpty()) {
            resultArea.append("没有更新任何数据\n");
            return;
        }

        sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());
        sqlBuilder.append(" WHERE id = ?");

        parameters.add(id);

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                resultArea.append("果树品种ID不存在\n");
            } else {
                resultArea.append("果树品种更新成功\n");
            }

        } catch (SQLException e) {
            resultArea.append("数据库操作失败：" + e.getMessage() + "\n");
        }
    }
    private boolean isTreeVarietyIdExists(String id) {
        String sql = "SELECT 1 FROM tree_varieties WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            resultArea.append("数据库操作失败：" + e.getMessage() + "\n");
        }
        return false;
    }
    //判断地块剩余面积即其他问题
    private float getRemainingArea(String plotId, String treeVarietyId) {
        String sql = "SELECT p.area - IFNULL(SUM(tv.area), 0) AS remaining_area " +
                "FROM plots p " +
                "LEFT JOIN tree_varieties tv ON p.id = tv.plot_id AND tv.id <> ? " +
                "WHERE p.id = ? " +
                "GROUP BY p.id";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, treeVarietyId);
            pstmt.setString(2, plotId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("remaining_area");
                } else {
                    System.out.println("地块ID不存在");
                    return -999; // 返回一个负数，表示错误或异常情况
                }
            }

        } catch (SQLException e) {
            System.out.println("数据库操作失败：" + e.getMessage());
            return -999; // 返回一个负数，表示错误或异常情况
        }
    }

}

// 记录管理界面
class RecordManagementGUI extends JFrame {
    private JPanel mainPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton viewButton;
    private JButton searchButton;
    private JButton updateButton;
    private JPanel buttonPanel;
    private JPanel contentPanel;

    public RecordManagementGUI() {
        setTitle("灌溉施肥记录管理");
        setSize(800, 600); // 设置更大尺寸
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 界面关闭不影响主菜单界面
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new GridLayout(5, 1));
        contentPanel = new JPanel(new BorderLayout());

        addButton = new JButton("添加记录");
        deleteButton = new JButton("删除记录");
        viewButton = new JButton("查看全部记录");
        searchButton = new JButton("精准查找");
        updateButton = new JButton("更新记录");

// 设置按钮字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 24);
        addButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        viewButton.setFont(buttonFont);
        searchButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);

// 设置按钮背景色和前景色
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(Color.BLUE);
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(Color.BLUE);
        viewButton.setBackground(Color.WHITE);
        viewButton.setForeground(Color.BLUE);
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLUE);
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(Color.BLUE);

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        addButton.addActionListener(e -> showAddRecordPanel());
        deleteButton.addActionListener(e -> showDeleteRecordPanel());
        viewButton.addActionListener(e -> showViewRecordsPanel());
        searchButton.addActionListener(e -> showSearchRecordPanel());
        updateButton.addActionListener(e -> showUpdateRecordPanel());

        setVisible(true);

    }

    private void showAddRecordPanel() {
        contentPanel.removeAll();
        JPanel addPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        addPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 设置字体
        Font labelFont = new Font("微软雅黑", Font.PLAIN, 16);

        JLabel typeLabel = new JLabel("记录类型：");
        typeLabel.setFont(labelFont);
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"灌溉", "施肥"});
        typeComboBox.setFont(labelFont);

        JLabel idLabel = new JLabel("记录ID：");
        idLabel.setFont(labelFont);
        JTextField idField = new JTextField();
        idField.setFont(labelFont);

        JLabel plotIdLabel = new JLabel("地块ID：");
        plotIdLabel.setFont(labelFont);
        JTextField plotIdField = new JTextField();
        plotIdField.setFont(labelFont);

        JLabel dateLabel = new JLabel("日期（YYYY-MM-DD）：");
        dateLabel.setFont(labelFont);
        JTextField dateField = new JTextField();
        dateField.setFont(labelFont);

        JLabel detailsLabel = new JLabel("详细信息：");
        detailsLabel.setFont(labelFont);
        JTextField detailsField = new JTextField();
        detailsField.setFont(labelFont);

        JButton saveButton = new JButton("保存");
        // 设置按钮字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 16);
        saveButton.setFont(buttonFont);
        // 设置按钮背景色和前景色
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(Color.BLUE);

        addPanel.add(typeLabel);
        addPanel.add(typeComboBox);
        addPanel.add(idLabel);
        addPanel.add(idField);
        addPanel.add(plotIdLabel);
        addPanel.add(plotIdField);
        addPanel.add(dateLabel);
        addPanel.add(dateField);
        addPanel.add(detailsLabel);
        addPanel.add(detailsField);
        addPanel.add(new JLabel());
        addPanel.add(saveButton);

        saveButton.addActionListener(e -> {
            int type = typeComboBox.getSelectedIndex() + 1;
            String id = idField.getText();
            String plotId = plotIdField.getText();
            String dateStr = dateField.getText();
            Date date = dateStr.isEmpty() ? null : Date.valueOf(dateStr);
            String details = detailsField.getText();

            String sql = type == 1 ? "INSERT INTO irrigation_records (id, plot_id, date, details) VALUES (?, ?, ?, ?)"
                    : "INSERT INTO fertilization_records (id, plot_id, date, details) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, id);
                pstmt.setString(2, plotId);
                if (date == null) {
                    pstmt.setNull(3, Types.DATE);
                } else {
                    pstmt.setDate(3, date);
                }
                pstmt.setString(4, details.isEmpty() ? null : details);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, type == 1 ? "灌溉记录添加成功" : "施肥记录添加成功");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "数据库操作失败：" + ex.getMessage());
            }
        });

        contentPanel.add(addPanel, BorderLayout.NORTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private void showDeleteRecordPanel() {
        contentPanel.removeAll();
        JPanel deletePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        deletePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 设置字体
        Font labelFont = new Font("微软雅黑", Font.PLAIN, 16);

        JLabel typeLabel = new JLabel("记录类型：");
        typeLabel.setFont(labelFont);
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"灌溉", "施肥"});
        typeComboBox.setFont(labelFont);

        JLabel idLabel = new JLabel("记录ID：");
        idLabel.setFont(labelFont);
        JTextField idField = new JTextField();
        idField.setFont(labelFont);

        JButton deleteButton = new JButton("删除");
        // 设置按钮字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 16);
        deleteButton.setFont(buttonFont);
        // 设置按钮背景色和前景色
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(Color.BLUE);

        deletePanel.add(typeLabel);
        deletePanel.add(typeComboBox);
        deletePanel.add(idLabel);
        deletePanel.add(idField);
        deletePanel.add(new JLabel());
        deletePanel.add(deleteButton);

        deleteButton.addActionListener(e -> {
            int type = typeComboBox.getSelectedIndex() + 1;
            String id = idField.getText();

            String sql = type == 1 ? "DELETE FROM irrigation_records WHERE id = ?"
                    : "DELETE FROM fertilization_records WHERE id = ?";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, id);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows == 0) {
                    JOptionPane.showMessageDialog(this, "记录ID不存在");
                } else {
                    JOptionPane.showMessageDialog(this, type == 1 ? "灌溉记录删除成功" : "施肥记录删除成功");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "数据库操作失败：" + ex.getMessage());
            }
        });

        contentPanel.add(deletePanel, BorderLayout.NORTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private void showViewRecordsPanel() {
        contentPanel.removeAll();
        JPanel viewPanel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"灌溉", "施肥"});
        typeComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JButton viewButton = new JButton("查看全部记录");
        viewButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        viewButton.setBackground(Color.WHITE);
        viewButton.setForeground(Color.BLUE);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("记录类型："));
        topPanel.add(typeComboBox);
        topPanel.add(viewButton);

        viewButton.addActionListener(e -> {
            int type = typeComboBox.getSelectedIndex() + 1;
            String sql = type == 1 ? "SELECT * FROM irrigation_records" : "SELECT * FROM fertilization_records";

            try (Connection conn = DatabaseManager.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                textArea.setText("");
                while (rs.next()) {
                    textArea.append("ID: " + rs.getString("id") + ", ");
                    textArea.append("地块ID: " + rs.getString("plot_id") + ", ");
                    textArea.append("日期: " + rs.getDate("date") + ", ");
                    textArea.append("详细信息: " + rs.getString("details") + "\n");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "数据库操作失败：" + ex.getMessage());
            }
        });

        viewPanel.add(topPanel, BorderLayout.NORTH);
        viewPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(viewPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private void showSearchRecordPanel() {
        contentPanel.removeAll();
        JPanel searchPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel typeLabel = new JLabel("记录类型：");
        typeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"灌溉", "施肥"});
        typeComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JLabel idLabel = new JLabel("记录ID：");
        idLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JTextField idField = new JTextField();
        idField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JButton searchButton = new JButton("搜索");
        searchButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLUE);

        searchPanel.add(typeLabel);
        searchPanel.add(typeComboBox);
        searchPanel.add(idLabel);
        searchPanel.add(idField);
        searchPanel.add(new JLabel());
        searchPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            int type = typeComboBox.getSelectedIndex() + 1;
            String id = idField.getText();

            String sql = type == 1 ? "SELECT * FROM irrigation_records WHERE id = ?"
                    : "SELECT * FROM fertilization_records WHERE id = ?";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "ID: " + rs.getString("id") + "\n" +
                            "地块ID: " + rs.getString("plot_id") + "\n" +
                            "日期: " + rs.getDate("date") + "\n" +
                            "详细信息: " + rs.getString("details"));
                } else {
                    JOptionPane.showMessageDialog(this, "记录ID不存在");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "数据库操作失败：" + ex.getMessage());
            }
        });

        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private void showUpdateRecordPanel() {
        contentPanel.removeAll();
        JPanel updatePanel = new JPanel(new GridLayout(6, 2, 10, 10));
        updatePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel typeLabel = new JLabel("记录类型：");
        typeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"灌溉", "施肥"});
        typeComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JLabel idLabel = new JLabel("记录ID：");
        idLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JTextField idField = new JTextField();
        idField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JLabel plotIdLabel = new JLabel("地块ID：");
        plotIdLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JTextField plotIdField = new JTextField();
        plotIdField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JLabel dateLabel = new JLabel("日期（YYYY-MM-DD）：");
        dateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JTextField dateField = new JTextField();
        dateField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JLabel detailsLabel = new JLabel("详细信息：");
        detailsLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JTextField detailsField = new JTextField();
        detailsField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JButton updateButton = new JButton("更新");
        updateButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(Color.BLUE);

        updatePanel.add(typeLabel);
        updatePanel.add(typeComboBox);
        updatePanel.add(idLabel);
        updatePanel.add(idField);
        updatePanel.add(plotIdLabel);
        updatePanel.add(plotIdField);
        updatePanel.add(dateLabel);
        updatePanel.add(dateField);
        updatePanel.add(detailsLabel);
        updatePanel.add(detailsField);
        updatePanel.add(new JLabel());
        updatePanel.add(updateButton);

        updateButton.addActionListener(e -> {
            int type = typeComboBox.getSelectedIndex() + 1;
            String id = idField.getText();
            String plotId = plotIdField.getText();
            String dateStr = dateField.getText();
            Date date = dateStr.isEmpty() ? null : Date.valueOf(dateStr);
            String details = detailsField.getText();

            String sql = type == 1 ? "UPDATE irrigation_records SET plot_id = ?, date = ?, details = ? WHERE id = ?"
                    : "UPDATE fertilization_records SET plot_id = ?, date = ?, details = ? WHERE id = ?";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, plotId);
                if (date == null) {
                    pstmt.setNull(2, Types.DATE);
                } else {
                    pstmt.setDate(2, date);
                }
                pstmt.setString(3, details.isEmpty() ? null : details);
                pstmt.setString(4, id);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows == 0) {
                    JOptionPane.showMessageDialog(this, "记录ID不存在");
                } else {
                    JOptionPane.showMessageDialog(this, type == 1 ? "灌溉记录更新成功" : "施肥记录更新成功");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "数据库操作失败：" + ex.getMessage());
            }
        });

        contentPanel.add(updatePanel, BorderLayout.NORTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecordManagementGUI());
    }
}

