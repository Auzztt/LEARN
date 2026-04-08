package com.renamer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileRenamerGUI {
    // 界面组件
    private JTextField folderPathField;
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private File currentFolder;

    // 重命名规则相关组件
    private JComboBox<String> ruleComboBox;
    private JTextField param1Field;
    private JTextField param2Field;
    private JTextField sequenceWidthField;
    private JPanel paramPanel;

    // 预览相关组件
    private JList<String> previewList;
    private DefaultListModel<String> previewListModel;
    private List<String> previewNewNames;

    // 历史记录（用于撤销）
    private List<List<String[]>> renameHistory;

    // 按钮
    private JButton previewButton;
    private JButton executeButton;

    // 文件筛选
    private JTextField filterField;

    // 进度条
    private JProgressBar progressBar;

    public FileRenamerGUI() {
        JFrame frame = new JFrame("批量文件重命名工具");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 750);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ========== 上半部分：选择文件夹 ==========
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        folderPathField = new JTextField();
        folderPathField.setEditable(false);

        JButton selectButton = new JButton("选择文件夹");
        selectButton.addActionListener(e -> selectFolder());

        topPanel.add(folderPathField, BorderLayout.CENTER);
        topPanel.add(selectButton, BorderLayout.EAST);

        // ========== 文件筛选区域（支持正则表达式） ==========
        JPanel filterPanel = new JPanel(new BorderLayout(5, 5));
        filterPanel.setBorder(BorderFactory.createTitledBorder("文件筛选（支持正则表达式）"));

        filterField = new JTextField();
        filterField.setToolTipText("输入正则表达式，如：^测试.*  表示以'测试'开头的文件");

        JButton filterButton = new JButton("应用筛选");
        filterButton.addActionListener(e -> refreshFileList());

        JButton clearFilterButton = new JButton("清除筛选");
        clearFilterButton.addActionListener(e -> {
            filterField.setText("");
            refreshFileList();
        });

        JButton regexHelpButton = new JButton("正则帮助");
        regexHelpButton.addActionListener(e -> showRegexHelp());

        JPanel filterButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterButtonPanel.add(filterButton);
        filterButtonPanel.add(clearFilterButton);
        filterButtonPanel.add(regexHelpButton);

        filterPanel.add(filterField, BorderLayout.CENTER);
        filterPanel.add(filterButtonPanel, BorderLayout.EAST);

        // 将选择文件夹和筛选组合在一起
        JPanel northContainer = new JPanel(new BorderLayout(5, 5));
        northContainer.add(topPanel, BorderLayout.NORTH);
        northContainer.add(filterPanel, BorderLayout.CENTER);

        // ========== 规则选择区域 ==========
        JPanel rulePanel = new JPanel(new GridBagLayout());
        rulePanel.setBorder(BorderFactory.createTitledBorder("重命名规则"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        rulePanel.add(new JLabel("选择规则："), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        ruleComboBox = new JComboBox<>(new String[]{
                "查找替换", "添加前缀", "添加后缀", "序号填充", "修改扩展名"
        });
        ruleComboBox.addActionListener(e -> updateParamFields());
        rulePanel.add(ruleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        paramPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rulePanel.add(paramPanel, gbc);

        updateParamFields();

        // ========== 文件列表区域 ==========
        JPanel fileListPanel = new JPanel(new BorderLayout());
        fileListPanel.setBorder(BorderFactory.createTitledBorder("原文件列表（筛选后）"));

        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane1 = new JScrollPane(fileList);
        fileListPanel.add(scrollPane1, BorderLayout.CENTER);

        // ========== 预览区域 ==========
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBorder(BorderFactory.createTitledBorder("预览结果"));

        previewListModel = new DefaultListModel<>();
        previewList = new JList<>(previewListModel);
        JScrollPane scrollPane2 = new JScrollPane(previewList);
        previewPanel.add(scrollPane2, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                fileListPanel, previewPanel);
        splitPane.setResizeWeight(0.5);

        // ========== 中间区域 ==========
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(rulePanel, BorderLayout.NORTH);
        centerPanel.add(splitPane, BorderLayout.CENTER);

        // ========== 按钮区域 ==========
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        previewButton = new JButton("预览重命名");
        previewButton.setEnabled(false);
        previewButton.addActionListener(e -> preview());

        executeButton = new JButton("执行重命名");
        executeButton.setEnabled(false);
        executeButton.addActionListener(e -> execute());

        JButton undoButton = new JButton("撤销上次重命名");
        undoButton.addActionListener(e -> undo());

        bottomPanel.add(previewButton);
        bottomPanel.add(executeButton);
        bottomPanel.add(undoButton);

        // 进度条
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.add(progressBar, BorderLayout.CENTER);

        JPanel southContainer = new JPanel(new BorderLayout(5, 5));
        southContainer.add(bottomPanel, BorderLayout.NORTH);
        southContainer.add(progressPanel, BorderLayout.CENTER);

        // 组装主面板
        mainPanel.add(northContainer, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southContainer, BorderLayout.SOUTH);

        frame.add(mainPanel);

        // 添加窗口关闭监听器，自动保存设置
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveSettings();
            }
        });

        frame.setVisible(true);

        // 加载上次的设置
        loadSettings();
    }

    /**
     * 根据选择的规则，动态显示不同的参数输入框
     */
    private void updateParamFields() {
        paramPanel.removeAll();
        String selectedRule = (String) ruleComboBox.getSelectedItem();

        switch (selectedRule) {
            case "查找替换":
                param1Field = new JTextField(10);
                param1Field.setToolTipText("要查找的内容");
                param2Field = new JTextField(10);
                param2Field.setToolTipText("替换成的内容");
                paramPanel.add(new JLabel("查找："));
                paramPanel.add(param1Field);
                paramPanel.add(new JLabel("替换为："));
                paramPanel.add(param2Field);
                break;

            case "添加前缀":
                param1Field = new JTextField(10);
                param1Field.setToolTipText("要添加的前缀");
                paramPanel.add(new JLabel("前缀："));
                paramPanel.add(param1Field);
                break;

            case "添加后缀":
                param1Field = new JTextField(10);
                param1Field.setToolTipText("要添加的后缀（扩展名前）");
                paramPanel.add(new JLabel("后缀："));
                paramPanel.add(param1Field);
                break;

            case "序号填充":
                sequenceWidthField = new JTextField(5);
                sequenceWidthField.setText("3");
                sequenceWidthField.setToolTipText("序号位数，如3表示001");
                paramPanel.add(new JLabel("序号位数："));
                paramPanel.add(sequenceWidthField);
                break;

            case "修改扩展名":
                param1Field = new JTextField(5);
                param1Field.setToolTipText("新扩展名（如：txt）");
                paramPanel.add(new JLabel("新扩展名："));
                paramPanel.add(param1Field);
                break;
        }

        paramPanel.revalidate();
        paramPanel.repaint();
    }

    /**
     * 选择文件夹
     */
    private void selectFolder() {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setDialogTitle("请选择要处理的文件夹");

        int result = folderChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFolder = folderChooser.getSelectedFile();
            folderPathField.setText(currentFolder.getAbsolutePath());
            refreshFileList();
            previewButton.setEnabled(true);
        }
    }

    /**
     * 刷新文件列表（支持正则表达式筛选）
     */
    private void refreshFileList() {
        listModel.clear();
        previewListModel.clear();

        if (currentFolder == null) return;

        // 获取筛选条件
        String filterText = filterField != null ? filterField.getText().trim() : "";

        File[] files = currentFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isFile()) continue;

                String fileName = file.getName();

                // 应用正则表达式筛选
                boolean shouldShow = true;
                if (!filterText.isEmpty()) {
                    try {
                        // 使用正则表达式匹配整个文件名
                        boolean matches = fileName.matches(filterText);
                        if (!matches) {
                            shouldShow = false;
                        }
                    } catch (Exception e) {
                        // 如果正则表达式无效，显示错误提示
                        System.err.println("正则表达式错误：" + e.getMessage());
                        shouldShow = false;
                    }
                }

                if (shouldShow) {
                    listModel.addElement(fileName);
                }
            }
        }

        int fileCount = listModel.size();
        System.out.println("共加载 " + fileCount + " 个文件");

        // 如果筛选后没有文件，提示用户
        if (fileCount == 0 && !filterText.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "没有找到匹配的文件！\n正则表达式：" + filterText + "\n\n提示：Java正则表达式需要匹配完整文件名",
                    "筛选结果为空",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 预览重命名结果
     */
    private void preview() {
        if (currentFolder == null || listModel.isEmpty()) {
            JOptionPane.showMessageDialog(null, "请先选择文件夹！");
            return;
        }

        previewListModel.clear();
        previewNewNames = new ArrayList<>();

        String selectedRule = (String) ruleComboBox.getSelectedItem();

        // 只对筛选后显示的文件进行预览
        File[] files = currentFolder.listFiles();
        if (files == null) return;

        int sequenceIndex = 1;
        int fileIndex = 0;

        for (File file : files) {
            if (!file.isFile()) continue;

            String fileName = file.getName();

            // 检查该文件是否在筛选列表中
            if (fileIndex < listModel.getSize() && fileName.equals(listModel.getElementAt(fileIndex))) {
                String newName = applyRule(fileName, selectedRule, sequenceIndex);
                previewListModel.addElement(fileName + "  →  " + newName);
                previewNewNames.add(newName);
                sequenceIndex++;
                fileIndex++;
            } else if (fileIndex < listModel.getSize()) {
                // 跳过不在筛选列表中的文件
                continue;
            }
        }

        if (previewNewNames.isEmpty()) {
            JOptionPane.showMessageDialog(null, "没有可预览的文件，请检查筛选条件！");
            return;
        }

        executeButton.setEnabled(true);

        JOptionPane.showMessageDialog(null,
                "预览完成！共 " + previewNewNames.size() + " 个文件\n请确认无误后点击「执行重命名」");
    }

    /**
     * 根据规则应用重命名
     */
    private String applyRule(String originalName, String rule, int sequenceIndex) {
        switch (rule) {
            case "查找替换":
                String search = param1Field != null ? param1Field.getText() : "";
                String replace = param2Field != null ? param2Field.getText() : "";
                return RenameRule.replace(originalName, search, replace);

            case "添加前缀":
                String prefix = param1Field != null ? param1Field.getText() : "";
                return RenameRule.addPrefix(originalName, prefix);

            case "添加后缀":
                String suffix = param1Field != null ? param1Field.getText() : "";
                return RenameRule.addSuffix(originalName, suffix);

            case "序号填充":
                int width = 3;
                try {
                    if (sequenceWidthField != null) {
                        width = Integer.parseInt(sequenceWidthField.getText());
                    }
                } catch (NumberFormatException e) {
                    width = 3;
                }
                return RenameRule.addSequence(originalName, sequenceIndex, width);

            case "修改扩展名":
                String newExt = param1Field != null ? param1Field.getText() : "";
                return RenameRule.changeExtension(originalName, newExt);

            default:
                return originalName;
        }
    }

    /**
     * 执行重命名
     */
    private void execute() {
        if (previewNewNames == null || previewNewNames.isEmpty()) {
            JOptionPane.showMessageDialog(null, "请先点击「预览重命名」！");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "确定要重命名这 " + previewNewNames.size() + " 个文件吗？\n此操作可以撤销！",
                "确认执行",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        File[] files = currentFolder.listFiles();
        if (files == null) return;

        // 显示进度条
        progressBar.setVisible(true);
        progressBar.setValue(0);

        if (renameHistory == null) {
            renameHistory = new ArrayList<>();
        }

        int successCount = 0;
        int fileIndex = 0;
        int totalFiles = previewNewNames.size();
        int processedCount = 0;

        List<String[]> currentHistory = new ArrayList<>();

        for (File file : files) {
            if (!file.isFile()) continue;

            // 只处理筛选后的文件
            if (fileIndex >= listModel.getSize()) break;
            if (!file.getName().equals(listModel.getElementAt(fileIndex))) {
                continue;
            }

            processedCount++;
            int percent = (processedCount * 100) / totalFiles;
            progressBar.setValue(percent);
            progressBar.setString("正在处理: " + processedCount + "/" + totalFiles);

            String newName = previewNewNames.get(fileIndex);
            String oldName = file.getName();

            if (oldName.equals(newName)) {
                fileIndex++;
                continue;
            }

            File newFile = new File(currentFolder, newName);

            if (newFile.exists()) {
                int overwrite = JOptionPane.showConfirmDialog(null,
                        "文件 " + newName + " 已存在，是否覆盖？",
                        "文件冲突",
                        JOptionPane.YES_NO_OPTION);
                if (overwrite != JOptionPane.YES_OPTION) {
                    fileIndex++;
                    continue;
                }
            }

            boolean success = file.renameTo(newFile);
            if (success) {
                successCount++;
                currentHistory.add(new String[]{newFile.getAbsolutePath(), file.getAbsolutePath()});
            } else {
                System.err.println("重命名失败：" + oldName);
            }

            fileIndex++;
        }

        progressBar.setVisible(false);
        progressBar.setValue(0);

        if (!currentHistory.isEmpty()) {
            renameHistory.add(0, currentHistory);
        }

        JOptionPane.showMessageDialog(null,
                "重命名完成！\n成功：" + successCount + " 个\n失败：" + (previewNewNames.size() - successCount) + " 个\n\n提示：可以点击「撤销上次重命名」恢复");

        refreshFileList();
        previewListModel.clear();
        executeButton.setEnabled(false);
        previewNewNames = null;
    }

    /**
     * 撤销上次重命名（支持多步撤销）
     */
    private void undo() {
        if (renameHistory == null || renameHistory.isEmpty()) {
            JOptionPane.showMessageDialog(null, "没有可撤销的操作！");
            return;
        }

        // 让用户选择撤销几步
        String[] options = {"撤销1步", "撤销2步", "撤销3步", "撤销全部"};
        int choice = JOptionPane.showOptionDialog(null,
                "当前共有 " + renameHistory.size() + " 条历史记录\n请选择要撤销的步数",
                "撤销选择",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        int stepsToUndo = 0;
        switch (choice) {
            case 0: stepsToUndo = 1; break;
            case 1: stepsToUndo = 2; break;
            case 2: stepsToUndo = 3; break;
            case 3: stepsToUndo = renameHistory.size(); break;
            default: return;
        }

        // 限制步数
        stepsToUndo = Math.min(stepsToUndo, renameHistory.size());

        int totalUndoCount = 0;

        for (int step = 0; step < stepsToUndo; step++) {
            List<String[]> lastRenames = renameHistory.get(0);

            for (String[] paths : lastRenames) {
                File newFile = new File(paths[0]);
                File oldFile = new File(paths[1]);

                if (newFile.exists()) {
                    boolean success = newFile.renameTo(oldFile);
                    if (success) {
                        totalUndoCount++;
                    } else {
                        System.err.println("撤销失败：" + newFile.getName());
                    }
                }
            }

            renameHistory.remove(0);
        }

        JOptionPane.showMessageDialog(null,
                "撤销完成！\n共撤销了 " + stepsToUndo + " 步操作\n恢复了 " + totalUndoCount + " 个文件");

        refreshFileList();
        previewListModel.clear();
        executeButton.setEnabled(false);
        previewNewNames = null;
    }

    /**
     * 显示正则表达式帮助对话框
     */
    private void showRegexHelp() {
        String helpText = """
            正则表达式筛选帮助
            ====================
            
            正则表达式会匹配【完整文件名】（包括扩展名）
            
            常用示例：
            ─────────────────────────────────────────
            ^测试.*         → 以"测试"开头的文件
            .*\\.txt$       → 以.txt结尾的文件
            ^\\d+           → 以数字开头的文件
            .*测试.*        → 文件名中包含"测试"
            ^[A-Z].*        → 以大写字母开头的文件
            ^(?!测试).*     → 不以"测试"开头的文件
            .*\\.(jpg|png)$ → jpg或png图片文件
            \\d{4}          → 文件名中包含4位连续数字
            
            特殊字符说明：
            ─────────────────────────────────────────
            .   匹配任意单个字符
            *   匹配前面的字符0次或多次
            +   匹配前面的字符1次或多次
            ?   匹配前面的字符0次或1次
            \\d  匹配数字
            \\s  匹配空白字符
            ^   匹配开头
            $   匹配结尾
            |   或运算
            []  字符集，如[A-Z]表示大写字母
            ()  分组
            
            注意：Java正则中，点号.需要转义为\\.
            """;

        JTextArea textArea = new JTextArea(helpText);
        textArea.setEditable(false);
        textArea.setFont(new Font("宋体", Font.PLAIN, 12));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 450));

        JOptionPane.showMessageDialog(null, scrollPane,
                "正则表达式帮助", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 保存用户设置
     */
    private void saveSettings() {
        try {
            java.util.Properties props = new java.util.Properties();

            if (currentFolder != null) {
                props.setProperty("lastFolder", currentFolder.getAbsolutePath());
            }

            props.setProperty("lastRule", (String) ruleComboBox.getSelectedItem());

            if (param1Field != null && param1Field.getText() != null) {
                props.setProperty("lastParam1", param1Field.getText());
            }
            if (param2Field != null && param2Field.getText() != null) {
                props.setProperty("lastParam2", param2Field.getText());
            }
            if (sequenceWidthField != null && sequenceWidthField.getText() != null) {
                props.setProperty("lastSequenceWidth", sequenceWidthField.getText());
            }
            if (filterField != null && filterField.getText() != null) {
                props.setProperty("lastFilter", filterField.getText());
            }

            java.io.File configFile = new java.io.File(System.getProperty("user.home"), ".renamer.properties");
            try (java.io.FileOutputStream out = new java.io.FileOutputStream(configFile)) {
                props.store(out, "File Renamer Settings");
            }

        } catch (Exception e) {
            System.err.println("保存设置失败：" + e.getMessage());
        }
    }

    /**
     * 加载用户设置
     */
    private void loadSettings() {
        try {
            java.io.File configFile = new java.io.File(System.getProperty("user.home"), ".renamer.properties");
            if (!configFile.exists()) return;

            java.util.Properties props = new java.util.Properties();
            try (java.io.FileInputStream in = new java.io.FileInputStream(configFile)) {
                props.load(in);
            }

            String lastFolder = props.getProperty("lastFolder");
            if (lastFolder != null && !lastFolder.isEmpty()) {
                File folder = new File(lastFolder);
                if (folder.exists() && folder.isDirectory()) {
                    currentFolder = folder;
                    folderPathField.setText(lastFolder);
                    refreshFileList();
                    previewButton.setEnabled(true);
                }
            }

            String lastRule = props.getProperty("lastRule");
            if (lastRule != null) {
                ruleComboBox.setSelectedItem(lastRule);
            }

            String lastParam1 = props.getProperty("lastParam1");
            String lastParam2 = props.getProperty("lastParam2");
            String lastWidth = props.getProperty("lastSequenceWidth");
            String lastFilter = props.getProperty("lastFilter");

            // 恢复筛选条件
            if (lastFilter != null && filterField != null) {
                filterField.setText(lastFilter);
            }

            SwingUtilities.invokeLater(() -> {
                if (lastParam1 != null && param1Field != null) {
                    param1Field.setText(lastParam1);
                }
                if (lastParam2 != null && param2Field != null) {
                    param2Field.setText(lastParam2);
                }
                if (lastWidth != null && sequenceWidthField != null) {
                    sequenceWidthField.setText(lastWidth);
                }
                // 应用筛选
                if (lastFilter != null && !lastFilter.isEmpty()) {
                    refreshFileList();
                }
            });

        } catch (Exception e) {
            System.err.println("加载设置失败：" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileRenamerGUI());
    }
}