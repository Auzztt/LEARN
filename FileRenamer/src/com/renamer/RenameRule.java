package com.renamer;

/**
 * 重命名规则工具类
 */
public class RenameRule {

    /**
     * 规则1：查找替换
     * @param originalName 原文件名
     * @param search 要查找的字符串
     * @param replace 替换成的字符串
     * @return 新文件名
     */
    public static String replace(String originalName, String search, String replace) {
        if (search == null || search.isEmpty()) {
            return originalName;
        }
        return originalName.replace(search, replace);
    }

    /**
     * 规则2：添加前缀
     * @param originalName 原文件名
     * @param prefix 要添加的前缀
     * @return 新文件名
     */
    public static String addPrefix(String originalName, String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        return prefix + originalName;
    }

    /**
     * 规则3：添加后缀（在扩展名之前添加）
     * @param originalName 原文件名
     * @param suffix 要添加的后缀
     * @return 新文件名
     */
    public static String addSuffix(String originalName, String suffix) {
        if (suffix == null) {
            suffix = "";
        }

        // 查找最后一个点的位置（扩展名的分隔符）
        int dotIndex = originalName.lastIndexOf(".");

        if (dotIndex == -1) {
            // 没有扩展名，直接添加后缀
            return originalName + suffix;
        }

        // 分离文件名和扩展名
        String nameWithoutExt = originalName.substring(0, dotIndex);
        String extension = originalName.substring(dotIndex);

        return nameWithoutExt + suffix + extension;
    }

    /**
     * 规则4：序号填充（在文件名前加序号）
     * @param originalName 原文件名
     * @param index 序号（从1开始）
     * @param width 序号宽度（如3表示001,002）
     * @return 新文件名
     */
    public static String addSequence(String originalName, int index, int width) {
        // 格式化序号：%0nd 表示n位数字，不足前面补0
        String sequence = String.format("%0" + width + "d", index);
        return sequence + "_" + originalName;
    }

    /**
     * 规则5：修改扩展名
     * @param originalName 原文件名
     * @param newExtension 新扩展名（不要带点，比如输入"txt"而不是".txt"）
     * @return 新文件名
     */
    public static String changeExtension(String originalName, String newExtension) {
        if (newExtension == null || newExtension.isEmpty()) {
            return originalName;
        }

        // 去掉可能带的点
        if (newExtension.startsWith(".")) {
            newExtension = newExtension.substring(1);
        }

        int dotIndex = originalName.lastIndexOf(".");
        if (dotIndex == -1) {
            // 没有扩展名，直接添加
            return originalName + "." + newExtension;
        }

        String nameWithoutExt = originalName.substring(0, dotIndex);
        return nameWithoutExt + "." + newExtension;
    }
}